package com.fans.common.utils;

/**
 * 通用脱敏工具类
 * 可用于：
 * 用户名
 * 手机号
 * 邮箱
 * 地址等
 */
public class DesensitizationUtil {

    private static final int SIZE = 6;
    private static final String SYMBOL = "*";

    public static void main(String[] args) {
        String name = commonDisplay("菜鸟裹裹" );
        String mobile = commonDisplay("18544444444" );
        String mail = commonDisplay("1234567890@163.com" );
        String address = commonDisplay("商都路十里铺100号" );

        System.out.println(name);
        System.out.println(mobile);
        System.out.println(mail);
        System.out.println(address);
    }

    /**
     * description: 通用脱敏方法
     *
     * @param value 要脱敏的字符
     * @return java.lang.String
     * @author k
     * @date 2020/10/25 8:40
     **/
    public static String commonDisplay(String value) {
        if (null == value || "".equals(value)) {
            return value;
        }
        int len = value.length();
        int pamaone = len / 2;
        int pamatwo = pamaone - 1;
        int pamathree = len % 2;
        StringBuilder stringBuilder = new StringBuilder();
        if (len <= 2) {
            if (pamathree == 1) {
                return SYMBOL;
            }
            stringBuilder.append(SYMBOL);
            stringBuilder.append(value.charAt(len - 1));
        } else {
            if (pamatwo <= 0) {
                stringBuilder.append(value.charAt(0));
                stringBuilder.append(SYMBOL);
                stringBuilder.append(value.charAt(len - 1));

            } else if (pamatwo >= SIZE / 2 && SIZE + 1 != len) {
                int campfire = (len - SIZE) / 2;
                stringBuilder.append(value.substring(0, campfire));
                for (int i = 0; i < SIZE; i++) {
                    stringBuilder.append(SYMBOL);
                }
                //noinspection ConstantConditions
                if ((pamathree == 0 && SIZE / 2 == 0) || (pamathree != 0 && SIZE % 2 != 0)) {
                    stringBuilder.append(value, len - campfire, len);
                } else {
                    stringBuilder.append(value, len - (campfire + 1), len);
                }
            } else {
                int pamafour = len - 2;
                stringBuilder.append(value.charAt(0));
                for (int i = 0; i < pamafour; i++) {
                    stringBuilder.append(SYMBOL);
                }
                stringBuilder.append(value.charAt(len - 1));
            }
        }
        return stringBuilder.toString();
    }

}
