package com.heshun.retrofitrxjava.http;

import com.heshun.retrofitrxjava.entity.HeadDefault;
import com.heshun.retrofitrxjava.entity.Order;
import com.heshun.retrofitrxjava.entity.stable.HttpResult;
import com.heshun.retrofitrxjava.entity.Pic;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by liukun on 16/3/9.
 */
public interface ApiService {

    //Observable<T>的泛型即为被观察者原始的发射数据类型
    @GET("news/appNewsList/{pageSize}/{page}/{orgId}")
    Observable<HttpResult<HeadDefault,List<Pic>>> getPic(@Path("pageSize") int pageSize, @Path("page") int page, @Path("orgId") int orgId);

    //findOrderList/NzY2MTUxZmI2YTE5NGEwYzhlODdmM2RiYmI5ZTY5ODA=/1/10/1
    @GET("findOrderList/{token}/{status}/{pageSize}/{page}")
    Observable<HttpResult<HeadDefault,List<Order>>> getOrderList(@Path("token") String token,@Path("status") int status,@Path("pageSize") int pageSize,@Path("page") int page);
}
