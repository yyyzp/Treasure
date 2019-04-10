package com.sohu.auto.treasure.activity;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.TextureMapView;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MyLocationStyle;
import com.sohu.auto.treasure.R;
import com.sohu.auto.treasure.fragment.TreasureDetailDialogFragment;
import com.sohu.auto.treasure.net.ServiceFactory;
import com.sohu.auto.treasure.net.TreasureApi;
import com.sohu.auto.treasure.utils.MarkerDrawer;
import com.sohu.auto.treasure.widget.RippleAnimationView;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by zhipengyang on 2019/4/9.
 */

public class SearchTreasureActivity extends RxAppCompatActivity implements AMap.OnMyLocationChangeListener {
    static final int REQUSER_CODE_QUESTION = 1;
    boolean hasQuestion = false;
    TextureMapView mTextureMapView;
    RippleAnimationView rippleAnimationView;
    AMap aMap;
    List<Marker> markerList;
    LatLng locateLatLng;
    Button btnSearch;
    boolean isFirst = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initView(savedInstanceState);
    }

    private void initView(Bundle savedInstanceState) {
        mTextureMapView = findViewById(R.id.texture_mapview);
        btnSearch = findViewById(R.id.btn_search);
        rippleAnimationView = findViewById(R.id.layout_RippleAnimation);
        markerList = new ArrayList<>();
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
        mTextureMapView.onCreate(savedInstanceState);//初始化地图控制器对象
        if (aMap == null) {
            aMap = mTextureMapView.getMap();
            locate();
        }
        initListener();
    }

    private void initListener() {
        btnSearch.setOnClickListener(v -> {
            rippleAnimationView.startRippleAnimation();
            apiStart();

        });
        aMap.setOnMarkerClickListener(new AMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
//                locate();
                LatLng markerLatLng = marker.getPosition();
                float distance = MarkerDrawer.calculateLineDistance(locateLatLng, markerLatLng);
                if (distance < 10) {
                    verifyOrOpen();
                    Toast.makeText(SearchTreasureActivity.this, "开启宝箱  distance   " + distance, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(SearchTreasureActivity.this, "距离太远了  distance   " + distance, Toast.LENGTH_LONG).show();
                }
                return true;
            }
        });
    }

    private void apiStart() {
//        TreasureApi.getInstance().getBaidu("http://www.baidu.com")
//                .subscribeOn(Schedulers.newThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<Void>() {
//                    @Override
//                    public void onCompleted() {
//                        rippleAnimationView.stopRippleAnimation();
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onNext(Void s) {
//                        MarkerDrawer.drawMarkerList(SearchTreasureActivity.this, aMap, CreateTreasureActivity.markerList);
//                    }
//                });
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                rippleAnimationView.stopRippleAnimation();
                MarkerDrawer.drawMarkerList(SearchTreasureActivity.this, aMap, CreateTreasureActivity.markerList);

            }
        }, 2000);

    }

    private void verifyOrOpen() {
        if (hasQuestion) {
            Intent intent = new Intent(this, OpenTreasureActivity.class);
            intent.putExtra("id", 1);
            startActivityForResult(intent, REQUSER_CODE_QUESTION);
        } else {
            TreasureDetailDialogFragment.newInstance().show(getSupportFragmentManager(), "dialog");
        }
    }

    private void locate() {
        MyLocationStyle myLocationStyle;
        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_SHOW);//只定位一次。
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW_NO_CENTER);//连续定位、蓝点不会移动到地图中心点，并且蓝点会跟随设备移动。
        myLocationStyle.interval(5000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        aMap.getUiSettings().setMyLocationButtonEnabled(false);//设置默认定位按钮是否显示，非必需设置。
        aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
        myLocationStyle.showMyLocation(true);//设置是否显示定位小蓝点，用于满足只想使用定位，不想使用定位小蓝点的场景，设置false以后图面上不再有定位蓝点的概念，但是会持续回调位置信息。
        aMap.setOnMyLocationChangeListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        mTextureMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mTextureMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mTextureMapView.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mTextureMapView.onSaveInstanceState(outState);
    }

    @Override
    public void onMyLocationChange(Location location) {
        locateLatLng = new LatLng(location.getLatitude(), location.getLongitude());
        if (isFirst) {
            aMap.moveCamera(CameraUpdateFactory.newLatLng(locateLatLng));
            aMap.moveCamera(CameraUpdateFactory.zoomTo(15));
            isFirst = false;
        }
        Log.d("yzp", "  " + location.getLatitude() + "    " + location.getLongitude());
    }


}
