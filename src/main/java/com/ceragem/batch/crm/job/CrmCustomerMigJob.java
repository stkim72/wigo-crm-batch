package com.ceragem.batch.crm.job;

import java.util.List;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ceragem.batch.crm.common.constant.Constants;
import com.ceragem.batch.crm.common.util.CrmJobListener;
import com.ceragem.batch.crm.common.util.Utilities;
import com.ceragem.batch.crm.model.CrmCustBasVo;
import com.ceragem.batch.crm.service.CrmCustBasService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class CrmCustomerMigJob {

	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;

	@Autowired
	CrmCustBasService custService;
	@Autowired
	CrmJobListener jobListener;

	List<CrmCustBasVo> readList = null;//new ArrayList<CrmCustBasVo>();

	@Bean("customerMigJob")
	Job customerMigJob() {
		log.debug("customerMigJob");
//		return jobBuilderFactory.get("customerEncryptJob").listener(jobListener).start(stepEncTask()).next(stepContactTask()).build();
		return jobBuilderFactory.get("customerMigJob").listener(jobListener).start(stepCrmCustomer()).build();
	}

	@Bean("stepCrmCustomer")
	Step stepCrmCustomer() {
		log.debug("stepCrmCustomer");
		return stepBuilderFactory.get("stepCrmCustomer").<CrmCustBasVo, CrmCustBasVo>chunk(Constants.FETCH_COUNT)
				.reader(crmCustReader()).writer(crmCustWriter()).build();
	}

	@Bean("crmCustReader")
	ItemReader<CrmCustBasVo> crmCustReader() {
		return new ItemReader<CrmCustBasVo>() {

			@Override
			public CrmCustBasVo read()
					throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {

				if (readList == null) {
					readList = custService.getMigList();
					
				}
				if (Utilities.isEmpty(readList))
					return null;
				return readList.remove(0);
			}
		};
	}
//
//	@Bean("crmCustProcess")
//	public ItemProcessor<CrmCustBasVo, CrmCustBasVo> crmCustProcess() {
//		return new ItemProcessor<CrmCustBasVo, CrmCustBasVo>() {
//
//			@Override
//			public CrmCustBasVo process(CrmCustBasVo item) throws Exception {
//				String val = item.getMphonNo();
//				if (Utilities.isNotEmpty(val)) {
//					item.setMphonNo(Utilities.encrypt(val));
//				}
//				return item;
//			}
//		};
//	}

	@Bean("crmCustWriter")
	ItemWriter<CrmCustBasVo> crmCustWriter() {
		return new ItemWriter<CrmCustBasVo>() {

			@Override
			public void write(List<? extends CrmCustBasVo> items) throws Exception {
				for (int i = 0; i < items.size(); i++) {
					CrmCustBasVo vo = items.get(i);
//					custService.updateEnc(vo);
//					vo.setMphonNoEncVal(vo.getMphonNo());
					custService.insertDefaultContact(vo);
				}

			}
		};
	}

//	@Bean("stepEncTask")
//	public Step stepEncTask() {
//		log.debug("stepEncTask");
//		return stepBuilderFactory.get("stepEncTask").tasklet(encTask).build();
//	}
//	@Bean("stepContactTask")
//	public Step stepContactTask() {
//		log.debug("stepContactTask");
//		return stepBuilderFactory.get("stepContactTask").tasklet(contactTask).build();
//	}

}
