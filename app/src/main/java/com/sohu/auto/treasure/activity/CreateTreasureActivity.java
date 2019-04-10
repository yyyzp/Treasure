package com.sohu.auto.treasure.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.sohu.auto.treasure.R;
import com.sohu.auto.treasure.entry.TreasureEvent;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

/**
 * Created by aiyou on 2019/4/10.
 */

public class CreateTreasureActivity extends RxAppCompatActivity {
    private Button btnNext;
    private EditText etTitle;
    private EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_treasure);
        initView(savedInstanceState);
    }

    private void initView(Bundle savedInstanceState) {
        btnNext = findViewById(R.id.btn_next);
        etTitle = findViewById(R.id.et_title);
        etPassword = findViewById(R.id.et_password);

        btnNext.setOnClickListener((v -> {
            Intent intent = new Intent(CreateTreasureActivity.this, CreateTreasureAddActivity.class);
            TreasureEvent treasureEvent = new TreasureEvent();
            treasureEvent.title = etTitle.getText().toString();
            treasureEvent.password = etPassword.getText().toString();
            intent.putExtra("treasureEvent", treasureEvent);
            startActivity(intent);
        }));
    }
}
