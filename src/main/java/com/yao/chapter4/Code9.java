package com.yao.chapter4;

import io.reactivex.Flowable;
import io.reactivex.Single;

public class Code9 {

    public static void main(String[] args) {
        String text = "Super heroes and super villains";
        Single.just(text)
                //将single转化成Flowable
                .flatMapPublisher(s -> Flowable.fromArray(s.split(" ")))
                .subscribe(s -> System.out.println(s));
    }

}
