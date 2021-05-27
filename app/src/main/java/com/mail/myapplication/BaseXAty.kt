package com.mail.myapplication

import android.graphics.Color
import android.widget.ImageView
import android.widget.RelativeLayout
import com.mail.comm.base.BaseAty
import com.zhy.autolayout.utils.AutoUtils

abstract  class BaseXAty :BaseAty() {


    //非沉淀状态栏   背景-资源文件
    fun initTopview(relay: RelativeLayout?) {
        val lp = relay?.layoutParams as RelativeLayout.LayoutParams
        lp.height = AutoUtils.getPercentHeightSize(130)
        relay.layoutParams = lp
        relay.setBackgroundColor(Color.parseColor("#ffffff"))
    }





}