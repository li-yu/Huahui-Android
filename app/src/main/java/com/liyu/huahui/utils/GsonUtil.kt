package com.liyu.huahui.utils

import com.google.gson.Gson
import com.google.gson.GsonBuilder

/**
 * Updated by ultranity on 2020/9/20.
 * Created by liyu on 2017/3/16.
 */
object GsonUtil {
    private var mGson: Gson? = null
    @JvmStatic
    val gson: Gson?
        get() {
            if (mGson == null) {
                synchronized(GsonUtil::class.java) { mGson = GsonBuilder().setLenient().create() }
            }
            return mGson
        }
}