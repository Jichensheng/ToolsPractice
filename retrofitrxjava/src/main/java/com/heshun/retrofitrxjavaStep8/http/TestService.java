package com.heshun.retrofitrxjavaStep8.http;

import com.heshun.retrofitrxjavaStep8.entity.HeadTest;
import com.heshun.retrofitrxjavaStep8.entity.HttpResult2;
import com.heshun.retrofitrxjavaStep8.entity.Pic;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by liukun on 16/3/9.
 */
public interface TestService {

    @GET("news/appNewsList/{pageSize}/{page}/{orgId}")
    Observable<HttpResult2<HeadTest,List<Pic>>> getPic(@Path("pageSize") int pageSize, @Path("page") int page, @Path("orgId") int orgId);

}
