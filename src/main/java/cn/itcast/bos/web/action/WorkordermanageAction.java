package cn.itcast.bos.web.action;

import cn.itcast.bos.domain.Workordermanage;
import cn.itcast.bos.service.IWorkordermanageService;
import cn.itcast.bos.web.action.base.BaseAction;
import com.opensymphony.xwork2.ActionContext;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * 工单管理workordermanagerAction
 * Created by lee on 2017/2/15.
 */
@SuppressWarnings(value = "all")
@Controller(value = "workordermanageAction")
@Scope(value = "prototype")
public class WorkordermanageAction extends BaseAction<Workordermanage> {
    //注入workordermanageService
    @Resource(name = "workordermanageService")
    private IWorkordermanageService workordermanageService;

    /**
     * 保存工作单
     */
    public String save() throws IOException {
        System.out.println(model.getArrivecity());
        String flag = "1";
        try {
            workordermanageService.save(model);
        } catch (Exception e) {
            flag = "0";
            e.printStackTrace();
        }
        //将flag响应到页面
        ServletActionContext.getResponse().setContentType("text/html;charset=UTF-8");
        ServletActionContext.getResponse().getWriter().print(flag);
        return NONE;
    }

    /**
     * 查询状态为未启用的工作单
     */
    public String list() {
        //查询状态为未启用的工作单
        List<Workordermanage> list = workordermanageService.findListNotStart();
        //将查询结果存入值栈
        ActionContext.getContext().getValueStack().set("list",list);
        return "list";
    }

    /**
     * 提供start方法，启动物流配送流程实例
     */
    public String start() {
        workordermanageService.start(model.getId());
        return "toList";
    }
}
