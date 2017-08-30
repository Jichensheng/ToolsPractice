package com.jcs.xrefresh;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.allen.library.SuperTextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jcs.xrefresh.banner.BannerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements OnSuperTextClickListener {
	private XRecyclerView mRecyclerView;
	private int refreshTime = 0;
	private List<String> list;
	private int times = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		list = new ArrayList<>();
		addItem(list);

		List<BannerItem> listB = new ArrayList<>();
		for (int i = 0; i < urls.length; i++) {
			BannerItem item = new BannerItem();
			item.image = urls[i];
			item.title = titles[i];

			listB.add(item);
		}

		final BannerView banner1 = (BannerView) findViewById(R.id.banner1);
		banner1.setViewFactory(new BannerViewFactory());
		banner1.setDataList(listB);
		banner1.start();
		final BannerView banner2 = (BannerView) findViewById(R.id.banner2);
		banner2.setViewFactory(new BannerViewFactory());
		banner2.setDataList(listB);
		banner2.start();

		mRecyclerView = (XRecyclerView) findViewById(R.id.xrv);
		LinearLayoutManager layoutManager = new LinearLayoutManager(this);
		layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
		mRecyclerView.setLayoutManager(layoutManager);

		final RecyclerView.Adapter adapter = getAdatapter(this,this, list);

		//上拉下拉风格
		mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
		mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallZigZagDeflect);
		//设置箭头
		mRecyclerView.setArrowImageView(R.mipmap.iconfont_downgrey);
		mRecyclerView.setAdapter(adapter);
		//回调监听部分
		mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
			@Override
			public void onRefresh() {
				refreshTime++;
				new Handler().postDelayed(new Runnable() {
					public void run() {
						list.clear();
						for (int i = 0; i < 15; i++) {
							list.add("item" + i + "after " + refreshTime + " times of refresh");
						}
						adapter.notifyDataSetChanged();
						//刷新结束
						mRecyclerView.refreshComplete();
					}

				}, 1000);
			}

			@Override
			public void onLoadMore() {
				if (times < 2) {
					new Handler().postDelayed(new Runnable() {
						public void run() {
							for (int i = 0; i < 15; i++) {
								list.add("item" + (1 + list.size()));
							}
							//加载更多
							mRecyclerView.loadMoreComplete();
							adapter.notifyDataSetChanged();
						}
					}, 1000);
				} else {
					new Handler().postDelayed(new Runnable() {
						public void run() {
							for (int i = 0; i < 9; i++) {
								list.add("item" + (1 + list.size()));
							}
							//没有更多了
							mRecyclerView.setNoMore(true);
							adapter.notifyDataSetChanged();
						}
					}, 1000);
				}
				times++;
			}
		});

	}

	private static void addItem(List<String> list) {
		for (int i = 0; i < 10; i++) {
			list.add("new " + new Random().nextInt(1500));
		}
	}

	@Override
	public void onSClick(SuperTextView textView) {
		textView.setLeftTvClickListener(new SuperTextView.OnLeftTvClickListener() {
			@Override
			public void onClickListener() {
				Toast.makeText(MainActivity.this, "YYYYY", Toast.LENGTH_SHORT).show();
			}
		});
	}

	private static RecyclerView.Adapter getAdatapter(final OnSuperTextClickListener listener, final Context context, final List<String> list) {

		return new RecyclerView.Adapter() {

			@Override
			public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
				switch (viewType) {
					case 0:
						return new XHolder(LayoutInflater.from(context).inflate(R.layout.holder_text, parent, false));
					default:
						return new SuperHolder(LayoutInflater.from(context).inflate(R.layout.holder_super_text, parent, false));
				}
			}

			@Override
			public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
				if (holder instanceof XHolder) {
					((XHolder) holder).textView.setText(list.get(position));
				} else if (holder instanceof SuperHolder) {
					((SuperHolder) holder).superTextView.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							listener.onSClick(((SuperHolder) holder).superTextView);
						}
					});
					((SuperHolder) holder).superTextView.setLeftTopString(list.get(position));
				}
			}

			@Override
			public int getItemCount() {
				return list.size();
			}

			@Override
			public int getItemViewType(int position) {
				return position % 2;
			}

			class XHolder extends RecyclerView.ViewHolder {
				TextView textView;

				public XHolder(View itemView) {
					super(itemView);
					textView = (TextView) itemView.findViewById(R.id.tv_holder);
				}
			}

			class SuperHolder extends RecyclerView.ViewHolder {
				SuperTextView superTextView;

				public SuperHolder(View itemView) {
					super(itemView);
					superTextView = (SuperTextView) itemView.findViewById(R.id.stv_click);
				}
			}


		};
	}
	public static String[] titles = new String[]{
			"每周7件Tee不重样",
			"俏皮又知性 适合上班族的漂亮衬衫",
			"名侦探柯南",
			"境界之轮回",
			"我的英雄学院",
			"全职猎人",
			"大咸鱼"
	};
	public static String[] urls = new String[]{//750x500
//			"https://s2.mogucdn.com/mlcdn/c45406/170422_678did070ec6le09de3g15c1l7l36_750x500.jpg",
//			"https://s2.mogucdn.com/mlcdn/c45406/170420_1hcbb7h5b58ihilkdec43bd6c2ll6_750x500.jpg",
//			"http://s18.mogucdn.com/p2/170122/upload_66g1g3h491bj9kfb6ggd3i1j4c7be_750x500.jpg",
//			"http://s18.mogucdn.com/p2/170204/upload_657jk682b5071bi611d9ka6c3j232_750x500.jpg",
//			"http://s16.mogucdn.com/p2/170204/upload_56631h6616g4e2e45hc6hf6b7g08f_750x500.jpg",
			"http://s16.mogucdn.com/p2/170206/upload_1759d25k9a3djeb125a5bcg0c43eg_750x500.jpg",
//			"https://hbimg.b0.upaiyun.com/1754bbeb32c1d76817a4137ed06a8ecc8a8807f7c810-B9iEre_fw658",
			"https://hbimg.b0.upaiyun.com/1754bbeb32c1d76817a4137ed06a8ecc8a8807f7c810-B9iEre_fw658",
			"https://hbimg.b0.upaiyun.com/1754bbeb32c1d76817a4137ed06a8ecc8a8807f7c810-B9iEre_fw658",
			"https://hbimg.b0.upaiyun.com/1754bbeb32c1d76817a4137ed06a8ecc8a8807f7c810-B9iEre_fw658",
			"https://hbimg.b0.upaiyun.com/1754bbeb32c1d76817a4137ed06a8ecc8a8807f7c810-B9iEre_fw658",
			"https://hbimg.b0.upaiyun.com/1754bbeb32c1d76817a4137ed06a8ecc8a8807f7c810-B9iEre_fw658",
			"https://hbimg.b0.upaiyun.com/1754bbeb32c1d76817a4137ed06a8ecc8a8807f7c810-B9iEre_fw658"
	};
	public static class BannerItem {
		public String image;
		public String title;

		@Override
		public String toString() {
			return title;
		}
	}

	public static class BannerViewFactory implements BannerView.ViewFactory<BannerItem> {
		@Override
		public View create(BannerItem item, int position, ViewGroup container) {
			ImageView iv = new ImageView(container.getContext());
			iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
			RequestOptions options = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.DATA);
			Glide.with(container.getContext().getApplicationContext()).load(item.image).apply(options).into(iv);
			return iv;
		}
	}

}
