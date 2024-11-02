package com.bot.qq_bot.util;

import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Component
public class ThreadUtil {
    // 全局线程池，使用 ThreadPoolExecutor 自定义线程池
    private static final ExecutorService GLOBAL_EXECUTOR;

    static {
        // 核心线程数
        //int corePoolSize = Runtime.getRuntime().availableProcessors();
        int corePoolSize = 1;
        // 最大线程数
        int maxPoolSize = 2;
        // 线程空闲存活时间
        long keepAliveTime = 60L;

        GLOBAL_EXECUTOR = new ThreadPoolExecutor(
                corePoolSize,           // 核心线程数
                maxPoolSize,            // 最大线程数
                keepAliveTime,          // 空闲线程存活时间
                TimeUnit.SECONDS,       // 时间单位
                new LinkedBlockingQueue<>(1000),  // 阻塞队列容量
                new ThreadFactory() {   // 自定义线程工厂
                    private int count = 0;
                    @Override
                    public Thread newThread(Runnable r) {
                        return new Thread(r, "global-thread-" + count++);
                    }
                },
                new ThreadPoolExecutor.AbortPolicy()  // 拒绝策略，当队列满时抛出异常
        );
    }

    // 私有构造方法，防止实例化
    private ThreadUtil() {}

    // 获取全局线程池
    public static ExecutorService getGlobalExecutor() {
        return GLOBAL_EXECUTOR;
    }

    // 关闭全局线程池
    public static void shutdown() {
        if (!GLOBAL_EXECUTOR.isShutdown()) {
            GLOBAL_EXECUTOR.shutdown();
        }
    }

    // 强制关闭所有线程
    public static void shutdownNow() {
        if (!GLOBAL_EXECUTOR.isShutdown()) {
            GLOBAL_EXECUTOR.shutdownNow();
        }
    }
}
