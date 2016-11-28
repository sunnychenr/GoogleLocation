package com.chenr.googlelocationdemo;

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

import com.chenr.entity.LocalWiFi;
import com.chenr.entity.MyClusterItem;
import com.chenr.entity.MyClusterRenderer;
import com.chenr.utils.LogUtil;
import com.chenr.utils.ToastUtil;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;
import com.google.maps.android.clustering.ClusterManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends FragmentActivity {

    private static final int GETWAY = 1;
    public static final int GETADDR = 2;

    private TextView tv_location = null;

    // 定位请求;
    private LocationRequest mLocationRequest = null;
    // 谷歌Api客户端;
    private GoogleApiClient mGoogleApiClient = null;
    // 当前位置经纬度;
    public static LatLng currentLocation = null;
    // 谷歌地图对象;
    private GoogleMap map = null;
    // 获取WiFi信息;
    private List<LocalWiFi.DataBean> wifis = null;
    // 线路申请集合;
    public static List<LatLng> mLatlngs = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    // 连接;
    private GoogleApiClient.ConnectionCallbacks mConnectionCallback = new GoogleApiClient.ConnectionCallbacks() {

        @Override
        public void onConnected(@Nullable Bundle bundle) {

            // 创建一个定位请求;
            mLocationRequest = LocationRequest.create();
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
            // 设置MyLocation层;(传说中的当前位置定位点和重定位按钮)
            map.setMyLocationEnabled(true);
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,
                    mLocationRequest, mLocationListener);

            Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            LogUtil.log("当前位置 -------> " + lastLocation);
            locationMap(lastLocation);
            getWifiInfos();

        }

        @Override
        public void onConnectionSuspended(int i) {
        }
    };

    // 连接失败监听;
    private GoogleApiClient.OnConnectionFailedListener mOnConnectionFailedListener =
            new GoogleApiClient.OnConnectionFailedListener() {

                @Override
                public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                    LogUtil.log("连接失败----->" + connectionResult.toString());
                }
            };

    // MyLocation层按钮点击监听;
    private GoogleMap.OnMyLocationButtonClickListener mOnMyLocationButtonClickListener = new GoogleMap.OnMyLocationButtonClickListener() {

        @Override
        public boolean onMyLocationButtonClick() {

            if (ActivityCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainActivity.this,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION,
                                android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);

                return true;
            }
            locationMap(LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient));
            LogUtil.log("重新定位按钮被点击... ...");
            return true;
        }
    };

    private void getWifiInfos() {

        String line = "";
        LocalWiFi localWiFi = null;
        StringBuffer buffer = new StringBuffer();
        BufferedReader br = new BufferedReader(new InputStreamReader(getResources().openRawResource(R.raw.wifi)));
        try {
            while ((line = br.readLine()) != null) {
                buffer.append(line);
            }
            localWiFi = new Gson().fromJson(buffer.toString(), LocalWiFi.class);
            wifis = localWiFi.getData();
            LogUtil.log("附近WiFi信息 -------------> " + wifis);
            addMarkerItems();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // 定位监听;
    private LocationListener mLocationListener = new LocationListener() {

        @Override
        public void onLocationChanged(Location location) {

            locationMap(location);

            LogUtil.log("定位结果----->" + location.toString());

        }

    };

    //private static Handler mHandler = new Handler() {
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case GETWAY:
                    LogUtil.log("mLatlngs ------> " + mLatlngs);

                    addMarkerItems();

                    PolylineOptions polylineOptions = new PolylineOptions();
                    polylineOptions.color(getResources().getColor(R.color.NavigationLineColor));
                    polylineOptions.width(5);
                    polylineOptions.geodesic(true);
                    polylineOptions.addAll(mLatlngs);
                    map.addPolyline(polylineOptions);
                    break;

                case GETADDR:
                    tv_location.setText(msg.obj.toString());
                    break;
            }
        }
    };

    // 将当前位置设置到界面中心;
    private void locationMap(Location location) {
        if (location != null) {
            LogUtil.log("获取 ------> 并将当前位置居中... ...");
            currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 18.0f));
        } else if (currentLocation != null) {
            LogUtil.log("定位并将当前位置居中... ...");
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 18.0f));
        } else {
            LogUtil.log("location 为空！！！");
            ToastUtil.toast("定位失败！！！请检查是否打开定位服务开关");
        }
    }

    private void initView() {
        tv_location = (TextView) findViewById(R.id.tv_location);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(mConnectionCallback)
                .addOnConnectionFailedListener(mOnConnectionFailedListener)
                .build();
        mGoogleApiClient.connect();

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.googleMap);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                map = googleMap;
                map.setIndoorEnabled(false);
                map.setBuildingsEnabled(false);
                map.getUiSettings().setIndoorLevelPickerEnabled(false);
                map.setOnMyLocationButtonClickListener(mOnMyLocationButtonClickListener);
                setClusterManager();
            }
        });

    }

    private ClusterManager<MyClusterItem> mClusterItemClusterManager;
    private void setClusterManager() {
        mClusterItemClusterManager = new ClusterManager<MyClusterItem>(MainActivity.this, map);
        MyClusterRenderer mMyClusterRenderer = new MyClusterRenderer(MainActivity.this, map, mClusterItemClusterManager, mHandler);
        mClusterItemClusterManager.setRenderer(mMyClusterRenderer);
        map.setOnMarkerClickListener(mClusterItemClusterManager);
        map.setOnCameraChangeListener((GoogleMap.OnCameraChangeListener) mClusterItemClusterManager);

    }

    private void addMarkerItems() {
        LogUtil.log("addMarkerItems ------> 被调用");
        for (LocalWiFi.DataBean dataBean: wifis) {
            mClusterItemClusterManager.addItem(new MyClusterItem(dataBean.getLati(), dataBean.getLongi(),
                    dataBean.getSsid() + "&" + dataBean.getDist()));
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
    }
}
