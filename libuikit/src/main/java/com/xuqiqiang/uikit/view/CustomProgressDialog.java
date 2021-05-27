package com.xuqiqiang.uikit.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.LayoutRes;

import com.xuqiqiang.uikit.R;

import java.lang.ref.SoftReference;

public class CustomProgressDialog extends Dialog {

    private static int mDefaultLayout = R.layout.dialog_custom_progress;
    private volatile static SoftReference<CustomProgressDialog> rDialog;
    // for test
    private static int progress;
    private final ProgressBar pbLoading;
    private final RoundProgressBar rpbLoading;
    private CharSequence mText;
    private boolean mIndeterminate = true;

    private CustomProgressDialog(Context context, CharSequence message) {
        super(context, R.style.CustomProgressDialog);
        @SuppressLint("InflateParams")
        View view = LayoutInflater.from(context).inflate(mDefaultLayout, null);
        if (!TextUtils.isEmpty(message)) {
            mText = message;
            TextView tvMessage = view.findViewById(R.id.tv_message);
            tvMessage.setVisibility(View.VISIBLE);
            tvMessage.setText(message);
        }
        pbLoading = view.findViewById(R.id.pb_loading);
        rpbLoading = view.findViewById(R.id.rpb_loading);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        addContentView(view, lp);
    }

    public static void setDefaultLayout(@LayoutRes int layoutId) {
        mDefaultLayout = layoutId;
    }

    public static void show(Context context) {
        show(context, null);
    }

    public static void show(Context context, CharSequence message) {
        show(context, message, true);
    }

    public static void show(Context context, CharSequence message, boolean cancelable) {
        CustomProgressDialog sDialog = null;
        if (rDialog != null) {
            sDialog = rDialog.get();
        }

        if (sDialog != null && sDialog.isShowing()) {
            if (TextUtils.equals(sDialog.mText, message)) return;
            // Fix: java.lang.IllegalArgumentException: View=DecorView not attached to window manager
            try {
                sDialog.dismiss();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (!(context instanceof Activity) || ((Activity) context).isFinishing())
            return;

        sDialog = new CustomProgressDialog(context, message);
        sDialog.setCancelable(cancelable);
        final CustomProgressDialog finalSDialog = sDialog;
        sDialog.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (rDialog != null && rDialog.get() == finalSDialog) {
                    clearReference();
                }
            }
        });

        if (sDialog != null && !sDialog.isShowing()) {
            try {
                sDialog.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        rDialog = new SoftReference<>(sDialog);
    }

    private static void clearReference() {
        if (rDialog != null) {
            rDialog.clear();
            rDialog = null;
        }
    }

    public static void setProgress(int progress) {
        CustomProgressDialog sDialog = null;
        if (rDialog != null) {
            sDialog = rDialog.get();
        }
        if (sDialog != null && sDialog.isShowing()) {
            sDialog.setLoadingProgress(progress);
        }
    }

    public static void close() {
        CustomProgressDialog sDialog = null;
        if (rDialog != null) {
            sDialog = rDialog.get();
        }
        if (sDialog != null && sDialog.isShowing()) {
            // Fix: java.lang.IllegalArgumentException: View=DecorView not attached to window manager
            try {
                sDialog.dismiss();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        clearReference();
    }

    @Deprecated
    public static void stop() {
        close();
    }

    public static void main(Context context) {
        CustomProgressDialog.show(context, "加载中...", true);
        progress = 0;
        CustomProgressDialog.setProgress(progress);
        testCase(context);
    }

    private static void testCase(final Context context) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                progress += 1;
                CustomProgressDialog.setProgress(progress);
                if (progress >= 100) {
                    CustomProgressDialog.close();
                    ToastMaster.showToast(context, "加载完成");
                    return;
                }
                testCase(context);
            }
        }, 30);
    }

    private void setLoadingProgress(int progress) {
        if (rpbLoading == null) return;
        if (mIndeterminate) {
            mIndeterminate = false;
            pbLoading.setVisibility(View.GONE);
            rpbLoading.setVisibility(View.VISIBLE);
        }
        rpbLoading.setProgress(progress);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            pbLoading.setIndeterminateTintMode(PorterDuff.Mode.CLEAR);
            pbLoading.setVisibility(View.GONE);
        }
    }
}
