package com.zjtx.ocmall.goods.entity;

import com.zjtx.ocmall.framework.base.BaseEntity;

/**
 * 商品实体类
 */
public class Goods extends BaseEntity {
   private String goodsName;
   private String imagePath;
   private String description;

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
