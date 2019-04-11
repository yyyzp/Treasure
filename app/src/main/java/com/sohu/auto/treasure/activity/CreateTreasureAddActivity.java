package com.sohu.auto.treasure.activity;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.sohu.auto.treasure.R;
import com.sohu.auto.treasure.adapter.TreasureToAddAdapter;
import com.sohu.auto.treasure.entry.Treasure;
import com.sohu.auto.treasure.entry.TreasureEvent;
import com.sohu.auto.treasure.net.NetError;
import com.sohu.auto.treasure.net.NetSubscriber;
import com.sohu.auto.treasure.net.TreasureApi;
import com.sohu.auto.treasure.utils.ToastUtils;
import com.sohu.auto.treasure.utils.TransformUtils;
import com.sohu.auto.treasure.widget.CommonItemDecoration;
import com.sohu.auto.treasure.widget.SHAutoActionbar;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

/**
 * Created by aiyou on 2019/4/10.
 */

public class CreateTreasureAddActivity extends RxAppCompatActivity {
    public final static int REQUEST_ADD = 1;

    private final static String TEST_AUTH = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjVjYWRjMGEwYzk0MzA2NmNlZTNlMGI0ZCJ9.MG_nHmm7wuD9JqibO2HaMFT1pBbGQEntAFkIfjJxLRM";

    private TreasureEvent treasureEvent;
    private SHAutoActionbar toolbar;
    private RecyclerView rvTreasure;
    private Button btnAddNewTreasure;
    private TreasureToAddAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_treasure_add);
        treasureEvent = (TreasureEvent) getIntent().getSerializableExtra("treasureEvent");
        initView(savedInstanceState);
    }

    private void initView(Bundle savedInstanceState) {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setRightTx("创建");
        toolbar.setListener(event -> {
            if (event == SHAutoActionbar.ActionBarEvent.RIGHT_TEXT_CLICK) {

                if (mAdapter.getCurrentTreasure().isEmpty()) {
                    ToastUtils.show(this, "埋藏的宝藏不能为空！");
                    return;
                }

                treasureEvent.treasures = mAdapter.getCurrentTreasure();
                postCreateTreasure(treasureEvent);
            }
        });
        btnAddNewTreasure = findViewById(R.id.btn_add_new_treasure);
        btnAddNewTreasure.setOnClickListener(v -> {
            startActivityForResult(new Intent(CreateTreasureAddActivity.this, CreateTreasureAddDetailActivity.class), REQUEST_ADD);
        });

        rvTreasure = findViewById(R.id.rv_treasure_to_add);
        rvTreasure.setLayoutManager(new LinearLayoutManager(this));
        rvTreasure.addItemDecoration(new CommonItemDecoration(this, 2));
        mAdapter = new TreasureToAddAdapter();
        rvTreasure.setAdapter(mAdapter);
    }

    private void postCreateTreasure(TreasureEvent treasureEvent) {
        TreasureApi
                .getInstance()
                .createTreasure(TEST_AUTH, treasureEvent)
                .compose(TransformUtils.defaultNetConfig(this))
                .subscribe(new NetSubscriber<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        finish();
                    }

                    @Override
                    public void onFailure(NetError error) {
                        ToastUtils.show(CreateTreasureAddActivity.this, error.message);
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_ADD) {
            if (resultCode == RESULT_OK) {
                Treasure treasure = (Treasure) data.getSerializableExtra("treasure");
                mAdapter.addItem(treasure);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
