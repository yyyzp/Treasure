package com.sohu.auto.treasure.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.sohu.auto.treasure.R;

/**
 * Created by zhipengyang on 2019/4/8.
 */

public class MeFragment extends LazyLoadBaseFragment {
    boolean isLogin;


    @Override
    protected void lazyLoad() {

    }


    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_me;
    }

    @Override
    protected void onInitView(Bundle savedInstanceState) {
        checkIsLogin(true);
    }

    public void checkIsLogin(boolean isLogin) {
        FragmentManager manager=getChildFragmentManager();
        if(isLogin){
            manager.beginTransaction().replace(R.id.container,new LoginFragment()).commit();
        }else {
            manager.beginTransaction().replace(R.id.container,new LogoutFragment()).commit();
        }
    }
}
