package com.ceragem.batch.crm.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ceragem.batch.crm.common.util.CrmJobListener;
import com.ceragem.batch.crm.tasklet.CustTypeJobTask;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class CrmCustTypeJob {

	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;
	private final CustTypeJobTask custTypeJobTask;
	
	@Autowired
	CrmJobListener jobListener;

	@Bean("custTypeJob")
	Job custTypeJob() {
		log.debug("custTypeJob");
		return jobBuilderFactory.get("custTypeJob").listener(jobListener).start(stepCustType()).build();
	}

	@Bean("stepCustType")
	Step stepCustType() {
		log.debug("stepCustType");
		return stepBuilderFactory.get("stepCustType").tasklet(custTypeJobTask).build();
	}

}
