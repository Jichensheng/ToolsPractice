package com.jcs.music;

import android.app.Application;
import android.content.Context;

/**
 * Created by dingmouren on 2017/1/18.
 */

public class MyApplication extends Application {
    public static Context mContext;
    @Override
    public void onCreate() {
        super.onCreate();
        this.mContext = this.getApplicationContext();
    }

}
