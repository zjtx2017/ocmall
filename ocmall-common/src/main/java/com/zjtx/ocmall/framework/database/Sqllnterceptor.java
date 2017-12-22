package com.zjtx.ocmall.framework.database;

import java.sql.Connection;
import com.zjtx.ocmall.framework.base.BaseEntity;
import com.zjtx.ocmall.framework.database.dialect.*;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.ibatis.executor.statement.PreparedStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

@SuppressWarnings("all")
@Intercepts({ @Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class }) })
public class Sqllnterceptor implements Interceptor {
    private static final String PATTERN_DEFAULT = "^.*Page.*$";
    public static final String DELEGATE = "delegate";
    public static final String CONFIGURATION = "configuration";
    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    private String pattern = null;
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    private static ThreadLocal<RowBounds> rowBounds = new ThreadLocal<RowBounds>();
    private BaseEntity entity = new BaseEntity();

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
        MappedStatement mappedStatement = (MappedStatement) metaStatementHandler.getValue("delegate.mappedStatement");
        pattern = pattern==null?PATTERN_DEFAULT:pattern;
        if(!mappedStatement.getId().matches(pattern)){
            return invocation.proceed();
        }
        //计算总数需要的变量值 start
        PreparedStatementHandler preparedStatHandler =
                (PreparedStatementHandler) FieldUtils.readField(statementHandler, DELEGATE, true);
        final Object[] queryArgs = invocation.getArgs();
        Connection connection = (Connection) queryArgs[0];
        Configuration configuration = (Configuration) metaStatementHandler.getValue("delegate.configuration");
        //计算总数需要的变量 end
        BaseEntity entity = (BaseEntity) metaStatementHandler.getValue("delegate.boundSql.parameterObject");
        if(entity!=null && entity != BaseEntity.BASEENTITY_DEFAULT){
            int offset = (entity.getPage()-1)*entity.getRows();
            RowBounds rowBoundss = new RowBounds(offset,entity.getRows());
            setRowBounds(rowBoundss);
        }
        RowBounds rowBounds = getRowBounds();
        if (rowBounds == null) {
            rowBounds = (RowBounds) metaStatementHandler.getValue("delegate.rowBounds");
        }
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
        String countSql = dialect.getTotalCountString(sql);
        System.out.println("countSql----->>>>>>"+countSql);
        CountHelper.getCount(countSql,preparedStatHandler,configuration,boundSql,connection);
        entity.setTotalCount(CountHelper.getTotalRowCount());
        System.out.println("totalCount-----?>>>>>"+entity.getTotalCount());
        if ((rowBounds != null) && (rowBounds != RowBounds.DEFAULT)) {
            sql = dialect.getLimitString(sql, rowBounds.getOffset(), rowBounds.getLimit());
        }
        metaStatementHandler.setValue("delegate.boundSql.sql", sql);
        metaStatementHandler.setValue("delegate.rowBounds.offset", RowBounds.NO_ROW_OFFSET);
        metaStatementHandler.setValue("delegate.rowBounds.limit", RowBounds.NO_ROW_LIMIT);
        logger.debug("SQL : " + boundSql.getSql());
        return invocation.proceed();
    }

    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    public void setProperties(Properties properties) {}

}