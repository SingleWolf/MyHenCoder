package com.example.walker.myhencoder.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.example.walker.myhencoder.R;
import com.example.walker.myhencoder.base.BaseFragment;

/**
 * @author Walker
 * @date on 2018/6/27 0027 下午 15:39
 * @email feitianwumu@163.com
 * @desc 通常展示
 */
public class CommonShowFragment extends BaseFragment {
    public static final String KEY_FLAG_SHOW = "FLAG_SHOW";
    public static final int FLAG_SHOW_1 = 1;

    private int mFlagShow;

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

    }

    @Override
    protected int getLayoutId() {
        switch (mFlagShow) {
            case FLAG_SHOW_1:
                return R.layout.frag_wave_progress_bar;
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