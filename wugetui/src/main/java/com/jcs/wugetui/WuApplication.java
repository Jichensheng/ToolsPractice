package com.jcs.wugetui;

import android.app.Application;
import android.os.Handler;
import android.os.Message;

import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;

/**
 * Created by Jcs on 2017/8/7.
 */

public class WuApplication extends Application {
	/**
	 * 应用未启动, 个推 service已经被唤醒,保存在该时间段内离线消息(此时 GetuiSdkDemoActivity.tLogView == null)
	 */
	public static StringBuilder payloadData = new StringBuilder();

	private static DemoHandler handler;
	public static MainActivity mainActivity;
	@Override
	public void onCreate() {
		super.onCreate();
		Beta.upgradeDialogLayoutId = R.layout.upgrade_layout;
		Bugly.init(getApplicationContext(), "0557130f5a", false);
		if (handler == null) {
			handler = new DemoHandler();
		}
	}


	public static void sendMessage(Message msg) {
		handler.sendMessage(msg);
	}

	public static class DemoHandler extends Handler {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case 0:
					if (mainActivity != null) {
						payloadData.append((String) msg.obj);
						payloadData.append("\n");
						if (MainActivity.tLogView != null) {
							MainActivity.tLogView.append(msg.obj + "\n");
						}
					}
					break;

				case 1:
					if (mainActivity != null) {
						if (MainActivity.tLogView != null) {
							MainActivity.tView.setText((String) msg.obj);
						}
					}
					break;
			}
		}
	}


}
