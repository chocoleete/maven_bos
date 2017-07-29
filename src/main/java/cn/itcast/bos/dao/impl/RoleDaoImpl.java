package cn.itcast.bos.dao.impl;

import cn.itcast.bos.dao.IRoleDao;
import cn.itcast.bos.dao.base.BaseDaoImpl;
import cn.itcast.bos.domain.Role;
import org.springframework.stereotype.Repository;

/**
 * 角色管理roleDao
 * Created by lee on 2017/2/18.
 */
@SuppressWarnings(value = "all")
@Repository(value = "roleDao")
public class RoleDaoImpl extends BaseDaoImpl<Role> implements IRoleDao {
}
