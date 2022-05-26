package com.yao.chapter6;

import io.reactivex.Observable;

import java.util.Arrays;
import java.util.List;

public class Code1 {

    private static List<String> SUPER_HEROES = Arrays.asList(
            "Superman",
            "Batman",
            "Aquaman",
            "Asterix",
            "Captain America"
    );

    public static void main(String[] args) {

        Observable.create(emitter -> {
            for (String superHero : SUPER_HEROES) {
                System.out.println("Emitting: " + superHero);
                emitter.onNext(superHero);
            }
            System.out.println("Completing");
            emitter.onComplete();
        }).subscribe( s -> System.out.println(s),
                Throwable::printStackTrace,
                () -> System.out.println("completed")
                );


    }

}
