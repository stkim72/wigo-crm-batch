package com.ceragem.batch.crm.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ceragem.batch.crm.common.util.CrmJobListener;
import com.ceragem.batch.crm.tasklet.CustomerStatusDormancyTask;
import com.ceragem.batch.crm.tasklet.CustomerStatusWithdrawalTask;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@RequiredArgsConstructor
class CrmCustomerStatusJob {

	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;
	private final CustomerStatusDormancyTask dormancyTask;
	private final CustomerStatusWithdrawalTask withdrawalTask;
	@Autowired
	CrmJobListener jobListener;

	@Bean("customerStatusJob")
	Job customerStatusJob() {
		log.debug("customerStatusJob");
		return jobBuilderFactory.get("customerStatusJob").listener(jobListener).start(stepWithdrawal())
				.next(stepDormancy()).build();
	}

	@Bean("stepWithdrawal")
	Step stepWithdrawal() {
		log.debug("stepWithdrawal");
		return stepBuilderFactory.get("stepWithdrawal").tasklet(withdrawalTask).build();
	}

	@Bean("stepDormancy")
	Step stepDormancy() {
		log.debug("stepDormancy");
		return stepBuilderFactory.get("stepDormancy").tasklet(dormancyTask).build();
	}

}
