package com.fans.common.threadpool.eventBean;

import lombok.*;

import java.util.Date;

/**
 * className: PayBean
 *
 * @author k
 * @version 1.0
 * @description 定时任务控制层
 * @date 2019-04-01 10:56
 **/
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PayBean {
    private Long orderNo;
    private Long productId;
    private String productName;
    private double price;
    private Date createTime;
}
