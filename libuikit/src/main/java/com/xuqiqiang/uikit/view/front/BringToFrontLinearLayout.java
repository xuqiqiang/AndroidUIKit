package com.xuqiqiang.uikit.view.front;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

/**
 * 将持有焦点的view的父控件移到视图最上方
 */
public class BringToFrontLinearLayout extends BringToFrontLinear {
    public BringToFrontLinearLayout(Context context) {
        super(context);
    }

    public BringToFrontLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BringToFrontLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void requestChildFocus(View child, View focused) {
        child.bringToFront();
        super.requestChildFocus(child, focused);
    }
}