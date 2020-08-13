package com.timain.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

/**
 * 线程相关工具类.
 * @author yyf
 * @version 1.0
 * @date 2020/8/13 11:14
 */
public class Threads {
    
    private static final Logger logger = LoggerFactory.getLogger(Threads.class);

    /**
     * sleep等待,单位为毫秒
     * @param milliseconds
     */
    public static void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            return;
        }
    }

    /**
     * 停止线程池
     * 先使用shutdown, 停止接收新任务并尝试完成所有已存在任务.
     * 如果超时, 则调用shutdownNow, 取消在workQueue中Pending的任务,并中断所有阻塞函数.
     * 如果仍然超时，则强制退出.
     * 另对在shutdown时线程本身被调用中断做了处理.
     * @param pool
     */
    public static void shutDownAndAwaitTermination(ExecutorService pool) {
        if (null != pool && !pool.isShutdown()) {
            pool.shutdown();
            try {
                if (!pool.awaitTermination(120, TimeUnit.SECONDS)) {
                    pool.shutdown();
                    if (!pool.awaitTermination(120, TimeUnit.SECONDS)) {
                        logger.info("Pool did not terminate");
                    }
                }
            } catch (InterruptedException e) {
                pool.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }
    }

    /**
     * 打印线程异常信息
     * @param r
     * @param t
     */
    public static void printException(Runnable r, Throwable t) {
        if (null == t && r instanceof Future<?>) {
            try {
                Future<?> future = (Future<?>) r;
                if (future.isDone()) {
                    future.get();
                }
            } catch (CancellationException e) {
                t = e;
            } catch (ExecutionException e) {
                t = e.getCause();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        if (null != t) {
            logger.error(t.getMessage(), t);
        }
    }
}
