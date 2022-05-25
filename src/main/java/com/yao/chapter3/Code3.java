package com.yao.chapter3;

import com.yao.superheroes.SuperHeroesService;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.ext.web.client.HttpResponse;

public class Code3 {

    public static void main(String[] args) {
        SuperHeroesService.run();

        SuperHeroesService.client().get("/heroes").rxSend()
                .map(HttpResponse::bodyAsJsonObject)
                .map(JsonObject::size)
                .subscribe(length -> System.out.println("Number of heroes: " + length));
    }


}
