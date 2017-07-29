package cn.itcast.bos.service.impl;

import cn.itcast.bos.dao.IRoleDao;
import cn.itcast.bos.dao.IUserDao;
import cn.itcast.bos.domain.Role;
import cn.itcast.bos.domain.User;
import cn.itcast.bos.service.IUserService;
import cn.itcast.bos.utils.MD5Utils;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.impl.persistence.entity.UserEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户操作Service
 * Created by lee on 2017/2/6.
 */
@Service(value = "userService")
@Transactional
@SuppressWarnings("all")
public class UserServiceImpl implements IUserService {
    //注入userDao
    @Resource(name = "userDao")
    private IUserDao userDao;

    //注入processEngine
    @Resource(name = "processEngine")
    private ProcessEngine processEngine;

    //注入roleDao
    @Resource(name = "roleDao")
    private IRoleDao roleDao;

    /**
     * 拦截器自动登录
     * @param user
     * @return
     */
    @Override
    public User autoLogin(User user) {
        //查询数据库，得到一个list集合
        List<User> list = userDao.findByNamedQuery("findByUsernameAndpassword", user.getUsername(), user.getPassword());
        //对查询结果进行判断
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    /**
     * 保存用户
     * 用户同步
     * 添加用户，同时关联角色，将用户数据同步到zjs_bos.ACT_ID_USER表中
     * @param user
     * @param roleIds
     */
    @Override
    public void save(User user, String[] roleIds) {
        //给密码加密
        String password = user.getPassword();
        password = MD5Utils.md5(password);
        user.setPassword(password);
        userDao.save(user);//持久对象

        //创建activiti的User对象userEntity
        org.activiti.engine.identity.User userEntity = new UserEntity();
        //将用户id设置为activiti的User对象的id
        userEntity.setId(user.getId());
        //保存到zjs_bos.ACT_ID_USER表中
        processEngine.getIdentityService().saveUser(userEntity);

        if (roleIds != null && roleIds.length > 0) {
            for (String id : roleIds) {
                /*Role role = new Role();
                role.setId(id);*/
                Role role = roleDao.findById(id);
                //关联到用户
                user.getRoles().add(role);
                //保存到zjs_bos.ACT_ID_MEMBERSHIP表中
                processEngine.getIdentityService().createMembership(userEntity.getId(),role.getName());
            }
        }
    }

    /**
     * 查询所有用户
     * @return
     */
    @Override
    public List<User> list() {
        return userDao.findAll();
    }

    /**
     * 用户登录
     *
     * @param model
     * @return
     */
    @Override
    public User login(User model) {
        //将输入的明文的密码进行MD5加密
        String password = MD5Utils.md5(model.getPassword());
        //查询数据库，得到一个list集合
        List<User> list = userDao.findByNamedQuery("findByUsernameAndpassword", model.getUsername(), password);
        //对查询结果进行判断
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    /**
     * 用户查询
     * @param user
     * @return
     */
    @Override
    public User check(User user) {
        //将输入的明文的密码进行MD5加密
        String password = MD5Utils.md5(user.getPassword());
        //查询数据库，得到一个list集合
        List<User> list = userDao.findByNamedQuery("findByUsernameAndpassword", user.getUsername(), password);
        //对查询结果进行判断
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    /**
     * 修改密码
     * @param id
     * @param passwordNew
     */
    @Override
    public void editPassword(String id, String passwordNew) {
        String password = MD5Utils.md5(passwordNew);
        userDao.executeNamedQuery("editPassword",password,id);
    }
}
