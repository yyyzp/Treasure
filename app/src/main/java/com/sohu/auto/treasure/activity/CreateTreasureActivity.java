package com.sohu.auto.treasure.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import com.sohu.auto.treasure.R;
import com.sohu.auto.treasure.entry.TreasureEvent;
import com.sohu.auto.treasure.utils.ToastUtils;
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

            if (!validInput())
                return;

            Intent intent = new Intent(CreateTreasureActivity.this, CreateTreasureAddActivity.class);
            TreasureEvent treasureEvent = new TreasureEvent();
            treasureEvent.title = etTitle.getText().toString();
            treasureEvent.password = etPassword.getText().toString();
            intent.putExtra("treasureEvent", treasureEvent);
            startActivity(intent);
            // 关闭自己
            finish();
        }));
    }

    private boolean validInput() {
        if (TextUtils.isEmpty(etTitle.getText())) {
            ToastUtils.show(this, "宝藏必须拥有姓名！");
            return false;
        }

        return true;
    }
}
