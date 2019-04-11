package com.sohu.auto.treasure.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.sohu.auto.treasure.R;
import com.sohu.auto.treasure.adapter.EventAdapter;
import com.sohu.auto.treasure.entry.EventFeed;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aiyou on 2019/4/10.
 */

public class MyJoinActivity extends RxAppCompatActivity {

    private RecyclerView rvJoin;
    private EventAdapter mAdapter;

    private List<EventFeed> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_join);
        getData();
        initView(savedInstanceState);
    }

    private void initView(Bundle savedInstanceState) {
        rvJoin = findViewById(R.id.rv_my_join);
        rvJoin.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new EventAdapter();
        mAdapter.setOnItemClickListener((item, position) -> {
            Intent intent = new Intent(MyJoinActivity.this, TreasureListActivity.class);
            intent.putExtra("eventId", data.get(position).id);
            startActivity(intent);
        });
        rvJoin.setAdapter(mAdapter);

        mAdapter.setData(data);
    }

    private void getData() {
        /**
         * 测试数据
         * */
        List<EventFeed> list = new ArrayList<>();

        for(int i = 0; i < 3; i++) {
            EventFeed history = new EventFeed();
            history.id = String.valueOf(i);
            history.title = "自己参加的任务" + i;
            list.add(history);
        }

        data = list;
    }
}
