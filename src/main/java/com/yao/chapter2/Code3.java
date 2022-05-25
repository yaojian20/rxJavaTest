package com.yao.chapter2;

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


        Observable.just(1,2,3).subscribe(
            name -> System.out.println(name),
                Throwable::printStackTrace,
                () -> System.out.println("Completion!")

        );

    }



}
