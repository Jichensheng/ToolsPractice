package com.heshun.retrofitrxjavaStep8.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.heshun.retrofitrxjavaStep8.R;
import com.heshun.retrofitrxjavaStep8.http.HttpMethods2;
import com.heshun.retrofitrxjavaStep8.subscribers.CommonSubscriber;
import com.heshun.retrofitrxjavaStep8.subscribers.SubscriberOnNextListener;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.click_me_BN)
    Button clickMeBN;
    @Bind(R.id.result_TV)
    TextView resultTV;

    private SubscriberOnNextListener getTopMovieOnNext,testOnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        /*getTopMovieOnNext = new SubscriberOnNextListener<List<Movies>>() {
            @Override
            public void onNext(List<Movies> movies) {
                resultTV.setText(movies.toString());
            }
        };*/
        testOnNext=new SubscriberOnNextListener<String>() {
            @Override
            public void onNext(String s) {
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
        getMovie();
    }

    //进行网络请求
    private void getMovie(){
//        HttpMethods.getInstance().getTopMovie(new CommonSubscriber(getTopMovieOnNext, MainActivity.this), 0, 10);
        HttpMethods2.getInstance().getPic(new CommonSubscriber(testOnNext,MainActivity.this),5,1,1);
    }
}
