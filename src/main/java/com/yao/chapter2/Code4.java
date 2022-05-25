package com.yao.chapter2;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;

public class Code4 {

    public static void main(String[] args) {
        Observable.create(observableEmitter -> {
            observableEmitter.onNext(1);
            observableEmitter.onNext(2);
            observableEmitter.onNext(3);
            observableEmitter.onComplete();
        }).subscribe( value -> System.out.println(value),
                throwable -> System.out.println("error"),
                () -> System.out.println("Completion!")
                );
    }


}
