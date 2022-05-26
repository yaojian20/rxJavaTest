package com.yao.chapter3;

import io.reactivex.Maybe;

public class Code4 {

    public static void main(String[] args) {

        //Maybe可以发送一条或者零条数据，关注success,complete,error

        Maybe.just("Superman").
                subscribe(s -> System.out.println(s),Throwable::printStackTrace,() -> System.out.println("Complete!"));

        Maybe.empty().
                subscribe(s -> System.out.println(s),Throwable::printStackTrace,() -> System.out.println("Complete!"));

    }


}
