package cn.itcast.bos.service.impl;

import cn.itcast.bos.dao.IWorkordermanageDao;
import cn.itcast.bos.domain.Workordermanage;
import cn.itcast.bos.service.IWorkordermanageService;
import org.activiti.engine.ProcessEngine;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 工单管理workordermanageService实现类
 * Created by lee on 2017/2/15.
 */
@SuppressWarnings(value = "all")
@Service(value = "workordermanageService")
@Transactional
public class WorkordermanageServiceImpl implements IWorkordermanageService {
    //注入workordermanageDao
    @Resource(name = "workordermanageDao")
    private IWorkordermanageDao workordermanageDao;

    //注入processEngine
    @Resource(name = "processEngine")
    private ProcessEngine processEngine;

    /**
     * 保存工作管理
     * @param workordermanage
     */
    @Override
    public void save(Workordermanage workordermanage) {
        workordermanageDao.save(workordermanage);
    }

    /**
     * 查询所有未启用的工作单
     * @return
     */
    @Override
    public List<Workordermanage> findListNotStart() {
        //创建离线查询对象
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Workordermanage.class);
        //添加查询条件，未启用
        detachedCriteria.add(Restrictions.eq("start", "0"));
        return workordermanageDao.findByCriteria(detachedCriteria);
    }

    /**
     * 启动物流配送流程
     * @param id
     */
    @Override
    public void start(String id) {
        //根据工作单ID查询工作单对象
        Workordermanage workordermanage = workordermanageDao.findById(id);
        //将状态修改为“1”，表示已启动
        workordermanage.setStart("1");
        //设置工作单对象到流程变量表
        String processDefinitionKey = "transfer";
        Map<String, Object> variables = new HashMap<String,Object>();
        variables.put("业务数据", workordermanage);
        processEngine.getRuntimeService().startProcessInstanceByKey(processDefinitionKey, variables);
    }

    /**
     * 更新工作单
     * @param workordermanage
     */
    @Override
    public void update(Workordermanage workordermanage) {
        workordermanageDao.update(workordermanage);
    }
}
