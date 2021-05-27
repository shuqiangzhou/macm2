package com.mail.myapplication.ui.home;

import android.app.Activity;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerAdapter;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.util.LogUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UtilsX implements LifecycleOwner {


    public static void  setBannerAdapter(Activity activity, Banner banner, ArrayList<HashMap<String,String>>list,LifecycleOwner lifecycleOwner){
        //自定义的图片适配器，也可以使用默认的BannerImageAdapter
         ImageNetAdapter adapter = new ImageNetAdapter(list);
         banner.setAdapter(adapter)
                .addBannerLifecycleObserver(lifecycleOwner)//添加生命周期观察者
                .setIndicator(new CircleIndicator(activity))//设置指示器
                .setOnBannerListener((data, position) -> {
//                    Snackbar.make(banner, ((DataBean) data).title, Snackbar.LENGTH_SHORT).show();
                    LogUtils.d("position：" + position);
                });
    }

    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return null;
    }
}
