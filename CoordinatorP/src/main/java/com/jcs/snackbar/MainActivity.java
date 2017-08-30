package com.jcs.snackbar;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.allen.library.SuperTextView;
import com.jaeger.library.StatusBarUtil;
import com.jcs.snackbar.widget.Bitmaptest;
import com.jcs.snackbar.widget.FastBlur;
import com.jcs.snackbar.widget.IconFontTabView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //注意了，这里使用了第三方库 StatusBarUtil，目的是改变状态栏的alpha
        StatusBarUtil.setTransparentForImageView(this, null);
        final ImageView imageView = (ImageView) findViewById(R.id.iv_img);
        final Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.head);
        blur(bitmap, imageView);
        SuperTextView superTextView= (SuperTextView) findViewById(R.id.stv_notation);
        superTextView.setSwitchCheckedChangeListener(new SuperTextView.OnSwitchCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Toast.makeText(MainActivity.this, String.valueOf(b), Toast.LENGTH_SHORT).show();
            }
        });

        IconFontTabView iconFontTabView= (IconFontTabView) findViewById(R.id.iftv_guanzhu);
        iconFontTabView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    private void blur(Bitmap bkg, ImageView view) {
        float radius = 20;
        view.setImageBitmap(FastBlur.doBlur(Bitmaptest.fitBitmap(bkg, 200), (int) radius, true));
    }
}
