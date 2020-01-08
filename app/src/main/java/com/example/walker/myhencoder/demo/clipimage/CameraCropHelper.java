package com.example.walker.myhencoder.demo.clipimage;

import android.app.Activity;
import android.os.Environment;

import com.example.walker.myhencoder.base.activityresult.ActivityResultHelper;
import com.example.walker.myhencoder.demo.clipimage.view.ClipView;

import java.io.File;

/**
 * @Author Walker
 * @Date 2019-11-28 20:54
 * @Summary 拍照裁剪辅助类
 */
public class CameraCropHelper {
    public static final String IMAGE_PATH = "image_path";
    private static CameraCropHelper sInstance;

    private CameraCropHelper() {
    }

    public static CameraCropHelper get() {
        if (sInstance == null) {
            synchronized (CameraCropHelper.class) {
                if (sInstance == null) {
                    sInstance = new CameraCropHelper();
                }
            }
        }
        return sInstance;
    }

    /**
     * 获得默认的图片路径
     *
     * @return
     */
    public String getDefaultImagePath() {
        return Environment.getExternalStorageDirectory() + File.separator + "Walker/TakePhoto/" + System.currentTimeMillis() + ".jpg";
    }

    /**
     * 裁剪
     *
     * @param activity     上下文宿主
     * @param destSaveFile 指定路径
     * @param clipScaleHW  裁剪框的高宽比
     * @param titleName    标题名称
     * @param callback     结果回传
     */
    public void take(Activity activity, String destSaveFile, float clipScaleHW, String titleName, ActivityResultHelper.Callback callback) {
        CameraCropActivity.actionStart(activity, destSaveFile, ClipView.TYPE_PALACE, clipScaleHW, titleName, callback);
    }

    /**
     * 裁剪
     *
     * @param activity     上下文宿主
     * @param destSaveFile 指定路径
     * @param clipScaleHW  裁剪框的高宽比
     * @param titleName    标题名称
     * @param isA4         是否A4效果
     * @param callback     结果回传
     */
    public void take(Activity activity, String destSaveFile, float clipScaleHW, String titleName, boolean isA4, ActivityResultHelper.Callback callback) {
        CameraCropActivity.actionStart(activity, destSaveFile, ClipView.TYPE_PALACE, clipScaleHW, titleName, isA4, callback);
    }

    /**
     * 高质量裁剪
     *
     * @param activity     上下文宿主
     * @param destSaveFile 指定路径
     * @param clipScaleHW  裁剪框的高宽比
     * @param titleName    标题名称
     * @param callback     结果回传
     */
    public void takePlus(Activity activity, String destSaveFile, float clipScaleHW, String titleName, ActivityResultHelper.Callback callback) {
        CameraCropPlusActivity.actionStart(activity, destSaveFile, ClipView.TYPE_PALACE, clipScaleHW, titleName, callback);
    }

    /**
     * 高质量裁剪
     *
     * @param activity     上下文宿主
     * @param destSaveFile 指定路径
     * @param clipScaleHW  裁剪框的高宽比
     * @param titleName    标题名称
     * @param isA4         是否A4效果
     * @param callback     结果回传
     */
    public void takePlus(Activity activity, String destSaveFile, float clipScaleHW, String titleName, boolean isA4, ActivityResultHelper.Callback callback) {
        CameraCropPlusActivity.actionStart(activity, destSaveFile, ClipView.TYPE_PALACE, clipScaleHW, titleName, isA4, callback);
    }

}
