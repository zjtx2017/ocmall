package com.zjtx.ocmall.store.entity;

import com.zjtx.ocmall.framework.base.BaseEntity;

/**
 * Author: zejitianxia1117
 * Date:2017/12/20
 */
public class StoreInfo extends BaseEntity {
  private Integer leftCount;
  private Integer goodsId;

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
