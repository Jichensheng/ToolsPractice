package com.example.customdialog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.view.View;

/**
 * Created by Jcs on 2017/8/5.
 */

public class DialogHelper {
    private static DialogHelper instance;
    private Context context;

    private DialogHelper(Context context) {
        this.context = context;

    }

    public static DialogHelper getInstance(Context context) {

        synchronized (DialogHelper.class) {
            if (instance == null) {
                instance = new DialogHelper(context);
            }
            return instance;
        }
    }

    /**
     * 自带的dialog
     * @param listenerOk 回调函数
     * @param cancelable 是否能通过其他方式取消
     * @param viewId     自定义view
     * @param title
     * @param content
     * @return
     */
    public AlertDialog show(DialogInterface.OnClickListener listenerOk,
                            boolean cancelable, int viewId, String title,String content) {
//        AlertDialog.Builder dialog = new AlertDialog.Builder(context,R.style.CustomDialog);
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        if (title != null) {
            dialog.setTitle(title);
        }
        if (content != null) {
            dialog.setMessage(content);
        }
        dialog.setCancelable(cancelable);
        dialog.setPositiveButton("确定", listenerOk);
        dialog.setNegativeButton("取消", null);
        if (viewId != 0) {
            dialog.setView(viewId);
        }
        return  dialog.show();
    }

}
