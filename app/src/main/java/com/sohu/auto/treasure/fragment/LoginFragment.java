package com.sohu.auto.treasure.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.sohu.auto.treasure.R;
import com.sohu.auto.treasure.activity.MyJoinActivity;
import com.sohu.auto.treasure.activity.MyPublishedActivity;
import com.sohu.auto.treasure.widget.Session;

/**
 * Created by zhipengyang on 2019/4/9.
 * <p>
 * 登录状态
 */

public class LoginFragment extends LazyLoadBaseFragment {
    private TextView tvLogout;
    private ImageView ivPic;
    private TextView tvName;
    private RelativeLayout rlCreate;
    private RelativeLayout rljoin;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_me_login;
    }

    @Override
    protected void onInitView(Bundle savedInstanceState) {
        tvLogout = rootView.findViewById(R.id.tv_logout);
        ivPic = rootView.findViewById(R.id.iv_pic);
        tvName = rootView.findViewById(R.id.tv_nickname);
        rlCreate = rootView.findViewById(R.id.relative_create);
        rljoin = rootView.findViewById(R.id.relative_join);

        Glide.with(this).load(Session.getInstance().mAvatar).apply(new RequestOptions().circleCrop()).into(ivPic);
        tvName.setText(Session.getInstance().mNickName);

        initListener();
    }

    private void initListener() {
        rlCreate.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), MyPublishedActivity.class));
        });

        rljoin.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), MyJoinActivity.class));
        });

        tvLogout.setOnClickListener(v -> {
            Session.getInstance().logout();
            MeFragment fragment = (MeFragment) getParentFragment();
            fragment.checkIsLogin(false);
        });
    }

    @Override
    protected void lazyLoad() {

    }
}
