package com.xuqiqiang.uikit.gesture;

import android.view.MotionEvent;
import android.view.View;

import com.xuqiqiang.uikit.utils.Logger;

public class ViewGroupGesture extends Gesture {

    private float mCurX;
    private float mCurY;

    private GestureView mChildGestureView;

    public ViewGroupGesture(View view) {
        super(view);
    }

    public void setGestureView(GestureView gestureView) {
        this.mChildGestureView = gestureView;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (DEBUG) Logger.d("_Gesture_ ViewGroupGesture dispatchTouchEvent:" + ev.getAction());
        mCurX = ev.getX();
        mCurY = ev.getY();
        if (ev.getAction() == MotionEvent.ACTION_UP) {
            if (mChildGestureView != null && mChildGestureView.getGesture() instanceof ScrollableGesture)
                ((ScrollableGesture) mChildGestureView.getGesture()).setStop(false);
        }
        return true;
    }

    public void dispatchDownEvent(MotionEvent ev) {
        ev.setAction(MotionEvent.ACTION_DOWN);
        ev.setLocation(mCurX, mCurY);
        mView.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return false;
    }
}