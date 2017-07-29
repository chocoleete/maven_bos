package cn.itcast.bos.web.action;

import cn.itcast.bos.domain.User;
import cn.itcast.bos.service.IUserService;
import cn.itcast.bos.utils.AutoLoginAndRemember;
import cn.itcast.bos.utils.BOSContext;
import cn.itcast.bos.utils.MD5Utils;
import cn.itcast.bos.web.action.base.BaseAction;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * 用户管理 Action
 * Created by lee on 2017/2/5.
 */
@Controller(value = "userAction")
@Scope(value = "prototype")
@SuppressWarnings(value = "all")
public class UserAction extends BaseAction<User> {
    //注入userService
    @Resource(name = "userService")
    private IUserService userService;
    //属性封装接收验证码
    private String checkcode;

    public void setCheckcode(String checkcode) {
        this.checkcode = checkcode;
    }

    //属性封装接收新密码
    private String passwordNew;

    public void setPasswordNew(String passwordNew) {
        this.passwordNew = passwordNew;
    }

    //属性封装roleIds
    private String[] roleIds;

    public void setRoleIds(String[] roleIds) {
        this.roleIds = roleIds;
    }

    /**
     * 查询所有用户
     */
    public String list() {
        //查询数据库得到所有的user信息
        List<User> list = userService.list();
        //将user的集合转换成json并响应到页面中
        String[] excludes={"noticebills","roles"};
        this.writeList2json(list,excludes);
        return NONE;
    }

    /**
     * 保存用户
     */
    public String save() {
        userService.save(model, roleIds);
        return "list";
    }

    /**
     * 修改密码
     */
    public String editPassword() throws IOException {
        //先判断原密码是否正确
        String password = model.getPassword();
//        System.out.println("原密码="+password);
        //从session中获得用户名
        User loginUser = BOSContext.getLoginUser("loginUser");
        String username = loginUser.getUsername();
//        System.out.println("用户名="+username);
//        System.out.println("新密码="+passwordNew);
        //先创建一个user对象checkUser将用户名和原密码封装进去，在service层调用查询用户名及密码得到一个user对象existUser
        User checkUser = new User();
        checkUser.setUsername(username);//封装用户名
        checkUser.setPassword(password);//封闭原密码
        User existUser = userService.check(checkUser);
        //判断existUser是否存在，如果存在，再更改密码
        if (existUser != null) {
            //定义标记变量
            boolean flag = true;//默认值为true
            String id = loginUser.getId();
            try {
                userService.editPassword(id, passwordNew);
            } catch (Exception e) {
                flag = false;
            }
            ServletActionContext.getResponse().setContentType("text/html;charset=UTF-8");
            ServletActionContext.getResponse().getWriter().print(flag);
        }
        return NONE;
    }

    /**
     * 用户退出的方法
     */
    public String logout() {
        //得到request
        HttpServletRequest request = ServletActionContext.getRequest();
        //得到session
        HttpSession session = request.getSession();
        //使session失效
        session.invalidate();

        //得到response
        HttpServletResponse response = ServletActionContext.getResponse();
        //销毁cookie
        Cookie cookie = new Cookie("autoLogin", "");
        cookie.setPath(request.getContextPath());//存储路径
        cookie.setMaxAge(0);//存储时间
        response.addCookie(cookie);
        return "login";
    }

    /**
     * 用户登录的方法
     */
    public String login() {
        //从session中获取生成的验证码
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();

        HttpSession session = request.getSession();//获得session
        String validateCode = (String) session.getAttribute("key");//得到session中存的验证码

//        System.out.println("session中的验证码:"+validateCode);
//        System.out.println("页面传来的验证码:"+checkcode);
        //确定验证码输入是否正确
        if (StringUtils.isBlank(checkcode) || !checkcode.equals(validateCode)) {
            //验证码输入错误，跳转到登录页面，提示错误信息
//            this.addActionError("验证码输入有误！");//为便于后期更改提示内容，将“验证码输入有误”用配置文件显示
            this.addActionError(this.getText("checkCodeError"));
            return "login";
        } else {
            //使用shiro提供的方式进行权限证
            Subject subject = SecurityUtils.getSubject();//获得当前用户对象，现在状态为“未认证”
            String username=model.getUsername();//获得用户名
            String password=model.getPassword();//获得密码
            password = MD5Utils.md5(password);//给密码加密
            AuthenticationToken token=new UsernamePasswordToken(username,password);
            try {
                subject.login(token);//调用安全管理器，安全管理器调用Realm
                User user = (User) subject.getPrincipal();//获取BOSRealm中存入的User对象

                /**
                 * 自动登录
                 */
                AutoLoginAndRemember.autoLogin(request, response, "autoLogin", user);

                /**
                 * 记住用户名
                 */
                AutoLoginAndRemember.rememberUserName(request,response,"remember",user);

                //登录成功，将user放入session,跳转到系统首页
                session.setAttribute("loginUser", user);
            } catch (UnknownAccountException e) {
                //用户名不存在，跳转到登录页面
                this.addActionError("用户名不存在！");
                e.printStackTrace();
                return "login";
            } catch (IncorrectCredentialsException e) {
                //密码错误，跳转到登录页面
                this.addActionError("密码错误！");
                e.printStackTrace();
                return "login";
            }
            //跳转到系统首页
            return "home";
        }
    }

    /**
     * 用户登录的方法备份
     */
    public String login_bak() {
        //从session中获取生成的验证码
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();

        HttpSession session = request.getSession();//获得session
        String validateCode = (String) session.getAttribute("key");//得到session中存的验证码

//        System.out.println("session中的验证码:"+validateCode);
//        System.out.println("页面传来的验证码:"+checkcode);
        //确定验证码输入是否正确
        if (StringUtils.isBlank(checkcode) || !checkcode.equals(validateCode)) {
            //验证码输入错误，跳转到登录页面，提示错误信息
//            this.addActionError("验证码输入有误！");//为便于后期更改提示内容，将“验证码输入有误”用配置文件显示
            this.addActionError(this.getText("checkCodeError"));
            return "login";
        } else {
            //验证码输入正确 进行登录校验
//            System.out.println(model.getUsername() + ":" + model.getPassword());
            User user = userService.login(model);
            //对user进行判断
            if (user != null) {

                /**
                 * 自动登录
                 */
                AutoLoginAndRemember.autoLogin(request, response, "autoLogin", user);

                /**
                 * 记住用户名
                 */
                AutoLoginAndRemember.rememberUserName(request,response,"remember",user);

                //登录成功，将user放入session，跳转到系统首页
                session.setAttribute("loginUser", user);
                return "home";
            } else {
                //登录失败，跳转到登录页面，设置登录失败的错误提示信息
                this.addActionError(this.getText("loginFail"));
                return "login";
            }
        }
    }
}
