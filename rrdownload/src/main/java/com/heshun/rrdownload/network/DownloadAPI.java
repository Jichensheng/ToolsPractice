package com.heshun.rrdownload.network;

import android.support.annotation.NonNull;
import android.util.Log;

import com.heshun.rrdownload.network.download.DownloadProgressInterceptor;
import com.heshun.rrdownload.network.download.DownloadProgressListener;
import com.heshun.rrdownload.network.exception.CustomizeException;
import com.heshun.rrdownload.utils.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Jcs on 16/7/5.
 */
public class DownloadAPI {
    private static final String TAG = "DownloadAPI";
    private static final int DEFAULT_TIMEOUT = 15;
    public Retrofit retrofit;


    public DownloadAPI(String url, DownloadProgressListener listener) {


        DownloadProgressInterceptor interceptor = new DownloadProgressInterceptor(listener);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .retryOnConnectionFailure(true)
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .build();


        retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .client(client)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    public void downloadAPK(@NonNull String url, final File file, Subscriber subscriber) {
        Log.d(TAG, "downloadAPK: " + url);

        retrofit.create(DownloadService.class)
                .download(url)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .map(new Func1<ResponseBody, InputStream>() {
                    @Override
                    public InputStream call(ResponseBody responseBody) {
                        return responseBody.byteStream();
                    }
                })
                //observeOn作用于该操作符之后操作符直到出现新的observeOn操作符
                .observeOn(Schedulers.computation())//CPU 密集型计算，不会被 I/O 等操作限制性能的操作
                .doOnNext(new Action1<InputStream>() {//输出元素前的操作，可以去保存/缓存网络结果
                    @Override
                    public void call(InputStream inputStream) {
                        try {
                            FileUtils.writeFile(inputStream, file);
                        } catch (IOException e) {
                            e.printStackTrace();
                            throw new CustomizeException(e.getMessage(), e);
                        }
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


}
