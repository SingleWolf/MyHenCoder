package com.example.walker.myhencoder.fragment;

import android.os.Bundle;
import android.view.View;

import com.example.walker.myhencoder.R;
import com.example.walker.myhencoder.base.BaseFragment;
import com.example.walker.myhencoder.view.DYLoadingView;
import com.example.walker.myhencoder.view.FlaskView;

/**
 * @author Walker
 * @e-mail feitianwumu@163.com
 * @date on 2018/9/18
 * @summary 加载控件集锦
 */
public class LoadingFragment extends BaseFragment {

    private DYLoadingView mLoading_1;
    private FlaskView mLoading_2;

    @Override
    protected void buildView(View baseView, Bundle savedInst) {
        mLoading_1 = (DYLoadingView) baseView.findViewById(R.id.loading_1);
        mLoading_2 = (FlaskView) baseView.findViewById(R.id.loading_2);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.frag_loading;
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
        mLoading_1.start();
        mLoading_2.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        mLoading_1.stop();
        mLoading_2.stop();
    }
}
