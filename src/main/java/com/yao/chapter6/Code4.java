package com.yao.chapter6;

import com.yao.superheroes.Helpers;
import io.reactivex.Observable;

import java.util.Arrays;
import java.util.List;

public class Code4 {

    private static List<String> SUPER_HEROES = Arrays.asList(
            "Superman",
            "Batman",
            "Aquaman",
            "Asterix",
            "Captain America"
    );

    public static void main(String[] args) {
        Observable<Object> observable = Observable.create(emitter -> {
            Thread thread = new Thread(() -> {
                for (String superHero : SUPER_HEROES) {
                    Helpers.log("Emitting: " + superHero);
                    emitter.onNext(superHero);
                }
                Helpers.log("Completing");
                emitter.onComplete();
            });
            thread.start();
        });

        Helpers.log("---------------- Subscribing");
        observable
                // Blocking the emission thread
                .doOnNext(x -> Thread.sleep(30))
                .subscribe(
                        item -> Helpers.log("Received " + item),
                        error -> Helpers.log("Error"),
                        () -> Helpers.log("Complete"));
        Helpers.log("---------------- Subscribed");
    }

}
