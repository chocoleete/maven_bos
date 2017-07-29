package cn.itcast.bos.service;

import cn.itcast.bos.domain.Role;

import java.util.List;

/**
 * 角色管理接口
 * Created by lee on 2017/2/18.
 */
public interface IRoleService {
    /**
     * 添加角色
     * @param role
     * @param ids
     */
    public void save(Role role, String ids);

    /**查询所有角色
     * @return
     */
    public List<Role> list();
}
