package cn.itcast.bos.dao.base;

import cn.itcast.bos.utils.PageBean;
import org.hibernate.criterion.DetachedCriteria;

import java.io.Serializable;
import java.util.List;

/**
 * 抽取持久层接口
 * Created by lee on 2017/2/5.
 */
public interface IBaseDao<T> {
    /**
     * 添加
     */
    public void save(T entity);

    /**
     * 删除
     */
    public void delete(T entity);

    /**
     * 修改
     */
    public void update(T entity);

    /**
     * 查
     */
    // 根据id查询
    public T findById(Serializable id);

    // 查询所有
    public List<T> findAll();

    // 根据条件查询
    public List<T> findByCriteria(DetachedCriteria criteria);

    // 根据命名查询语句查询
    public List<T> findByNamedQuery(String queryName, Object... args);

    /**
     * 执行增删改操作的命名语句
     */
    public void executeNamedQuery(String queryName, Object... args);

    /**
     * 分页查询
     */
    public void pageQuery(PageBean pageBean);
}
