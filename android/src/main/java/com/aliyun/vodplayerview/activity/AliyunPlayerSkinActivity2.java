//package com.aliyun.vodplayerview.activity;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v7.app.AppCompatActivity;
//import android.widget.RelativeLayout;
//
//import com.aliyun.vodplayer.R;
//import com.aliyun.vodplayerview.view.RNAliyunPlayer;
//import com.aliyun.vodplayerview.view.RNAliyunPlayerCallBack;
//
///**
// * 播放器和播放列表界面 Created by Mulberry on 2018/4/9.
// */
//public class AliyunPlayerSkinActivity2 extends AppCompatActivity {
//    /**
//     * 开启设置界面的请求码
//     */
//    private static final int CODE_REQUEST_SETTING = 1000;
//
//    RelativeLayout rl;
//    RNAliyunPlayer player;
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_aliyun_one);
//
//        rl = findViewById(R.id.aliyun_test_rl);
//        player = new RNAliyunPlayer(getApplicationContext());
//        rl.addView(player);
////        player.initUI(callBack, this);
//
//    }
//
//    RNAliyunPlayerCallBack callBack = new RNAliyunPlayerCallBack() {
//        @Override
//        public void tvStartSettingOnCLick() {
//            Intent intent = new Intent(AliyunPlayerSkinActivity2.this, AliyunPlayerSettingActivity.class);
//            // 开启时, 默认为vid
//            startActivityForResult(intent, CODE_REQUEST_SETTING);
//        }
//    };
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        player.onActivityResult(requestCode, resultCode, data);
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        player.onResume();
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        player.onStop();
//    }
//
//    @Override
//    protected void onDestroy() {
//        player.onDestroy();
//        super.onDestroy();
//    }
//}
