package cn.itcast.bos.web.action;

import cn.itcast.bos.domain.Workordermanage;
import cn.itcast.bos.service.IWorkordermanageService;
import cn.itcast.bos.utils.BOSContext;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 创建TaskAction提供findGroupTask方法
 * Created by lee on 2017/2/20.
 */
@SuppressWarnings(value = "all")
@Controller(value = "taskAction")
@Scope(value = "prototype")
public class TaskAction extends ActionSupport {
    //注入processEngine
    @Resource(name = "processEngine")
    private ProcessEngine processEngine;

    //注入workService
    @Resource(name = "workordermanageService")
    private IWorkordermanageService workordermanageService;

    /**
     * 查询组任务
     */
    public String findGroupTask() {
        //创建taskQuery对象
        TaskQuery taskQuery = processEngine.getTaskService().createTaskQuery();
        //创建过滤条件
        String loginUserId = BOSContext.getLoginUser("loginUser").getId();
        taskQuery.taskCandidateUser(loginUserId);
        //查询
        List<Task> list = taskQuery.list();
        //将值存入值栈
        ActionContext.getContext().getValueStack().set("list",list);
        return "groupTaskList";
    }

    /**
     * 提供showData方法，根据任务ID查询流程变量
     */
    //接收从页面传来的任务Id
    private String taskId;

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskId() {
        return taskId;
    }

    //查询业务数据
    public String showData() throws IOException {
        Map<String, Object> variables = processEngine.getTaskService().getVariables(taskId);//获取流程变量
        ServletActionContext.getResponse().setContentType("text/html;charset=UTF-8");
        ServletActionContext.getResponse().getWriter().print(variables);
        return NONE;
    }

    /**
     * 拾取组任务
     */
    public String takeTask() {
        //获取当前登录用户ID
        String loginUserId = BOSContext.getLoginUser("loginUser").getId();
        //拾取任务
        processEngine.getTaskService().claim(taskId,loginUserId);
        return "toGroupTaskList";
    }

    /**
     * 查询个人任务
     */
    public String findPersonalTask() {
        //创建taskQuery对象
        TaskQuery taskQuery = processEngine.getTaskService().createTaskQuery();
        //添加过滤条件(当前登录用户的ID)
        taskQuery.taskAssignee(BOSContext.getLoginUser("loginUser").getId());
        //查询
        List<Task> list = taskQuery.list();
        //存入值栈
        ActionContext.getContext().getValueStack().set("list", list);
        return "personalTaskList";
    }

    /**
     * 办理个人任务
     * 办理审核工作单任务
     */
    //是否审核通过
    private Integer check;

    public Integer getCheck() {
        return check;
    }

    public void setCheck(Integer check) {
        this.check = check;
    }

    public String checkWorkOrderManage() {
        //根据任务ID查询流程变量
        Map<String, Object> variables = processEngine.getTaskService().getVariables(taskId);
        Workordermanage workordermanage = null;
        if (check == null) {
            //跳转到审核工作单页面
            ActionContext.getContext().getValueStack().set("map", variables);
            return "checkUI";
        } else {
            //将manageCheck改为“1”
            workordermanage = (Workordermanage) variables.get("业务数据");
            workordermanage.setManagerCheck("1");
            workordermanageService.update(workordermanage);
            //重新设置流程变量
            processEngine.getTaskService().setVariable(taskId, "业务数据", workordermanage);
            //办理业务
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("check", check);
            processEngine.getTaskService().complete(taskId, map);
            if ("0".equals(check)) {
                //审核不通过,将工作单中的start设置为“0”
                workordermanage.setStart("0");
                workordermanageService.update(workordermanage);
            }
        }
        return "toPersonalTaskList";
    }

    /**
     * 办理其它任务
     */
    //出库
    public String outStore() {
        processEngine.getTaskService().complete(taskId);
        return "toPersonalTaskList";
    }

    //派送
    public String transferGoods() {
        processEngine.getTaskService().complete(taskId);
        return "toPersonalTaskList";
    }

    //签收
    public String receive() {
        processEngine.getTaskService().complete(taskId);
        return "toPersonalTaskList";
    }
}
