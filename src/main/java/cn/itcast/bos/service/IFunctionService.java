package cn.itcast.bos.service;

import cn.itcast.bos.domain.Function;
import cn.itcast.bos.domain.User;

import java.util.List;

/**
 * 权限管理接口IFunctionService
 * Created by lee on 2017/2/17.
 */
public interface IFunctionService {
    /**
     * 查询所有
     * @return
     */
    public List<Function> list();

    /**
     * 根据用户查询相应权限
     * @param user
     * @return
     */
    public List<Function> findMenu(User user);
}
