package com.mail.comm.base;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.mail.comm.app.AppManager;
import com.mail.comm.interfaces.LifeCycleListener;

import org.xutils.common.util.LogUtil;


public abstract class AbsViewHolder implements LifeCycleListener {

    private String mTag;
    protected Context mContext;
    protected ViewGroup mParentView;
    protected View mContentView;

    public AbsViewHolder(Context context, ViewGroup parentView) {
        mTag = getClass().getSimpleName();
        mContext = context;
        mParentView = parentView;
        mContentView = LayoutInflater.from(context).inflate(getLayoutId(), mParentView, false);
        init();
    }

    protected abstract int getLayoutId();

    public abstract void init();

    public  <T extends View> T findViewById(int res) {
        return mContentView.findViewById(res);
    }

    public View getContentView() {
        return mContentView;
    }

    public void addToParent() {
        if (mParentView != null && mContentView != null) {
            mParentView.addView(mContentView);
        }
    }

    public void removeFromParent() {
        ViewParent parent = mContentView.getParent();
        if (parent != null) {
            ((ViewGroup) parent).removeView(mContentView);
        }
    }

    /**
     * 订阅Activity的生命周期
     */
    public void subscribeActivityLifeCycle() {
        if(mContext instanceof AbsAty){
            ((AbsAty)mContext).addLifeCycleListener(this);
        }
    }

    /**
     * 取消订阅Activity的生命周期
     */
    public void unSubscribeActivityLifeCycle() {
        if(mContext instanceof AbsAty){
            ((AbsAty)mContext).removeLifeCycleListener(this);
        }
    }

    public void finishAcitivty(){
        if(mContext !=null&&mContext instanceof Activity){
            AppManager.getInstance().killActivity((Activity) mContext);
        }
    }


    @Override
    public void onCreate() {
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onReStart() {
    }

    @Override
    public void onResume() {
    }

    @Override
    public void onPause() {
    }

    @Override
    public void onStop() {
    }

    @Override
    public void onDestroy() {
    }



}
