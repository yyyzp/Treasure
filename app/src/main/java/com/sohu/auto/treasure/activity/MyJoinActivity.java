package com.sohu.auto.treasure.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.sohu.auto.treasure.R;
import com.sohu.auto.treasure.adapter.EventAdapter;
import com.sohu.auto.treasure.entry.EventFeed;
import com.sohu.auto.treasure.net.NetError;
import com.sohu.auto.treasure.net.NetSubscriber;
import com.sohu.auto.treasure.net.TreasureApi;
import com.sohu.auto.treasure.net.response.EventFeedResponse;
import com.sohu.auto.treasure.utils.TransformUtils;
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
            intent.putExtra("from", 1);
            startActivity(intent);
        });
        rvJoin.setAdapter(mAdapter);
    }

    private void getData() {
        TreasureApi
                .getInstance()
                .getMyJoined()
                .compose(TransformUtils.defaultNetConfig(this))
                .subscribe(new NetSubscriber<EventFeedResponse>() {
                    @Override
                    public void onSuccess(EventFeedResponse eventFeedResponse) {
                        data = eventFeedResponse.data;
                        mAdapter.setData(data);
                    }

                    @Override
                    public void onFailure(NetError error) {

                    }
                });
    }
}
