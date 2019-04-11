package com.sohu.auto.treasure.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.sohu.auto.treasure.R;
import com.sohu.auto.treasure.adapter.EventAdapter;
import com.sohu.auto.treasure.entry.EventHistory;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aiyou on 2019/4/10.
 */

public class MyPublishedActivity extends RxAppCompatActivity {

    private RecyclerView rvMyEvent;
    private EventAdapter mAdapter;

    private List<EventHistory> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_published);
        getData();
        initView(savedInstanceState);
    }

    private void initView(Bundle savedInstanceState) {
        rvMyEvent = findViewById(R.id.rv_my_event);
        rvMyEvent.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new EventAdapter();
        mAdapter.setOnItemClickListener((item, position) -> {
            Intent intent = new Intent(MyPublishedActivity.this, TreasureListActivity.class);
            intent.putExtra("eventId", data.get(position).id);
            startActivity(intent);
        });
        rvMyEvent.setAdapter(mAdapter);

        mAdapter.setData(data);
    }

    private void getData() {
        /**
         * 测试数据
         * */
        List<EventHistory> list = new ArrayList<>();

        for(int i = 0; i < 3; i++) {
            EventHistory history = new EventHistory();
            history.id = i;
            history.title = "用户发布的任务" + i;
            list.add(history);
        }

        data = list;
    }
}