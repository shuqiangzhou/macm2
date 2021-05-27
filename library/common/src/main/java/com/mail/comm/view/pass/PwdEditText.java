package com.mail.comm.view.pass;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.InputType;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatEditText;

import com.zhy.autolayout.utils.AutoUtils;

import java.util.ArrayList;
import java.util.List;

public class PwdEditText extends AppCompatEditText {

    private Paint sidePaint, backPaint, textPaint;
    private String mText = "";
    private int textLength = 6;
    private int Wide = AutoUtils.getPercentWidthSize(150);
    private int spzceY = AutoUtils.getPercentWidthSize(135);
    private List<RectF> rectFS;
    private int spzceX = AutoUtils.getPercentWidthSize(0);

    public PwdEditText(Context context) {
        super(context);
        init();
    }

    public PwdEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PwdEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        if (mText == null) {
            return;
        }
        //如果字数不超过用户设置的总字数，就赋值给成员变量mText；
        // 如果字数大于用户设置的总字数，就只保留用户设置的那几位数字，并把光标制动到最后，让用户可以删除；
        if (text.toString().length() <= textLength) {
            mText = text.toString();
        } else {
            setText(mText);
            setSelection(getText().toString().length());  //光标制动到最后
            //setText(mText)之后键盘会还原，再次把键盘设置为数字键盘；
            setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_NORMAL);
        }
       if (onTextChangeListeven != null) onTextChangeListeven.onTextChange(mText);
    }

    /**
     * 输入监听
     */
   public interface OnTextChangeListeven{
        void onTextChange(String pwd);
    }
    private OnTextChangeListeven onTextChangeListeven;

    public void setOnTextChangeListeven(OnTextChangeListeven onTextChangeListeven){
        this.onTextChangeListeven = onTextChangeListeven;
    }

    private void init() {
        setTextColor(0X00000000);
        sidePaint = new Paint();
        backPaint = new Paint();
        textPaint = new Paint();
        mText = "";
        textLength = 6;
        this.setBackgroundDrawable(null);
        setLongClickable(false);
        setTextIsSelectable(false);
        setCursorVisible(false);
        rectFS = new ArrayList<>();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension((Wide)*6+12, spzceY+ AutoUtils.getPercentWidthSize(5));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //边框画笔
//        canvas.drawColor(0xffff0000);
        sidePaint.setAntiAlias(true); //消除锯齿
        sidePaint.setStrokeWidth(2);//设置画笔的宽度
        sidePaint.setStyle(Paint.Style.STROKE);//设置绘制轮廓
        sidePaint.setColor(0xaa444444);
        //背景色画笔
        backPaint.setStyle(Paint.Style.FILL);
        backPaint.setColor(0xffffffff);
        //文字的画笔
        textPaint.setTextSize(18);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setColor(0xff000000);
        for (int i = 0; i < textLength; i++) {
            //区分已输入和未输入的边框颜色
//            if (mText.length() >= i) {
//                sidePaint.setColor(0xff000000);
//            } else {
//                sidePaint.setColor(0xffff0000);
//            }
//            RectF rect = new RectF(i * Wide , spzceY,i * Wide + Wide,Wide); //四个值，分别代表4条线，距离起点位置的线
            @SuppressLint("DrawAllocation") RectF rect = new RectF(i * Wide, 0, i * Wide + Wide , spzceY); //四个值，分别代表4条线，距离起点位置的线

            canvas.drawRoundRect(rect, 0, 0, backPaint); //绘制背景色
            canvas.drawRoundRect(rect, 0, 0, sidePaint); //绘制边框；
            rectFS.add(rect);
        }
        //画密码圆点
        for (int j = 0; j < mText.length(); j++) {
            canvas.drawCircle(rectFS.get(j).centerX(), rectFS.get(j).centerY(), AutoUtils.getPercentWidthSize(15), textPaint);
        }
    }
    public void clearText(){
        setText("");
        setInputType(InputType.TYPE_CLASS_NUMBER| InputType.TYPE_NUMBER_VARIATION_NORMAL);
    }

}
