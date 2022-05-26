package com.yao.chapter3;

import io.reactivex.Observable;

public class Code8 {

    public static void main(String[] args) {

        //从1发射到1000
        //注意：在同一线程的话是发送一条，接收一条
        Observable.range(1,1000).map(Item :: new).
                subscribe( item -> {
                    nap();
                    System.out.println("received : " + item.i);
                });

    }

    private static class Item{

        private final int i;

        public Item(int number) {
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
