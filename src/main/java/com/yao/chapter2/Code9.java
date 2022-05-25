package com.yao.chapter2;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

public class Code9 {


    public static void main(String[] args) {
        Observable<Integer> stream = HotStream.create();
        Disposable a = stream.subscribe(value -> System.out.println("A received :" + value),
                error -> System.out.println("A hit a error"),
                () -> System.out.println(" A have completed!"));

        HotStream.nap();

        //只能收到订阅之后的消息
        Disposable b = stream.subscribe(value -> System.out.println("B received :" + value),
                error -> System.out.println("B hit a error"),
                () -> System.out.println(" B have completed!"));

        HotStream.nap(5);
        //a停止订阅
        a.dispose();

        HotStream.nap(3);
        //b停止订阅
        b.dispose();

    }


}
