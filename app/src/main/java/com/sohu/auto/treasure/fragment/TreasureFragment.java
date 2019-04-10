package com.sohu.auto.treasure.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.sohu.auto.treasure.activity.CreateTreasureActivity;
import com.sohu.auto.treasure.activity.TreasureLocationActivity;
import com.sohu.auto.treasure.R;
import com.sohu.auto.treasure.activity.SearchTreasureActivity;
import com.sohu.auto.treasure.adapter.BaseAdapter;
import com.sohu.auto.treasure.adapter.UserActionAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by zhipengyang on 2019/4/8.
 */

public class TreasureFragment extends LazyLoadBaseFragment {

    TextView tvOfficalAction;
    TextView tvCreateAction;
    RecyclerView recyclerView;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_treasure;
    }

    @Override
    protected void onInitView(Bundle savedInstanceState) {
        tvOfficalAction = rootView.findViewById(R.id.tv_offical_action);
        tvCreateAction = rootView.findViewById(R.id.tv_create_action);
        recyclerView = rootView.findViewById(R.id.recyclerview);
        UserActionAdapter adapter = new UserActionAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        initData(adapter);
        initListener();
    }

    private void initData(BaseAdapter adapter) {
        List<String> list = new ArrayList<>();
        list.add("活动1");
        list.add("活动2");
        list.add("活动3");
        list.add("活动4");
        adapter.setData(list);
        adapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View item, int position) {
                startActivity(new Intent(getActivity(), SearchTreasureActivity.class));
            }
        });
    }

    private void initListener() {
        tvOfficalAction.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), SearchTreasureActivity.class));
        });
        tvCreateAction.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), CreateTreasureActivity.class));
        });
    }

    @Override
    protected void lazyLoad() {

    }


}
