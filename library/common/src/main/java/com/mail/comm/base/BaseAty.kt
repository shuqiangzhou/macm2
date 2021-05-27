package com.mail.comm.base

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.WindowManager
import com.mail.comm.R
import com.mail.comm.app.AppManager
import com.mail.comm.net.ApiListener
import com.mail.comm.utils.ToastUitl
import com.mail.comm.view.loading.LoadingDialog
import com.zhy.autolayout.AutoLayoutActivity
import java.util.ArrayList
import org.xutils.common.Callback
import org.xutils.http.RequestParams

abstract class BaseAty : AutoLayoutActivity(), ApiListener {

    var fragments: ArrayList<BaseFrg>? = null

    var currentFragment: BaseFrg? = null

    var isTwoBack: Boolean = false

    var firstTime: Long = 0

    var loadingDialog: LoadingDialog? = null

    abstract fun getLayoutId(): Int

    abstract fun initView()

    abstract fun requestData()

    open fun getFragmentContainerId(): Int = 0

    open fun getFragmentContainerId2(): Int = 0

//    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        AppManager.getInstance().addActivity(this)
        setStatusColor()
        initView()
        requestData()
        loadingDialog?.destroy()
    }

    private fun setStatusColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.parseColor("#000000")
            return
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.parseColor("#000000")
        }
    }

    fun setTranslanteBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val window = window
                val decorView = window.decorView
                val option =
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                decorView.systemUiVisibility = option
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                window.statusBarColor = Color.TRANSPARENT
            } else {
                val window = window
                val attributes = window.attributes
                val flagTranslucentStatus = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                attributes.flags = attributes.flags or flagTranslucentStatus
                window.attributes = attributes
            }
        }
    }


    fun startActivity(cls: Class<*>) {
        startActivity(cls, null)
    }

    fun startActivity(cls: Class<*>, bundle: Bundle?) {
        val intent = Intent(this, cls)
        bundle?.let { intent.putExtras(it) }
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }

    fun startActivityForResult(cls: Class<*>, requestCode: Int) {
        startActivityForResult(
            cls,
            null,
            requestCode
        )
    }

    private fun startActivityForResult(cls: Class<*>, bundle: Bundle?, requestCode: Int) {
        val intent = Intent(this, cls)
        bundle?.let { intent.putExtras(it) }
        startActivityForResult(intent, requestCode)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }

    fun showToastS(text: String?) = ToastUitl.showShort(this, text)

    fun showToastL(text: String?) = ToastUitl.showLong(this, text)

    fun startProgressDialog() {
        if (loadingDialog == null) loadingDialog = LoadingDialog()
        loadingDialog?.showDialogForLoading(this)
    }

    fun stopProgressDialog() = loadingDialog?.cancelDialogForLoading()

    fun setBackTwo(isTwoBack: Boolean) {
        this.isTwoBack = isTwoBack
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

    override fun onDestroy() {
        super.onDestroy()
        AppManager.getInstance().removeActivity(this)
        loadingDialog?.destroy()
    }

    override fun onBackPressed() {
        if (isTwoBack) {
            if (System.currentTimeMillis() - firstTime < 3000) {
                AppManager.getInstance().AppExit(this)
            } else {
                firstTime = System.currentTimeMillis()
                showToastS("再按一次返回桌面")
            }
        } else super.onBackPressed()
    }

    fun addFragment(cls: Class<*>, data: Any?) {
        val param = FragmentParam()
        param.cls = cls
        param.data = data
        param.addToBackStack = false
        processFragement(param)
    }

    private fun getFragmentTag(param: FragmentParam): String =
        StringBuilder(param.cls.toString() + param.tag).toString()

    private fun addDistinctEntry(sourceList: ArrayList<BaseFrg>?, entry: BaseFrg): Boolean {
        return sourceList != null && !sourceList.contains(entry) && sourceList.add(entry)
    }

    private fun processFragement(param: FragmentParam) {
        val containerId: Int =
            if (!TextUtils.isEmpty(param.Id)) getFragmentContainerId2() else getFragmentContainerId()
        val cls = param.cls
        if (cls == null) return
        try {
            val e = getFragmentTag(param)
            var f1 = supportFragmentManager.findFragmentByTag(e)
            var fragment = f1?.let { it as BaseFrg } ?: cls.newInstance() as BaseFrg
            if (this.fragments == null) this.fragments = ArrayList()
            addDistinctEntry(this.fragments, fragment)
            val ft = supportFragmentManager.beginTransaction()

            if (param.type != FragmentParam.TYPE.ADD)
                ft.replace(containerId, fragment, e)
            else if (!fragment.isAdded) {
                if (this.currentFragment != null) this.currentFragment!!.onPause()
                ft.add(containerId, fragment, e)
                if (param.data != null) fragment.initRefreshData(param.data)
            } else {
                val var7 = this.fragments!!.iterator()
                while (var7.hasNext()) ft.hide(var7.next())
                if (this.currentFragment != null) this.currentFragment!!.onPause()
                ft.show(fragment)
                fragment.onResume()
                if (param.data != null) fragment.refreshData(param.data)
            }
            this.currentFragment = fragment
            if (param.addToBackStack) ft.addToBackStack(e)
            ft.commitAllowingStateLoss()
        } catch (var9: InstantiationException) {
            var9.printStackTrace()
        } catch (var10: IllegalAccessException) {
            var10.printStackTrace()
        }
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