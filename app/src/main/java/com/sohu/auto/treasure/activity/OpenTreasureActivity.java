package com.sohu.auto.treasure.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.sohu.auto.treasure.R;
import com.sohu.auto.treasure.fragment.TreasureDetailDialogFragment;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

/**
 * Created by zhipengyang on 2019/4/10.
 */

public class OpenTreasureActivity extends RxAppCompatActivity implements TreasureDetailDialogFragment.DismissListener{
    int id;
    TextView tvQuestion;
    EditText etAnswer;
    Button btnConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);
        setContentView(R.layout.activity_open_treasure);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initView() {
        tvQuestion = findViewById(R.id.tv_question);
        etAnswer = findViewById(R.id.et_answer);
        btnConfirm = findViewById(R.id.btn_confirm);
        btnConfirm.setOnClickListener(v -> {
            verify();
        });
    }

    private void verify() {
        if (!TextUtils.isEmpty(etAnswer.getText())) {
            String answer = etAnswer.getText().toString().trim();
            TreasureDetailDialogFragment.newInstance(this).show(getSupportFragmentManager(),"dialog");
        }
    }

    @Override
    public void onDismiss() {
        finish();
    }
}
