package com.heshun.rrdownload.network;

import android.support.annotation.NonNull;
import android.util.Log;

import com.heshun.rrdownload.network.download.DownloadProgressInterceptor;
import com.heshun.rrdownload.network.download.DownloadProgressListener;
import com.heshun.rrdownload.network.exception.CustomizeException;
import com.heshun.rrdownload.utils.FileUtils;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;

/**
 * Created by Jcs on 16/7/5.
 */
public class DownloadAPI {
    private static final String TAG = "DownloadAPI";
    private static final int DEFAULT_TIMEOUT = 15;
    public Retrofit retrofit;


    public DownloadAPI(String url, DownloadProgressListener listener) {

        //2、监听器实例传给拦截器
        DownloadProgressInterceptor interceptor = new DownloadProgressInterceptor(listener);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .retryOnConnectionFailure(true)
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .build();


        retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public void downloadAPK(@NonNull String url, final File file, Observer<InputStream> observer) {
        Log.d(TAG, "downloadAPK: " + url);
        retrofit.create(DownloadService.class)
                .download(url)
                .subscribeOn(Schedulers.newThread())
                .map(new Function<ResponseBody, InputStream>(){

                    @Override
                    public InputStream apply(ResponseBody responseBody) throws Exception {
                        return responseBody.byteStream();
                    }
                })
                //observeOn作用于该操作符之后操作符直到出现新的observeOn操作符
                .observeOn(Schedulers.computation())//CPU 密集型计算，不会被 I/O 等操作限制性能的操作
                .doOnNext(new Consumer<InputStream>(){

                    @Override
                    public void accept(InputStream inputStream) throws Exception {
                        try {
                            FileUtils.writeFile(inputStream, file);
                        } catch (IOException e) {
                            e.printStackTrace();
                            throw new CustomizeException(e.getMessage(), e);
                        }
                    }
                })
                .subscribe(observer);
    }


}
