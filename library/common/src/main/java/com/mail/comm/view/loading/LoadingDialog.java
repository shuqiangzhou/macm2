package com.mail.comm.view.loading;

import android.app.Activity;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.mail.comm.R;

public class LoadingDialog {

    private Dialog mLoadingDialog;
    private ImageView imgv_down_loading;
    public Animation animation;

    public Dialog showDialogForLoading(Activity context) {

        if (mLoadingDialog != null) {
            if (!mLoadingDialog.isShowing()) {
                mLoadingDialog.show();
            }
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.loading_dialog, null);
            mLoadingDialog = new Dialog(context, R.style.CustomProgressDialog);
            mLoadingDialog.setCancelable(true);
            mLoadingDialog.setCanceledOnTouchOutside(false);
            mLoadingDialog.setContentView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
            imgv_down_loading = view.findViewById(R.id.down_loading);
             animation = AnimationUtils.loadAnimation(context, R.anim.loading_dialog_rotate);
            animation.setInterpolator(new LinearInterpolator());
            mLoadingDialog.show();
        }
        imgv_down_loading.startAnimation(animation);
        return mLoadingDialog;
    }


    public void cancelDialogForLoading() {
        if (mLoadingDialog != null) {
            mLoadingDialog.cancel();
        }
        if (imgv_down_loading!=null){
            imgv_down_loading.clearAnimation();
        }
    }

    public void destroy() {
        if (imgv_down_loading!=null){
            imgv_down_loading.clearAnimation();
        }
        if (mLoadingDialog != null) {
            mLoadingDialog.cancel();
            mLoadingDialog = null;
        }
    }
}
