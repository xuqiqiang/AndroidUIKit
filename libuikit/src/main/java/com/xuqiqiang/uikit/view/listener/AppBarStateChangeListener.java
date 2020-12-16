package com.xuqiqiang.uikit.view.listener;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.view.View;

import com.google.android.material.appbar.AppBarLayout;
import com.xuqiqiang.uikit.utils.ArrayUtils;
import com.xuqiqiang.uikit.utils.Logger;

import java.util.List;

/**
 * Created by xuqiqiang on 2019/12/16.
 */
public class AppBarStateChangeListener implements AppBarLayout.OnOffsetChangedListener {

    private State mCurrentState = State.COLLAPSED;
    private View mBindView;
    private List<View> mBindViewList;
    private ObjectAnimator mObjectAnimator;

    public AppBarStateChangeListener bind(View view) {
        mBindView = view;
        return this;
    }

    public AppBarStateChangeListener bind(List<View> viewList) {
        mBindViewList = viewList;
        return this;
    }

    @Override
    public final void onOffsetChanged(AppBarLayout appBarLayout, int i) {
        if (i == 0) {
            if (mCurrentState != State.EXPANDED) {
                onStateChanged(appBarLayout, State.EXPANDED, mCurrentState);
                mCurrentState = State.EXPANDED;
            }
        } else if (Math.abs(i) >= appBarLayout.getTotalScrollRange()) {
            if (mCurrentState != State.COLLAPSED) {
                onStateChanged(appBarLayout, State.COLLAPSED, mCurrentState);
                mCurrentState = State.COLLAPSED;
            }
        } else if (Math.abs(i) >= appBarLayout.getTotalScrollRange() * 0.57) {
            if (mCurrentState != State.INTERNEDIATE) {
                onStateChanged(appBarLayout, State.INTERNEDIATE, mCurrentState);
                if (mCurrentState == State.INTERNEDIATE_LARGE && mBindView != null) {
                    PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat("scaleX", 1f, 0.0f);
                    PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("scaleY", 1f, 0.0f);
                    PropertyValuesHolder pvhAlpha = PropertyValuesHolder.ofFloat("alpha", 1f, 0.0f);
                    if (mObjectAnimator != null && mObjectAnimator.isRunning()) {
                        mObjectAnimator.removeAllListeners();
                        mObjectAnimator.cancel();
                    }
                    mObjectAnimator = ObjectAnimator.ofPropertyValuesHolder(mBindView, pvhX, pvhY, pvhAlpha);
                    mObjectAnimator.setDuration(300);
                    mObjectAnimator.start();
                } else if (mCurrentState == State.INTERNEDIATE_LARGE && !ArrayUtils.isEmpty(mBindViewList)) {
                    for (View view : mBindViewList) {
                        PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat("scaleX", 1f, 0.0f);
                        PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("scaleY", 1f, 0.0f);
                        PropertyValuesHolder pvhAlpha = PropertyValuesHolder.ofFloat("alpha", 1f, 0.0f);
                        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(view, pvhX, pvhY, pvhAlpha);
                        animator.setDuration(300);
                        animator.start();
                    }
                }
                mCurrentState = State.INTERNEDIATE;
            }
        } else {
            if (mCurrentState != State.INTERNEDIATE_LARGE) {
                onStateChanged(appBarLayout, State.INTERNEDIATE_LARGE, mCurrentState);
                if (mCurrentState == State.INTERNEDIATE && mBindView != null) {
                    PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat("scaleX", 0.0f, 1f);
                    PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("scaleY", 0.0f, 1f);
                    PropertyValuesHolder pvhAlpha = PropertyValuesHolder.ofFloat("alpha", 0.0f, 1f);
                    if (mObjectAnimator != null && mObjectAnimator.isRunning()) {
                        mObjectAnimator.removeAllListeners();
                        mObjectAnimator.cancel();
                    }
                    mObjectAnimator = ObjectAnimator.ofPropertyValuesHolder(mBindView, pvhX, pvhY, pvhAlpha);
                    mObjectAnimator.setDuration(300);
                    mObjectAnimator.start();
                } else if (mCurrentState == State.INTERNEDIATE && !ArrayUtils.isEmpty(mBindViewList)) {
                    for (View view : mBindViewList) {
                        PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat("scaleX", 0.0f, 1f);
                        PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("scaleY", 0.0f, 1f);
                        PropertyValuesHolder pvhAlpha = PropertyValuesHolder.ofFloat("alpha", 0.0f, 1f);
                        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(view, pvhX, pvhY, pvhAlpha);
                        animator.setDuration(300);
                        animator.start();
                    }
                }
                mCurrentState = State.INTERNEDIATE_LARGE;
            }
        }
    }

    public State getState() {
        return mCurrentState;
    }

    public void onStateChanged(AppBarLayout appBarLayout, State nextState, State lastState) {
        Logger.d("onStateChanged:" + nextState + "," + lastState);
        if (nextState == State.INTERNEDIATE_LARGE && lastState == State.INTERNEDIATE) {
            onShouldUpdate(true);
        } else if (nextState == State.INTERNEDIATE && lastState == State.INTERNEDIATE_LARGE) {
            onShouldUpdate(false);
        }
    }

    public void onShouldUpdate(boolean expanded) {
    }

    public enum State {
        EXPANDED,
        COLLAPSED,
        INTERNEDIATE,
        INTERNEDIATE_LARGE,
    }
}