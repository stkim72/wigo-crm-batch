package com.ceragem.batch.crm.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ceragem.batch.crm.common.util.CrmJobListener;
import com.ceragem.batch.crm.tasklet.CampCpnPntTask;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class CrmCampCpnPntJob {

	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;
	private final CampCpnPntTask campCpnPntTask;
	
	@Autowired
	CrmJobListener jobListener;

	@Bean("campCpnPntJob")
	Job campCpnPntJob() {
		log.debug("campCpnPntJob");
		return jobBuilderFactory.get("campCpnPntJob").listener(jobListener).start(stepCampCpnPntIssue()).build();
	}

	@Bean("stepCampCpnPntIssue")
	Step stepCampCpnPntIssue() {
		log.debug("stepCampCpnPntIssue");
		return stepBuilderFactory.get("stepCampCpnPntIssue").tasklet(campCpnPntTask).build();
	}

}
