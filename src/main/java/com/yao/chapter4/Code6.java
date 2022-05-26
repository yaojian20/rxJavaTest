package com.yao.chapter4;

import com.yao.chapter2.HotStream;
import com.yao.superheroes.Helpers;
import com.yao.superheroes.SuperHeroesService;

import java.util.HashSet;

public class Code6 {

    public static void main(String[] args) {

        Helpers.heroes()
                .scan(new HashSet<>(),(set,character) -> {
                    set.addAll(character.getSuperpowers());
                    return set;
                }).doOnNext(System.out :: println)
                .count()
                .subscribe(count -> System.out.println("Heroes have " + count + " unique super powers"));


    }

}
