package com.heshun.retrofitrxjava.http;


import com.heshun.retrofitrxjava.entity.Order;
import com.heshun.retrofitrxjava.entity.stable.Data;
import com.heshun.retrofitrxjava.entity.HeadDefault;
import com.heshun.retrofitrxjava.entity.stable.HttpResult;
import com.heshun.retrofitrxjava.entity.Pic;

import java.util.List;
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
 * author：Jics
 * 2017/4/27 13:49
 */
public class HttpMethods {

    public static final String BASE_URL = "http://sz.app.jsclp.cn/cpm/api/app/";

    private static final int DEFAULT_TIMEOUT = 5;

    private Retrofit retrofit;
    private ApiService apiService;

    //构造方法私有
    private HttpMethods() {
        //手动创建一个OkHttpClient并设置超时时间
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        retrofit = new Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();

        apiService = retrofit.create(ApiService.class);
    }

    //在访问HttpMethods时创建单例
    private static class SingletonHolder{
        private static final HttpMethods INSTANCE = new HttpMethods();
    }

    //获取单例
    public static HttpMethods getInstance(){
        return SingletonHolder.INSTANCE;
    }

    public void getPic(Subscriber subscriber, int pageSize, int page, int orgId){
        //getPic之后发射的数据类型是Interface定义的HttpResult2<HeadDefault,List<Pic>>
        //map处理之后的类型是Data<HeadDefault,List<Pic>>
        //观察者Subscriber的onNext(T t)中接收的类型就是被观察者最后发射的Data<HeadDefault,List<Pic>>,然后再做处理
        Observable observable1= apiService.getPic(pageSize,page,orgId).map(new HttpResultFunc2<HeadDefault,List<Pic>>());

        //将被观察者与观察者关联
        toSubscribe(observable1, subscriber);
    }
 public void getOrderList(Subscriber subscriber,String token,int status, int pageSize, int page){
     Observable observable1= apiService.getOrderList(token,status,pageSize,page).map(new HttpResultFunc2<HeadDefault,List<Order>>());

     //将被观察者与观察者关联
     toSubscribe(observable1, subscriber);
 }
    /**
     *
     * @param o
     * @param s
     * @param <T>
	 */
    private <T> void toSubscribe(Observable<T> o, Subscriber<T> s){
         o.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s);
    }

    /**
     * 用来统一处理Http的resultCode,并将HttpResult的Data部分剥离出来返回给subscriber
     * Func1<T, R>
     * @param <E> Head
     * @param <T> Body
        {
            "succ": true,
            "statusCode": 200,
            "msg": "消息",
            "data": {
            },
            "time": 1476842649455
        }
     */
    private class HttpResultFunc2<E,T> implements Func1<HttpResult<E,T>, Data<E,T>>{
        @Override
        public Data<E,T> call(HttpResult<E, T> etHttpResult) {
            if (!etHttpResult.isSucc()) {
                throw new ApiException(etHttpResult.getStatusCode());
            }
            return etHttpResult.getData();
        }

    }

}
