package com.yao.chapter2;

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

        Observable.fromIterable(SUPER_HEROES).map( name -> {
            if (name.endsWith("x")){
                throw new RuntimeException("What a terrible failure");
            }
            return name.toUpperCase();
        }).doOnNext(name -> System.out.println("doNext is :" + name))
                .doOnComplete(() -> System.out.println("Completion!"))
                .doOnError( error -> System.out.println("hit exception, message is :" + error.getMessage()))
                .subscribe( name -> System.out.println(name));

    }

}
