package com.mail.comm.utils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.mail.comm.R;
import com.zhy.autolayout.utils.AutoUtils;


/**
 * Created by aa on 2017/5/5.
 */

public class ImagesUtils {

    public static void disImg(Context context, String imgUrl, ImageView imgv) {
        Glide.with(context)
                .load(imgUrl)
                .into(imgv);
    }

    public static void disImg(Context context, String imgUrl, ImageView imgv, int error, int placeholder) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.error(error);
        requestOptions.placeholder(placeholder);

        Glide.with(context)
                .load(imgUrl)
                .apply(requestOptions)
                .into(imgv);
    }

    public static void disImg2(Context context, String imgUrl, ImageView imgv) {
        Glide.with(context)
                .load(imgUrl)
                .into(imgv);
    }

    public static void disImg3(Context context, String imgUrl, ImageView imgv) {
        CornerTransform transformation = new CornerTransform(context, AutoUtils.getPercentWidthSize(20));
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.transform(transformation);
        Glide.with(context)
                .load(imgUrl).apply(requestOptions)
                .into(imgv);
    }


    public static void disImgAndLoad(Context context, String imgUrl, int load, ImageView imgv) {

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(load);
        requestOptions.error(R.drawable.error);

        Glide.with(context)
                .load(imgUrl)
                .apply(requestOptions)
                .into(imgv);
    }


    public static void disImgCircleNo(Context context, String imgUrl, ImageView imgv) {
        if (!TextUtils.isEmpty(imgUrl)) {
            RequestOptions requestOptions = new RequestOptions();
            requestOptions.apply(RequestOptions.bitmapTransform(new CircleCrop()));
//            requestOptions.error(R.drawable.login_logo);
//            requestOptions.placeholder(R.drawable.login_logo);
//            requestOptions.skipMemoryCache(false);
//            requestOptions.transform(new GlideCircleTransform(context));
            Glide.with(context)
                    .load(imgUrl).apply(requestOptions)
                    .into(imgv);

        }
    }

    public static void disImgCircleNo(Context context, int imgUrl, ImageView imgv) {
        RequestOptions requestOptions = new RequestOptions();
//            requestOptions.error(R.drawable.login_logo);
//            requestOptions.placeholder(R.drawable.login_logo);
        requestOptions.transform(new GlideCircleTransform(context));
        Glide.with(context)
                .load(imgUrl).apply(requestOptions)
                .into(imgv);
    }



}
