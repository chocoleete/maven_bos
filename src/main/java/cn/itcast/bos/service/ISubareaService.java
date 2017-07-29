package cn.itcast.bos.service;

import cn.itcast.bos.domain.Subarea;
import cn.itcast.bos.utils.PageBean;
import org.hibernate.criterion.DetachedCriteria;

import java.util.List;

/**
 * 分区管理service接口
 * Created by lee on 2017/2/11.
 */
@SuppressWarnings(value = "all")
public interface ISubareaService {
    /**
     * 添加分区
     * @param subarea
     */
    public void save(Subarea subarea);

    /**
     * 分页查询
     * @param pageBean
     */
    public void pageQuery(PageBean pageBean);

    /**
     * 查询所有
     * @return
     */
    public List<Subarea> findAll();

    /**
     * 离线条件查询
     * @param detachedCriteria
     * @return
     */
    public List<Subarea> findByCondition(DetachedCriteria detachedCriteria);
}
