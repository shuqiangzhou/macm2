package com.mail.myapplication.ui.video

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.mail.myapplication.BaseXAty
import com.mail.myapplication.R
import com.shuyu.gsyvideoplayer.GSYVideoManager
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack
import com.shuyu.gsyvideoplayer.utils.OrientationUtils
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer

class GsyVideoAty : BaseXAty() {


    var relay_top_bg: RelativeLayout? = null
    var tv_title: TextView? = null

    var detailPlayer: StandardGSYVideoPlayer? = null

    var orientationUtils: OrientationUtils? = null

    var url ="https://testaksjdfgksadglakjljk.fengtianhaishi.com/video/m3u8/318939/webshow_1618841099471.m3u8?sign=06d21f2afe0622f89f6817989cb8e00e&t=1621846856"

     var isPlay = false
     var isPause = false

    override fun getLayoutId(): Int = R.layout.aty_gsy_video

    override fun initView() {
        relay_top_bg = findViewById(R.id.relay_top_bg)
        tv_title = findViewById(R.id.tv_title)
        detailPlayer = findViewById(R.id.detail_player)
    }

    override fun requestData() {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initTopview(relay_top_bg)
        tv_title?.text = "GsyVideo"

        initVideo();
    }

    fun initVideo() {

        //增加封面
        val imageView = ImageView(this)
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        imageView.setImageResource(R.mipmap.ic_launcher)

        detailPlayer?.titleTextView?.visibility = View.GONE
        detailPlayer?.backButton?.visibility = View.GONE


        //外部辅助的旋转，帮助全屏
        orientationUtils = OrientationUtils(this, detailPlayer)
        //初始化不打开外部的旋转
        //初始化不打开外部的旋转
        orientationUtils!!.isEnable = false

        val gsyVideoOption = GSYVideoOptionBuilder()
        gsyVideoOption.setThumbImageView(imageView)
            .setIsTouchWiget(true)
            .setRotateViewAuto(false)
            .setLockLand(false)
            .setAutoFullWithSize(false)
            .setShowFullAnimation(false)
            .setNeedLockFull(true)
            .setUrl(url)
            .setCacheWithPlay(false)
            .setVideoTitle("测试视频")
            .setVideoAllCallBack(object : GSYSampleCallBack() {
                override fun onPrepared(url: String, vararg objects: Any) {
                    super.onPrepared(url, *objects)
                    //开始播放了才能旋转和全屏
                    orientationUtils!!.isEnable = detailPlayer!!.isRotateWithSystem
                    isPlay = true
                }

                override fun onQuitFullscreen(url: String, vararg objects: Any) {
                    super.onQuitFullscreen(url, *objects)
                    if (orientationUtils != null) {
                        orientationUtils!!.backToProtVideo()
                    }
                }
            }).setLockClickListener { view, lock ->
                if (orientationUtils != null) {
                    //配合下方的onConfigurationChanged
                    orientationUtils!!.isEnable = !lock
                }
            }.build(detailPlayer)

        detailPlayer!!.fullscreenButton.setOnClickListener { //直接横屏
            orientationUtils!!.resolveByClick()
            //第一个true是否需要隐藏actionbar，第二个true是否需要隐藏statusbar
            detailPlayer!!.startWindowFullscreen(this@GsyVideoAty, true, true)
        }
    }

    fun mainClick(v: View) {
        when (v.id) {
            R.id.relay_back -> {
                finish()
            }
        }
    }

    override fun onBackPressed() {
        if (orientationUtils != null) {
            orientationUtils!!.backToProtVideo()
        }
        if (GSYVideoManager.backFromWindowFull(this)) {
            return
        }
        super.onBackPressed()
    }


    override fun onPause() {
        detailPlayer!!.currentPlayer.onVideoPause()
        super.onPause()
        isPause = true
    }

    override fun onResume() {
        detailPlayer!!.currentPlayer.onVideoResume(false)
        super.onResume()
        isPause = false
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isPlay) {
            detailPlayer!!.currentPlayer.release()
        }
        if (orientationUtils != null) orientationUtils!!.releaseListener()
    }


    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        //如果旋转了就全屏
        if (isPlay && !isPause) {
            detailPlayer!!.onConfigurationChanged(this, newConfig, orientationUtils, true, true)
        }
    }
}