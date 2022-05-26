package com.yao.chapter4;

import io.reactivex.Flowable;

public class Code5 {

    public static void main(String[] args) {

        /**
         * scan跟reduce都是用一种方法处理发送的数据，
         * 但是scan会接收到每一步的数据，而reduce只接收最终的数据
         */

        Flowable.range(0,10)
                .scan((last, item) -> last + item)
                .subscribe(value -> System.out.println("scan value received : " + value));

        Flowable.range(0,10)
                .reduce((last,item) -> last+item)
                .subscribe(value -> System.out.println("reduce value received : " + value));

    }


}
