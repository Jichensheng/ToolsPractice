package com.jcs.md5sign;


import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity
{
	private Button mGenBtn;
	private EditText mPackageEt;
	private TextView md5SignTv;


	protected void onCreate(Bundle paramBundle)
	{
		super.onCreate(paramBundle);
		setContentView(R.layout.activity_main);
		md5SignTv = ((TextView)findViewById(R.id.tv_md5));
		mPackageEt = ((EditText)findViewById(R.id.ev_package));
		mGenBtn = ((Button)findViewById(R.id.btn_get));
		mGenBtn.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View paramView)
			{
				String packageName = mPackageEt.getText().toString();
				if (TextUtils.isEmpty(packageName));
				byte[] arrayOfByte;
				arrayOfByte =  getSign(MainActivity.this, packageName);
				String result = "";
				try
				{
					result = MD5.hexdigest(arrayOfByte);
					md5SignTv.setText(result);

					ClipboardManager cmb = (ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
					cmb.setPrimaryClip(ClipData.newPlainText(null, result));
					Toast.makeText(MainActivity.this, "已经复制到剪贴板", Toast.LENGTH_SHORT).show();
					return;
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});

	}


	public static byte[] getSign(Context paramContext, String paramString)
	{

		PackageInfo localPackageInfo;
		int i;
		byte[] arrayOfByte;
		try
		{
			localPackageInfo = paramContext.getPackageManager().getPackageInfo(paramString, PackageManager.GET_SIGNATURES);
			i = 0;
			if (i >= localPackageInfo.signatures.length) {
				return null;
			}
			arrayOfByte = localPackageInfo.signatures[i].toByteArray();
			return arrayOfByte;
		}
		catch (PackageManager.NameNotFoundException localNameNotFoundException)
		{
			return null;
		}

	}
}
