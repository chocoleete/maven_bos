package cn.itcast.bos.dao.impl;

import cn.itcast.bos.dao.INoticebillDao;
import cn.itcast.bos.dao.base.BaseDaoImpl;
import cn.itcast.bos.domain.Noticebill;
import org.springframework.stereotype.Repository;

/**
 * 通知单noticedao实现类
 * Created by lee on 2017/2/14.
 */
@SuppressWarnings(value = "all")
@Repository(value =


        "noticebillDao")
public class NoticebillDaoImpl extends BaseDaoImpl<Noticebill> implements INoticebillDao {

}
