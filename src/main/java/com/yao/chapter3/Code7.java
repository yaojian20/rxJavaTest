package com.yao.chapter3;

import io.vertx.reactivex.core.Vertx;
import io.vertx.reactivex.core.buffer.Buffer;

public class Code7 {

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();

        //写数据到文件中并且订阅
        vertx.fileSystem().rxWriteFile("hello.txt", Buffer.buffer("hello world!"))
                .subscribe(() -> System.out.println("complete!"), Throwable::printStackTrace)
        ;
    }

}
