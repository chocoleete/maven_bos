package cn.itcast.bos.dao.impl;

import cn.itcast.bos.dao.IRegionDao;
import cn.itcast.bos.dao.base.BaseDaoImpl;
import cn.itcast.bos.domain.Region;
import org.springframework.stereotype.Repository;

/**
 * 区域管理dao
 * Created by lee on 2017/2/10.
 */
@SuppressWarnings(value = "all")
@Repository(value = "regionDao")
public class RegionDaoImpl extends BaseDaoImpl<Region> implements IRegionDao {
    /**
     * 更新或保存
     * @param region
     */
    @Override
    public void saveOrUpdate(Region region) {
        this.getHibernateTemplate().saveOrUpdate(region);
    }
}
