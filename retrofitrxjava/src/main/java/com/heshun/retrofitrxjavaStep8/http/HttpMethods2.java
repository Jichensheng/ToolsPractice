package com.heshun.retrofitrxjavaStep8.http;


import com.heshun.retrofitrxjavaStep8.entity.Data;
import com.heshun.retrofitrxjavaStep8.entity.HeadTest;
import com.heshun.retrofitrxjavaStep8.entity.HttpResult2;
import com.heshun.retrofitrxjavaStep8.entity.Pic;

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
 * Created by liukun on 16/3/9.
 */
public class HttpMethods2 {

    public static final String BASE_URL = "http://sz.app.jsclp.cn/cpm/api/app/";

    private static final int DEFAULT_TIMEOUT = 5;

    private Retrofit retrofit;
    private TestService testService;

    //构造方法私有
    private HttpMethods2() {
        //手动创建一个OkHttpClient并设置超时时间
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        retrofit = new Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();

        testService = retrofit.create(TestService.class);
    }

    //在访问HttpMethods时创建单例
    private static class SingletonHolder{
        private static final HttpMethods2 INSTANCE = new HttpMethods2();
    }

    //获取单例
    public static HttpMethods2 getInstance(){
        return SingletonHolder.INSTANCE;
    }

    public void getPic(Subscriber<Pic> subscriber, int pageSize, int page, int orgId){

        Observable observable1=testService.getPic(pageSize,page,orgId).map(new HttpResultFunc2<HeadTest,Pic>());

        //将被观察者与观察者关联，此处的观察者是重点
        toSubscribe(observable1, subscriber);
    }

    /**
     * 让被观察者和观察者更直观
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
     * 脱掉HttpResult<T>层
     * 用来统一处理Http的resultCode,并将HttpResult的Data部分剥离出来返回给subscriber
     * RxJava的map函数只有一个参数，参数一般是Func1，Func1的<I,O>I,O模版分别为输入和输出值的类型，实现Func1的call方法对I类型进行处理后返回O类型数据
     * @param <T>   Subscriber真正需要的数据类型即json的Data部分，此案例T为List<Movies>
     */
    private class HttpResultFunc2<E,T> implements Func1<HttpResult2<E,T>, Data<E,T>>{
        @Override
        public Data<E,T> call(HttpResult2<E, T> etHttpResult2) {
            if (!etHttpResult2.isSucc()) {
                throw new ApiException2(etHttpResult2.getStatusCode());
            }
            return etHttpResult2.getData();
        }

       /* @Override
        public T call(HttpResult2<T> httpResult) {
            if (!httpResult.isSucc()) {
                throw new ApiException2(httpResult.getStatusCode());
            }
            return httpResult.getData();
        }*/
    }

}
