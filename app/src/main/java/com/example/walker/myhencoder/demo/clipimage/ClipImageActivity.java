package com.example.walker.myhencoder.demo.clipimage;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.walker.myhencoder.R;
import com.example.walker.myhencoder.base.activityresult.ActivityResultHelper;
import com.example.walker.myhencoder.demo.clipimage.view.ClipView;
import com.example.walker.myhencoder.demo.clipimage.view.ClipViewLayout;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @Author Walker
 * @Date 2019-11-27 16:35
 * @Summary 头像裁剪Activity
 */
public class ClipImageActivity extends Activity implements OnClickListener {

    private static final String TAG = "ClipImageActivity";

    public static final String CLIP_TYPE = "clip_type";
    public static final String CLIP_SCALE_HW = "clip_scale_hw";
    public static final String TITLE_NAME = "title_name";
    public static final String DEST_FILE = "dest_file";
    public static final int REQ_CLIP_AVATAR = 150;

    private ClipViewLayout mClipViewLayout;
    private ImageView mBack;
    private TextView mBtnCancel;
    private TextView mBtnOk;
    private TextView mTvTitleName;

    private int mClipType;
    private float mClipScaleHW;
    private String mTitleName;
    private String mDestFile;

    /**
     * 开启操作
     *
     * @param activity     上下文宿主
     * @param uri          图片uri
     * @param destSaveFile 指定路径
     * @param clipType     裁剪类型（圆、矩形、九宫格）
     * @param clipScaleHW  裁剪框的高宽比
     * @param titleName    标题名称
     * @param callback     结果回传
     */
    public static void actionStart(Activity activity, Uri uri, String destSaveFile, int clipType, float clipScaleHW, String titleName, ActivityResultHelper.Callback callback) {
        if (uri == null) {
            return;
        }
        Intent intent = new Intent();
        intent.setClass(activity, ClipImageActivity.class);
        intent.putExtra(DEST_FILE, destSaveFile);
        intent.putExtra(CLIP_TYPE, clipType);
        intent.putExtra(CLIP_SCALE_HW, clipScaleHW);
        intent.putExtra(TITLE_NAME, titleName);
        intent.setData(uri);
        ActivityResultHelper.init(activity).startActivityForResult(intent, callback);
    }

    /**
     * 开启操作
     *
     * @param activity     上下文宿主
     * @param uri          图片uri
     * @param destSaveFile 指定路径
     * @param clipType     裁剪类型（圆、矩形、九宫格）
     * @param clipScaleHW  裁剪框的高宽比
     * @param titleName    标题名称
     */
    public static void actionStart(Activity activity, Uri uri, String destSaveFile, int clipType, float clipScaleHW, String titleName) {
        if (uri == null) {
            return;
        }
        Intent intent = new Intent();
        intent.setClass(activity, ClipImageActivity.class);
        intent.putExtra(DEST_FILE, destSaveFile);
        intent.putExtra(CLIP_TYPE, clipType);
        intent.putExtra(CLIP_SCALE_HW, clipScaleHW);
        intent.putExtra(TITLE_NAME, titleName);
        intent.setData(uri);
        activity.startActivityForResult(intent, REQ_CLIP_AVATAR);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clip_image);
        initData();
        initView();
    }

    private void initData() {
        mClipType = getIntent().getIntExtra(CLIP_TYPE, ClipView.TYPE_RECT);
        Log.i(TAG, "onCreate: mType =" + mClipType);
        mClipScaleHW = getIntent().getFloatExtra(CLIP_SCALE_HW, 0.6f);
        mTitleName = getIntent().getStringExtra(TITLE_NAME);
        mDestFile = getIntent().getStringExtra(DEST_FILE);
    }

    /**
     * 初始化组件
     */
    public void initView() {
        mClipViewLayout = findViewById(R.id.clipViewLayout);
        mBack = findViewById(R.id.iv_back);
        mBtnCancel = findViewById(R.id.btn_cancel);
        mBtnOk = findViewById(R.id.bt_ok);
        mTvTitleName = findViewById(R.id.stock_name);
        //设置点击事件监听器
        mBack.setOnClickListener(this);
        mBtnCancel.setOnClickListener(this);
        mBtnOk.setOnClickListener(this);
        mClipViewLayout.setClipType(mClipType);
        mClipViewLayout.setScaleClipHW(mClipScaleHW);
        mTvTitleName.setText(mTitleName);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mClipViewLayout.setVisibility(View.VISIBLE);
        mClipViewLayout.setClipType(mClipType);
        //设置图片资源
        mClipViewLayout.setImageSrc(getIntent().getData());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
            case R.id.btn_cancel:
                finish();
                break;
            case R.id.bt_ok:
                if (TextUtils.isEmpty(mDestFile)) {
                    generateUriAndReturn();
                } else {
                    generateFileAndReturn();
                }
                break;
            default:
                break;
        }
    }


    /**
     * 生成Uri并且通过setResult返回给打开的activity
     */
    private void generateUriAndReturn() {
        //调用返回剪切图
        Bitmap zoomedCropBitmap = mClipViewLayout.clip();

        if (zoomedCropBitmap == null) {
            Log.e("android", "zoomedCropBitmap == null");
            return;
        }
        Uri mSaveUri = Uri.fromFile(new File(getCacheDir(), "cropped_" + System.currentTimeMillis() + ".jpg"));
        if (mSaveUri != null) {
            OutputStream outputStream = null;
            try {
                outputStream = getContentResolver().openOutputStream(mSaveUri);
                if (outputStream != null) {
                    zoomedCropBitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream);
                }
            } catch (IOException ex) {
                Log.e("android", "Cannot open file: " + mSaveUri, ex);
            } finally {
                if (outputStream != null) {
                    try {
                        outputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            Intent intent = new Intent();
            intent.setData(mSaveUri);
            setResult(RESULT_OK, intent);
            Log.i(TAG,"generateUriAndReturn->uri:"+mSaveUri);
            finish();
        }
    }

    /**
     * 生成文件路径并且通过setResult返回给打开的activity
     */
    private void generateFileAndReturn() {
        String clipSavePath = mClipViewLayout.clip2Path(mDestFile);
        Intent intent = new Intent();
        intent.putExtra(ClipImageHelper.CLIP_PATH, clipSavePath);
        setResult(RESULT_OK, intent);
        Log.i(TAG,"generateFileAndReturn->clipSavePath:"+clipSavePath);
        finish();
    }
}
