package com.example.walker.myhencoder.demo.clipimage;

import android.app.Activity;
import android.net.Uri;
import android.os.Environment;

import com.example.walker.myhencoder.base.activityresult.ActivityResultHelper;
import com.example.walker.myhencoder.demo.clipimage.view.ClipView;

import java.io.File;

/**
 * @Author Walker
 * @Date 2019-11-28 10:50
 * @Summary 裁剪辅助类
 */
public class ClipImageHelper {
    public static final String CLIP_PATH="clip_path";
    private static ClipImageHelper sInstance;

    private ClipImageHelper() {
    }

    public static ClipImageHelper get() {
        if (sInstance == null) {
            synchronized (ClipImageHelper.class) {
                if (sInstance == null) {
                    sInstance = new ClipImageHelper();
                }
            }
        }
        return sInstance;
    }

    /**
     * 获得默认的图片路径
     * @return
     */
    public String getDefaultImagePath(){
        return Environment.getExternalStorageDirectory() + File.separator + "Walker/ClipImage/" + System.currentTimeMillis() + ".jpg";
    }

    /**
     * 裁剪
     *
     * @param activity     上下文宿主
     * @param uri          图片uri
     * @param destSaveFile 指定路径
     * @param clipScaleHW  裁剪框的高宽比
     * @param titleName    标题名称
     * @param callback     结果回传
     */
    public void clip(Activity activity, Uri uri, String destSaveFile, float clipScaleHW, String titleName, ActivityResultHelper.Callback callback) {
        ClipImageActivity.actionStart(activity, uri, destSaveFile, ClipView.TYPE_PALACE, clipScaleHW, titleName, callback);
    }

    /**
     * 裁剪
     *
     * @param activity     上下文宿主
     * @param uri          图片uri
     * @param destSaveFile 指定路径
     * @param clipScaleHW  裁剪框的高宽比
     * @param titleName    标题名称
     */
    public void clip(Activity activity, Uri uri, String destSaveFile, float clipScaleHW, String titleName) {
        ClipImageActivity.actionStart(activity, uri, destSaveFile, ClipView.TYPE_PALACE, clipScaleHW, titleName);
    }
}
