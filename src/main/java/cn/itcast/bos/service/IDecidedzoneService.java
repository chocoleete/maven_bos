package cn.itcast.bos.service;

import cn.itcast.bos.domain.Decidedzone;
import cn.itcast.bos.utils.PageBean;

/**
 * 定区管理service接口
 * Created by lee on 2017/2/12.
 */
@SuppressWarnings(value = "all")
public interface IDecidedzoneService {
    /**
     * 保存
     * @param decidedzone
     * @param subareaid
     */
    public void save(Decidedzone decidedzone, String subareaid);

    /**
     * 分页查询
     * @param pageBean
     */
    public void pageQuery(PageBean pageBean);
}
