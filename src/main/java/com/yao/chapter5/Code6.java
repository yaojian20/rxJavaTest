package com.yao.chapter5;

import com.yao.superheroes.Character;
import io.reactivex.Maybe;
import io.reactivex.MaybeSource;
import io.reactivex.Single;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

public class Code6 extends AbstractSuperAPI{

    public static void main(String[] args) {
        new Code6().findByName("SuperGirl")
                .subscribe(
                        c -> System.out.println(c.getName() + " is a super " + (c.isVillain() ? "villain" : "hero")),
                        Throwable::printStackTrace,
                        () -> System.out.println("Nope"));

        new Code6().findByName("Clement")
                .subscribe(
                        c -> System.out.println(c.getName() + " is a super " + (c.isVillain() ? "villain" : "hero")),
                        Throwable::printStackTrace,
                        () -> System.out.println("No, Clement is not a " + "super hero (and not a super villain either despite the rumor)"));

        new Code6().findByNameOrError("Yoda")
                .subscribe(
                        c -> System.out.println(c.getName() + " is a super " + (c.isVillain() ? "villain" : "hero")),
                        Throwable::printStackTrace);

        new Code6().findByNameOrError("Clement")
                .subscribe(
                        c -> System.out.println(c.getName() + " is a super " + (c.isVillain() ? "villain" : "hero")),
                        t -> System.out.println("The lookup as failed, as expected, Clement is neither a super hero or super villain"));
    }

    @Override
    public Maybe<Character> findByName(String name) {
        //Maybe可以发送0或者1条数据，没有数据就会oncomplete
        return load().filter( character -> name.equalsIgnoreCase(character.getName())).firstElement();
    }

    @Override
    public Single<Character> findByNameOrError(String name) {
        //single只能发送1条数据，如果没有数据就会有异常
        return load().filter( character -> name.equalsIgnoreCase(character.getName())).firstOrError();
    }
}
