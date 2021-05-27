package com.mail.myapplication.ui.video.jz.tiktok;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.mail.comm.video.jz.JZMediaAliyun;
import com.mail.comm.video.jz.JZMediaExo;
import com.mail.comm.video.jz.JZMediaIjk;
import com.mail.myapplication.R;
import com.mail.myapplication.config.MyAppConfig;


import cn.jzvd.JZDataSource;
import cn.jzvd.JZMediaSystem;
import cn.jzvd.Jzvd;

public class TikTokRecyclerViewAdapter extends RecyclerView.Adapter<TikTokRecyclerViewAdapter.MyViewHolder> {

    public static final String TAG = "AdapterTikTokRecyclerView";
    int[] videoIndexs = {0, 1, 2, 3, 4, 5};
    private Context context;


    public TikTokRecyclerViewAdapter(Context context) {
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                context).inflate(R.layout.item_video_tiktok, parent,
                false));
        return holder;
    }

    @SuppressLint("LongLogTag")
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Log.i(TAG, "onBindViewHolder [" + holder.jzvdStd.hashCode() + "] position=" + position);
      String url ="https://testaksjdfgksadglakjljk.fengtianhaishi.com/video/m3u8/2021/04/21/ca4ac8dd042c0b73bcb42e58e9a3c7c7/20000k/webshow_1619078375784.m3u8?sign=7c6b0eccdc221b8ebe6a2e7c5451f512&t=1622037521";
        JZDataSource jzDataSource = new JZDataSource(url, "title");
        jzDataSource.looping = true;
//        JZMediaAliyun
        holder.jzvdStd.setUp(jzDataSource, Jzvd.SCREEN_NORMAL, JZMediaIjk.class);
//        jzvdStd.setUp(url,"", JzvdStd.SCREEN_NORMAL, JZMediaAliyun::class.java)
//        Jzvd.setVideoImageDisplayType(Jzvd.VIDEO_IMAGE_DISPLAY_TYPE_ORIGINAL);

//        Glide.with(holder.jzvdStd.getContext()).load(UrlsKt.getPl3()[position]).into(holder.jzvdStd.posterImageView);
    }

    @Override
    public int getItemCount() {
        return videoIndexs.length;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        JzvdStdTikTok jzvdStd;

        public MyViewHolder(View itemView) {
            super(itemView);
            jzvdStd = itemView.findViewById(R.id.videoplayer);
        }
    }

}
