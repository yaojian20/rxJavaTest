package com.yao.chapter2;

import io.reactivex.Observable;

import java.util.Scanner;

public class Code6 {

    public static void main(String[] args) {
        Observable stream = Observable.create( subscribe -> {
            boolean done = false;
            Scanner scanner = new Scanner(System.in);
            while (!done){
                String message = scanner.next();
                if ("done".equals(message)){
                    done = true;
                    subscribe.onComplete();
                } else if ("error".equals(message)) {
                    subscribe.onError(new RuntimeException("this is a error!"));
                } else {
                    subscribe.onNext(message);
                }
            }
        });

        stream.subscribe( message -> System.out.println(message),
                error -> System.out.println("hit a error"),
                () -> System.out.println("Completion!")
        );
    }

}
