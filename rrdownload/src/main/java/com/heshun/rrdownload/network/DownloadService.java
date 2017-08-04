package com.heshun.rrdownload.network;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import io.reactivex.Observable;

/**
 * Created by Jcs on 16/7/5.
 */
public interface DownloadService {


    @Streaming
    @GET
    Observable<ResponseBody> download(@Url String url);//流是从这边来的
}
