package com.joy.app.activity.sample;

import android.util.SparseArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Daisw on 16/8/15.
 */

public class RxJavaSample {

    public static void main(String[] args) {

        convert3();
    }

    public static void convert1() {

        String[] params = new String[]{"params1", "params2", "params3"};
        Observable.just(params)
                .subscribeOn(Schedulers.io())
                .map(p -> {
                    System.out.println("map: " + Thread.currentThread());
                    return getBeans(p);
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(beens -> System.out.println("onNext: " + beens.size() + " " + Thread.currentThread()),
                        error -> System.out.println("onError: " + error.getMessage() + " " + Thread.currentThread()),
                        () -> System.out.println("onCompleted: " + " " + Thread.currentThread()));
    }

    private static final int KEY1 = 1;
    private static final int KEY2 = 2;
    private static final int KEY3 = 3;
    private static final int KEY4 = 4;

    public static void convert2() {

        SparseArray<List<String>> p1 = new SparseArray<>(1);
        SparseArray<List<String>> p2 = new SparseArray<>(1);
        SparseArray<List<String>> p3 = new SparseArray<>(1);
        SparseArray<List<String>> p4 = new SparseArray<>(1);
        p1.append(KEY1, Collections.singletonList("a1"));
        p2.append(KEY2, Arrays.asList("b1", "b2"));
        p3.append(KEY3, Arrays.asList("c1", "c2", "c3"));
        p4.append(KEY4, Collections.emptyList());

        Observable.just(p1, p2, p3, p4)
                .subscribeOn(Schedulers.io())
                .map(RxJavaSample::getBean)
                .filter(bean -> bean != null)
                .take(1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Bean>() {

                    @Override
                    public void onNext(Bean bean) {

                        System.out.println("onNext: " + bean.toString());
                    }

                    @Override
                    public void onError(Throwable e) {

                        System.out.println("onError: " + e.getMessage());
                    }

                    @Override
                    public void onCompleted() {

                        System.out.println("onCompleted");
                    }
                });
    }

    public static void convert3() {

        Observable.<Bean>create(new Observable.OnSubscribe<Bean>() {

            @Override
            public void call(Subscriber<? super Bean> subscriber) {

                System.out.println("convert3 # create");
                subscriber.onNext(new Bean("key", "value"));
                subscriber.onCompleted();
            }
        }).map(new Func1<Bean, String>() {

            @Override
            public String call(Bean bean) {

                System.out.println("convert3 # map");
                return bean.toString();
            }
        }).subscribe(new Subscriber<String>() {

            @Override
            public void onStart() {

                System.out.println("convert3 # onStart");
            }

            @Override
            public void onNext(String s) {

                System.out.println("convert3 # " + s);
            }

            @Override
            public void onError(Throwable e) {

                System.out.println("convert3 # onError");
            }

            @Override
            public void onCompleted() {

                System.out.println("convert3 # onCompleted");
            }
        });
    }

    private void xxx() {

        Schedulers.newThread().createWorker().schedule(() -> {
        });
    }

    private static void sleep(long millis) {

        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据一些参数,获取数据
     *
     * @param params 获取数据时所需的参数
     * @return 所需要的数据
     */
    private static List<Bean> getBeans(String[] params) {

        sleep(1000);// 模拟耗时操作

        List<Bean> beans = new ArrayList<>(params.length);
        int i = 1;
        for (String str : params) {
            beans.add(new Bean("params" + i, str));
            i++;
        }
        return beans;
    }

    private static Bean getBean(SparseArray<List<String>> paramsArray) {

        sleep(1000);// 模拟耗时操作

        switch (paramsArray.keyAt(0)) {

            case KEY1:

                String k1p1 = paramsArray.get(KEY1).get(0);
                return new Bean("key1", k1p1);
            case KEY2:

                String k2p1 = paramsArray.get(KEY2).get(0);
                String k2p2 = paramsArray.get(KEY2).get(1);
                return new Bean("key2", k2p1 + " " + k2p2);
            case KEY3:

                String k3p1 = paramsArray.get(KEY3).get(0);
                String k3p2 = paramsArray.get(KEY3).get(1);
                String k3p3 = paramsArray.get(KEY3).get(2);
                return new Bean("key3", k3p1 + " " + k3p2 + " " + k3p3);
            case KEY4:

                return new Bean("key4", "key4-value");
            default:

                return null;
        }
    }

    private static class Bean {

        String key;
        String value;

        Bean(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "Bean{" +
                    "key='" + key + '\'' +
                    ", value='" + value + '\'' +
                    '}';
        }
    }
}
