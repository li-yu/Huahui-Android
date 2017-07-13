package com.liyu.huahui.http.api;

import com.liyu.huahui.model.YoudaoResponse;

import retrofit2.http.GET;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by liyu on 2017/7/12.
 */

public interface YoudaoApi {

    @GET
    Observable<YoudaoResponse> get(@Url String url);
}
