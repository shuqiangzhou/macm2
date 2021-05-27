package com.mail.comm.utils;

import android.content.Context;
import android.widget.Toast;



public class ToastUitl {


    private static Toast toast;

    private static Toast initToast(Context context, CharSequence message, int duration) {
        if (toast == null) {
            toast = Toast.makeText(context, message, duration);
        } else {
            toast.setText(message);
            toast.setDuration(duration);
        }
        return toast;
    }

    public static void showShort(Context context, CharSequence str) {
        initToast(context,str, Toast.LENGTH_SHORT).show();
    }


    public static void showLong(Context context, CharSequence message) {
        initToast(context,message, Toast.LENGTH_LONG).show();
    }


}
