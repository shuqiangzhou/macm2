package com.mail.comm.base

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mail.comm.R
import com.mail.comm.net.ApiListener
import com.mail.comm.utils.ToastUitl
import com.mail.comm.view.loading.LoadingDialog
import org.xutils.common.Callback
import org.xutils.http.RequestParams

abstract class BaseFrg: Fragment(),ApiListener{

    var rootView: View? = null

    var loadingDialog: LoadingDialog? = null

    abstract fun getLayoutId(): Int

    abstract fun initView()

    abstract fun requestData()

    fun refreshData(data: Any?) {}

    fun initRefreshData(data: Any?) {}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        if (rootView == null) {
            rootView = inflater.inflate(getLayoutId(), container, false)
            rootView!!.isClickable = true
        }
        initView()
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        loadingDialog?.destroy()
        requestData()
    }

    fun startActivity(cls: Class<*>) { startActivity(cls, null) }

    fun startActivity(cls: Class<*>, bundle: Bundle?) {
        val intent = Intent(activity, cls)
        bundle?.let { intent.putExtras(it) }
        startActivity(intent)
        if(activity is BaseAty){
            activity?.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
    }

    fun startActivityForResult(cls: Class<*>, requestCode: Int) {
        startActivityForResult(cls, null, requestCode)
    }

    private fun startActivityForResult(cls: Class<*>, bundle: Bundle?, requestCode: Int) {
        val intent = Intent(activity, cls)
        bundle?.let { intent.putExtras(it) }
        startActivityForResult(intent, requestCode)
        if(activity is BaseAty){
            activity?.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
    }

    fun showToastS(text: String?) = ToastUitl.showShort(activity, text)

    fun showToastL(text: String?) = ToastUitl.showLong(activity, text)

    fun startProgressDialog() {
        if (loadingDialog == null) loadingDialog = LoadingDialog()
        loadingDialog?.showDialogForLoading(activity)
    }

    fun stopProgressDialog() = loadingDialog?.cancelDialogForLoading()

    override fun onDestroy() {
        super.onDestroy()
        loadingDialog?.destroy()
    }


    override fun onCancelled(var1: Callback.CancelledException?) {
    }

    override fun onComplete(var1: RequestParams?, var2: String?, type: String?) {
    }
    override fun onError(var1: Map<String?, String?>?, var2: RequestParams?) {
    }

    override fun onExceptionType(var1: Throwable?, params: RequestParams?, type: String?) {
    }
}