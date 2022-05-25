package com.yao.chapter2;

import io.reactivex.Observable;

import java.util.concurrent.atomic.AtomicInteger;

public class HotStream{


    static Observable<Integer> create(){
        //订阅者数量,使用原子变量，保证当前共享变量指挥有一个线程使用
        AtomicInteger subscribers = new AtomicInteger();
        AtomicInteger count = new AtomicInteger();
        return Observable.<Integer>create( subscriber -> {
            new Thread( () -> {
                //当订阅者数量大于0的时候他会一直发送
                while (subscribers.get() > 0){
                    //发送数据并自增
                    subscriber.onNext(count.incrementAndGet());
                    //间隔一秒发送一次
                    nap();
                }
            }).start();
            //publish()创建，那么订阅者只能收到在订阅之后Cold Observable发出的数据，而如果使用reply(int N)创建，那么订阅者在订阅后可以收到Cold Observable在订阅之前发送的N个数据
            //autoConnect将connectObservable转化为Observable，特点就是即使所有订阅者全部取消订阅，他还是会继续发送消息,而refCount所有取消订阅后停止发送消息

        }).publish().autoConnect().doOnSubscribe(s -> {
                    System.out.println("one subscribe has register!");
                    //有订阅者订阅，订阅者数量+1
                    subscribers.incrementAndGet();
                })
                .doOnDispose( () -> {
                    System.out.println("one subscribe has leaving!");
                    //有订阅者取消订阅，数量-1
                    subscribers.decrementAndGet();
                });

    }

    public static void nap() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // Ignore me.
        }
    }

    public static void nap(int sec) {
        try {
            Thread.sleep(sec *1000);
        } catch (InterruptedException e) {
            // Ignore me.
        }
    }



}
