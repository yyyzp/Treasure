package com.sohu.auto.treasure;

import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.TextureMapView;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MyLocationStyle;

/**
 * Created by zhipengyang on 2019/4/8.
 */

public class TreasureFragment extends LazyLoadBaseFragment implements AMap.OnMyLocationChangeListener {
    TextureMapView mTextureMapView;
    AMap aMap;
    Marker mMarker;
    Button btnAddMarker;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_treasure;
    }

    @Override
    protected void onInitView(Bundle savedInstanceState) {
        mTextureMapView = rootView.findViewById(R.id.texture_mapview);
        btnAddMarker = rootView.findViewById(R.id.btn_add_marker);
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
        mTextureMapView.onCreate(savedInstanceState);//初始化地图控制器对象
        if (aMap == null) {
            aMap = mTextureMapView.getMap();
            locate();
        }
        initListener();
    }

    private void initListener() {
        btnAddMarker.setOnClickListener(v -> {
            LatLng latLng = mMarker.getPosition();
            MarkerDrawer.drawMarker(getContext(),aMap,latLng);
            Log.d("yzp", latLng.latitude + "    " + latLng.longitude);
        });
    }

    private void locate() {
        MyLocationStyle myLocationStyle;
        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);//定位一次，且将视角移动到地图中心点。
//        myLocationStyle.interval(2000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        aMap.getUiSettings().setMyLocationButtonEnabled(false);//设置默认定位按钮是否显示，非必需设置。
        aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
        myLocationStyle.showMyLocation(false);//设置是否显示定位小蓝点，用于满足只想使用定位，不想使用定位小蓝点的场景，设置false以后图面上不再有定位蓝点的概念，但是会持续回调位置信息。
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
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    protected void lazyLoad() {

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

        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        aMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        aMap.moveCamera(CameraUpdateFactory.zoomTo(15));
        mMarker = MarkerDrawer.drawCenterMarker(getActivity(), aMap, latLng);

        Log.d("yzp", "  " + location.getLatitude() + "    " + location.getLongitude());
    }
}
