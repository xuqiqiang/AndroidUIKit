package com.xuqiqiang.uikit.view.front;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

public class BringToFrontRelative extends RelativeLayout {

    private BringToFrontHelper bringToFrontHelper;

    public BringToFrontRelative(Context context) {
        super(context);
        mInit();
    }

    public BringToFrontRelative(Context context, AttributeSet attrs) {
        super(context, attrs);
        mInit();
    }

    public BringToFrontRelative(Context context, AttributeSet attrs, int defStyleAttr) {
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