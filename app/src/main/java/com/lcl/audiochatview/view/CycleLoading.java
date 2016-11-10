package com.lcl.audiochatview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Message;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.lcl.audiochatview.R;


/**
 * 循环播放图片组自定义View
 */

public class CycleLoading extends BaseLoading{

    private Drawable startBg;
    private Drawable endBg;

    private ImageView switcher;

    private Drawable[] resources=null;

    private boolean isStarted=false;
    private boolean isExist=false;
    private int index=-1;
    private TaskThread thread;

    public CycleLoading(Context context) {
        super(context);
        init(context,null);
    }

    public CycleLoading(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public CycleLoading(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    @Override
    public void start() {
        if(isStarted){
            return;
        }
        isExist=false;
        switcher.setImageDrawable(startBg);
        if(this.resources==null){
            throw new NullPointerException("resource array is null");
        }
        thread=new TaskThread();
        thread.start();
        isStarted=true;
    }

    @Override
    public void stop() {
        isExist=true;
        if(thread!=null){
            thread.interrupt();
            thread=null;
        }
    }

    @Override
    protected void handle(Message msg) {
        if(!isExist){
            switch (msg.what){
                case 1:
                    switcher.setImageDrawable(endBg);
                    break;
                case 0:
                    if(index==this.resources.length-1){
                        index=-1;
                    }
                    index++;
                    switcher.setImageDrawable(resources[index]);
                    break;
            }
        }else{
            switcher.setImageDrawable(endBg);
        }
    }

    private void init(Context context, AttributeSet attrs){
        TypedArray ta=context.obtainStyledAttributes(attrs, R.styleable.CycleLoading);
        startBg=ta.getDrawable(R.styleable.CycleLoading_startBg);
        endBg=ta.getDrawable(R.styleable.CycleLoading_endBg);
        initImageView();
        ta.recycle();
    }

    private void initImageView(){
        switcher=new ImageView(getViewContext());
        switcher.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));
        switcher.setPadding(0,0,0,0);
        switcher.setImageDrawable(startBg);
        addView(switcher);
    }

    public void setStartDrawable(int resId){
        Drawable start=getResources().getDrawable(resId);
        if(start==null){
            throw new RuntimeException("not found resource for this resId");
        }
        this.startBg=start;
        switcher.setImageDrawable(startBg);
    }

    public void setStartDrawable(Drawable drawable){
        if(drawable==null){
            throw new RuntimeException("not found resource for this drawable");
        }
        this.startBg=drawable;
        switcher.setImageDrawable(startBg);
    }

    public Drawable getStartDrawable(){
        return this.startBg;
    }

    public void setEndDrawable(int resId){
        Drawable end=getResources().getDrawable(resId);
        if(end==null){
            throw new RuntimeException("not found resource for this resId");
        }
        this.endBg=end;
    }

    public void setEndDrawable(Drawable drawable){
        if(drawable==null){
            throw new RuntimeException("not found resource for this drawable");
        }
    }

    public Drawable getEndDrawable(){
        return this.endBg;
    }

    public void setDrawable(Drawable[] resources){
        this.resources=resources;
    }

    public Drawable[] getDrawable(){
        return this.resources;
    }

    class TaskThread extends Thread{
        @Override
        public void run() {
            super.run();
            while (!isExist){
                try {
                    Thread.sleep(getDuration());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                handler.sendEmptyMessage(0);
            }
            isStarted=false;
            handler.sendEmptyMessage(1);
        }
    }
}
