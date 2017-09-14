package com.len.library;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatTextView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.view.View;


/**
 * Created by Shaolei on 2016/10/1.
 * 添加链接textview
 */
public class AutoLinkStyleTextView extends AppCompatTextView {

    private static final int TYPE_START_IMAGE = 0;
    private static final int TYPE_CONTENT_TEXT = 1;

    private static int styleType = TYPE_CONTENT_TEXT;
    private static int defaultColor = Color.parseColor("#f23218");
    private String defaultTextValue = null;
    private boolean hasUnderLine = true;
    private int startImageDes;

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
        styleType = typedArray.getInt(R.styleable.AutoLinkStyleTextView_AutoLinkStyleTextView_type, TYPE_CONTENT_TEXT);
        defaultTextValue = typedArray.getString(R.styleable.AutoLinkStyleTextView_AutoLinkStyleTextView_text_value);
        defaultColor = typedArray.getColor(R.styleable.AutoLinkStyleTextView_AutoLinkStyleTextView_default_color, defaultColor);
        hasUnderLine = typedArray.getBoolean(R.styleable.AutoLinkStyleTextView_AutoLinkStyleTextView_has_under_line, hasUnderLine);
        startImageDes = typedArray.getResourceId(R.styleable.AutoLinkStyleTextView_AutoLinkStyleTextView_start_image, 0);
        addStyle();
        typedArray.recycle();
    }

    /**
     * 部分文字链接的通过xml设置静态渲染
     */
    private void addStyle() {
        if (!TextUtils.isEmpty(defaultTextValue) && defaultTextValue.contains(",")) {
            String[] values = defaultTextValue.split(",");
            SpannableString spannableString = new SpannableString(getText().toString().trim());
            for (int i = 0; i < values.length; i++) {
                final int position = i;
                spannableString.setSpan(new ClickableSpan() {
                    @Override
                    public void onClick(View widget) {
                        if (mClickCallBack != null) mClickCallBack.onClick(position);
                    }

                    @Override
                    public void updateDrawState(TextPaint ds) {
                        super.updateDrawState(ds);
                        ds.setColor(defaultColor);
                        ds.setUnderlineText(hasUnderLine);
                    }
                }, getText().toString().trim().indexOf(values[i]), getText().toString().trim().indexOf(values[i]) + values[i].length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            setText(spannableString);
            setMovementMethod(LinkMovementMethod.getInstance());
        }
    }

    /**
     * 以image开头风格的需要动态调用这个方法
     * 图片和文字已经过居中适配
     * @param text
     */
    public void setStartImageText(CharSequence text){
        if (styleType == TYPE_START_IMAGE && !TextUtils.isEmpty(text) && startImageDes != 0) {
            SpannableString spannableString = new SpannableString("   " + text);
            CenteredImageSpan span = new CenteredImageSpan(getContext(), startImageDes);
            spannableString.setSpan(span, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            setText(spannableString);
        }
    }

    private class CenteredImageSpan extends ImageSpan {

        private CenteredImageSpan(Context context, final int drawableRes) {
            super(context, drawableRes);
        }

        @Override
        public void draw(Canvas canvas, CharSequence text,
                         int start, int end, float x,
                         int top, int y, int bottom, Paint paint) {
            // image to draw
            Drawable b = getDrawable();
            // font metrics of text to be replaced
            Paint.FontMetricsInt fm = paint.getFontMetricsInt();
            int transY = (y + fm.descent + y + fm.ascent) / 2 - b.getBounds().bottom / 2;
            canvas.save();
            canvas.translate(x, transY);
            b.draw(canvas);
            canvas.restore();
        }
    }

    public void setOnClickCallBack(ClickCallBack clickCallBack) {
        this.mClickCallBack = clickCallBack;
    }

    public interface ClickCallBack {
        void onClick(int position);
    }
}
