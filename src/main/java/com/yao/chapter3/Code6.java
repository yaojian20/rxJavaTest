package com.yao.chapter3;

import io.reactivex.Completable;

public class Code6 {

    public static void main(String[] args) {

        //Completable不发送数据，只关注complete事件跟onError事件

        Completable.fromAction( () -> System.out.println("runnable is running"))
                .subscribe(() -> System.out.println("Complete"), error -> System.out.println("error"));
    }


}
