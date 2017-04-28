package com.heshun.retrofitrxjava.http;

import com.heshun.retrofitrxjava.entity.HeadDefault;
import com.heshun.retrofitrxjava.entity.stable.HttpResult;
import com.heshun.retrofitrxjava.entity.Pic;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by liukun on 16/3/9.
 */
public interface TestService {

    @GET("news/appNewsList/{pageSize}/{page}/{orgId}")
    //Observable<T>的泛型即为被观察者原始的发射数据类型
    Observable<HttpResult<HeadDefault,List<Pic>>> getPic(@Path("pageSize") int pageSize, @Path("page") int page, @Path("orgId") int orgId);

}
