package com.example.walker.myhencoder.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.walker.myhencoder.R;
import com.example.walker.myhencoder.base.BaseFragment;
import com.example.walker.myhencoder.view.RulerView;

/**
 * @author Walker
 * @date on 2018/6/22 0022 下午 15:24
 * @email feitianwumu@163.com
 * @desc 刻度尺
 */
public class DrawRuleViewFragment extends BaseFragment {
    private RulerView rulerView;
    private Button btn;
    private EditText edt;

    @Override
    protected void buildView(View baseView, Bundle savedInst) {
        rulerView = (RulerView) baseView.findViewById(R.id.rulerView);
        btn = (Button) baseView.findViewById(R.id.btn);
        edt = (EditText) baseView.findViewById(R.id.edt);

        rulerView.setOnChooseResulterListener(new RulerView.OnChooseResulterListener() {
            @Override
            public void onEndResult(String result) {

            }

            @Override
            public void onScrollResult(String result) {

            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rulerView.computeScrollTo(Float.parseFloat(edt.getText().toString()));
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.frag_draw_rule_view;
    }

    @Override
    protected void bindListener() {

    }

    @Override
    protected void unbindListener() {

    }
}
