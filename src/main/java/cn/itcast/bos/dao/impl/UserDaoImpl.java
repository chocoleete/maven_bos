package cn.itcast.bos.dao.impl;

import cn.itcast.bos.dao.IUserDao;
import cn.itcast.bos.dao.base.BaseDaoImpl;
import cn.itcast.bos.domain.User;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户管理 Dao
 * Created by lee on 2017/2/5.
 */
@Repository(value = "userDao")
@SuppressWarnings(value = "all")
public class UserDaoImpl extends BaseDaoImpl<User> implements IUserDao {
    //根据用户名查询用户
    @Override
    public User findUserByUsername(String username) {
        String hql = "from User u where u.username=?";
        List<User> list = (List<User>) this.getHibernateTemplate().find(hql, username);
        //判断
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }
    /**
     * 添加用户用测试方法添加
     */
}
