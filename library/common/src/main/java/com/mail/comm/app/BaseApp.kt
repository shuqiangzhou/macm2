package com.mail.comm.app

import android.app.Application
//import cn.jpush.android.api.JPushInterface
import com.zhy.autolayout.config.AutoLayoutConifg
import org.xutils.x

class BaseApp:Application() {


    override fun onCreate() {
        super.onCreate()
        AutoLayoutConifg.getInstance().useDeviceSize()
        x.Ext.init(this)
        x.Ext.setDebug(AppConfig.isDebuger)
//
//        JPushInterface.setDebugMode(true);
//        JPushInterface.init(this);
    }

}