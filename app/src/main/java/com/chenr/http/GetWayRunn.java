package com.chenr.http;

import android.os.Handler;
import android.os.Message;

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
import java.util.List;

/**
 * Created by ChenR on 2016/11/19.
 */

public class GetWayRunn implements Runnable {

    private final String getWayURL = "https://maps.googleapis.com/maps/api/directions/json?";
    private final String APPKEY = "&key=AIzaSyAC7f8wFBelDxjr6GW-WCLb-m8qqsuyQx4";
    private final String ORIGIN = "origin=";
    private final String DESTINATION = "&destination=";
    private final String MODE = "&mode=walking";
    private final String UNITS = "&units=metric";
    private final String LANGUAGE = "&language=zh_CN";

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
            conn.setConnectTimeout(5000);
            conn.connect();

            int code = conn.getResponseCode();

            if (code == HttpURLConnection.HTTP_OK) {

                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                while ((line = br.readLine()) != null) {
                    buffer.append(line);
                }

                //LogUtil.log("获取路线结果 -----------> " + buffer.toString());

                NavigationLine navigationLine = new Gson().fromJson(buffer.toString(), NavigationLine.class);
                NavigationLine.RoutesBean routesBean = navigationLine.getRoutes().get(0);
                List<NavigationLine.RoutesBean.LegsBean> legs = routesBean.getLegs();

                LogUtil.log("线路集合长度----->" + legs.size());

                NavigationLine.RoutesBean.LegsBean legsBean = legs.get(0);
                List<NavigationLine.RoutesBean.LegsBean.StepsBean> steps = legsBean.getSteps();

                for (NavigationLine.RoutesBean.LegsBean.StepsBean stepsBean : steps) {
                    MainActivity.locations.add(stepsBean.getEnd_location());
                }

                mHandler.sendMessage(msg);

            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
