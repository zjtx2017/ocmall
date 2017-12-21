package com.zjtx.ocmall.order.entity;

import com.zjtx.ocmall.framework.base.BaseEntity;

import java.math.BigDecimal;

public class Order extends BaseEntity {
  private String customerId;
  private BigDecimal amount;
  private BigDecimal discountAmt;
  private Integer status;

  public String getCustomerId() {
    return customerId;
  }

  public void setCustomerId(String customerId) {
    this.customerId = customerId;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public BigDecimal getDiscountAmt() {
    return discountAmt;
  }

  public void setDiscountAmt(BigDecimal discountAmt) {
    this.discountAmt = discountAmt;
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }
}
