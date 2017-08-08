package com.jcs.wugetui;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.igexin.sdk.PushManager;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import io.reactivex.functions.Consumer;

/**
 * android:launchMode="singleTop"
 */
public class MainActivity extends AppCompatActivity {
	// DemoPushService.class 自定义服务名称, 核心服务
	private Class userPushService = DemoPushService.class;

	public static TextView tLogView;
	public static TextView tView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//重要！！！
		WuApplication.mainActivity = this;

		initView();
		RxPermissions permissions = new RxPermissions(this);
		permissions.request(android.Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE)
				.subscribe(new Consumer<Boolean>() {
					@Override
					public void accept(Boolean aBoolean) throws Exception {
						if (aBoolean) {
							PushManager.getInstance().initialize(MainActivity.this.getApplicationContext(), userPushService);
						} else {

						}
					}
				});
		// com.getui.demo.DemoIntentService 为第三方自定义的推送服务事件接收类
		PushManager.getInstance().registerPushIntentService(this.getApplicationContext(), DemoIntentService.class);
		// 应用未启动, 个推 service已经被唤醒,显示该时间段内离线消息
		if (WuApplication.payloadData != null) {
			tLogView.append(WuApplication.payloadData + "  ——来自StringBuilder池\n");
		}
		//注册
		EventBus.getDefault().register(this);

	}


	@Subscribe(threadMode = ThreadMode.MAIN)
	public void onMoonEvent(MessageEvent messageEvent){
		tLogView.append(messageEvent.getMessage()+ "  ——来自EventBus\n");
	}
	private void initView() {
		tView = (TextView) findViewById(R.id.tvclientid);
		tLogView = (TextView) findViewById(R.id.textContent);
		tLogView.setInputType(InputType.TYPE_NULL);
		tLogView.setSingleLine(false);
		tLogView.setHorizontallyScrolling(false);
	}

	@Override
	public void onDestroy() {
		Log.d("GetuiSdkDemo", "onDestroy()");
		WuApplication.payloadData.delete(0, WuApplication.payloadData.length());
		EventBus.getDefault().unregister(this);
		super.onDestroy();
	}


	public void next(View view) {
		startActivity(new Intent(this, Activity2.class));
	}
}
