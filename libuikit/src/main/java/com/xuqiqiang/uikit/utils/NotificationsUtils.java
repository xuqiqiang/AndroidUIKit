package com.xuqiqiang.uikit.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

import androidx.core.app.NotificationManagerCompat;

import java.lang.reflect.Method;

/**
 * Created by xuqiqiang on 2016/05/22.
 */
public class NotificationsUtils {

    @SuppressLint("NewApi")
    public static boolean isNotificationEnabled(Context context) {
        NotificationManagerCompat manager = NotificationManagerCompat.from(context);
        return manager.areNotificationsEnabled();
    }

    public static void getAppDetailSettingIntent(Context context) {
        getAppDetailSettingIntent(context, 0);
    }

    public static void getAppDetailSettingIntent(Context context, int requestCode) {

        try {
            // 根据isOpened结果，判断是否需要提醒用户跳转AppInfo页面，去打开App通知权限
            Intent intent = new Intent();
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setAction(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
            //这种方案适用于 API 26, 即8.0（含8.0）以上可以用
            intent.putExtra(Settings.EXTRA_APP_PACKAGE, context.getPackageName());
            intent.putExtra(Settings.EXTRA_CHANNEL_ID, context.getApplicationInfo().uid);

            //这种方案适用于 API21——25，即 5.0——7.1 之间的版本可以使用
            intent.putExtra("app_package", context.getPackageName());
            intent.putExtra("app_uid", context.getApplicationInfo().uid);

            // 小米6 -MIUI9.6-8.0.0系统，是个特例，通知设置界面只能控制"允许使用通知圆点"——然而这个玩意并没有卵用，我想对雷布斯说：I'm not ok!!!
            if ("MI 6".equals(Build.MODEL)) {
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", context.getPackageName(), null);
                intent.setData(uri);
                // intent.setAction("com.android.settings/.SubSettings");
            }
            if (context instanceof Activity)
                ((Activity) context).startActivityForResult(intent, requestCode);
            else context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            // 出现异常则跳转到应用设置界面：锤子坚果3——OC105 API25
            Intent intent = new Intent();
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.FROYO) {
                intent.setAction(Intent.ACTION_VIEW);
                intent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
                intent.putExtra("com.android.settings.ApplicationPkgName", context.getPackageName());
            } else {
                //下面这种方案是直接跳转到当前应用的设置界面。
                //https://blog.csdn.net/ysy950803/article/details/71910806
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", context.getPackageName(), null);
                intent.setData(uri);
            }
            if (context instanceof Activity)
                ((Activity) context).startActivityForResult(intent, requestCode);
            else context.startActivity(intent);
        }
    }

    public static Notification newNotification(Context context,
                                               boolean autoCancel,
                                               CharSequence title,
                                               CharSequence text,
                                               PendingIntent intent,
                                               int icon) {
        return newNotification(context, autoCancel, title, text, intent, icon, System.currentTimeMillis());
    }

    public static Notification newNotification(Context context,
                                               boolean autoCancel,
                                               CharSequence title,
                                               CharSequence text,
                                               PendingIntent intent,
                                               int icon,
                                               long when) {
        Notification notification = null;

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            Class clazz = Notification.class;//notification.getClass();
            try {
                notification = new Notification(icon, text, when);
                notification.flags = 16;
                Method m2 = clazz.getDeclaredMethod("setLatestEventInfo", Context.class, CharSequence.class, CharSequence.class, PendingIntent.class);
                m2.invoke(notification, context, title,
                        text, intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            notification = new Notification.Builder(context)
                    .setAutoCancel(autoCancel)
                    .setContentTitle(title)
                    .setContentText(text)
                    .setContentIntent(intent)
                    .setSmallIcon(icon)
                    .setWhen(when)
                    .build();
        }
        return notification;
    }
}
