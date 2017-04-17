package com.heshun.crash.base;

import android.app.Application;

import com.heshun.crash.crash.CrashHandler;

public class BaseApplication extends Application
{
  private static BaseApplication instance;

  public static BaseApplication getInstance()
  {
    return instance;
  }

  private void initImageLoader()
  {
  }

  public void onCreate()
  {
    super.onCreate();
    instance = this;
    initImageLoader();
    CrashHandler.getInstance().setCustomCrashHanler(getApplicationContext());
  }
}
