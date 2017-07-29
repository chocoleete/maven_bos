package cn.itcast.bos.web.action;

import cn.itcast.bos.domain.Staff;
import cn.itcast.bos.service.IStaffService;
import cn.itcast.bos.web.action.base.BaseAction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * 取派员管理Action
 * Created by lee on 2017/2/9.
 */
@Controller(value = "staffAction")
@Scope(value = "prototype")
@SuppressWarnings(value = "all")
public class StaffAction extends BaseAction<Staff> {
    //注入staffService
    @Resource(name = "staffService")
    private IStaffService staffService;

    //属性封装ids,接收页面传来的ids参数
    private String ids;

    public void setIds(String ids) {
        this.ids = ids;
    }

    /**
     * 保存取派员方法
     */
    public String save() {
        staffService.save(model);
        return "list";
    }

    /**
     * 查询取派员数据，序列化为json数据
     */
    public String pageQuery() throws IOException {
        //调用service层的方法
        staffService.pageQuery(pageBean);

        /**
         * pageBean中的list和total都有了
         * 使用json-lib将pageBean对象序列化为json数据
         */
        //使用JsonConfig对象设置哪些属性不需要进行序列化
        this.writePageBean2json(pageBean, new String[]{"detachedCriteria", "pageSize", "currentPage","decidedzones","noticebills","workbills"});
        return NONE;
    }

    /**
     * 作废的方法
     */
//    @RequiresPermissions(value = "staff-delete")//必须具有staff-delete权限，才能访问该方法
    public String delete() {
        staffService.delete(ids);
        return "list";
    }

    /**
     * 还原的方法
     */
    public String restore() {
        staffService.restore(ids);
        return "list";
    }

    /**
     * 修改取派员信息
     */
    public String update() {
        //先查询原始数据，再进行数据覆盖
        staffService.update(model);
        return "list";
    }

    /**
     * 异步查询staff信息，返回json
     */
    public String findStaffByAjax() {
        //提供离线条件查询，包装查询条件
        DetachedCriteria staffDetachedCriteria = DetachedCriteria.forClass(Staff.class);
        //添加过滤条件，没有作废的取派员
        staffDetachedCriteria.add(Restrictions.eq("deltag", "0"));
        //添加过滤条件，没有负责定区的取派员(与需求不符)
//        staffDetachedCriteria.add((Restrictions.isEmpty("decidedzones")));
        //查询数据库,得到满足条件的staff集合
        List<Staff> list = staffService.findByCondition(staffDetachedCriteria);
        /**
         * 将list集合转成json,并响应到页面
         * 页面需要的只是staff实体对象中的id及name属性
         */
        String[] excludes={"decidedzones","standard","station","deltag","haspda","telephone","noticebills","workbills"};
        this.writeList2json(list,excludes);
        return NONE;
    }
}
