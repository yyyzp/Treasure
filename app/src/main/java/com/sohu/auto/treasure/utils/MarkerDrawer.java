package com.sohu.auto.treasure.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;

import com.amap.api.location.CoordinateConverter;
import com.amap.api.location.DPoint;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MarkerOptionsCreator;
import com.sohu.auto.treasure.R;
import com.sohu.auto.treasure.utils.DeviceInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhipengyang on 2019/4/8.
 */

public class MarkerDrawer {

    public static Marker drawMarker(Context context, AMap aMap, LatLng latLng) {
        MarkerOptions markerOption = new MarkerOptions();
        markerOption.position(latLng);
        markerOption.title("宝藏");
        markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                .decodeResource(context.getResources(), R.mipmap.ic_treasure)));
        return aMap.addMarker(markerOption);
    }

    public static void drawMarkerList(Context context, AMap aMap, List<Marker> markerList) {
        ArrayList<MarkerOptions> list = new ArrayList<>();
        LatLng latLng;
        for (Marker marker : markerList) {
            MarkerOptions markerOption = new MarkerOptions();
            latLng = new LatLng(marker.getPosition().latitude, marker.getPosition().longitude);
            markerOption.position(latLng);
            markerOption.title("宝藏");
            markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                    .decodeResource(context.getResources(), R.mipmap.ic_treasure)));
            list.add(markerOption);
        }
        aMap.addMarkers(list, false);
    }

    public static Marker drawCenterMarker(Activity activity, AMap aMap, LatLng latLng) {
        MarkerOptions markerOption = new MarkerOptions();
        markerOption.position(latLng);
        markerOption.title("宝藏");
        markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                .decodeResource(activity.getResources(), R.mipmap.ic_flag)));
        Marker marker = aMap.addMarker(markerOption);
        marker.setPositionByPixels(DeviceInfo.getScreenWidth(activity) / 2, DeviceInfo.getScreenHeight(activity) / 2);
        return marker;
    }

    public static float calculateLineDistance(LatLng locateLatLng,LatLng markerLatLng) {
        return AMapUtils.calculateLineDistance(locateLatLng, markerLatLng);
    }

    public static void clearAllMarkers(AMap aMap){
        aMap.clear();
    }
}
