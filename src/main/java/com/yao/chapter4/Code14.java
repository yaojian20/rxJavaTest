package com.yao.chapter4;

import com.yao.superheroes.Character;
import com.yao.superheroes.SuperHeroesService;
import io.vertx.reactivex.ext.web.client.HttpResponse;
import io.vertx.reactivex.ext.web.codec.BodyCodec;

public class Code14 {

    public static void main(String[] args) {
        SuperHeroesService.run();

        SuperHeroesService.client()
                .get("/heroes/random")
                //返回体为character
                .as(BodyCodec.json(Character.class))
                .rxSend()
                .map(HttpResponse::body)
                .map(Character::getName)
                .onErrorReturnItem("Clement, even if I'm not a superhero")
                .subscribe(name -> System.out.println("random hero name is : " + name) , error -> error.printStackTrace());
    }

}
