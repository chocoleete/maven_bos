package cn.itcast.bos.dao.impl;

import cn.itcast.bos.dao.IStaffDao;
import cn.itcast.bos.dao.base.BaseDaoImpl;
import cn.itcast.bos.domain.Staff;
import org.springframework.stereotype.Repository;

/**
 * 取派员管理Dao
 * Created by lee on 2017/2/9.
 */
@Repository(value = "staffDao")
public class StaffDaoImpl extends BaseDaoImpl<Staff> implements IStaffDao {
}
