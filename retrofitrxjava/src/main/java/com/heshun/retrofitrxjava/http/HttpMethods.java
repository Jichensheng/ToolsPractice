package com.heshun.retrofitrxjava.http;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.heshun.retrofitrxjava.entity.HeadDefault;
import com.heshun.retrofitrxjava.entity.pojo.CommonUser;
import com.heshun.retrofitrxjava.entity.pojo.Order;
import com.heshun.retrofitrxjava.entity.pojo.Pic;
import com.heshun.retrofitrxjava.entity.pojo.PileStation;
import com.heshun.retrofitrxjava.entity.pojo.UpdataVersion;
import com.heshun.retrofitrxjava.entity.stable.Data;
import com.heshun.retrofitrxjava.entity.stable.DefaultResult;
import com.heshun.retrofitrxjava.entity.stable.HttpResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 用户和api接口的桥接
 * author：Jics
 * 2017/4/27 13:49
 */
public class HttpMethods {

	public static final String BASE_URL = "http://sun.app.jsclp.cn/cpm/api/app/";

	private static final int DEFAULT_TIMEOUT = 5;

	private Retrofit retrofit;
	private ApiService apiService;

	//构造方法私有
	private HttpMethods() {
		//手动创建一个OkHttpClient并设置超时时间
		OkHttpClient.Builder builder = new OkHttpClient.Builder();
		builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
		builder.addInterceptor(new LoggingInterceptor());

		retrofit = new Retrofit.Builder()
				.client(builder.build())
				.addConverterFactory(GsonConverterFactory.create())
				.addCallAdapterFactory(RxJavaCallAdapterFactory.create())
				.baseUrl(BASE_URL)
				.build();

		apiService = retrofit.create(ApiService.class);
	}

	//在访问HttpMethods时创建单例
	private static class SingletonHolder {
		private static final HttpMethods INSTANCE = new HttpMethods();
	}

	//获取单例
	public static HttpMethods getInstance() {
		return SingletonHolder.INSTANCE;
	}

	/**
	 * 获取新闻图片
	 * @param subscriber
	 * @param pageSize
	 * @param page
	 * @param orgId
	 */
	public void getPic(Subscriber subscriber, int pageSize, int page, int orgId) {
		//getPic之后发射的数据类型是Interface定义的HttpResult2<HeadDefault,List<Pic>>
		//map处理之后的类型是Data<HeadDefault,List<Pic>>
		//观察者Subscriber的onNext(T t)中接收的类型就是被观察者最后发射的Data<HeadDefault,List<Pic>>,然后再做处理
		Observable observable1 = apiService.getPic(pageSize, page, orgId).map(new HttpResultFunc<HeadDefault, List<Pic>>());

		toSubscribe(observable1, subscriber);
	}

	/**
	 * 获取订单列表
	 * @param subscriber
	 * @param token
	 * @param status
	 * @param pageSize
	 * @param page
	 */
	public void getOrderList(Subscriber subscriber, String token, int status, int pageSize, int page) {
		Observable observable1 = apiService.getOrderList(token, status, pageSize, page).map(new HttpResultFunc<HeadDefault, List<Order>>());

		//将被观察者与观察者关联
		toSubscribe(observable1, subscriber);
	}

	/**
	 * 根据地点获取附近充电桩
	 * @param subscriber
	 * @param lon
	 * @param lat
	 * @param orgId
	 * @param pageSize
	 * @param page
	 * @param city
	 */
	public void getPileStation(Subscriber subscriber, double lon, double lat,
							   int orgId, int pageSize, int page, String city) {
		Observable observable1 = apiService.getPileStation(lon, lat, orgId, pageSize, page, city).map(new HttpResultFunc<HeadDefault, List<PileStation>>());

		//将被观察者与观察者关联
		toSubscribe(observable1, subscriber);
	}

	/**
	 * 获取用户的余额
	 * @param subscriber
	 * @param token
	 */
	public void getMoney(Subscriber subscriber,String token) {
		Map<String, String> map = new HashMap<>();
		map.put("token",token);
		JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(map));
		Observable observable1 = apiService.getMoney(jsonObject).map(new HttpResultFunc<HeadDefault, CommonUser>());

		//将被观察者与观察者关联
		toSubscribe(observable1, subscriber);
	}

	/**
	 * 检测更新
	 * @param subscriber
	 * @param versionNo
	 */
	public void checkUpdata(Subscriber subscriber, int versionNo) {
		JSONObject jsonObject = JSON.parseObject("{\"versionNo\":" + versionNo + "}");
		Observable observable = apiService.checkUpdata(jsonObject).map(new HttpResultFunc<HeadDefault, UpdataVersion>());
		toSubscribe(observable, subscriber);
	}

	/**
	 * 公共部分的代码
	 *
	 * @param o
	 * @param s
	 * @param <T>
	 */
	private <T> void toSubscribe(Observable<T> o, Subscriber<T> s) {
		o.subscribeOn(Schedulers.io())
				.unsubscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(s);
	}

	/**
	 * 用来统一处理Http的resultCode,并将HttpResult的Data部分剥离出来返回给subscriber
	 * Func1<T, R>
	 *
	 * @param <E> Head
	 * @param <T> Body
	 *            情形一：{
	 *            "succ": true,
	 *            "statusCode": 200,
	 *            "msg": "消息",
	 *            "data": {
	 *            },
	 *            "time": 1476842649455
	 *            }
	 *            情形二： {"succ":false,"statusCode":200,"msg":"当前已是最新版本！","time":1493693997958}
	 *            情形三： {"succ":true,"statusCode":200,"msg":"查询成功","data":{"head":{"total":0}},"time":1493703004483}
	 */
	private class HttpResultFunc<E, T> implements Func1<HttpResult<E, T>, Data<E, T>> {
		@Override
		public Data<E, T> call(HttpResult<E, T> etHttpResult) {
			if (etHttpResult.getData() == null || etHttpResult.getData().getBody() == null) {
				DefaultResult<E, T> defaultResult = new DefaultResult<>();
				defaultResult.setEtHttpResult(etHttpResult);
				return defaultResult;
			}
			if (etHttpResult.getStatusCode() != 200)
				throw new ApiException(etHttpResult.getStatusCode());
			return etHttpResult.getData();
		}

	}

}
