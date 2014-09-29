package com.asiainfo.dbx.ln.component.dao.mybatis;

import com.asiainfo.dbx.ln.component.dao.RowLimit;
import com.asiainfo.dbx.ln.component.dao.Utils.DasUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.ibatis.executor.statement.BaseStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.VendorDatabaseIdProvider;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.util.Properties;
import java.util.regex.Pattern;

/**
 * 分页拦截器
 * 把执行的SQL,转换为分页查询sql
 * Created by yanlei on 2014/8/4.
 */

@Intercepts(@Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class }))

public class PaginationInterceptor implements Interceptor {
    private final Logger logger = LoggerFactory.getLogger(PaginationInterceptor.class);
    private final static String SQL_SELECT_REGEX = "(?is)^\\s*SELECT.*$";
    private final static String SQL_COUNT_REGEX = "(?is)^\\s*SELECT\\s+COUNT\\s*\\(\\s*(?:\\*|\\w+)\\s*\\).*$";
    Pattern selectPattern = Pattern.compile(SQL_SELECT_REGEX);
    Pattern selectCountPattern = Pattern.compile(SQL_COUNT_REGEX);
    VendorDatabaseIdProvider vendorDatabaseIdProvider  = new VendorDatabaseIdProvider();

    public Object intercept(Invocation inv) throws Throwable {

        StatementHandler statementHandler = (StatementHandler) inv.getTarget();
        Object [] args =  inv.getArgs();
        BoundSql boundSql = statementHandler.getBoundSql();
        String sql = boundSql.getSql();
        if (StringUtils.isBlank(sql)) {
            return inv.proceed();
        }

        // 只有为select查询语句时才进行下一步
        if (selectPattern.matcher(sql).matches()&&!(selectCountPattern.matcher(sql).matches())) {

            BaseStatementHandler baseStatementHandler = (BaseStatementHandler)FieldUtils.readField(statementHandler, "delegate", true);
            // 反射获取 RowBounds 对象。
            RowBounds rowBounds = (RowBounds) FieldUtils.readField(baseStatementHandler,"rowBounds", true);

            // 分页参数存在且不为默认值时进行分页SQL构造
            if (rowBounds != null && rowBounds != RowBounds.DEFAULT) {
                Connection connection = (Connection)args[0];
                RowLimit rowLimit =  new RowLimit();
                rowLimit.setLimit(rowBounds.getLimit());
                rowLimit.setOffset(rowBounds.getOffset());
                FieldUtils.writeField(boundSql, "sql", DasUtils.paginationSql(connection, sql, rowLimit),true);
                logger.info("origin sql>>>>>:{}",sql);
                logger.info("new sql>>>>>:{}", boundSql.getSql());
                // 一定要还原否则将无法得到下一组数据(第一次的数据被缓存了)
                FieldUtils.writeField(rowBounds, "offset", RowBounds.NO_ROW_OFFSET, true);
                FieldUtils.writeField(rowBounds, "limit",  RowBounds.NO_ROW_LIMIT, true);
            }
        }
        return inv.proceed();
    }

    //@Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }
    //@Override
    public void setProperties(Properties arg0) {

    }
}
