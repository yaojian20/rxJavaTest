package com.yao.chapter5;

import com.yao.superheroes.Character;
import io.reactivex.Flowable;

public class Code4 extends AbstractSuperAPI{

    public static void main(String[] args) {
        new Code4().heroes()
                .count()
                .subscribe(i -> System.out.println(i + " heroes loaded"), Throwable::printStackTrace);

        new Code4().villains()
                .count()
                .subscribe(i -> System.out.println(i + " villains loaded"), Throwable::printStackTrace);
    }

    @Override
    public Flowable<Character> heroes() {
        return load().filter( character -> !character.isVillain());
    }

    @Override
    public Flowable<Character> villains() {
        return load().filter( character -> character.isVillain());
    }
}
