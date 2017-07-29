package cn.itcast.crm.service.impl;

import cn.itcast.crm.domain.Customer;
import cn.itcast.crm.service.CustomerService;
import cn.itcast.crm.utils.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

@SuppressWarnings(value = "all")
public class CustomerServiceImpl implements CustomerService {

	public List<Customer> findnoassociationCustomers() {
		Session session = HibernateUtils.openSession();
		session.beginTransaction();

		String hql = "from Customer where decidedzone_id is null";
		List<Customer> customers = session.createQuery(hql).list();

		session.getTransaction().commit();
		session.close();

		return customers;
	}

	public List<Customer> findhasassociationCustomers(String decidedZoneId) {
		Session session = HibernateUtils.openSession();
		session.beginTransaction();

		String hql = "from Customer where decidedzone_id = ?";
		List<Customer> customers = session.createQuery(hql).setParameter(0, decidedZoneId).list();

		session.getTransaction().commit();
		session.close();

		return customers;
	}

	public void assignCustomersToDecidedZone(Integer[] customerIds, String decidedZoneId) {
		Session session = HibernateUtils.openSession();
		session.beginTransaction();

		// 取消定区所有关联客户
		String hql2 = "update Customer set decidedzone_id=null where decidedzone_id=?";
		session.createQuery(hql2).setParameter(0, decidedZoneId).executeUpdate();

		// 进行关联
		String hql = "update Customer set decidedzone_id=? where id =?";
		if (customerIds != null) {
			for (Integer id : customerIds) {
				session.createQuery(hql).setParameter(0, decidedZoneId).setParameter(1, id).executeUpdate();
			}
		}
		session.getTransaction().commit();
		session.close();
	}

	//根据手机号查询客户信息
	@Override
	public Customer findCustomerByPhone(String phone) {
		//打开session
		Session session = HibernateUtils.openSession();
		//开启事务
		Transaction transaction = session.beginTransaction();

		//需要执行的SQL语句
		String hql = "from Customer where telephone=?";
		List<Customer> list = session.createQuery(hql).setParameter(0, phone).list();
		//判断该用户是否存在
		Customer customer = null;
		if (list != null && list.size() > 0) {
			customer = list.get(0);
		}

		//提交事务
		transaction.commit();
		//关闭session
		session.close();
		return customer;
	}

	//根据客户取件地址查询定区ID
	@Override
	public String findDecidedzoneidByAddress(String address) {
		//打开session
		Session session = HibernateUtils.openSession();
		//开启事务
		Transaction transaction = session.beginTransaction();

		//需要执行的HQL语句
		String hql = "select decidedzoneId from Customer where address=?";
		List<String> list = session.createQuery(hql).setParameter(0, address).list();
		//判断
		String decidedzoneid = null;
		if (list != null && list.size() > 0) {
			decidedzoneid = list.get(0);
		}
		//提交事务
		transaction.commit();
		//关闭session
		session.close();
		return decidedzoneid;
	}

	//根据客户ID查询定区ID
	@Override
	public String findDecidedzoneidByCustomerId(String customerId) {
		//打开session
		Session session = HibernateUtils.openSession();
		//开启事务
		Transaction transaction = session.beginTransaction();

		//需要执行的HQL语句
		String hql = "select decidedzoneId from Customer where id=?";
		List<String> list = session.createQuery(hql).setParameter(0, Integer.parseInt(customerId)).list();
		//判断
		String decidedzoneid = null;
		if (list != null && list.size() > 0) {
			decidedzoneid = list.get(0);
		}
		//提交事务
		transaction.commit();
		//关闭session
		session.close();
		return decidedzoneid;
	}
}
