package com.fans.common.utils;

/**
 * className: Constant
 *
 * @author k
 * @version 1.0
 * @description 常量配置
 * @date 2020-10-25 14:47:34
 **/
public class Constant {
    /**
     * 超级管理员ID
     */
    public final static int SUPER_ADMIN = 1;
    /**
     * 当前页码
     */
    public final static String PAGE = "page";
    /**
     * 每页显示记录数
     */
    public final static String LIMIT = "limit";
    /**
     * 排序字段
     */
    public final static String ORDER_FIELD = "sidx";
    /**
     * 排序方式
     */
    public final static String ORDER = "order";
    /**
     * 升序
     */
    public final static String ASC = "asc";
    /**
     * 降序
     */
    public final static String DESC = "desc";
    /**
     * 调度任务类型  执行jar包方式
     */
    public static final String JAR_JOB = "0";

    /**
     * 权限类型
     */
    public enum AclType {
        /**
         * 目录
         */
        CATALOG("0"),
        /**
         * 菜单
         */
        MENU("1"),
        /**
         * 按钮
         */
        BUTTON("2"),
        /**
         * 其他
         */
        OTHER("3");

        private String value;

        AclType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    /**
     * 定时任务状态
     */
    public enum ScheduleStatus {
        /**
         * 正常
         */
        NORMAL(0),
        /**
         * 暂停
         */
        PAUSE(1);

        private int value;

        ScheduleStatus(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    /**
     * 云服务商
     */
    public enum CloudService {
        /**
         * 七牛云
         */
        QINIU(1),
        /**
         * 阿里云
         */
        ALIYUN(2),
        /**
         * 腾讯云
         */
        QCLOUD(3);

        private int value;

        CloudService(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public enum UserStatus {
        /**
         * 冻结
         */
        FREEZE("0"),
        /**
         * 正常
         */
        NORMAL("1"),
        /**
         * 删除
         */
        DELETE("2");

        private String value;

        UserStatus(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}
