package com.xuqiqiang.uikit.view.front;

import android.view.View;
import android.view.ViewGroup;

/**
 * 如果需要把当前拥有焦点的view放大显示，而如果view之间的间隔较小放大后可能会造成压边情况，
 * 这时候常见的解决办法就是让拥有焦点的view掉用bringToFront()方法，但这种方法有一个问题就是
 * 如果该view的父布局是LinearLayout的话，获取焦点的view会跑到LinearLayout的最后一个位置，
 * 也就是说LinearLayout中哪个子view获取焦点哪个在LinearLayout的最后面
 */
public class BringToFrontHelper {

    private int mFocusChildIndex;

    public void bringChildToFront(ViewGroup viewGroup, View childView) {
        mFocusChildIndex = viewGroup.indexOfChild(childView);
        if (mFocusChildIndex != -1) {
            viewGroup.postInvalidate();
        }
    }

    public int getChildDrawingOrder(int childCount, int i) {
        if (mFocusChildIndex != -1) {
            if (i == childCount - 1) {
                if (mFocusChildIndex > childCount - 1) {
                    mFocusChildIndex = childCount - 1;
                }
                return mFocusChildIndex;
            }
            if (i == mFocusChildIndex) {

                return childCount - 1;
            }
        }
        if (childCount <= i) {
            i = childCount - 1;
        }
        return i;
    }
}