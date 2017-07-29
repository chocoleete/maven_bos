package cn.itcast.bos.service;

import cn.itcast.bos.domain.Noticebill;

/**
 * 通知单notilebillService接口
 * Created by lee on 2017/2/14.
 */
public interface INoticebillService {
    //保存业务通知单
    public void save(Noticebill noticebill);
}
