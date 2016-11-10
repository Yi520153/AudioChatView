package com.lcl.audiochatview;

import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.lcl.audiochatview.view.CycleLoading;

public class MainActivity extends AppCompatActivity {

    private LinearLayout audioReceive;
    private CycleLoading audioReceiveCycle;

    private LinearLayout audioSend;
    private CycleLoading audioSendCycle;

    private LinearLayout audioReceiveAnim;
    private ImageView audioReceiveAnimImg;

    private LinearLayout audioSendAnim;
    private ImageView audioSendAnimImg;

    private Drawable[] audioReceiveDrawable;
    private Drawable[] audioSendDrawable;

    private AnimationDrawable animationDrawableReceive;
    private AnimationDrawable animationDrawableSend;

    private boolean receiveIsStart=false;
    private boolean sendIsStart=false;
    private boolean receiveAnimIsStart=false;
    private boolean sendAnimIsStart=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        initEvent();
    }

    private void initView() {
        audioReceive = (LinearLayout) findViewById(R.id.audioReceive);
        audioReceiveCycle = (CycleLoading) findViewById(R.id.audioReceiveCycle);

        audioSend = (LinearLayout) findViewById(R.id.audioSend);
        audioSendCycle = (CycleLoading) findViewById(R.id.audioSendCycle);

        audioReceiveAnim= (LinearLayout) findViewById(R.id.audioReceiveAnim);
        audioReceiveAnimImg= (ImageView) findViewById(R.id.audioReceiveAnimImg);

        audioSendAnim= (LinearLayout) findViewById(R.id.audioSendAnim);
        audioSendAnimImg= (ImageView) findViewById(R.id.audioSendAnimImg);
    }

    private void initData() {
        //初始化资源
        audioReceiveDrawable=new Drawable[]{getResources().getDrawable(R.drawable.chatfrom_voice_playing_f1),
                getResources().getDrawable(R.drawable.chatfrom_voice_playing_f2),getResources().
                getDrawable(R.drawable.chatfrom_voice_playing_f3)};
        audioSendDrawable=new Drawable[]{getResources().getDrawable(R.drawable.chatto_voice_playing_f1),
                getResources().getDrawable(R.drawable.chatto_voice_playing_f2),
                getResources().getDrawable(R.drawable.chatto_voice_playing_f3)};

        //初始化动画
        animationDrawableReceive= (AnimationDrawable) audioReceiveAnimImg.getDrawable();
        animationDrawableSend= (AnimationDrawable) audioSendAnimImg.getDrawable();
    }

    private void initEvent() {
        //自定义View接收
        audioReceive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!receiveIsStart){
                    audioReceiveCycle.setDrawable(audioReceiveDrawable);
                    audioReceiveCycle.start();
                    receiveIsStart=true;
                }else{
                    audioReceiveCycle.stop();
                    receiveIsStart=false;
                }
            }
        });

        //自定义View发送
        audioSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!sendIsStart){
                    audioSendCycle.setDrawable(audioSendDrawable);
                    audioSendCycle.start();
                    sendIsStart=true;
                }else{
                    audioSendCycle.stop();
                    sendIsStart=false;
                }
            }
        });

        //帧动画接收
        audioReceiveAnim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!receiveAnimIsStart){
                    animationDrawableReceive.start();
                    receiveAnimIsStart=true;
                }else{
                    animationDrawableReceive.stop();
                    receiveAnimIsStart=false;
                }
            }
        });

        //帧动画发送
        audioSendAnim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!sendAnimIsStart){
                    animationDrawableSend.start();
                    sendAnimIsStart=true;
                }else{
                    animationDrawableSend.stop();
                    sendAnimIsStart=false;
                }
            }
        });
    }
}
