package com.zjtx.ocmall.goods.entity;

import com.zjtx.ocmall.framework.base.BaseEntity;

import java.math.BigDecimal;

/**
 * Author: zejitianxia1117
 * Date:2017/12/20
 */
public class Goods extends BaseEntity {
  private String goodsName;
  private BigDecimal goodsPrice;
  private String goodsImg;
  private String goodsDesc;

  public String getGoodsName() {
    return goodsName;
  }

  public void setGoodsName(String goodsName) {
    this.goodsName = goodsName;
  }

  public BigDecimal getGoodsPrice() {
    return goodsPrice;
  }

  public void setGoodsPrice(BigDecimal goodsPrice) {
    this.goodsPrice = goodsPrice;
  }

  public String getGoodsImg() {
    return goodsImg;
  }

  public void setGoodsImg(String goodsImg) {
    this.goodsImg = goodsImg;
  }

  public String getGoodsDesc() {
    return goodsDesc;
  }

  public void setGoodsDesc(String goodsDesc) {
    this.goodsDesc = goodsDesc;
  }
}
