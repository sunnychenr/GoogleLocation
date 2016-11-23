package com.chenr.other;

import android.content.Context;

import com.chenr.googlelocationdemo.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.List;

/**
 * Created by ChenR on 2016/11/23.
 */

public class DrawLine {

    private Context context;
    private GoogleMap map;
    private List<LatLng> latlngs;
    private PolylineOptions options;

    public DrawLine(Context context, GoogleMap map, List<LatLng> latlngs, PolylineOptions options) {
        this.context = context;
        this.map = map;
        this.latlngs = latlngs;
        this.options = options;
    }

    public void draw () {
        //PolylineOptions options = new PolylineOptions();
        options.width(5);
        options.color(context.getResources().getColor(R.color.NavigationLineColor));
        options.geodesic(true);

        options.addAll(latlngs);

        map.addPolyline(options);
    }
}
