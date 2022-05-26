package com.yao.chapter3;

import io.reactivex.Single;

public class Code2 {

    public static void main(String[] args) {

        //Single只能发送一条数据，只关注success跟error

        Single.just("Super Man").subscribe((s,error) -> {
            if (error == null){
                System.out.println(s);
            } else {
                error.printStackTrace();
            }
        });
    }

}
