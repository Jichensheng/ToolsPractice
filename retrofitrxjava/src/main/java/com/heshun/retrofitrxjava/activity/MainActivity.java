package com.heshun.retrofitrxjava.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.heshun.retrofitrxjava.R;
import com.heshun.retrofitrxjava.entity.HeadDefault;
import com.heshun.retrofitrxjava.entity.pojo.CommonUser;
import com.heshun.retrofitrxjava.entity.pojo.Order;
import com.heshun.retrofitrxjava.entity.pojo.Pic;
import com.heshun.retrofitrxjava.entity.pojo.PileStation;
import com.heshun.retrofitrxjava.entity.pojo.UpdataVersion;
import com.heshun.retrofitrxjava.entity.stable.Data;
import com.heshun.retrofitrxjava.entity.stable.DefaultResult;
import com.heshun.retrofitrxjava.http.HttpMethods;
import com.heshun.retrofitrxjava.subscribers.CommonSubscriber;
import com.heshun.retrofitrxjava.subscribers.ProgressSubscriber;
import com.heshun.retrofitrxjava.subscribers.SubscriberOnNextListener;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * http://gank.io/post/56e80c2c677659311bed9841
 */
public class MainActivity extends AppCompatActivity {

    @Bind(R.id.btn_1)
    Button btn_1;
    @Bind(R.id.btn_2)
    Button btn_2;
    @Bind(R.id.btn_3)
    Button btn_3;
    @Bind(R.id.btn_4)
    Button btn_4;
    @Bind(R.id.btn_5)
    Button btn_5;
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
                if (data instanceof DefaultResult) {
                    resultTV.setText(data.toString());
                }else{
                    if (data.getBody() != null) {
                        List<Order> list=data.getBody();
                        String s=data.getHead().toString()+"\n\n";
                        for (Order order : list) {
                            s+=order.toString()+"\n\n";
                        }
                        resultTV.setText(s);
                    }else
                        resultTV.setText("暂无数据");
                }
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

    public void click(View view) {
        switch (view.getId()){

            case R.id.btn_1:
                HttpMethods.getInstance().getPic(new ProgressSubscriber(new SubscriberOnNextListener<Data<HeadDefault,List<Pic>>>() {
                    @Override
                    public void onNext(Data<HeadDefault, List<Pic>> data) {
                        if (data instanceof DefaultResult) {
                            resultTV.setText(data.toString());
                        }else{
                            if (data.getBody() != null) {
                                List<Pic> list=data.getBody();
                                String s=data.getHead().toString()+"\n\n";
                                for (Pic pic : list) {
                                    s+=pic.toString()+"\n\n";
                                }
                                resultTV.setText(s);
                            }else
                                resultTV.setText("暂无数据");
                        }
                    }
                },MainActivity.this),5,1,1);
                break;


            case R.id.btn_2:
                HttpMethods.getInstance().getOrderList(new ProgressSubscriber(testOnNext,MainActivity.this),"NmIzOGJlZWIzMTlmNGQ4ZmI4YzE1ODZlZDc0OWM2YWY=",1,10,1);
                break;


            case R.id.btn_3:
                //120.674771/31.355091/50/10/1?city=苏州市
                HttpMethods.getInstance().getPileStation(new ProgressSubscriber(new SubscriberOnNextListener<Data<HeadDefault,List<PileStation>>>() {
                    @Override
                    public void onNext(Data<HeadDefault, List<PileStation>> data) {
                        if (data.getBody() != null) {
                            List<PileStation> list=data.getBody();
                            String s=data.getHead().toString()+"\n\n";
                            for (PileStation pileStation : list) {
                                s+=pileStation.toString()+"\n\n";
                            }
                            resultTV.setText(s);
                        }else
                            resultTV.setText("暂无数据");
                    }
                },MainActivity.this),120.674771,31.355091,50,10,1,"苏州市");
                break;


            case R.id.btn_4:
                HttpMethods.getInstance().getMoney(new CommonSubscriber(new SubscriberOnNextListener<Data<HeadDefault,CommonUser>>() {

                    @Override
                    public void onNext(Data<HeadDefault, CommonUser> headDefaultCommonUserData) {
                        resultTV.setText(headDefaultCommonUserData.getBody().toString());
                    }
                }, MainActivity.this), "NmIzOGJlZWIzMTlmNGQ4ZmI4YzE1ODZlZDc0OWM2YWY=");
                break;


            case R.id.btn_5:
                HttpMethods.getInstance().checkUpdata(new CommonSubscriber(new SubscriberOnNextListener<Data<HeadDefault,UpdataVersion>>() {
                    @Override
                    public void onNext(Data<HeadDefault, UpdataVersion> headDefaultUpdataVersionData) {
                        resultTV.setText(headDefaultUpdataVersionData.getBody().toString());
                    }
                }, MainActivity.this),6);
                break;
        }
    }
}
