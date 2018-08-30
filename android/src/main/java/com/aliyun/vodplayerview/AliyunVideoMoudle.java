package com.aliyun.vodplayerview;


import android.util.Log;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

/**
 * Created by Administrator on 2017/9/16.
 */

public class AliyunVideoMoudle extends ReactContextBaseJavaModule {
    ReactApplicationContext context;
    public AliyunVideoMoudle(ReactApplicationContext context) {
        super(context);
    }

    @Override
    public String getName() {
        return "AliyunVideo";
    }


    @ReactMethod
    public void start() {
        Log.i(getName(), "start");
//        ALiVideoViewManager.getInstance().initUI();
//        ALiVideoViewManager.getInstance().setSource("http://player.alicdn.com/video/aliyunmedia.mp4");
        ALiVideoViewManager.getInstance().onResume();
    }
    @ReactMethod
    public void setUrl() {
        Log.i(getName(), "start");
//        ALiVideoViewManager.getInstance().initUI();
        ALiVideoViewManager.getInstance().setSource("http://player.alicdn.com/video/aliyunmedia.mp4");
//        ALiVideoViewManager.getInstance().onResume();
    }
    @ReactMethod
    public void pause() {

        ALiVideoViewManager.getInstance().onStop();
    }

    @ReactMethod
    public void release() {
//        ALiVideoViewManager.getInstance().release();
    }

    /**
     * 重新设置render渲染目标，该方法能达到抹去之前视频最后一帧的效果<br>
     * 一般在stopPlayBack后，设置新播放源之前调用。
     */
    @ReactMethod
    public void reSetRender() {
//        ALiVideoViewManager.getInstance().reSetRender();
    }

    /**
     * 将播放器指定到某个播放位置
     */
    @ReactMethod
    public void seekTo(int i) {
//        ALiVideoViewManager.getInstance().seekTo(i);
    }

    /**
     * 获得视频时长，单位为毫秒！
     *
     * @return
     */
    @ReactMethod
    public void getDuration(Callback successCallback) {
//        successCallback.invoke(ALiVideoViewManager.getInstance().getDuration());
    }

    /**
     * 获取当前播放进度，单位为毫秒！
     *
     * @return
     */
    @ReactMethod
    public void getCurrentPosition(Callback successCallback) {
//        successCallback.invoke(ALiVideoViewManager.getInstance().getCurrentPosition());
    }

    /**
     * 获取视频宽度,高度
     *
     * @return
     */
    @ReactMethod
    public void getVideoLayout(Callback successCallback) {
//        successCallback.invoke(ALiVideoViewManager.getInstance().getVideoWidth(),ALiVideoViewManager.getInstance().getVideoHeight());
    }

    /**
     * 当前视频是否在播放
     *
     * @return
     */
    @ReactMethod
    public void isPlaying(Callback successCallback) {
//        successCallback.invoke(ALiVideoViewManager.getInstance().isPlaying());
    }


}
