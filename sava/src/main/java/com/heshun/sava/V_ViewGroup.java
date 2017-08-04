package com.heshun.sava;

import android.content.Context;
import android.view.MotionEvent;
import android.view.ViewGroup;

/**
 * author：Jics
 * 2017/5/16 16:06
 */
public class V_ViewGroup extends ViewGroup {
	public V_ViewGroup(Context context) {
		super(context);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {

	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		return super.dispatchTouchEvent(ev);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		return super.onInterceptTouchEvent(ev);
	}
}
