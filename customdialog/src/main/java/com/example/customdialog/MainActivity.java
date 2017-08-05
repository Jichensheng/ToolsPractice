package com.example.customdialog;

import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        makeDialog();
        UpdataDialog updataDialog=new UpdataDialog(this,R.layout.loading,new int[0]);
        updataDialog.show();
    }

    private void makeDialog() {
        final UpdataDialog updataDialog = new UpdataDialog(this, R.layout.dialog_updataversion,
                new int[]{R.id.dialog_sure});
        show = (Button) findViewById(R.id.show);
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updataDialog.show();
            }
        });
        updataDialog.setOnCenterItemClickListener(new UpdataDialog.OnCenterItemClickListener() {
            @Override
            public void OnCenterItemClick(UpdataDialog dialog, View view) {
                switch (view.getId()) {
                    case R.id.dialog_sure:
                        /**调用系统自带的浏览器去下载最新apk*/
                        Toast.makeText(MainActivity.this, "hehehh", Toast.LENGTH_SHORT).show();
                        DialogHelper.getInstance(MainActivity.this)
                                .show(new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Toast.makeText(MainActivity.this, "自定义", Toast.LENGTH_SHORT).show();
                                    }
                                }, true, R.layout.dialog_updataversion, "Title", "内容");
                        break;
                }
                updataDialog.dismiss();
            }
        });
    }
}
