package cn.itcast.bos.dao;

import cn.itcast.bos.dao.base.IBaseDao;
import cn.itcast.bos.domain.Function;

import java.util.List;

/**
 * 权限管理Dao层接口
 * Created by lee on 2017/2/17.
 */
public interface IFunctionDao extends IBaseDao<Function> {
    //根据用户名id查询权限
    public List<Function> findFunctionByUserId(String userId);

    /**
     * 查询所有菜单
     * @return
     */
    public List<Function> findAllMenu();

    /**
     * 根据用户id查询菜单
     * @param id
     * @return
     */
    public List<Function> findMenu(String id);
}
