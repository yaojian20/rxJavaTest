package com.yao.chapter6;

import com.yao.superheroes.Helpers;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import static java.util.concurrent.Executors.newFixedThreadPool;

public class Code6 {

    private static List<String> SUPER_HEROES = Arrays.asList(
            "Superman",
            "Batman",
            "Aquaman",
            "Asterix",
            "Captain America"
    );

    public static void main(String[] args) throws Exception {

        Scheduler scheduler = Schedulers.from(newFixedThreadPool(10, Helpers.threadFactory));

        CountDownLatch latch = new CountDownLatch(1);

        // Synchronous emission
        Observable<Object> observable = Observable.create(emitter -> {
            for (String superHero : SUPER_HEROES) {
                Helpers.log("Emitting: " + superHero);
                emitter.onNext(superHero);
            }
            Helpers.log("Completing");
            emitter.onComplete();
        });

        Helpers.log("---------------- Subscribing");
        observable
                //刚开始发送消息跟接收消息都是一个线程，然后改变接收消息的线程，发送线程不变
                //当发送跟接收不在一个线程的时候，不再是一发一收，而是看各自的速度，当接收的速度跟不上发送的速度就会产生背压
                .observeOn(scheduler)
                .subscribe(
                        item -> Helpers.log("Received " + item),
                        error -> Helpers.log("Error"),
                        () -> {
                            Helpers.log("Complete");
                            latch.countDown();
                        });
        Helpers.log("---------------- Subscribed");

        latch.await();
    }

}
