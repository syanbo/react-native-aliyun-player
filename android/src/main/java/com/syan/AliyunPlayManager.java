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
        AliyunPlayerView playView = new AliyunPlayerView(context);
        this.initSurfaceView();
        playView.addView(mSurfaceView);

        return playView;
    }

    @ReactProp(name = "exampleProp")
    public void setExampleProp(AliyunPlayerView view, String prop) {
        Log.e(TAG, "exampleProp" + prop);
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

        AliyunVidSts mVidSts = new AliyunVidSts();
        mVidSts.setVid("04aa91c809c741178d76af4b95f1b680");
        mVidSts.setAcId("LTAIH1j0TQwkhfjC");
        mVidSts.setAkSceret("M8wXRq0Vq03JY8jHdMddCupyAPNvJs");
        mVidSts.setSecurityToken("eyJTZWN1cml0eVRva2VuIjoiQ0FJUzN3SjFxNkZ0NUIyeWZTaklyNG5kQjhuT25JZ1cvSWFLWldYbTEwUS9ZN2g5cWEzZHFEejJJSHBOZTNocUIrMGZzUGt3bEdsVTZmZ2Nsck1xRnNjZkhoYWVONUVxdE1RUHExUDRKcExGc3QySjZyOEpqc1ZkcE1OazNscXBzdlhKYXNEVkVma3VFNVhFTWlJNS8wMGU2TC8rY2lyWVhEN0JHSmFWaUpsaFE4MEtWdzJqRjFSdkQ4dFhJUTBRazYxOUszemRaOW1nTGlidWkzdnhDa1J2MkhCaWptOHR4cW1qL015UTV4MzFpMXYweStCM3dZSHRPY3FjYThCOU1ZMVdUc3Uxdm9oemFyR1Q2Q3BaK2psTStxQVU2cWxZNG1YcnM5cUhFa0ZOd0JpWFNaMjJsT2RpTndoa2ZLTTNOcmRacGZ6bjc1MUN0L2ZVaXA3OHhtUW1YNGdYY1Z5R0ZkMzhtcE9aUXJ6eGFvWmdLZStxQVJtWGpJRFRiS3VTbWhnL2ZIY1dPRGxOZjljY01YSnFBWFF1TUdxQWMvRDJvZzZYTzFuK0ZQamNqUDVvajRBSjVsSHA3TWVNR1YrRGVMeVF5aDBFSWFVN2EwNDQxTUtpUXVranBzUWFnQUdUcTdBbVZMOWtuV2dzMXVzd0o2bHNXZWVzaUVKU2owUmROa01ySkVOejI3R0FWdUYrVzFZQkRGNVA1dFBsYk45ZDMreE02QkQyTHJVdUdMT1dCbXE1b2JyOVJmeW95MTBNZ2FFS1NObVI0VUl1dXFSdjdweFFscnFWNmlBcGZZR0NaV1VxM0JTQW5nM0VLY1hoS1QyeTZVbVBSYW8wait5Tkk1d2o4cC8zdFE9PSIsIkF1dGhJbmZvIjoie1wiQ2FsbGVyXCI6XCJZMXN3bnBGK2RGL2g4RVVLeTBSU0xOcHVWMGxnQ3ZvSXNyUU1PcE9ROXRRPVxcclxcblwiLFwiRXhwaXJlVGltZVwiOlwiMjAxOC0wNi0wNlQwNjoxNDozMVpcIixcIk1lZGlhSWRcIjpcIjhlODgwZjRlNjg1MjQ4MzY4ZDZjMDgyMjJlNzlmMmYyXCIsXCJTaWduYXR1cmVcIjpcIkpqaHhvcmRqSVFXNXRqZVJoRW1zemNjRmVOMD1cIn0iLCJWaWRlb01ldGEiOnsiU3RhdHVzIjoiTm9ybWFsIiwiVmlkZW9JZCI6IjhlODgwZjRlNjg1MjQ4MzY4ZDZjMDgyMjJlNzlmMmYyIiwiVGl0bGUiOiLor77nqIvlvJXlhaXigJTigJTnmb3ph5Hov5jmmK");
        mAliyunVodPlayer.prepareAsync(mVidSts);

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
