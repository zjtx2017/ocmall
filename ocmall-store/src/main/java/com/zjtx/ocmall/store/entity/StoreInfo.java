package com.zjtx.ocmall.store.entity;

import com.zjtx.ocmall.base.BaseEntity;

import java.math.BigDecimal;

/**
 * Author: zejitianxia1117
 * Date:2017/12/20
 */
public class StoreInfo extends BaseEntity {
  private Integer goodsId;
  private Integer leftCount;

  public Integer getGoodsId() {
    return goodsId;
  }

  public void setGoodsId(Integer goodsId) {
    this.goodsId = goodsId;
  }

  public Integer getLeftCount() {
    return leftCount;
  }

  public void setLeftCount(Integer leftCount) {
    this.leftCount = leftCount;
  }
}
