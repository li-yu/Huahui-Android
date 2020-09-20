package com.liyu.huahui.http;

import com.liyu.huahui.http.api.YoudaoApi;

/**
 * Created by liyu on 2017/7/12.
 */

public class ApiFactory {
    protected static final Object monitor = new Object();

    private static YoudaoApi youdaoApi;

// --Commented out by Inspection START (2020/9/20 15:16):
//    public static YoudaoApi getYoudaoApi() {
//        if (youdaoApi == null) {
//            synchronized (monitor) {
//                youdaoApi = RetrofitManager.getInstance().create(YoudaoApi.class);
//            }
//        }
//        return youdaoApi;
//    }
// --Commented out by Inspection STOP (2020/9/20 15:16)
}
