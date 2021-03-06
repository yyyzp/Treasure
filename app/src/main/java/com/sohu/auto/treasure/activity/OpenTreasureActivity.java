package com.sohu.auto.treasure.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.sohu.auto.treasure.R;
import com.sohu.auto.treasure.entry.Treasure;
import com.sohu.auto.treasure.utils.ToastUtils;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

/**
 * Created by zhipengyang on 2019/4/10.
 */

public class OpenTreasureActivity extends RxAppCompatActivity{
    TextView tvQuestion;
    EditText etAnswer;
    Button btnConfirm;
    Treasure treasure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        treasure = (Treasure) intent.getSerializableExtra("treasure");
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
        tvQuestion.setText(TextUtils.isEmpty(treasure.question) ? " " : treasure.question);
        btnConfirm.setOnClickListener(v -> {
            verify();
        });
    }

    private void verify() {
        if (!TextUtils.isEmpty(etAnswer.getText())) {
            String answer = etAnswer.getText().toString().trim();
            if (TextUtils.equals(answer, treasure.answer)) {
                ToastUtils.show(this, "答案正确");
                Intent intent = new Intent(OpenTreasureActivity.this, TreasureDetailActivity.class);
                intent.putExtra("treasure", treasure);
                startActivity(intent);
                finish();
            } else {
                ToastUtils.show(this, "答案错误");
            }
        } else {
            ToastUtils.show(this, "请输入答案");
        }
    }

}
