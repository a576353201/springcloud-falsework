package com.fans.utils;

import com.google.common.collect.Maps;

import java.util.Arrays;
import java.util.Map;

/**
 * className: DataStatisticsUtils
 *
 * @author k
 * @version 1.0
 * @description 数据统计常用算法
 * @date 2018-12-20 14:14
 **/
public class DataStatisticsUtils {
    /**
     * 求和
     *
     * @param arr 数组
     * @return 求和结果
     */
    public static double getSum(double[] arr) {
        double sum = 0;
        for (double num : arr) {
            sum += num;
        }
        return sum;
    }

    /**
     * 求均值
     *
     * @param arr 数组
     * @return 结果
     */
    public static double getMean(double[] arr) {
        return getSum(arr) / arr.length;
    }

    /**
     * 求众数
     *
     * @param arr 数组
     * @return 结果
     */
    public static double getMode(double[] arr) {
        Map<Double, Integer> map = Maps.newHashMap();
        for (double v : arr) {
            if (map.containsKey(v)) {
                map.put(v, map.get(v) + 1);
            } else {
                map.put(v, 1);
            }
        }
        int maxCount = 0;
        double mode = -1;
        for (double num : map.keySet()) {
            int count = map.get(num);
            if (count > maxCount) {
                maxCount = count;
                mode = num;
            }
        }
        return mode;
    }

    /**
     * 求中位数
     *
     * @param arr 数组
     * @return 结果
     */
    public static double getMedian(double[] arr) {
        double[] tempArr = Arrays.copyOf(arr, arr.length);
        Arrays.sort(tempArr);
        if (tempArr.length % 2 == 0) {
            return (tempArr[tempArr.length >> 1] + tempArr[(tempArr.length >> 1) - 1]) / 2;
        } else {
            return tempArr[(tempArr.length >> 1)];
        }
    }


    /**
     * 求中列数
     *
     * @param arr 数组
     * @return 结果
     */
    public static double getMidrange(double[] arr) {
        double max = arr[0], min = arr[0];
        for (double v : arr) {
            if (v > max) {
                max = v;
            }
            if (v < min) {
                min = v;
            }
        }
        return (min + max) / 2;
    }

    /**
     * 求四分位数
     *
     * @param arr 数组
     * @return 存放三个四分位数的数组
     */
    public static double[] getQuartiles(double[] arr) {
        double[] tempArr = Arrays.copyOf(arr, arr.length);
        Arrays.sort(tempArr);
        double[] quartiles = new double[3];
        // 第二四分位数（中位数）
        quartiles[1] = getMedian(tempArr);
        // 求另外两个四分位数
        if (tempArr.length % 2 == 0) {
            quartiles[0] = getMedian(Arrays.copyOfRange(tempArr, 0, tempArr.length / 2));
            quartiles[2] = getMedian(Arrays.copyOfRange(tempArr, tempArr.length / 2, tempArr.length));
        } else {
            quartiles[0] = getMedian(Arrays.copyOfRange(tempArr, 0, tempArr.length / 2));
            quartiles[2] = getMedian(Arrays.copyOfRange(tempArr, tempArr.length / 2 + 1, tempArr.length));
        }
        return quartiles;
    }

    /**
     * 求极差
     *
     * @param arr 数组
     * @return 结果
     */
    public static double getRange(double[] arr) {
        double max = arr[0], min = arr[0];
        for (double v : arr) {
            if (v > max) {
                max = v;
            }
            if (v < min) {
                min = v;
            }
        }
        return max - min;
    }

    /**
     * 求四分位数极差
     *
     * @param arr 数组
     * @return 结果
     */
    public static double getQuartilesRange(double[] arr) {
        return getRange(getQuartiles(arr));
    }

    /**
     * 求截断均值
     *
     * @param arr 求值数组
     * @param p   截断量p，例如p的值为20，则截断20%（高10%，低10%）
     * @return 结果
     */
    public static double getTrimmedMean(double[] arr, int p) {
        int tmp = arr.length * p / 100;
        double[] tempArr = Arrays.copyOfRange(arr, tmp, arr.length + 1 - tmp);
        return getMean(tempArr);
    }

    /**
     * 求方差
     *
     * @param arr 数组
     * @return 结果
     */
    public static double getVariance(double[] arr) {
        double variance = 0;
        double sum = 0, sum2 = 0;
        for (double v : arr) {
            sum += v;
            sum2 += v * v;
        }
        variance = sum2 / arr.length - (sum / arr.length) * (sum / arr.length);
        return variance;
    }

    /**
     * 求绝对平均偏差(AAD)
     *
     * @param arr 数组
     * @return 结果
     */
    public static double getAbsoluteAverageDeviation(double[] arr) {
        double sum = 0;
        double mean = getMean(arr);
        for (double v : arr) {
            sum += Math.abs(v - mean);
        }
        return sum / arr.length;
    }

    /**
     * 求中位数绝对偏差(MAD)
     *
     * @param arr 数组
     * @return 结果
     */
    public static double getMedianAbsoluteDeviation(double[] arr) {
        double[] tempArr = new double[arr.length];
        double median = getMedian(arr);
        for (int i = 0; i < arr.length; i++) {
            tempArr[i] = Math.abs(arr[i] - median);
        }
        return getMedian(tempArr);
    }

    /**
     * 求标准差
     *
     * @param arr 数组
     * @return 结果
     */
    public static double getStandardDevition(double[] arr) {
        double sum = 0;
        double mean = getMean(arr);
        for (double v : arr) {
            sum += Math.sqrt((v - mean) * (v - mean));
        }
        return (sum / (arr.length - 1));
    }
}
