package com.yao.chapter3;

import com.yao.chapter2.HotStream;
import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;

public class Code10 {

    public static void main(String[] args) {
        //Flowable是为了解决背压问题,消息堆积到一定程度，会消费掉这些消息，再进行发射消息
        /**
         * 不会出现背压的情况,没有必要使用Flowable,会影响效率
         * 1、上下游运行在同一个线程中，
         * 2、上下游工作在不同的线程中，但是下游处理数据的速度不慢于上游发射数据的速度，
         * 3、上下游工作在不同的线程中，但是数据流中只有一条数据
         */
        Flowable.range(1,999_999_999).map(Item::new).observeOn(Schedulers.io()).subscribe(value -> {
            nap();
            System.out.println("received value :" + value.i);
        });

        HotStream.nap(20);
    }

    private static class Item {
        private final int i;

        Item(int number) {
            System.out.println("Constructing item using " + number);
            this.i = number;
        }
    }

    private static void nap() {
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            // Ignore me.
        }
    }

}
