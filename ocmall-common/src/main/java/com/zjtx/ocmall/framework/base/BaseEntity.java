package com.zjtx.ocmall.framework.base;

import java.io.Serializable;
import java.util.Date;

/**
 * 基础类
 */
public class BaseEntity implements Serializable {
    private Integer id;
    private Integer page;
    private Integer rows;
    private Integer totalCount;
    private Date createTime;
    private Date updateTime;
    public static final BaseEntity BASEENTITY_DEFAULT = new BaseEntity();

    public BaseEntity(){
        this.page = null;
        this.rows = null;
        this.totalCount = null;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }
}
