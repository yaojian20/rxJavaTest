package com.yao.chapter2;

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
        Observable.fromIterable(SUPER_HEROES).doOnNext( name -> System.out.println("doNext is :" + name))
                .doOnComplete( ()->System.out.println("completion!"))
                .subscribe( name-> System.out.println(name));
    }

}
