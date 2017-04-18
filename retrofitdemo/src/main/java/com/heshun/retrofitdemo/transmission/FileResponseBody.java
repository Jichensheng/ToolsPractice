package com.heshun.retrofitdemo.transmission;


import com.heshun.retrofitdemo.http.RxBus;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

/**
 * 进度数据获取源头，通过RxBus发送给订阅者
 * response中的source
 */
public class FileResponseBody extends ResponseBody {

    Response originalResponse;
    //在okhttp的拦截器里将response传进来的
    public FileResponseBody(Response originalResponse) {
        this.originalResponse = originalResponse;
    }

    @Override
    public MediaType contentType() {
        return originalResponse.body().contentType();
    }

    @Override
    public long contentLength() {
        return originalResponse.body().contentLength();
    }

    /**
     * 数据接收核心
     * @return
	 */
    @Override
    public BufferedSource source() {
        Source source=new ForwardingSource(originalResponse.body().source()) {
            long bytesReaded = 0;

            @Override
            public long read(Buffer sink, long byteCount) throws IOException {
                long bytesRead = super.read(sink, byteCount);
                bytesReaded += bytesRead == -1 ? 0 : bytesRead;
                //FileResponseBody重写了source()方法
                // 在Okio.buffer中处理下载进度相关的逻辑，也是在这个时候发送的FileLoadEvent的
                RxBus.getDefault().post(new FileLoadEvent(contentLength(), bytesReaded));
                return bytesRead;
            }
        };
        return Okio.buffer(source);
    }

}