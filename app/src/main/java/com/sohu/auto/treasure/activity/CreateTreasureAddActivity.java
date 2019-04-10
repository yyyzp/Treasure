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
import com.sohu.auto.treasure.widget.CommonItemDecoration;
import com.sohu.auto.treasure.widget.SHAutoActionbar;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

/**
 * Created by aiyou on 2019/4/10.
 */

public class CreateTreasureAddActivity extends RxAppCompatActivity {
    public final static int REQUEST_ADD = 1;

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
                //todo 完成活动的创建
                treasureEvent.treasures = mAdapter.getCurrentTreasure();
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
