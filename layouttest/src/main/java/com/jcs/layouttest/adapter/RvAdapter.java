package com.jcs.layouttest.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;
import com.jcs.layouttest.R;

import java.util.List;
import java.util.Random;

/**
 * author：Jics
 * 2017/9/5 13:39
 */
public class RvAdapter extends RecyclerView.Adapter<RvAdapter.MHolder>{
private List<String> list;
	private Context context;
	RequestOptions options;
	public RvAdapter(Context context,List<String> list) {
		this.context=context;
		this.list = list;
		options = new RequestOptions()
				.centerCrop()
				.placeholder(R.mipmap.ic_launcher)
				.error(R.mipmap.ic_launcher)
				.priority(Priority.HIGH);
	}

	@Override
	public MHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(context).inflate(R.layout.item_banner,parent,false);
		return new MHolder(view);
	}

	@Override
	public void onBindViewHolder(MHolder holder, int position) {
		holder.textView.setText(list.get(position));
//Todo 圆角
		Glide.with(context)
				.load(MockData.URLS[new Random().nextInt(MockData.URLS.length)])
				.apply(options)
				.into(holder.iv);
	}

	@Override
	public int getItemCount() {
		return list.size();
	}

	class MHolder extends RecyclerView.ViewHolder {
		TextView textView;
		ImageView iv;
		public MHolder(View itemView) {
			super(itemView);
			textView= (TextView) itemView.findViewById(R.id.tv_item);
			iv= (ImageView) itemView.findViewById(R.id.iv);
		}
	}
}
