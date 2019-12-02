package com.example.walker.myhencoder.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.walker.myhencoder.BuildConfig;
import com.example.walker.myhencoder.R;
import com.example.walker.myhencoder.base.BaseFragment;
import com.example.walker.myhencoder.base.activityresult.ActivityResultHelper;
import com.example.walker.myhencoder.demo.clipimage.CameraCropHelper;
import com.example.walker.myhencoder.demo.clipimage.ClipImageHelper;
import com.example.walker.myhencoder.util.FileUtil;
import com.liao.lizhi.chat.gift.view.GiftSelectDialog;
import com.walker.core.util.ToastUtils;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * @author walker zheng
 * @date 2019/3/15
 * @desc demo预演
 */
public class DemoPreviewFragment extends BaseFragment {
    public static final String KEY_FLAG_SHOW = "FLAG_SHOW";
    public static final int FLAG_SHOW_1 = 101;
    public static final int FLAG_SHOW_2 = 102;
    private int mFlagShow;

    //请求相机
    private static final int REQUEST_CAPTURE = 100;
    //请求相册
    private static final int REQUEST_PICK = 101;

    private ImageView imageView;
    //调用照相机返回图片文件
    private File tempFile;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Bundle data = getArguments();
        if (data != null) {
            mFlagShow = data.getInt(KEY_FLAG_SHOW, FLAG_SHOW_1);
        }
    }

    @Override
    protected void buildView(View baseView, Bundle savedInst) {
        if (mFlagShow == FLAG_SHOW_1) {
            baseView.findViewById(R.id.tvGiftPopup).setOnClickListener(v -> onShowGiftPopup());
        } else if (mFlagShow == FLAG_SHOW_2) {
            imageView = baseView.findViewById(R.id.ivPreview);
            baseView.findViewById(R.id.tvCameraCrop).setOnClickListener(v -> onCameraCrop());
            baseView.findViewById(R.id.tvImageClip).setOnClickListener(v -> onImageClip());
        }
    }

    private void onImageClip() {
        final BottomSheetDialog dialog = new BottomSheetDialog(mHoldActivity);
        dialog.setTitle("请选择图片来源");
        ListView listView = new ListView(mHoldActivity);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(mHoldActivity, android.R.layout.simple_list_item_1,
                new String[]{"相 机", "相  册", "取  消"});
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    onClipByCamera();
                } else if (i == 1) {
                    gotoPhoto();
                }
                dialog.dismiss();
            }
        });
        dialog.setContentView(listView);
        dialog.show();
    }

    private void onCameraCrop() {
        AndPermission.with(getHoldActivity())
                .runtime()
                .permission(Permission.Group.CAMERA)
                .onGranted(permissions -> CameraCropHelper.get().take(getHoldActivity(), "", 0.8f, "请对准取景框拍摄", (resultCode, data) -> {
                    if (resultCode == RESULT_OK) {
                        if (data != null) {
                            String filePath = data.getStringExtra(CameraCropHelper.IMAGE_PATH);
                            if (imageView != null && !TextUtils.isEmpty(filePath)) {
                                Bitmap bitmap = BitmapFactory.decodeFile(filePath);
                                imageView.setImageBitmap(bitmap);
                            }
                        }
                    }
                }))
                .onDenied(permissions -> ToastUtils.showLong("没有相机权限!"))
                .start();
    }

    private void onShowGiftPopup() {
//        View contentView = new GiftPopupView(getHoldActivity());
//        PopupWindow popupWindow = new PopupWindow(contentView,
//                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
//        popupWindow.setTouchable(true);
//        popupWindow.setTouchInterceptor((v, event) -> {
//            return false;
//            // 这里如果返回true的话，touch事件将被拦截
//            // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
//        });
//        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
//        popupWindow.setBackgroundDrawable(getResources().getDrawable(android.R.color.transparent));
//
//        popupWindow.showAtLocation(getHoldActivity().getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);

        GiftSelectDialog dialog = new GiftSelectDialog(getHoldActivity(), "旋风少年", "", 9898998);
        dialog.show();
    }

    @Override
    protected int getLayoutId() {
        switch (mFlagShow) {
            case FLAG_SHOW_1:
                return R.layout.frag_demo_gift;
            case FLAG_SHOW_2:
                return R.layout.frag_demo_clip_image;
            default:
                return 0;
        }
    }

    @Override
    protected void bindListener() {

    }

    @Override
    protected void unbindListener() {

    }

    /**
     * 跳转到相册
     */
    private void gotoPhoto() {
        //跳转到调用系统图库
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(intent, "请选择图片"), REQUEST_PICK);
    }


  private void onClipByCamera(){
      AndPermission.with(getHoldActivity())
              .runtime()
              .permission(Permission.Group.CAMERA)
              .onGranted(permissions -> gotoCamera())
              .onDenied(permissions -> ToastUtils.showLong("没有相机权限!"))
              .start();
  }

    /**
     * 跳转到照相机
     */
    private void gotoCamera() {
        //创建拍照存储的图片文件
        tempFile = new File(FileUtil.checkDirPath(Environment.getExternalStorageDirectory().getPath() + "/Walker/TakePhoto/"), System.currentTimeMillis() + ".jpg");

        //跳转到调用系统相机
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //设置7.0中共享文件，分享路径定义在xml/file_paths.xml
            intent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(getContext(), BuildConfig.APPLICATION_ID + ".fileprovider", tempFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);
            List<ResolveInfo> resInfoList = getContext().getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
            for (ResolveInfo resolveInfo : resInfoList) {
                String packageName = resolveInfo.activityInfo.packageName;
                getContext().grantUriPermission(packageName, contentUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
            }

        } else {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
        }
        startActivityForResult(intent, REQUEST_CAPTURE);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        switch (requestCode) {
            case REQUEST_CAPTURE: //调用系统相机返回
                if (resultCode == RESULT_OK) {
                    Uri uri =  Uri.fromFile(tempFile);
                    ClipImageHelper.get().clip(getHoldActivity(), uri, ClipImageHelper.get().getDefaultImagePath(), 0.8f, "", (resultCode1, data) -> {
                        if (resultCode1 == RESULT_OK) {
                            if (data != null) {
                                String filePath = data.getStringExtra(ClipImageHelper.CLIP_PATH);
                                if (imageView != null && !TextUtils.isEmpty(filePath)) {
                                    Bitmap bitmap = BitmapFactory.decodeFile(filePath);
                                    imageView.setImageBitmap(bitmap);
                                }
                            }
                        }
                    });
                }
                break;
            case REQUEST_PICK:  //调用系统相册返回
                if (resultCode == RESULT_OK) {
                    Uri uri = intent.getData();
                    ClipImageHelper.get().clip(getHoldActivity(),uri, ClipImageHelper.get().getDefaultImagePath(), 0.8f, "", (resultCode2, data) -> {
                        if (resultCode2 == RESULT_OK) {
                            if (data != null) {
                                String filePath = data.getStringExtra(ClipImageHelper.CLIP_PATH);
                                if (imageView != null && !TextUtils.isEmpty(filePath)) {
                                    Bitmap bitmap = BitmapFactory.decodeFile(filePath);
                                    imageView.setImageBitmap(bitmap);
                                }
                            }
                        }
                    });
                }
                break;
            default:
                break;
        }
    }

}
