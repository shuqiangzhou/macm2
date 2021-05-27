package com.mail.myapplication.ui.video.jz

import android.os.Bundle
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import cn.jzvd.JZMediaSystem
import cn.jzvd.Jzvd
import cn.jzvd.JzvdStd
import com.mail.comm.video.jz.JZMediaIjk
import com.mail.myapplication.BaseXAty
import com.mail.myapplication.R

class JzvdAty :BaseXAty(){

    var relay_top_bg:RelativeLayout?=null
    var tv_title:TextView?=null

    override fun getLayoutId(): Int = R.layout.aty_jzvd

    override fun initView() {
        relay_top_bg =findViewById(R.id.relay_top_bg)
        tv_title =findViewById(R.id.tv_title)
    }

    override fun requestData() {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initTopview(relay_top_bg)
        tv_title?.text= "Jzvd"
        initVideo();
    }

    fun initVideo(){
        val jzvdStd: MyJzvdStd = findViewById<View>(R.id.jz_video) as MyJzvdStd
        val url = "https://testaksjdfgksadglakjljk.fengtianhaishi.com/video/m3u8/2021/04/21/ca4ac8dd042c0b73bcb42e58e9a3c7c7/20000k/webshow_1619078375784.m3u8?sign=7c6b0eccdc221b8ebe6a2e7c5451f512&t=1622037521"

        Jzvd.setVideoImageDisplayType(Jzvd.VIDEO_IMAGE_DISPLAY_TYPE_FILL_PARENT)
        jzvdStd.setUp(url,"", JzvdStd.SCREEN_NORMAL, JZMediaIjk::class.java)
    }

    fun clickVideoImageDiaplayAdapter(view: View?) {
        Jzvd.setVideoImageDisplayType(Jzvd.VIDEO_IMAGE_DISPLAY_TYPE_ADAPTER)
    }

    fun clickVideoImageDisplayFillParent(view: View?) {
        Jzvd.setVideoImageDisplayType(Jzvd.VIDEO_IMAGE_DISPLAY_TYPE_FILL_PARENT)
    }

    fun clickVideoImageDisplayFillCrop(view: View?) {
        Jzvd.setVideoImageDisplayType(Jzvd.VIDEO_IMAGE_DISPLAY_TYPE_FILL_SCROP)
    }

    fun clickVideoImageDiaplayOriginal(view: View?) {
        Jzvd.setVideoImageDisplayType(Jzvd.VIDEO_IMAGE_DISPLAY_TYPE_ORIGINAL)
    }

    override fun onBackPressed() {
        if (Jzvd.backPress()) {
            return
        }
        super.onBackPressed()
    }

    override fun onPause() {
        super.onPause()
        Jzvd.goOnPlayOnPause()
    }

    override fun onResume() {
        super.onResume()
        Jzvd.goOnPlayOnResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        Jzvd.releaseAllVideos()
    }

    fun mainClick(v:View){
        when(v.id){
            R.id.relay_back->{
                finish()
            }
        }
    }
}