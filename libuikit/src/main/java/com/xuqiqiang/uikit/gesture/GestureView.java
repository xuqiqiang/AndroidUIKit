package com.xuqiqiang.uikit.gesture;

import android.view.MotionEvent;

public interface GestureView<T extends Gesture> {
    boolean DEBUG = true;

    boolean dispatchTouchEvent(MotionEvent ev);

    boolean onInterceptTouchEvent(MotionEvent ev);

    boolean onTouchEvent(MotionEvent ev);

    void requestDisallowInterceptTouchEvent(boolean disallowIntercept);

    T getGesture();
}