package com.heshun.bvpager;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MainActivity extends AppCompatActivity {
	private ViewPager bbv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		bbv= (ViewPager) findViewById(R.id.bbv);
		bbv.setAdapter(new PagerAdapter() {
			@Override
			public int getCount() {
				return 10;
			}

			@Override
			public boolean isViewFromObject(View view, Object object) {
				return view==object;
			}

			@Override
			public Object instantiateItem(ViewGroup container, int position) {
				View view= LayoutInflater.from(MainActivity.this).inflate(R.layout.item,container,false);
				container.addView(view);
				return view;
			}

			@Override
			public void destroyItem(ViewGroup container, int position, Object object) {
				container.removeView((View) object);
			}
		});
	}
}
