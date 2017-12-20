package com.zjtx.ocmall.customer.entity;

import com.zjtx.ocmall.base.BaseEntity;

/**
 * 客戶管理实体类
 */
public class Customer extends BaseEntity {
    private String userName;//用户名
    private String realName;//真实姓名
    private String password;//密码

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
