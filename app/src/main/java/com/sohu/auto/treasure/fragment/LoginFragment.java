package com.sohu.auto.treasure.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.TextView;

import com.sohu.auto.treasure.R;

/**
 * Created by zhipengyang on 2019/4/9.
 * <p>
 * 登录状态
 */

public class LoginFragment extends LazyLoadBaseFragment {
    TextView tv_logout;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_me_login;
    }

    @Override
    protected void onInitView(Bundle savedInstanceState) {
        tv_logout = rootView.findViewById(R.id.tv_logout);
        tv_logout.setOnClickListener(v -> {
            MeFragment fragment = (MeFragment) getParentFragment();
            fragment.checkIsLogin(false);
        });
    }

    @Override
    protected void lazyLoad() {

    }
}
