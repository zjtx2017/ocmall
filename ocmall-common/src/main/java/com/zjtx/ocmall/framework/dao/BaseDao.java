package com.zjtx.ocmall.framework.dao;

import java.util.List;

/**
 * 数据库操作基类
 * @param <M>
 * @param <QM>
 */
public interface BaseDao<M,QM> {
    public Integer create(M m);

    public Integer update(M m);

    public Integer delById(int uuid);

    public List<M> selectByConditionPage(QM qm);

    public M selectById(int uuid);
}
