package com.ceragem.batch.crm.config;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class CrmBatchConfig extends DefaultBatchConfigurer {
	@Resource(name = "txManager")
	PlatformTransactionManager transactionManager;

	@Override
	public void setDataSource(DataSource dataSource) {
		// 여기를 비워놓는다
	}

	@Override
	public PlatformTransactionManager getTransactionManager() {

		return transactionManager;
	}
}
