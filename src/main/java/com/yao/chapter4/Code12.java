package com.yao.chapter4;

import com.yao.superheroes.Character;
import com.yao.superheroes.Helpers;
import com.yao.superheroes.SuperHeroesService;
import io.reactivex.Flowable;

public class Code12 {

    public static void main(String[] args) {
        Flowable<String> villains_superpowers =
                Helpers.villains().map(Character::getSuperpowers)
                        .flatMap(Flowable::fromIterable);
        Flowable<String> heroes_superpowers =
                Helpers.heroes().map(Character::getSuperpowers)
                        .flatMap(Flowable::fromIterable);

        Flowable.merge(villains_superpowers, heroes_superpowers)
                .distinct()
                .count()
                .subscribe(count -> System.out.println("heroes and villains have " + count + " powers"));


        villains_superpowers.mergeWith(heroes_superpowers)
                .distinct()
                .count()
                .subscribe(count -> System.out.println("heroes and villains have " + count + " powers"));

    }

}
