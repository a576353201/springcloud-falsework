package com.fans.common.utils;

import java.util.Random;

/**
 * className: IdUtils
 *
 * @author k
 * @version 1.0
 * @description id生成器
 * @date 2018-12-20 14:14
 **/
public class IdUtils {

    public static String getTimestampId() {
        String idStr = "";
        long millis = System.currentTimeMillis();
        Random random = new Random();
        Integer end = random.nextInt(999);
        idStr = millis + String.format("%03d" , end);
        return idStr;
    }

    /**
     * 商品id生成
     */
    public static long genItemId() {
        //取当前时间的长整形值包含毫秒
        long millis = System.currentTimeMillis();
        //long millis = System.nanoTime();
        //加上两位随机数
        Random random = new Random();
        int end2 = random.nextInt(99);
        //如果不足两位前面补0
        String str = millis + String.format("%02d" , end2);
        return new Long(str);
    }
}
