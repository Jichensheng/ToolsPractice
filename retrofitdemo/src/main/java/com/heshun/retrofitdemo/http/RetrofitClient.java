package com.heshun.retrofitdemo.http;

import com.heshun.retrofitdemo.ToolsUitl.Tools;
import com.heshun.retrofitdemo.interceptor.LoggerInterceptor;
import com.heshun.retrofitdemo.transmission.FileCallback;
import com.heshun.retrofitdemo.transmission.FileRequestBody;
import com.heshun.retrofitdemo.transmission.FileResponseBody;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.ConnectionPool;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * author：Jics
 * 2016/10/25 09:39
 */

public class RetrofitClient {

	public static final String BASE_DOUBAN = "https://api.douban.com/v2/";
	public static final String BASE_HESHUN = "http://sz.app.jsclp.cn/cpm/";
	public static final String BASE_DOWNLOAD = "https://apka.mumayi.com/2016/10/25/112/1129123/";

	public static final int TYPE_NORMAL = 1;
	public static final int TYPE_DOWNLOAD = 2;

	private static final int DEFAULT_TIMEOUT = 10;
	private static RetrofitClient instance;
	private Retrofit retrofit;
	private OkHttpClient.Builder httpBuilder;
	private BaseApiService baseApiService;
	private static Hashtable<String, RetrofitClient> clientTable;
	private static Call<ResponseBody> call;

	static {
		clientTable = new Hashtable<>();
	}

	/**
	 * 单例——私有构造器
	 * 支持构造具有不同baseUrl的单例
	 *
	 * @param baseUrl
	 */
	private RetrofitClient(String baseUrl) {

		retrofit = new Retrofit.Builder()
				.client(initOkHttpClient())
				.baseUrl(baseUrl)
				.addCallAdapterFactory(RxJavaCallAdapterFactory.create())
				.addConverterFactory(JsonConverterFactory.create())
				.build();
		baseApiService = retrofit.create(BaseApiService.class);
	}

	private RetrofitClient(String baseUrl, int type) {
		retrofit = new Retrofit.Builder()
				.client(initDownloadClient())
				.baseUrl(baseUrl)
				.build();
		baseApiService = retrofit.create(BaseApiService.class);
	}

	/**
	 * 初始化OkHttp client
	 *
	 * @return
	 */
	private OkHttpClient initOkHttpClient() {
//			httpBuilder = HttpsUtils.getBuilder(BaseApplication.getInstance().getAssets().open("client.bks"), "123456", BaseApplication.getInstance().getAssets().open("server.cer"))
		int threadCount = 3;
		if (Tools.getNumCores() != 0) {
			threadCount = 2 * Tools.getNumCores();
		}
		httpBuilder = new OkHttpClient.Builder()
				.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
				.writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
				.readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
				.connectionPool(new ConnectionPool(threadCount, 15, TimeUnit.SECONDS))
				.addNetworkInterceptor(new LoggerInterceptor());
		return httpBuilder.build();
	}

	/**
	 * 初始化OkHttp下载的Client
	 *
	 * @return
	 */
	private OkHttpClient initDownloadClient() {
		OkHttpClient.Builder builder = new OkHttpClient.Builder();
		builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
		builder.networkInterceptors().add(new Interceptor() {
			@Override
			public Response intercept(Chain chain) throws IOException {
				Response originalResponse = chain.proceed(chain.request());
				return originalResponse
						.newBuilder()
						.body(new FileResponseBody(originalResponse))
						.build();
			}
		});
		return builder.build();
	}

	/**
	 * 初始化上传的okhttpclient
	 * @return
	 */
	private void upload() {
		OkHttpClient.Builder builder = new OkHttpClient.Builder();
		File file=new File("/storage/emulated/0/Download/11.jpg");
		RequestBody requestBody=RequestBody.create(MediaType.parse("image/jpeg"),file);
		MultipartBody.Part.createFormData("file_name",file.getName(), new FileRequestBody(requestBody));

	}

	/**
	 * 通过type判断是普通请求还是下载的
	 * baseUrl可能相同，所以用baseUrl和type作为map的key
	 *
	 * @param baseUrl
	 * @param type
	 * @return
	 */
	public static RetrofitClient getInstance(String baseUrl, int type) {

		switch (type) {
			case TYPE_DOWNLOAD:
				instance = clientTable.get(baseUrl + type);
				if (instance == null) {
					synchronized (RetrofitClient.class) {
						if (instance == null) {
							instance = new RetrofitClient(baseUrl, type);
							clientTable.put(baseUrl + type, instance);
						}
					}
				}
				break;
			default:
				instance = clientTable.get(baseUrl + type);
				if (instance == null) {
					synchronized (RetrofitClient.class) {
						if (instance == null) {
							instance = new RetrofitClient(baseUrl);
							clientTable.put(baseUrl + type, instance);
						}
					}
				}
		}

		return instance;
	}

	/**
	 * 无参数的get请求
	 *
	 * @param url
	 * @param observable
	 */
	public void get(String url, Observer<JSONObject> observable) {
		get(url, null, observable);
	}

	/**
	 * 带多个参数的get请求
	 *
	 * @param url
	 * @param map
	 * @param observable
	 */
	public void get(String url, Map<String, Object> map, Observer<JSONObject> observable) {
		if (map != null) {
			baseApiService
					.getWithParam(url, map)
					.subscribeOn(Schedulers.io())
					.observeOn(AndroidSchedulers.mainThread())
					.subscribe(observable);
		} else {
			baseApiService
					.get(url)
					.subscribeOn(Schedulers.io())
					.observeOn(AndroidSchedulers.mainThread())
					.subscribe(observable);
		}
	}

	/**
	 * post请求
	 *
	 * @param url
	 * @param param
	 * @param observable
	 */
	public void post(String url, JSONObject param, Observer<JSONObject> observable) {
		baseApiService.getUpdateInfoConverter(url, param)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(observable);
	}

	/**
	 * 下载文件
	 *
	 * @param fileName
	 * @param callback
	 */
	public void loadFileByName(String fileName, FileCallback callback) {
		call = baseApiService.loadFile(fileName);
		call.enqueue(callback);
	}

	/**
	 * 取消下载
	 */
	public static void cancelLoading() {
		if (call != null && call.isCanceled() == false) {
			call.cancel();
		}
	}

}












