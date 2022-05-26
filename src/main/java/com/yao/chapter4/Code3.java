package com.yao.chapter4;

import com.yao.superheroes.Helpers;
import com.yao.superheroes.SuperHeroesService;
import io.reactivex.Observable;

public class Code3 {

    public static void main(String[] args) {
        SuperHeroesService.run();

        Helpers.heroes_name()
                .filter( name -> name.equals("Asterix"))
                .switchIfEmpty(Observable.just("sorry", "Asterix", "is","not" ,"a" ,"hero"))
                .subscribe(System.out :: println);
    }

}
