package cn.itcast.bos.dao.impl;

import cn.itcast.bos.dao.IWorkordermanageDao;
import cn.itcast.bos.dao.base.BaseDaoImpl;
import cn.itcast.bos.domain.Workordermanage;
import org.springframework.stereotype.Repository;

/**
 * 工作单管理workordermanageDao实现类
 * Created by lee on 2017/2/15.
 */
@SuppressWarnings(value = "all")
@Repository(value = "workordermanageDao")
public class WorkordermanageDaoImpl extends BaseDaoImpl<Workordermanage> implements IWorkordermanageDao {
}
