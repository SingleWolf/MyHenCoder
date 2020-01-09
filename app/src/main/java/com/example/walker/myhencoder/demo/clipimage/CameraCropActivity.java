package com.example.walker.myhencoder.demo.clipimage;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.OrientationEventListener;
import android.view.View;

import com.example.walker.myhencoder.R;
import com.example.walker.myhencoder.base.activityresult.ActivityResultHelper;
import com.example.walker.myhencoder.demo.clipimage.view.CameraPreview;
import com.example.walker.myhencoder.demo.clipimage.view.ClipView;
import com.example.walker.myhencoder.util.BitmapUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @Author Walker
 * @Date 2019-11-28 18:47
 * @Summary 带裁剪功能的拍照
 */
public class CameraCropActivity extends Activity {
    private static final String TAG = "CameraCropActivity";

    public static final String CLIP_TYPE = "clip_type";
    public static final String CLIP_SCALE_HW = "clip_scale_hw";
    public static final String TITLE_NAME = "title_name";
    public static final String DEST_FILE = "dest_file";
    public static final String IS_A4 = "is_a4";

    private CameraPreview cameraPreview;
    private ClipView mClipView;
    private View mControlViewTop;
    private View mControlViewBottom;
    // 设备方向监听器
    private OrientationEventListener mOrientationListener;
    //当前是否为竖屏方向
    private boolean mIsOrientationPortrait = true;
    //是否还能旋转
    private boolean mIsOrientationEnable = true;

    private int mClipType;
    private float mClipScaleHW;
    private String mTitleName;
    private String mDestFile;
    private boolean mIsA4;
    private Direction mDirection;

    /**
     * 开启操作
     *
     * @param activity     上下文宿主
     * @param destSaveFile 指定路径
     * @param clipType     裁剪类型（圆、矩形、九宫格）
     * @param clipScaleHW  裁剪框的高宽比
     * @param titleName    标题名称
     * @param callback     结果回传
     */
    public static void actionStart(Activity activity, String destSaveFile, int clipType, float clipScaleHW, String titleName, ActivityResultHelper.Callback callback) {
        Intent intent = new Intent();
        intent.setClass(activity, CameraCropActivity.class);
        intent.putExtra(DEST_FILE, destSaveFile);
        intent.putExtra(CLIP_TYPE, clipType);
        intent.putExtra(CLIP_SCALE_HW, clipScaleHW);
        intent.putExtra(TITLE_NAME, titleName);
        ActivityResultHelper.init(activity).startActivityForResult(intent, callback);
    }

    /**
     * 开启操作
     *
     * @param activity     上下文宿主
     * @param destSaveFile 指定路径
     * @param clipType     裁剪类型（圆、矩形、九宫格）
     * @param clipScaleHW  裁剪框的高宽比
     * @param titleName    标题名称
     * @param isA4         是否A4纸效果
     * @param callback     结果回传
     */
    public static void actionStart(Activity activity, String destSaveFile, int clipType, float clipScaleHW, String titleName, boolean isA4, ActivityResultHelper.Callback callback) {
        Intent intent = new Intent();
        intent.setClass(activity, CameraCropActivity.class);
        intent.putExtra(DEST_FILE, destSaveFile);
        intent.putExtra(CLIP_TYPE, clipType);
        intent.putExtra(CLIP_SCALE_HW, clipScaleHW);
        intent.putExtra(TITLE_NAME, titleName);
        intent.putExtra(IS_A4, isA4);
        ActivityResultHelper.init(activity).startActivityForResult(intent, callback);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_crop);
        initData();
        initView();
    }

    private void initData() {
        mClipType = getIntent().getIntExtra(CLIP_TYPE, ClipView.TYPE_RECT);
        Log.i(TAG, "onCreate: mType =" + mClipType);
        mClipScaleHW = getIntent().getFloatExtra(CLIP_SCALE_HW, 0.6f);
        mTitleName = getIntent().getStringExtra(TITLE_NAME);
        mDestFile = getIntent().getStringExtra(DEST_FILE);
        mIsA4 = getIntent().getBooleanExtra(IS_A4, false);
        mDirection = new Direction();
    }

    private void initView() {
        cameraPreview = findViewById(R.id.camera_surface);
        mControlViewTop = findViewById(R.id.controlViewTop);
        mControlViewBottom = findViewById(R.id.controlViewBottom);
        mClipView = findViewById(R.id.clip_view);
        mClipView.setClipType(ClipView.TYPE_PALACE);
        mClipView.setScaleClipHW(mClipScaleHW);
        mClipView.setMessageTip(mTitleName);
        //设置剪切框边框
        mClipView.setClipBorderWidth(ClipView.dip2px(this, 2f));
        //设置剪切框水平间距
        if (mIsA4) {
            mClipView.setHorizontalPaddingForA4(ClipView.dip2px(this, 35f));
        } else {
            mClipView.setHorizontalPadding(ClipView.dip2px(this, 35f));
        }

        //监听屏幕方向
        mOrientationListener = new OrientationEventListener(this, SensorManager.SENSOR_DELAY_NORMAL) {
            @Override
            public void onOrientationChanged(int orientation) {
                if (mIsOrientationEnable && (((orientation >= 0) && (orientation <= 45)) || (orientation >= 315)
                        || ((orientation >= 135) && (orientation <= 225)))) {// portrait
                    if (!mIsOrientationPortrait) {
                        Log.i(TAG, "竖屏");
                        mIsOrientationPortrait = true;
                        if (mClipView != null) {
                            if (mIsA4) {
                                mDirection.setOrientation(orientation);
                                resetControlView();
                                mClipView.refreshOrientationChangedForA4(true, orientation);
                            } else {
                                mClipView.refreshOrientationChanged(true);
                            }
                        }
                    }
                } else if (mIsOrientationEnable && (((orientation > 45) && (orientation < 135))
                        || ((orientation > 225) && (orientation < 315)))) {// landscape
                    if (mIsOrientationPortrait) {
                        Log.i(TAG, "横屏");
                        mIsOrientationPortrait = false;
                        if (mClipView != null) {
                            if (mIsA4) {
                                mDirection.setOrientation(orientation);
                                resetControlView();
                                mClipView.refreshOrientationChangedForA4(false, orientation);
                            } else {
                                mClipView.refreshOrientationChanged(false);
                            }
                        }
                    }
                }
            }
        };
        mOrientationListener.enable();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (cameraPreview != null) {
            cameraPreview.focus();
        }
        mOrientationListener.enable();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mOrientationListener.disable();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            onCancel();
        }
        return super.onKeyDown(keyCode, event);
    }

    public void onFocus(View v) {
        cameraPreview.focus();
    }

    public void onCancel(View v) {
        onCancel();
    }

    private void onCancel() {
        finish();
    }

    public void onTake(View v) {
        takePhoto();
    }

    private void takePhoto() {
        try {
            cameraPreview.takePhoto((data, camera) -> {
                if (TextUtils.isEmpty(mDestFile)) {
                    mDestFile = CameraCropHelper.get().getDefaultImagePath();
                }
                File file = new File(mDestFile);
                if (file.exists()) {
                    file.delete();
                }
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
                try {
                    Rect rect = mClipView.getClipRect();
                    if (mIsA4) {
                        int destDegress = 0;
                        if (mDirection.getDirection() == Direction.RIGHT) {
                            destDegress = 270;
                        } else if (mDirection.getDirection() == Direction.BOTTOM) {
                            destDegress = 180;
                        } else if (mDirection.getDirection() == Direction.LEFT) {
                            destDegress = 90;
                        }
                        BitmapUtil.saveBitMap(CameraCropActivity.this, mDestFile, data, rect.left, rect.top, rect.width(), rect.height(), true, destDegress);
                    } else {
                        BitmapUtil.saveBitMap(CameraCropActivity.this, mDestFile, data, rect.left, rect.top, rect.width(), rect.height(), true);
                    }
                    mIsOrientationEnable = false;
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    Intent intent = new Intent();
                    intent.putExtra(CameraCropHelper.IMAGE_PATH, mDestFile);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void showControlView(boolean isTop) {
        if (isTop) {
            mControlViewTop.setVisibility(View.VISIBLE);
            mControlViewBottom.setVisibility(View.GONE);
        } else {
            mControlViewBottom.setVisibility(View.VISIBLE);
            mControlViewTop.setVisibility(View.GONE);
        }
    }

    private void resetControlView() {
        if (mDirection.getDirection() == Direction.LEFT || mDirection.getDirection() == Direction.BOTTOM) {
            showControlView(true);
        } else {
            showControlView(false);
        }
    }
}
