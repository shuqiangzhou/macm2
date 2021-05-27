package com.mail.myapplication.ui

import android.content.Context
import android.graphics.drawable.BitmapDrawable
import android.view.Gravity
import android.view.ViewGroup
import android.widget.PopupWindow
import android.widget.RelativeLayout
import com.mail.comm.app.AppManager
import com.mail.comm.base.AbsViewHolder
import com.mail.comm.base.BaseAty
import com.mail.myapplication.R
import com.zhy.autolayout.utils.AutoUtils
import java.util.*

class PopTopMsgHolder(context: Context, parentView: ViewGroup) :
    AbsViewHolder(context, parentView) {

    var timer: Timer? = null
    var timerTask: TimerTask? = null
    var popTop: PopupWindow? = null


    override fun getLayoutId(): Int = R.layout.pop_top_msg

    override fun init() {}

    fun showTopMmgPop() {
        popTop?.dismiss()
        if (null == popTop) {
            var relay_01 = mContentView?.findViewById(R.id.relay_01) as RelativeLayout

            val params = relay_01?.layoutParams as RelativeLayout.LayoutParams
            params?.height = AutoUtils.getPercentHeightSize(170)
            relay_01?.layoutParams = params

            popTop = PopupWindow(mContentView, ViewGroup.LayoutParams.MATCH_PARENT, AutoUtils.getPercentHeightSize(170))
            popTop?.isOutsideTouchable = false
            popTop?.setBackgroundDrawable(BitmapDrawable())
        }

        popTop?.animationStyle = R.style.pop_animation
        popTop?.showAtLocation((AppManager.getInstance().topActivity as BaseAty).window.decorView, Gravity.TOP, 0, 0)
        startTime()
    }

    override fun onStop() {
        super.onStop()
        popTop?.dismiss()
        cancelTimer()
    }

    private fun startTime() {
        cancelTimer()
        timer = Timer()
        timerTask = object : TimerTask() {
            override fun run() {
                popTop?.let {
                    (mContext as BaseAty).runOnUiThread {
                        run {
                            if (it.isShowing) it.dismiss()
                        }
                    };
                }
            }
        }
        timer?.schedule(timerTask, 2000)
    }

    fun cancelTimer() {
        timer?.cancel()
        timer = null
        timerTask?.cancel()
        timerTask = null
    }
}