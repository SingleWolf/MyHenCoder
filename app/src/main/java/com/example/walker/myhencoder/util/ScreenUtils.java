package com.example.walker.myhencoder.util;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.util.DisplayMetrics;

/**
 * @Author Walker
 * @Date 2019-11-28 19:35
 * @Summary 屏幕工具类
 */
public class ScreenUtils {

    public static int getScreenWidth(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        if (Build.VERSION.SDK_INT >= 17) {
            ((Activity)context).getWindowManager().getDefaultDisplay().getRealMetrics(displayMetrics);
        } else {
            ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        }
        return displayMetrics.widthPixels;
    }

    public static int getScreenHeight(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        if (Build.VERSION.SDK_INT >= 17) {
            ((Activity)context).getWindowManager().getDefaultDisplay().getRealMetrics(displayMetrics);
        } else {
            ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        }
        return displayMetrics.heightPixels;
    }
}
