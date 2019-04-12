package com.sohu.auto.treasure.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import com.sohu.auto.treasure.R;
import com.sohu.auto.treasure.adapter.TreasureWatchAdapter;
import com.sohu.auto.treasure.entry.TreasureHistory;
import com.sohu.auto.treasure.net.NetError;
import com.sohu.auto.treasure.net.NetSubscriber;
import com.sohu.auto.treasure.net.TreasureApi;
import com.sohu.auto.treasure.net.response.TreasureHistoryResponse;
import com.sohu.auto.treasure.utils.TransformUtils;
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

    // 0 从Publish进入， 1 从Join进入
    private int from;
    // 活动id
    private String eventId;

    private List<TreasureHistory> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treasure_list);

        Intent intent = getIntent();
        from = intent.getIntExtra("from", 0);
        eventId = intent.getStringExtra("eventId");

        getTreasureList();
        initView(savedInstanceState);
    }

    private void initView(Bundle savedInstanceState) {
        rvTreasure = findViewById(R.id.rv_treasure);
        rvTreasure.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new TreasureWatchAdapter();
        mAdapter.setFrom(from);
        mAdapter.setOnItemClickListener((item, position) -> {
            Intent intent = new Intent(TreasureListActivity.this, TreasureDetailActivity.class);
            intent.putExtra("treasure", data.get(position).convertToTreasure());
            startActivity(intent);
        });

        rvTreasure.setAdapter(mAdapter);
    }

    private void getTreasureList() {
        if (TextUtils.isEmpty(eventId))
            return;

        if (from == 0) {
            TreasureApi
                    .getInstance()
                    .getPublishedTreasure(eventId)
                    .compose(TransformUtils.defaultNetConfig(this))
                    .subscribe(new NetSubscriber<TreasureHistoryResponse>() {
                        @Override
                        public void onSuccess(TreasureHistoryResponse treasureHistoryResponse) {
                            data = treasureHistoryResponse.data;
                            mAdapter.setData(data);
                        }

                        @Override
                        public void onFailure(NetError error) {

                        }
                    });
        } else {
            TreasureApi
                    .getInstance()
                    .getJoinedTreasure(eventId)
                    .compose(TransformUtils.defaultNetConfig(this))
                    .subscribe(new NetSubscriber<TreasureHistoryResponse>() {
                        @Override
                        public void onSuccess(TreasureHistoryResponse treasureHistoryResponse) {
                            data = treasureHistoryResponse.data;
                            mAdapter.setData(data);
                        }

                        @Override
                        public void onFailure(NetError error) {

                        }
                    });
        }
    }
}
