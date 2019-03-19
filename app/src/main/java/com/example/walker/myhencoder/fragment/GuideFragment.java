package com.example.walker.myhencoder.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.walker.myhencoder.R;
import com.example.walker.myhencoder.adapter.GuideAdapter;
import com.example.walker.myhencoder.base.BaseFragment;
import com.example.walker.myhencoder.delegate.OnRecyclerItemClickListener;
import com.example.walker.myhencoder.model.SummaryBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 主目录向导页面
 * <p>
 * Created by Walker on 2017/7/10.
 */

public class GuideFragment extends BaseFragment {
    private GuideAdapter mAdapter;
    private List<SummaryBean> mData;

    /**
     * 获取实例
     *
     * @return BaseFragment
     */
    public static BaseFragment newInstance() {
        GuideFragment fragment = new GuideFragment();
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mData = getData();
        mAdapter = new GuideAdapter(mData);
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
                    DrawTestFragment fragment = (DrawTestFragment) Class.forName(summary.getClazz()).newInstance();
                    if (fragment == null) {
                        return;
                    }
                    Bundle data = new Bundle();
                    data.putInt("TYPE_TEST", summary.getType());
                    fragment.setArguments(data);
                    addFragment(fragment, summary.getDesc());
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
        return R.layout.frag_guide;
    }

    @Override
    protected void bindListener() {

    }

    @Override
    protected void unbindListener() {

    }

    private List<SummaryBean> getData() {
        List data = new ArrayList();
        data.add(new SummaryBean(DrawTestFragment.class.getName(), "自定义View 1-1 绘制基础", DrawTestFragment.TYPE_TEST_1));
        data.add(new SummaryBean(DrawTestFragment.class.getName(), "自定义View 1-2 Paint详解", DrawTestFragment.TYPE_TEST_2));
        data.add(new SummaryBean(DrawTestFragment.class.getName(), "自定义View 1-3 文字的绘制", DrawTestFragment.TYPE_TEST_3));
        data.add(new SummaryBean(DrawTestFragment.class.getName(), "自定义View 1-4 Canvas对绘制的辅助", DrawTestFragment.TYPE_TEST_4));
        data.add(new SummaryBean(DrawTestFragment.class.getName(), "自定义View 1-5 绘制顺序", DrawTestFragment.TYPE_TEST_5));
        data.add(new SummaryBean(DrawTestFragment.class.getName(), "自定义View 1-6 属性动画", DrawTestFragment.TYPE_TEST_6));
        data.add(new SummaryBean(DrawTestFragment.class.getName(), "参考借鉴", DrawTestFragment.TYPE_TEST_7));
        data.add(new SummaryBean(DrawTestFragment.class.getName(), "没事画俩下", DrawTestFragment.TYPE_TEST_8));
        data.add(new SummaryBean(DrawTestFragment.class.getName(), "demo预演", DrawTestFragment.TYPE_TEST_9));
        return data;
    }
}
