package com.example.walker.myhencoder.fragment;

import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.example.walker.myhencoder.R;
import com.example.walker.myhencoder.base.BaseFragment;
import com.example.walker.myhencoder.view.DownloadButton;

import org.jetbrains.annotations.NotNull;

/**
 * @author Walker
 * @date on 2018/6/27 0027 下午 15:39
 * @email feitianwumu@163.com
 * @desc 通常展示
 */
public class CommonShowFragment extends BaseFragment {
    public static final String KEY_FLAG_SHOW = "FLAG_SHOW";
    public static final int FLAG_SHOW_1 = 1;
    public static final int FLAG_SHOW_2 = 2;
    public static final int FLAG_SHOW_3 = 3;
    public static final int FLAG_SHOW_4 = 4;
    public static final int FLAG_SHOW_5 = 5;

    private ValueAnimator mValueAnimator;
    private int mFlagShow;
    private DownloadButton downloadButton;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Bundle data = getArguments();
        if (data != null) {
            mFlagShow = data.getInt(KEY_FLAG_SHOW, FLAG_SHOW_1);
            if (mFlagShow == FLAG_SHOW_1) {
                mValueAnimator = ValueAnimator.ofFloat(0, 1);
                mValueAnimator.setDuration(2000);
                mValueAnimator.setInterpolator(new LinearInterpolator());
                mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        float progress = (float) animation.getAnimatedValue();
                        if (downloadButton != null) {
                            downloadButton.setProgress(progress);
                        }
                    }
                });
            }
        }
    }

    @Override
    protected void buildView(View baseView, Bundle savedInst) {
        if (mFlagShow == FLAG_SHOW_1) {
            downloadButton = (DownloadButton) baseView.findViewById(R.id.downloadButton);
            downloadButton.setTpaClickListener(new DownloadButton.OnTapClickListener() {
                @Override
                public void onStartDownload() {
                    downloadButton.setDownloadState(new DownloadButton.DownloadState.DOWNLOADING());
                    if (mValueAnimator != null) {
                        mValueAnimator.start();
                    }
                }

                @Override
                public void onDownloading() {

                }

                @Override
                public void onCompleteDownload() {

                }

                @Override
                public void onExtra() {

                }
            });
        }
    }

    @Override
    protected int getLayoutId() {
        switch (mFlagShow) {
            case FLAG_SHOW_1:
                return R.layout.frag_wave_progress_bar;
            case FLAG_SHOW_2:
                return R.layout.frag_signature_board;
            case FLAG_SHOW_3:
                return R.layout.frag_traffic_light;
            case FLAG_SHOW_4:
                return R.layout.frag_six_ball_loading;
            case FLAG_SHOW_5:
                return R.layout.frag_finger_point;
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
}
