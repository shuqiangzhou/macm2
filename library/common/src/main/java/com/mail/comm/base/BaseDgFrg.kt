package com.mail.comm.base

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import androidx.fragment.app.DialogFragment
import java.lang.ref.WeakReference

abstract class BaseDgFrg : DialogFragment() {

    var mContext: BaseAty? = null

    var mRootView: View? = null

    abstract fun getLayoutId(): Int

    abstract fun getDialogStyle(): Int

    abstract fun canCancel(): Boolean

    abstract fun setWindowAttributes(window: Window?)

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        mContext = WeakReference(getActivity()).get() as BaseAty
        mRootView = LayoutInflater.from(mContext).inflate(getLayoutId(), null);
        var dialog = Dialog(mContext!!, getDialogStyle());
        dialog.setContentView(mRootView!!);
        dialog.setCancelable(canCancel());
        dialog.setCanceledOnTouchOutside(canCancel());
        setWindowAttributes(dialog.window);
        return dialog;
    }

    fun <T : View> findViewById(id: Int): T? {
        return mRootView?.findViewById(id)
    }


}