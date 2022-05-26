package com.yao.chapter4;

import com.yao.superheroes.SuperHeroesService;
import io.reactivex.Single;
import io.vertx.reactivex.ext.web.client.HttpResponse;
import io.vertx.reactivex.ext.web.codec.BodyCodec;

public class Code15 {

    public static void main(String[] args) {

        SuperHeroesService.client()
                .get("/heroes/random")
                .as(BodyCodec.json(Character.class))
                .rxSend()
                .map(HttpResponse::body)
                .map(Character::getName)
                //报错返回值
                .onErrorResumeNext(Single.just("Clement, even if I'm not a superhero"))
                .subscribe(
                        name -> System.out.println("Retrieved: " + name),
                        err -> System.err.println("Oh no... something bad happened: " + err)
                );


    }

}
