package com.ceragem.batch.crm.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ceragem.batch.crm.common.util.CrmJobListener;
import com.ceragem.batch.crm.tasklet.PromEventCoupnPointTask;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class CrmPromEventJob {

	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;
	private final PromEventCoupnPointTask promEventCoupnPointTask;	// 프로모션 혜택지급
				  	
	@Autowired
	CrmJobListener jobListener;

	@Bean("promEventJob")
	Job coupnCancelJob() {
		log.debug("promEventJob");
		return jobBuilderFactory.get("promEventJob").listener(jobListener).start(stepPromEventJobIssue()).build();
	}

	@Bean("stepPromEventJobIssue")
	Step stepPromEventJobIssue() {
		log.debug("stepPromEventJobIssue");
		return stepBuilderFactory.get("stepPromEventJobIssue").tasklet(promEventCoupnPointTask).build();
	}

}
