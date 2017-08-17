package com.jcs.printertest.base;

import android.app.Application;

/**
 * author：Jics
 * 2017/8/16 14:02
 */
public class BaseApplication extends Application {
	private static BaseApplication instance;

	@Override
	public void onCreate() {
		super.onCreate();
		instance=this;
	}

	public static BaseApplication getInstance(){
			return instance;
	}
}
