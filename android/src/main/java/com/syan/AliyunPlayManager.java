package com.syan;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;

import com.alivc.player.VcPlayerLog;
import com.aliyun.vodplayer.media.AliyunVidSts;
import com.aliyun.vodplayer.media.IAliyunVodPlayer;
import com.aliyun.vodplayer.media.AliyunVodPlayer;
import com.facebook.react.uimanager.events.RCTEventEmitter;

import java.lang.ref.WeakReference;

public class AliyunPlayManager extends SimpleViewManager<AliyunPlayerView> {
    private static final String TAG = "AliyunPlayManager";
    public static final String REACT_CLASS = "AliyunPlay";
    private static final String PLAYING_CALLBACK = "onPlayingCallback";
    private static final String EVENT_CALLBACK = "onEventCallback";

    //视频画面
    private SurfaceView mSurfaceView;
    // 组件view
    private AliyunPlayerView mAliyunPlayerView;
    //播放器
    private AliyunVodPlayer mAliyunVodPlayer;
    // 播放进度计时器
    private ProgressUpdateTimer mProgressUpdateTimer;
    // 事件发送者
    private RCTEventEmitter mEventEmitter;

    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @Override
    public AliyunPlayerView createViewInstance(ThemedReactContext context) {
        mProgressUpdateTimer = new ProgressUpdateTimer(AliyunPlayManager.this);
        AliyunPlayerView view = new AliyunPlayerView(context);
        mAliyunPlayerView = view;

        mSurfaceView = new SurfaceView(context);
        view.addView(mSurfaceView);
        SurfaceHolder holder = mSurfaceView.getHolder();

        mAliyunVodPlayer = new AliyunVodPlayer(context);
        mAliyunVodPlayer.setDisplay(holder);

        //增加surfaceView的监听
        holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                VcPlayerLog.d(TAG, " surfaceCreated = surfaceHolder = " + surfaceHolder);
                mAliyunVodPlayer.setDisplay(surfaceHolder);
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int width,
                                       int height) {
                VcPlayerLog.d(TAG, " surfaceChanged surfaceHolder = " + surfaceHolder + " ,  width = " + width + " , height = " + height);
                mAliyunVodPlayer.surfaceChanged();
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                VcPlayerLog.d(TAG, " surfaceDestroyed = surfaceHolder = " + surfaceHolder);
            }
        });

        this.onListener();
        return view;
    }

    /**
     * 准备视频(异步)
     */
    @ReactProp(name = "prepareAsyncParams")
    public void setPrepareAsyncParams(AliyunPlayerView view, ReadableMap options) {

        String type = options.getString("type");

        switch (type) {
            // 使用vid+STS方式播放（点播用户推荐使用）
            case "vidSts":
                String vid = options.getString("vid");
                String accessKeyId = options.getString("accessKeyId");
                String accessKeySecret = options.getString("accessKeySecret");
                String securityToken = options.getString("securityToken");

                AliyunVidSts mVidSts = new AliyunVidSts();
                mVidSts.setVid(vid);
                mVidSts.setAcId(accessKeyId);
                mVidSts.setAkSceret(accessKeySecret);
                mVidSts.setSecurityToken(securityToken);

                mAliyunVodPlayer.prepareAsync(mVidSts);
                break;

            default:
                Log.e(TAG, "prepareAsync" + type);
                break;
        }
    }

    /**
     * 播放器监听事件
     */
    private void onListener() {

        Log.e(TAG, "版本号" + AliyunVodPlayer.getSDKVersion());

        mAliyunVodPlayer.setOnPreparedListener(new IAliyunVodPlayer.OnPreparedListener() {
            @Override
            public void onPrepared() {
                Log.e(TAG, "onPrepared");
                mAliyunVodPlayer.start();
                //准备完成触发

                // TODO：待优化的 listener 处理，应该新建个独立文件处理？
                WritableMap body = Arguments.createMap();
                body.putInt("event", mAliyunVodPlayer.getPlayerState().ordinal());
                mEventEmitter.receiveEvent(mAliyunPlayerView.getId(), EVENT_CALLBACK, body);
            }
        });

        // 第一帧显示
        mAliyunVodPlayer.setOnFirstFrameStartListener(new IAliyunVodPlayer.OnFirstFrameStartListener() {
            @Override
            public void onFirstFrameStart() {
                // 开始启动更新进度的定时器
                startProgressUpdateTimer();
                // TODO：待优化的 listener 处理，应该新建个独立文件处理？
                WritableMap body = Arguments.createMap();
                body.putInt("event", mAliyunVodPlayer.getPlayerState().ordinal());
                mEventEmitter.receiveEvent(mAliyunPlayerView.getId(), EVENT_CALLBACK, body);
            }
        });

        mAliyunVodPlayer.setOnErrorListener(new IAliyunVodPlayer.OnErrorListener() {
            @Override
            public void onError(int errorCode, int errorEvent, String errorMsg) {
                Log.e(TAG, "onError" + errorMsg);
                // 停止定时器
                stopProgressUpdateTimer();
            }
        });

    }

    /**
     * 开始播放进度计时器
     */
    private void startProgressUpdateTimer() {
        if (mProgressUpdateTimer != null) {
            mProgressUpdateTimer.removeMessages(0);
            mProgressUpdateTimer.sendEmptyMessageDelayed(0, 1000);
        }
    }

    /**
     * 停止播放进度计时器
     */
    private void stopProgressUpdateTimer() {
        if (mProgressUpdateTimer != null) {
            mProgressUpdateTimer.removeMessages(0);
        }
    }

    /**
     * 更新播放进度
     */
    private void handlePlayingMessage(Message message) {
        if (mAliyunVodPlayer != null) {
            long currentTime = mAliyunVodPlayer.getCurrentPosition();
            long duration = mAliyunVodPlayer.getDuration();
            WritableMap body = Arguments.createMap();
            body.putString("currentTime", currentTime + "");
            body.putString("duration", duration + "");
            mEventEmitter.receiveEvent(mAliyunPlayerView.getId(), PLAYING_CALLBACK, body);
        }

        startProgressUpdateTimer();
    }

    /**
     * 播放进度计时器
     */
    private static class ProgressUpdateTimer extends Handler {
        private WeakReference<AliyunPlayManager> managerWeakReference;

        ProgressUpdateTimer(AliyunPlayManager playManager) {
            managerWeakReference = new WeakReference<AliyunPlayManager>(playManager);
        }

        @Override
        public void handleMessage(Message msg) {
            AliyunPlayManager playManager = managerWeakReference.get();
            if (playManager != null) {
                playManager.handlePlayingMessage(msg);
            }
            super.handleMessage(msg);
        }
    }
}
