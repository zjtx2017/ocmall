package com.zjtx.ocmall.framework.database.dialect;

public abstract class Dialect {
    public Dialect() {
    }

    public abstract String getLimitString(String var1, int var2, int var3);

    public abstract String addLog(String var1);

    public static enum Type {
        MYSQL,
        ORACLE,
        MSSQL;

        private Type() {
        }
    }
}

