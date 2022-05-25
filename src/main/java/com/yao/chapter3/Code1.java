package com.yao.chapter3;

import io.reactivex.Single;

public class Code1 {

    public static void main(String[] args) {
        Single.just("Super Man").doOnSuccess( s -> System.out.println("hello " + s)).subscribe();
    }

}
