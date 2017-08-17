package com.jcs.printertest.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.jcs.printertest.base.BuildConfig;
import com.jcs.printertest.tools.LocalFileManager;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * author：Jics
 * 2017/7/31 13:30
 *
 */
public class NetClient {
	public static final String HOST="http://192.168.191.1:3000/";
	private static ApiService yzuServer;
	private static Retrofit retrofit;
	private NetClient(){

	}
	static {
		Gson gson=new GsonBuilder()
				.setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
				.create();
		OkHttpClient client = genericClient();

		retrofit=new Retrofit.Builder()
				.client(client)
				.baseUrl(HOST)
				.addConverterFactory(GsonConverterFactory.create(gson))
				.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
				.build();
	}
	public static ApiService getInstance(){
		synchronized (NetClient.class){
			if (yzuServer == null) {
				yzuServer=retrofit.create(ApiService.class);
			}
			return yzuServer;
		}
	}

	/**
	 * 带缓存的okhttp
	 * @return
	 */
	public static OkHttpClient genericClient() {

		//缓存路径
		File cacheFile = LocalFileManager.getInstance().getCacheDir(BuildConfig.APP_CACHE_DIR);
		Cache cache = new Cache(cacheFile, BuildConfig.RETROFIT_CACHE_SIZE);//缓存文件为10MB

		OkHttpClient.Builder builder = new OkHttpClient.Builder();
		if (BuildConfig.DEBUG) {
			// Log信息拦截器
			HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
			loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);//这里可以选择拦截级别
			//设置 Debug Log 模式
			builder.addInterceptor(loggingInterceptor);
		}

		builder.connectTimeout(BuildConfig.DEFAULT_TIMEOUT, TimeUnit.SECONDS)
				.cache(cache);
		return builder.build();
	}


}
