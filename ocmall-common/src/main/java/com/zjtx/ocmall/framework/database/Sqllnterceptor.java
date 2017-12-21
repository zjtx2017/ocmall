package com.zjtx.ocmall.framework.database;

import com.zjtx.ocmall.framework.database.dialect.Dialect;
import com.zjtx.ocmall.framework.database.dialect.Mysql5Dialect;
import com.zjtx.ocmall.framework.database.dialect.OracleDialect;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.util.Properties;

@SuppressWarnings("all")
@Intercepts({ @Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class }) })
public class Sqllnterceptor implements Interceptor {

    protected final Logger logger = LoggerFactory.getLogger(getClass());
    private static ThreadLocal<RowBounds> rowBounds = new ThreadLocal<RowBounds>();

    public static RowBounds getRowBounds() {
        RowBounds rowBounds = Sqllnterceptor.rowBounds.get();
        Sqllnterceptor.rowBounds.remove();
        return rowBounds;
    }

    public static void setRowBounds(RowBounds rowBounds) {
        Sqllnterceptor.rowBounds.set(rowBounds);
    }

    public Object intercept(Invocation invocation) throws Throwable {
        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        BoundSql boundSql = statementHandler.getBoundSql();
        MetaObject metaStatementHandler = MetaObject.forObject(statementHandler, new DefaultObjectFactory(), new DefaultObjectWrapperFactory());
        RowBounds rowBounds = getRowBounds();
        if (rowBounds == null) {
            rowBounds = (RowBounds) metaStatementHandler.getValue("delegate.rowBounds");
        }
        Configuration configuration = (Configuration) metaStatementHandler.getValue("delegate.configuration");
        Dialect.Type databaseType = null;
        try {
            databaseType = Dialect.Type.valueOf(configuration.getVariables().getProperty("dialect").toUpperCase());
        } catch (Exception e) {
            // ignore
        }
        if (databaseType == null) {
            throw new RuntimeException("the value of the dialect property in configuration.xml is not defined : " + configuration.getVariables().getProperty("dialect"));
        }
        Dialect dialect = null;
        switch (databaseType) {
            case MYSQL:
                dialect = new Mysql5Dialect();
                break;
            case ORACLE:
                dialect = new OracleDialect();
                break;
        }

        String sql = (String) metaStatementHandler.getValue("delegate.boundSql.sql");
        if ((rowBounds != null) && (rowBounds != RowBounds.DEFAULT)) {
            sql = dialect.getLimitString(sql, rowBounds.getOffset(), rowBounds.getLimit());
        }

        metaStatementHandler.setValue("delegate.boundSql.sql", sql);
        metaStatementHandler.setValue("delegate.rowBounds.offset", RowBounds.NO_ROW_OFFSET);
        metaStatementHandler.setValue("delegate.rowBounds.limit", RowBounds.NO_ROW_LIMIT);
        //logger.debug("SQL : " + boundSql.getSql());
        return invocation.proceed();
    }

    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    public void setProperties(Properties properties) {}

}