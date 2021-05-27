package com.mail.comm.utils;

import android.app.ActivityManager;
import android.content.Context;

import java.util.List;

public class AppProcessUtils {


    public static void killAllOtherProcess(Context context) {
        final ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (am == null) {
            return;
        }
        List<ActivityManager.RunningAppProcessInfo> appProcessList = am
                .getRunningAppProcesses();

        if (appProcessList == null) {
            return;
        }
        // NOTE: getRunningAppProcess() ONLY GIVE YOU THE PROCESS OF YOUR OWN PACKAGE IN ANDROID M
        // BUT THAT'S ENOUGH HERE
        for (ActivityManager.RunningAppProcessInfo ai : appProcessList) {
            // KILL OTHER PROCESS OF MINE
            if (ai.uid == android.os.Process.myUid() && ai.pid != android.os.Process.myPid()) {
                android.os.Process.killProcess(ai.pid);
            }
        }

    }

    public static void killProcessExceptMain(Context context) {
        final ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (am == null) {
            return;
        }
        List<ActivityManager.RunningAppProcessInfo> appProcessList = am.getRunningAppProcesses();
        if (appProcessList != null) {
            // NOTE: getRunningAppProcess() ONLY GIVE YOU THE PROCESS OF YOUR OWN PACKAGE IN ANDROID M
            // BUT THAT'S ENOUGH HERE
            for (ActivityManager.RunningAppProcessInfo ai : appProcessList) {
                if (ai.uid != android.os.Process.myUid()) {
                    continue;
                }
                if (ai.processName.equals(context.getPackageName())) {
                    continue;
                }
                android.os.Process.killProcess(ai.pid);
            }
        }
    }
}