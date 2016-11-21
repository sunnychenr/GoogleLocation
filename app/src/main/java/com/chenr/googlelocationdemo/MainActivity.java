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
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.chenr.entity.LocalWiFi;
import com.chenr.http.AddressAnalysisRunn;
import com.chenr.http.GetWayRunn;
import com.chenr.utils.LogUtil;
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
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity {

    private TextView tv_location;

    // 谷歌定位请求;
    private LocationRequest mLocationRequest;
    // 谷歌Api客户端;
    private GoogleApiClient mGoogleApiClient;
    // 谷歌地图对象;
    private GoogleMap map;

    // 线路申请集合;
    public static List<LatLng> locations = new ArrayList();

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

            map.setMyLocationEnabled(true);

            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,
                    mLocationRequest, mLocationListener);

            //Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

            //LogUtil.log("lastLocation ------->" + lastLocation);

            map.setOnMarkerClickListener(mOnMarkerClickListener);
            //locationMap(lastLocation);
            getWifiInfos();
            /*new Thread(new GetWayRunn(mHandler, new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude()),
                    new LatLng(30.5408915639, 104.0764697120), 1)).start();*/

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

            if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainActivity.this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION,
                                android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);

                return true;
            }
            locationMap(LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient));
            LogUtil.log("被点击了…………");
            return true;
        }
    };

    // 获取WiFi信息;
    private void getWifiInfos() {

        String line = "";
        LocalWiFi localWiFi = null;
        List<LocalWiFi.DataBean> wifis = null;
        StringBuffer buffer = new StringBuffer();
        BufferedReader br = new BufferedReader(new InputStreamReader(getResources().openRawResource(R.raw.wifi)));

        try {
            while ((line = br.readLine()) != null) {
                buffer.append(line);
            }
            localWiFi = new Gson().fromJson(buffer.toString(), LocalWiFi.class);
            wifis = localWiFi.getData();
            LogUtil.log("附近WiFi信息 -------------> " + wifis);
            addMarker(wifis);
        } catch (IOException e) {
            e.printStackTrace();
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

    private String address = "暂时没有";

    // 地图标记点击监听;
    private GoogleMap.OnMarkerClickListener mOnMarkerClickListener = new GoogleMap.OnMarkerClickListener() {
        @Override
        public boolean onMarkerClick(Marker marker) {

            LatLng position = marker.getPosition();

            final AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
            View view = View.inflate(MainActivity.this, R.layout.dialog_layout, null);
            TextView tv_title = (TextView) view.findViewById(R.id.dialog_title);
            TextView tv_addr = (TextView) view.findViewById(R.id.dialog_address);
            Button btn_dist = (Button) view.findViewById(R.id.btn_dist);

            tv_title.setText(marker.getTitle());

            btn_dist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            new Thread(new AddressAnalysisRunn(mHandler, position, tv_addr, 1)).start();
            dialog.setView(view);
            dialog.show();

            return true;
        }
    };

    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case 0:

                    LogUtil.log("携带信息: " + msg.toString());
                    String addr = msg.obj.toString();
                    tv_location.setText(addr);
                    break;

                case 1:
                    PolylineOptions polylineOptions = new PolylineOptions();
                    polylineOptions.width(5);
                    polylineOptions.color(getResources().getColor(R.color.NavigationLineColor));
                    polylineOptions.geodesic(true);
                    polylineOptions.addAll(locations);

                    map.addPolyline(polylineOptions);

                    break;
            }

        }
    };

    // 将当前位置设置到界面中心;
    private void locationMap(final Location location) {
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15.0f));
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
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mGoogleApiClient.disconnect();
    }

    private void addMarker (List<LocalWiFi.DataBean> wifi) {
        MarkerOptions options = null;

        for (LocalWiFi.DataBean wifidata : wifi ) {
            options = new MarkerOptions();
            options.position(new LatLng(wifidata.getLati(), wifidata.getLongi()));
            options.icon(BitmapDescriptorFactory.fromResource(R.mipmap.wifi));
            options.title(wifidata.getSsid());

            map.addMarker(options);
        }

    }
}
