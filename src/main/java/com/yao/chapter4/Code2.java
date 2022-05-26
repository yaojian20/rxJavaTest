package com.yao.chapter4;

import com.yao.superheroes.Helpers;
import com.yao.superheroes.SuperHeroesService;

public class Code2 {

    public static void main(String[] args) {
        SuperHeroesService.run();

        Helpers.villains_names().skip(20).take(10)
                .subscribe( name -> System.out.println(name));
    }

}
