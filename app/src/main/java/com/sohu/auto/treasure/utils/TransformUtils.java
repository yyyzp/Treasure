package com.sohu.auto.treasure.utils;

import com.trello.rxlifecycle.LifecycleTransformer;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.util.concurrent.Callable;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func0;
import rx.schedulers.Schedulers;

/**
 * rxjava 转换器
 */
public class TransformUtils {

    public static <T> Observable.Transformer<T, T> defaultNetConfig(RxAppCompatActivity activity) {
        if (activity == null)
            return defaultNetConfig();
        LifecycleTransformer<T> transformer = activity.bindToLifecycle();
        return tObservable -> tObservable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .compose(transformer);
    }

    public static <T> Observable.Transformer<T, T> defaultNetConfig() {
        return tObservable -> tObservable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }

    public static <T> Observable.Transformer<T, T> defaultDBConfig() {
        return tObservable -> tObservable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }

    public static  <T> Observable<T> dbToObservable(Callable<T> callable){
        return Observable.defer(new Func0<Observable<T>>() {
            @Override
            public Observable<T> call() {
                try {
                    return Observable.just(callable.call());
                } catch (Exception e) {
                    return Observable.error(e);
                }
            }
        });
    }
}
