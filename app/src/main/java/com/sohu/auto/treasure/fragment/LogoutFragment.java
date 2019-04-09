package com.sohu.auto.treasure.fragment;

import android.os.Bundle;
import android.widget.TextView;

import com.sohu.auto.treasure.R;

/**
 * Created by zhipengyang on 2019/4/9.
 * <p>
 * 退出状态
 */

public class LogoutFragment extends LazyLoadBaseFragment {

    TextView tv_login;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_me_logout;
    }

    @Override
    protected void onInitView(Bundle savedInstanceState) {
        tv_login = rootView.findViewById(R.id.tv_login);
        tv_login.setOnClickListener(v -> {
            MeFragment fragment = (MeFragment) getParentFragment();
            fragment.checkIsLogin(true);
        });
    }

    @Override
    protected void lazyLoad() {

    }
}
