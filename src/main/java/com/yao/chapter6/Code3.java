package com.yao.chapter6;

import com.yao.superheroes.Helpers;
import io.reactivex.Observable;

import java.util.Arrays;
import java.util.List;

public class Code3 {

    private static List<String> SUPER_HEROES = Arrays.asList(
            "Superman",
            "Batman",
            "Aquaman",
            "Asterix",
            "Captain America"
    );

    public static void main(String[] args) {

        //发送跟接收消息在同一线程内
        Observable<Object> observable = Observable.create(emitter -> {
            new Thread(() -> {
                for (String superHero : SUPER_HEROES) {
                    Helpers.log("Emitting: " + superHero);
                    emitter.onNext(superHero);
                }
                Helpers.log("Completing");
                emitter.onComplete();
            }).start();
        });

        Helpers.log("---------------- Subscribing");
        observable.subscribe(
                item -> Helpers.log("Received " + item),
                error -> Helpers.log("Error"),
                () -> Helpers.log("Complete"));
        Helpers.log("---------------- Subscribed");
    }

}
