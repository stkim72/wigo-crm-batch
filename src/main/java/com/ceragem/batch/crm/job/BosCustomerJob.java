package com.ceragem.batch.crm.job;

import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ceragem.batch.crm.bos.model.BosCustVo;
import com.ceragem.batch.crm.bos.service.BosApiService;
import com.ceragem.batch.crm.common.constant.Constants;
import com.ceragem.batch.crm.common.util.CrmJobListener;
import com.ceragem.batch.crm.common.util.Utilities;
import com.ceragem.batch.crm.model.CrmCustBasVo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class BosCustomerJob {

	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;

	List<BosCustVo> readList = null;

	@Autowired
	CrmJobListener jobListener;

	@Autowired
	BosApiService bosService;

	@Bean("syncBosCustomerJob")
	Job syncBosCustomerJob() {
		log.debug("syncBosCustomerJob");
		return jobBuilderFactory.get("syncBosCustomerJob").listener(jobListener).start(stepBosCustomer()).build();
	}

	@Bean("stepBosCustomer")
	Step stepBosCustomer() {
		log.debug("stepBosCustomer");
		return stepBuilderFactory.get("stepBosCustomer").<BosCustVo, CrmCustBasVo>chunk(Constants.FETCH_COUNT)
				.reader(readerCustomer()).processor(processCustomer()).writer(writerCustomer()).build();
	}

	@Bean("readerCustomer")
	ItemReader<BosCustVo> readerCustomer() {
		return new ItemReader<BosCustVo>() {

			@Override
			public BosCustVo read()
					throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
				if (readList == null) {
					getList();
				}
				if (Utilities.isEmpty(readList))
					return null;
				return readList.remove(0);
			}
		};
	}

	protected void getList() {
		readList = new ArrayList<BosCustVo>();
//		JobParameters jobParameters = jobListener.getJobExecution().getJobParameters();
	}

	@Bean("processCustomer")
	ItemProcessor<BosCustVo, CrmCustBasVo> processCustomer() {
		return new ItemProcessor<BosCustVo, CrmCustBasVo>() {

			@Override
			public CrmCustBasVo process(BosCustVo item) throws Exception {
				// TODO Auto-generated method stub
				return null;
			}
		};
	}

	@Bean("writerCustomer")
	ItemWriter<CrmCustBasVo> writerCustomer() {
		return new ItemWriter<CrmCustBasVo>() {

			@Override
			public void write(List<? extends CrmCustBasVo> items) throws Exception {
				// TODO Auto-generated method stub

			}
		};
	}

}
