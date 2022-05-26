package com.yao.chapter4;

import com.yao.superheroes.Character;
import com.yao.superheroes.Helpers;

public class Code8 {

    public static void main(String[] args) {
        Helpers.villains()
                .map(Character::getName)
                //将所有数据转化成一个list一次性发送，Single发送
                .toList()
                .subscribe(list -> System.out.println("List contains " + list.size() + " names"));
    }

}
