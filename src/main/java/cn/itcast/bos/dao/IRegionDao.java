package cn.itcast.bos.dao;

import cn.itcast.bos.dao.base.IBaseDao;
import cn.itcast.bos.domain.Region;

/**
 * 区域设置dao接口
 * Created by lee on 2017/2/10.
 */
public interface IRegionDao extends IBaseDao<Region> {
    /**
     * 更新或保存
     * @param region
     */
    public void saveOrUpdate(Region region);
}
