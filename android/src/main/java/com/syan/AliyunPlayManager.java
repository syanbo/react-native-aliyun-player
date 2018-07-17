//  Created by react-native-create-bridge

package com.syan;

import android.content.Context;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.alivc.player.VcPlayerLog;
import com.aliyun.vodplayer.media.IAliyunVodPlayer;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;

import com.facebook.react.uimanager.annotations.ReactProp;
import com.aliyun.vodplayer.media.AliyunVodPlayer;

public class AliyunPlayManager extends SimpleViewManager<AliyunPlayerView> {
    private static final String TAG = "AliyunPlayManager";
    public static final String REACT_CLASS = "AliyunPlay";

    private Context context;
    //视频画面
    private SurfaceView mSurfaceView;

    //播放器
    private AliyunVodPlayer mAliyunVodPlayer;

    @Override
    public String getName() {
        // Tell React the name of the module
        // https://facebook.github.io/react-native/docs/native-components-android.html#1-create-the-viewmanager-subclass
        return REACT_CLASS;
    }

    @Override
    public AliyunPlayerView createViewInstance(ThemedReactContext context){
        this.context = context;
        // Create a view here
        // https://facebook.github.io/react-native/docs/native-components-android.html#2-implement-method-createviewinstance


        return new AliyunPlayerView(context);
    }

    @ReactProp(name = "exampleProp")
    public void setExampleProp(AliyunPlayerView view, String prop) {
        Log.e(TAG, "exampleProp" + prop);
        this.initSurfaceView();
        view.addView(mSurfaceView);
        // Set properties from React onto your native component via a setter method
        // https://facebook.github.io/react-native/docs/native-components-android.html#3-expose-view-property-setters-using-reactprop-or-reactpropgroup-annotation
    }

    /**
     * 初始化播放器显示view
     */
    private void initSurfaceView() {
        mSurfaceView = new SurfaceView(this.context);
        SurfaceHolder holder = mSurfaceView.getHolder();

        mAliyunVodPlayer = new AliyunVodPlayer(context);
        mAliyunVodPlayer.setDisplay(holder);

        Log.e(TAG, "版本号" + AliyunVodPlayer.getSDKVersion());

        mAliyunVodPlayer.setOnPreparedListener(new IAliyunVodPlayer.OnPreparedListener() {
            @Override
            public void onPrepared() {
                Log.e(TAG, "onPrepared");
                //准备完成触发
            }
        });

        mAliyunVodPlayer.setOnErrorListener(new IAliyunVodPlayer.OnErrorListener() {
            @Override
            public void onError(int errorCode, int errorEvent, String errorMsg) {
                Log.e(TAG, "onError" + errorMsg);
            }
        });
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
    }
}
