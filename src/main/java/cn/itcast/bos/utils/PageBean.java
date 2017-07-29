package cn.itcast.bos.utils;

import org.hibernate.criterion.DetachedCriteria;

import java.util.List;

/**
 * 封装分页信息
 * Created by lee on 2017/2/9.
 */
public class PageBean {
    //成员变量
    private Integer currentPage;//当前页码
    private Integer pageSize;//每页显示记录数
    private DetachedCriteria detachedCriteria;//分页查询条件

    private Integer total;//总记录数
    private List rows;//当前页展示的数据集合

    //成员方法
    public PageBean() {
    }

    public PageBean(Integer currentPage, Integer pageSize, DetachedCriteria detachedCriteria, Integer total, List rows) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.detachedCriteria = detachedCriteria;
        this.total = total;
        this.rows = rows;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public DetachedCriteria getDetachedCriteria() {
        return detachedCriteria;
    }

    public void setDetachedCriteria(DetachedCriteria detachedCriteria) {
        this.detachedCriteria = detachedCriteria;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List getRows() {
        return rows;
    }

    public void setRows(List rows) {
        this.rows = rows;
    }
}
