package com.sohu.auto.treasure.activity;

import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.TextureMapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MyLocationStyle;
import com.sohu.auto.treasure.R;
import com.sohu.auto.treasure.entry.Treasure;
import com.sohu.auto.treasure.entry.TreasureListEntity;
import com.sohu.auto.treasure.entry.TreasureListParam;
import com.sohu.auto.treasure.net.NetError;
import com.sohu.auto.treasure.net.NetSubscriber;
import com.sohu.auto.treasure.net.TreasureApi;
import com.sohu.auto.treasure.utils.MarkerDrawer;
import com.sohu.auto.treasure.utils.ToastUtils;
import com.sohu.auto.treasure.utils.TransformUtils;
import com.sohu.auto.treasure.widget.RippleAnimationView;
import com.sohu.auto.treasure.widget.SHAutoActionbar;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by zhipengyang on 2019/4/9.
 */

public class SearchTreasureActivity extends RxAppCompatActivity implements AMap.OnMyLocationChangeListener {
    static final int REQUSER_CODE_QUESTION = 1;
    TextureMapView mTextureMapView;
    RippleAnimationView rippleAnimationView;
    AMap aMap;
    List<Marker> markerList;
    LatLng locateLatLng;
    TextView tvSearch;
    SHAutoActionbar actionbar;
    boolean isFirst = true;
    String mTitle;
    String mActivityId;
    private List<TreasureListEntity.DataBean> mTreasureListEntities;
    private List<Treasure> mTreasureList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        mTitle = getIntent().getStringExtra("title");
        mActivityId = getIntent().getStringExtra("activityId");
        mTreasureListEntities = new ArrayList<>();
        markerList = new ArrayList<>();
        mTreasureList = new ArrayList<>();
        initView(savedInstanceState);
    }

    private void initView(Bundle savedInstanceState) {
        mTextureMapView = findViewById(R.id.texture_mapview);
        actionbar = findViewById(R.id.title);
        actionbar.setTitle(mTitle);
        tvSearch = findViewById(R.id.tv_search);
        rippleAnimationView = findViewById(R.id.layout_RippleAnimation);
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
        mTextureMapView.onCreate(savedInstanceState);//初始化地图控制器对象
        if (aMap == null) {
            aMap = mTextureMapView.getMap();
            aMap.getUiSettings().setZoomControlsEnabled(false);
            aMap.getUiSettings().setLogoBottomMargin(-50);
            locate();
        }
        initListener();
    }

    private void initListener() {
        tvSearch.setOnClickListener(v -> {
            getTreasurelist();

        });
        aMap.setOnMarkerClickListener(new AMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Log.d("yzp", "   " + marker.getDisplayLevel() + "    " + aMap.getMapScreenMarkers().size());

                if (locateLatLng == null || marker.getTitle() == null) {
                    return false;
                }
                String treasureId = marker.getTitle();
                LatLng markerLatLng = findTreasure(treasureId);
                float distance = MarkerDrawer.calculateLineDistance(locateLatLng, markerLatLng);

                if (distance < 50) {
                    verifyOrOpen(markerLatLng);
                    Toast.makeText(SearchTreasureActivity.this, "开启宝箱  distance   " + distance, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(SearchTreasureActivity.this, "距离太远了  distance  " + distance, Toast.LENGTH_LONG).show();
                }
                return true;
            }
        });
    }

    private LatLng findTreasure(String treasureId) {
        LatLng latLng;
        for (int i = 0; i < mTreasureList.size(); i++) {
            Treasure treasure = mTreasureList.get(i);
            if (TextUtils.equals(treasureId, treasure.id)) {
                double lng = treasure.locations[0];
                double lat = treasure.locations[1];
                latLng = new LatLng(lat, lng);
                return latLng;
            }
        }
        return null;
    }

    private void getTreasurelist() {
        if (locateLatLng != null) {
            rippleAnimationView.startRippleAnimation();
            double[] d = new double[]{locateLatLng.longitude, locateLatLng.latitude};
            TreasureListParam param = new TreasureListParam(d);
            TreasureApi.getInstance()
                    .getTreasurelist(mActivityId, param)
                    .compose(TransformUtils.defaultNetConfig((RxAppCompatActivity) this))
                    .subscribe(new NetSubscriber<TreasureListEntity>() {
                        @Override
                        public void onSuccess(TreasureListEntity treasureListEntities) {
                            //clear all markers
                            MarkerDrawer.clearAllMarkers(aMap);
                            mTreasureListEntities = treasureListEntities.getData();
                            if (mTreasureListEntities == null) {
                                return;
                            }
                            //draw markers
                            for (int i = 0; i < mTreasureListEntities.size(); i++) {
                                Treasure treasure = new Treasure();
                                TreasureListEntity.DataBean bean = mTreasureListEntities.get(i);
                                treasure.locations[0] = bean.getLocation().get(0);
                                treasure.locations[1] = bean.getLocation().get(1);
                                treasure.id = bean.getId();
                                treasure.question = bean.getQuestion();
                                treasure.answer = bean.getAnswer();
                                treasure.imagePath = bean.getImage();
                                treasure.content = bean.getContent();
                                mTreasureList.add(treasure);
                                LatLng latLng = new LatLng(treasure.locations[1], treasure.locations[0]);
//                                double latitude = mTreasureListEntities.get(i).getLocation().get(0);
//                                double longitude = mTreasureListEntities.get(i).getLocation().get(1);
//                                LatLng latLng = new LatLng(latitude, longitude);
                                MarkerDrawer.drawMarker(SearchTreasureActivity.this, aMap, latLng, treasure.id);
                            }
                        }

                        @Override
                        public void onFailure(NetError error) {
                            rippleAnimationView.stopRippleAnimation();
                        }

                        @Override
                        public void onCompleted() {
                            super.onCompleted();
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    rippleAnimationView.stopRippleAnimation();
                                }
                            }, 500);
                        }
                    });
        }
    }

    private void verifyOrOpen(LatLng latLng) {
        Treasure treasure = null;
        for (int i = 0; i < mTreasureList.size(); i++) {
            if (mTreasureList.get(i).locations[0] == latLng.longitude && mTreasureList.get(i).locations[1] == latLng.latitude) {
                treasure = mTreasureList.get(i);
                break;
            }
        }
        if (treasure == null) {
            return;
        }
        Treasure finalTreasure = treasure;
        TreasureApi.getInstance()
                .openTreasure(treasure.id)
                .compose(TransformUtils.defaultNetConfig((RxAppCompatActivity) this))
                .subscribe(new NetSubscriber<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        if (getResponse().code() == 204) {
                            if (!TextUtils.isEmpty(finalTreasure.question)) {
                                Intent intent = new Intent(SearchTreasureActivity.this, OpenTreasureActivity.class);
                                intent.putExtra("treasure", finalTreasure);
                                startActivity(intent);
                            } else {
                                Intent intent = new Intent(SearchTreasureActivity.this, TreasureDetailActivity.class);
                                intent.putExtra("treasure", finalTreasure);
                                startActivity(intent);
                            }
                        }
                    }

                    @Override
                    public void onFailure(NetError error) {

                    }
                });

    }

    private void locate() {
        MyLocationStyle myLocationStyle;
        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_SHOW);//只定位一次。
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW_NO_CENTER);//连续定位、蓝点不会移动到地图中心点，并且蓝点会跟随设备移动。
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_locate2));
        myLocationStyle.strokeColor(Color.argb(0, 0, 0, 0));// 设置圆形的边框颜色
        myLocationStyle.radiusFillColor(Color.argb(0, 0, 0, 0));// 设置圆形的填充颜色
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
        getTreasurelist();
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
            aMap.moveCamera(CameraUpdateFactory.zoomTo(18));
            isFirst = false;
        }
        Log.d("yzp", "  " + location.getLatitude() + location.getLongitude() + "    ");
    }

}
