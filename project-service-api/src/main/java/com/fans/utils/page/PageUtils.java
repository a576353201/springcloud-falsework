package com.fans.utils.page;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fans.common.utils.ObjectUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * className: PageUtils
 *
 * @author k
 * @version 1.0
 * @description 分页工具类
 * @date 2020-10-25 14:47:34
 **/
@SuppressWarnings(value = {"unchecked"})
public class PageUtils {

    /**
     * description: 返回分页数据
     *
     * @param page 分页数据
     * @return com.fans.Paging<S>
     * @author k
     * @date 2020/06/03 15:37
     **/
    public static <S> Paging<S> page(IPage<S> page) {
        return new Paging<>(page);
    }

    /**
     * description: 返回分页数据带转化VO
     *
     * @param page   分页数据
     * @param tClass 要转化的VO源类
     * @return com.fans.Paging<T>
     * @author k
     * @date 2020/06/03 15:37
     **/
    public static <S, T> Paging<T> page(IPage<S> page, Class<T> tClass) {
        Paging<S> source = new Paging<>(page);
        source.setList(Lists.newArrayList());
        Paging<T> paging = ObjectUtils.copyProperties(source, Paging.class);
        paging.setList(page.getRecords().stream().map(sour -> ObjectUtils.copyProperties(sour, tClass)).collect(Collectors.toList()));
        return paging;
    }

    /**
     * 集合分页
     *
     * @param currentPage 当前页数 0
     * @param pageSize    页面元素容量 5
     * @param sourceData  要分页的原数据
     * @return 分页结果
     */
    public static <S> Paging<S> pageByList(int currentPage, int pageSize, List<S> sourceData) {
        Paging<S> result = new Paging<>(sourceData.size(), currentPage, pageSize, sourceData);
        if (currentPage == 0 && pageSize == 0) {
            result.setList(sourceData);
        } else {
            result.setList(sourceData.stream().skip(currentPage * pageSize).limit(pageSize).collect(Collectors.toList()));
        }
        return result;
    }


    /**
     * 集合分页带转化VO
     *
     * @param currentPage 当前页数 1
     * @param pageSize    页面元素容量 5
     * @param sourceData  要分页的原数据
     * @param tClass      要转化的VO源类
     * @return 分页结果
     */
    public static <S, T> Paging<T> pageByList(int currentPage, int pageSize, List<S> sourceData, Class<T> tClass) {
        List<T> items = sourceData
                .stream()
                .map(source -> ObjectUtils.copyProperties(source, tClass))
                .collect(Collectors.toList());
        Paging<S> source = pageByList(currentPage, pageSize, sourceData);
        source.setList(Lists.newArrayList());
        Paging<T> result = ObjectUtils.copyProperties(source, Paging.class);
        if (currentPage == 0 && pageSize == 0) {
            result.setList(items);
        } else {
            result.setList(items.stream().skip((currentPage - 1) * pageSize).limit(pageSize).collect(Collectors.toList()));
        }
        result.setTotalCount(items.size());
        return result;
    }

    public static Map<String, Object> pagingPackaging(Integer page, Integer pageSize) {
        Map<String, Object> result = Maps.newHashMap();
        result.put("page" , page);
        result.put("limit" , pageSize);
        return result;
    }
}
