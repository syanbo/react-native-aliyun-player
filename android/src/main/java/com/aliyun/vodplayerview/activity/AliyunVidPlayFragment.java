package com.aliyun.vodplayerview.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.aliyun.vodplayer.R;
import com.aliyun.vodplayerview.constants.PlayParameter;
import com.aliyun.vodplayerview.utils.VidStsUtil;

import java.lang.ref.WeakReference;

/**
 * vid设置界面
 * Created by Mulberry on 2018/4/4.
 */
public class AliyunVidPlayFragment extends Fragment {

    EditText etVid;
    EditText etAkId;
    EditText etAkSecret;
    EditText etScuToken;
    /**
     * get StsToken stats
     */
    private boolean inRequest;

    /**
     * 返回给上个activity的resultcode: 100为vid播放类型, 200为URL播放类型
     */
    private static final int CODE_RESULT_VID = 100;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_player_vidplay_layout, container, false);

        initStsView(v);
        return v;
    }

    private void initStsView(View v){
        etVid = (EditText)v.findViewById(R.id.vid);
        etAkId = (EditText)v.findViewById(R.id.akId);
        etAkSecret = (EditText)v.findViewById(R.id.akSecret);
        etScuToken = (EditText)v.findViewById(R.id.scuToken);
    }

    public void startToPlayerByVid(){
        String mVid = etVid.getText().toString();
        String akId = etAkId.getText().toString();
        String akSecret = etAkSecret.getText().toString();
        String scuToken = etScuToken.getText().toString();
        PlayParameter.PLAY_PARAM_TYPE = "vidsts";
        if (TextUtils.isEmpty(mVid) || TextUtils.isEmpty(akId) || TextUtils.isEmpty(akSecret) || TextUtils.isEmpty(scuToken)) {
            if(inRequest){
                return;
            }

            inRequest = true;
            VidStsUtil.getVidSts(mVid,new MyStsListener(this));

        } else {

            PlayParameter.PLAY_PARAM_VID= mVid;
            PlayParameter.PLAY_PARAM_AK_ID = akId;
            PlayParameter.PLAY_PARAM_AK_SECRE = akSecret;
            PlayParameter.PLAY_PARAM_SCU_TOKEN = scuToken;

            getActivity().setResult(CODE_RESULT_VID);
            getActivity().finish();
        }
    }

    private static class MyStsListener implements VidStsUtil.OnStsResultListener{

        private WeakReference<AliyunVidPlayFragment> weakctivity;

        public MyStsListener(AliyunVidPlayFragment view)
        {
            weakctivity = new WeakReference<AliyunVidPlayFragment>(view);
        }

        @Override
        public void onSuccess(String vid, String akid, String akSecret, String token) {
            AliyunVidPlayFragment fragment = weakctivity.get();
            if(fragment != null){
                fragment.onStsSuccess(vid,akid,akSecret,token);
            }
        }

        @Override
        public void onFail() {
            AliyunVidPlayFragment fragment = weakctivity.get();
            if(fragment != null){
                fragment.onStsFail();
            }
        }
    }

    private void onStsFail() {

        Toast.makeText(AliyunVidPlayFragment.this.getActivity().getApplicationContext(), R.string.request_vidsts_fail, Toast.LENGTH_LONG).show();
        inRequest = false;
    }

    private void onStsSuccess(String mVid,String akid, String akSecret, String token) {

        PlayParameter.PLAY_PARAM_VID= mVid;
        PlayParameter.PLAY_PARAM_AK_ID = akid;
        PlayParameter.PLAY_PARAM_AK_SECRE = akSecret;
        PlayParameter.PLAY_PARAM_SCU_TOKEN = token;

        getActivity().setResult(CODE_RESULT_VID);
        getActivity().finish();

        inRequest = false;
    }

}
