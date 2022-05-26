package com.yao.chapter5;

import io.reactivex.Single;
import io.vertx.core.json.JsonArray;
import io.vertx.reactivex.core.Vertx;
import io.vertx.reactivex.core.buffer.Buffer;

public class Code2 {

    public static void main(String[] args) {
        load().map(JsonArray::encodePrettily)
                .subscribe(s -> System.out.println(s), Throwable::printStackTrace);
    }

    static Single<JsonArray> load(){
        Vertx vertx = Vertx.vertx();
        return vertx.fileSystem().rxReadFile("src/main/resources/characters.json")
                .map(Buffer::toJsonArray);
    }

}
