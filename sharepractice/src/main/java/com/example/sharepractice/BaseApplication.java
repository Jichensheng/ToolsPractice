package com.example.sharepractice;

import android.app.Application;

import cn.sharesdk.framework.ShareSDK;

/**
 * Created by Jcs on 2017/4/16.
 */

public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ShareSDK.initSDK(getApplicationContext(),"1186d21d7898d");
    }
}
