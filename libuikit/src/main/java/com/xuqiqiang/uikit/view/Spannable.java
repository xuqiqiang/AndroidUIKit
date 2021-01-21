package com.xuqiqiang.uikit.view;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

import androidx.annotation.NonNull;

public class Spannable extends SpannableString {

    private int mColor;
    private int mSize;
    private boolean mUnderline;
    private View.OnClickListener mOnClickListener;

    public Spannable(CharSequence source) {
        super(source);
    }

    public Spannable(CharSequence source, int color) {
        this(source, color, 0, false, null);
    }

    public Spannable(CharSequence source, int color, int size, boolean underline, View.OnClickListener onClickListener) {
        super(source);
        this.mColor = color;
        this.mSize = size;
        this.mUnderline = underline;
        this.mOnClickListener = onClickListener;

        ClickableSpan span = new ClickableSpan() {

            @Override
            public void onClick(@NonNull View widget) {
                if (mOnClickListener != null) {
                    mOnClickListener.onClick(widget);
                }
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                ds.setColor(mColor);
                if (mSize > 0)
                    ds.setTextSize(mSize);
                ds.setUnderlineText(mUnderline);
            }
        };
        setSpan(span, 0, source.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
    }
}
