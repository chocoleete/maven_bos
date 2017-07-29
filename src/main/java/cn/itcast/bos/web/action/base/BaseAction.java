package cn.itcast.bos.web.action.base;

import cn.itcast.bos.utils.PageBean;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * 抽取表现层
 * Created by lee on 2017/2/5.
 */
@SuppressWarnings(value = "all")
public class BaseAction<T> extends ActionSupport implements ModelDriven<T> {
    //声明模型对象 便于子类方便取值权限修饰符为protected
    protected T model;

    @Override
    public T getModel() {
        return model;
    }

    //抽取pageBean
    protected PageBean pageBean = new PageBean();

    public void setRows(Integer rows) {
        //封装从页面传来的每页显示的记录数
        pageBean.setPageSize(rows);
    }

    public void setPage(Integer page) {
        //封装从页面传来的当前页码
        pageBean.setCurrentPage(page);
    }

    //使用离线条件查询对象包装查询条件
    protected DetachedCriteria detachedCriteria;

    //在构造方法中动态得到实体的类型，并通过反射实例化model对象
    public BaseAction() {
        //获得父类类型(BaseAction<T>)，向下转型成ParameterizedType
        ParameterizedType genericSuperclass = null;
        Type type = this.getClass().getGenericSuperclass();
        //判断有没有泛型
        if (type instanceof ParameterizedType) {
            genericSuperclass = (ParameterizedType) type;
        } else {
            genericSuperclass = (ParameterizedType)this.getClass().getSuperclass().getGenericSuperclass();
        }
        //获得父类上的泛型数组
        Type[] actualTypeArguments = genericSuperclass.getActualTypeArguments();
        //由于只有一个泛型将其赋值给domainClass 获得实体类型
        Class<T> domainClass = (Class<T>) actualTypeArguments[0];
        //在构造方法中动态创建离线条件查询对象
        detachedCriteria = DetachedCriteria.forClass(domainClass);
        //封装离线条件查询对象
        pageBean.setDetachedCriteria(detachedCriteria);
        try {
            model = domainClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * pageBean中的list和total都有了
     * 使用json-lib将pageBean对象序列化为json数据
     */
    public void writePageBean2json(PageBean pageBean,String[] excludes) {
        //使用JsonConfig对象设置哪些属性不需要进行序列化
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(excludes);
        JSONObject jsonObject = JSONObject.fromObject(pageBean, jsonConfig);
        String json = jsonObject.toString();

        //解决编码问题
        ServletActionContext.getResponse().setContentType("text/json;charset=UTF-8");
        try {
            //将json数据写到页面
            ServletActionContext.getResponse().getWriter().print(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将list序列化为json数据，并响应到页面
     */
    public void writeList2json(List list, String[] excludes) {
        //使用JsonConfig对象，设置不需要进行序列化的属性
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(excludes);
        JSONArray jsonArray = JSONArray.fromObject(list, jsonConfig);
        String json = jsonArray.toString();

        //解决编码问题
        ServletActionContext.getResponse().setContentType("text/json;charset=UTF-8");
        try {
            //将json数据响应到页面
            ServletActionContext.getResponse().getWriter().print(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将Object对象序列化为json数据，并响应到页面
     */
    public void writeObject2json(Object object, String[] excludes) {
        //使用JsonConfig对象设置哪些属性不需要进行序列化
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(excludes);
        JSONObject jsonObject = JSONObject.fromObject(object, jsonConfig);
        String json = jsonObject.toString();

        //解决编码问题
        ServletActionContext.getResponse().setContentType("text/json;charset=UTF-8");
        try {
            //将json数据写到页面
            ServletActionContext.getResponse().getWriter().print(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
