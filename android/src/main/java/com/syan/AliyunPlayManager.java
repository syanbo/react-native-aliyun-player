package com.syan;

import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;

import com.alivc.player.VcPlayerLog;
import com.aliyun.vodplayer.media.AliyunVidSts;
import com.aliyun.vodplayer.media.IAliyunVodPlayer;
import com.aliyun.vodplayer.media.AliyunVodPlayer;

public class AliyunPlayManager extends SimpleViewManager<AliyunPlayerView> {
    private static final String TAG = "AliyunPlayManager";
    public static final String REACT_CLASS = "AliyunPlay";

    //视频画面
    private SurfaceView mSurfaceView;
    //播放器
    private AliyunVodPlayer mAliyunVodPlayer;

    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @Override
    public AliyunPlayerView createViewInstance(ThemedReactContext context) {
        AliyunPlayerView view = new AliyunPlayerView(context);

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
            }
        });

        mAliyunVodPlayer.setOnErrorListener(new IAliyunVodPlayer.OnErrorListener() {
            @Override
            public void onError(int errorCode, int errorEvent, String errorMsg) {
                Log.e(TAG, "onError" + errorMsg);
            }
        });

    }
}
