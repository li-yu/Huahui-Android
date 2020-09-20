package com.liyu.huahui.http;

import com.liyu.huahui.utils.GsonUtil;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by liyu on 2017/2/20.
 */
public class RetrofitManager {

    private static RetrofitManager instance;
    private static Retrofit retrofit;

    private RetrofitManager() {
        retrofit = new Retrofit.Builder()
                .baseUrl("http://liyuyu.cn")
                .client(getNewClient())
                .addConverterFactory(GsonConverterFactory.create(GsonUtil.getGson()))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

// --Commented out by Inspection START (2020/9/20 15:16):
//    public static void reset() {
//        instance = null;
//    }
// --Commented out by Inspection STOP (2020/9/20 15:16)

    public static RetrofitManager getInstance() {
        if (instance == null) {
            synchronized (RetrofitManager.class) {
                instance = new RetrofitManager();
            }
        }
        return instance;
    }

    public <T> T create(Class<T> service) {
        return retrofit.create(service);
    }

// --Commented out by Inspection START (2020/9/20 15:16):
//    private static OkHttpClient getNewClient() {
//        return new OkHttpClient.Builder()
//                .connectTimeout(10, TimeUnit.SECONDS)
//                .readTimeout(5, TimeUnit.MINUTES)
//                .build();
//    }
// --Commented out by Inspection STOP (2020/9/20 15:16)

}
