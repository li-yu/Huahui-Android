package com.liyu.huahui.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
/**
 * Created by liyu on 2017/3/16.
 */

public class GsonUtil {

    private static Gson mGson;

    public static Gson getGson() {
        if (mGson == null) {
            synchronized (GsonUtil.class) {
                mGson = new GsonBuilder().setLenient().create();
            }
        }
        return mGson;
    }
}
