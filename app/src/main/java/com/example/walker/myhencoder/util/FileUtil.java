package com.example.walker.myhencoder.util;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;

/**
 * @Author Walker
 * @Date 2019-11-27 15:47
 * @Summary 文件工具类
 */
public class FileUtil {
    private static final String TAG = "FileUtil";
    /**
     * 根据Uri返回文件绝对路径
     * 兼容了file:///开头的 和 content://开头的情况
     */
    public static String getRealFilePathFromUri(final Context context, final Uri uri) {
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_FILE.equalsIgnoreCase(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equalsIgnoreCase(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

    /**
     * 检查文件是否存在
     */
    public static String checkDirPath(String dirPath) {
        if (TextUtils.isEmpty(dirPath)) {
            return "";
        }
        File dir = new File(dirPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dirPath;
    }

    /**
     * 保存bitmap到SD卡
     *
     * @param bitmap bitmap
     * @param imagename 路径
     */
    public static String saveBitmap2SDCard(Bitmap bitmap, String imagename) {
        String name = "img-" + imagename + ".jpg";
        String absolutePath = Environment.getExternalStorageDirectory().getAbsolutePath();
        String path = absolutePath + File.separator + "myPic" + File.separator;
        File file = new File(path);
        boolean newFile = file.mkdirs();
        Log.i(TAG, "saveBitmapToSDCard: newFile = " + newFile);

        path = path + name;
        FileOutputStream fos = null;
        Log.i(TAG, "saveBitmapToSDCard: path =" + path);
        try {
            File file1 = new File(path);
            fos = new FileOutputStream(path);
            if (fos != null) {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fos);
                fos.close();
            }

            return path;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 保存bitmap到指定路径
     * @param bitmap bitmap
     * @param filePath 指定路径
     * @return 指定路径
     */
    public static String saveBitmap2Path(Bitmap bitmap,String filePath){
        try {
            File imageFile = new File(filePath);
            if (!imageFile.getParentFile().exists()) {
                imageFile.getParentFile().mkdirs();
            }
            FileOutputStream fos = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
        } catch (Exception e) {
            Log.e(TAG,e.toString());
        }
      return filePath;
    }

    public static long getFileSize(String path) {
        if (TextUtils.isEmpty(path) || !new File(path).exists()) {
            return 0L;
        }
        return new File(path).length();
    }

}
