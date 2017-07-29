package cn.itcast.bos.dao.impl;

import cn.itcast.bos.dao.IWorkbillDao;
import cn.itcast.bos.dao.base.BaseDaoImpl;
import cn.itcast.bos.domain.Workbill;
import org.springframework.stereotype.Repository;

/**
 * 工单Dao层实现类workbillDao
 * Created by lee on 2017/2/14.
 */
@SuppressWarnings(value = "all")
@Repository(value = "workbillDao")
public class WorkbillDaoImpl extends BaseDaoImpl<Workbill> implements IWorkbillDao {
}
