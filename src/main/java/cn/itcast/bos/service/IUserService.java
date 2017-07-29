package cn.itcast.bos.service;

import cn.itcast.bos.domain.User;

import java.util.List;

/**
 * userService接口
 * Created by lee on 2017/2/6.
 */
@SuppressWarnings("all")
public interface IUserService {
    //用户登录
    public User login(User model);

    //用户查询
    public User check(User user);

    //用户编辑
    public void editPassword(String id, String passwordNew);

    //自动登录
    public User autoLogin(User user);

    //保存用户
    public void save(User user, String[] roleIds);

    //查询所有用户
    public List<User> list();
}
