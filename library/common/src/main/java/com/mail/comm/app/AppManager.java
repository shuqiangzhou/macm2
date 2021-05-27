package com.mail.comm.app;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

import com.mail.comm.utils.AppProcessUtils;

import java.util.Iterator;
import java.util.Stack;


public class AppManager {

    private static Stack<Activity> mActivityStack;
    private static AppManager mAppManager;

    private AppManager() {
    }

    public static AppManager getInstance() {
        if (mAppManager == null) {
            mAppManager = new AppManager();
        }
        return mAppManager;
    }

    public void addActivity(Activity activity) {
        if (mActivityStack == null) {
            mActivityStack = new Stack();
        }
        mActivityStack.add(activity);
    }

    public Activity getTopActivity() {
        Activity activity = (Activity) mActivityStack.lastElement();
        return activity;
    }

    public void killTopActivity() {
        Activity activity = (Activity) mActivityStack.lastElement();
        this.killActivity(activity);
    }

    public void killActivity(Activity activity) {
        if (activity != null) {
            mActivityStack.remove(activity);
            activity.finish();
        }

    }
    public void removeActivity(Activity activity) {
        if (activity != null) {
            mActivityStack.remove(activity);
        }
    }

    public void killtoActivity(Class<?> cls) {
        Iterator iterator = mActivityStack.iterator();
        while (iterator.hasNext()) {
            Activity activity = (Activity) iterator.next();
            if (!activity.getClass().equals(cls)) {
                iterator.remove();
                activity.finish();
            }
        }
    }


    public Activity selectActivity(Class<?> cls) {

        Activity mActivity = null;
        Iterator iterator = mActivityStack.iterator();

        while (iterator.hasNext()) {
            Activity activity = (Activity) iterator.next();
            if (activity.getClass().equals(cls)) {
                mActivity = activity;
            }
        }
        return mActivity;
    }


    public void killActivity(Class<?> cls) {
        Iterator iterator = mActivityStack.iterator();

        while (iterator.hasNext()) {
            Activity activity = (Activity) iterator.next();
            if (activity.getClass().equals(cls)) {
                iterator.remove();
                activity.finish();
            }
        }

    }

    public void killAllActivity() {
        int i = 0;
        for (int size = mActivityStack.size(); i < size; ++i) {
            if (null != mActivityStack.get(i)) {
                ((Activity) mActivityStack.get(i)).finish();
            }
        }
        mActivityStack.clear();
    }

    public void AppExit(Context context) {
        try {
            this.killAllActivity();
            AppProcessUtils.killAllOtherProcess(context);
        } catch (Exception var3) {
        }

    }
}
