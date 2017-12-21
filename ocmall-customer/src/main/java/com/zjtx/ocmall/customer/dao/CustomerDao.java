package com.zjtx.ocmall.customer.dao;

import com.zjtx.ocmall.customer.entity.Customer;
import com.zjtx.ocmall.customer.entity.CustomerQuery;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Author: zejitianxia1117
 * Date:2017/12/20
 */
@Component
public interface CustomerDao {
  /**
   * 添加用户
   * @param customer
   * @return
   */
  public Integer createCustomer(Customer customer);

  public Integer updateCustomer(Customer customer);

  public Integer delById(Integer id);

  public Customer selectById(Integer id);

  public List<Customer> getByCondition(CustomerQuery cuq);
}
