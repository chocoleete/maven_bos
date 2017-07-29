package cn.itcast.bos.web.action;

import cn.itcast.bos.domain.Function;
import cn.itcast.bos.service.IFunctionService;
import cn.itcast.bos.utils.BOSContext;
import cn.itcast.bos.web.action.base.BaseAction;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.List;

/**
 * 权限管理functionAction
 * Created by lee on 2017/2/17.
 */
@SuppressWarnings(value = "all")
@Controller(value = "functionAction")
@Scope(value = "prototype")
public class FunctionAction extends BaseAction<Function> {
    //注入functionService
    @Resource(name = "functionService")
    private IFunctionService functionService;

    /**
     * 查询权限数据
     */
    public String list() {
        //查询数据库，得到所有的function数据list集合
        List<Function> list = functionService.list();
        //将list集合转换成json数据，并响应到页面中去
        String[] excludes={"function","functions","roles"};
        this.writeList2json(list,excludes);
        return NONE;
    }

    /**
     * 根据登录用户查询对应权限菜单数据
     */
    public String findMenu() {
        //查询数据库
        List<Function> list = functionService.findMenu(BOSContext.getLoginUser("loginUser"));
        //将list集合转转换成json并响应到页面
        String[] excludes={"function","functions","roles"};
        this.writeList2json(list,excludes);
        return NONE;
    }
}
