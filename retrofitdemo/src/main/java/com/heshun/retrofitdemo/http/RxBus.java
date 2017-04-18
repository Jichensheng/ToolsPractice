package com.heshun.retrofitdemo.http;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * 时间：2016/6/2 13:50
 * 邮箱：tailyou@163.com
 * 描述：
 */
public class RxBus {

    private static volatile RxBus mInstance;
    //Subject同时充当了Observer和Observable的角色
    // Subject是非线程安全的，要避免该问题
    // 需要将 Subject转换为一个 SerializedSubject
    // 构造函数里把线程非安全的PublishSubject包装成线程安全的SerializedSubject
    private final Subject<Object, Object> bus;

    private RxBus() {
        bus = new SerializedSubject<>(PublishSubject.create());
    }

    /**
     * 单例RxBus
     *
     * @return
     */
    public static RxBus getDefault() {
        RxBus rxBus = mInstance;
        if (mInstance == null) {
            synchronized (RxBus.class) {
                rxBus = mInstance;
                if (mInstance == null) {
                    rxBus = new RxBus();
                    mInstance = rxBus;
                }
            }
        }
        return rxBus;
    }

    /**
     * 发送一个新事件
     *
     * @param o
     */
    public void post(Object o) {
        bus.onNext(o);
    }

    /**
     * 返回特定类型的被观察者
     *
     * @param eventType
     * @param <T>
     * @return
     */
    public <T> Observable<T> toObservable(Class<T> eventType) {
        //ofType操作符只发射指定类型的数据，其内部就是filter+cast
        //filter操作符可以使你提供一个指定的测试数据项，只有通过测试的数据才会被“发射”。
        //cast操作符可以将一个Observable转换成指定类型的Observable

        return bus.ofType(eventType);
    }

}
