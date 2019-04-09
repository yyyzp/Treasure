package com.sohu.auto.treasure.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by zhipengyang on 2019/4/8.
 */

public abstract class BaseFragment extends Fragment {

    protected View rootView;

    public View findViewById(@IdRes int id) {
        return rootView.findViewById(id);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(getLayoutResource(), container, false);
            onInitView(savedInstanceState);
        }

        ViewGroup parentView = (ViewGroup) rootView.getParent();
        if (parentView != null)
            parentView.removeView(rootView);

        return rootView;
    }

    protected abstract int getLayoutResource();

    protected abstract void onInitView(Bundle savedInstanceState);

    public String getName() {
        return BaseFragment.class.getName();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }


}
