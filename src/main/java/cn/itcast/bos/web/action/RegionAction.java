package cn.itcast.bos.web.action;

import cn.itcast.bos.domain.Region;
import cn.itcast.bos.service.IRegionService;
import cn.itcast.bos.utils.PinYin4jUtils;
import cn.itcast.bos.web.action.base.BaseAction;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 区域设置Action
 * Created by lee on 2017/2/10.
 */
@Controller(value = "regionAction")
@Scope(value = "prototype")
@SuppressWarnings(value = "all")
public class RegionAction extends BaseAction<Region>{
    //注入regionService对象
    @Resource(name = "regionService")
    private IRegionService regionService;
    //属性封装接收上传的文件
    private File myFile;

    public void setMyFile(File myFile) {
        this.myFile = myFile;
    }

    /**
     * 查询所有的区域,查询结果需要转换成json语句写到页面
     */
    public String findListByAjax() {
        //查询数据库得到所有的region对象
        List<Region> list = regionService.findAll();
        /**
         * 序列化为json数据
         */
        JsonConfig jsonConfig = new JsonConfig();
        //列出不需要序列化的字段
        jsonConfig.setExcludes(new String[]{"subareas","province","city","district","postcode","shortcode","citycode"});
        //将list转换成jsonArray
        JSONArray jsonArray = JSONArray.fromObject(list, jsonConfig);
        //转换成json数据
        String json = jsonArray.toString();
        ServletActionContext.getResponse().setContentType("text/json;charset=UTF-8");
        try {
            //写回页面
            ServletActionContext.getResponse().getWriter().print(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return NONE;
    }

    /**
     * 分页查询方法
     */
    public String pageQuery() {
        //调用service层方法
        regionService.pageQuery(pageBean);
        //使用JsonConfig对象设置哪些属性不需要进行序列化
        this.writePageBean2json(pageBean, new String[]{"detachedCriteria", "pageSize", "currentPage","subareas"});
        return NONE;
    }

    /**
     * 区域批量导入
     */
    public String importXls() throws IOException {
//        System.out.println(myFile.getName()+";"+myFile+";"+myFile.getAbsolutePath());
        //声明导入是否成功的标识
        String flag = "1";
        //创建一个list对象，包装区域实体对象
        ArrayList<Region> regions = new ArrayList<Region>();
        try {
            //1、加载excel文件
            HSSFWorkbook hssfWorkbook = new HSSFWorkbook(new FileInputStream(myFile));
            //2、加载第一个sheet页
            HSSFSheet sheetAt = hssfWorkbook.getSheetAt(0);
            //3、循环sheet页，获得每一行数据
            for (Row cells : sheetAt) {
                Integer rowNum = cells.getRowNum();
                if (rowNum == 0) {
                    //跳过第一行
                    continue;
                }
                //4、获得当前行的每一列
                String id = cells.getCell(0).getStringCellValue();
                String province = cells.getCell(1).getStringCellValue();
                String city = cells.getCell(2).getStringCellValue();
                String district = cells.getCell(3).getStringCellValue();
                String postcode = cells.getCell(4).getStringCellValue();

                //将解析出来的数据包装成一个对象
                Region region = new Region(id, province, city, district, postcode);

                // 使用Pinyin4J工具生成简码和城市编码
                //简码 String shortcode
                String shortcode = province + city + district;
                String[] headByString = PinYin4jUtils.getHeadByString(shortcode);
                shortcode = StringUtils.join(headByString, "");

                //城市编码 String citycode
                String[] strings = PinYin4jUtils.stringToPinyin(city);
                String citycode = StringUtils.join(strings);

                //封装到对象中
                region.setShortcode(shortcode);
                region.setCitycode(citycode);

                //放入集合
                regions.add(region);
            }
            //批量保存数据
            regionService.saveBatch(regions);
            
        } catch (IOException e) {
            flag = "0";
            e.printStackTrace();
        }
        // 向客户端浏览器写回数据
        ServletActionContext.getResponse().getWriter().print(flag);
        return NONE;
    }
}
