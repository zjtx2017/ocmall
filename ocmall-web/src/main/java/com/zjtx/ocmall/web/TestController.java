package com.zjtx.ocmall.web;

import com.zjtx.ocmall.customer.dao.CustomerDao;
import com.zjtx.ocmall.customer.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Date;

@Controller
@RequestMapping("/test")
public class TestController {
    @Autowired
    private CustomerDao customerDao;
    @ResponseBody
    @RequestMapping("/test")
    public String test(){
        Customer customer = new Customer();
        customer.setUserName("aa1");
        customer.setRealName("bb2");
        customer.setPassword("1233");
        customer.setCreateTime(new Date());
        customer.setUpdateTime(new Date());
        customerDao.createCustomer(customer);
        Customer res = customerDao.selectById(1);
        System.out.println(res);
        return "123";
    }

}
