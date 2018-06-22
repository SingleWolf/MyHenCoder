package com.example.walker.myhencoder.fragment;

import android.os.Bundle;
import android.view.View;

import com.example.walker.myhencoder.R;
import com.example.walker.myhencoder.base.BaseFragment;

/**
 * 画点
 * Created by Walker on 2017/7/11.
 */

public class DrawOvalFragment extends BaseFragment{
    /**
     * 获取实例
     *
     * @return BaseFragment
     */
    public static BaseFragment newInstance() {
        DrawOvalFragment fragment = new DrawOvalFragment();
        return fragment;
    }

    @Override
    protected void buildView(View baseView, Bundle savedInst) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.frag_draw_oval;
    }

    @Override
    protected void bindListener() {

    }

    @Override
    protected void unbindListener() {

    }
}
