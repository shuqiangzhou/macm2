package com.mail.comm.net

import org.xutils.common.Callback.CancelledException
import org.xutils.http.RequestParams

interface ApiListener {
    fun onCancelled(var1: CancelledException?)
    fun onComplete(var1: RequestParams?, var2: String?, type: String?)
    fun onError(var1: Map<String?, String?>?, var2: RequestParams?)
    fun onExceptionType(var1: Throwable?, params: RequestParams?, type: String?)
}