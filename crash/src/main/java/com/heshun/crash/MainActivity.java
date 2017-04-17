package com.heshun.crash;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.heshun.crash.base.BaseActivity;

public class MainActivity extends BaseActivity {
private TextView tv_hello;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		tv_hello= (TextView) findViewById(R.id.tv_hello);
		tv_hello.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(MainActivity.this,SecondActivity.class);
				intent.putExtra("msg","第二页");
				startActivity(intent);
			}
		});
	}
}
