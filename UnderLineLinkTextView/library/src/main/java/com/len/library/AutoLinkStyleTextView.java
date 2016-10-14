package com.len.library;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Shaolei on 2016/10/10.
 */
public class AutoLinkStyleTextView extends TextView {

    private static String DEFAULT_TEXT_VALUE = null;
    private static int DEFAULT_COLOR = Color.parseColor("#f23218");
    private static boolean HAS_UNDER_LINE = true;

    private ClickCallBack mClickCallBack;

    public AutoLinkStyleTextView(Context context) {
        this(context, null);
    }

    public AutoLinkStyleTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AutoLinkStyleTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.AutoLinkStyleTextView, defStyleAttr, 0);
        DEFAULT_TEXT_VALUE = typedArray.getString(R.styleable.AutoLinkStyleTextView_AutoLinkStyleTextView_text_value);
        DEFAULT_COLOR = typedArray.getColor(R.styleable.AutoLinkStyleTextView_AutoLinkStyleTextView_default_color, DEFAULT_COLOR);
        HAS_UNDER_LINE = typedArray.getBoolean(R.styleable.AutoLinkStyleTextView_AutoLinkStyleTextView_has_under_line, HAS_UNDER_LINE);
        addStyle();
		typedArray.recycle();
    }

    private void addStyle(){
        if (!TextUtils.isEmpty(DEFAULT_TEXT_VALUE) && DEFAULT_TEXT_VALUE.contains(",")) {
            String[] values = DEFAULT_TEXT_VALUE.split(",");
            SpannableString spannableString = new SpannableString(getText().toString().trim());
            for (int i = 0; i < values.length; i++){
                final int position = i;
                spannableString.setSpan(new ClickableSpan() {
                    @Override
                    public void onClick(View widget) {
                        if (mClickCallBack != null) mClickCallBack.onClick(position);
                    }

                    @Override
                    public void updateDrawState(TextPaint ds) {
                        super.updateDrawState(ds);
                        ds.setColor(DEFAULT_COLOR);
                        ds.setUnderlineText(HAS_UNDER_LINE);
                    }
                }, getText().toString().trim().indexOf(values[i]), getText().toString().trim().indexOf(values[i]) + values[i].length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            setText(spannableString);
            setMovementMethod(LinkMovementMethod.getInstance());
        }
    }


    public void setOnClickCallBack(ClickCallBack clickCallBack){
        this.mClickCallBack = clickCallBack;
    }

    public interface ClickCallBack{
        void onClick(int position);
    }
}
