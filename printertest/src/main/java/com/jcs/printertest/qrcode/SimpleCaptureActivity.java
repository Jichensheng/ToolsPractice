package com.jcs.printertest.qrcode;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import com.jcs.printertest.MainActivity;

import io.github.xudaojie.qrcodelib.CaptureActivity;

/**
 * Created by xdj on 16/9/17.
 */

public class SimpleCaptureActivity extends CaptureActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void handleResult(String resultString) {
        if (TextUtils.isEmpty(resultString)) {
            Toast.makeText(SimpleCaptureActivity.this, io.github.xudaojie.qrcodelib.R.string.scan_failed, Toast.LENGTH_SHORT).show();

            restartPreview();
        } else {
            Intent intent=new Intent(SimpleCaptureActivity.this,MainActivity.class);
            intent.putExtra("result",resultString);
            setResult(RESULT_OK,intent);
            finish();
        }
    }

}