package com.yao.superheroes;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.core.Vertx;
import io.vertx.reactivex.core.buffer.Buffer;
import io.vertx.reactivex.core.file.FileSystem;
import io.vertx.reactivex.ext.web.client.HttpResponse;

import java.util.Map;
import java.util.stream.Collectors;

public class Helpers {

    public static FileSystem fs(){
        return Vertx.vertx().fileSystem();
    }


    public static Observable<String> villains_names() {

        //http请求路径
        return SuperHeroesService.client().get("/villains").rxSend().
                //返回数据转化成jsonObject->json转化成list
                map(HttpResponse::bodyAsJsonObject).map(jsonObject -> jsonObject.stream().map(Map.Entry :: getValue).collect(Collectors.toList()))
                //将single转化成Observable
                .flatMapObservable(Observable :: fromIterable).cast(String.class);

    }

    public static Observable<String> heroes_name(){
        return SuperHeroesService.client().get("/heroes").rxSend()
                .map(HttpResponse::bodyAsJsonObject).map(jsonObject -> jsonObject.stream().map(Map.Entry :: getValue).collect(Collectors.toList()))
                .flatMapObservable(Observable :: fromIterable).cast(String.class);
    }

    public static Flowable<Character> heroes(){
        return fs().rxReadFile("src/main/resources/characters.json")
                //将流转化成jsonArray
                .map(Buffer::toJsonArray).
                //转化成Flowable
                flatMapPublisher(Flowable :: fromIterable)
                .cast(JsonObject.class)
                //将json转化成character
                .map(jsonObject -> jsonObject.mapTo(Character.class))
                //过滤出英雄
                .filter(character -> !character.isVillain());
    }

    public static Flowable<Character> villains(){
        return fs().rxReadFile("src/main/resources/characters.json")
                .map(Buffer::toJsonArray)
                .flatMapPublisher(Flowable :: fromIterable)
                .cast(JsonObject.class)
                .map(jsonObject -> jsonObject.mapTo(Character.class))
                .filter( character -> character.isVillain());
    }


}
