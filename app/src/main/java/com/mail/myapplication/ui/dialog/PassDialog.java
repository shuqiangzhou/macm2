package com.mail.myapplication.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;

import com.mail.comm.base.BaseAty;
import com.mail.comm.view.pass.PwdEditText;
import com.mail.myapplication.R;


public class PassDialog extends Dialog implements PwdEditText.OnTextChangeListeven {

    BaseAty context;
    onScuesssListen listen;
    InputMethodManager mInputManager;//软键盘管理类

    PwdEditText edit_code;

    public PassDialog(@NonNull Context context) {
        super(context);
        this.context = (BaseAty) context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_pass);

        edit_code = findViewById(R.id.edit_code);
        findViewById(R.id.imgv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clear();
                dismiss();
            }
        });
        mInputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        Window dialogWindow = getWindow();
        dialogWindow.setWindowAnimations(R.style.bottomToTopAnim);
        dialogWindow.setBackgroundDrawable(null);
        dialogWindow.setGravity(Gravity.BOTTOM);
        setCanceledOnTouchOutside(true);
        WindowManager m = context.getWindowManager();
        Display d = m.getDefaultDisplay();// 获取屏幕宽、高
        WindowManager.LayoutParams p = dialogWindow.getAttributes();
//        p.height = (int) (d.getHeight() * 1); // 高度设置为屏幕的0.6
        p.width = (int) (d.getWidth() * 1); // 宽度设置为屏幕的0.85
        dialogWindow.setAttributes(p);

        edit_code.setOnTextChangeListeven(this);


    }

    @Override
    public void onTextChange(String pwd) {
        if (!TextUtils.isEmpty(pwd) && pwd.length() == 6) {
            if (listen != null) {
                listen.reutrnOK(pwd);
            }
        }
    }

    public interface onScuesssListen {
        void reutrnOK(String pwd);
    }

    public void setOnScuesssListen(onScuesssListen listen) {
        this.listen = listen;
    }


    public void showSoftInput() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (edit_code != null) {
                    edit_code.requestFocus();
                }
                if (mInputManager != null) {
                    mInputManager.showSoftInput(edit_code, InputMethodManager.SHOW_FORCED);
                }
            }
        }, 200);

    }

    private void hideSoftInput() {
        if (mInputManager != null) {
            mInputManager.hideSoftInputFromWindow(edit_code.getWindowToken(), 0);
        }
    }


    public void clear() {
        edit_code.clearText();
        hideSoftInput();
    }

}
