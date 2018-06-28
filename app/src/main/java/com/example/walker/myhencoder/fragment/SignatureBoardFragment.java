package com.example.walker.myhencoder.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.example.walker.myhencoder.R;
import com.example.walker.myhencoder.base.BaseFragment;
import com.example.walker.myhencoder.view.SignatureBoard;

/**
 * @author Walker
 * @date on 2018/6/28 0028 上午 11:31
 * @email feitianwumu@163.com
 * @desc 签名画板
 */
public class SignatureBoardFragment extends BaseFragment implements View.OnClickListener {
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
        mCbSmooth.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mViewSign.setSmoothType(isChecked);
            }
        });
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

    private void onSave() {
        if (mViewSign == null) {
            return;
        }
        Bitmap bitmap = view2Bitmap(mViewSign);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        AlertDialog dialog = builder.create();
        dialog.setTitle("是否保存签名？");
        ImageView imageView = new ImageView(getContext());
        imageView.setImageBitmap(bitmap);
        dialog.setView(imageView);
        dialog.setButton(DialogInterface.BUTTON_NEUTRAL, "暂不", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "保存", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //TODO 保存bitmap到文件
            }
        });
        dialog.show();
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
                onSave();
                break;
            default:
                break;
        }
    }
}
