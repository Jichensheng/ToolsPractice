package com.heshun.retrofitdemo;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.heshun.retrofitdemo.ToolsUitl.DataManager;
import com.heshun.retrofitdemo.transmission.FileCallback;
import com.heshun.retrofitdemo.http.HttpsUtils;
import com.heshun.retrofitdemo.http.JsonConverterFactory;
import com.heshun.retrofitdemo.http.RetrofitClient;
import com.heshun.retrofitdemo.http.RxBus;
import com.heshun.retrofitdemo.interceptor.HttpInterceptor;
import com.heshun.retrofitdemo.interceptor.LoggerInterceptor;
import com.heshun.retrofitdemo.pojo.BookEn;
import com.heshun.retrofitdemo.pojo.DataBean;
import com.heshun.retrofitdemo.pojo.UpdateInfo;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
	private static final String TAG = "jics:  ";
	private TextView asyncText;
	private EditText editText;
	private Button button, btnrx, btnjson, btnpost, btn_down,btn_rxbus;
	private CompositeSubscription rxSubscriptions = new CompositeSubscription();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		asyncText = (TextView) findViewById(R.id.asyncText);
		editText = (EditText) findViewById(R.id.et);
		button = (Button) findViewById(R.id.btn);
		btnrx = (Button) findViewById(R.id.btnrx);
		btnjson = (Button) findViewById(R.id.btnjson);
		btnpost = (Button) findViewById(R.id.btnpost);
		btn_down = (Button) findViewById(R.id.btn_down);
		btn_rxbus = (Button) findViewById(R.id.btn_rxbus);
		button.setOnClickListener(this);
		btnjson.setOnClickListener(this);
		btnrx.setOnClickListener(this);
		btnpost.setOnClickListener(this);
		btn_down.setOnClickListener(this);
		btn_rxbus.setOnClickListener(this);
		//响应Rxbus
		rxSubscriptions.add(RxBus.getDefault().toObservable(TestEvent.class).subscribe(new Action1<TestEvent>() {
			@Override
			public void call(TestEvent testEvent) {
				asyncText.setText(testEvent.getContent());
			}
		}));
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (!rxSubscriptions.isUnsubscribed()) {
			rxSubscriptions.unsubscribe();
		}
	}

	@Override
	public void onClick(View view) {
		String name = editText.getText().toString();
		asyncText.setText("请稍后……");
		if (name.length() < 1) {
			name = "小王子";
		}
		switch (view.getId()) {
			case R.id.btn:
//				doSearch(name);
//				getHttpsJsonBooks("ds");//success
//				getName("小王子");//success
//				getPost();//success
				getHttps();
				break;
			case R.id.btnrx:
				getRxBook(name);
				break;
			case R.id.btnjson:
				getJsonBooks(name);
				break;
			case R.id.btnpost:
//				getPostJson();//fail
//				getJsonField(7);//fail
//				getPostJsonMultipart(7);//success
//				getPostPojo();//success
//				getUpdateInfoConvert();//自定义转换器success
				getUpdateInfoConvertHeshun();//success
				break;
			case R.id.btn_down:
				if (btn_down.getText().equals("下载文件")) {
					btn_down.setText("取消下载");
					downloadFile();
				}else{
					btn_down.setText("下载文件");
					RetrofitClient.getInstance(RetrofitClient.BASE_DOWNLOAD,RetrofitClient.TYPE_DOWNLOAD).cancelLoading();
					asyncText.setText("已取消");
				}
				break;
			case R.id.btn_rxbus:
				//发射事件
				RxBus.getDefault().post(new TestEvent(System.currentTimeMillis()+""));
				break;
		}
	}

	/**
	 * 最普通的
	 *
	 * @param name
	 */
	private void doSearch(String name) {
		Retrofit retrofit = new Retrofit.Builder()
				.baseUrl("https://api.douban.com/v2/")
				.addConverterFactory(GsonConverterFactory.create())
				.build();
		ApiService service = retrofit.create(ApiService.class);
		//固定url
//		Call<BookSearchResponse> call = service.getSearchBooks("小王子", null, 0, 21);
		//动态url
//		Call<BookSearchResponse> call = service.getSearchBooksPath("search", "book", name);
		Map map = new HashMap();
		map.put("q", name);
		map.put("tag", "童话");
		map.put("start", 0);
		map.put("count", 5);
		//固定url的map参数形式
		Call<BookSearchResponse> call = service.getSearchBooksMap(map);
		final String names = name;
		call.enqueue(new Callback<BookSearchResponse>() {

			@Override
			public void onResponse(Call<BookSearchResponse> call, Response<BookSearchResponse> response) {
				if (response != null) {
					List<BookEn> bookEns = response.body().books;
					StringBuffer stringBuffer = new StringBuffer();
					for (int i = 0; i < bookEns.size(); i++) {
						BookEn bookEn = bookEns.get(i);
						stringBuffer.append("默认所在线程：\n" + Thread.currentThread().getName() + "\n\n第" + i + "本\n\n\n作者信息：\n"
								+ bookEn.getAuthor_intro() + "\n\n" + names + "简介：\n"
								+ bookEn.summary + "\n封面地址：\n" + bookEn.getImages().getMedium());

					}
					asyncText.setText(stringBuffer.toString());
				}
			}

			@Override
			public void onFailure(Call<BookSearchResponse> call, Throwable t) {

			}
		});
	}

	/**
	 * 结合Rxjava完成回调处理
	 *
	 * @param name
	 */
	private void getRxBook(String name) {
		Retrofit retrofit = new Retrofit.Builder()
				.baseUrl("https://api.douban.com/v2/")
				.addConverterFactory(GsonConverterFactory.create())
				.addCallAdapterFactory(RxJavaCallAdapterFactory.create())
				.build();
		ApiService apiService = retrofit.create(ApiService.class);
		apiService.getRxBooks(name)
				.subscribeOn(Schedulers.io())//必要
				.observeOn(AndroidSchedulers.mainThread())//必要
				.subscribe(new Observer<BookSearchResponse>() {
					@Override
					public void onCompleted() {
						Log.e(TAG, "onCompleted: 完成");
					}

					@Override
					public void onError(Throwable e) {
						Log.e(TAG, "onError: " + e.toString());
					}

					@Override
					public void onNext(BookSearchResponse bookSearchResponse) {
						if (bookSearchResponse != null) {
							List<BookEn> bookEns = bookSearchResponse.books;
							StringBuffer stringBuffer = new StringBuffer();
							for (int i = 0; i < bookEns.size(); i++) {
								BookEn bookEn = bookEns.get(i);
								stringBuffer.append("observeOn所在线程：\n" + Thread.currentThread().getName() + "\n\n第" + i + "本\n\n\n作者信息：\n"
										+ bookEn.getAuthor_intro() + "\n\n简介：\n"
										+ bookEn.summary + "\n封面地址：\n" + bookEn.getImages().getMedium());

							}
							asyncText.setText(stringBuffer.toString());
						}
					}
				});
	}

	/**
	 * 自定义的工具类get测试
	 *
	 * @param name
	 */
	private void getName(String name) {
		//参数
		Map<String, Object> map = new HashMap<>();
		map.put("q", name);
		map.put("tag", "童话");
		map.put("start", 0);
		map.put("count", 5);
		RetrofitClient.getInstance(RetrofitClient.BASE_DOUBAN, RetrofitClient.TYPE_NORMAL).get("book/search", map, new Observer<JSONObject>() {
			@Override
			public void onCompleted() {

			}

			@Override
			public void onError(Throwable e) {

			}

			@Override
			public void onNext(JSONObject jsonObject) {
				asyncText.setText(jsonObject.toString());
			}
		});
	}

	private void getHttps() {
		String restUrl = "book/search?q=小王子";
		String restUrl2 = "api/app/news/appNewsList/5/1/1";
		Map<String, Object> map = new HashMap<>();
		map.put("tag", "童话");
		map.put("start", 0);
		map.put("count", 5);
		RetrofitClient.getInstance(RetrofitClient.BASE_HESHUN, RetrofitClient.TYPE_NORMAL)
				.get(restUrl2, defaultResultHandler);
	}

	private void getPost() {
		JSONObject jsonObject = null;
		try {
			jsonObject = new JSONObject("{\"versionNo\":7}");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		RetrofitClient.getInstance(RetrofitClient.BASE_HESHUN, RetrofitClient.TYPE_NORMAL)
				.post("api/app/version/updateVersion", jsonObject, resultHandler);
	}

	/**
	 * 获取最原始的json数据
	 *
	 * @param name
	 */
	private void getJsonBooks(String name) {
		Retrofit retrofit = new Retrofit.Builder()
				.baseUrl("https://api.douban.com/v2/")
				.build();
		ApiService apiService = retrofit.create(ApiService.class);
		apiService.getJsonBooks(name).enqueue(new Callback<ResponseBody>() {
			@Override
			public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
				try {
					asyncText.setText(response.body().string());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(Call<ResponseBody> call, Throwable t) {

			}
		});
	}

	/**
	 * 获取最原始的json数据
	 *
	 * @param name
	 */
	private void getHttpsJsonBooks(String name) {

		OkHttpClient okHttpClient = null;
		try {
			okHttpClient = HttpsUtils.getBuilder(this.getAssets().open("client.bks"), "123456", this.getAssets().open("server.cer"))
					.addNetworkInterceptor(new LoggerInterceptor())
					.build();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Retrofit retrofit = new Retrofit.Builder()
				.client(okHttpClient)
				.baseUrl("https://172.31.59.72:8443/")
//				.baseUrl("http://172.31.59.72:8080/")
				.build();
		ApiService apiService = retrofit.create(ApiService.class);
		apiService.getHttpsJsonBooks("names", "asdf").enqueue(new Callback<ResponseBody>() {
			@Override
			public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
				try {
					asyncText.setText(response.body().string());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(Call<ResponseBody> call, Throwable t) {

			}
		});
	}

	/**
	 * post请求
	 */
	private void getPostJson() {

		//通过build方法构建OkHttpClient
		OkHttpClient.Builder builder = new OkHttpClient.Builder();
		builder.addInterceptor(new HttpInterceptor());

		Retrofit retrofit = new Retrofit.Builder()
//				.client(builder.build())
				.callFactory(builder.build())
				.addCallAdapterFactory(RxJavaCallAdapterFactory.create())
				.baseUrl("http://sz.app.jsclp.cn/cpm/")
				.build();
		ApiService apiService = retrofit.create(ApiService.class);
		apiService.getUpdateInfo("{\"versionNo\":7}")
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Observer<ResponseBody>() {
					@Override
					public void onCompleted() {

					}

					@Override
					public void onError(Throwable e) {

					}

					@Override
					public void onNext(ResponseBody responseBody) {
						try {
							String body = responseBody.string();
							asyncText.setText(body);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				});

	}

	/**
	 * post请求相应泛型为默认的
	 *
	 * @param versionNo
	 */
	private void getPostJsonMultipart(int versionNo) {
		Retrofit retrofit = new Retrofit.Builder()
				.baseUrl("http://sz.app.jsclp.cn/cpm/")
				.addCallAdapterFactory(RxJavaCallAdapterFactory.create())
				.build();
		ApiService apiService = retrofit.create(ApiService.class);
		RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), "{\"versionNo\":7}");
		apiService.getUpdateInfoMultipart(body)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Observer<ResponseBody>() {
					@Override
					public void onCompleted() {

					}

					@Override
					public void onError(Throwable e) {
						Log.e(TAG, "onError: " + e.toString());
					}

					@Override
					public void onNext(ResponseBody responseBody) {
						try {
							String body = responseBody.string();
							asyncText.setText(body);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				});
	}

	/**
	 * post请求
	 * 响应泛型为自定义的pojo
	 */
	private void getPostPojo() {
		Retrofit retrofit = new Retrofit.Builder()
				.baseUrl("http://sz.app.jsclp.cn/cpm/")
				.addCallAdapterFactory(RxJavaCallAdapterFactory.create())
				.addConverterFactory(GsonConverterFactory.create())
				.build();
		ApiService apiService = retrofit.create(ApiService.class);
		final RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), "{\"versionNo\":7}");
		apiService.getUpdateInfoMultipartPojo(body)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Observer<UpdateInfo>() {
					@Override
					public void onCompleted() {

					}

					@Override
					public void onError(Throwable e) {

					}

					@Override
					public void onNext(UpdateInfo updateInfo) {
						StringBuilder sb = new StringBuilder();
						sb.append("请求状态：\n" + (updateInfo.isSucc() ? "成功" : "失败") + "\n\n");
						sb.append("返回状态码：\n" + updateInfo.getStatusCode() + "\n\n");
						sb.append("msg：\n" + updateInfo.getMsg() + "\n\n");
						sb.append("时刻：\n" + updateInfo.getTime() + "\n\n");
						DataBean datebean = updateInfo.getData();
						DataBean.BodyBean bodybean = datebean.getBody();
						sb.append("版本号：\n" + bodybean.getVersionNo() + "\n\n");
						sb.append("版本名称：\n" + bodybean.getVersionName() + "\n\n");
						sb.append("apk下载地址：\n" + bodybean.getLoadAddress() + "\n\n");
						sb.append("更新内容：\n" + bodybean.getContent() + "\n\n");
						sb.append("签名：\n" + bodybean.getSign() + "\n\n");

						asyncText.setText(sb.toString());
					}
				});

	}

	/**
	 * Field方式的post请求
	 * 自定义pojo
	 *
	 * @param versionNo
	 */
	private void getJsonField(int versionNo) {
		Retrofit retrofit = new Retrofit.Builder()
				.addCallAdapterFactory(RxJavaCallAdapterFactory.create())
				.baseUrl("http://sz.app.jsclp.cn/cpm/")
				.addConverterFactory(GsonConverterFactory.create())
				.build();
		ApiService apiService = retrofit.create(ApiService.class);
		apiService.getUpdateInfoField(versionNo).subscribeOn(Schedulers.io())
				.subscribeOn(AndroidSchedulers.mainThread())
				.subscribe(new Observer<UpdateInfo>() {
					@Override
					public void onCompleted() {
					}

					@Override
					public void onError(Throwable e) {
						asyncText.setText(e.toString());
					}

					@Override
					public void onNext(UpdateInfo updateInfo) {
						StringBuilder sb = new StringBuilder();
						sb.append("请求状态：\n" + (updateInfo.isSucc() ? "成功" : "失败") + "\n\n");
						sb.append("返回状态码：\n" + updateInfo.getStatusCode() + "\n\n");
						sb.append("msg：\n" + updateInfo.getMsg() + "\n\n");
						sb.append("时刻：\n" + updateInfo.getTime() + "\n\n");
						DataBean datebean = updateInfo.getData();
						DataBean.BodyBean bodybean = datebean.getBody();
						sb.append("版本号：\n" + bodybean.getVersionNo() + "\n\n");
						sb.append("版本名称：\n" + bodybean.getVersionName() + "\n\n");
						sb.append("apk下载地址：\n" + bodybean.getLoadAddress() + "\n\n");
						sb.append("更新内容：\n" + bodybean.getContent() + "\n\n");
						sb.append("签名：\n" + bodybean.getSign() + "\n\n");

						asyncText.setText(sb.toString());
					}
				});
	}

	/**
	 * 自定义json转换器post请求
	 * 自定义pojo
	 */
	public void getUpdateInfoConvert() {
		//设置超时
		OkHttpClient client = new OkHttpClient.Builder()
				.connectTimeout(12, TimeUnit.SECONDS)
				.writeTimeout(12, TimeUnit.SECONDS)
				.addNetworkInterceptor(new LoggerInterceptor())
				.readTimeout(12, TimeUnit.SECONDS)
				.build();
		Retrofit retrofit = new Retrofit.Builder()
				.client(client)
				.baseUrl("http://sz.app.jsclp.cn/cpm/")
				.addCallAdapterFactory(RxJavaCallAdapterFactory.create())
				.addConverterFactory(JsonConverterFactory.create())
				.build();
		ApiService apiService = retrofit.create(ApiService.class);
		final String jsonString = "{\"versionNo\":7}";
		try {
			JSONObject jsonObject = new JSONObject(jsonString);
			apiService.getUpdateInfoConverter(jsonObject)
					.subscribeOn(Schedulers.io())
					.observeOn(AndroidSchedulers.mainThread())
					.subscribe(new Observer<JSONObject>() {
						@Override
						public void onCompleted() {
//							asyncText.setText("完成"+asyncText.getText().toString());
						}

						@Override
						public void onError(Throwable e) {
							if (e instanceof SocketTimeoutException) {
								//网络超时，重构时可以通过借口实现通用回调
								asyncText.setText(TAG + "超时ScoketTimoutException");

							} else if (e instanceof ConnectException) {
								//网络错误
								asyncText.setText(TAG + "网络错误ConnectException");
							} else if (e instanceof RuntimeException) {
								//系统错误
								asyncText.setText(TAG + "系统错误RuntimeException");
							} else if (e instanceof TimeoutException) {
								//网络超时
								asyncText.setText(TAG + "超时TimeoutException");
							}
							try {
								asyncText.setText(asyncText.getText().toString() + "：" + exception(e));
							} catch (IOException e1) {
								e1.printStackTrace();
							}
						}

						@Override
						public void onNext(JSONObject jsonObject) {
							/*
							//方法一：使用Gson来将json字符串解析成pojo
							UpdateInfo updateInfo=new Gson().fromJson(jsonObject.toString(),UpdateInfo.class);
							*/

							//方法二：使用fastjson将json字符串解析出pojo
							UpdateInfo updateInfo = com.alibaba.fastjson.JSONObject.parseObject(jsonObject.toString(), UpdateInfo.class);

							StringBuilder sb = new StringBuilder();
							sb.append("请求状态：\n" + (updateInfo.isSucc() ? "成功" : "失败") + "\n\n");
							sb.append("返回状态码：\n" + updateInfo.getStatusCode() + "\n\n");
							sb.append("msg：\n" + updateInfo.getMsg() + "\n\n");
//							sb.append("时刻：\n"+updateInfo.getTime()+"\n\n");
							sb.append("时刻：\n" + (new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss").format(new Date(updateInfo.getTime()))) + "\n\n");
							DataBean datebean = updateInfo.getData();
							DataBean.BodyBean bodybean = datebean.getBody();
							sb.append("版本号：\n" + bodybean.getVersionNo() + "\n\n");
							sb.append("版本名称：\n" + bodybean.getVersionName() + "\n\n");
							sb.append("apk下载地址：\n" + bodybean.getLoadAddress() + "\n\n");
							sb.append("更新内容：\n" + bodybean.getContent() + "\n\n");
							sb.append("签名：\n" + bodybean.getSign() + "\n\n");

							asyncText.setText(sb.toString());
						}
					});
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 基于baseUrl灵活的单例模式
	 */
	public void getUpdateInfoConvertHeshun() {
		JSONObject jsonObject;
		try {
			jsonObject = new JSONObject("{\"versionNo\":7}");
			String restUrl = "api/app/version/updateVersion";
			RetrofitClient.getInstance(RetrofitClient.BASE_HESHUN, RetrofitClient.TYPE_NORMAL)
					.post(restUrl, jsonObject, resultHandler);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private void downloadFile() {
		String fileName = "WPSOffice_V9.9.5_mumayi_2c51f.apk";
		String fileStoreDir = Environment.getExternalStorageDirectory().getAbsolutePath() + File
				.separator + "M_DEFAULT_DIR";
		String fileStoreName = fileName;
		RetrofitClient.getInstance(RetrofitClient.BASE_DOWNLOAD, RetrofitClient.TYPE_DOWNLOAD)
				.loadFileByName(fileName, new FileCallback(fileStoreDir, fileStoreName) {
					@Override
					public void onSuccess(File file) {
						super.onSuccess(file);
						asyncText.setText("下载完成！");
					}

					@Override
					public void progress(long progress, long total) {
						asyncText.setText(String.format("正在下载：(%s/%s)",
								DataManager.getFormatSize(progress),
								DataManager.getFormatSize(total)));
					}

					@Override
					public void onFailure(Call<ResponseBody> call, Throwable t) {

					}
				});
	}

	/**
	 * 打印错误信息
	 *
	 * @param t
	 * @return
	 * @throws IOException
	 */
	private static String exception(Throwable t) throws IOException {
		if (t == null)
			return null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			t.printStackTrace(new PrintStream(baos));
		} finally {
			baos.close();
		}
		return baos.toString();
	}

	//普通的回调
	private Observer<JSONObject> defaultResultHandler = new Observer<JSONObject>() {
		@Override
		public void onCompleted() {

		}

		@Override
		public void onError(Throwable e) {

		}

		@Override
		public void onNext(JSONObject jsonObject) {
			asyncText.setText(jsonObject.toString());
		}
	};
	//处理第四个按钮返回结果
	private Observer<JSONObject> resultHandler = new Observer<JSONObject>() {
		@Override
		public void onCompleted() {

		}

		@Override
		public void onError(Throwable e) {

		}

		@Override
		public void onNext(JSONObject jsonObject) {
			UpdateInfo updateInfo = com.alibaba.fastjson.JSONObject.parseObject(jsonObject.toString(), UpdateInfo.class);

			StringBuilder sb = new StringBuilder();
			sb.append("请求状态：\n" + (updateInfo.isSucc() ? "成功" : "失败") + "\n\n");
			sb.append("返回状态码：\n" + updateInfo.getStatusCode() + "\n\n");
			sb.append("msg：\n" + updateInfo.getMsg() + "\n\n");
//							sb.append("时刻：\n"+updateInfo.getTime()+"\n\n");
			sb.append("时刻：\n" + (new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss").format(new Date(updateInfo.getTime()))) + "\n\n");
			DataBean datebean = updateInfo.getData();
			DataBean.BodyBean bodybean = datebean.getBody();
			sb.append("版本号：\n" + bodybean.getVersionNo() + "\n\n");
			sb.append("版本名称：\n" + bodybean.getVersionName() + "\n\n");
			sb.append("apk下载地址：\n" + bodybean.getLoadAddress() + "\n\n");
			sb.append("更新内容：\n" + bodybean.getContent() + "\n\n");
			sb.append("签名：\n" + bodybean.getSign() + "\n\n");

			asyncText.setText(sb.toString());
		}
	};
}
