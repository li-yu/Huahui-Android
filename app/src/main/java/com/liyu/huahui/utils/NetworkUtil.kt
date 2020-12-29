package com.liyu.huahui.utils

import android.content.Context
import android.net.ConnectivityManager

/**
 * copied from https://github.com/Blankj/AndroidUtilCode
 * Thanks
 * Updated by ultranity on 2020/9/20.
 * Created by liyu on 2017/7/11.
 */
object NetworkUtil {
    @JvmStatic
    fun isConnected(ctx:Context): Boolean {
            val info = (ctx.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo
            return info != null && info.isConnected
        }
}