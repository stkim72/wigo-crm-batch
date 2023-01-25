package com.ceragem.batch.crm.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ceragem.batch.crm.common.util.CrmJobListener;
import com.ceragem.batch.crm.tasklet.BosGoodsSyncTask;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class BosGoodsSyncJob {

	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;
	private final BosGoodsSyncTask tast;
	@Autowired
	CrmJobListener jobListener;

	@Bean("syncBosGoodsJob")
	Job syncBosGoodsJob() {
		log.debug("syncBosGoodsJob");
		return jobBuilderFactory.get("syncBosGoodsJob").listener(jobListener).start(stepGoods()).build();
	}

	@Bean("stepGoods")
	Step stepGoods() {
		log.debug("stepGoods");
		return stepBuilderFactory.get("stepGoods").tasklet(tast).build();
	}

}
