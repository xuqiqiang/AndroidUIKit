package com.xuqiqiang.uikit.gesture;

import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.xuqiqiang.uikit.utils.Logger;

public abstract class ScrollableGesture extends Gesture {

    private boolean isStop;
    private float mDownY;

    private GestureView<ViewGroupGesture> mParentGestureView;

    public ScrollableGesture(View view) {
        super(view);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (DEBUG) Logger.d("_Gesture_ ScrollableGesture dispatchTouchEvent:" + ev.getAction());
        if (mParentGestureView == null) {
            if (DEBUG) Logger.d("_Gesture_ ScrollableGesture dispatchTouchEvent 1");
            ViewParent parent = mView.getParent();
            while (!(parent instanceof GestureView
                    && ((GestureView) parent).getGesture() instanceof ViewGroupGesture)) {
                if (parent == null) {
                    throw new IllegalStateException("Not find parent gestureView!");
                }
                parent = parent.getParent();
            }

            if (DEBUG) Logger.d("_Gesture_ ScrollableGesture dispatchTouchEvent 2");
            mParentGestureView = (GestureView<ViewGroupGesture>) parent;
            mParentGestureView.getGesture().setGestureView((GestureView) mView);
        }

        if (ev.getAction() == MotionEvent.ACTION_DOWN && !isStop) {
            mDownY = ev.getY();
            isStop = false;
        } else {
            if (!isStop) {
                if (ev.getY() < mDownY && isScrollToBottom()) {
                    isStop = true;
                    mParentGestureView.getGesture().dispatchDownEvent(ev);
                }
            }
            if (isStop) {
                return false;
            }
        }
        ((ViewParent) mParentGestureView).requestDisallowInterceptTouchEvent(true);
        return true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mView instanceof ViewGroup && ((ViewGroup) mView).onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return mView.onTouchEvent(ev);
    }

    public void setStop(boolean stop) {
        isStop = stop;
    }

    protected abstract boolean isScrollToBottom();
}