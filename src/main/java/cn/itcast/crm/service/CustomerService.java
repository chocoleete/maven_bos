package cn.itcast.crm.service;

import cn.itcast.crm.domain.Customer;

import java.util.List;

// 客户服务接口 
public interface CustomerService {
	// 未关联定区客户
	public List<Customer> findnoassociationCustomers();

	// 查询已经关联指定定区的客户
	public List<Customer> findhasassociationCustomers(String decidedZoneId);

	// 将未关联定区客户关联到定区上
	public void assignCustomersToDecidedZone(Integer[] customerIds, String decidedZoneId);

	//根据客户的手机号查询客户对象
	public Customer findCustomerByPhone(String phone);

	//根据客户取件地址查询定区id
	public String findDecidedzoneidByAddress(String address);

	//根据客户ID查询定区ID
	public String findDecidedzoneidByCustomerId(String customerId);
}
