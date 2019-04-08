package com.sohu.auto.treasure;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;

import com.amap.api.maps.AMap;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.sohu.auto.treasure.utils.DeviceInfo;

/**
 * Created by zhipengyang on 2019/4/8.
 */

public class MarkerDrawer {

    public static Marker drawMarker(Context context, AMap aMap, LatLng latLng) {
        MarkerOptions markerOption = new MarkerOptions();
        markerOption.position(latLng);
        markerOption.title("宝藏");
        markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                .decodeResource(context.getResources(), R.mipmap.ic_launcher_round)));
        return aMap.addMarker(markerOption);
    }

    public static Marker drawCenterMarker(Activity activity, AMap aMap, LatLng latLng) {
        MarkerOptions markerOption = new MarkerOptions();
        markerOption.position(latLng);
        markerOption.title("宝藏");
        markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                .decodeResource(activity.getResources(), R.mipmap.ic_launcher_round)));
        Marker marker = aMap.addMarker(markerOption);
        marker.setPositionByPixels(DeviceInfo.getScreenWidth(activity)/2, DeviceInfo.getScreenHeight(activity)/2);
        return marker;
    }
}
