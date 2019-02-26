package com.example.walker.myhencoder.fragment;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.walker.myhencoder.R;
import com.example.walker.myhencoder.adapter.DrawTestAdapter;
import com.example.walker.myhencoder.base.BaseFragment;
import com.example.walker.myhencoder.delegate.OnRecyclerItemClickListener;
import com.example.walker.myhencoder.model.SummaryBean;
import com.example.walker.myhencoder.view.DrawCanvasHelp;
import com.example.walker.myhencoder.view.DrawChameleon;
import com.example.walker.myhencoder.view.DrawFlipboardView;

import java.util.ArrayList;
import java.util.List;

/**
 * 绘制练习
 * Created by Walker on 2017/7/11.
 */

public class DrawTestFragment extends BaseFragment {
    public static int TYPE_TEST_1 = 1;
    public static int TYPE_TEST_2 = 2;
    public static int TYPE_TEST_3 = 3;
    public static int TYPE_TEST_4 = 4;
    public static int TYPE_TEST_5 = 5;
    public static int TYPE_TEST_6 = 6;
    public static int TYPE_TEST_7 = 7;
    public static int TYPE_TEST_8 = 8;

    private DrawTestAdapter mAdapter;
    private List<SummaryBean> mData;
    private int mTypeTest;

    /**
     * 获取实例
     *
     * @param type_test 类型
     * @return BaseFragment
     */
    public static DrawTestFragment newInstance(int type_test) {
        DrawTestFragment fragment = new DrawTestFragment();
        Bundle data = new Bundle();
        data.putInt("TYPE_TEST", type_test);
        fragment.setArguments(data);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mTypeTest = getArguments().getInt("TYPE_TEST", TYPE_TEST_1);
        mData = getData();
        mAdapter = new DrawTestAdapter(mData);
    }

    @Override
    protected void buildView(View baseView, Bundle savedInst) {

        RecyclerView recyclerView = (RecyclerView) baseView.findViewById(R.id.recyclerView);
        recyclerView.setAdapter(mAdapter);
        recyclerView.addOnItemTouchListener(new OnRecyclerItemClickListener(recyclerView) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                int pos = vh.getLayoutPosition();
                try {
                    SummaryBean summary = mData.get(pos);
                    if (summary.getType() == 0) {
                        BaseFragment fragment = (BaseFragment) Class.forName(summary.getClazz()).newInstance();
                        if (fragment == null) {
                            return;
                        }
                        addFragment(fragment, summary.getDesc());
                    } else {
                        CommonShowFragment fragment = (CommonShowFragment) Class.forName(summary.getClazz()).newInstance();
                        if (fragment == null) {
                            return;
                        }
                        Bundle data = new Bundle();
                        data.putInt(CommonShowFragment.KEY_FLAG_SHOW, summary.getType());
                        fragment.setArguments(data);
                        addFragment(fragment, summary.getDesc());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onItemLongClick(RecyclerView.ViewHolder vh) {

            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.frag_draw_test;
    }

    @Override
    protected void bindListener() {

    }

    @Override
    protected void unbindListener() {

    }

    private List<SummaryBean> getData() {
        List data = new ArrayList();
        switch (mTypeTest) {
            case 1:
                data.add(new SummaryBean(DrawCircleFragment.class.getName(), "画圆基础", 0));
                data.add(new SummaryBean(DrawRectFragment.class.getName(), "画矩形基础", 0));
                data.add(new SummaryBean(DrawPointFragment.class.getName(), "画点基础", 0));
                data.add(new SummaryBean(DrawOvalFragment.class.getName(), "画椭圆基础", 0));
                data.add(new SummaryBean(DrawLineFragment.class.getName(), "画线基础", 0));
                data.add(new SummaryBean(DrawRoundRectFragment.class.getName(), "画圆角矩形基础", 0));
                data.add(new SummaryBean(DrawArcFragment.class.getName(), "画弧形或扇形基础", 0));
                data.add(new SummaryBean(DrawPathFragment.class.getName(), "基于Path自定义图形", 0));
                data.add(new SummaryBean(DrawBitmapFragment.class.getName(), "根据bitmap绘制", 0));
                data.add(new SummaryBean(DrawTextFragment.class.getName(), "绘制文字基础", 0));
                data.add(new SummaryBean(DrawCakeFragment.class.getName(), "练习-饼状图", 0));
                break;
            case 2:
                data.add(new SummaryBean(DrawShaderFragment.class.getName(), "设置颜色着色器", 0));
                break;
            case 3:
                data.add(new SummaryBean(DrawTextMoreFragment.class.getName(), "更多文字绘制技巧", 0));
                break;
            case 4:
                data.add(new SummaryBean(DrawCanvasHelpFragment.class.getName(), "Canvas对绘制的辅助", 0));
                data.add(new SummaryBean(DrawFlipboardFragment.class.getName(), "翻页效果", 0));
                break;
            case 5:
                data.add(new SummaryBean(DrawOrderFragment.class.getName(), "绘制顺序", 0));
                break;
            case 6:
                data.add(new SummaryBean(DrawAnimator1Fragment.class.getName(), "ViewPropertyAnimator", 0));
                data.add(new SummaryBean(DrawAnimator2Fragment.class.getName(), "ObjectAnimator", 0));
                data.add(new SummaryBean(DrawChameleonFragment.class.getName(), "TypeEvaluator", 0));
                data.add(new SummaryBean(DrawFallingballFragment.class.getName(), "自定义TypeEvaluator", 0));
                data.add(new SummaryBean(DrawPropertyValuesFragment.class.getName(), "PropertyValuesHolder", 0));
                data.add(new SummaryBean(DrawAnimatorSetFragment.class.getName(), "AnimatorSet ", 0));
                data.add(new SummaryBean(DrawKeyframeFragment.class.getName(), "PropertyValuesHolders.ofKeyframe()", 0));
                break;
            case 7:
                data.add(new SummaryBean(DrawRuleViewFragment.class.getName(), "刻度尺", 0));
                data.add(new SummaryBean(LoadingFragment.class.getName(), "加载", 0));
                break;
            case 8:
                data.add(new SummaryBean(CommonShowFragment.class.getName(), "进度条", CommonShowFragment.FLAG_SHOW_1));
                data.add(new SummaryBean(SignatureBoardFragment.class.getName(), "签名画板", 0));
                data.add(new SummaryBean(CommonShowFragment.class.getName(), "红绿灯", CommonShowFragment.FLAG_SHOW_3));
                data.add(new SummaryBean(CommonShowFragment.class.getName(), "六球加载", CommonShowFragment.FLAG_SHOW_4));
                data.add(new SummaryBean(CommonShowFragment.class.getName(), "指纹跟踪", CommonShowFragment.FLAG_SHOW_5));
                break;
            default:
                break;
        }
        return data;
    }
}
