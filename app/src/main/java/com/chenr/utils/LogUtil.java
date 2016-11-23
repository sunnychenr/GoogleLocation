package com.chenr.utils;

import android.util.Log;

/**
 * Created by ChenR on 2016/11/16.
 */

public class LogUtil {

    private static final String TAG = "ChenR";
    private static boolean isOutput = true;

    public static void log (String log) {
        if (isOutput) {
            Log.d(TAG, log);
        }
    }

}
