package com.yao.chapter6;

import com.yao.superheroes.Helpers;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import static java.util.concurrent.Executors.newFixedThreadPool;

public class Code5 {

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
                //改变发送消息的线程，接收消息的线程会跟他保持一致
                .subscribeOn(scheduler)
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
