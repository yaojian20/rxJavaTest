package com.yao.chapter4;

import com.yao.superheroes.Helpers;
import com.yao.superheroes.SuperHeroesService;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.core.buffer.Buffer;
import io.vertx.reactivex.ext.web.client.HttpResponse;

public class Code10 {

    public static void main(String[] args) {

        SuperHeroesService.run();

        //拿到所有英雄的map并转化成JsonObject
        Single<JsonObject> request1 = SuperHeroesService.client()
                .get("/heroes").rxSend()
                .map(HttpResponse::bodyAsJsonObject);

        //拿到所有角色id
        request1.flatMapObservable( jsonObject -> Observable.fromIterable(jsonObject.fieldNames()))
                //取第一个角色id
                .take(1)
                //根据id去英雄中寻找
                .flatMapSingle( Code10::getHero)
                .subscribe(json -> System.out.println(json.encodePrettily()));

    }

    private static Single<JsonObject> getHero(String s) {
        return SuperHeroesService.client().get("/heroes/" + s).rxSend().map(HttpResponse::bodyAsJsonObject);
    }

}
