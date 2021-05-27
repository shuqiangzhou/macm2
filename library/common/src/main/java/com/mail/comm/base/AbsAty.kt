package com.mail.comm.base

import android.content.Context
import android.os.Bundle
import com.mail.comm.interfaces.LifeCycleListener
import com.zhy.autolayout.AutoLayoutActivity
import java.util.*
import kotlin.collections.ArrayList

class AbsAty : AutoLayoutActivity() {

    var mContext: Context? = null

    var mLifeCycleListeners: ArrayList<LifeCycleListener>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this
        mLifeCycleListeners = ArrayList()
        for (listener in mLifeCycleListeners!!) {
            listener.onCreate()
        }
    }

    override fun onDestroy() {
        for (listener in mLifeCycleListeners!!) {
            listener.onDestroy()
        }
        mLifeCycleListeners?.clear()
        mLifeCycleListeners = null
        super.onDestroy()
    }

    override fun onStart() {
        super.onStart()
        if (mLifeCycleListeners != null) {
            for (listener in mLifeCycleListeners!!) {
                listener.onStart()
            }
        }
    }

    override fun onRestart() {
        super.onRestart()
        if (mLifeCycleListeners != null) {
            for (listener in mLifeCycleListeners!!) {
                listener.onReStart()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (mLifeCycleListeners != null) {
            for (listener in mLifeCycleListeners!!) {
                listener.onResume()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        if (mLifeCycleListeners != null) {
            for (listener in mLifeCycleListeners!!) {
                listener.onPause()
            }
        }
    }

    override fun onStop() {
        super.onStop()
        if (mLifeCycleListeners != null) {
            for (listener in mLifeCycleListeners!!) {
                listener.onStop()
            }
        }
    }

    fun addLifeCycleListener(listener: LifeCycleListener?) {
        if (mLifeCycleListeners != null && listener != null) {
            mLifeCycleListeners!!.add(listener)
        }
    }

    fun addAllLifeCycleListener(listeners: ArrayList<LifeCycleListener>?) {
        if (mLifeCycleListeners != null && listeners != null) {
            mLifeCycleListeners!!.addAll(listeners)
        }
    }

    fun removeLifeCycleListener(listener: LifeCycleListener?) {
        if (mLifeCycleListeners != null) {
            mLifeCycleListeners!!.remove(listener!!)
        }
    }


}


