package com.zjtx.ocmall.framework.service;

import com.zjtx.ocmall.framework.base.ResponseData;

import java.util.List;

public interface IBaseService<M, QM> {
    public Integer create(M m);

    public Integer update(M m);

    public Integer delById(int uuid);

    public ResponseData selectByConditionPage(QM qm);

    public M selectById(int uuid);
}
