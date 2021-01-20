package com.xuqiqiang.uikit.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;

/**
 * Created by xuqiqiang on 2019/09/20.
 */
public class TouchInterceptLayout extends FrameLayout {

    public TouchInterceptLayout(Context context) {
        this(context, null);
    }

    public TouchInterceptLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }
}
