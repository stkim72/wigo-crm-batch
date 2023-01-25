package com.ceragem.batch.crm.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ceragem.batch.crm.common.util.CrmJobListener;
import com.ceragem.batch.crm.tasklet.GrdCoupn001IssueTask;
import com.ceragem.batch.crm.tasklet.GrdCoupn002IssueTask;
import com.ceragem.batch.crm.tasklet.GrdCoupn003IssueTask;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class CrmGrdCoupnIssueJob {

	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;
	private final GrdCoupn001IssueTask grdCoupn001IssuTask;
	private final GrdCoupn002IssueTask grdCoupn002IssuTask;
	private final GrdCoupn003IssueTask grdCoupn003IssuTask;
	
	@Autowired
	CrmJobListener jobListener;

	@Bean("grdCoupnIssueJob")
	Job grdCoupnIssueJob() {
		log.debug("grdCoupnIssueJob");
		return jobBuilderFactory.get("grdCoupnIssueJob").listener(jobListener).start(stepGrdCoupn001Issue()).next(stepGrdCoupn002Issue()).next(stepGrdCoupn003Issue()).build();
	}

	// 임직원
	@Bean("stepGrdCoupn001Issue")
	Step stepGrdCoupn001Issue() {
		log.debug("stepGrdCoupn001Issue");
		return stepBuilderFactory.get("stepGrdCoupn001Issue").tasklet(grdCoupn001IssuTask).build();
	}

	// 제휴
	@Bean("stepGrdCoupn002Issue")
	Step stepGrdCoupn002Issue() {
		log.debug("stepGrdCoupn002Issue");
		return stepBuilderFactory.get("stepGrdCoupn002Issue").tasklet(grdCoupn002IssuTask).build();
	}
	
	// 회원
	@Bean("stepGrdCoupn003Issue")
	Step stepGrdCoupn003Issue() {
		log.debug("stepGrdCoupn003Issue");
		return stepBuilderFactory.get("stepGrdCoupn003Issue").tasklet(grdCoupn003IssuTask).build();
	}
}
