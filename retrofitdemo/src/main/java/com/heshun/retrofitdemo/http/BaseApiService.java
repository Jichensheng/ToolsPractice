package com.heshun.retrofitdemo.http;

import org.json.JSONObject;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;
import rx.Observable;

/**
 * author：Jics
 * 2016/10/25 10:34
 */
public interface BaseApiService {
	/*
	//url里有中文就会乱码
	@GET("{url}")
	Observable<JSONObject> get(@Path(value = "url",encoded = true) String url);
	*/

	/*
	//中文仍会乱码
	@GET("{url}")
	Observable<JSONObject> getWithParam(@Path("url") String url,@QueryMap Map<String,Object> map);
*/
	/*
	//解决url乱码(不包括中文)的方法二
	@POST("{url}")
	Observable<JSONObject> getUpdateInfoConverterUrl(@Path(value = "url",encoded = true) String url, @Body JSONObject jsonObject);
	*/


	//无参数的get请求
	@GET
	Observable<JSONObject> get(@Url String url);
	//参数map化的get请求
	@GET
	Observable<JSONObject> getWithParam(@Url String url, @QueryMap Map<String, Object> map);

	//post 参数为json
	@POST
	Observable<JSONObject> getUpdateInfoConverter(@Url String url, @Body JSONObject jsonObject);
	/**
	 * 下载数据库、资源
	 *
	 * @param fileName
	 * @return
	 */
	@GET("{fileName}")
	Call<ResponseBody> loadFile(@Path("fileName") String fileName);

	/*上传文件*/
//	@Multipart
//	@POST("AppYuFaKu/uploadHeadImg")
//	Observable<BaseResultEntity<UploadResulte>> uploadImage(@Part("uid") RequestBody uid, @Part("auth_key") RequestBody auth_key, @Part MultipartBody.Part file);
}
