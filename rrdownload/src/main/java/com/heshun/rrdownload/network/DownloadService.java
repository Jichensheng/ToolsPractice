package com.heshun.rrdownload.network;

import com.heshun.rrdownload.bean.Download;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by Jcs on 16/7/5.
 */
public interface DownloadService {


    @Streaming
    @GET
    Observable<ResponseBody> download(@Url String url);//流是从这边来的

    /**
     * 直接post一个实体
     * @param download
     * @return
     */
    @POST("book/reviews")
    Observable<ResponseBody> testPostBody(@Body Download download);

/*    @Multipart
    @POST("upload")
    Call<ResponseBody> upload(@Part("description") RequestBody description,
                              @Part MultipartBody.Part file);*/

    /**
     * multipartBody形式
     * @param file
     * @return
     */
    @Multipart
    @POST("upload/pic")
    Observable<ResponseBody> uploadPic( @Part() MultipartBody.Part file);

    /**
     * RequestBody形式
     * @param Body
     * @return
     */
    @POST("upload/body")
    Observable<ResponseBody> upLoadBody(  @Body RequestBody Body);
}
