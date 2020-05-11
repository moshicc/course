package com.course.server.dto;

import java.util.List;

/**
 * @author zcc
 * @date 2020/5/11 14:23
 * @description 方便前后端分页数据交互
 */

public class PageDto<T> {
    /**
     * 当前页码(前端传入)
     */
    protected int page;
    /**
     * 每页条数（前端传入）
     */
    protected int size;
    /**
     * 总条数
     */
    protected long total;
    /**
     * 所查询当前页的记录
     */
    protected List<T> list;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "PageDto{" +
                "page=" + page +
                ", size=" + size +
                ", total=" + total +
                ", list=" + list +
                '}';
    }
}
