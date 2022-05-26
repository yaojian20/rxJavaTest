package com.yao.chapter5;

import com.yao.superheroes.Character;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.core.Vertx;
import io.vertx.reactivex.core.file.FileSystem;

import java.util.ArrayList;
import java.util.Random;

public class AbstractSuperAPI implements SuperAPI{

    public Random random = new Random();

    protected Flowable<Character> load() {
        Vertx vertx = Vertx.vertx();
        FileSystem fileSystem = vertx.fileSystem();
        return fileSystem.rxReadFile("src/main/resources/characters.json")
                .map(buffer -> buffer.toJsonArray())
                .flatMapPublisher(array -> Flowable.fromIterable(array))
                .cast(JsonObject.class)
                .map(json -> json.mapTo(Character.class));
    }


    @Override
    public Single<Character> hero() {

        return null;
    }

    @Override
    public Single<Character> villain() {
        return null;
    }

    @Override
    public Flowable<Character> heroes() {
        return null;
    }

    @Override
    public Flowable<Character> villains() {
        return null;
    }

    @Override
    public Maybe<Character> findByName(String name) {
        return null;
    }

    @Override
    public Single<Character> findByNameOrError(String name) {
        return null;
    }
}
