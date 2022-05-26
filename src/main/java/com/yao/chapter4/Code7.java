package com.yao.chapter4;

import com.yao.superheroes.Helpers;

import java.util.HashSet;

public class Code7 {

    public static void main(String[] args) {
        Helpers.villains()
                .reduce(new HashSet<String>(),(set,character) -> {
                    set.addAll(character.getSuperpowers());
                    return set;
                }).doOnSuccess(System.out::println)
                .subscribe( set -> System.out.println("villains have " + set.size() + " unique powers"));
    }

}
