package cn.itcast.bos.service.impl;

import cn.itcast.bos.dao.IDecidedzoneDao;
import cn.itcast.bos.dao.INoticebillDao;
import cn.itcast.bos.dao.IWorkbillDao;
import cn.itcast.bos.domain.Decidedzone;
import cn.itcast.bos.domain.Noticebill;
import cn.itcast.bos.domain.Staff;
import cn.itcast.bos.domain.Workbill;
import cn.itcast.bos.service.INoticebillService;
import cn.itcast.crm.service.CustomerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Timestamp;

/**
 * 通知单noticebillService实现类
 * Created by lee on 2017/2/14.
 */
@SuppressWarnings(value = "all")
@Service(value = "noticebillService")
@Transactional
public class NoticebillServiceImpl implements INoticebillService {
    //注入noticebillDao
    @Resource(name = "noticebillDao")
    private INoticebillDao noticebillDao;

    //注入customerService
    @Resource(name = "customerService")
    private CustomerService customerService;

    //注入decidedzoneDao
    @Resource(name = "decidedzoneDao")
    private IDecidedzoneDao decidedzoneDao;

    //注入workbillDao
    @Resource(name = "workbillDao")
    private IWorkbillDao workbillDao;

    /**
     * 保存业务通知单
     * @param noticebill
     */
    @Override
    public void save(Noticebill noticebill) {
        //调用noticeDao保存
        noticebillDao.save(noticebill);
        //根据客户的取件地址查询定区ID
//        String decidedzoneid = customerService.findDecidedzoneidByAddress(noticebill.getPickaddress());
        //根据客户ID查询定区ID
        String decidedzoneid = customerService.findDecidedzoneidByCustomerId(noticebill.getCustomerId());
        //尝试自动分单，判断
        if (decidedzoneid != null) {
            //可以自动分单
            //根据decidedzoneid查询定区
            Decidedzone decidedzone = decidedzoneDao.findById(decidedzoneid);
            //根据定区查询取派员
            Staff staff = decidedzone.getStaff();
            //业务通知单关联取派员
            noticebill.setStaff(staff);
            //设置分单类型为“自动”
            noticebill.setOrdertype("自动");
            //为当前取派员创一个工单
            Workbill workbill = new Workbill();
            //设置追单次数
            workbill.setAttachbilltimes(0);
            //设置工单生成时间
            workbill.setBuildtime(new Timestamp(System.currentTimeMillis()));
            //设置工单关联业务通知单
            workbill.setNoticebill(noticebill);
            //设置取件状态
            workbill.setPickstate("未取件");
            //设置备注信息
            workbill.setRemark(noticebill.getRemark());
            //工单关联取派员
            workbill.setStaff(staff);
            //设置工单类型：新，追，改，销
            workbill.setType("新");
            //保存工单
            workbillDao.save(workbill);
            //调用webService完成当前取派员发送短信
        } else {
            //不能自动分单，转为人工分单
            noticebill.setOrdertype("人工");
        }
    }
}
