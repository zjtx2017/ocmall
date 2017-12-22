package com.zjtx.ocmall.framework.database.dialect;

import java.sql.Connection;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.ibatis.executor.statement.PreparedStatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.session.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CountHelper {
    private static final String MAPPED_STATEMENT = "mappedStatement";
    private static Logger logger = LoggerFactory.getLogger(CountHelper.class);

    /**
     * 保存计算总行数的值
     */
    private static ThreadLocal<Integer> totalRowCountHolder = new ThreadLocal<Integer>();

    /**
     * 获取查询对象的总行数
     * @param sql  获取总行数的SQL
     * @param statementHandler
     * @param configuration
     * @param boundSql
     * @param connection
     * @throws Throwable
     */
    public static void getCount(String sql, PreparedStatementHandler statementHandler,
                         Configuration configuration, BoundSql boundSql,
                         Connection connection)
            throws Throwable{
        Object parameterObject = statementHandler.getParameterHandler().getParameterObject();
        if (logger.isDebugEnabled()) {
            logger.debug("Total count SQL [{}] ", sql);
            logger.debug("Total count Parameters: {} ", parameterObject);
        }


        PreparedStatement countStmt = null;
        ResultSet rs = null;
        try {
            countStmt = connection.prepareStatement(sql);
            final BoundSql countBS = new BoundSql(configuration, sql,
                    boundSql.getParameterMappings(), parameterObject);

            MappedStatement mappedStatement = (MappedStatement) FieldUtils.readField(statementHandler, MAPPED_STATEMENT, true);

            DefaultParameterHandler handler =
                    new DefaultParameterHandler(mappedStatement, parameterObject, countBS);
            handler.setParameters(countStmt);

            rs = countStmt.executeQuery();
            int count = 0;
            if (rs.next()) {
                count = rs.getInt(1);
            }
            if (logger.isDebugEnabled()) {
                logger.debug("Total count: {}", count);
            }
            totalRowCountHolder.set(count);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } finally {
                if (countStmt != null) {
                    countStmt.close();
                }
            }
        }
    }

    /**
     * 获取当前线程对应的分页查询的总行数
     *
     * @return
     */
    public static int getTotalRowCount() {
        return totalRowCountHolder.get();
    }
}
