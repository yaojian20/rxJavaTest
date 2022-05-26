package com.yao.chapter5;

import com.yao.superheroes.Character;
import io.reactivex.Single;

import java.util.Collections;

public class Code5 extends AbstractSuperAPI{

    public static void main(String[] args) {

        new Code5().hero()
                .subscribe( s -> System.out.println(s), Throwable::printStackTrace);

        new Code5().villain()
                .subscribe( s -> System.out.println(s), Throwable::printStackTrace);

    }


    @Override
    public Single<Character> hero() {
        //先转化成singleList
        return load().
                filter( character -> !character.isVillain())
                .toList()
                .map(list -> {
                    //随机取下标元素
                    int index = random.nextInt(list.size());
                    return list.get(index);
                });
    }

    @Override
    public Single<Character> villain() {
        return load()
                .filter( character -> character.isVillain())
                .toList()
                .map(list -> {
                    //打乱list的顺序
                    Collections.shuffle(list);
                    return list.get(0);
                });
    }
}
