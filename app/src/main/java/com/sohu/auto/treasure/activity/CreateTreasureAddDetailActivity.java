package com.sohu.auto.treasure.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.sohu.auto.treasure.R;
import com.sohu.auto.treasure.entry.Treasure;
import com.sohu.auto.treasure.widget.SHAutoActionbar;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

/**
 * Created by aiyou on 2019/4/10.
 */

public class CreateTreasureAddDetailActivity extends RxAppCompatActivity {
    private final static int REQUEST_CHOOSE_PIC = 1;
    private final static int REQUEST_ADD_LOCATION = 2;

    private TextView tvLocation;
    private TextView tvChangeLocation;
    private EditText edtTreasureText;
    private ImageView ivTreasureImage;
    private EditText edtTreasureQuestionStem;
    private EditText edtTreasureQuestionAnswer;
    private SHAutoActionbar toolbar;

    private Uri imagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_treasure_add_detail);
        initView(savedInstanceState);
        initListener();
    }

    private void initView(Bundle savedInstanceState) {
        tvLocation = findViewById(R.id.tv_location);
        tvChangeLocation = findViewById(R.id.tv_change_location);
        edtTreasureText = findViewById(R.id.edt_treasure_text);
        ivTreasureImage = findViewById(R.id.iv_treasure_img);
        edtTreasureQuestionStem = findViewById(R.id.edt_question_stem);
        edtTreasureQuestionAnswer = findViewById(R.id.edt_question_answer);
        toolbar = findViewById(R.id.toolbar);

        toolbar.setRightTx("确定");
    }

    private void initListener() {
        toolbar.setListener(event -> {
            if (event == SHAutoActionbar.ActionBarEvent.RIGHT_TEXT_CLICK) {
                Treasure treasure = new Treasure();
                treasure.location = tvLocation.getText().toString() + "测试描述";
                treasure.text = edtTreasureText.getText().toString();
                treasure.image = imagePath;
                treasure.stem = edtTreasureQuestionStem.getText().toString();
                treasure.answer = edtTreasureQuestionAnswer.getText().toString();

                Intent intent = new Intent();
                intent.putExtra("treasure", treasure);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        tvChangeLocation.setOnClickListener(v -> {
            //todo to choose Map
            Intent intent = new Intent(CreateTreasureAddDetailActivity.this, AddTreasureLocationActivity.class);
            startActivityForResult(intent, REQUEST_ADD_LOCATION);
        });

        ivTreasureImage.setOnClickListener(v -> {
            if (imagePath == null) {
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);// 调用android的图库
                startActivityForResult(i, REQUEST_CHOOSE_PIC);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CHOOSE_PIC) {
            if (data != null) {
                Uri uri = data.getData();
                if (uri != null) {
                    // 成功获取到图片
                    imagePath = uri;
                    ivTreasureImage.setImageURI(imagePath);
                }
            }
        } else if (requestCode == REQUEST_ADD_LOCATION) {
            if (data != null && resultCode == RESULT_OK) {
                String latitude = data.getStringExtra("latitude");
                String longitude = data.getStringExtra("longitude");
                String address = data.getStringExtra("address");
                tvLocation.setText(address);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
