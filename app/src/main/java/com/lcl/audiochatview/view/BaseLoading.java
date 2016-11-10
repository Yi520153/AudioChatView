package com.lcl.audiochatview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.lcl.audiochatview.R;


/**
 * 基础方法、及动画时长控制类
 */

public abstract class BaseLoading extends LinearLayout{

    private static final int DEFAULT_DURATION_TIME=300;
    private static final int MAX_DURATION_TIME=1000;

    private int duration;

    private Context context;

    public BaseLoading(Context context) {
        super(context);
        init(context,null);
    }

    public BaseLoading(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public BaseLoading(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    private void init(Context context, AttributeSet attrs){
        this.context=context;
        TypedArray ta=context.obtainStyledAttributes(attrs, R.styleable.BasicLoading);
        int duration = ta.getInt(R.styleable.BasicLoading_duration, DEFAULT_DURATION_TIME);
        setDuration(duration);
        ta.recycle();
    }

    private void setDuration(int duration){
        this.duration=duration;
        if(this.duration<0||this.duration>MAX_DURATION_TIME){
            this.duration=DEFAULT_DURATION_TIME;
        }
    }

    public int getDuration(){
        return this.duration;
    }

    public Context getViewContext(){
        return this.context;
    }

    protected abstract void start();

    protected abstract void stop();

    protected abstract void handle(Message msg);

    protected Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            handle(msg);
        }
    };
}
