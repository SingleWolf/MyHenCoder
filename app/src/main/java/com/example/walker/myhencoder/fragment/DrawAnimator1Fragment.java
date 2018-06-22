package com.example.walker.myhencoder.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.walker.myhencoder.R;
import com.example.walker.myhencoder.base.BaseFragment;

/**
 * @author Walker
 * @date on 2018/6/13 0013 下午 16:41
 * @email feitianwumu@163.com
 * @desc ViewPropertyAnimator
 */
public class DrawAnimator1Fragment extends BaseFragment implements View.OnClickListener {
    private ImageView imageView;
    private Button btnFly;
    float x;
    float y;

    @Override
    protected void buildView(View baseView, Bundle savedInst) {
        imageView = (ImageView) baseView.findViewById(R.id.ivBatman);
        x = imageView.getX();
        y = imageView.getY();
        btnFly = (Button) baseView.findViewById(R.id.btnFly);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.frag_draw_animator_1;
    }

    @Override
    protected void bindListener() {
        btnFly.setOnClickListener(this);
    }

    @Override
    protected void unbindListener() {
        btnFly.setOnClickListener(null);
    }

    @Override
    public void onClick(View view) {
        imageView.animate().translationX(200).translationY(200).scaleX(2).scaleY(2).withEndAction(new Runnable() {
            @Override
            public void run() {
                imageView.animate().translationX(-200).translationY(-200).scaleX(0.75f).scaleY(0.75f).setStartDelay(500);
            }
        });
    }
}
