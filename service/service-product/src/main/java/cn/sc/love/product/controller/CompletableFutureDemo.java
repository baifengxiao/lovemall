package cn.sc.love.product.controller;


import java.util.concurrent.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @Author yupengtao
 * @Date 2023/6/4 15:23
 **/
public class CompletableFutureDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {


        //两种异步对象，其一：创建一个没有返回值的异步对象
//        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
//            System.out.println("没有返回结果");
//        });
//        System.out.println(future.get());

        //两种异步对象，其二：支持返回值
        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(new Supplier<Integer>() {

            @Override
            public Integer get() {
                int a = 1 / 0;
                return 666;
            }
        }).whenComplete(new BiConsumer<Integer, Throwable>() {

            //回调函数1，和异步对象使用相同线程池
            @Override
            public void accept(Integer integer, Throwable throwable) {

                System.out.println("whenComplete:" + integer);
                System.out.println("whenComplete:" + throwable);
            }
        }).exceptionally(new Function<Throwable, Integer>() {
            //回调函数4，只处理异常的回调
            @Override
            public Integer apply(Throwable throwable) {
                return null;
            }
        }).whenCompleteAsync(new BiConsumer<Integer, Throwable>() {
            //回调函数2，和异步对象使用不同线程池，能指定线程池
            //回调函数3，和异步对象使用不同线程池，能指定线程池,Executor，破例执行

            @Override
            public void accept(Integer integer, Throwable throwable) {

            }
        });
        System.out.println(future1.get());


        //串行与并行
        //三类，1，thenApply,依赖其他线程结果，并且返回自己结果
        //三类，2，thenAccept,依赖其他线程结果，但是不返回自己结果
        //三类，thenApply,依赖其他线程，不要结果，上个线程完成后立即执行
        //B，C异步
        //串行：1，同步串行代表任务1、任务2、任务3按时间先后顺序执行，并由相同的线程来执行。
        //串行：2，异步串行代表任务1、任务2、任务3按时间先后顺序执行，并由不同的线程来执行。
        //并行:3，没有依赖关系

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(50, 500, 30, TimeUnit.SECONDS, new ArrayBlockingQueue<>(10000));

        CompletableFuture<Object> futureA = CompletableFuture.supplyAsync(new Supplier<Object>() {

            @Override
            public Object get() {
                System.out.println("A");
                return "404";

            }
        }, threadPoolExecutor);
        CompletableFuture<Void> futureB = futureA.thenAcceptAsync(new Consumer<Object>() {

            @Override
            public void accept(Object o) {
//                try {
//                    Thread.sleep(1000);
//                }
//                catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
                System.out.println("B");
            }
        }, threadPoolExecutor);
        CompletableFuture<Void> futureC = futureA.thenAcceptAsync(new Consumer<Object>() {

            @Override
            public void accept(Object o) {
                System.out.println("C");
            }
        }, threadPoolExecutor);

    }
}
