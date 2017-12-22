package com.zjtx.ocmall.customer.dao;

import com.zjtx.ocmall.customer.entity.Customer;
import com.zjtx.ocmall.customer.entity.CustomerQuery;
import com.zjtx.ocmall.framework.dao.BaseDao;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Author: zejitianxia1117
 * Date:2017/12/20
 */
@Component
public interface CustomerDao<Customer> extends BaseDao<Customer,CustomerQuery> {

}
