package com.mail.comm.view.loading

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import com.mail.comm.R
import com.mail.comm.utils.ToastUitl

class LoadingTipX : LinearLayout, View.OnClickListener {

    var loadListen: LoadingTipXReloadCallback? = null

    var progress: ImageView? = null
    var linlay_empty: LinearLayout? = null
    var linlay_error: LinearLayout? = null

    interface LoadingTipXReloadCallback {
        fun reload()
    }

    enum class LoadStatus {
        error, empty, loading, finish,
    }

    constructor(context: Context?) : super(context) {
        initView(context)
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        initView(context)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initView(context)
    }

    fun setLoadingTipXReloadCallback(loadListen: LoadingTipXReloadCallback) {
        this.loadListen = loadListen
    }


    fun initView(context: Context?) {
        inflate(context, R.layout.loading_tip, this)
        setOnClickListener { }
        progress = findViewById<View>(R.id.progress) as ImageView
        linlay_empty = findViewById<View>(R.id.linlay_empty) as LinearLayout
        linlay_error = findViewById<View>(R.id.linlay_empty) as LinearLayout
        visibility = GONE
        setListen()
    }

    fun  setListen(){
        linlay_empty?.setOnClickListener(this)
        linlay_error?.setOnClickListener(this)
    }

    fun statLodingAnim() {
        progress?.clearAnimation()
        var animation = AnimationUtils.loadAnimation(context, R.anim.loading_dialog_rotate)
        animation?.setInterpolator(LinearInterpolator())
        progress?.startAnimation(animation)
    }

    fun stopLodingAnim() {
        progress?.clearAnimation()
    }

    fun setLoadingTip(loadStatus: LoadStatus) {

        when (loadStatus) {

            LoadStatus.loading -> {
                setVisibility(VISIBLE)
                progress?.visibility = VISIBLE
                linlay_empty?.visibility = GONE
                linlay_error?.visibility = GONE
                statLodingAnim()
            }
            LoadStatus.finish -> {
                setVisibility(GONE)
                stopLodingAnim()
            }
            LoadStatus.empty -> {
                setVisibility(VISIBLE)
                progress?.visibility = GONE
                linlay_empty?.visibility = VISIBLE
                linlay_error?.visibility = GONE
                stopLodingAnim()

            }
            LoadStatus.error -> {
                setVisibility(VISIBLE)
                progress?.visibility = GONE
                linlay_empty?.visibility = GONE
                linlay_error?.visibility = VISIBLE
                stopLodingAnim()

            }
        }
    }


    override fun onClick(v: View) {
        when (v.id) {
            R.id.linlay_empty,
            R.id.linlay_error -> {
                ToastUitl.showShort(org.xutils.x.app(),"ssss")
                loadListen?.reload()
            }
        }
    }


}