package com.yao.chapter1;

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
        Observable<String> stream = Observable.fromIterable(SUPER_HEROES);

        stream.map( name -> name.toUpperCase())
                .filter(name -> name.startsWith("A"))
                .subscribe( name -> System.out.println(name));

    }

}
