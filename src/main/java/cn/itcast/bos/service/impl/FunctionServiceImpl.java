package cn.itcast.bos.service.impl;

import cn.itcast.bos.dao.IFunctionDao;
import cn.itcast.bos.domain.Function;
import cn.itcast.bos.domain.User;
import cn.itcast.bos.service.IFunctionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 权限管理functionService实现类
 * Created by lee on 2017/2/17.
 */
@SuppressWarnings(value = "all")
@Service(value = "functionService")
@Transactional
public class FunctionServiceImpl implements IFunctionService {
    //注入functionDao
    @Resource(name = "functionDao")
    private IFunctionDao functionDao;

    /**
     * 查询所有
     * @return
     */
    @Override
    public List<Function> list() {
        List<Function> functions = functionDao.findAll();
        return functions;
    }

    /**
     * 根据用户查询相应权限
     * @param user
     * @return
     */
    @Override
    public List<Function> findMenu(User user) {
        List<Function> list = null;
        //判断
        if ("admin".equals(user.getUsername())) {
            //查询所有的菜单
            list = functionDao.findAllMenu();
        } else {
            list = functionDao.findMenu(user.getId());
        }
        return list;
    }
}
