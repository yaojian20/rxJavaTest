package com.yao.chapter3;

import com.yao.chapter2.HotStream;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class Code9 {

    public static void main(String[] args) {
        // Create an observable emitting all numbers between 1 and 999_999_999
        Observable.range(1, 999_9_9)
                .map(Item::new)
                // 在主线程上发射数据
                // 下一终端用户在io线程接收消息
                //observeOn可以切换线程，改变后续链式操作所在的线程，可以多次调用，每次调用都切换一次线程。
                //在不同线程发射跟接收的话，不会一条发一条收，而是发射方一直发送，接收方一直接收
                //如果接收方的速度跟不上发射方的话，就会出现背压
                .observeOn(Schedulers.io())
                .subscribe(
                        item -> {
                            nap();
                            System.out.println("Received : " + item.i);
                        }
                );

        // Wait for 20 seconds. Without this the process will terminate immediately.
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
