package com.jcs.music.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * author：Jics
 * 2017/8/30 14:22
 */
public class TService extends Service implements MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener {
	private static final String TAG = "播放器";
	private int currentTime = 0;//记录当前播放时间
	//MediaPlayer
	private MediaPlayer mediaPlayer;
	//音频管理对象
	private AudioManager mAudioManager;
	private Observable mObservable;
	private Consumer onNext;

	@Override
	public void onCreate() {
		super.onCreate();
		//初始化MediaPlayer,设置监听事件
		mediaPlayer = new MediaPlayer();
		mediaPlayer.setOnPreparedListener(this);
		mediaPlayer.setOnCompletionListener(this);
		mediaPlayer.setOnErrorListener(this);
		mediaPlayer.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
			@Override
			public void onBufferingUpdate(MediaPlayer mp, int percent) {
				Log.e(TAG, "onBufferingUpdate: " + percent);
				Message msg = Message.obtain();
				msg.what = 4;
				msg.arg1 = percent;
				sendToClient(msg);
			}
		});
		//初始化音频管理对象
		mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

		mObservable = Observable.interval(1, 1, TimeUnit.SECONDS, Schedulers.computation());


		onNext = new Consumer() {
			@Override
			public void accept(Object o) throws Exception {
				if (null != mediaPlayer && mediaPlayer.isPlaying()) {
					Message msgToPlayingAcitvity = Message.obtain();
					msgToPlayingAcitvity.what = 3;
					msgToPlayingAcitvity.arg1 = mediaPlayer.getCurrentPosition();
					msgToPlayingAcitvity.arg2 = mediaPlayer.getDuration();

					Log.e(TAG, "发给客户端的时间--getCurrentPosition:" + mediaPlayer.getCurrentPosition() + " getDuration" + mediaPlayer.getDuration());
					sendToClient(msgToPlayingAcitvity);
				}
			}
		};


	}

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case 3:

					Message message = Message.obtain(null, 3);
					message.arg1 = 999;
						play("http://ws.stream.qqmusic.qq.com/200881259.m4a?fromtag=46");
					cMessenger = msg.replyTo;
					try {
						cMessenger.send(message);
					} catch (RemoteException e) {
						e.printStackTrace();
					}
					break;
				case 121:
					resume();
					break;
				case 131:
					pause();
					break;
				default:
					super.handleMessage(msg);
			}
		}
	};
	private Messenger mMessenger = new Messenger(handler);//自己的信使从handler获取
	private Messenger cMessenger;//客户端信使从Message获取
	private Disposable disposable;

	@Nullable
	@Override
	public IBinder onBind(Intent intent) {

		return mMessenger.getBinder();
	}

	/**
	 * 只是单纯的请求音乐流
	 *
	 * @param musicUrl
	 */
	private void play(String musicUrl) {
		requestAudioFocus();//请求音频焦点
		if (null == mediaPlayer) return;
		mediaPlayer.reset();//停止音乐后，不重置的话就会崩溃
		try {
			mediaPlayer.setDataSource(getApplicationContext(), Uri.parse(musicUrl));
			Log.e(TAG, "play: " + musicUrl);
			mediaPlayer.prepareAsync();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 音乐暂停
	 */
	private void pause() {
		Log.e(TAG, "pause()");
		if (null == mediaPlayer) return;
		if (mediaPlayer.isPlaying()) {
			currentTime = mediaPlayer.getCurrentPosition();
			mediaPlayer.pause();
		}
	}

	/**
	 * 音乐继续播放
	 */
	public void resume() {
		Log.e(TAG, "resume()");
		if (null == mediaPlayer) return;
		mediaPlayer.start();
		if (currentTime > 0) {
			mediaPlayer.seekTo(currentTime);
		}

	}

	@Override
	public void onCompletion(MediaPlayer mp) {
		Log.e(TAG, "onCompletion: ");
		Message msg = Message.obtain(null, 131);
		sendToClient(msg);
		Log.e(TAG, "onPrepared: ");
	}

	/**
	 * 用客户端的Messenger向客户端发送消息
	 *
	 * @param msg
	 */
	private void sendToClient(Message msg) {
		try {
			if (null != cMessenger) {
				cMessenger.send(msg);
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean onError(MediaPlayer mp, int what, int extra) {
		Log.e(TAG, "onError: ");
		return false;
	}

	@Override
	public void onPrepared(MediaPlayer mp) {
		if (disposable != null) {
			disposable.dispose();
		}
		resume();
		//开始启动一秒一次的计时更新
		disposable = mObservable.subscribe(onNext);
		mediaPlayer.seekTo(150 * 1000);


		Message msg = Message.obtain(null, 121);
		sendToClient(msg);
		Log.e(TAG, "onPrepared: ");
	}

	/**
	 * 放弃音频焦点
	 */
	private void abandonFocus() {
		if (null != onAudioFocusChangeListener) {
			mAudioManager.abandonAudioFocus(onAudioFocusChangeListener);
		}
	}

	/**
	 * 申请音频焦点
	 */
	private void requestAudioFocus() {
		Log.e(TAG, "请求音频焦点requestAudioFocus");
		if (null != onAudioFocusChangeListener) {
			int result = mAudioManager.requestAudioFocus(onAudioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
			if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
				Log.e(TAG, "请求音频焦点成功");
			} else if (result == AudioManager.AUDIOFOCUS_REQUEST_FAILED) {
				Log.e(TAG, "请求音频焦点失败");
			}
		}
	}

	//音频焦点监听处理
	AudioManager.OnAudioFocusChangeListener onAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
		@Override
		public void onAudioFocusChange(int focusChange) {
			switch (focusChange) {
				case AudioManager.AUDIOFOCUS_GAIN:
					//获取音频焦点
					Log.e(TAG, "AUDIOFOCUS_GAIN");
					break;
				case AudioManager.AUDIOFOCUS_LOSS:
					//永久失去 音频焦点
					Log.e(TAG, "AUDIOFOCUS_LOSS");
					//暂停

					abandonFocus();//放弃音频焦点
					break;
				case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
					//暂时失去 音频焦点，并会很快再次获得。必须停止Audio的播放，但是因为可能会很快再次获得AudioFocus，这里可以不释放Media资源
					Log.e(TAG, "AUDIOFOCUS_LOSS_TRANSIENT");
					break;
				case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
					//暂时失去 音频焦点 ，但是可以继续播放，不过要在降低音量。
					Log.e(TAG, "AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK");
					break;
				default:
					Log.e(TAG, "default" + focusChange);
					break;
			}
		}
	};
}
