package cn.itcast.bos.service;

import cn.itcast.bos.domain.Workordermanage;

import java.util.List;

/**
 * 工单管理接口
 * Created by lee on 2017/2/15.
 */
@SuppressWarnings(value = "all")
public interface IWorkordermanageService {
    /**
     * 保存工单管理接口
     * @param workordermanage
     */
    public void save(Workordermanage workordermanage);

    /**
     * 查询未启用的工作单
     * @return
     */
    public List<Workordermanage> findListNotStart();

    /**
     * 启动流程
     * @param id
     */
    public void start(String id);

    /**
     * 更新工作单
     * @param workordermanage
     */
    public void update(Workordermanage workordermanage);
}
