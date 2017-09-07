package com.jcs.layouttest;

import android.app.Application;

/**
 * author：Jics
 * 2017/9/7 19:20
 */
public class BaseApplication extends Application {
	private static BaseApplication instance;

	@Override
	public void onCreate() {
		super.onCreate();
		instance=this;
	}

	public static BaseApplication getInstance() {
		return instance;
	}
}
