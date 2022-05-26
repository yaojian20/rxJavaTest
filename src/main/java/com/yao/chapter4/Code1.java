package com.yao.chapter4;

import com.yao.superheroes.Helpers;
import com.yao.superheroes.SuperHeroesService;

public class Code1 {

    public static void main(String[] args) {
        SuperHeroesService.run();

        Helpers.villains_names().filter( name -> name.contains("Queen"))
                .subscribe(name -> System.out.println(name));
    }

}
