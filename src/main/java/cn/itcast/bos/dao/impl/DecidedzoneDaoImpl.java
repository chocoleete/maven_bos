package cn.itcast.bos.dao.impl;

import cn.itcast.bos.dao.IDecidedzoneDao;
import cn.itcast.bos.dao.base.BaseDaoImpl;
import cn.itcast.bos.domain.Decidedzone;
import org.springframework.stereotype.Repository;

/**
 * 定区管理dao实现类
 * Created by lee on 2017/2/12.
 */
@SuppressWarnings(value = "all")
@Repository(value = "decidedzoneDao")
public class DecidedzoneDaoImpl extends BaseDaoImpl<Decidedzone> implements IDecidedzoneDao {
}
