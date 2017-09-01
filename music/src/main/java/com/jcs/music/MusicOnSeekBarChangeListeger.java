package com.jcs.music;

import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import android.widget.SeekBar;

import com.jcs.music.activity.MusicActivity;

import java.lang.ref.WeakReference;

import static android.content.ContentValues.TAG;

/**
 * author：Jics
 * 2017/2/13 13:41
 */

public class MusicOnSeekBarChangeListeger implements SeekBar.OnSeekBarChangeListener {
    private WeakReference<MusicActivity> weakActivity;
    public MusicOnSeekBarChangeListeger(MusicActivity activity) {
        weakActivity = new WeakReference<>(activity);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        MusicActivity activity = weakActivity.get();
        if (fromUser && null != activity) {//判断来自用户的滑动
            activity.mPercent = (float) progress * 100 / (float) activity.mSeekBar.getMax();
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        MusicActivity activity = weakActivity.get();
        activity.isTrackingTouch=true;
        Log.e(TAG, "onStartTrackingTouch: "+activity.isTrackingTouch );
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        //用户松开SeekBar，通知MediaPlayerService更新播放器的进度，解决拖动过程中卡顿的问题
        MusicActivity activity = weakActivity.get();
        if (null != activity) {
            activity.isTrackingTouch=false;

            Log.e(TAG, "onStartTrackingTouch: "+activity.isTrackingTouch );
            Message msgToMediaPlayerService = Message.obtain();
            msgToMediaPlayerService.what = Constant.PLAYING_ACTIVITY_CUSTOM_PROGRESS;
            msgToMediaPlayerService.arg1 = (int) activity.mPercent;
            try {
                activity.mServiceMessenger.send(msgToMediaPlayerService);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }
}
