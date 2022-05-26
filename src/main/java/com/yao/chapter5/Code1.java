package com.yao.chapter5;

import io.reactivex.Single;
import io.vertx.reactivex.core.Vertx;
import io.vertx.reactivex.core.buffer.Buffer;

public class Code1 {

    public static void main(String[] args) {

        load().subscribe( s -> System.out.println(s), Throwable::printStackTrace);

    }

    static Single<String> load(){
        Vertx vertx = Vertx.vertx();
        return vertx.fileSystem().rxReadFile("src/main/resources/characters.json")
                .map(Buffer::toString);
    }

}
