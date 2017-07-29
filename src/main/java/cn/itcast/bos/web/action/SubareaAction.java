package cn.itcast.bos.web.action;

import cn.itcast.bos.domain.Region;
import cn.itcast.bos.domain.Subarea;
import cn.itcast.bos.service.ISubareaService;
import cn.itcast.bos.utils.FileUtils;
import cn.itcast.bos.web.action.base.BaseAction;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * 分区管理action
 * Created by lee on 2017/2/11.
 */
@SuppressWarnings(value = "all")
@Controller(value = "subareaAction")
@Scope(value = "prototype")
public class SubareaAction extends BaseAction<Subarea> {
    //注入service
    @Resource(name = "subareaService")
    private ISubareaService subareaService;

    /**
     * 添加分区
     */
    public String save() {
        subareaService.save(model);
        return "list";
    }

    /**
     * 分页查询
     */
    public String pageQuery() {
        /**
         * 添加组合条件分页查询的逻辑
         */
        //得到关键字
        String addresskey = model.getAddresskey();
        //得到region对象
        Region region = model.getRegion();
        //获取查询subarea的离线查询对象
        DetachedCriteria subareaDetachedCriteria = pageBean.getDetachedCriteria();
        //判断关键字
        if (StringUtils.isNoneBlank(addresskey)) {
            //按照关键字进行模糊查询
            subareaDetachedCriteria.add(Restrictions.like("addresskey", "%" + addresskey + "%"));
        }
        //判断region
        if (region != null) {
            //获取查询region的离线查询对象
            DetachedCriteria regionDetachedCriteria = subareaDetachedCriteria.createCriteria("region");
            //得到省的值
            String province = region.getProvince();
            //得到市
            String city = region.getCity();
            //得到区
            String district = region.getDistrict();
            //判断省
            if (StringUtils.isNoneBlank(province)) {
                //按照省进行模糊查询
                regionDetachedCriteria.add(Restrictions.like("province", "%" + province + "%"));
            }
            //判断市
            if (StringUtils.isNoneBlank(city)) {
                //按照市进行模糊查询
                regionDetachedCriteria.add(Restrictions.like("city", "%" + city + "%"));
            }
            //判断区
            if (StringUtils.isNoneBlank(district)) {
                //按照区进行模糊查询
                regionDetachedCriteria.add(Restrictions.like("district", "%" + district + "%"));
            }
        }

        //查询数据库
        subareaService.pageQuery(pageBean);
        //将pageBean序列化成json数据，并写回页面
        String[] excludes = {"decidedzone", "detachedCriteria", "pageSize", "currentPage", "subareas"};
        this.writePageBean2json(pageBean, excludes);
        return NONE;
    }

    /**
     * 提供exportXls方法，使用apache POI导出分区数据到Excel文件中，并提供下载功能
     */
    public String exportXls() throws IOException {
        //查询所有的分区信息，得到一个list集合
        List<Subarea> list = subareaService.findAll();
        /**
         * 将list集合中的数据写入到一个excel文件中
         */
        //在内存中创建一个Excel文件
        HSSFWorkbook workbook = new HSSFWorkbook();
        //创建一个sheet“分区数据”
        HSSFSheet sheet = workbook.createSheet("分区数据");
        //在sheet中创建标题行headRow
        HSSFRow headRow = sheet.createRow(0);
        //给标题行headRow的每一列设置列名
        headRow.createCell(0).setCellValue("分区编号");
        headRow.createCell(1).setCellValue("区域编码");
        headRow.createCell(2).setCellValue("关键字");
        headRow.createCell(3).setCellValue("起始号");
        headRow.createCell(4).setCellValue("结束号");
        headRow.createCell(5).setCellValue("单双号");
        headRow.createCell(6).setCellValue("位置信息");//标题行所有列创建完成
        //遍历list集合，将数据写入到Excel表格中
        for (Subarea subarea : list) {
            //创建数据行，index为上一个有数据行的index+1
            HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
            //给每一列赋值
            dataRow.createCell(0).setCellValue(subarea.getId());
            dataRow.createCell(1).setCellValue(subarea.getRegion().getId());
            dataRow.createCell(2).setCellValue(subarea.getAddresskey());
            dataRow.createCell(3).setCellValue(subarea.getStartnum());
            dataRow.createCell(4).setCellValue(subarea.getEndnum());
            dataRow.createCell(5).setCellValue(subarea.getSingle());
            dataRow.createCell(6).setCellValue(subarea.getPosition());
        }
        //文件下载：两头，一流(输出流)
        ServletOutputStream outputStream = ServletActionContext.getResponse().getOutputStream();//得到输出流
        String filename = "分区数据.xls";//文件名
        //处理 filename中含有中文时下载文件无法显示文件名等问题
        filename = FileUtils.encodeDownloadFilename(filename, ServletActionContext.getRequest().getHeader("user-agent"));
        //设置头
        ServletActionContext.getResponse().setContentType(ServletActionContext.getServletContext().getMimeType(filename));
        ServletActionContext.getResponse().setHeader("content-disposition", "attachment;filename=" + filename);
        workbook.write(outputStream);

        return NONE;
    }

    /**
     * 异步查询分区数据，返回json
     */
    public String findSubareaByAjax() {
        //提供离线条件查询，包装查询条件
        DetachedCriteria subareaDetachedCriteria = DetachedCriteria.forClass(Subarea.class);
        //添加过滤条件，未分配到定区的分区
        subareaDetachedCriteria.add(Restrictions.isNull("decidedzone"));
        //查询数据库，得到subarea的list集合
        List<Subarea> list = subareaService.findByCondition(subareaDetachedCriteria);
        //将list集合转换成json数据，并响应到页面
        String[] excludes={"startnum","endnum","single","decidedzone","region"};
        this.writeList2json(list,excludes);
        return NONE;
    }
}
