package com.heshun.retrofitdemo.http;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

final class JsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
	private JSONObject jsonObject;
	@Override
	public T convert(ResponseBody value) throws IOException {
		BufferedReader br=new BufferedReader(value.charStream());
		String line="";
		StringBuffer buffer = new StringBuffer();
		while((line=br.readLine())!=null){
			buffer.append(line);
		}
		Log.d("jcsのConverter","接收:"+buffer.toString());
		try {
			jsonObject=new JSONObject(buffer.toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return (T) jsonObject;
	}
}