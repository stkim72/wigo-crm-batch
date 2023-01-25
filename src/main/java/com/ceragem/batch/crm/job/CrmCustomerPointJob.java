package com.ceragem.batch.crm.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ceragem.batch.crm.common.util.CrmJobListener;
import com.ceragem.batch.crm.tasklet.CustomerPointInfoTask;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class CrmCustomerPointJob {

	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;
	private final CustomerPointInfoTask pointTask;
	@Autowired
	CrmJobListener jobListener;

	@Bean("customerPointJob")
	Job customerPointJob() {
		log.debug("customerPointJob");
		return jobBuilderFactory.get("customerPointJob").start(stepPoint()).listener(jobListener).build();
	}

	@Bean("stepPoint")
	Step stepPoint() {
		log.debug("stepPoint");
		return stepBuilderFactory.get("stepPoint").tasklet(pointTask).build();
	}

}
