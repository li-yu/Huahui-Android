package com.liyu.huahui.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.liyu.huahui.App;

/**
 * copied from https://github.com/Blankj/AndroidUtilCode
 * Thanks
 * Created by liyu on 2017/7/11.
 */

public class NetworkUtil {

    public static boolean isConnected() {
        NetworkInfo info = getActiveNetworkInfo();
        return info != null && info.isConnected();
    }

    private static NetworkInfo getActiveNetworkInfo() {
        return ((ConnectivityManager) App.getContext().getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
    }
}
