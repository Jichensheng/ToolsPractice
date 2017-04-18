package com.heshun.retrofitdemo;

import android.app.Application;

/**
 * author：Jics
 * 2016/10/25 17:30
 */
public class BaseApplication extends Application {
	public static BaseApplication instance;

	@Override
	public void onCreate() {
		super.onCreate();
		instance=this;
	}
	public static BaseApplication getInstance(){
		return instance;
	}
}
