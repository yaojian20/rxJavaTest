package com.yao.chapter5;

import com.yao.superheroes.Character;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.core.Vertx;
import io.vertx.reactivex.core.buffer.Buffer;

public class Code3 {

    public static void main(String[] args) {
        load().subscribe(s -> System.out.println(s), Throwable::printStackTrace);
    }

    static Flowable<Character> load() {
        Vertx vertx = Vertx.vertx();
        return vertx.fileSystem().rxReadFile("src/main/resources/characters.json")
                .map(Buffer::toJsonArray)
                .flatMapPublisher(jsonArray -> Flowable.fromIterable(jsonArray))
                .cast(JsonObject.class)
                .map(json -> json.mapTo(Character.class));
    }

}
