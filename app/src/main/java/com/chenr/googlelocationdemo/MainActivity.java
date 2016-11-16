package com.chenr.googlelocationdemo;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.TextView;

import com.chenr.utils.LogUtil;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;

public class MainActivity extends FragmentActivity {

    private TextView tv_location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

    }

    private void initView() {
        tv_location = (TextView) findViewById(R.id.tv_location);

        /*MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.basic_map);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                LogUtil.log(googleMap.toString());
            }
        });*/
    }
}
