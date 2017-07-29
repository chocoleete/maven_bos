package cn.itcast.bos.service.impl;

import cn.itcast.bos.dao.IRegionDao;
import cn.itcast.bos.domain.Region;
import cn.itcast.bos.service.IRegionService;
import cn.itcast.bos.utils.PageBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 区域设置Service实现类
 * Created by lee on 2017/2/10.
 */
@Service(value = "regionService")
@Transactional
@SuppressWarnings(value = "all")
public class RegionServiceImpl implements IRegionService {
    //注入regionDao对象
    @Resource(name = "regionDao")
    private IRegionDao regionDao;

    /**
     * 批量保存
     * @param regions
     */
    @Override
    public void saveBatch(ArrayList<Region> regions) {
        //遍历集合
        for (Region region : regions) {
            regionDao.saveOrUpdate(region);
        }
    }

    /**
     * 分页显示
     * @param pageBean
     */
    @Override
    public void pageQuery(PageBean pageBean) {
        regionDao.pageQuery(pageBean);
    }

    /**
     * 查询所有区域
     * @return
     */
    @Override
    public List<Region> findAll() {
        List<Region> list = regionDao.findAll();
        return list;
    }
}
