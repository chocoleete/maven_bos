package cn.itcast.bos.dao.impl;

import cn.itcast.bos.dao.ISubareaDao;
import cn.itcast.bos.dao.base.BaseDaoImpl;
import cn.itcast.bos.domain.Subarea;
import org.springframework.stereotype.Repository;

/**
 * 分区管理dao实现类
 * Created by lee on 2017/2/11.
 */
@SuppressWarnings(value = "all")
@Repository(value = "subareaDao")
public class SubareaDaoImpl extends BaseDaoImpl<Subarea> implements ISubareaDao {
}
