package com.fans.core.datasource.config;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * className: DynamicContextHolder
 *
 * @author k
 * @version 1.0
 * @description 多数据源上下文
 * @date 2018-08-04 02:09
 **/
public class DynamicContextHolder {
    /**
     * threadLocal中加入双向队列进行切换数据源
     */
    private static final ThreadLocal<Deque<String>> CONTEXT_HOLDER = ThreadLocal.withInitial(ArrayDeque::new);

    /**
     * 获取当前线程数据源
     *
     * @return 数据源名称
     */
    public static String peek() {
        return CONTEXT_HOLDER.get().peek();
    }

    /**
     * 设置当前线程数据源
     *
     * @param dataSource 数据源名称
     */
    public static void push(String dataSource) {
        CONTEXT_HOLDER.get().push(dataSource);
    }

    /**
     * 清除当前线程的数据源
     */
    public static void poll() {
        Deque<String> deque = CONTEXT_HOLDER.get();
        deque.poll();
        if (deque.isEmpty()) {
            CONTEXT_HOLDER.remove();
        }
    }
}
