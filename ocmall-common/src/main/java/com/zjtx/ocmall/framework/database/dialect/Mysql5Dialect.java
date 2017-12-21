package com.zjtx.ocmall.framework.database.dialect;

public class Mysql5Dialect extends Dialect {
    protected static final String SQL_END_DELIMITER = ";";

    public Mysql5Dialect() {
    }

    public String getLimitString(String sql, boolean hasOffset) {
        return Mysql5PageHelper.getLimitString(sql, -1, -1);
    }

    public String getLimitString(String sql, int offset, int limit) {
        return Mysql5PageHelper.getLimitString(sql, offset, limit);
    }

    public boolean supportsLimit() {
        return true;
    }

    public String addLog(String sql) {
        return sql;
    }
}