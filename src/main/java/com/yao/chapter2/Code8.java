package com.yao.chapter2;

import io.reactivex.Observable;

public class Code8 {

    public static void main(String[] args) {
        Observable<Integer> stream = HotStream.create();
        stream.subscribe(value -> System.out.println("A received :" + value),
                error -> System.out.println("A hit a error"),
                () -> System.out.println(" A have completed!"));

        HotStream.nap();

        //只能收到订阅之后的消息
        stream.subscribe(value -> System.out.println("B received :" + value),
                error -> System.out.println("B hit a error"),
                () -> System.out.println(" B have completed!"));
    }


}
