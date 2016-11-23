package com.chenr.utils;

import android.app.IntentService;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.ResultReceiver;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by ChenR on 2016/11/22.
 */

public class AnalysisLatlngIntentService extends IntentService {

    private final int SUCCESS_RESULT = 1;
    private final int FAILED_RESULT = 2;
    private final int EXCEPTION_RESULT = 3;


    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */
    public AnalysisLatlngIntentService() {
        super("AnalysisLatlngIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        ResultReceiver receiver = intent.getParcelableExtra("receiver");
        LatLng location = intent.getParcelableExtra("location");

        Geocoder mGeocoder = new Geocoder(this, Locale.ENGLISH);
        List<Address> addresses = null;
        try {
            addresses = mGeocoder.getFromLocation(location.latitude, location.longitude, 1);

            LogUtil.log("address -----> " + addresses.get(0).getAddressLine(0));

            if (addresses == null || addresses.isEmpty()) {
                receiver.send(FAILED_RESULT, null);
            } else {
                Bundle resultData = new Bundle();
                resultData.putCharSequence("address", addresses.get(0).getAddressLine(0));
                receiver.send(SUCCESS_RESULT, resultData);
            }

        } catch (Exception e) {
            e.printStackTrace();
            receiver.send(EXCEPTION_RESULT, null);
        }

    }

}
