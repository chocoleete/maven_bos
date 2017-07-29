package cn.itcast.bos.service.impl;

import cn.itcast.bos.dao.IDecidedzoneDao;
import cn.itcast.bos.dao.ISubareaDao;
import cn.itcast.bos.domain.Decidedzone;
import cn.itcast.bos.domain.Subarea;
import cn.itcast.bos.service.IDecidedzoneService;
import cn.itcast.bos.utils.PageBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 定区管理service实现类
 * Created by lee on 2017/2/12.
 */
@SuppressWarnings(value = "all")
@Service(value = "decidedzoneService")
@Transactional
public class DecidedzoneServiceImpl implements IDecidedzoneService {
    //注入decidedzoneDao
    @Resource(name = "decidedzoneDao")
    private IDecidedzoneDao decidedzoneDao;

    //注入subareaDao
    @Resource(name = "subareaDao")
    private ISubareaDao subareaDao;

    /**
     * 保存定区
     * @param decidedzone
     * @param subareaid
     */
    @Override
    public void save(Decidedzone decidedzone, String subareaid) {
        //持久态的对象
        decidedzoneDao.save(decidedzone);
        //持久态对象
        Subarea subarea = subareaDao.findById(subareaid);
        subarea.setDecidedzone(decidedzone);
    }

    @Override
    public void pageQuery(PageBean pageBean) {
        decidedzoneDao.pageQuery(pageBean);
    }
}
