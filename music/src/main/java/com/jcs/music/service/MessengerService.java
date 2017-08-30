package com.jcs.music.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import java.util.Random;
  
public class MessengerService extends Service {
	private String TAG = "MessengerService";
  
    @Override  
    public void onDestroy() {  
        // TODO Auto-generated method stub  
        Log.i(TAG, "onDestroy");  
        cMessenger = null;  
        super.onDestroy();  
    }  
  
    @Override  
    public boolean onUnbind(Intent intent) {  
        // TODO Auto-generated method stub  
        Log.i(TAG, "onUnbind");  
        return super.onUnbind(intent);  
    }  
  
    static final int MSG_REGISTER_CLIENT = 1;  
    static final int MSG_UNREGISTER_CLIENT = 2;  
    static final int MSG_SET_VALUE = 3;  
  
    private Random random = new Random();  
  
    private Handler mHandler = new Handler() {  
  
        @Override  
        public void handleMessage(Message msg) {  
            // TODO Auto-generated method stub  
            Log.i(TAG, "handleMessage");  
            switch (msg.what) {  
            case MSG_SET_VALUE:  
                try {  
                    Message message = Message.obtain(null,  
                            MessengerService.MSG_SET_VALUE);  
                    message.arg1 = random.nextInt(100);  
                      
                    //得到客户端的信使对象，并向它发送消息  
                    cMessenger = msg.replyTo;  
                    cMessenger.send(message);  
                } catch (RemoteException e) {  
                    // TODO Auto-generated catch block  
                    e.printStackTrace();  
                }  
                break;  
            default:  
                super.handleMessage(msg);  
            }  
  
        }  
    };  
  
    /** 
     * 自己的信使对象 
     */  
    private Messenger mMessenger = new Messenger(mHandler);  
  
    /** 
     * 客户的信使 
     */  
    private Messenger cMessenger;  
  
    @Override  
    public IBinder onBind(Intent intent) {  
        // TODO Auto-generated method stub  
        Log.i(TAG, "onBind");  
        //返回自己信使的bindler,以供客户端通过这个bindler得到服务端的信使对象（通过new Messenger(bindler)）  
        return mMessenger.getBinder();  
    }  
  
    @Override  
    public void onRebind(Intent intent) {  
        // TODO Auto-generated method stub  
        Log.i(TAG, "onRebind");  
  
    }  
  
}  