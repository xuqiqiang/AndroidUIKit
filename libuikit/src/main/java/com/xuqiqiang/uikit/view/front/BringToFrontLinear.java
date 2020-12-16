package com.xuqiqiang.uikit.view.front;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

public class BringToFrontLinear extends LinearLayout {

    private BringToFrontHelper bringToFrontHelper;

    public BringToFrontLinear(Context context) {
        super(context);
        mInit();
    }

    public BringToFrontLinear(Context context, AttributeSet attrs) {
        super(context, attrs);
        mInit();
    }

    public BringToFrontLinear(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mInit();
    }

    private void mInit() {
        setWillNotDraw(true);
        setChildrenDrawingOrderEnabled(true);
        bringToFrontHelper = new BringToFrontHelper();
    }

    @Override
    protected int getChildDrawingOrder(int childCount, int i) {
        return bringToFrontHelper.getChildDrawingOrder(childCount, i);
    }

    @Override
    public void bringChildToFront(View child) {
        bringToFrontHelper.bringChildToFront(this, child);
    }
}