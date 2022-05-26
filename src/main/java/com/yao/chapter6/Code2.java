package com.yao.chapter6;

import com.yao.superheroes.Helpers;
import com.yao.superheroes.SuperHeroesService;
import io.reactivex.Observable;

import java.util.Arrays;
import java.util.List;

public class Code2 {

    private static List<String> SUPER_HEROES = Arrays.asList(
            "Superman",
            "Batman",
            "Aquaman",
            "Asterix",
            "Captain America"
    );

    public static void main(String[] args) {
        Observable<Object> observable = Observable.create(emitter -> {
            for (String superHero : SUPER_HEROES) {
                Thread.sleep(30); // Introduce fake latency
                Helpers.log("Emitting: " + superHero);
                emitter.onNext(superHero);
            }
            Helpers.log("Completing");
            emitter.onComplete();
        });

        Helpers.log("---------------- Subscribing");
        observable.subscribe(
                item -> Helpers.log("Received " + item),
                error -> Helpers.log("Error"),
                () -> Helpers.log("Complete"));
        Helpers.log("---------------- Subscribed");

    }

}
