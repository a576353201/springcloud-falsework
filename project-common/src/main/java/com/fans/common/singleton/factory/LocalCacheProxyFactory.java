package com.fans.common.singleton.factory;

import com.fans.common.singleton.proxy.LocalCacheProxy;

/**
 * className: LocalCacheProxyFactory
 *
 * @author k
 * @version 1.0
 * @description 本地缓存工厂
 * @date 2019-04-01 10:56
 **/
public class LocalCacheProxyFactory {

    private static volatile LocalCacheProxy localCacheProxy;

    public static LocalCacheProxy getLocalCacheProxy() {
        if (localCacheProxy == null) {
            synchronized (LocalCacheProxyFactory.class) {
                if (localCacheProxy == null) {
                    localCacheProxy = LocalCacheProxy.getInstance();
                }
            }
        }
        return localCacheProxy;
    }

    /**
     * 根据需求增加不同的单例缓存
     */
    private static Object getLocalCacheProxyGoOn() {
        return new Object();
    }
}
