package com.jcs.mobshare;

import android.app.Application;

import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.common.QueuedWork;

public class App extends Application {
	@Override
	public void onCreate() {
		super.onCreate();
		//开启debug模式，方便定位错误，具体错误检查方式可以查看http://dev.umeng.com/social/android/quick-integration的报错必看，正式发布，请关闭该模式
		Config.DEBUG = true;
		QueuedWork.isUseThreadPool = false;
		UMShareAPI.get(this);
	}

	{
		//这里是appid和secret，签名是用软件计算出来的
		PlatformConfig.setWeixin("wx903e9deabba021b5", "089ed35f3752b9be2d2baaa2aa43e03d");
		PlatformConfig.setQQZone("1106337834", "syipgczyYwyH5QiF");
		PlatformConfig.setSinaWeibo("1122866594", "f1e8036b39d19b62393e39559139c701", "http://sns.whalecloud.com");
	}
}
