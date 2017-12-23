package com.zjtx.ocmall.framework.service.impl;

import com.zjtx.ocmall.framework.base.BaseEntity;
import com.zjtx.ocmall.framework.base.ResponseData;
import com.zjtx.ocmall.framework.dao.BaseDao;
import com.zjtx.ocmall.framework.service.IBaseService;

import java.util.List;

public class BaseService<M,QM> implements IBaseService<M,QM>{
    private BaseDao dao;

    public void setDao(BaseDao dao) {
        this.dao = dao;
    }

    @Override
    public Integer create(M m) {
        return dao.create(m);
    }

    @Override
    public Integer update(M m) {
        return dao.update(m);
    }

    @Override
    public Integer delById(int uuid) {
        return dao.delById(uuid);
    }

    @Override
    public ResponseData selectByConditionPage(QM qm) {
        ResponseData result = new ResponseData();
        List<M> resultList = dao.selectByConditionPage(qm);
        BaseEntity entity = (BaseEntity) qm;
        result.setStatus(10000);
        result.setMsg("success");
        result.setTotalCount(entity.getTotalCount());
        result.setDataList(resultList);
        return result;
    }

    @Override
    public M selectById(int uuid) {
        return (M) dao.selectById(uuid);
    }
}
