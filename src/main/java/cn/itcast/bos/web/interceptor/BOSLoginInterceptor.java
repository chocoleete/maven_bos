package cn.itcast.bos.web.interceptor;


import cn.itcast.bos.domain.User;
import cn.itcast.bos.service.IUserService;
import cn.itcast.bos.utils.BOSContext;
import cn.itcast.bos.utils.CookieUtils;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 自定义拦截器，实现用户未登录，跳转到登录页面
 * Created by lee on 2017/2/7.
 */
@Component(value = "loginInterceptor")
public class BOSLoginInterceptor extends MethodFilterInterceptor {
    //注入userService
    @Resource(name = "userService")
    protected IUserService userService;

    //拦截的方法
    @Override
    protected String doIntercept(ActionInvocation invocation) throws Exception {
        System.out.println("执行自定义拦截器...");

        //获取request response
        HttpServletRequest request = BOSContext.getRequestFun();
        HttpServletResponse response = BOSContext.getResponseFun();
        //获取session
        HttpSession session = BOSContext.getSessionFun();

        //从session中获取当前用户
        User loginUser = BOSContext.getLoginUser("loginUser");
        //判断session中是否有值
        if (loginUser == null) {

            //获取cookie
            Cookie cookie = CookieUtils.findCookie(request.getCookies(), "autoLogin");
            if (cookie != null) {
                String username = cookie.getValue().split("#")[0];
                String password = cookie.getValue().split("#")[1];//密码已经加密

                //使用shiro提供的方式进行权限证
                Subject subject = SecurityUtils.getSubject();//获得当前用户对象，现在状态为“未认证”
                AuthenticationToken token = new UsernamePasswordToken(username, password);
                try {
                    subject.login(token);//调用安全管理器，安全管理器调用Realm
                    User user = (User) subject.getPrincipal();//获取BOSRealm中存入的User对象

                    //登录成功，将user放入session,跳转到系统首页
                    session.setAttribute("loginUser", user);
                    //放行
                    return invocation.invoke();
                } catch (UnknownAccountException e) {
                    //用户名不存在，跳转到登录页面
                    e.printStackTrace();
                    return "login";
                } catch (IncorrectCredentialsException e) {
                    //密码错误，跳转到登录页面
                    e.printStackTrace();
                    return "login";
                }
            }
            /*//获取cookie
            Cookie cookie = CookieUtils.findCookie(request.getCookies(), "autoLogin");
            if (cookie != null) {
                String username = cookie.getValue().split("#")[0];
                String password = cookie.getValue().split("#")[1];
                System.out.println("cookie中的值:" + username + "#" + password);
                //创建user对象
                User user = new User();
                user.setUsername(username);
                user.setPassword(password);
                //创建userService
                *//*
                UserService userService = new UserService();
                注意如果直接new UserService对象会导致Null Point exception
                 *//*
                //登录
                System.out.println("传给service的user对象中的值:" + user.getUsername() + "#" + user.getPassword());
                User userExist = userService.autoLogin(user);
                if (userExist != null) {
                    //将查到的usr对象存入session
                    session.setAttribute("loginUser", userExist);
                    return invocation.invoke();//放行
                }
            }*/

            //用户没有登录，跳转到登录页面
            return "login";
        }
        return invocation.invoke();//放行
    }
}
