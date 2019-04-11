package com.sohu.auto.treasure.utils;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;


/**
 * Update by Chason Chen on 2017/8/25.
 */
public class ToastUtils {
    private static Toast mToast;

    private static Toast mInnerToast;

    public static void show(Context context, String text) {
        getToast(context, text, Toast.LENGTH_SHORT).show();
    }

    public static void showLong(Context context, String text) {
        getToast(context, text, Toast.LENGTH_LONG).show();
    }

    private static Toast getToast(Context context, String text, int duration) {
        if (null == mInnerToast) {
            if (context instanceof AppCompatActivity) {
                //将context替换为applicationContext 防止内存泄露
                context = context.getApplicationContext();
            }
            mInnerToast = Toast.makeText(context,"",Toast.LENGTH_SHORT);
        }
        mInnerToast.setText(text);
        mInnerToast.setDuration(duration);
        return mInnerToast;
    }

    public static void showCenterView(Context context, View view) {
        if (context == null) {
            return;
        }
        //将context替换为applicationContext 防止内存泄露
        context = context.getApplicationContext();
        if (mToast == null) {
            mToast = new Toast(context);
        }
        mToast.setView(view);
        mToast.setGravity(Gravity.CENTER, 0, 0);
        mToast.setDuration(Toast.LENGTH_SHORT);
        mToast.show();
    }
}
