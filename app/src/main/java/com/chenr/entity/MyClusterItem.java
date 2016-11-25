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

    public MyClusterItem(LatLng mLatLng) {
        this.mLatLng = mLatLng;
    }

    public MyClusterItem (double lat, double lng) {
        this.mLatLng = new LatLng(lat, lng);
    }

    @Override
    public LatLng getPosition() {
        return mLatLng;
    }
}
