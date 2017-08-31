package com.jcs.music.service;


import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.jcs.music.CDView;
import com.jcs.music.R;

public class MessengerServiceActivities {


	public static class Binding extends Activity implements View.OnClickListener, CDView.OnPlayListener, CDView.OnStopListener {
		private CDView cdView;
		private String TAG = "Binding";

		TextView mCallbackText;

		private boolean isBound;

		@Override
		protected void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
			setContentView(R.layout.messenger_service_binding);
			findViewById(R.id.bind).setOnClickListener(this);
			findViewById(R.id.unbind).setOnClickListener(this);

			cdView = (CDView) findViewById(R.id.cd_music);

			cdView.setOnPlayListener(this);
			cdView.setOnStopListener(this);
			mCallbackText = (TextView) findViewById(R.id.callback);
			mCallbackText.setText("Not attached.");

			Intent intent = new Intent(this, TService.class);
			if (!isBound) {
				isBound = bindService(intent, connection, BIND_AUTO_CREATE);
				//isBound = true;
			} else {
				sendMessage();
			}

		}

		private Handler mHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				Log.i(TAG, "handleMessage");
				switch (msg.what) {
					case MessengerService.MSG_SET_VALUE:
						mCallbackText.setText("Received from service: " + (float) msg.arg1 / 1000 + " / " + (float) msg.arg2 / 1000);
						break;
					case 121:
						cdView.playMusic();
						break;
					case 131:
						cdView.stopMusic();
						cdView.resetCD();
						break;
					case 4:
						Toast.makeText(Binding.this, "已缓存"+msg.arg1+"%", Toast.LENGTH_SHORT).show();
						break;
					default:
						super.handleMessage(msg);
				}
			}
		};

		/**
		 * 自己的信使 从handler获取
		 */
		private Messenger mMessenger;

		/**
		 * 远程服务的信使从IBinder获取
		 */
		private Messenger rMessenger;

		private ServiceConnection connection = new ServiceConnection() {

			public void onServiceConnected(ComponentName name, IBinder service) {
				Log.i(TAG, "onServiceConnected");
				rMessenger = new Messenger(service);
				mMessenger = new Messenger(mHandler);

				sendMessage();
			}


			public void onServiceDisconnected(ComponentName name) {
				rMessenger = null;
			}
		};

		public void onClick(View v) {
			Intent intent = new Intent(this, TService.class);
			switch (v.getId()) {
				case R.id.bind:
				/*	if (!isBound) {
						isBound = bindService(intent, connection, BIND_AUTO_CREATE);
						//isBound = true;
					} else {
						sendMessage();
					}*/
					break;
				case R.id.unbind:
					if (isBound) {
						unbindService(connection);
						isBound = false;
					}
					break;
				default:
					break;
			}
		}
/**
 * Message携带消息，信使传递Message,handler中处理Message
 * 1、一个信使关联一个handler,信使发送的Message，只有与之对应的handler中才可以处理
 * 2、服务端获取客户端的信使需要在Handler的message中通过{msg.replyTo}获取，此处的msg就是信使send的在客户端构建的Message实例，
 * 如何把客户端信使加到Message中呢？“message.replyTo=客户端信使” 即可
 * 3、客户端获取服务器端的信使需要在ServiceConnection中通过获取到的IBinder去构建“new Messenger(service)”
 * 如何把服务器端的信使附加到IBinder中呢，只用"Messenger.getBinder()" 即可
 */
		/**
		 * 使用服务端的信使向它发送一个消息。
		 */
		private void sendMessage() {
			Message message = Message.obtain(null, MessengerService.MSG_SET_VALUE);
			message.replyTo = mMessenger;
			try {
				if (rMessenger != null) {
					rMessenger.send(message);
				}
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}


		@Override
		public void onPlayMusic() {
			Message message = Message.obtain(null, 121);
			try {
				if (rMessenger != null) {
					rMessenger.send(message);
				}
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void onStopMusic() {
			Message message = Message.obtain(null, 131);
			try {
				if (rMessenger != null) {
					rMessenger.send(message);
				}
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}

}  