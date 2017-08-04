package com.jcs.picpicker;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.PicassoEngine;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

	private static final int REQUEST_CODE_CHOOSE=0x01;

	final private int REQUEST_CODE_ASK_PERMISSIONS = 0x02;

	private List<Uri> mSelected;
	private TextView tv;
	private Button button;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		checkPermission();
		tv= (TextView) findViewById(R.id.tv_hello);
		button= (Button) findViewById(R.id.button);
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Matisse.from(MainActivity.this)
						.choose(MimeType.ofAll(), false) // 选择 mime 的类型
						.countable(true)
						.maxSelectable(9) // 图片选择的最多数量
						.gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
						.restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
						.theme( R.style.Matisse_Dracula)
						.thumbnailScale(0.85f) // 缩略图的比例
						.imageEngine(new PicassoEngine()) // 使用的图片加载引擎
						.forResult(REQUEST_CODE_CHOOSE); // 设置作为标记的请求码
			}
		});



	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
			mSelected = Matisse.obtainResult(data);
			tv.setText(mSelected.toString());
			Log.d("Matisse", "mSelected: " + mSelected);
		}
	}

	public void checkPermission() {
		if (Build.VERSION.SDK_INT >= 23) {
			List<String> permissionStrs = new ArrayList<>();
			int hasWriteSdcardPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
			if (hasWriteSdcardPermission != PackageManager.PERMISSION_GRANTED) {
				permissionStrs.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
			}

/*			int hasCameraPermission = ContextCompat.checkSelfPermission(
					StartPage.this,
					Manifest.permission.CAMERA);
			if (hasCameraPermission != PackageManager.PERMISSION_GRANTED) {
				permissionStrs.add(Manifest.permission.CAMERA);
			}*/
			String[] stringArray = permissionStrs.toArray(new String[0]);
			if (permissionStrs.size() > 0) {
				requestPermissions(stringArray, REQUEST_CODE_ASK_PERMISSIONS);
				return;
			}
		}
	}

	//权限设置后的回调函数，判断相应设置，requestPermissions传入的参数为几个权限，则permissions和grantResults为对应权限和设置结果
	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		switch (requestCode) {
			case REQUEST_CODE_ASK_PERMISSIONS:
				//可以遍历每个权限设置情况
				if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
					//这里写你需要相关权限的操作
				} else {
					Toast.makeText(this, "权限没有开启", Toast.LENGTH_SHORT).show();
				}
		}
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
	}
}
