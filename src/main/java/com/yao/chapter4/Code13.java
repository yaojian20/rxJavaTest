package com.yao.chapter4;

import com.yao.superheroes.Character;
import com.yao.superheroes.SuperHeroesService;
import io.reactivex.Single;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.ext.web.client.HttpResponse;
import io.vertx.reactivex.ext.web.codec.BodyCodec;

public class Code13 {

    public static void main(String[] args) {

        SuperHeroesService.run();

        Single<Character> random_heroes = SuperHeroesService.client()
                .get("/heroes/random")
                .as(BodyCodec.json(Character.class))
                .rxSend()
                .map(HttpResponse::body);

        Single<Character> random_villains = SuperHeroesService.client()
                .get("/villains/random")
                .as(BodyCodec.json(Character.class))
                .rxSend()
                .map(HttpResponse::body);

        //zipWith将两个发射消息进行整合
        random_heroes.zipWith(random_villains,Code13::fight)
                .subscribe(json -> System.out.println(json.encodePrettily()));


    }


    private static JsonObject fight(Character h, Character v) {
        String winner = h.getName();
        if (v.getSuperpowers().size() > h.getSuperpowers().size()) {
            winner = v.getName();
        } else if (v.getSuperpowers().size() == h.getSuperpowers().size()) {
            winner = "none";
        }
        return new JsonObject()
                .put("hero", h.getName())
                .put("villain", v.getName())
                .put("winner", winner);
    }

}
