package com.heshun.bvpager;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * author：Jics
 * 2017/4/17 10:01
 */
public class BounceAdapter extends PagerAdapter {
	private Context context;
	public BounceAdapter(Context context) {
		this.context=context;
	}

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
		View view=LayoutInflater.from(context).inflate(R.layout.item,container,false);
		container.addView(view);
		return view;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View) object);
	}
}
