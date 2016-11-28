package com.chenr.entity;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.chenr.googlelocationdemo.R;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

/**
 * Created by ChenR on 2016/11/24.
 */

public class MyClusterItem implements ClusterItem {

    private final LatLng mLatLng;
    private String msg;

    public MyClusterItem(LatLng mLatLng, String msg) {
        this.mLatLng = mLatLng;
        this.msg = msg;
    }

    public MyClusterItem (double lat, double lng, String msg) {
        this.mLatLng = new LatLng(lat, lng);
        this.msg = msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public LatLng getmLatLng() {
        return mLatLng;
    }

    public String getMsg() {
        return msg;
    }

    @Override
    public LatLng getPosition() {
        return mLatLng;
    }
}
