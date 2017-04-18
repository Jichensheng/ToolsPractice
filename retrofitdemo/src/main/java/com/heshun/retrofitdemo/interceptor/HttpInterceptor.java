package com.heshun.retrofitdemo.interceptor;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * author：Jics
 * 2016/10/20 15:05
 */
public class HttpInterceptor implements Interceptor {
	@Override
	public Response intercept(Chain chain) throws IOException {
		Request.Builder builder =chain.request().newBuilder();
		Request request=builder.addHeader("Content-type","application/json").build();
		return chain.proceed(request);
	}
}
