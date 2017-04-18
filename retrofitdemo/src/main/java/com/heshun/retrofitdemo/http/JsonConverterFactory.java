package com.heshun.retrofitdemo.http;

import org.json.JSONObject;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

public final class JsonConverterFactory extends Converter.Factory {
	private JSONObject jsonObject;
	public static JsonConverterFactory create() {
		return create(new JSONObject());
	}
	private static JsonConverterFactory create(JSONObject jsonObject) {
		return new JsonConverterFactory(jsonObject);
	}
	private JsonConverterFactory(JSONObject jsonObject) {
		if (jsonObject == null)throw new NullPointerException("json == null");
		this.jsonObject = jsonObject;
	}
	@Override
	public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
		return new JsonResponseBodyConverter<>();
	}

	@Override
	public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
		return new JsonRequestBodyConverter<>();
	}
}