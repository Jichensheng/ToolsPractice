package com.heshun.rrdownload;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.heshun.rrdownload.bean.Download;
import com.heshun.rrdownload.service.DownloadService;
import com.heshun.rrdownload.utils.StringUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 下载过程分两路走
 * 第一路通过retrofit的get请求获取到ResponseBody，通过RxJava转换成InputStream异步写入文件
 * 第二路在okHttp层添加拦截器，通过拦截器获取到Response的body数据，通过广播实时更新下载进度
 */
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    public static final String MESSAGE_PROGRESS = "message_progress";
    private LocalBroadcastManager bManager;


    private AppCompatButton btn_download;
    private ProgressBar progress;
    private TextView progress_text;

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent.getAction().equals(MESSAGE_PROGRESS)) {

                Download download = intent.getParcelableExtra("download");
                Log.e("---------------", "onReceive: " );
                progress.setProgress(download.getProgress());
                if (download.getProgress() == 100) {

                    progress_text.setText("File Download Complete");

                } else {

                    progress_text.setText(
                            StringUtils.getDataSize(download.getCurrentFileSize())
                            + "/" +
                            StringUtils.getDataSize(download.getTotalFileSize()));

                }
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_download = (AppCompatButton) findViewById(R.id.btn_download);
        progress = (ProgressBar) findViewById(R.id.progress);
        progress_text = (TextView) findViewById(R.id.progress_text);

        registerReceiver();

        btn_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RxPermissions rxPermissions = new RxPermissions(MainActivity.this);
                rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .subscribe(new Observer<Boolean>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(Boolean aBoolean) {
                                if (aBoolean) {
                                    Intent intent = new Intent(MainActivity.this, DownloadService.class);
                                    startService(intent);
                                } else {
                                    Toast.makeText(MainActivity.this, "权限没允许", Toast.LENGTH_LONG)
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
        });
    }

    private void registerReceiver() {

        bManager = LocalBroadcastManager.getInstance(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(MESSAGE_PROGRESS);
        bManager.registerReceiver(broadcastReceiver, intentFilter);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //解除注册时，使用注册时的manager解绑
        bManager.unregisterReceiver(broadcastReceiver);
    }
}
