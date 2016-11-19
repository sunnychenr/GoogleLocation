package com.chenr.http;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.TextView;
import android.widget.Toast;

import com.chenr.entity.AddressResponse;
import com.chenr.utils.LogUtil;
import com.chenr.utils.ToastUtil;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by ChenR on 2016/11/18.
 */

public class AddressAnalysisRunn implements Runnable {

    private final String getAddrURL = "https://maps.googleapis.com/maps/api/geocode/json?";
    private final String AppKey = "&key=AIzaSyAC7f8wFBelDxjr6GW-WCLb-m8qqsuyQx4";
    private final String LATLNG = "latlng=";
    private final String ANOTHER = "&language=zh_CN&locaiotn_type=ROOFTOP";

    private Handler mHandler;
    private String latitude;
    private String longitude;
    private int what;

    public AddressAnalysisRunn(Handler mHandler, double latitude, double longitude, int what) {
        this.mHandler = mHandler;
        this.latitude = String.valueOf(latitude);
        this.longitude = String.valueOf(longitude);
        this.what = what;
    }

    @Override
    public void run() {

        String path = getAddrURL + LATLNG + latitude + "," + longitude + AppKey + ANOTHER;

        URL url = null;
        HttpURLConnection conn = null;
        BufferedReader br = null;
        String line = "";
        Message msg = mHandler.obtainMessage(what);
        int responseCode = -1;
        StringBuffer buffer = new StringBuffer();

        try {
            url = new URL(path);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            conn.connect();

            responseCode = conn.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                while ((line = br.readLine()) != null) {
                    buffer.append(line);
                }

                AddressResponse addressResponse = new Gson().fromJson(buffer.toString(), AddressResponse.class);

                String status = addressResponse.getStatus();

                if (status.equals("OK")) {

                    AddressResponse.ResultsBean resultsBean = addressResponse.getResults().get(0);
                    String formatted_address = resultsBean.getFormatted_address();
                    msg.obj = formatted_address;

                    mHandler.sendMessage(msg);

                } else {
                    Looper.prepare();
                    ToastUtil.toast(status);
                    Looper.loop();
                }
            } else {
                ToastUtil.toast("请求失败！！！responseCode：" + responseCode);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
