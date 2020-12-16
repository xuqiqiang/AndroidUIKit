package com.xuqiqiang.uikit.gesture;

import android.view.MotionEvent;
import android.view.View;

public class Gesture implements GestureView {

    protected View mView;

    Gesture(View view) {
        if (!(view instanceof GestureView))
            throw new IllegalStateException("Should implements gestureView!");
        this.mView = view;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return false;
    }

    @Override
    public void requestDisallowInterceptTouchEvent(boolean disallowIntercept) {
    }

    @Override
    public Gesture getGesture() {
        return this;
    }
}