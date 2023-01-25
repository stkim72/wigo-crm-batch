package com.ceragem.batch.crm.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ceragem.batch.crm.common.util.CrmJobListener;
import com.ceragem.batch.crm.tasklet.BosOrgSyncTask;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class BosOrgSyncJob {

	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;
	private final BosOrgSyncTask tast;
	@Autowired
	CrmJobListener jobListener;

	@Bean("syncBosOrgJob")
	Job syncBosOrgJob() {
		log.debug("syncBosOrgJob");
		return jobBuilderFactory.get("syncBosOrgJob").listener(jobListener).start(stepBosOrg()).build();
	}

	@Bean("stepBosOrg")
	Step stepBosOrg() {
		log.debug("stepBosOrg");
		return stepBuilderFactory.get("stepBosOrg").tasklet(tast).build();
	}

}
