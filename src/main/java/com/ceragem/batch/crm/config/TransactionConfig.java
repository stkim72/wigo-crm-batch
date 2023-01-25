package com.ceragem.batch.crm.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 
 * <pre>
 * com.ceragem.crm.config - TransactionConfig.java
 * </pre>
 *
 * @ClassName : TransactionConfig
 * @Description : 트랜잭션 설정
 * @author : 김성태
 * @date : 2021. 1. 5.
 * @Version : 1.0
 * @Company : Copyright ⓒ wigo.ai. All Right Reserved
 */

//@SuppressWarnings("deprecation")
@Configuration
@EnableTransactionManagement
public class TransactionConfig {
	@Primary
	@Bean(name = "txManager")
	DataSourceTransactionManager crmTxManager(@Qualifier("crmDataSource") DataSource dataSource) throws Exception {
		DataSourceTransactionManager tx = new DataSourceTransactionManager(dataSource);
		tx.setDefaultTimeout(3600 *2);
		return tx;
	}

	@Bean(name = "txAiccManager")
	DataSourceTransactionManager aiccTxManager(@Qualifier("aiccDataSource") DataSource dataSource) throws Exception {
		DataSourceTransactionManager tx = new DataSourceTransactionManager(dataSource);
		tx.setDefaultTimeout(3600 * 2);
		return tx;
	}
}
