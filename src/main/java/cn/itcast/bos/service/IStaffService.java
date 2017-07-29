package cn.itcast.bos.service;

import cn.itcast.bos.domain.Staff;
import cn.itcast.bos.utils.PageBean;
import org.hibernate.criterion.DetachedCriteria;

import java.util.List;

/**
 * 取派员管理Service接口
 * Created by lee on 2017/2/9.
 */
@SuppressWarnings(value = "all")
public interface IStaffService {
    /**
     * 保存取派员
     * @param staff
     */
    public void save(Staff staff);

    /**
     * 分页查询
     * @param pageBean
     */
    public void pageQuery(PageBean pageBean);

    /**
     * 作废
     * @param ids
     */
    public void delete(String ids);

    /**
     * 还原
     * @param ids
     */
    public void restore(String ids);

    /**
     * 修改取派员信息
     * @param staff
     */
    public void update(Staff staff);

    /**
     * 离线条件查询
     * @param detachedCriteria
     * @return
     */
    public List<Staff> findByCondition(DetachedCriteria detachedCriteria);
}
