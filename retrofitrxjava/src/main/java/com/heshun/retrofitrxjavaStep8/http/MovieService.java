package com.heshun.retrofitrxjavaStep8.http;

import com.heshun.retrofitrxjavaStep8.entity.HttpResult;
import com.heshun.retrofitrxjavaStep8.entity.Movies;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by liukun on 16/3/9.
 */
public interface MovieService {

//    @GET("top250")
//    Call<MovieEntity> getTopMovie(@Query("start") int start, @Query("count") int count);

//    @GET("top250")
//    Observable<MovieEntity> getTopMovie(@Query("start") int start, @Query("count") int count);

//    @GET("top250")
//    Observable<HttpResult<List<Movies>>> getTopMovie(@Query("start") int start, @Query("count") int count);

    @GET("top250")
    Observable<HttpResult<List<Movies>>> getTopMovie(@Query("start") int start, @Query("count") int count);
}
