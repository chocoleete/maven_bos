package cn.itcast.bos.web.action;

import cn.itcast.bos.domain.Role;
import cn.itcast.bos.service.IRoleService;
import cn.itcast.bos.web.action.base.BaseAction;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.List;

/**
 * 角色管理roleAction
 * Created by lee on 2017/2/18.
 */
@SuppressWarnings(value = "all")
@Controller(value = "roleAction")
@Scope(value = "prototype")
public class RoleAction extends BaseAction<Role> {
    //注入roleService
    @Resource(name = "roleService")
    private IRoleService roleService;

    //属性封装页面传来的functionIds
    private String functionIds;

    public void setFunctionIds(String functionIds) {
        this.functionIds = functionIds;
    }

    /**
     * 保存角色
     */
    public String save() {
        roleService.save(model,functionIds);
        return "list";
    }

    /**
     * 查询所有角色
     */
    public String list() {
        //查询数据库中所有的role
        List<Role> list = roleService.list();
        //将role转换成json语句并响应到页面中
        String[] excludes={"functions","users"};
        this.writeList2json(list,excludes);
        return NONE;
    }
}
