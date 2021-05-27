package com.mail.myapplication.interfaces

import com.mail.comm.app.AppConfig
import com.mail.comm.net.ApiListener
import com.mail.comm.net.ApiTool
import org.xutils.http.RequestParams
import org.xutils.x

class Home {


    fun a(apiListener: ApiListener) {
        val params = RequestParams(AppConfig.Host_Url)
//        if (Config.isLogin())params.addHeader("Authorization", "Bearer "+ PreferencesUtils.getString(
//            x.app(), "token"))
//        params.addBodyParameter("payment_type",payment_type)
//        params.addBodyParameter("count",count)
//        params.addBodyParameter("index",page.toString())
        ApiTool().postApi(params, apiListener, "home/list")
    }
}