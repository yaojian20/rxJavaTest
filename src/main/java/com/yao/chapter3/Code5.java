package com.yao.chapter3;

import com.yao.superheroes.SuperHeroesService;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.ext.web.client.HttpResponse;

public class Code5 {

    public static void main(String[] args) {

        SuperHeroesService.run();

        String name1 = "Yoda";
        String name2 = "clement";

        SuperHeroesService.client().get("/heroes").rxSend()
                .map(HttpResponse::bodyAsJsonObject).filter(jsonObject -> contains(name1, jsonObject))
                .subscribe(jsonObject -> System.out.println(name1 + "is a hero"),
                        Throwable::printStackTrace,
                        () -> System.out.println(name1 + " is not a hero!"));

        SuperHeroesService.client().get("/heroes").rxSend()
                .map(HttpResponse::bodyAsJsonObject).filter(jsonObject -> contains(name2, jsonObject))
                .subscribe(jsonObject -> System.out.println(name2 + "is a hero"),
                        Throwable::printStackTrace,
                        () -> System.out.println(name2 + " is not a hero!"));

    }


    private static boolean contains(String name, JsonObject json) {
        return json.stream().anyMatch(e -> e.getValue().toString().equalsIgnoreCase(name));
    }


}
