package com.xuqiqiang.uikit.gesture;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;

import com.xuqiqiang.uikit.utils.Logger;

public abstract class PullRefreshGesture extends Gesture {

    private final float MAX_TEXT_OFFSET;
    private final float MAX_TOUCH_OFFSET;
    private View mAnimView;

    private RefreshListener mListener;
    private boolean mNeedRefresh;

    private boolean startTouch;
    private float mDownY;
    private float mMoveY;

    private float mTranslationY;
    private boolean mDisallowIntercept;

    public PullRefreshGesture(View view) {
        super(view);
        MAX_TEXT_OFFSET = initMaxViewOffset();
        MAX_TOUCH_OFFSET = initMaxTouchOffset();
    }

    protected abstract float initMaxViewOffset();

    protected abstract float initMaxTouchOffset();

    protected abstract boolean superDispatchTouchEvent(MotionEvent ev);

    protected abstract boolean onSuperTouchEvent(MotionEvent ev);

    public void setAnimView(View view) {
        this.mAnimView = view;
    }

    public void setListener(RefreshListener listener) {
        this.mListener = listener;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
//        super.dispatchTouchEvent(ev);
        if (mListener != null && !mNeedRefresh && mAnimView != null) {
            Logger.d("dispatchTouchEvent:" + ev.getY());
            float y = ev.getY();
            if (ev.getAction() == MotionEvent.ACTION_DOWN) {
                startTouch = true;
                mDownY = y;
                mMoveY = 0;
                mDisallowIntercept = false;
            } else {
                if (mDisallowIntercept || !startTouch) return superDispatchTouchEvent(ev);
                if (ev.getAction() == MotionEvent.ACTION_MOVE) {
                    if (y > mDownY) {
                        float translationY = Math.min((y - mDownY) / MAX_TOUCH_OFFSET * MAX_TEXT_OFFSET, MAX_TEXT_OFFSET);
                        mAnimView.setTranslationY(translationY);
                        mAnimView.setAlpha(translationY / MAX_TEXT_OFFSET);
                        mTranslationY = translationY;
                        if (y < mMoveY) {
                            mMoveY = y;
                            return true;
                        }
                        mMoveY = y;
                    }
                } else if (ev.getAction() == MotionEvent.ACTION_UP) {
                    startTouch = false;
                    if (y > mDownY) {
                        mNeedRefresh = mTranslationY > MAX_TEXT_OFFSET * 0.6;
                        mTranslationY = 0;
                        PropertyValuesHolder translationY = PropertyValuesHolder.ofFloat("translationY", 0f);
                        PropertyValuesHolder alpha = PropertyValuesHolder.ofFloat("alpha", 0f);
                        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(mAnimView,
                                translationY, alpha);
                        objectAnimator.setDuration(200);
                        objectAnimator.start();
                        objectAnimator.addListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                super.onAnimationEnd(animation);
                                if (mNeedRefresh) {
                                    mListener.onRefresh();
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            mNeedRefresh = false;
                                        }
                                    }, 200);
//                                    new Handler().postDelayed(
//                                            () -> mNeedRefresh = false, 200);
                                }
                            }
                        });
                    }
                }
            }
        }
        return superDispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }

    @Override
    public void requestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        mDisallowIntercept = disallowIntercept;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (mListener != null) {
            float y = ev.getY();
            if (ev.getAction() == MotionEvent.ACTION_MOVE) {
                if (y > mDownY) {
                    return true;
                }
            }
        }
        return onSuperTouchEvent(ev);
    }

    public interface RefreshListener {
        void onRefresh();
    }
}