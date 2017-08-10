package com.jcs.mobshare;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.shareboard.ShareBoardConfig;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.Log;
import com.umeng.socialize.utils.ShareBoardlistener;

import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity {
	private String TAG = "Jcs";
	private Button button, button2;
	private ShareAction mShareAction;
	private UMShareListener mShareListener;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		button = (Button) findViewById(R.id.button);
		button2 = (Button) findViewById(R.id.button2);

		mShareListener = new CustomShareListener(this);
/*增加自定义按钮的分享面板*/
		mShareAction = new ShareAction(MainActivity.this)
				//各大平台按钮
				.setDisplayList(
						SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.WEIXIN_FAVORITE,
						SHARE_MEDIA.SINA, SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE,
						SHARE_MEDIA.ALIPAY, SHARE_MEDIA.RENREN, SHARE_MEDIA.DOUBAN,
						SHARE_MEDIA.SMS/*短信按钮*/, SHARE_MEDIA.EMAIL, SHARE_MEDIA.YNOTE,
						SHARE_MEDIA.EVERNOTE, SHARE_MEDIA.LAIWANG, SHARE_MEDIA.LAIWANG_DYNAMIC,
						SHARE_MEDIA.LINKEDIN, SHARE_MEDIA.YIXIN, SHARE_MEDIA.YIXIN_CIRCLE,
						SHARE_MEDIA.TENCENT, SHARE_MEDIA.FACEBOOK, SHARE_MEDIA.TWITTER,
						SHARE_MEDIA.WHATSAPP, SHARE_MEDIA.GOOGLEPLUS, SHARE_MEDIA.LINE,
						SHARE_MEDIA.INSTAGRAM, SHARE_MEDIA.KAKAO, SHARE_MEDIA.PINTEREST,
						SHARE_MEDIA.POCKET, SHARE_MEDIA.TUMBLR, SHARE_MEDIA.FLICKR,
						SHARE_MEDIA.FOURSQUARE, SHARE_MEDIA.MORE/*更多按钮*/)
				//自定义按钮 显示的文字  关键字 图标 灰色图标
				.addButton("umeng_sharebutton_copy", "umeng_sharebutton_copy", "umeng_socialize_copy", "umeng_socialize_copy")
				.addButton("umeng_sharebutton_copyurl", "umeng_sharebutton_copyurl", "umeng_socialize_copyurl", "umeng_socialize_copyurl")
				//分享面板点击事件
				.setShareboardclickCallback(new ShareBoardlistener() {
					@Override
					public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
						//自定义按钮触发
						if (snsPlatform.mShowWord.equals("umeng_sharebutton_copy")) {
							Toast.makeText(MainActivity.this, "复制文本按钮", Toast.LENGTH_LONG).show();
						} else if (snsPlatform.mShowWord.equals("umeng_sharebutton_copyurl")) {
							Toast.makeText(MainActivity.this, "复制链接按钮", Toast.LENGTH_LONG).show();

						} else if (share_media == SHARE_MEDIA.SMS) {//短信按钮触发的
							new ShareAction(MainActivity.this).withText("来自分享面板标题")
									.setPlatform(share_media)
									.setCallback(mShareListener)
									.share();
						} else {
							//可跳转到相应平台的按钮事件
							UMWeb web = new UMWeb("http://www.baidu.com");
							web.setTitle("来自分享面板标题");
							web.setDescription("来自分享面板内容");
							web.setThumb(new UMImage(MainActivity.this, R.drawable.umeng_socialize_qq));
							new ShareAction(MainActivity.this).withMedia(web)
									.setPlatform(share_media)
									.setCallback(mShareListener)
									.share();
						}
					}
				});
		findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//中部弹出分享页
				ShareBoardConfig config = new ShareBoardConfig();
				config.setShareboardPostion(ShareBoardConfig.SHAREBOARD_POSITION_CENTER);
				config.setMenuItemBackgroundShape(ShareBoardConfig.BG_SHAPE_CIRCULAR); // 圆角背景
				//构造函数open（）是默认下边弹出分享页，带config参数的可以控制位置
				mShareAction.open(config);
			}
		});
		checkPermission();


	}

	private void checkPermission() {
		if (Build.VERSION.SDK_INT >= 23) {
			String[] mPermissionList = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
					Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CALL_PHONE,
					Manifest.permission.READ_LOGS, Manifest.permission.READ_PHONE_STATE,
					Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.SET_DEBUG_APP,
					Manifest.permission.SYSTEM_ALERT_WINDOW, Manifest.permission.GET_ACCOUNTS, Manifest.permission.WRITE_APN_SETTINGS};
			ActivityCompat.requestPermissions(this, mPermissionList, 123);
		}
	}

	@Override
	public void onRequestPermissionsResult(int requestCode,
										   String permissions[], int[] grantResults) {


	}

	/**
	 * 自定义分享毁掉监听器
	 */
	private class CustomShareListener implements UMShareListener {
		private WeakReference<MainActivity> mActivity;

		private CustomShareListener(MainActivity activity) {
			mActivity = new WeakReference(activity);
		}

		@Override
		public void onStart(SHARE_MEDIA platform) {

		}

		@Override
		public void onResult(SHARE_MEDIA platform) {

			if (platform.name().equals("WEIXIN_FAVORITE")) {
				Toast.makeText(MainActivity.this, platform + " 收藏成功啦", Toast.LENGTH_SHORT).show();
			} else {
				if (platform != SHARE_MEDIA.MORE && platform != SHARE_MEDIA.SMS
						&& platform != SHARE_MEDIA.EMAIL
						&& platform != SHARE_MEDIA.FLICKR
						&& platform != SHARE_MEDIA.FOURSQUARE
						&& platform != SHARE_MEDIA.TUMBLR
						&& platform != SHARE_MEDIA.POCKET
						&& platform != SHARE_MEDIA.PINTEREST

						&& platform != SHARE_MEDIA.INSTAGRAM
						&& platform != SHARE_MEDIA.GOOGLEPLUS
						&& platform != SHARE_MEDIA.YNOTE
						&& platform != SHARE_MEDIA.EVERNOTE) {
					Toast.makeText(MainActivity.this, platform + " 分享成功啦", Toast.LENGTH_SHORT).show();
				}

			}
		}

		@Override
		public void onError(SHARE_MEDIA platform, Throwable t) {
			if (platform != SHARE_MEDIA.MORE && platform != SHARE_MEDIA.SMS
					&& platform != SHARE_MEDIA.EMAIL
					&& platform != SHARE_MEDIA.FLICKR
					&& platform != SHARE_MEDIA.FOURSQUARE
					&& platform != SHARE_MEDIA.TUMBLR
					&& platform != SHARE_MEDIA.POCKET
					&& platform != SHARE_MEDIA.PINTEREST

					&& platform != SHARE_MEDIA.INSTAGRAM
					&& platform != SHARE_MEDIA.GOOGLEPLUS
					&& platform != SHARE_MEDIA.YNOTE
					&& platform != SHARE_MEDIA.EVERNOTE) {
				Toast.makeText(MainActivity.this, platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
				if (t != null) {
					Log.d("throw", "throw:" + t.getMessage());
				}
			}

		}

		@Override
		public void onCancel(SHARE_MEDIA platform) {

			Toast.makeText(MainActivity.this, platform + " 分享取消了", Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		/** attention to this below ,must add this**/
		UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
	}
}
