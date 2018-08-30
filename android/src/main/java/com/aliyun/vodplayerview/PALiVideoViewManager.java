package com.aliyun.vodplayerview;


import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.RelativeLayout;


import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.aliyun.vodplayerview.view.RNAliyunPlayer;


public class PALiVideoViewManager extends SimpleViewManager<RNAliyunPlayer> {
    private String TAG = PALiVideoViewManager.class.getSimpleName();
    private static final String REACT_CLASS = "AliyunPlay";


    /**
     * 开启设置界面的请求码
     */
    private static final int CODE_REQUEST_SETTING = 1000;

    RelativeLayout rl;
    RNAliyunPlayer player;

    @Override
    public String getName() {
        return REACT_CLASS;
    }

    private static RNAliyunPlayer currentCloudVideoView;
    private static RNAliyunPlayer lastCloudVideoView;
    ThemedReactContext reactContext;
    @Override
    protected RNAliyunPlayer createViewInstance(ThemedReactContext reactContext) {
        this.reactContext = reactContext;
        lastCloudVideoView = new RNAliyunPlayer(reactContext);
        lastCloudVideoView.initUI();
        return lastCloudVideoView;
    }

    //    RNAliyunPlayerCallBack callBack = new RNAliyunPlayerCallBack() {
//        @Override
//        public void tvStartSettingOnCLick() {
//            Intent intent = new Intent(AliyunPlayerSkinActivity2.this, AliyunPlayerSettingActivity.class);
//            // 开启时, 默认为vid
//            startActivityForResult(intent, CODE_REQUEST_SETTING);
//        }
//    };
    public static RNAliyunPlayer getInstance() {
        if (currentCloudVideoView != null) {
            return currentCloudVideoView;
        }
        return lastCloudVideoView;
    }

    @ReactProp(name = "setCurrent")
    public void setCurrent(RNAliyunPlayer rnAliyunPlayer, @Nullable int i) {
        currentCloudVideoView = rnAliyunPlayer;
    }

    @ReactProp(name = "url")
    public void setUrl(RNAliyunPlayer rnAliyunPlayer, @Nullable String url) {
        Log.i(TAG, "setUrl;".concat(url));
//        player.initUI();
        PALiVideoViewManager.getInstance().setSource(url);

//        player.setSource(url);

//        RNAliyunPlayer.setVideoScalingMode(rnAliyunPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT);

    }

    /**
     * i=1 填充，保持视频内容的宽高比。视频与屏幕宽高不一致时，会留有黑边
     * i=2 裁剪，保持视频内容的宽高比。视频与屏幕宽高不一致时，会裁剪部分视频内容
     * i=3 铺满，不保证视频内容宽高比。视频显示与屏幕宽高相等
     */
    public static final int VIDEO_SCALING_MODE_SCALE_TO_MATCH_PARENT = 3;


    @ReactProp(name = "setVideoScalingMode")
    public void setVideoScalingMode(RNAliyunPlayer RNAliyunPlayer, @Nullable int i) {
//        RNAliyunPlayer.setVideoScalingMode(i);
    }

    @ReactProp(name = "start")
    public void start(RNAliyunPlayer rnAliyunPlayer, @Nullable int i) {
//        RNAliyunPlayer.start();
//        rnAliyunPlayer.initUI();
        //        PALiVideoViewManager.getInstance().setSource("http://player.alicdn.com/video/aliyunmedia.mp4");
        PALiVideoViewManager.getInstance().onResume();

    }

    @ReactProp(name = "pause")
    public void pause(RNAliyunPlayer RNAliyunPlayer, @Nullable int i) {
        PALiVideoViewManager.getInstance().onStop();
    }


    @ReactProp(name = "release")
    public void release(RNAliyunPlayer RNAliyunPlayer, @Nullable int i) {
//        RNAliyunPlayer.release();
    }


    /**
     * 将播放器指定到某个播放位置
     */
    @ReactProp(name = "seekTo")
    public void seekTo(RNAliyunPlayer RNAliyunPlayer, @Nullable int i) {
//        RNAliyunPlayer.seekTo(i);
    }

    /**
     * 重新设置render渲染目标，该方法能达到抹去之前视频最后一帧的效果<br>
     * 一般在stopPlayBack后，设置新播放源之前调用。
     */
    @ReactProp(name = "reSetRender")
    public void reSetRender(RNAliyunPlayer RNAliyunPlayer, @Nullable int i) {
//        RNAliyunPlayer.reSetRender();
    }

}