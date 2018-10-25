package com.example.walker.myhencoder.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.dasu.blur.BlurConfig;
import com.dasu.blur.DBlur;
import com.dasu.blur.OnBlurListener;
import com.example.walker.myhencoder.R;
import com.example.walker.myhencoder.base.BaseFragment;
import com.example.walker.myhencoder.view.SignatureBoard;
import com.walker.core.util.DateTimeUtils;
import com.walker.core.util.StringBuilderUtils;
import com.walker.core.util.ToastUtils;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * @author Walker
 * @date on 2018/6/28 0028 上午 11:31
 * @email feitianwumu@163.com
 * @desc 签名画板
 */
public class SignatureBoardFragment extends BaseFragment implements View.OnClickListener {
    /**
     * view转换为bitmap
     */
    public static final int MSG_VIEW_2_BITMAP = 1;
    /**
     * 保存图片成功
     */
    public static final int MSG_SAVE_IMG_SUC = 2;
    /**
     * 保存图片失败
     */
    public static final int MSG_SAVE_IMG_ERR = 3;
    /**
     * 对图片进行高斯模糊处理成功
     */
    public static final int MSG_BLUR_IMG_SUC = 4;
    /**
     * 对图片进行高斯模糊处理失败
     */
    public static final int MSG_BLUR_IMG_ERR = 5;

    private SignatureBoard mViewSign;
    private Button mBtnClear;
    private Button mBtnSave;
    private CheckBox mCbSmooth;

    @Override
    protected void buildView(View baseView, Bundle savedInst) {
        mViewSign = (SignatureBoard) baseView.findViewById(R.id.viewSign);
        mViewSign.setSmoothType(true);
        mBtnClear = (Button) baseView.findViewById(R.id.btnClear);
        mBtnSave = (Button) baseView.findViewById(R.id.btnSave);
        mCbSmooth = (CheckBox) baseView.findViewById(R.id.cbSmooth);
        mCbSmooth.setOnCheckedChangeListener((buttonView, isChecked) -> mViewSign.setSmoothType(isChecked));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.frag_signature_board;
    }

    @Override
    protected void bindListener() {
        mBtnClear.setOnClickListener(this);
        mBtnSave.setOnClickListener(this);
    }

    @Override
    protected void unbindListener() {
        mBtnClear.setOnClickListener(null);
        mBtnSave.setOnClickListener(null);
    }

    private void onClear() {
        if (mViewSign != null) {
            mViewSign.clear();
        }
    }

    private void onStoragePermission() {
        AndPermission.with(this)
                .runtime()
                .permission(Permission.Group.STORAGE)
                .onGranted(permissions -> onSave())
                .onDenied(permissions -> ToastUtils.showLong("没有存储权限!"))
                .start();
    }


    private void onSave() {
        if (mViewSign == null) {
            return;
        }
        AsyncTask.THREAD_POOL_EXECUTOR.execute(() -> {
            Bitmap bitmap = view2Bitmap(mViewSign);
            if (bitmap == null) {
                return;
            }
            Message msg = mHandler.obtainMessage(MSG_VIEW_2_BITMAP, bitmap);
            mHandler.sendMessage(msg);
        });
    }

    private AlertDialog mShowDialog;

    private void showPreviewDialog(final Bitmap bitmap) {
        if (bitmap == null) {
            return;
        }
        if (mShowDialog == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            mShowDialog = builder.create();
            mShowDialog.setTitle("是否保存签名？");
            mShowDialog.setButton(DialogInterface.BUTTON_NEUTRAL, "暂不", (dialog, which) -> {

            });
            mShowDialog.setButton(DialogInterface.BUTTON_POSITIVE, "保存", (dialog, which) -> AsyncTask.THREAD_POOL_EXECUTOR.execute(() -> {
                String fileName = StringBuilderUtils.pliceStr("sign_", DateTimeUtils.getNormalDate(), ".png");
                if (saveBitmap2File(bitmap, fileName)) {
                    mHandler.sendEmptyMessage(MSG_SAVE_IMG_SUC);
                } else {
                    mHandler.sendEmptyMessage(MSG_SAVE_IMG_ERR);
                }
            }));
            mShowDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "高斯模糊", (dialog, which) -> AsyncTask.THREAD_POOL_EXECUTOR.execute(() -> DBlur.source(getActivity(), bitmap).radius(20).mode(BlurConfig.MODE_NATIVE).build()
                    .doBlur(new OnBlurListener() {
                        @Override
                        public void onBlurSuccess(Bitmap bitmap1) {
                            if (bitmap1 == null) {
                                return;
                            }
                            Message msg = mHandler.obtainMessage(MSG_BLUR_IMG_SUC, bitmap1);
                            mHandler.sendMessage(msg);
                        }

                        @Override
                        public void onBlurFailed() {
                            mHandler.sendEmptyMessage(MSG_BLUR_IMG_ERR);
                        }
                    })));
        }

        ImageView imageView = new ImageView(getContext());
        imageView.setImageBitmap(bitmap);
        mShowDialog.setView(imageView);

        if (mShowDialog.isShowing() == false) {
            mShowDialog.show();
        }
    }

    private Bitmap view2Bitmap(View view) {
        view.setDrawingCacheEnabled(true);
        view.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        view.setDrawingCacheBackgroundColor(Color.WHITE);

        int width = view.getWidth();
        int height = view.getHeight();
        view.setDrawingCacheEnabled(true);
        Bitmap goalBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(goalBitmap);
        canvas.drawColor(Color.WHITE);//如果不设置canvas画布为白色，则生成透明
        view.layout(0, 0, width, height);
        view.draw(canvas);

        view.destroyDrawingCache();

        return goalBitmap;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnClear:
                onClear();
                break;
            case R.id.btnSave:
                onStoragePermission();
                break;
            default:
                break;
        }
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_VIEW_2_BITMAP:
                    Bitmap bitmap = (Bitmap) msg.obj;
                    showPreviewDialog(bitmap);
                    break;
                case MSG_SAVE_IMG_SUC:
                    ToastUtils.showLong("图片保存成功！");
                    break;
                case MSG_SAVE_IMG_ERR:
                    ToastUtils.showLong("图片保存失败！");
                    break;
                case MSG_BLUR_IMG_SUC:
                    ToastUtils.showLong("模糊处理成功！");
                    Bitmap bitmap_2 = (Bitmap) msg.obj;
                    showPreviewDialog(bitmap_2);
                    break;
                case MSG_BLUR_IMG_ERR:
                    ToastUtils.showLong("模糊处理失败！");
                    break;
                default:
                    break;
            }
        }
    };


    private boolean saveBitmap2File(Bitmap bm, String fileName) {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) == false) {
            return false;
        }

        boolean result = true;
        String path = Environment.getExternalStorageDirectory() + "/Walker/myHenCode/sign";
        File dirFile = new File(path);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }

        File myCaptureFile = new File(path, fileName);
        try {
            myCaptureFile.createNewFile();
            BufferedOutputStream bos = null;
            bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
            bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
            bos.flush();
            bos.close();
        } catch (Exception e) {
            result = false;
            e.printStackTrace();
        }
        return result;
    }

}
