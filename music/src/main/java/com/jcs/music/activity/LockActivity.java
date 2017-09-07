package com.jcs.music.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jcs.music.Constant;
import com.jcs.music.MyApplication;
import com.jcs.music.R;
import com.jcs.music.bean.TalkBean;
import com.jcs.music.service.MediaPlayerService;

import java.lang.ref.WeakReference;

import jp.wasabeef.glide.transformations.BlurTransformation;

/**
 * Created by dingmouren on 2017/2/16.
 */

public class LockActivity extends AppCompatActivity implements View.OnClickListener{

	private static final String TAG = LockActivity.class.getName();

	private SildingFinishLayout mRootLayout;
	private ImageView mImgBg;
	private ImageView mImgAlbum;
	private TextView mTvSongName;
	private ImageButton mImgPre;
	private ImageButton mImgPlay;
	private ImageButton mImgNext;
	private Messenger mServiceMessenger;
	private Messenger mMessengerClient;
	private MyHandler myHandler;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lock);
		init();
		initView();
	}


	public void init() {
		//隐藏系统锁屏
		this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
		bindService(new Intent(getApplicationContext(), MediaPlayerService.class), mServiceConnection, BIND_AUTO_CREATE);
		myHandler = new MyHandler(this);
		mMessengerClient = new Messenger(myHandler);

	}

	public void initView() {
		mTvSongName= (TextView) findViewById(R.id.tv_song_name);

		mRootLayout = (SildingFinishLayout) findViewById(R.id.container);
		mImgBg = (ImageView) findViewById(R.id.img_bg);
		mImgAlbum = (ImageView) findViewById(R.id.img_album);
		mImgPre = (ImageButton) findViewById(R.id.img_pre);
		mImgPlay = (ImageButton) findViewById(R.id.img_paly);
		mImgNext = (ImageButton) findViewById(R.id.img_next);

		mImgPre.setOnClickListener(this);
		mImgPlay.setOnClickListener(this);
		mImgNext.setOnClickListener(this);


		mRootLayout.setTouchView(getWindow().getDecorView());
		mRootLayout.setOnSildingFinishListener(new SildingFinishLayout.OnSildingFinishListener() {
			@Override
			public void onSildingFinish() {
				finish();
			}
		});
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.img_pre:
				if (null != mServiceMessenger) {
					Message msgToServicePlay = Message.obtain();
					msgToServicePlay.what = Constant.LOCK_ACTIVITY_PRE;
					try {
						mServiceMessenger.send(msgToServicePlay);
					} catch (RemoteException e) {
						e.printStackTrace();
					}
				}
				break;
			case R.id.img_paly:
				if (null != mServiceMessenger) {
					Message msgToServicePlay = Message.obtain();
					msgToServicePlay.arg1 = 0x40001;//表示这个暂停是由点击按钮造成的，
					msgToServicePlay.what = Constant.LOCK_ACTIVITY_PLAY;
					try {
						mServiceMessenger.send(msgToServicePlay);
					} catch (RemoteException e) {
						e.printStackTrace();
					}
				}
				break;
			case R.id.img_next:
				if (null != mServiceMessenger) {
					Message msgToServicePlay = Message.obtain();
					msgToServicePlay.what = Constant.LOCK_ACTIVITY_NEXT;
					try {
						mServiceMessenger.send(msgToServicePlay);
					} catch (RemoteException e) {
						e.printStackTrace();
					}
				}
				break;

		}
	}

	//屏蔽按键
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		int key = event.getKeyCode();
		switch (key) {
			case KeyEvent.KEYCODE_BACK: {
				return true;
			}
			case KeyEvent.KEYCODE_MENU: {
				return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onDestroy() {
		mRootLayout.removeAllViews();
		unbindService(mServiceConnection);
		if (null != myHandler) {
			myHandler.removeCallbacksAndMessages(null);
			myHandler = null;
		}
		if (null != mServiceMessenger) {
			mServiceMessenger = null;
		}
		super.onDestroy();
	}

	ServiceConnection mServiceConnection = new ServiceConnection() {
		@Override
		public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
			mServiceMessenger = new Messenger(iBinder);
			//连接到服务
			if (null != mServiceMessenger) {
				Message msgToService = Message.obtain();
				msgToService.replyTo = mMessengerClient;
				msgToService.what = Constant.LOCK_ACTIVITY;
				try {
					mServiceMessenger.send(msgToService);
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			}
		}

		@Override
		public void onServiceDisconnected(ComponentName componentName) {

		}
	};

	static class MyHandler extends Handler {
		private WeakReference<LockActivity> weakActivity;
		private TalkBean mBean;

		public MyHandler(LockActivity activity) {
			weakActivity = new WeakReference<LockActivity>(activity);
		}

		@Override
		public void handleMessage(Message msgFromService) {
			LockActivity activity = weakActivity.get();
			if (null == activity) return;
			switch (msgFromService.what) {
				case Constant.MEDIA_PLAYER_SERVICE_SONG_PLAYING://通过Bundle传递对象,显示正在播放的歌曲
					Log.e(TAG, "收到消息了");
					Bundle bundle = msgFromService.getData();
					mBean = (TalkBean) bundle.getSerializable(Constant.MEDIA_PLAYER_SERVICE_MODEL_PLAYING);
					activity.mTvSongName.setText(mBean.getTitle());
					Glide.with(MyApplication.mContext).load(mBean.getImage()).asBitmap()
							.centerCrop().diskCacheStrategy(DiskCacheStrategy.SOURCE)
							.placeholder(R.mipmap.ic_launcher).into(activity.mImgAlbum);
					Glide.with(MyApplication.mContext)//底部的模糊效果
							.load(mBean.getImage())
							.bitmapTransform(new BlurTransformation(MyApplication.mContext, 99))
							.diskCacheStrategy(DiskCacheStrategy.SOURCE)
							.crossFade()
							.placeholder(R.mipmap.ic_launcher)
							.into(activity.mImgBg);
					break;
				case Constant.MEDIA_PLAYER_SERVICE_IS_PLAYING:
					Log.e(TAG, "收到更新播放状态的消息");
					if (1 == msgFromService.arg1) {//正在播放
						activity.mImgPlay.setImageResource(R.mipmap.play);
					} else {
						activity.mImgPlay.setImageResource(R.mipmap.pause);
					}
			}
			super.handleMessage(msgFromService);
		}
	}
}
