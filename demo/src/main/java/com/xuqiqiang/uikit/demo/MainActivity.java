package com.xuqiqiang.uikit.demo;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import com.xuqiqiang.uikit.activity.BaseThemeActivity;
import com.xuqiqiang.uikit.utils.ArrayUtils;
import com.xuqiqiang.uikit.view.CustomProgressDialog;
import com.xuqiqiang.uikit.view.ToastMaster;
import com.xuqiqiang.uikit.view.dialog.BaseDialog;
import com.xuqiqiang.uikit.view.listener.OnItemClickListener;
import com.xuqiqiang.uikit.view.menu.PopupMenu;

public class MainActivity extends BaseThemeActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void toast(View view) {
        ToastMaster.showToast(this, "这是Toast");
    }

    public void dialog(View view) {
        new BaseDialog.Builder(this)
                .setTitle("标题")
                .setMessage("这是对话框内容")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .setNegativeButton(android.R.string.cancel, null)
                .create().show();
    }

    public void progressDialog(View view) {
        CustomProgressDialog.show(this, "请稍等...");
    }

    public void progressDialog2(View view) {
        CustomProgressDialog.main(this);
    }

    public void popupMenu(View view) {
        new PopupMenu(this).show(ArrayUtils.createList(
                new PopupMenu.MenuItem("选项1"),
                new PopupMenu.MenuItem("选项2"),
                new PopupMenu.MenuItem("选项3")),
                new OnItemClickListener() {
                    @Override
                    public void onItemClick(View v, int position) {
                        ToastMaster.showToast(MainActivity.this, "选项" + (position + 1));
                    }
                });
    }
}
