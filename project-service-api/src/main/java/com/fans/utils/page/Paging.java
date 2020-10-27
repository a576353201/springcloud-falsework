package com.fans.utils.page;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * className: PageUtils
 *
 * @author k
 * @version 1.0
 * @description 分页对象
 * @date 2020-10-25 14:47:34
 **/
@ApiModel(value = "分页结果")
@NoArgsConstructor
@Data
public class Paging<T> implements Serializable {

    private static final long serialVersionUID = -6123889349750361441L;

    @ApiModelProperty(value = "总记录数", dataType = "int")
    private int totalCount;
    @ApiModelProperty(value = "每页记录数", dataType = "int")
    private int pageSize;
    @ApiModelProperty(value = "总页数", dataType = "int")
    private int totalPage;
    @ApiModelProperty(value = "当前页数", dataType = "int")
    private int currPage;
    @ApiModelProperty(value = "列表数据", dataType = "list")
    private List<T> list;

    /**
     * 分页
     *
     * @param totalCount 总记录数
     * @param pageSize   每页记录数
     * @param currPage   当前页数
     * @param list       列表数据
     */
    public Paging(int totalCount, int pageSize, int currPage, List<T> list) {
            this.totalCount = totalCount;
            this.pageSize = pageSize;
            this.currPage = currPage;
            this.list = list;
            this.totalPage = (int) Math.ceil((double) totalCount / pageSize);
    }

    public Paging(IPage<T> page) {
            this.list = page.getRecords();
            this.totalCount = (int) page.getTotal();
            this.pageSize = (int) page.getSize();
            this.currPage = (int) page.getCurrent();
            this.totalPage = (int) page.getPages();
    }

    public int getTotalCount() {
            return totalCount;
    }

    public void setTotalCount(int totalCount) {
            this.totalCount = totalCount;
    }

    public int getPageSize() {
            return pageSize;
    }

    public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
    }

    public int getTotalPage() {
            return totalPage;
    }

    public void setTotalPage(int totalPage) {
            this.totalPage = totalPage;
    }

    public int getCurrPage() {
            return currPage;
    }

    public void setCurrPage(int currPage) {
            this.currPage = currPage;
    }

    public List<T> getList() {
            return list;
    }

    public void setList(List<T> list) {
            this.list = list;
    }
}
