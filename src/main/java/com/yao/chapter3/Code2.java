package com.yao.chapter3;

import io.reactivex.Single;

public class Code2 {

    public static void main(String[] args) {
        Single.just("Super Man").subscribe((s,error) -> {
            if (error == null){
                System.out.println(s);
            } else {
                error.printStackTrace();
            }
        });
    }

}
