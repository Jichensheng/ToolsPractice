package com.jcs.wugetui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * author：Jics
 * 2017/8/8 13:19
 */
public class Activity2 extends AppCompatActivity {
	TextView textView;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_2);
		textView = (TextView) findViewById(R.id.tv_content);
		EventBus.getDefault().register(this);
	}
	@Subscribe(threadMode = ThreadMode.MAIN)
	public void setTextView(MessageEvent messageEvent){
		textView.append(messageEvent.getMessage() +"  ——来自EventBus\n");

	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		EventBus.getDefault().unregister(this);
	}
}
