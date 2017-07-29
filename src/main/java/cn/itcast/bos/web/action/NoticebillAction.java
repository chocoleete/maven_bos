package cn.itcast.bos.web.action;

import cn.itcast.bos.domain.Noticebill;
import cn.itcast.bos.domain.User;
import cn.itcast.bos.service.INoticebillService;
import cn.itcast.bos.utils.BOSContext;
import cn.itcast.bos.web.action.base.BaseAction;
import cn.itcast.crm.domain.Customer;
import cn.itcast.crm.service.CustomerService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

/**
 * 通知单Action
 * Created by lee on 2017/2/14.
 */
@SuppressWarnings(value = "all")
@Controller(value = "noticebillAction")
@Scope(value = "prototype")
public class NoticebillAction extends BaseAction<Noticebill> {
    //注入noticebillService
    @Resource(name = "noticebillService")
    private INoticebillService noticebillService;

    //注入代理对象，调用crm服务
    @Resource(name = "customerService")
    private CustomerService customerService;

    //属性封装，接收页面Ajax传来的参数phone
    private String phone;

    public void setPhone(String phone) {
        this.phone = phone;
    }
    /**
     * 调用crm项目，通过手机号查询客户
     */
    public String findCustomerByPhone() {
        Customer customer = customerService.findCustomerByPhone(phone);
        String[] excludes={"decidedzoneId"};
        this.writeObject2json(customer, excludes);
        return NONE;
    }

    /**
     * 业务受理，保存业务通知单，尝试自动分单
     */
    public String save() {
        //从session中获取登录用户
        User loginUser = BOSContext.getLoginUser("loginUser");
        //业务通知单关联当前用户
        model.setUser(loginUser);
        //保存业务通知单
        noticebillService.save(model);

        return "toSave";
    }
}
