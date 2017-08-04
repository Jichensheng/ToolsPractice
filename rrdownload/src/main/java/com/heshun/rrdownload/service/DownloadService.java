package com.heshun.rrdownload.service;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.heshun.rrdownload.MainActivity;
import com.heshun.rrdownload.R;
import com.heshun.rrdownload.bean.Download;
import com.heshun.rrdownload.network.DownloadAPI;
import com.heshun.rrdownload.network.download.DownloadProgressListener;
import com.heshun.rrdownload.utils.StringUtils;

import java.io.File;
import java.io.InputStream;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * 下载服务，发送广播进行回调
 * Created by Jcs on 16/7/5.
 */
public class DownloadService extends IntentService {
	private static final String TAG = "DownloadService";

	private NotificationCompat.Builder notificationBuilder;
	private NotificationManager notificationManager;

	int downloadCount = 0;

	private String apkUrl = "http://download.fir.im/v2/app/install/5818acbcca87a836f50014af?download_token=a01301d7f6f8f4957643c3fcfe5ba6ff";

	public DownloadService() {
		super("DownloadService");
	}

	private File outputFile;

	@Override
	protected void onHandleIntent(Intent intent) {
		notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

		notificationBuilder = new NotificationCompat.Builder(this)
				.setSmallIcon(R.mipmap.ic_download)
				.setContentTitle("Download")
				.setContentText("Downloading File")
				.setAutoCancel(true);

		notificationManager.notify(0, notificationBuilder.build());
		//监听判断

		download();
	}

	private void download() {
		//拦截器与视图的桥梁，回调处理
		//1、实例化监听器
		DownloadProgressListener listener = new DownloadProgressListener() {
			@Override
			//4、收到回调数据
			public void update(long bytesRead, long contentLength, boolean done) {
				//不频繁发送通知，防止通知栏下拉卡顿
				int progress = (int) ((bytesRead * 100) / contentLength);
				if ((downloadCount == 0) || progress > downloadCount) {
					Download download = new Download();
					download.setTotalFileSize(contentLength);
					download.setCurrentFileSize(bytesRead);
					download.setProgress(progress);
					//发广播在此执行
					sendNotification(download);
				}
			}
		};
		outputFile = new File(Environment.getExternalStoragePublicDirectory
				(Environment.DIRECTORY_DOWNLOADS), "file.apk");

		if (outputFile.exists()) {
			outputFile.delete();
		}


		String baseUrl = StringUtils.getHostName(apkUrl);

		new DownloadAPI(baseUrl, listener).downloadAPK(apkUrl, outputFile, new Observer<InputStream>() {
			@Override
			public void onSubscribe(Disposable d) {

			}

			@Override
			public void onNext(InputStream value) {
				//已经在doOnNexr里处理了
			}

			@Override
			public void onError(Throwable e) {
				e.printStackTrace();
				downloadCompleted(false);
				Log.e(TAG, "onError: " + e.getMessage());
			}

			@Override
			public void onComplete() {
				downloadCompleted(true);
			}
		});
	}

	private void downloadCompleted(boolean isSucc) {
		if (isSucc) {
			Download download = new Download();
			download.setProgress(100);
			sendIntent(download);

			notificationManager.cancel(0);
			notificationBuilder.setProgress(0, 0, false);
			notificationBuilder.setContentText("File Downloaded");
			notificationManager.notify(0, notificationBuilder.build());


			//安装apk
			Uri apkUri= FileProvider.getUriForFile(this,"com.heshun.rrdownload.fileprovider",outputFile);
			Intent intent = new Intent(Intent.ACTION_VIEW);
			intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
			//添加这一句表示对目标应用临时授权该Uri所代表的文件
			intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//			intent.setDataAndType(Uri.fromFile(outputFile), "application/vnd.android.package-archive");
			intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
			startActivity(intent);
		}

	}

	private void sendNotification(Download download) {
		//发广播
		sendIntent(download);
		notificationBuilder.setProgress(100, download.getProgress(), false);
		notificationBuilder.setContentText(
				StringUtils.getDataSize(download.getCurrentFileSize()) + "/" +
						StringUtils.getDataSize(download.getTotalFileSize()));
		notificationManager.notify(0, notificationBuilder.build());
	}

	/**
	 * 发送广播
	 * @param download
	 */
	private void sendIntent(Download download) {

		Intent intent = new Intent(MainActivity.MESSAGE_PROGRESS);
		intent.putExtra("download", download);
		LocalBroadcastManager.getInstance(DownloadService.this).sendBroadcast(intent);
	}

	@Override
	public void onTaskRemoved(Intent rootIntent) {
		notificationManager.cancel(0);
	}

}
