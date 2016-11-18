package com.chenr.googlelocationdemo;

import android.Manifest;
import android.app.AlertDialog;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.widget.TextView;

import com.chenr.http.AddressAnalysisRunn;
import com.chenr.utils.LogUtil;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import static com.chenr.googlelocationdemo.R.id.googleMap;

public class MainActivity extends FragmentActivity {

    private TextView tv_location;

    // 谷歌定位请求;
    private LocationRequest mLocationRequest;
    // 谷歌Api客户端;
    private GoogleApiClient mGoogleApiClient;
    // 谷歌地图对象;
    private GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    // 谷歌连接回调(调什么不知道);
    private GoogleApiClient.ConnectionCallbacks mConnectionCallback = new GoogleApiClient.ConnectionCallbacks() {

        @Override
        public void onConnected(@Nullable Bundle bundle) {

            // 创建一个定位请求;
            mLocationRequest = LocationRequest.create();
            // 设置请求间隔;
            //mLocationRequest.setInterval(2000);
            // 设置请求优先级;
            mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            if (ActivityCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION,
                                android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);

                return;
            }
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,
                    mLocationRequest, mLocationListener);

        }

        @Override
        public void onConnectionSuspended(int i) {
        }
    };

    // 谷歌连接失败监听;
    private GoogleApiClient.OnConnectionFailedListener mOnConnectionFailedListener =
            new GoogleApiClient.OnConnectionFailedListener() {

        @Override
        public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
            LogUtil.log("连接失败结果----->" + connectionResult.toString());
        }
    };

    private GoogleMap.OnMyLocationButtonClickListener mOnMyLocationButtonClickListener = new GoogleMap.OnMyLocationButtonClickListener() {
        @Override
        public boolean onMyLocationButtonClick() {
            LogUtil.log("被点击了…………");
            return false;
        }
    };

    // 定位监听;
    private LocationListener mLocationListener = new LocationListener() {

        @Override
        public void onLocationChanged(Location location) {

            LogUtil.log("定位结果----->" + location.toString());

            map.setOnMarkerClickListener(mOnMarkerClickListener);
            getAddress(location);
            locationMap(location);
        }

    };

    private String address = "暂时没有";

    // 地图标记点击监听;
    private GoogleMap.OnMarkerClickListener mOnMarkerClickListener = new GoogleMap.OnMarkerClickListener() {
        @Override
        public boolean onMarkerClick(Marker marker) {

            LatLng latLng = marker.getPosition();
            new Thread(new AddressAnalysisRunn(mHandler, latLng.latitude, latLng.longitude, 1)).start();

            AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
            dialog.setTitle("WiFi地址");
            dialog.setMessage(address);
            dialog.setPositiveButton("此处为距离", null);
            dialog.show();
            return false;
        }
    };

    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case 0:
                    String addr = msg.obj.toString();
                    tv_location.setText(addr);
                    break;
                case 1:
                    address = msg.obj.toString();
                    break;
            }

        }
    };

    private boolean isFirstLocation;
    private void locationMap(final Location location) {
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        CameraUpdate cameraUpdate = null;
        if (!isFirstLocation) {
            cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 15.0f);
            isFirstLocation = true;
        } else {
            cameraUpdate = CameraUpdateFactory.newLatLng(latLng);
        }

        map.animateCamera(cameraUpdate);

        if (ActivityCompat.checkSelfPermission(MainActivity.this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(MainActivity.this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(MainActivity.this, new String [] {Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION}, 1);

            return;
        }
        map.setMyLocationEnabled(true);

        addMarker(new LatLng(30.8, 104.8));
        addMarker(new LatLng(30.5, 104.7));
        addMarker(new LatLng(30.2, 104.4));


    }

    private void getAddress(Location location) {

        Thread th = new Thread(new AddressAnalysisRunn(mHandler, location.getLatitude(), location.getLongitude(), 0));
        th.start();

    }

    private void initView() {

        tv_location = (TextView) findViewById(R.id.tv_location);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(mConnectionCallback)
                .addOnConnectionFailedListener(mOnConnectionFailedListener)
                .build();

        mGoogleApiClient.connect();

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(googleMap);

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                map = googleMap;
            }
        });

    }

    private void addMarker (LatLng mLatLng) {
        MarkerOptions options = new MarkerOptions();
        options.icon(BitmapDescriptorFactory.fromResource(R.mipmap.wifi));
        options.position(mLatLng);
        map.addMarker(options);
    }

}
