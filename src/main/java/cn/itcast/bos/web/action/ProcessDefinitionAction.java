package cn.itcast.bos.web.action;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipInputStream;

/**
 * 查询最新版本的流程定义列表数据
 * Created by lee on 2017/2/20.
 */
@SuppressWarnings(value = "all")
@Controller(value = "processDefinitionAction")
@Scope(value = "prototype")
public class ProcessDefinitionAction extends ActionSupport{
    //注入流程引擎对象
    @Resource(name = "processEngine")
    private ProcessEngine processEngine;

    /**
     * 查询最新版本的流程定义列表
     */
    public String list() {
        //创建processDefinitionQuery对象
        ProcessDefinitionQuery processDefinitionQuery = processEngine.getRepositoryService().createProcessDefinitionQuery();
        //查询
        List<ProcessDefinition> processDefinitionList = processDefinitionQuery.list();
        //将得到的list集合存入栈中
        ActionContext.getContext().getValueStack().set("list",processDefinitionList);
        return "list";
    }

    /**
     * 在Action中提供一个File类型的属性deploy，用于接收上传的文件，提供一个deploy方法，完成部署流程定义操作
     */
    //接收页面上传的文件
    private File deploy;

    public void setDeploy(File deploy) {
        this.deploy = deploy;
    }

    public String deploy() throws FileNotFoundException {
        //创建deploymentBuilder
        DeploymentBuilder deploymentBuilder = processEngine.getRepositoryService().createDeployment();
        //创建输入流
        FileInputStream fileInputStream = new FileInputStream(deploy);
        //在deploymentBuilder对象中添加流
        deploymentBuilder.addZipInputStream(new ZipInputStream(fileInputStream));
        //部署流程定义
        deploymentBuilder.deploy();
        //转到查询流程定义的方法
        return "toList";
    }

    /**
     * 获取当前png图片对应的输入流，提供id属性接收页面传递的参数（流程定义ID）
     */
    //接收流程定义id
    private String id;

    public void setId(String id) {
        this.id = id;
    }

    //根据定义id获取对应的png图片输入流，使用文件下载方式展示到浏览器中
    public String viewpng() {
        //获取png输入流
        InputStream pngStream = processEngine.getRepositoryService().getProcessDiagram(id);
        //存入栈中
        ActionContext.getContext().getValueStack().set("pngStream", pngStream);
        return "viewpng";
    }
}
