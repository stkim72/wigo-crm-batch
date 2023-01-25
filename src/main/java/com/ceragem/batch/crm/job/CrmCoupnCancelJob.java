package com.ceragem.batch.crm.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ceragem.batch.crm.common.util.CrmJobListener;
import com.ceragem.batch.crm.tasklet.CoupnCancelReSendTask;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class CrmCoupnCancelJob {

	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;
	private final CoupnCancelReSendTask coupnCancelReSendTask;	// 만료7일전 재 알림
	
	@Autowired
	CrmJobListener jobListener;

	@Bean("coupnCancelJob")
	Job coupnCancelJob() {
		log.debug("coupnCancelJob");
		return jobBuilderFactory.get("coupnCancelJob").listener(jobListener).start(stepCoupnCancelIssue()).build();
	}

	@Bean("stepCoupnCancelIssue")
	Step stepCoupnCancelIssue() {
		log.debug("stepCoupnCancelIssue");
		return stepBuilderFactory.get("stepCoupnCancelIssue").tasklet(coupnCancelReSendTask).build();
	}

}
