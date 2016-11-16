package com.chenr.utils;

import android.widget.Toast;

import com.chenr.application.App;

/**
 * Created by ChenR on 2016/11/16.
 */

public class ToastUtil {

    public static void toast (String toast) {

        Toast.makeText(App.applicationContext, toast, Toast.LENGTH_SHORT).show();

    }

}
