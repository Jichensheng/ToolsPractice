package com.jcs.layouttest.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jcs.layouttest.R;
import com.jcs.layouttest.adapter.RvAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * author：Jics
 * 2017/9/5 11:24 argument
 */
public class ChildFragment extends Fragment {
	private String name;
	private RecyclerView recyclerView;
	private List<String> list;


	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		list = initlist();
		View view = inflater.inflate(R.layout.fragment_child, container, false);
		recyclerView = (RecyclerView) view.findViewById(R.id.rv_child);
		recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
		recyclerView.setAdapter(new RvAdapter(getContext(), list));
//		recyclerView.addOnScrollListener(new ImageAutoLoadScrollListener(getContext()));
		return view;
	}

	/**
	 * 模拟数据
	 * @return
	 */
	private List<String> initlist() {
		List<String> list = new ArrayList<>();
		for (int i = 0; i < 200; i++) {
			list.add("item" + i);
		}
		return list;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
