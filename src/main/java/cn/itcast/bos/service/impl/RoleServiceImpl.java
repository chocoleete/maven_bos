package cn.itcast.bos.service.impl;

import cn.itcast.bos.dao.IRoleDao;
import cn.itcast.bos.domain.Function;
import cn.itcast.bos.domain.Role;
import cn.itcast.bos.service.IRoleService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.identity.Group;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 角色管理roleService实现类
 * Created by lee on 2017/2/18.
 */
@SuppressWarnings(value = "all")
@Service(value = "roleService")
@Transactional
public class RoleServiceImpl implements IRoleService {
    //注入roleDao
    @Resource(name = "roleDao")
    private IRoleDao roleDao;

    //注入流程引擎对象
    @Resource(name = "processEngine")
    private ProcessEngine processEngine;

    /**
     * 角色同步
     * 添加角色时，将数据同步到zjs_bos.ACT_ID_GROUP表中
     * @param role
     * @param ids
     */
    @Override
    public void save(Role role, String ids) {
        roleDao.save(role);//持久态对象

        //创建Group对象
        Group groupEntity = new GroupEntity();
        //设置ID,将角色名设置成流程实例ID
        groupEntity.setId(role.getName());
        //保存到zjs_bos.ACT_ID_GROUP表中
        processEngine.getIdentityService().saveGroup(groupEntity);

        //遍历ids
        String[] functionIds = ids.split(",");//将字符串分割成id数组
        for (String id : functionIds) {
            Function function = new Function();//创建function对象
            function.setId(id);//设置function的id
            role.getFunctions().add(function);//将function添加到role中的functions集合中
        }
    }

    @Override
    public List<Role> list() {
        return roleDao.findAll();
    }
}
