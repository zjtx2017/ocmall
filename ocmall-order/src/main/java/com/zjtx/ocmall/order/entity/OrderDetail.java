package com.zjtx.ocmall.order.entity;

import com.zjtx.ocmall.base.BaseEntity;

import java.math.BigDecimal;

/**
 * Author: zejitianxia1117
 * Date:2017/12/20
 */
public class OrderDetail extends BaseEntity{
  private Integer orderId;
  private Integer goodsId;
  private Integer count;
  private BigDecimal price;
  private BigDecimal totalAmt;
  private BigDecimal discountAmt;

  public Integer getOrderId() {
    return orderId;
  }

  public void setOrderId(Integer orderId) {
    this.orderId = orderId;
  }

  public Integer getGoodsId() {
    return goodsId;
  }

  public void setGoodsId(Integer goodsId) {
    this.goodsId = goodsId;
  }

  public Integer getCount() {
    return count;
  }

  public void setCount(Integer count) {
    this.count = count;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public BigDecimal getTotalAmt() {
    return totalAmt;
  }

  public void setTotalAmt(BigDecimal totalAmt) {
    this.totalAmt = totalAmt;
  }

  public BigDecimal getDiscountAmt() {
    return discountAmt;
  }

  public void setDiscountAmt(BigDecimal discountAmt) {
    this.discountAmt = discountAmt;
  }
}
