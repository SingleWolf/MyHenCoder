package com.example.walker.myhencoder;

import android.os.Bundle;
import android.widget.TextView;

import com.example.walker.myhencoder.base.BaseFragActivity;
import com.example.walker.myhencoder.base.BaseFragment;
import com.example.walker.myhencoder.fragment.GuideFragment;

/**
 * Android进阶 自定义View练习
 */
public class MainActivity extends BaseFragActivity {

    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected int getFragContentId() {
        return R.id.fragment_content;
    }

    @Override
    protected void initialization(Bundle savedInstanceState) {
    }

    @Override
    protected void bindListener() {

    }

    @Override
    protected void unbindListener() {

    }

    @Override
    protected BaseFragment getFirstFragment() {
        return GuideFragment.newInstance();
    }
}
