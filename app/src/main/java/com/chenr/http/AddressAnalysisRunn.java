package com.chenr.http;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.chenr.application.App;
import com.chenr.entity.AddressInfos;
import com.chenr.googlelocationdemo.MainActivity;
import com.chenr.googlelocationdemo.R;
import com.chenr.utils.LogUtil;
import com.chenr.utils.ToastUtil;
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
 * Created by ChenR on 2016/11/18.
 */

public class AddressAnalysisRunn implements Runnable {

    private final String getAddrURL = "https://maps.googleapis.com/maps/api/geocode/json?";
    private final String AppKey = "&key=" + App.AppKey;
    private final String LATLNG = "latlng=";
    private final String LOCATION_TYPE = "&locaiotn_type=ROOFTOP";
    private final String LANGUAGE = "&language=" + App.SystemLanguage;

    private LatLng mLatLng;
    private Handler mHandler;
    private TextView tv;

    public AddressAnalysisRunn(LatLng mLatLng, Handler mHandler, TextView tv) {
        this.mLatLng = mLatLng;
        this.mHandler = mHandler;
        this.tv = tv;
    }

    @Override
    public void run() {

        String path = getAddrURL + LATLNG + mLatLng.latitude + "," + mLatLng.longitude + AppKey + LANGUAGE + LOCATION_TYPE;

        LogUtil.log("地理位置反编译接口 -------> " + path);

        URL url = null;
        HttpURLConnection conn = null;
        BufferedReader br = null;
        String line = "";
        int responseCode = -1;
        Message msg = mHandler.obtainMessage(MainActivity.GETADDR);
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
                    LogUtil.log("ResultsBean ---> " + resultsBean.toString());
                    final String formatted_address = resultsBean.getFormatted_address();
                    String cityName = "";
                    List<AddressInfos.ResultsBean.AddressComponentsBean> address_components = resultsBean.getAddress_components();

                    for (int i = 0; i < address_components.size(); i ++) {
                        int d = 0;
                        AddressInfos.ResultsBean.AddressComponentsBean addressComponentsBean = address_components.get(i);
                        List<String> types = addressComponentsBean.getTypes();
                        LogUtil.log("城市地址 ------> " + types.toString());
                        for (int j = 0; j < types.size(); j ++) {
                            String str = types.get(j);
                            if ("locality".equals(str)) {
                                d = 10;
                                break;
                            }
                        }
                        if (d == 10) {
                            cityName = addressComponentsBean.getLong_name();
                            break;
                        }
                    }
                    tv.post(new Runnable() {
                        @Override
                        public void run() {
                            tv.setText(formatted_address);
                        }
                    });
                    msg.obj = cityName;
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
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
                conn.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
