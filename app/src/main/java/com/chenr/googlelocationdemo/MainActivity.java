package com.chenr.googlelocationdemo;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.ResultReceiver;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.chenr.application.App;
import com.chenr.entity.LocalWiFi;
import com.chenr.http.AddressAnalysisRunn;
import com.chenr.http.GetWayRunn;
import com.chenr.other.DrawLine;
import com.chenr.utils.AnalysisLatlngIntentService;
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
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends FragmentActivity {

    private static final int GETWAY = 1;

    private TextView tv_location = null;

    // 定位请求;
    private LocationRequest mLocationRequest = null;
    // 谷歌Api客户端;
    private GoogleApiClient mGoogleApiClient = null;
    // 当前位置经纬度;
    private LatLng currentLocation = null;
    // 谷歌地图对象;
    private static GoogleMap map = null;
    //private GoogleMap map = null;
    // 线路申请集合;
    public static List<LatLng> mLatlngs = new ArrayList();
    // wifi位置集合;
    private Map<LatLng, String> markerInfos = new HashMap();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    // 连接回调(调什么不知道);
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
            map.setMyLocationEnabled(true);
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,
                    mLocationRequest, mLocationListener);

            Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            LogUtil.log("lastLocation -------> " + lastLocation);

            if (lastLocation != null) {
                locationMap(lastLocation);
            }

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

    // 获取WiFi信息;
    private List<LocalWiFi.DataBean> wifis = null;

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
            addMarker();
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

    private ResultReceiver mResultRecevier = new ResultReceiver(mHandler) {

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            if (tv_addr != null & resultCode == 1 & resultData != null) {
                String address = resultData.getCharSequence("address").toString();
                tv_addr.setText(address);
            }
        }

    };


    private TextView tv_addr;
    // 地图标记点击监听;
    private GoogleMap.OnMarkerClickListener mOnMarkerClickListener = new GoogleMap.OnMarkerClickListener() {
        @Override
        public boolean onMarkerClick(final Marker marker) {

            final LatLng position = marker.getPosition();
            String str = markerInfos.get(position);

            String title = str.substring(0, str.indexOf("&"));
            String dist = str.substring(str.indexOf("&") + 1, str.indexOf(".")) + "m";

            AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
            View view = View.inflate(MainActivity.this, R.layout.dialog_layout, null);
            TextView tv_title = (TextView) view.findViewById(R.id.dialog_title);
            tv_addr = (TextView) view.findViewById(R.id.dialog_address);
            Button btn_dist = (Button) view.findViewById(R.id.btn_dist);

            tv_title.setText(title);
            btn_dist.setText(dist);

            //new Thread(new AddressAnalysisRunn(position, tv_addr)).start();
            Intent intent = new Intent(MainActivity.this, AnalysisLatlngIntentService.class);
            intent.putExtra("receiver", mResultRecevier);
            intent.putExtra("location", currentLocation);
            startService(intent);

            dialog.setView(view);
            final AlertDialog show = dialog.show();

            btn_dist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    mLatlngs.clear();
                    addMarker();
                    new Thread(new GetWayRunn(mHandler, currentLocation, position, GETWAY)).start();
                    show.dismiss();
                }
            });

            return true;
        }
    };

    private static Handler mHandler = new Handler() {
//    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {

                case GETWAY:
                    LogUtil.log("mLatlngs ------> " + mLatlngs);

                    PolylineOptions polylineOptions = new PolylineOptions();
                    //addMarker();
                    /*polylineOptions.color(getResources().getColor(R.color.NavigationLineColor));
                    polylineOptions.width(5);
                    polylineOptions.geodesic(true);

                    polylineOptions.addAll(mLatlngs);

                    map.addPolyline(polylineOptions);*/

                    new DrawLine(App.applicationContext, map, mLatlngs, polylineOptions).draw();

                    break;
            }

        }
    };

    // 将当前位置设置到界面中心;
    private void locationMap(final Location location) {
        if (location != null) {
            currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
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
                map.setOnMyLocationButtonClickListener(mOnMyLocationButtonClickListener);
                map.setOnMarkerClickListener(mOnMarkerClickListener);
            }
        });
    }

    private void addMarker () {
        LogUtil.log("... ...添加标记");
        MarkerOptions options = null;
        LatLng ll = null;
        map.clear();
        for (LocalWiFi.DataBean wifidata : wifis ) {
            options = new MarkerOptions();
            ll = new LatLng(wifidata.getLati(), wifidata.getLongi());
            options.position(ll);
            options.icon(BitmapDescriptorFactory.fromResource(R.mipmap.wifi));
            markerInfos.put(ll, wifidata.getSsid() + "&" + wifidata.getDist());
            map.addMarker(options);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mGoogleApiClient.disconnect();
    }
}
