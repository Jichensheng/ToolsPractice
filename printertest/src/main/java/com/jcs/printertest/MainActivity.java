package com.jcs.printertest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jcs.printertest.qrcode.SimpleCaptureActivity;

public class MainActivity extends AppCompatActivity {
	public static final int REQUEST_QR_CODE = 1;
	private TextView textView;
	private Button button;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		textView= (TextView) findViewById(R.id.textView);
		button= (Button) findViewById(R.id.button);
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(MainActivity.this, SimpleCaptureActivity.class);
				MainActivity.this.startActivityForResult(i, REQUEST_QR_CODE);
			}
		});

	}

	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK
                && requestCode == REQUEST_QR_CODE
                && data != null) {
            String result = data.getStringExtra("result");
			textView.setText(result);
        }
    }
}
