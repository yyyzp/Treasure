package com.sohu.auto.treasure.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.sohu.auto.treasure.R;
import com.sohu.auto.treasure.entry.Treasure;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

/**
 * Created by aiyou on 2019/4/11.
 */

public class TreasureDetailActivity extends RxAppCompatActivity {

    private TextView tvText;
    private ImageView ivImg;
    private Treasure treasure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treasure_detail);
        getData();
        initView(savedInstanceState);
    }

    private void initView(Bundle savedInstanceState) {
        tvText = findViewById(R.id.tv_treasure_text);
        ivImg = findViewById(R.id.iv_treasure_img);

        if (treasure != null) {
            tvText.setText(treasure.text);
        }
    }

    private void getData() {
        /**
         * 测试数据
         * */
        Treasure t = new Treasure();
        t.text = "我只是一个测试宝藏的文字描述而已\n不用想多了";
        t.imagePath = "";

        treasure = t;
    }
}
