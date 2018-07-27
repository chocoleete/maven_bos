package cn.itcast.bos.utils;

import cn.itcast.bos.domain.User;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 获取session中的存放的用户信息的工具类
 * Created by lee on 2017/2/7.
 */
public class BOSContext {
    /**
     * 返回值类型User
     * 参数列表String key
     */
    public static User getLoginUser(String key) {
        // 获取session
        HttpSession session = getSessionFun();
        // 判断session中是否有值
        User existUser = (User)session.getAttribute(key);
        return existUser;
    }

    /**
     * 返回值类型 HttpServletRequest request
     * 参数列表 无
     */
    public static HttpServletRequest getRequestFun(){
        return ServletActionContext.getRequest();
    }

    /**
     * 返回值类型 HttpServletResponse response
     * 参数列表 无
     */
    public static HttpServletResponse getResponseFun() {
        return ServletActionContext.getResponse();
    }

    /**
     * 返回值类型 HttpSession session
     * 参数列表 无
     */
    public static HttpSession getSessionFun() {
        return getRequestFun().getSession();
    }
}
