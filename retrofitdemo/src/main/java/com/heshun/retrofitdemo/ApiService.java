package com.heshun.retrofitdemo;

import com.heshun.retrofitdemo.pojo.UpdateInfo;

import org.json.JSONObject;

import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * author：Jics
 * 2016/10/18 10:01
 */
public interface ApiService {
//	-----------get--------------
	//动态URL测试
	@GET("{A}/{B}")
	Call<BookSearchResponse> getSearchBooksPath(@Path("B") String search, @Path("A") String book, @Query("q") String name);

	//固定URL测试
	@GET("book/search")
	Call<BookSearchResponse> getSearchBooks(@Query("q") String name, @Query("tag") String tag, @Query("start")
			int start, @Query("count") int count);

	//固定URL测试Map参数
	@GET("book/search")
	Call<BookSearchResponse> getSearchBooksMap(@QueryMap Map<String, Object> options);
	//配合Rxjava测试
	@GET("book/search")
	Observable<BookSearchResponse> getRxBooks(@Query("q") String name);

	//返回原始的json数据
	@GET("book/search")
	Call<ResponseBody> getJsonBooks(@Query("q") String name);
	//返回https原始的json数据
	@GET("Test/jcs")
	Call<ResponseBody> getHttpsJsonBooks(@Query("username") String name, @Query("userpassword") String pass);

//--------------post--------------
	@POST("api/app/version/updateVersion")
	Observable<ResponseBody> getUpdateInfo(@Body String string);

	@Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
	@POST("api/app/version/updateVersion")
	Observable<ResponseBody> getUpdateInfoMultipart(@Body RequestBody body);

	@Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
	@POST("api/app/version/updateVersion")
	Observable<UpdateInfo> getUpdateInfoMultipartPojo(@Body RequestBody body);

	//Field方法
	@FormUrlEncoded
	@POST("api/app/version/updateVersion")
	Observable<UpdateInfo> getUpdateInfoField(@Field("versionNo") int versionNo);
	//FieldMap方法
	@FormUrlEncoded
	@POST("api/app/version/updateVersion")
	Observable<UpdateInfo> getUpdateInfoFieldMap(@FieldMap Map<String, Integer> map);
	//--------------自定义转换器 post--------------
	@POST("api/app/version/updateVersion")
	Observable<JSONObject> getUpdateInfoConverter(@Body JSONObject jsonObject);
}
