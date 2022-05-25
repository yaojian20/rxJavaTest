package com.yao.chapter2;

import io.reactivex.Observable;

public class Code7 {

    public static void main(String[] args) {
        Observable stream = Observable.just("tom","jack","jerry");

        stream.subscribe(
                value -> System.out.println(value),
                error -> System.out.println("hit a error"),
                () -> System.out.println("Completion!")
        );

        //两个收到的消息是独立的，数据一致

        stream.subscribe(
                value -> System.out.println(value),
                error -> System.out.println("hit a error"),
                () -> System.out.println("Completion!")
        );

    }


}
