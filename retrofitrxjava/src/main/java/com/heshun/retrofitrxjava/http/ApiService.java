package com.heshun.retrofitrxjava.http;

import com.alibaba.fastjson.JSONObject;
import com.heshun.retrofitrxjava.entity.HeadDefault;
import com.heshun.retrofitrxjava.entity.pojo.CommonUser;
import com.heshun.retrofitrxjava.entity.pojo.Order;
import com.heshun.retrofitrxjava.entity.pojo.Pic;
import com.heshun.retrofitrxjava.entity.pojo.PileStation;
import com.heshun.retrofitrxjava.entity.pojo.UpdataVersion;
import com.heshun.retrofitrxjava.entity.stable.HttpResult;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by liukun on 16/3/9.
 */
public interface ApiService {

    //Observable<T>的泛型即为被观察者原始的发射数据类型
    @GET("news/appNewsList/{pageSize}/{page}/{orgId}")
    Observable<HttpResult<HeadDefault,List<Pic>>> getPic(@Path("pageSize") int pageSize, @Path("page") int page, @Path("orgId") int orgId);

    @GET("findOrderList/{token}/{orderType}/{pageSize}/{page}")
    Observable<HttpResult<HeadDefault,List<Order>>> getOrderList(@Path("token") String token,@Path("orderType") int orderType,@Path("pageSize") int pageSize,@Path("page") int page);

    //http://sun.app.jsclp.cn/cpm/api/app/pileStation/list/120.674771/31.355091/50/10/1?city=city
    @GET("pileStation/list/{lon}/{lat}/{orgId}/{pageSize}/{page}")
    Observable<HttpResult<HeadDefault,List<PileStation>>> getPileStation(@Path("lon") double lon,@Path("lat") double lat,
                                                                         @Path("orgId") int orgId,@Path("pageSize") int pageSize,@Path("page") int page,@Query("city") String city);

    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("member/query")
    Observable<HttpResult<HeadDefault,CommonUser>> getMoney(@Body JSONObject jsonObject);

    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("version/updateVersion")
    Observable<HttpResult<HeadDefault,UpdataVersion>> checkUpdata(@Body JSONObject jsonObject);

}
