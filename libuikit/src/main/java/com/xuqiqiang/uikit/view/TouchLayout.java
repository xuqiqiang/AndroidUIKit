package com.xuqiqiang.uikit.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;

/**
 * Created by xuqiqiang on 2019/09/20.
 */
public class TouchLayout extends FrameLayout {

    public TouchLayout(Context context) {
        this(context, null);
    }

    public TouchLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }
}
