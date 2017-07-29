package cn.itcast.bos.service;

import cn.itcast.bos.domain.Region;
import cn.itcast.bos.utils.PageBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 区域管理service接口
 * Created by lee on 2017/2/10.
 */
@SuppressWarnings(value = "all")
public interface IRegionService {
    /**
     * 批量保存
     * @param regions
     */
    public void saveBatch(ArrayList<Region> regions);

    /**
     * 分页查询
     * @param pageBean
     */
    public void pageQuery(PageBean pageBean);

    /**
     * 查询所有区域
     * @return
     */
    public List<Region> findAll();
}
