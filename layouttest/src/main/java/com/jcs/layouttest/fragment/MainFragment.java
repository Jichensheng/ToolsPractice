package com.jcs.layouttest.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jcs.layouttest.R;
import com.jcs.layouttest.adapter.MainAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * author：Jics
 * 2017/9/5 10:32
 */
public class MainFragment extends Fragment {
	private TabLayout tlTitle;
	private ViewPager vp;
	private List<ChildFragment> list;

	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_coordinator, container, false);
		initView(view);
		return view;
	}

	private void initView(View parent) {
		list=new ArrayList<>();
			ChildFragment bean = new ChildFragment();
			bean.setName("说话");
			list.add(bean);
			ChildFragment bean1 = new ChildFragment();
			bean1.setName("电台");
			list.add(bean1);
			ChildFragment bean2 = new ChildFragment();
			bean2.setName("万象");
			list.add(bean2);
		tlTitle = (TabLayout) parent.findViewById(R.id.tb_main_fragment);

		vp = (ViewPager) parent.findViewById(R.id.rv_coor);

		vp.setAdapter(new MainAdapter(getChildFragmentManager(),list));

//		tlTitle.setSelectedTabIndicatorColor(ContextCompat.getColor(getContext(), R.color.transparent));
		tlTitle.setTabTextColors(ContextCompat.getColor(getContext(), R.color.light_gray), ContextCompat.getColor(getContext(), R.color.tab_text_selected));
//		tlTitle.setTabGravity(TabLayout.GRAVITY_FILL);
		tlTitle.setTabMode(TabLayout.GRAVITY_CENTER);
		tlTitle.setupWithViewPager(vp);

	}



}
