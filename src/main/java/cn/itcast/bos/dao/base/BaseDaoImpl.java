package cn.itcast.bos.dao.base;

import cn.itcast.bos.utils.PageBean;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import javax.annotation.Resource;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * 持久层的实现类
 * Created by lee on 2017/2/5.
 */

/**
 * 测试save()方法时出现如下的问题
 * Write operations are not allowed in read-only mode
 * (FlushMode.MANUAL): Turn your Session into FlushMode.COMMIT/AUTO or remove 'readOnly' marker from transaction definition.
 * 在BaseDao上加@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
 * 但这个注解会对持久态对象产生影响，让其变成游离态
 */
@SuppressWarnings("all")
public class BaseDaoImpl<T> extends HibernateDaoSupport implements IBaseDao<T> {
    //注入sessionFactory
    @Resource
    public void setSFactory(SessionFactory sessionFactory) {
        //调用父类的方法注入会话工厂对象
        super.setSessionFactory(sessionFactory);
    }
    //定义一个属性，表示实体的类型
    private Class<T> domainClass;

    //在构造方法中动态获取实体类型
    public BaseDaoImpl() {
        //获得父类类型，向下转型成ParameterizedType
        ParameterizedType genericSuperclass = (ParameterizedType) this.getClass().getGenericSuperclass();
        //获得父类上的泛型数组
        Type[] actualTypeArguments = genericSuperclass.getActualTypeArguments();
        //由于只有一个泛型将其赋值给domainClass 获得实体类型
        domainClass = (Class<T>) actualTypeArguments[0];
    }

    /**
     * 保存
     * @param entity
     */
    @Override
    public void save(T entity) {
        this.getHibernateTemplate().save(entity);
    }

    /**
     * 删除
     * @param entity
     */
    @Override
    public void delete(T entity) {
        this.getHibernateTemplate().delete(entity);
    }

    /**
     * 修改
     * @param entity
     */
    @Override
    public void update(T entity) {
        this.getHibernateTemplate().update(entity);
    }

    /**
     * 通过id查询
     * @param id
     * @return
     */
    @Override
    public T findById(Serializable id) {
        return this.getHibernateTemplate().get(domainClass, id);
    }

    /**
     * 查询所有
     * @return
     */
    @Override
    public List<T> findAll() {
        String hql = "FROM " + domainClass.getSimpleName();
        return (List<T>) this.getHibernateTemplate().find(hql);
    }

    /**
     * 离线条件查询
     * @param criteria
     * @return
     */
    @Override
    public List<T> findByCriteria(DetachedCriteria criteria) {
        return (List<T>) this.getHibernateTemplate().findByCriteria(criteria);
    }

    /**
     * 命名查询
     * @param queryName
     * @param args
     * @return
     */
    @Override
    public List<T> findByNamedQuery(String queryName, Object... args) {
        return (List<T>) this.getHibernateTemplate().findByNamedQuery(queryName, args);
    }

    /**
     * 命名增删改
     * @param queryName
     * @param args
     */
    @Override
    public void executeNamedQuery(String queryName, Object... args) {
        Session session = this.currentSession();
        Query query = session.getNamedQuery(queryName);
        if (args != null && args.length > 0) {
            int i = 0;
            for (Object arg : args) {
                query.setParameter(i++, arg);
            }
        }
        query.executeUpdate();
    }

    /**
     * 分页查询
     * @param pageBean
     */
    @Override
    public void pageQuery(PageBean pageBean) {
        //当前页面
        Integer currentPage = pageBean.getCurrentPage();
        //每页显示记录数
        Integer pageSize = pageBean.getPageSize();
        //离线条件查询对象
        DetachedCriteria detachedCriteria = pageBean.getDetachedCriteria();

        //查询总记录数,指定hibernate框架发出select count(id) from xxx where ...统计数据量的sql
        detachedCriteria.setProjection(Projections.rowCount());
        List<Long> list = (List<Long>) this.getHibernateTemplate().findByCriteria(detachedCriteria);
        Long total = list.get(0);//总记录数

        //查询当前页展示的数据集合,指定hibernate框架发出select * from xxx where
        detachedCriteria.setProjection(null);//重置
        //指定hibernate框架将从数据库是查询的每一行数据封装成一个实体类对象
        detachedCriteria.setResultTransformer(DetachedCriteria.ROOT_ENTITY);
        Integer firstResult = (currentPage - 1) * pageSize;
        Integer maxResult = pageSize;
        List recordList = this.getHibernateTemplate().findByCriteria(detachedCriteria, firstResult, maxResult);

        //将查询到的数据封装到pageBean
        pageBean.setTotal(total.intValue());
        pageBean.setRows(recordList);
    }
}
