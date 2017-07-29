package cn.itcast.bos.shiro;

import cn.itcast.bos.dao.IFunctionDao;
import cn.itcast.bos.dao.IUserDao;
import cn.itcast.bos.domain.Function;
import cn.itcast.bos.domain.User;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import javax.annotation.Resource;
import java.util.List;

/**
 * 自定义realm,进行认证及授权
 * Created by lee on 2017/2/17.
 */
@SuppressWarnings(value = "all")
public class
        BOSRealm extends AuthorizingRealm {
    //注入userDao
    @Resource(name = "userDao")
    private IUserDao userDao;

    //注入functionDao
    @Resource(name = "functionDao")
    private IFunctionDao functionDao;

    /**
     * 认证方法
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken myToken = (UsernamePasswordToken) authenticationToken;//向下转型
        String username = myToken.getUsername();
        char[] password = myToken.getPassword();

        //根据用户名查询数据库中的密码，将密码交给安全管理器，由安全管理器对象负责比较数据库中的密码和页面传递的密码是否一致
        User user = userDao.findUserByUsername(username);
        if (user == null) {
            return null;
        }
        /*
        参数一：签名对象,如果认证通过后，可以在程序任意位置获取当前放入的对象
        参数二：数据库中查询出的密码
        参数三：当前realm的类名
         */
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(user, user.getPassword(), this.getClass().getName());
        //返回
        return simpleAuthenticationInfo;
    }

    /**
     * 授权方法
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //授权信息对象
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        //根据当前登录用户查询数据库，获取其相应的权限
//        simpleAuthorizationInfo.addStringPermission("staff");
//        simpleAuthorizationInfo.addStringPermission("staff-delete");
        User user = (User) principalCollection.getPrimaryPrincipal();
        if ("admin".equals(user.getUsername())) {
            //超级管理员用户
            List<Function> list = functionDao.findAll();
            //遍历，赋所有权限
            for (Function function : list) {
                simpleAuthorizationInfo.addStringPermission(function.getCode());
            }
        } else {
            //普通用户，根据用户查询相应的权限
            List<Function> list = functionDao.findFunctionByUserId(user.getId());
            for (Function function : list) {
                simpleAuthorizationInfo.addStringPermission(function.getCode());
            }
        }
        return simpleAuthorizationInfo;
    }
}
