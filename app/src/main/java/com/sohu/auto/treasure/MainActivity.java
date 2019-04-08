package com.sohu.auto.treasure;


import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;


/**
 * Created by zhipengyang on 2019/4/8.
 */

public class MainActivity extends RxAppCompatActivity {
    public static final int TAB_TREASURE_FRAGMENT = 0;
    public static final int TAB_FIND_FRAGMENT = 1;
    public static final int TAB_ME_FRAGMENT = 2;
    private FragmentTabHost mTabHost;
    private TabWidget mTabWidget;
    private String[] mFragmentTags;
    private Class[] mFragments;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        mTabHost=findViewById(android.R.id.tabhost);
        mTabHost.setup(this,getSupportFragmentManager(),android.R.id.tabcontent);
        mTabHost.getTabWidget().setDividerDrawable(null); // 去掉分割线
        mTabWidget = mTabHost.getTabWidget();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mTabHost.setElevation(8);
        }
        for (int i = 0; i < mFragmentTags.length; i++) {
            // Tab按钮添加文字和图片
            TabHost.TabSpec tabSpec = mTabHost.newTabSpec(mFragmentTags[i]).setIndicator(getTabIndicatorView(i));
            // 添加Fragment
            mTabHost.addTab(tabSpec, mFragments[i], null);
        }
        setTabWidgetClickListener();
    }

    private void initView() {
        mFragmentTags =new String[]{"挖宝","埋宝","我的"};
        mFragments = new Class[]{
                TreasureFragment.class,
                FindFragment.class,
                MeFragment.class
        };
    }

    /**
     * 设置tab的点击事件
     */
    private void setTabWidgetClickListener() {
        mTabWidget.getChildTabViewAt(TAB_TREASURE_FRAGMENT).setOnClickListener(v -> {
            mTabHost.setCurrentTabByTag(mFragmentTags[TAB_TREASURE_FRAGMENT]);

        });

        mTabWidget.getChildTabViewAt(TAB_FIND_FRAGMENT).setOnClickListener(v -> {
            mTabHost.setCurrentTabByTag(mFragmentTags[TAB_FIND_FRAGMENT]);

        });
        mTabWidget.getChildTabViewAt(TAB_ME_FRAGMENT).setOnClickListener(v -> {
            mTabHost.setCurrentTabByTag(mFragmentTags[TAB_ME_FRAGMENT]);
        });

    }
    private View getTabIndicatorView(int index) {
        @SuppressLint("InflateParams")
        View view = getLayoutInflater().inflate(R.layout.view_tab_indicator, null);
        TextView tvTabText = (TextView) view.findViewById(R.id.tv_tab_main_activity_text);
        tvTabText.setText(mFragmentTags[index]);
        return view;
    }
}
