package cn.itcast.bos.web;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * 查看正在运行的流程实例
 * Created by lee on 2017/2/20.
 */
@SuppressWarnings(value = "all")
@Controller(value = "processInstanceAction")
@Scope(value = "prototype")
public class ProcessInstanceAction extends ActionSupport {
    //注入processEngine
    @Resource(name = "processEngine")
    private ProcessEngine processEngine;

    /**
     * 查询所有正在运行的实例数据
     */
    public String list() {
        //创建processInstanceQuery对象
        ProcessInstanceQuery processInstanceQuery = processEngine.getRuntimeService().createProcessInstanceQuery();
        //查询
        List<ProcessInstance> processInstanceList = processInstanceQuery.list();
        //将结果存入栈中
        ActionContext.getContext().getValueStack().set("list",processInstanceList);
        return "list";
    }

    /**
     * 提供findData方法，根据流程实例ID查询流程变量数据
     */
    //接收流程实例ID
    private String id;

    public void setId(String id) {
        this.id = id;
    }

    //根据流程实例ID查询流程变量
    public String findData() throws IOException {
        //查询流程变量
        Map<String, Object> variables = processEngine.getRuntimeService().getVariables(id);
        //响应到页面中
        ServletActionContext.getResponse().setContentType("text/html;charset=UTF-8");
        ServletActionContext.getResponse().getWriter().print(variables);
        return NONE;
    }

    /**
     * 提供showPng方法，查询部署ID、png图片名称、当前任务的坐标
     */
    public String showPng() {
        //根据流程实例ID查询流程实例对象
        ProcessInstance processInstance = processEngine.getRuntimeService().createProcessInstanceQuery().processInstanceId(id).singleResult();
        //根据流程实例对象查询对应的流程定义ID
        String processDefinitionId = processInstance.getProcessDefinitionId();
        //根据流程定义ID查询流程定义对象
        ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity) processEngine.getRepositoryService().getProcessDefinition(processDefinitionId);
        //根据流程定义对象获取部署ID和png图片名称
        String deploymentId = processDefinitionEntity.getDeploymentId();//部署ID
        String diagramResourceName = processDefinitionEntity.getDiagramResourceName();//png图片名称
        //根据流程实例对象获取当前流程实例运行到哪个任务节点
        String activityId = processInstance.getActivityId();//获得节点ID
        ActivityImpl activity = processDefinitionEntity.findActivity(activityId);//获得节点
        //获取任务节点坐标,图片宽，高
        int x = activity.getX();
        int y = activity.getY();
        int width = activity.getWidth();
        int height = activity.getHeight();
        //将部署ID，png图片名称，x坐标,y坐标,图片宽，高存入值栈
        ActionContext.getContext().getValueStack().set("deploymentId",deploymentId);
        ActionContext.getContext().getValueStack().set("imageName",diagramResourceName);
        ActionContext.getContext().getValueStack().set("x",x);
        ActionContext.getContext().getValueStack().set("y",y);
        ActionContext.getContext().getValueStack().set("width",width);
        ActionContext.getContext().getValueStack().set("height",height);
        return "showPng";
    }

    /**
     * 提供viewImage方法，获取png图片的输入流
     */
    //封装页面传来的deploymentId
    private String deploymentId;

    //封装页面传来的imageName

    private String imageName;

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public void setDeploymentId(String deploymentId) {
        this.deploymentId = deploymentId;
    }

    //获取输入流
    public String viewImage() {
        InputStream pngStream = processEngine.getRepositoryService().getResourceAsStream(deploymentId, imageName);
        //存入栈中
        ActionContext.getContext().getValueStack().set("pngStream",pngStream);
        return "viewImage";
    }
}
