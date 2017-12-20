package com.zjtx.ocmall.customer.dao;

import com.zjtx.ocmall.customer.entity.Customer;

/**
 * Author: zejitianxia1117
 * Date:2017/12/20
 */
public interface CustomerDao {
  /**
   * 添加用户
   * @param customer
   * @return
   */
  public Integer createCustomer(Customer customer);
}
