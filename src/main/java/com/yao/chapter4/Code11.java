package com.yao.chapter4;

import com.yao.superheroes.Helpers;
import com.yao.superheroes.SuperHeroesService;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.ext.web.client.HttpResponse;

public class Code11 {


    public static void main(String[] args) {
        SuperHeroesService.run();


        Single<JsonObject> request1 = SuperHeroesService.client()
                .get("/heroes")
                .rxSend()
                .map(HttpResponse::bodyAsJsonObject);



        request1
                // Transform the response to retrieve a stream of ids.
                .flatMapObservable(j -> Observable.fromIterable(j.fieldNames()))
                // 拿第一条数据并且转化成single
                .firstOrError()

                // Second request
                .flatMap(Code11::getHero)

                // Print the result
                .subscribe(json -> System.out.println(json.encodePrettily()));

    }

    private static Single<JsonObject> getHero(String s) {
        return SuperHeroesService.client().get("/heroes/" + s).rxSend().map(HttpResponse::bodyAsJsonObject);
    }
}
