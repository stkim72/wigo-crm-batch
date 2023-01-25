package com.ceragem.batch.crm.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ceragem.batch.crm.common.util.CrmJobListener;
import com.ceragem.batch.crm.tasklet.BosEmpSyncTask;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class BosEmpSyncJob {

	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;
	private final BosEmpSyncTask tast;
	@Autowired
	CrmJobListener jobListener;

	@Bean("syncBosEmpJob")
	Job syncBosEmpJob() {
		log.debug("syncBosEmpJob");
		return jobBuilderFactory.get("syncBosEmpJob").listener(jobListener).start(stepBosEmp()).build();
	}

	@Bean("stepBosEmp")
	Step stepBosEmp() {
		log.debug("stepBosEmp");
		return stepBuilderFactory.get("stepBosEmp").tasklet(tast).build();
	}

}
