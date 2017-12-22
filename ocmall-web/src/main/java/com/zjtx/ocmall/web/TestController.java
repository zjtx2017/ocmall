package com.zjtx.ocmall.web;

import com.zjtx.ocmall.customer.dao.CustomerDao;
import com.zjtx.ocmall.customer.entity.Customer;
import com.zjtx.ocmall.customer.entity.CustomerQuery;
import com.zjtx.ocmall.framework.base.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/tests")
public class TestController {
    @Autowired
    private CustomerDao customerDao;
    @ResponseBody
    @RequestMapping("/test")
    public ResponseData test(CustomerQuery query){
        ResponseData responseData = new ResponseData();
        List<Customer> customerList = customerDao.selectByConditionPage(query);
        responseData.setDataList(customerList);
        responseData.setMsg("success");
        responseData.setStatus(10000);
        responseData.setTotalCount(query.getTotalCount());
        return responseData;
    }

}
