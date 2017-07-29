package cn.itcast.bos.web.action;

import cn.itcast.bos.domain.Decidedzone;
import cn.itcast.bos.service.IDecidedzoneService;
import cn.itcast.bos.web.action.base.BaseAction;
import cn.itcast.crm.domain.Customer;
import cn.itcast.crm.service.CustomerService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by lee on 2017/2/12.
 */
@SuppressWarnings(value = "all")
@Controller(value = "decidedzoneAction")
@Scope(value = "prototype")
public class DecidedzoneAction extends BaseAction<Decidedzone> {
    //注入decidedService
    @Resource(name = "decidedzoneService")
    private IDecidedzoneService decidedzoneService;

    //注入远程服务代理对象
    @Resource(name = "customerService")
    private CustomerService customerService;

    //属性封装分区id
    private String subareaid;

    public void setSubareaid(String subareaid) {
        this.subareaid = subareaid;
    }

    /**
     * 保存定区
     */
    public String save() {
        System.out.println("name="+model.getName());
        decidedzoneService.save(model, subareaid);
        return "list";
    }
    /**
     * 分页查询
     */
    public String pageQuery() {
        //查询数据库
        decidedzoneService.pageQuery(pageBean);
        //将pageBean转换成json数据，并响应到页面
        String[] excludes={"subareas","currentPage","pageSize","detachedCriteria","decidedzones","noticebills","workbills"};
        this.writePageBean2json(pageBean,excludes);
        return NONE;
    }

    /**
     * 通过代理对象调用crm服务，查询未关联到定区的客户
     */
    public String findCustomersNoAssociation() {
        //调用远程crm服务查询数据库中未关联到定区的客户
        List<Customer> customers = customerService.findnoassociationCustomers();
        //将customers集合转为json并响应到页面中
        String[] excludes={"station","telephone","address","decidedzone_id"};
        this.writeList2json(customers,excludes);
        return NONE;
    }

    /**
     * 通过代理对象调用crm服务，查询已经关联定区的客户
     */
    public String findCustomersAssociation() {
        //调用远程crm服务查询数据库中未关联到定区的客户
        List<Customer> customers = customerService.findhasassociationCustomers(model.getId());
        //将customers集合转为json并响应到页面中
        String[] excludes={"station","telephone","address","decidedzone_id"};
        this.writeList2json(customers,excludes);
        return NONE;
    }

    //属性封装接收页面表单提交的客户id数组
    private Integer[] customerIds;

    public void setCustomerIds(Integer[] customerIds) {
        this.customerIds = customerIds;
    }

    /**
     * 关联客户到定区
     */
    public String assignCustomersToDecidedZone() {
        customerService.assignCustomersToDecidedZone(customerIds,model.getId());
        return "list";
    }
}
