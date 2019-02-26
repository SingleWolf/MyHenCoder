package com.example.walker.myhencoder.fragment;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.animation.PathInterpolatorCompat;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.example.walker.myhencoder.R;
import com.example.walker.myhencoder.base.BaseFragment;
import com.example.walker.myhencoder.view.PaperPlaneView;
import com.walker.core.util.DisplayUtils;

/**
 * @author walker zheng
 * @date 2019/2/26
 * @desc 纸飞机
 */
public class AirplaneFragment extends BaseFragment {

    private ImageView mImgWhiteCloud;
    private PaperPlaneView mPlaneLayout;

    private Context mContext;
    private ObjectAnimator objCloudAnim;
    private TranslateAnimation planeAnimation;

    private float iconX, iconY;
    //设置飞机是否已点击，如果为true，则另一个飞机不可点击
    private boolean mIsClick = false;

    @Override
    protected void buildView(View baseView, Bundle savedInst) {
        mImgWhiteCloud = baseView.findViewById(R.id.img_white_cloud);
        mPlaneLayout = baseView.findViewById(R.id.plane_layout);

        mContext = getHoldActivity();
        //初始化动画
        initAnimation();
        initListener();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.frag_airplane;
    }

    @Override
    protected void bindListener() {

    }

    @Override
    protected void unbindListener() {

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    private void initAnimation() {
        //设定纸飞机进入的位置
        initPlaneEnterAnimation();
        //飞机进入后做上下浮动
        initPlaneAnimation();
        //云彩循环从屏幕右侧飘到屏幕左侧
        initCloudAnimation();
    }

    //设定纸飞机进入的位置
    private void initPlaneEnterAnimation() {
        for (int i = 0; i < 4; i++) {
            final int temp = i;
            mPlaneLayout.post(new Runnable() {
                @Override
                public void run() {
                    //下面的值根据用户自己设定
                    if (temp == 0) {
                        mPlaneLayout.addHeart(
                                150, 200, 0);
                    }
                    if (temp == 1) {
                        mPlaneLayout.addHeart(
                                DisplayUtils.getDisplaySize(getHoldActivity()).x - 150, 280, 1);
                    }
                    if (temp == 2) {
                        mPlaneLayout.addHeart(
                                30, 340, 2);
                    }
                    if (temp == 3) {
                        mPlaneLayout.addHeart(
                                DisplayUtils.getDisplaySize(getHoldActivity()).x - 280, 340, 3);
                    }
                }
            });
        }
    }

    //飞机进入后做上下浮动
    private void initPlaneAnimation() {
        planeAnimation = new TranslateAnimation(0, 0, -10, 10);
        planeAnimation.setDuration(1000);
        planeAnimation.setRepeatCount(Animation.INFINITE);
        planeAnimation.setRepeatMode(Animation.REVERSE);
        mPlaneLayout.setAnimation(planeAnimation);
        planeAnimation.start();
    }

    //云彩循环从屏幕右侧飘到屏幕左侧
    private void initCloudAnimation() {
        if (objCloudAnim == null) {
            objCloudAnim = ObjectAnimator
                    .ofFloat(mImgWhiteCloud, "translationX",
                            DisplayUtils.getDisplaySize(getHoldActivity()).x - 50, -DisplayUtils.getDisplaySize(getHoldActivity()).x);
            // 设置持续时间
            objCloudAnim.setDuration(5000);
            // 设置循环播放
            objCloudAnim.setRepeatCount(
                    ObjectAnimator.INFINITE);
        }
        objCloudAnim.start();
    }

    private void initListener() {
        mPlaneLayout.setOnImageClickListener(new PaperPlaneView.OnImageListener() {
            @Override
            public void onClick(ImageView v) {
                if (!mIsClick) {
                    mIsClick = true;
                    iconX = v.getX();
                    iconY = v.getY();
                    //当点击某一个纸飞机时，飞机会有一个飞出动画
                    planeOutAnimation(v);
                }
            }
        });
    }

    /**
     * 飞机飞出动画
     */
    private void planeOutAnimation(final View iconView) {
        AnimatorSet flyUpAnim = new AnimatorSet();
        flyUpAnim.setDuration(600);

        ObjectAnimator transX = ObjectAnimator
                .ofFloat(iconView, "translationX",
                        iconView.getX(),
                        DisplayUtils.getDisplaySize(getHoldActivity()).x * 2);
        ObjectAnimator transY = ObjectAnimator
                .ofFloat(iconView, "translationY",
                        0,
                        -DisplayUtils.getDisplaySize(getHoldActivity()).y * 2);
        transY.setInterpolator(PathInterpolatorCompat
                .create(0.7f, 1f));
        ObjectAnimator rotation = ObjectAnimator
                .ofFloat(iconView, "rotation", -45, 0);
        rotation.setInterpolator(new DecelerateInterpolator());
        ObjectAnimator rotationX = ObjectAnimator
                .ofFloat(iconView, "rotationX", 0, 60);
        rotationX.setInterpolator(
                new DecelerateInterpolator());

        flyUpAnim.playTogether(transX, transY, rotationX,
                ObjectAnimator
                        .ofFloat(iconView, "scaleX", 1, 0.3f),
                ObjectAnimator
                        .ofFloat(iconView, "scaleY", 1, 0.3f),
                rotation
        );
        flyUpAnim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                // 飞机飞入动画
                downPlaneAnimation(iconView);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        flyUpAnim.start();
    }

    /**
     * 飞机飞入动画
     */
    private void downPlaneAnimation(final View iconView) {
        final int offDistX = -iconView.getRight();
        final int offDistY = (int) -DisplayUtils.dp2px(getHoldActivity(), 10);
        AnimatorSet flyDownAnim = new AnimatorSet();
        flyDownAnim.setDuration(500);
        ObjectAnimator transX1 = ObjectAnimator
                .ofFloat(iconView, "translationX",
                        DisplayUtils.getDisplaySize(getHoldActivity()).x, offDistX);
        ObjectAnimator transY1 = ObjectAnimator
                .ofFloat(iconView, "translationY",
                        -DisplayUtils.getDisplaySize(getHoldActivity()).y,
                        offDistY);
        transY1.setInterpolator(
                PathInterpolatorCompat.create(0.1f, 1f));
        ObjectAnimator rotation1 = ObjectAnimator
                .ofFloat(iconView, "rotation",
                        iconView.getRotation(), 0);
        rotation1.setInterpolator(
                new AccelerateInterpolator());
        flyDownAnim.playTogether(transX1, transY1,
                ObjectAnimator
                        .ofFloat(iconView, "scaleX", 0.3f, 0.9f),
                ObjectAnimator
                        .ofFloat(iconView, "scaleY", 0.3f, 0.9f),
                rotation1
        );
        flyDownAnim.addListener(
                new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        iconView.setRotationY(180);
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mIsClick = false;
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });

        AnimatorSet flyInAnim = new AnimatorSet();
        flyInAnim.setDuration(500);
        flyInAnim.setInterpolator(
                new DecelerateInterpolator());
        ObjectAnimator tranX2 = ObjectAnimator
                .ofFloat(iconView, "translationX",
                        offDistX, iconX);
        ObjectAnimator tranY2 = ObjectAnimator
                .ofFloat(iconView, "translationY",
                        offDistY, iconY);
        ObjectAnimator rotationX2 = ObjectAnimator
                .ofFloat(iconView, "rotationX", 30, 0);
        flyInAnim.playTogether(tranX2, tranY2, rotationX2,
                ObjectAnimator.ofFloat(iconView, "scaleX", 0.9f, 1f),
                ObjectAnimator.ofFloat(iconView, "scaleY", 0.9f, 1f));
        flyInAnim.setStartDelay(100);
        flyInAnim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                iconView.setRotationY(0);
            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        AnimatorSet mFlyAnimator = new AnimatorSet();
        mFlyAnimator.playSequentially(flyDownAnim, flyInAnim);
        mFlyAnimator.start();
    }
}
