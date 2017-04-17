package com.heshun.crash;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.TextView;

import com.heshun.crash.base.BaseActivity;

/**
 * author：Jics
 * 2017/4/17 10:52
 */
public class SecondActivity extends BaseActivity {
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		TextView textView= (TextView) findViewById(R.id.tv_hello);
		textView.setText(getIntent().getStringExtra("msg"));
		Log.e("-------", "onCreate: "+1/0 );
	}
}
