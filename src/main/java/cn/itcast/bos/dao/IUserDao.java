package cn.itcast.bos.dao;

import cn.itcast.bos.dao.base.IBaseDao;
import cn.itcast.bos.domain.User;

/**
 * IUserDao 接口
 * Created by lee on 2017/2/5.
 */
public interface IUserDao extends IBaseDao<User> {
    //根据用户名查询用户
    public User findUserByUsername(String username);
}
