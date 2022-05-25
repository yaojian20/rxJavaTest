package com.yao.chapter2;

import io.reactivex.Observable;

public class Code5 {

    public static void main(String[] args) {
        Observable.create( subscribe -> {
            subscribe.onNext(1);
            subscribe.onNext(2);
            subscribe.onError(new RuntimeException("there throw a runtime exception"));
            subscribe.onNext(3);
            subscribe.onComplete();
        }).subscribe(value -> System.out.println(value),
                error -> System.out.println("hit error"),
                () -> System.out.println("Completion!"));
    }


}
