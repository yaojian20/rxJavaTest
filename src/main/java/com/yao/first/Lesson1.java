package com.yao.first;

import io.reactivex.*;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class Lesson1 {

    static Disposable disposable;

    public static void main(String[] args) {

        //test();
        //testMap();
//        testZip();
//        testContact();
//        testFlatMap();
//
//        testContactMap();
//        testDistinct();
//
//        testFilter();

//        testBuffer();

//        testTimer();
//        testInterval();
//        testDoOnNext();
//        testSkip();
//        testTake();
//        testSingle();

//        testDebounce();
//        testDefer();
//        testLast();
//        testMerge();
//        testReduce();

        testScan();

    }

    public static void test(){
        StringBuffer message = new StringBuffer();



        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> observableEmitter) throws Exception {
                System.out.println("Observable emit 1");
                message.append("Observable emit 1\n");
                observableEmitter.onNext(1);
                System.out.println("Observable emit 2");
                message.append("Observable emit 2\n");
                observableEmitter.onNext(2);
                System.out.println("Observable emit 3");
                message.append("Observable emit 3\n");
                observableEmitter.onNext(3);

                //onComplete不影响发送事件，但是后面的发送事件不能接收
                observableEmitter.onComplete();

                System.out.println("Observable emit 4");
                message.append("Observable emit 4\n");
                observableEmitter.onNext(4);
            }
        }).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                System.out.println("disposable ：" + d.isDisposed());
                disposable = d;

            }

            @Override
            public void onNext(@NonNull Integer integer) {
                System.out.println("onNext value is :" + integer.intValue());
                message.append("onNext value is :" + integer.intValue());
                if (integer == 2){
                    //dispose方法能主动切断接收方接收
                    disposable.dispose();
                }
            }

            @Override
            public void onError(@NonNull Throwable throwable) {
                throwable.printStackTrace();
                System.out.println("error :" + throwable.getMessage());
            }

            @Override
            public void onComplete() {
                System.out.println("complete!");
            }
        });
    }

    /**
     * 测试map方法，调用map方法是将Observable 转化成另外一种Observable，此例为将Integer转化成string
     */
    public static void testMap(){

        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> observableEmitter) throws Exception {
                observableEmitter.onNext(1);
                observableEmitter.onNext(2);
                observableEmitter.onNext(3);
            }
        }).map(new Function<Integer, String>() {
            @Override
            public String apply(@NonNull Integer integer) throws Exception {
                return "转为String，数值是：" + integer.intValue();
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                System.out.println(s);
            }
        });

    }

    /**
     * zip方法是将两个发送事件组合起来，最终到接收者那边的数量跟发送少的一方的数量一致，而且组合的顺序跟发送的顺序保持一致
     */
    public static void testZip(){
        Observable a = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> observableEmitter) throws Exception {
                observableEmitter.onNext(1);
                observableEmitter.onNext(2);
                observableEmitter.onNext(3);
                observableEmitter.onNext(4);
                observableEmitter.onNext(5);
            }
        });

        Observable b = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> observableEmitter) throws Exception {
                observableEmitter.onNext("A");
                observableEmitter.onNext("B");
                observableEmitter.onNext("C");
            }
        });

        Observable.zip(a, b, new BiFunction<Integer, String , String >() {
            @NonNull
            @Override
            public String apply(@NonNull Integer integer, @NonNull String s) throws Exception {
                return "value is :" + s + integer.intValue();
            }
        }).subscribe(new Consumer<String >() {
            //Consumer是简化版的observer，只有接收方法
            @Override
            public void accept(String  s) throws Exception {
                System.out.println(s);
            }
        });

    }


    /**
     * contact方法能将两个发射器连接成一个,把第二发射器的元素发射给第一个
     */
    public static void testContact(){

        Observable.concat(Observable.just(1,2,3),Observable.just(4,5,6)).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                System.out.println(integer.intValue());
            }
        });

    }


    /**
     * 把一个发射器 Observable 通过某种方法转换为多个 Observables，然后再把这些分散的 Observables装进一个单一的发射器 Observable。
     * 解决了双重for循环问题
     * 输出顺序不固定
     */
    public static void testFlatMap(){
        Observable.fromArray(4,5,6).flatMap(new Function<Integer, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(@NonNull Integer integer) throws Exception {
                List<String> list = new ArrayList<>();
                for(int i =0; i < integer; i++){
                    list.add("我的value是" + integer.intValue());
                }
                return Observable.fromIterable(list).delay(2000,TimeUnit.MILLISECONDS);
            }
        }).subscribeOn(Schedulers.newThread()).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                System.out.println(s);
            }
        });

        try {
            TimeUnit.MILLISECONDS.sleep(20000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }



    /**
     * 把一个发射器 Observable 通过某种方法转换为多个 Observables，然后再把这些分散的 Observables装进一个单一的发射器 Observable。
     * 解决了双重for循环问题
     * 输出顺序固定
     */
    public static void testContactMap(){
        Observable.fromArray(4,5,6).concatMap(new Function<Integer, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(@NonNull Integer integer) throws Exception {
                List<String> list = new ArrayList<>();
                for(int i =0; i < integer; i++){
                    list.add("我的value是" + integer.intValue());
                }
                return Observable.fromIterable(list).delay(2000,TimeUnit.MILLISECONDS);
            }
        }).subscribeOn(Schedulers.newThread()).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                System.out.println(s);
            }
        });

        try {
            TimeUnit.MILLISECONDS.sleep(20000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }


    /**
     * 去重
     */
    public static void testDistinct(){

        Observable.just(1,2,3,1,13,5,5,6).distinct().subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                System.out.println(integer.intValue());
            }
        });

    }


    /**
     * 过滤
     */
    public static void testFilter(){

        Observable.just(1,3,11,67,34,100,99,22,72,45).filter( a -> a > 50).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                System.out.println(integer);
            }
        });

    }

    /**
     * buffer(count,skip)，将数据按照skip步长位置进行取值，每组取数据数量不超过count
     * 也就是从0开始取count个数据为第一组，从skip位置再取count个数据，以此类推。。。
     */
    public static void testBuffer(){

        Observable.just(1,2,3,4,5,6,7,8).buffer(2,4).subscribe(new Consumer<List<Integer>>() {
            @Override
            public void accept(List<Integer> integers) throws Exception {
                System.out.println(integers);
            }
        });

    }


    /**
     * 延时操作
     */
    public static void testTimer(){
        System.out.println(new Date());
        Observable.just(1,2,3).timer(1,TimeUnit.SECONDS).subscribeOn(Schedulers.io()).subscribe(new Consumer<Long>() {
            @Override
            public void accept(Long aLong) throws Exception {
                System.out.println(new Date());
            }
        });

        try {
            TimeUnit.SECONDS.sleep(8);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * 周期循环操作
     */
    public static void testInterval(){
        System.out.println(new Date());

        //第一次延迟3秒执行，后面的间隔2s执行一次
        Observable.interval(3,2,TimeUnit.SECONDS).subscribe(new Consumer<Long>() {
            @Override
            public void accept(Long aLong) throws Exception {
                System.out.println(new Date());
            }
        });

        try {
            TimeUnit.SECONDS.sleep(20);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * 在接收方接收到之前做一些操作
     */
    public static void testDoOnNext(){
        Observable.just(1,2,3,4,5,6).doOnNext(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                System.out.println("我要保存数值" + integer.intValue());
            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                System.out.println(integer.intValue());
            }
        });
    }

    /**
     * 跳过count数据后开始接收
     */
    public static void testSkip(){
        Observable.just(1,2,3,4,5,6).skip(2).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                System.out.println(integer.intValue());
            }
        });
    }


    /**
     * take()表示最多接收到count条数据
     */
    public static void testTake(){
        Observable.just(1,2,3,4,5,6).take(2).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                System.out.println(integer.intValue());
            }
        });
    }

    /**
     * Single只会接收发送一个参数
     */
    public static void testSingle(){
        Single.just(1).subscribe(new SingleObserver<Integer>() {
            @Override
            public void onSubscribe(@NonNull Disposable disposable) {
                System.out.println(disposable.isDisposed());
            }

            @Override
            public void onSuccess(@NonNull Integer integer) {
                System.out.println(integer);
            }

            @Override
            public void onError(@NonNull Throwable throwable) {
                System.out.println(throwable.getMessage());
            }
        });
    }

    /**
     * 去除发送率过快的选项
     * 比如说一个数据1发送之后，如果限定最少时间内又发送了一条，那么数据1不会被接收，如果超过最少时间后发送，那么数据1就会被接收
     */
    public static void testDebounce(){

        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> observableEmitter) throws Exception {
                observableEmitter.onNext(0);
                System.out.println("send value is :" + 0 + ",date is :" + new Date());
                Thread.sleep(6000);
                observableEmitter.onNext(1);
                System.out.println("send value is :" + 1 + ",date is :" + new Date());
                Thread.sleep(1000);
                observableEmitter.onNext(2);
                System.out.println("send value is :" + 2 + ",date is :" + new Date());
                Thread.sleep(7000);
                observableEmitter.onNext(3);
                System.out.println("send value is :" + 3 + ",date is :" + new Date());
                Thread.sleep(1000);
                observableEmitter.onNext(4);
                System.out.println("send value is :" + 4 + ",date is :" + new Date());
                Thread.sleep(8000);
                observableEmitter.onNext(5);
                System.out.println("send value is :" + 5 + ",date is :" + new Date());
                Thread.sleep(1000);
                observableEmitter.onNext(6);
                System.out.println("send value is :" + 6 + ",date is :" + new Date());
                Thread.sleep(5500);
            }
        }).debounce(5000,TimeUnit.MILLISECONDS).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                System.out.println(integer.intValue());
            }
        });



    }

    /**
     * defer方法也是创建observable的方法，但是他是相当于懒加载的方式，只有建立了订阅关系之后才会开始发送消息，组成一个新的observable,每次订阅都会产生一个新的，也就是会再发一次
     */
    public static void testDefer(){
        Observable observable = Observable.defer(new Callable<ObservableSource<Integer>>() {
            @Override
            public ObservableSource<Integer> call() throws Exception {
                return Observable.create(new ObservableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(@NonNull ObservableEmitter<Integer> observableEmitter) throws Exception {
                        System.out.println("发送数据：1");
                        observableEmitter.onNext(1);
                        System.out.println("发送数据：2");
                        observableEmitter.onNext(2);
                        System.out.println("发送数据：3");
                        observableEmitter.onNext(3);
                    }
                });
            }
        });
        System.out.println("开始建立订阅关系1");
        observable.subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                System.out.println(integer);
            }
        });

        System.out.println("开始建立订阅关系2");
        observable.subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                System.out.println(integer);
            }
        });
    }


    /**
     * 接收最后一条消息
     */
    public static void testLast(){
        Observable.just(1,2,3,4,5).last(6).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                System.out.println("last value is :" + integer.intValue());
            }
        });
    }

    /**
     * 将两个observable结合起来，跟contact的区别，就是不要等A发送完，B就可以进行发送
     */
    public static void testMerge(){
        Observable.merge(Observable.just(1,2,3,4),Observable.just(1,2,5,6,7)).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                System.out.println(integer.intValue());
            }
        });
    }

    /**
     * 每次用用同一个方法处理这些值，最后输出最终结果
     */
    public static void testReduce(){
        Observable.just(1,2,3,4).reduce(new BiFunction<Integer, Integer, Integer>() {
            @NonNull
            @Override
            public Integer apply(@NonNull Integer integer, @NonNull Integer integer2) throws Exception {
                return integer + integer2;
            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                System.out.println(integer.intValue());
            }
        });
    }

    /**
     * 每次用用同一个方法处理这些值,然后输出每一步的结果
     */
    public static void testScan(){
        Observable.just(1,2,3,4).scan(new BiFunction<Integer, Integer, Integer>() {
            @NonNull
            @Override
            public Integer apply(@NonNull Integer integer, @NonNull Integer integer2) throws Exception {
                return integer + integer2;
            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                System.out.println(integer.intValue());
            }
        });
    }

    public static void testWindow(){


    }



}
