package com.sohu.auto.treasure;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by zhipengyang on 2019/4/8.
 */

public abstract class LazyLoadBaseFragment extends BaseFragment {
    private boolean isFirstVisible = true;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        //setUserVisibleHint()有可能在fragment的生命周期外被调用，可能View都还未初始化
        if (rootView == null) {
            return;
        }
        if (isFirstVisible && getUserVisibleHint()) {
            lazyLoad();
            onFragmentVisibleChange(true);
            isFirstVisible = false;
            return;
        }
        if (getUserVisibleHint()) {
            onFragmentVisibleChange(true);
        } else {
            onFragmentVisibleChange(false);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        // setUserVisibleHint有可能在onCreateView之前调用，因此避免初始化后不进行正常加载
        // 同时需要getUserVisibleHint()可见性判断后再进行加载，避免Fragment还不可见时进行加载，懒加载失效
        if (isFirstVisible && getUserVisibleHint()) {
            lazyLoad();
            onFragmentVisibleChange(true);
            isFirstVisible = false;
        }
        beforeRootViewBind();
        return rootView;
    }

    @Override
    public void onDestroyView() {
        beforeRootViewUnBind();
        super.onDestroyView();
    }

    // 当rootView被绑定到Fragment之前的操作
    public void beforeRootViewBind() {

    }

    // 当rootView需要从Fragment解绑之前的操作
    public void beforeRootViewUnBind() {

    }
    /**
     * 可在该回调方法里进行一些ui显示与隐藏，比如加载框的显示和隐藏
     *
     * @param isVisible true  不可见 -> 可见
     *                  false 可见  -> 不可见
     */
    protected void onFragmentVisibleChange(boolean isVisible) {
    }

    protected abstract void lazyLoad();
}
