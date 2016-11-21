package com.chenr.http;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.chenr.application.App;
import com.chenr.entity.AddressInfos;
import com.chenr.googlelocationdemo.R;
import com.chenr.utils.ToastUtil;
import com.google.android.gms.maps.model.LatLng;
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
    private final String AppKey = "&key=" + App.AppKey;
    private final String LATLNG = "latlng=";
    private final String LOCATION_TYPE = "&locaiotn_type=ROOFTOP";
    private final String LANGUAGE = "&language=" + App.SystemLanguage;

    private Handler mHandler;
    private LatLng mLatLng;
    private TextView tv_addr;
    private int what;

    public AddressAnalysisRunn(Handler mHandler, LatLng mLatLng, TextView tv_addr, int what) {
        this.mHandler = mHandler;
        this.mLatLng = mLatLng;
        this.tv_addr = tv_addr;
        this.what = what;
    }

    @Override
    public void run() {

        String path = getAddrURL + LATLNG + mLatLng.latitude + "," + mLatLng.longitude + AppKey + LANGUAGE + LOCATION_TYPE;

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

                AddressInfos addressInfos = new Gson().fromJson(buffer.toString(), AddressInfos.class);

                String status = addressInfos.getStatus();

                if (status.equals("OK")) {

                    AddressInfos.ResultsBean resultsBean = addressInfos.getResults().get(0);
                    final String formatted_address = resultsBean.getFormatted_address();
                    msg.obj = formatted_address;

                    tv_addr.post(new Runnable() {

                        @Override
                        public void run() {

                            tv_addr.setText(formatted_address);

                        }

                    });

                    //mHandler.sendMessage(msg);

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
        } finally {
            try {
                br.close();
                conn.disconnect();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
