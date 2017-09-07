package com.jcs.layouttest.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.jcs.layouttest.fragment.ChildFragment;

import java.util.List;

/**
 * author：Jics
 * 2017/9/5 11:17
 */
public class MainAdapter extends FragmentPagerAdapter{
	private List<ChildFragment> list;

	public MainAdapter(FragmentManager fm , List<ChildFragment> list) {
		super(fm);
		this.list=list;
	}

	@Override
	public Fragment getItem(int position) {
		return list.get(position);
	}

	@Override
	public int getCount() {
		return list.size();
	}

	/**
	 * 给tablayout设置标题
	 * @param position
	 * @return
	 */
	@Override
	public CharSequence getPageTitle(int position) {
		return list.get(position).getName();
	}
}
