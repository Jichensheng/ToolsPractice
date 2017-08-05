package com.jcs.picpicker;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ContentFrameLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.engine.impl.PicassoEngine;
import com.zhihu.matisse.filter.Filter;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class SampleActivity extends AppCompatActivity implements View.OnClickListener {

	private static final int REQUEST_CODE_CHOOSE = 23;

	private UriAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main2);
		findViewById(R.id.zhihu).setOnClickListener(this);
		findViewById(R.id.dracula).setOnClickListener(this);

		RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
		recyclerView.setLayoutManager(new LinearLayoutManager(this));
		recyclerView.setAdapter(mAdapter = new UriAdapter());
	}

	@Override
	public void onClick(final View v) {
		RxPermissions rxPermissions = new RxPermissions(this);
		rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
				.subscribe(new Observer<Boolean>() {
					@Override
					public void onSubscribe(Disposable d) {

					}

					@Override
					public void onNext(Boolean aBoolean) {
						if (aBoolean) {
							switch (v.getId()) {
								case R.id.zhihu:
									Matisse.from(SampleActivity.this)
											.choose(MimeType.ofAll(), false)
											.countable(true)
											.capture(true)
											.captureStrategy( new CaptureStrategy(true, "com.zhihu.matisse.sample.fileprovider"))
											.maxSelectable(9)
											.addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
											.gridExpectedSize( getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
											.restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
											.thumbnailScale(0.85f)
											.imageEngine(new GlideEngine())
											.forResult(REQUEST_CODE_CHOOSE);
									break;
								case R.id.dracula:
									Matisse.from(SampleActivity.this)
											.choose(MimeType.ofImage())
											.theme(R.style.Matisse_Dracula)
											.countable(false)
											.maxSelectable(9)
											.imageEngine(new PicassoEngine())
											.forResult(REQUEST_CODE_CHOOSE);
									break;
							}
							mAdapter.setData(null, null);
						} else {
							Toast.makeText(SampleActivity.this, R.string.permission_request_denied, Toast.LENGTH_LONG)
									.show();
						}
					}

					@Override
					public void onError(Throwable e) {

					}

					@Override
					public void onComplete() {

					}
				});
	}

	/**
	 * 选择之后的回调
	 * @param requestCode
	 * @param resultCode
	 * @param data
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
			mAdapter.setData(Matisse.obtainResult(data), Matisse.obtainPathResult(data));
		}
	}

	private static class UriAdapter extends RecyclerView.Adapter<UriAdapter.UriViewHolder> {
        private Context context;
		private List<Uri> mUris;
		private List<String> mPaths;

		void setData(List<Uri> uris, List<String> paths) {
			mUris = uris;
			mPaths = paths;
			notifyDataSetChanged();
		}

		@Override
		public UriViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            context=parent.getContext();
			return new UriViewHolder(
					LayoutInflater.from(parent.getContext()).inflate(R.layout.uri_item, parent, false));
		}

		@Override
		public void onBindViewHolder(UriViewHolder holder, int position) {
			holder.mUri.setText(mUris.get(position).toString());
			holder.mPath.setText(mPaths.get(position));
//            holder.imageView.setImageURI(mUris.get(position));
            Picasso.with(context).load(mUris.get(position)).resize(1000,750).centerInside().into(holder.imageView);
			holder.mUri.setAlpha(position % 2 == 0 ? 1.0f : 0.54f);
			holder.mPath.setAlpha(position % 2 == 0 ? 1.0f : 0.54f);
		}

		@Override
		public int getItemCount() {
			return mUris == null ? 0 : mUris.size();
		}

		static class UriViewHolder extends RecyclerView.ViewHolder {

			private TextView mUri;
			private TextView mPath;
            private ImageView imageView;
			UriViewHolder(View contentView) {
				super(contentView);
				mUri = (TextView) contentView.findViewById(R.id.uri);
				mPath = (TextView) contentView.findViewById(R.id.path);
                imageView = (ImageView) contentView.findViewById(R.id.imageView);
			}
		}
	}

}