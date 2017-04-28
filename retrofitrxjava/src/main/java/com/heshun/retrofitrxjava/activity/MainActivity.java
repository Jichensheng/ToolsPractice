package com.heshun.retrofitrxjava.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.heshun.retrofitrxjava.R;
import com.heshun.retrofitrxjava.entity.HeadDefault;
import com.heshun.retrofitrxjava.entity.Order;
import com.heshun.retrofitrxjava.entity.stable.Data;
import com.heshun.retrofitrxjava.http.HttpMethods;
import com.heshun.retrofitrxjava.subscribers.ProgressSubscriber;
import com.heshun.retrofitrxjava.subscribers.SubscriberOnNextListener;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * http://gank.io/post/56e80c2c677659311bed9841
 */
public class MainActivity extends AppCompatActivity {

    @Bind(R.id.click_me_BN)
    Button clickMeBN;
    @Bind(R.id.result_TV)
    TextView resultTV;

    private SubscriberOnNextListener<Data<HeadDefault, List<Order>>> testOnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //此处给Data<T,E>赋类型
        testOnNext=new SubscriberOnNextListener<Data<HeadDefault,List<Order>>>() {
            @Override
            public void onNext(Data<HeadDefault, List<Order>> data) {
                List<Order> list=data.getBody();
                String s=data.getHead().toString()+"\n\n";
                for (Order order : list) {
                    s+=order.toString()+"\n\n";
                }
                resultTV.setText(s);
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();


    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @OnClick(R.id.click_me_BN)
    public void onClick() {
        getPic();
    }

    //进行网络请求
    private void getPic(){
//        HttpMethods.getInstance().getPic(new ProgressSubscriber(testOnNext,MainActivity.this),5,1,1);
        HttpMethods.getInstance().getOrderList(new ProgressSubscriber(testOnNext,MainActivity.this),"NzY2MTUxZmI2YTE5NGEwYzhlODdmM2RiYmI5ZTY5ODA=",1,10,1);
    }
}
