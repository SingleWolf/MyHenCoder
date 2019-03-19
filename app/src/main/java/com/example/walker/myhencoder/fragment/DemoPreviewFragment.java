package com.example.walker.myhencoder.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.example.walker.myhencoder.R;
import com.example.walker.myhencoder.base.BaseFragment;
import com.liao.lizhi.chat.gift.view.GiftPopupView;
import com.liao.lizhi.chat.gift.view.GiftSelectDialog;

/**
 * @author walker zheng
 * @date 2019/3/15
 * @desc demo预演
 */
public class DemoPreviewFragment extends BaseFragment {
    public static final String KEY_FLAG_SHOW = "FLAG_SHOW";
    public static final int FLAG_SHOW_1 = 101;
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
        if (mFlagShow == FLAG_SHOW_1) {
            baseView.findViewById(R.id.tvGiftPopup).setOnClickListener(v -> onShowGiftPopup());
        }
    }

    private void onShowGiftPopup() {
//        View contentView = new GiftPopupView(getHoldActivity());
//        PopupWindow popupWindow = new PopupWindow(contentView,
//                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
//        popupWindow.setTouchable(true);
//        popupWindow.setTouchInterceptor((v, event) -> {
//            return false;
//            // 这里如果返回true的话，touch事件将被拦截
//            // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
//        });
//        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
//        popupWindow.setBackgroundDrawable(getResources().getDrawable(android.R.color.transparent));
//
//        popupWindow.showAtLocation(getHoldActivity().getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);

        GiftSelectDialog dialog = new GiftSelectDialog(getHoldActivity(), "旋风少年", "", 9898998);
        dialog.show();
    }

    @Override
    protected int getLayoutId() {
        switch (mFlagShow) {
            case FLAG_SHOW_1:
                return R.layout.frag_demo_gift;
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
