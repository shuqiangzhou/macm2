package com.mail.myapplication.ui.video

import android.os.Bundle
import android.widget.TableLayout
import com.mail.myapplication.BaseXAty
import com.mail.myapplication.R
import tv.danmaku.ijk.media.example.widget.media.AndroidMediaController
import tv.danmaku.ijk.media.example.widget.media.IjkVideoView
import tv.danmaku.ijk.media.player.IjkMediaPlayer

class IjkplayVideoAty:BaseXAty() {

    var  mVideoView: IjkVideoView?=null
    var mMediaController: AndroidMediaController? = null
    var mHudView: TableLayout? = null
    var mBackPressed = false

    var  mVideoUri =""

    override fun getLayoutId(): Int = R.layout.aty_video

    override fun initView() {
        // init player
        IjkMediaPlayer.loadLibrariesOnce(null)
        IjkMediaPlayer.native_profileBegin("libijkplayer.so")
        mVideoView =findViewById(R.id.video_view)
        mHudView =findViewById(R.id.hud_view)
    }

    override fun requestData() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mMediaController = AndroidMediaController(this, false)
        mVideoView!!.setMediaController(mMediaController)
        mVideoView!!.setHudView(mHudView)

        mVideoView?.setVideoPath("https://testaksjdfgksadglakjljk.fengtianhaishi.com/video/m3u8/318939/webshow_1618841099471.m3u8?sign=06d21f2afe0622f89f6817989cb8e00e&t=1621846856")
        mVideoView?.start()
    }

    override fun onBackPressed() {
        mBackPressed = true
        super.onBackPressed()
    }

    override fun onStop() {
        super.onStop()
        if (mBackPressed || !mVideoView!!.isBackgroundPlayEnabled) {
            mVideoView!!.stopPlayback()
            mVideoView!!.release(true)
            mVideoView!!.stopBackgroundPlay()
        } else {
            mVideoView!!.enterBackground()
        }
        IjkMediaPlayer.native_profileEnd()
    }
}