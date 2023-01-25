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

import com.ceragem.batch.crm.bos.model.BosCrmBcustTxnVo;
import com.ceragem.batch.crm.bos.service.BosApiService;
import com.ceragem.batch.crm.common.constant.Constants;
import com.ceragem.batch.crm.common.util.CrmJobListener;
import com.ceragem.batch.crm.common.util.Utilities;
import com.ceragem.batch.crm.dao.CrmBosBcustTxnDao;
import com.ceragem.batch.crm.model.BatchInfoBasVo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @ClassName BosBcustJob
 * @author 김성태
 * @date 2022. 6. 30.
 * @Version 1.0
 * @description 반환내역 동기화
 * @Company Copyright ⓒ wigo.ai. All Right Reserved
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class BosBcustSyncJob {

	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;

	List<BosCrmBcustTxnVo> readList = null;

	@Autowired
	CrmJobListener jobListener;

	@Autowired
	BosApiService service;

	@Autowired
	CrmBosBcustTxnDao dao;

	int pageSize = Constants.PAGE_SIZE;
	int currentPage = 0;

	int totalSize = 0;
	int totalInsert = 0;
	int totalUpdate = 0;
	int totalSkip = 0;

	@Bean("syncBosBcustJob")
	Job syncBcustJob() {
		log.debug("syncBosBcustJob");
		return jobBuilderFactory.get("syncBosBcustJob").listener(jobListener).start(stepBcust()).build();
	}

	@Bean("stepBcust")
	Step stepBcust() {
		log.debug("stepBcust");
		return stepBuilderFactory.get("stepBcust").<BosCrmBcustTxnVo, BosCrmBcustTxnVo>chunk(Constants.FETCH_COUNT)
				.reader(readerBcust()).writer(writerBcust()).build();
	}

	@Bean("readerBcust")
	ItemReader<BosCrmBcustTxnVo> readerBcust() {
		return new ItemReader<BosCrmBcustTxnVo>() {

			@Override
			public BosCrmBcustTxnVo read()
					throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
				if (Utilities.isEmpty(readList))
					getList();
				if (Utilities.isEmpty(readList)) {
					readList = null;
					if(totalSize == 0)
					{
						BatchInfoBasVo batchInfo = (BatchInfoBasVo) jobListener.getJobExecution().getExecutionContext()
								.get("batchInfo");
						batchInfo.addErrorLog("total read : 0");
						
					}
					return null;
				}

				return readList.remove(0);
			}
		};
	}

	protected void getList() throws Exception {
		if (currentPage < 0)
			return;
		currentPage++;
		readList = service.getBcustList(currentPage, pageSize);
		if (readList != null)
			totalSize += readList.size();
		if (Utilities.isEmpty(readList) || readList.size() < pageSize) {
			currentPage = -1;
		}
		

	}

	@Bean("writerBcust")
	ItemWriter<BosCrmBcustTxnVo> writerBcust() {
		return new ItemWriter<BosCrmBcustTxnVo>() {

			@Override
			public void write(List<? extends BosCrmBcustTxnVo> items) throws Exception {
				for (int i = 0; i < items.size(); i++) {
					BosCrmBcustTxnVo vo = items.get(i);
					if (Utilities.isEmpty(vo.getCustno()) || Utilities.isEmpty(vo.getCrmcustno()))
					{
						totalSkip++;
						continue;
					}

					BosCrmBcustTxnVo old = dao.select(vo);
					if (old == null)
						totalInsert += dao.insert(vo);
					else
						totalUpdate += dao.update(vo);
				}
				if (readList == null) {
					BatchInfoBasVo batchInfo = (BatchInfoBasVo) jobListener.getJobExecution().getExecutionContext()
							.get("batchInfo");
					StringBuffer bf = new StringBuffer();
					bf.append("total read : ");
					bf.append(totalSize);

					bf.append("\ntotal write : ");
					bf.append(totalInsert + totalUpdate);

					bf.append("\ntotal insert : ");
					bf.append(totalInsert);

					bf.append("\ntotal update : ");
					bf.append(totalUpdate);

					
					bf.append("\ntotal skip : ");
					bf.append(totalSkip);
					batchInfo.addErrorLog(bf.toString());
				}

			}
		};
	}

}
