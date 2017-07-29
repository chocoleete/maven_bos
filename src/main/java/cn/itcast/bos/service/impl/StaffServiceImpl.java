package cn.itcast.bos.service.impl;

import cn.itcast.bos.dao.IStaffDao;
import cn.itcast.bos.domain.Staff;
import cn.itcast.bos.service.IStaffService;
import cn.itcast.bos.utils.PageBean;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 取派员管理Service
 * Created by lee on 2017/2/9.
 */
@Service(value = "staffService")
@Transactional
@SuppressWarnings("all")
public class StaffServiceImpl implements IStaffService {
    //注入staffDao
    @Resource(name = "staffDao")
    private IStaffDao staffDao;

    /**
     * 保存取派员
     * @param staff
     */
    @Override
    public void save(Staff staff) {
        staffDao.save(staff);
    }

    /**
     * 分页查询
     * @param pageBean
     */
    @Override
    public void pageQuery(PageBean pageBean) {
        staffDao.pageQuery(pageBean);
    }

    /**
     * 批量作废
     * @param ids
     */
    @Override
    public void delete(String ids) {
        //先将字符串转化成数组
        String[] idArr = ids.split(",");
        //遍历数组，将每一个id对应的数据作废
        for (int i = 0; i < idArr.length; i++) {
            String id = idArr[i];
            Staff staff = staffDao.findById(id);//持久态对象(有问题，既是持久层，为何在下一步操作后未发送sql语句)
            staff.setDeltag("1");//逻辑删除

//            staffDao.update(staff);//为何非要手动执行这一操作？？？原因在BaseDaoImpl的事务注解
        }
    }

    /**
     * 批量还原
     * @param ids
     */
    @Override
    public void restore(String ids) {
        //先将字符串转化成数组
        String[] idArr = ids.split(",");
        //遍历数组，将每一个id对应的数据还原
        for (int i = 0; i < idArr.length; i++) {
            String id = idArr[i];
            Staff staff = staffDao.findById(id);//持久态对象(有问题，既是持久层，为何在下一步操作后未发送sql语句)原因在BaseDaoImpl的事务注解
            staff.setDeltag("0");//逻辑还原

//            staffDao.update(staff);//为何非要手动执行这一操作？？？原因在BaseDaoImpl的事务注解
        }
    }

    /**
     * 修改取派员信息
     * @param staff
     */
    @Override
    public void update(Staff staff) {
        staffDao.update(staff);
    }

    /**
     * 离线条件查询
     * @param detachedCriteria
     * @return
     */
    @Override
    public List<Staff> findByCondition(DetachedCriteria detachedCriteria) {
        return staffDao.findByCriteria(detachedCriteria);
    }
}
