package cn.itcast.bos.utils;

import cn.itcast.bos.domain.User;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自动登录与记住密码工具类
 * Created by lee on 2017/2/17.
 */
public class AutoLoginAndRemember {

    /**
     * 自动登录
     * 返回值类型 void
     * 参数列表 (HttpServletRequest request,HttpServletResponse response,String autoLoginKey,User user)
     */
    public static void autoLogin(HttpServletRequest request,HttpServletResponse response,String autoLoginKey,User user) {
        /**
         * 自动登录
         */
        String autoLogin = request.getParameter(autoLoginKey);
        //判断值是否为true
        if ("true".equals(autoLogin)) {
            //记录用户名及密码
            Cookie cookie = new Cookie(autoLoginKey, user.getUsername()+"#"+user.getPassword());
            cookie.setPath(request.getContextPath());//存储路径
            cookie.setMaxAge(60*60*24*7);//存储时间
            response.addCookie(cookie);
        }
    }

    /**
     * 记住用户名
     * 返回值类型 void
     * 参数列表(HttpServletRequest request,HttpServletResponse response,String rememberKey,User user)
     */
    public static void rememberUserName(HttpServletRequest request,HttpServletResponse response,String rememberKey,User user) {
        String remember = request.getParameter(rememberKey);
        //判断其值是否为true
        if ("true".equals(remember)) {
            //存入cookie
            Cookie cookie = new Cookie(rememberKey, user.getUsername());
            cookie.setPath(request.getContextPath());//存储路径
            cookie.setMaxAge(60*60*24*7);//存储时间
            response.addCookie(cookie);//添加cook到response中
        }
    }
}
