package com.yao.superheroes;

import io.reactivex.Completable;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.WebClientOptions;
import io.vertx.reactivex.core.Vertx;
import io.vertx.reactivex.ext.web.Router;
import io.vertx.reactivex.ext.web.RoutingContext;
import io.vertx.reactivex.ext.web.client.WebClient;

import java.util.*;
import java.util.stream.Collectors;

public class SuperHeroesService {

    private final static Vertx vertx = Vertx.vertx();

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
        //绑定路由地址及处理方法
        router.route("/heroes").handler(this::getAllHeroes);

        //读取文件
        return vertx.fileSystem().rxReadFile("src/main/resources/characters.json")
                //将json文件里的数据转化成character
                .map( buffer -> buffer.toJsonArray().stream().map( jsonObject -> new Character((JsonObject) jsonObject)).collect(Collectors.toList()))
                .doOnSuccess( list -> {
                    //过滤恶棍，并按id放入map中
                    this.villains = list.stream().filter(Character::isVillain).collect(
                            HashMap::new,(map,character) -> map.put(character.getId(),character),HashMap::putAll);
                    //过滤英雄，并按id放入map中
                    this.heroes = list.stream().filter(e -> ! e.isVillain()).collect(
                            HashMap::new, (map, character) -> map.put(character.getId(), character), HashMap::putAll);
                })
                //转成成一个http请求，端口号8080
                .flatMap(x -> vertx.createHttpServer()
                        .requestHandler(router::accept)
                        .rxListen(8080))
                .toCompletable();
    }

    public void getAllHeroes(RoutingContext routingContext){
            routingContext.response().end(villains.values().stream()
                    .collect(JsonObject::new,
                            (json, superStuff) -> json.put(Integer.toString(superStuff.getId()), superStuff.getName()),
                            JsonObject::mergeIn)
                    .encodePrettily());
    }

    public static void run() {
        new SuperHeroesService(true).start().blockingAwait();
    }

    public static WebClient client() {
        return WebClient.create(vertx,
                new WebClientOptions().setDefaultPort(8080).setDefaultHost("localhost")
        );
    }

}
