package com.sohu.auto.treasure.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sohu.auto.treasure.R;
import com.sohu.auto.treasure.activity.MyJoinActivity;
import com.sohu.auto.treasure.activity.MyPublishedActivity;
import com.sohu.auto.treasure.activity.TreasureListActivity;

/**
 * Created by zhipengyang on 2019/4/9.
 * <p>
 * 登录状态
 */

public class LoginFragment extends LazyLoadBaseFragment {
    TextView tv_logout;
    RelativeLayout rl_create;
    RelativeLayout rl_join;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_me_login;
    }

    @Override
    protected void onInitView(Bundle savedInstanceState) {
        tv_logout = rootView.findViewById(R.id.tv_logout);
        rl_create = rootView.findViewById(R.id.relative_create);
        rl_join = rootView.findViewById(R.id.relative_join);
        initListener();
    }

    private void initListener() {
        rl_create.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), MyPublishedActivity.class));
        });

        rl_join.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), MyJoinActivity.class));
        });

        tv_logout.setOnClickListener(v -> {
            MeFragment fragment = (MeFragment) getParentFragment();
            fragment.checkIsLogin(false);
        });
    }

    @Override
    protected void lazyLoad() {

    }
}
