package com.yao.superheroes;

import io.reactivex.Completable;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;

import java.util.Map;
import java.util.Random;

public class SuperHeroesService {


    //详细
    private final boolean verbose;
    private Random random = new Random();
    //恶棍，坏人
    private Map<Integer, Character> villains;
    //英雄
    private Map<Integer, Character> heroes;


    public SuperHeroesService(boolean verbose) {
        this.verbose = verbose;
    }

    public Completable start() {
        Vertx vertx = Vertx.vertx();
        //创建一个路由对象
        Router router = Router.router(vertx);
        return null;
    }

}
