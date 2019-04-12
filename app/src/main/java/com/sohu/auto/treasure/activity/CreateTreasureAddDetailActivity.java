package com.sohu.auto.treasure.activity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.sohu.auto.treasure.R;
import com.sohu.auto.treasure.entry.RemotePicture;
import com.sohu.auto.treasure.entry.Treasure;
import com.sohu.auto.treasure.net.NetError;
import com.sohu.auto.treasure.net.NetSubscriber;
import com.sohu.auto.treasure.net.PhotoApi;
import com.sohu.auto.treasure.utils.CommonUtils;
import com.sohu.auto.treasure.utils.ToastUtils;
import com.sohu.auto.treasure.widget.SHAutoActionbar;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by aiyou on 2019/4/10.
 */

public class CreateTreasureAddDetailActivity extends RxAppCompatActivity {
    private final static int REQUEST_CHOOSE_PIC = 1;
    private final static int CLIENT = 10006;
    private final static int MAX_IMG_SIZE = 500 * 1024;
    private final static int REQUEST_ADD_LOCATION = 2;

    private TextView tvLocation;
    private TextView tvChangeLocation;
    private EditText edtTreasureText;
    private ImageView ivTreasureImage;
    private EditText edtTreasureQuestionStem;
    private EditText edtTreasureQuestionAnswer;
    private SHAutoActionbar toolbar;

    private String imagePath;
    private double latitude;
    private double longitude;
    private boolean isLoadingPic;

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
                if (isLoadingPic) {
                    ToastUtils.show(this, "图片正在上传中，请稍等...");
                    return;
                }

                if (!validInput())
                    return;

                Treasure treasure = new Treasure();
                treasure.locationStr = tvLocation.getText().toString();
                treasure.locations[0] = longitude;
                treasure.locations[1] = latitude;
                treasure.content = edtTreasureText.getText().toString();
                treasure.imagePath = imagePath;
                treasure.question = edtTreasureQuestionStem.getText().toString();
                treasure.answer = edtTreasureQuestionAnswer.getText().toString();

                Intent intent = new Intent();
                intent.putExtra("treasure", treasure);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        tvChangeLocation.setOnClickListener(v -> {
            Intent intent = new Intent(CreateTreasureAddDetailActivity.this, AddTreasureLocationActivity.class);
            startActivityForResult(intent, REQUEST_ADD_LOCATION);
        });

        ivTreasureImage.setOnClickListener(v -> {
            if (imagePath == null && !isLoadingPic) {
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);// 调用android的图库
                startActivityForResult(i, REQUEST_CHOOSE_PIC);
            }
        });
    }

    private boolean validInput() {
        if (TextUtils.isEmpty(tvLocation.getText())) {
            ToastUtils.show(this, "宝藏必须指定埋藏地点");
            return false;
        }

        if (TextUtils.isEmpty(edtTreasureText.getText()) && TextUtils.isEmpty(imagePath)) {
            ToastUtils.show(this, "宝藏应至少包含图片或者文字其中的一种");
            return false;
        }

        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CHOOSE_PIC) {
            if (data != null) {
                Uri uri = data.getData();
                if (uri != null) {
                    // 成功获取到图片
                    if (!CommonUtils.isImageFile(getPath(uri))) return;
                    ivTreasureImage.setImageURI(uri);
                    handleImage(uri);
                }
            }
        } else if (requestCode == REQUEST_ADD_LOCATION) {
            if (data != null && resultCode == RESULT_OK) {
                latitude = data.getDoubleExtra("latitude", 0.0d);
                longitude = data.getDoubleExtra("longitude", 0.0f);
                String address = data.getStringExtra("address");
                if(TextUtils.isEmpty(address)){
                    address=longitude+"   "+latitude;
                }
                tvLocation.setText(address);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void handleImage(Uri imgUri) {
        File imgFile = new File(getPath(imgUri));
        // compress image below 500K
        if (imgFile.length() > MAX_IMG_SIZE) {
            byte[] compressImage = CommonUtils.compressImage(BitmapFactory.decodeFile(imgFile.getPath()), MAX_IMG_SIZE);
            CommonUtils.bytes2File(compressImage, imgFile);
        }

        if (imgFile.length() <= 0) {
            return;
        }

        isLoadingPic = true;
        uploadImage(imgFile)
                .subscribe(new NetSubscriber<RemotePicture>() {
                    @Override
                    public void onSuccess(RemotePicture remotePicture) {
                        imagePath = remotePicture.url;
                        isLoadingPic = false;
                    }

                    @Override
                    public void onFailure(NetError error) {
                        // todo end load
                        isLoadingPic = false;
                    }
                });
    }

    private Observable<Response<RemotePicture>> uploadImage(File imgFile) {
        RequestBody imageBody = RequestBody.create(MediaType.parse("multipart/form-data"), imgFile);
        MultipartBody.Part imagePart = MultipartBody.Part.createFormData("file", imgFile.getName(), imageBody);
        RequestBody client = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(CLIENT));
        return PhotoApi
                .getInstance()
                .uploadImg(client, imagePart)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .compose(bindToLifecycle());
    }

    private String getPath(Uri uri) {
        String[] projection = {MediaStore.Video.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
}
