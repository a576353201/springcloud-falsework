package com.fans.utils.page;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fans.modules.utils.Constant;
import com.fans.modules.xss.SQLFilter;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * className: Query
 *
 * @author k
 * @version 1.0
 * @description 查询参数
 * @date 2020-10-25 14:47:34
 **/
public class Query<T> {

    public IPage<T> getPage(Map<String, Object> params) {
        return this.getPage(params, null, false);
    }

    private IPage<T> getPage(Map<String, Object> params, String defaultOrderField, Boolean isAsc) {
        //分页参数
        long curPage = 1;
        long limit = 10;
        if (params.get(Constant.PAGE) != null) {
            curPage = Long.parseLong(params.get(Constant.PAGE) + StringUtils.EMPTY);
        }
        if (params.get(Constant.LIMIT) != null) {
            limit = Long.parseLong(params.get(Constant.LIMIT) + StringUtils.EMPTY);
        }
        //分页对象
        Page<T> page = new Page<>(curPage, limit);
        //分页参数
        params.put(Constant.PAGE, page);

        //排序字段
        //防止SQL注入（因为sidx、order是通过拼接SQL实现排序的，会有SQL注入风险）
        String orderField = SQLFilter.sqlInject((String) params.get(Constant.ORDER_FIELD));
        String order = (String) params.get(Constant.ORDER);

        //前端字段排序
        if (StringUtils.isNotEmpty(orderField) && StringUtils.isNotEmpty(order)) {
            if (Constant.ASC.equalsIgnoreCase(order)) {
                page.setOrders(OrderItem.ascs(orderField));
            } else {
                page.setOrders(OrderItem.descs(orderField));
            }
        }
        //默认排序
        if (isAsc) {
            page.setOrders(OrderItem.ascs(defaultOrderField));
        } else {
            page.setOrders(OrderItem.descs(defaultOrderField));
        }
        page.setOrders(null);
        return page;
    }
}