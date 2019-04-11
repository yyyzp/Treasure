package com.sohu.auto.treasure.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.sohu.auto.treasure.R;
import com.sohu.auto.treasure.adapter.TreasureWatchAdapter;
import com.sohu.auto.treasure.entry.TreasureHistory;
import com.sohu.auto.treasure.widget.CommonItemDecoration;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aiyou on 2019/4/10.
 */

public class TreasureListActivity extends RxAppCompatActivity {

    private RecyclerView rvTreasure;
    private TreasureWatchAdapter mAdapter;

    private List<TreasureHistory> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treasure_list);
        getTreasureList();
        initView(savedInstanceState);
    }

    private void initView(Bundle savedInstanceState) {
        rvTreasure = findViewById(R.id.rv_treasure);
        rvTreasure.setLayoutManager(new LinearLayoutManager(this));
        rvTreasure.addItemDecoration(new CommonItemDecoration(this, 2));

        mAdapter = new TreasureWatchAdapter();
        mAdapter.setOnItemClickListener((item, position) -> {
            Intent intent = new Intent(TreasureListActivity.this, TreasureDetailActivity.class);
            intent.putExtra("treasureId", data.get(position).id);
            startActivity(intent);
        });
        mAdapter.setData(data);

        rvTreasure.setAdapter(mAdapter);
    }

    private void getTreasureList() {
        /**
         * 测试数据
         * */
        List<TreasureHistory> list = new ArrayList<>();

        for(int i = 0; i < 5; i++) {
            TreasureHistory treasureHistory = new TreasureHistory();
            treasureHistory.id = 0;
            treasureHistory.name = "宝藏" + i;
            treasureHistory.location = "some where";
            treasureHistory.openTime = System.currentTimeMillis();
            treasureHistory.openMan = "坛哥" + i;
            list.add(treasureHistory);
        }

        data = list;
    }
}
