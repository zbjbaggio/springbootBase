package com.springboot.base.util;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * 描述：线程池
 * Created by jay on 2017-11-13.
 */
public class ExecutorUtils {

    private static Executor executor = Executors.newCachedThreadPool();

    public static void async(Runnable runnable){
        executor.execute(runnable);
    }

}
