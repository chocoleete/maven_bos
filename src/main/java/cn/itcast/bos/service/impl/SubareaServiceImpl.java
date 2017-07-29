package cn.itcast.bos.service.impl;

import cn.itcast.bos.dao.ISubareaDao;
import cn.itcast.bos.domain.Subarea;
import cn.itcast.bos.service.ISubareaService;
import cn.itcast.bos.utils.PageBean;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 分区管理service实现类
 * Created by lee on 2017/2/11.
 */
@SuppressWarnings(value = "all")
@Transactional
@Service(value = "subareaService")
public class SubareaServiceImpl implements ISubareaService{
    //注入subareaDao
    @Resource(name = "subareaDao")
    private ISubareaDao subareaDao;

    /**
     * 添加分区
     * @param subarea
     */
    @Override
    public void save(Subarea subarea) {
        subareaDao.save(subarea);
    }

    /**
     * 分页查询
     * @param pageBean
     */
    @Override
    public void pageQuery(PageBean pageBean) {
        subareaDao.pageQuery(pageBean);
    }

    /**
     * 查询所有
     * @return
     */
    @Override
    public List<Subarea> findAll() {
        return subareaDao.findAll();
    }

    /**
     * 离线条件查询
     * @param detachedCriteria
     * @return
     */
    @Override
    public List<Subarea> findByCondition(DetachedCriteria detachedCriteria) {
        return subareaDao.findByCriteria(detachedCriteria);
    }
}
