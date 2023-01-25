package com.ceragem.batch.crm.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ceragem.batch.crm.common.util.CrmJobListener;
import com.ceragem.batch.crm.tasklet.SyncJadeEmpCeragemCncTasklet;
import com.ceragem.batch.crm.tasklet.SyncJadeEmpCeragemTasklet;
import com.ceragem.batch.crm.tasklet.SyncJadeOrgTasklet;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class CrmSyncJadeJob {

	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;
	private final SyncJadeOrgTasklet orgTask;
	private final SyncJadeEmpCeragemTasklet empCeragemTask;
	private final SyncJadeEmpCeragemCncTasklet empCeragemCncTask;

	@Autowired
	CrmJobListener jobListener;

	@Bean("syncJadeJob")
	Job jadeJob() {
		log.debug("syncJadeJob");
		return jobBuilderFactory.get("syncJadeJob").listener(jobListener).start(stepOrg()).next(StepHrCeragem())
				.next(StepHrCeragemCnC()).build();
	}

	@Bean("stepOrg")
	Step stepOrg() {
		log.debug("stepOrg");
		return stepBuilderFactory.get("stepOrg").tasklet(orgTask).build();
	}

	@Bean("StepCeragem")
	Step StepHrCeragem() {
		log.debug("StepCeragem");
		return stepBuilderFactory.get("StepCeragem").tasklet(empCeragemTask).build();
	}

	@Bean("StepCeragemCnC")
	Step StepHrCeragemCnC() {
		log.debug("StepCeragemCnC");
		return stepBuilderFactory.get("StepCeragemCnC").tasklet(empCeragemCncTask).build();
	}
}
