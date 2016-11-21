package com.chenr.http;

import android.os.Handler;
import android.os.Message;

import com.chenr.application.App;
import com.chenr.entity.NavigationLine;
import com.chenr.googlelocationdemo.MainActivity;
import com.chenr.utils.LogUtil;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ChenR on 2016/11/19.
 */

public class GetWayRunn implements Runnable {

    private final String getWayURL = "https://maps.googleapis.com/maps/api/directions/json?";
    private final String APPKEY = "&key=" + App.AppKey;
    private final String ORIGIN = "origin=";
    private final String DESTINATION = "&destination=";
    private final String MODE = "&mode=walking";
    private final String UNITS = "&units=metric";
    private final String LANGUAGE = "&language=" + App.SystemLanguage;

    private Handler mHandler;
    private LatLng mOrigin;
    private LatLng mDestination;
    private int what;

    public GetWayRunn(Handler mHandler, LatLng mOrigin, LatLng mDestination, int what) {
        this.mHandler = mHandler;
        this.mOrigin = mOrigin;
        this.mDestination = mDestination;
        this.what = what;

    }

    @Override
    public void run() {
        String path = getWayURL + ORIGIN + mOrigin.latitude + "," + mOrigin.longitude + DESTINATION +
                mDestination.latitude + "," + mDestination.longitude + APPKEY + MODE + UNITS + LANGUAGE;
        String method = "GET";

        String line = "";
        Message msg = mHandler.obtainMessage(what);
        StringBuffer buffer = new StringBuffer();

        URL url = null;
        HttpURLConnection conn = null;
        BufferedReader br = null;

        try {

            url = new URL(path);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(method);
            conn.setRequestProperty("Charset", "UTF-8");
            conn.setConnectTimeout(5000);
            conn.connect();

            int code = conn.getResponseCode();

            if (code == HttpURLConnection.HTTP_OK) {

                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                while ((line = br.readLine()) != null) {
                    buffer.append(line);
                }

                LogUtil.log("获取路线结果 -----------> " + buffer.toString());

                NavigationLine navigationLine = new Gson().fromJson(buffer.toString(), NavigationLine.class);

                if (navigationLine.getStatus().equals("OK")) {

                    List<NavigationLine.RoutesBean> routes = navigationLine.getRoutes();

                    if (!routes.isEmpty()) {
                        String points = routes.get(0).getOverview_polyline().getPoints();
                        MainActivity.locations.addAll(decodePoly(points));

                        mHandler.sendMessage(msg);
                    }

                } else {

                    LogUtil.log("请求路线失败!!! ----> " + navigationLine.getStatus());

                }
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private List<LatLng> decodePoly(String encoded) {

        List<LatLng> poly = new ArrayList();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng((((double) lat / 1E5)),
                    (((double) lng / 1E5)));
            poly.add(p);
        }
        return poly;
    }
}
