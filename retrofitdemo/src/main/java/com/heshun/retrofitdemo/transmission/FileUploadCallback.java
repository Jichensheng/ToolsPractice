package com.heshun.retrofitdemo.transmission;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * author：Jics
 * 2016/10/27 16:50
 */
public abstract class FileUploadCallback  implements Callback<ResponseBody> {

	public abstract void progress(long progress, long total);

	@Override
	public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

	}
	@Override
	public void onFailure(Call<ResponseBody> call, Throwable t) {

	}
}
