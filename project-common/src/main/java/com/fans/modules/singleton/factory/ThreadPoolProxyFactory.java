package com.fans.modules.singleton.factory;

import com.fans.modules.singleton.proxy.ThreadPoolProxy;

/**
 * className: ThreadPoolProxyFactory
 *
 * @author k
 * @version 1.0
 * @description 线程池工厂
 * @date 2019-04-01 10:56
 **/
public class ThreadPoolProxyFactory {

    /**
     * 默认单例线程池 volatile防止重排序带来的线程安全问题
     */
    private static volatile ThreadPoolProxy threadPoolProxy;


    public static ThreadPoolProxy getThreadPoolProxy() {
        if (threadPoolProxy == null) {
            synchronized (ThreadPoolProxyFactory.class) {
                if (threadPoolProxy == null) {
                    threadPoolProxy = ThreadPoolProxy.getInstance();
                }
            }
        }
        return threadPoolProxy;
    }

    /**
     * 根据需求增加不同的单例线程池
     */
    private static Object getThreadPoolProxyGoOn() {
        return new Object();
    }
}
