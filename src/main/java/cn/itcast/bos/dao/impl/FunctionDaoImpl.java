package cn.itcast.bos.dao.impl;

import cn.itcast.bos.dao.IFunctionDao;
import cn.itcast.bos.dao.base.BaseDaoImpl;
import cn.itcast.bos.domain.Function;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 权限管理functionDao实现类
 * Created by lee on 2017/2/17.
 */
@SuppressWarnings(value = "all")
@Repository(value = "functionDao")
public class FunctionDaoImpl extends BaseDaoImpl<Function> implements IFunctionDao {
    /**
     * 根据userId查询权限
     * @param userId
     * @return
     */
    @Override
    public List<Function> findFunctionByUserId(String userId) {
        String hql = "from Function f left outer join fetch f.roles r left outer join fetch r.users u where u.id=?";
        return (List<Function>) this.getHibernateTemplate().find(hql,userId);
    }

    /**
     * 查询所有菜单
     * @return
     */
    @Override
    public List<Function> findAllMenu() {
        String hql = "from Function f where f.generatemenu='1' order by f.zindex desc";
        return (List<Function>) this.getHibernateTemplate().find(hql);
    }

    /**
     * 根据用户id查询菜单
     * @param id
     * @return
     */
    @Override
    public List<Function> findMenu(String id) {
        String hql = "select distinct f from Function f " +
                "left outer join fetch f.roles r " +
                "left outer join fetch r.users u " +
                "where u.id=? and f.generatemenu='1' order by f.zindex desc";
        return (List<Function>) this.getHibernateTemplate().find(hql,id);
    }
}
