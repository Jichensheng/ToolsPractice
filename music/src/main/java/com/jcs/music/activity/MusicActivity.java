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
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.jcs.music.CDView;
import com.jcs.music.Constant;
import com.jcs.music.MusicOnSeekBarChangeListeger;
import com.jcs.music.R;
import com.jcs.music.VirticleTitleView;
import com.jcs.music.bean.MockBean;
import com.jcs.music.bean.TalkBean;
import com.jcs.music.service.MediaPlayerService;
import com.jcs.music.utils.CircleTransform;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class MusicActivity extends AppCompatActivity implements CDView.OnPlayListener, CDView.OnStopListener {

	private static final String TAG = MusicActivity.class.getName();

	public SeekBar mSeekBar;
	public boolean isTrackingTouch = false;//进度是否走动的标志位

	public Messenger mServiceMessenger;//来自服务端的Messenger
	public float mPercent;//进度的百分比
	public int mPosition;//传递过来的的歌曲的位置
	public int mPositionPlaying;//正在播放的歌曲的位置
	public String flag;//歌曲集合的类型
	public int currentTime;//实时当前进度
	public int duration;//歌曲的总进度
	public float mPositionOffset;//viewpager滑动的百分比
	public int mState;//viewpager的滑动状态
	public List<TalkBean> mList = new ArrayList<>();
	public String songNamePlaying;
	public String singerNamePlaying;
	private MusicOnSeekBarChangeListeger musicOnSeekBarChangeListeger;//seekbar的监听
	private Messenger mClientMessenger;
	private MyHandler myHandler;

	private Button btn_play;
	private CDView cdView;
	private TextView textView;
	private TextView tv_time;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.music_activity_layout);

		initData();
		init();
		initView();
		initListener();


		VirticleTitleView vtv_title= (VirticleTitleView) findViewById(R.id.vtv_title);
		vtv_title.setTitle("平如美棠书读的");
		vtv_title.setAuthors("文/季晨生","声/小王婕");
		vtv_title.startAnimator();

	}

	private void initData() {
		mList.add(MockBean.talkBean1);
		mList.add(MockBean.talkBean2);
		mList.add(MockBean.talkBean3);
	}

	public void init() {
		//绑定音乐服务
		bindService(new Intent(this, MediaPlayerService.class), mServiceConnection, BIND_AUTO_CREATE);
		musicOnSeekBarChangeListeger = new MusicOnSeekBarChangeListeger(this);
		myHandler = new MyHandler(this);
		mClientMessenger = new Messenger(myHandler);
	}


	public void initView() {
		mSeekBar = (SeekBar) findViewById(R.id.seek_bar);
		mSeekBar.setMax(100);

		btn_play = (Button) findViewById(R.id.btn_play);
		cdView = (CDView) findViewById(R.id.cd_music);
		textView = (TextView) findViewById(R.id.music_callback);
		tv_time = (TextView) findViewById(R.id.tv_time);

	}


	public void initListener() {
		//进度条的监听
		mSeekBar.setOnSeekBarChangeListener(musicOnSeekBarChangeListeger);
		cdView.setOnStopListener(this);
		cdView.setOnPlayListener(this);
	}

	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.btn_play://播放or暂停
				if (null != mServiceMessenger) {
					Message msgToServicePlay = Message.obtain();
					msgToServicePlay.arg1 = 0x40001;//表示这个暂停是由点击按钮造成的，
					msgToServicePlay.what = Constant.PLAYING_ACTIVITY_PLAY;
					try {
						mServiceMessenger.send(msgToServicePlay);
					} catch (RemoteException e) {
						e.printStackTrace();
					}
				}
				break;
			case R.id.btn_xuhuan://顺序播放还是单曲循环
				Message msgToServceSingle = Message.obtain();
				msgToServceSingle.what = Constant.PLAYING_ACTIVITY_SINGLE;
				try {
					if (mServiceMessenger != null) {
						mServiceMessenger.send(msgToServceSingle);

					}
				} catch (RemoteException e) {
					e.printStackTrace();
				}
				break;

		}
	}

	@Override
	public void onPlayMusic() {
		if (null != mServiceMessenger) {
			Message msgToServicePlay = Message.obtain();
			msgToServicePlay.arg1 = 0x40001;//表示这个暂停是由点击按钮造成的，
			msgToServicePlay.what = Constant.PLAYING_ACTIVITY_PLAY;
			try {
				mServiceMessenger.send(msgToServicePlay);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void onStopMusic() {
		if (null != mServiceMessenger) {
			Message msgToServicePlay = Message.obtain();
			msgToServicePlay.arg1 = 0x40001;//表示这个暂停是由点击按钮造成的，
			msgToServicePlay.what = Constant.PLAYING_ACTIVITY_PLAY;
			try {
				mServiceMessenger.send(msgToServicePlay);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}

	ServiceConnection mServiceConnection = new ServiceConnection() {
		@Override
		public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
			Log.e(TAG, "onServiceConnected");
			//获取服务端信使 用于向服务端传递数据
			mServiceMessenger = new Messenger(iBinder);
			//用于在服务端初始化来自客户端的Messenger对象,连接成功的时候，就进行初始化
			if (null != mServiceMessenger) {
				Message msgToService = Message.obtain();
				//实现数据双向传递关键步骤
				msgToService.replyTo = mClientMessenger;//将自己的“信使”通过replyto放到msg里，让服务端获取后传递数据
				msgToService.what = Constant.MusicAcitcity_ACTIVITY;//msg的标志
				if (0 != currentTime) {//当前进度不是0，就更新MediaPlayerService的当前进度
					msgToService.arg1 = currentTime;
				}
				try {
					mServiceMessenger.send(msgToService);
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			}
			//连接成功的时候，
			mPosition = getIntent().getIntExtra("position", 0);
			flag = getIntent().getStringExtra("flag");
			Log.e(TAG, "传递过来的positon:" + mPosition + " flag:" + flag);
			if (null != mServiceMessenger) {
				Message msgToService = Message.obtain();
				msgToService.arg1 = mPosition;
//				mList.clear();
				//Todo 歌曲集合
//				List<MusicBean> musicBeanList=new ArrayList<>();
//				mList.addAll(musicBeanList);
				if (null != mList) {
					//传递歌曲集合数据
					Bundle songsData = new Bundle();
					songsData.putSerializable(Constant.PLAYING_ACTIVITY_DATA_KEY, (Serializable) mList);
					msgToService.setData(songsData);
					msgToService.what = Constant.PLAYING_ACTIVITY_INIT;
					try {
						mServiceMessenger.send(msgToService);
					} catch (RemoteException e) {
						e.printStackTrace();
					}
				}

			}
		}

		@Override
		public void onServiceDisconnected(ComponentName componentName) {
			Log.e(TAG, "onServiceDisconnected");
		}
	};


	@Override
	protected void onDestroy() {
		Log.e(TAG, "onDestroy");
		unbindService(mServiceConnection);
		if (null != myHandler) {
			myHandler.removeCallbacksAndMessages(null);//移除消息队列中所有的消息和所有的Runnable
			myHandler = null;
		}
		System.gc();
		super.onDestroy();
	}


	class MyHandler extends Handler {
		private WeakReference<MusicActivity> weakActivity;

		public MyHandler(MusicActivity activity) {
			weakActivity = new WeakReference<>(activity);
		}

		@Override
		public void handleMessage(Message msgFromService) {
			MusicActivity activity = weakActivity.get();
			if (null == activity) return;
			switch (msgFromService.what) {
				case Constant.MEDIA_PLAYER_SERVICE_PROGRESS://更新进度条
					activity.currentTime = msgFromService.arg1;
					activity.duration = msgFromService.arg2;
					if (0 == activity.duration) break;
					if (!isTrackingTouch) {
						activity.mSeekBar.setProgress(activity.currentTime * 100 / activity.duration);
					}
					activity.tv_time.setText(formatTime(activity.currentTime, activity.duration));
					break;
				//从服务端获取到歌曲列表
				case Constant.MEDIA_PLAYER_SERVICE_SONG_PLAYING:
					Bundle bundle = msgFromService.getData();
					activity.mList.clear();
					activity.mList.addAll((List<TalkBean>) bundle.getSerializable(Constant.MEDIA_PLAYER_SERVICE_MODEL_PLAYING));
					if (null != activity.mList && 0 < activity.mList.size()) {
						textView.setText(mList.get(msgFromService.arg1).getTitle());
						Picasso.with(activity.getApplicationContext())
								.load(mList.get(msgFromService.arg1).getImage())
								.transform(new CircleTransform()).into(cdView);
					}
					break;
				case Constant.MEDIA_PLAYER_SERVICE_IS_PLAYING:
					if (1 == msgFromService.arg1) {//正在播放
						activity.btn_play.setText("播放");
						cdView.playMusic();
					} else {
						activity.btn_play.setText("暂停");
						cdView.stopMusic();
					}
					break;
				case Constant.PLAYING_ACTIVITY_PLAY_MODE://显示播放器的播放模式

					break;
				case Constant.MEDIA_PLAYER_SERVICE_UPDATE_SONG://播放完成自动播放下一首时，更新正在播放UI
					int positionPlaying = msgFromService.arg1;
					activity.textView.setText("" + positionPlaying);
					Log.e(TAG, "更新正在播放的UI");
					break;
				case Constant.MEDIA_PLAYER_SERVICE_DOWNLOAD_PROGRESS:
					int downloadProgress=msgFromService.arg1;
					activity.mSeekBar.setSecondaryProgress(downloadProgress);
					break;

			}
			super.handleMessage(msgFromService);
		}
	}

	public static String formatTime(long start, long end) {
		int minute = (int) (start / 1000 / 60);
		int second = (int) (start / 1000 % 60);

		int minuteE = (int) (end / 1000 / 60);
		int secondE = (int) (end / 1000 % 60);

		return String.format("%02d:%02d/%02d:%02d", minute, second, minuteE, secondE);
	}
}