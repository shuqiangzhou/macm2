package com.mail.comm.utils.a;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.RequiresApi;

import com.mail.comm.base.BaseAty;
import com.zhy.autolayout.utils.AutoUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class MyUtils2 {


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static Bitmap rsBlur(Context context, Bitmap source, int radius) {

        Bitmap inputBmp = source;
        //(1)
        RenderScript renderScript = RenderScript.create(context);

//        Log.i(TAG,"scale size:"+inputBmp.getWidth()+"*"+inputBmp.getHeight());

        // Allocate memory for Renderscript to work with
        //(2)
        final Allocation input = Allocation.createFromBitmap(renderScript, inputBmp);
        final Allocation output = Allocation.createTyped(renderScript, input.getType());
        //(3)
        // Load up an instance of the specific script that we want to use.
        ScriptIntrinsicBlur scriptIntrinsicBlur = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript));
        //(4)
        scriptIntrinsicBlur.setInput(input);
        //(5)
        // Set the blur radius
        scriptIntrinsicBlur.setRadius(radius);
        //(6)
        // Start the ScriptIntrinisicBlur
        scriptIntrinsicBlur.forEach(output);
        //(7)
        // Copy the output to the blurred bitmap
        output.copyTo(inputBmp);
        //(8)
        renderScript.destroy();

        return inputBmp;
    }

    public static Bitmap zoomImg(Bitmap bm, int newWidth, int newHeight) {
        int w = bm.getWidth(); // ????????????????????????
        int h = bm.getHeight();
        int retX;
        int retY;
        double wh = (double) w / (double) h;
        double nwh = (double) newWidth / (double) newHeight;
        if (wh > nwh) {
            retX = h * newWidth / newHeight;
            retY = h;
        } else {
            retX = w;
            retY = w * newHeight / newWidth;
        }
        int startX = w > retX ? (w - retX) / 2 : 0;//????????????????????????????????????x??????
        int startY = h > retY ? (h - retY) / 2 : 0;
        Bitmap bit = Bitmap.createBitmap(bm, startX, startY, retX, retY, null, false);
//        bm.recycle();
        return bit;
    }

    public static Bitmap getNewBitmap(Bitmap bitmap, int newWidth, int newHeight) {
        // ?????????????????????.
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        // ??????????????????.
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // ?????????????????????matrix??????.
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // ??????????????????.
        Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
        return newBitmap;
    }


    /**
     * ????????????
     *
     * @param activity
     * @param themeResId
     * @param tv
     * @param calendar
     */
    public static void showTimePickerDialog(Activity activity, int themeResId, final TextView tv, Calendar calendar) {
        // Calendar c = Calendar.getInstance();
        // ????????????TimePickerDialog??????????????????????????????
        // ???????????????Activity???context?????????
        new TimePickerDialog(activity, themeResId,
                // ???????????????
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        tv.setText(" " + hourOfDay + ":" + minute);
                    }
                }
                // ??????????????????
                , calendar.get(Calendar.HOUR_OF_DAY)
                , calendar.get(Calendar.MINUTE)
                // true????????????24?????????
                , true).show();
    }


    /**
     * ????????????
     *
     * @param activity
     * @param themeResId
     * @param tv
     * @param calendar
     */
    public static void showDatePickerDialog(Activity activity, int themeResId, final TextView tv, Calendar calendar) {
        // ??????????????????DatePickerDialog???????????????????????????????????????
        new DatePickerDialog(activity, themeResId, new DatePickerDialog.OnDateSetListener() {
            // ???????????????(How the parent is notified that the date is set.)
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // ????????????????????????????????????????????????????????????
                tv.setText(year + "???" + (monthOfYear + 1) + "???" + dayOfMonth + "???");
            }
        }
                // ??????????????????
                , calendar.get(Calendar.YEAR)
                , calendar.get(Calendar.MONTH)
                , calendar.get(Calendar.DAY_OF_MONTH)).show();
    }


    public static void saveImg(Bitmap bitmap, BaseAty baseAty) {

        Bitmap newBitmap = Bitmap.createScaledBitmap(bitmap, AutoUtils.getPercentWidthSize(600), AutoUtils.getPercentWidthSize(600), true);
        File appDir = new File(Environment.getExternalStorageDirectory(), "Pictures");
        if (!appDir.exists()) {
            appDir.mkdirs();
        }
        String fileName = "code" + ".png";
//        android.util.Log.i("maptrix", "@#$%^&*------------------------bindphone message----------------------------" + fileName + "---" + appDir.getAbsolutePath());
        File file = new File(appDir, fileName);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(file);
            newBitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            newBitmap.recycle();
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }

        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri = Uri.fromFile(file);
        intent.setData(uri);
        baseAty.sendBroadcast(intent);
        baseAty.showToastL("????????????");

    }

}
