package com.ceragem.batch.crm.common.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * <pre>
 * com.ceragem.batch.crm.common.util - CrmSqlLogger.java
 * </pre>
 *
 * @ClassName : CrmSqlLogger
 * @Description : sql log
 * @author : 김성태
 * @date : 2021. 4. 23.
 * @Version : 1.0
 * @Company : Copyright ⓒ wigo.ai. All Right Reserved
 */
@Slf4j(topic = "jdbc.sqlonly")
@Intercepts({ @Signature(type = Executor.class, method = "update", args = { MappedStatement.class, Object.class })
, @Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class,  RowBounds.class, ResultHandler.class})
, @Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class,  RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class })
})
@Component("crmSqlLogger")
public class CrmSqlLogger implements Interceptor {

	
	
	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		
		Object[] args = invocation.getArgs();
		MappedStatement ms = (MappedStatement) args[0];
		Object parameterObject = (Object) args[1];
		
		BoundSql boundSql = ms.getBoundSql(parameterObject);
		String sql = getSql(boundSql, Utilities.beanToMap(parameterObject));
		if(Utilities.isNotEmpty(sql))
		{
			sql = "\n               " +  sql.replace("\\n", "\n");
			log.debug(sql);
		}
		return invocation.proceed();
	}

	private String getSql(BoundSql boundSql, Object parameter) {
		try {

			StringBuilder sql = new StringBuilder(boundSql.getSql());

			if (parameter != null) {
				List<Object> valueList = new ArrayList<Object>();
				for (ParameterMapping parameterMapping : boundSql.getParameterMappings()) {
					String property = parameterMapping.getProperty();
					Object value = null;
					if (boundSql.hasAdditionalParameter(property)) {
						value = boundSql.getAdditionalParameter(property);
					} else if (parameter != null) {
						value = PropertyUtils.getProperty(parameter, property);
						valueList.add(value);
					}
				}

				for (Object value : valueList) {
					int start = sql.indexOf("?");
					int end = start + 1;
					if (value == null) {
						sql.replace(start, end, "NULL");
					} else if (value instanceof String) {
						sql.replace(start, end, "'" + value.toString() + "'");
					} else {
						sql.replace(start, end, value.toString());
					}
				}
			}
			return sql.toString();
		} catch (Exception ex) {
			return null;
		}
	}

}
