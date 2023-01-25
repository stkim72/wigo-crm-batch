package com.ceragem.batch.crm.job;

import java.util.Calendar;
import java.util.List;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
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

import com.ceragem.batch.crm.bos.model.BosCrmServiceVo;
import com.ceragem.batch.crm.bos.service.BosApiService;
import com.ceragem.batch.crm.common.constant.Constants;
import com.ceragem.batch.crm.common.util.CrmJobListener;
import com.ceragem.batch.crm.common.util.Utilities;
import com.ceragem.batch.crm.dao.CrmCustBosHcHstDao;
import com.ceragem.batch.crm.model.BatchInfoBasVo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class BosServiceSyncJob {
	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;

//	private final BosServiceSyncTask tast;
	private List<BosCrmServiceVo> readList = null;
	@Autowired
	CrmJobListener jobListener;
	@Autowired
	BosApiService service;

	@Autowired
	CrmCustBosHcHstDao dao;

	Calendar calStart = null;
	int totalSize = 0;
	int currentPage = 0;
	int pageSize = Constants.MIG_PAGE_SIZE;

	int totalInsert = 0;
	int totalUpdate = 0;
	int totalSkip = 0;

	@Bean("syncBosServiceJob")
	Job syncBosServiceJob() {
		log.debug("syncBosServiceJob");
		return jobBuilderFactory.get("syncBosServiceJob").listener(jobListener).start(stepBosService()).build();
	}

	@Bean("stepBosService")
	Step stepBosService() {
		log.debug("stepBosService");
		return stepBuilderFactory.get("stepBosService").<BosCrmServiceVo, BosCrmServiceVo>chunk(Constants.FETCH_COUNT)
				.reader(crmServiceReader())
//				.processor(processContract())
				.writer(crmServiceWriter()).build();
	}

	@Bean("crmServiceReader")
	ItemReader<BosCrmServiceVo> crmServiceReader() {
		return new ItemReader<BosCrmServiceVo>() {

			@Override
			public BosCrmServiceVo read()
					throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
				if (Utilities.isEmpty(readList)) {
					getList();
				}
				if (Utilities.isEmpty(readList)) {
					log.debug("totalSize  : " + totalSize + "");
					readList = null;
					if (totalSize == 0) {
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
		JobParameters jobParameters = jobListener.getJobExecution().getJobParameters();

		if (Utilities.isNotEmpty(jobParameters.getString("mig"))) {
			int pSize = Utilities.parseInt(jobParameters.getString("pageSize"));
			if (pSize > 0)
				pageSize = pSize;
			if (calStart == null) {
				calStart = Calendar.getInstance();
				int month = Constants.MIG_START_YEAR;
				if (Utilities.isNotEmpty(jobParameters.getString("year"))) {
					int mon = Utilities.parseInt(jobParameters.getString("year"));
					if (mon > 2000)
						month = mon;
				}

				calStart.set(month, Constants.MIG_START_MONTH, Constants.MIG_START_DAY, 0, 0, 0);
			}
			Calendar cur = Calendar.getInstance();
			if (calStart.getTimeInMillis() > cur.getTimeInMillis())
				return;

			String fromDate = Utilities.getDateString(calStart.getTime());
			Calendar calEnd = Calendar.getInstance();

			calEnd.setTime(calStart.getTime());
			calEnd.add(Calendar.MONTH, 1);
			calEnd.add(Calendar.DATE, -1);
			String toDate = Utilities.getDateString(calEnd.getTime());
			currentPage++;
			readList = service.getCrmServiceList(null, fromDate, toDate, currentPage, pageSize);

			if (Utilities.isEmpty(readList)) {
				currentPage = 0;
				calStart.add(Calendar.MONTH, 1);
				getList();
				return;
			}
			if (readList.size() < pageSize) {
				currentPage = 0;
				calStart.add(Calendar.MONTH, 1);
			}
			totalSize += readList.size();
			log.debug("readList.size()  : " + readList.size() + "");
			log.debug("totalSize  : " + totalSize + "");

		} else {

			if (currentPage < 0)
				return;
			pageSize = Constants.PAGE_SIZE;
			BatchInfoBasVo batchInfo = (BatchInfoBasVo) jobListener.getJobExecution().getExecutionContext()
					.get("batchInfo");
			String toDate = Utilities.getDateString();
			String fromDate = batchInfo == null ? null : batchInfo.getLastExecDt();
			if (Utilities.isEmpty(fromDate)) {
//				fromDate = String.format("%s%s", Utilities.getDateString().substring(0, 6), "01");
				fromDate = toDate;
			} else
				fromDate = fromDate.substring(0, 8);
			currentPage++;
			readList = service.getCrmServiceList(null, fromDate, toDate, currentPage, pageSize);
			if (readList != null)
				totalSize += readList.size();
			if (Utilities.isEmpty(readList) || readList.size() < pageSize) {
				currentPage = -1;
				return;
			}

		}

	}

	@Bean("crmServiceWriter")
	ItemWriter<BosCrmServiceVo> crmServiceWriter() {
		return new ItemWriter<BosCrmServiceVo>() {

			@Override
			public void write(List<? extends BosCrmServiceVo> items) throws Exception {
				for (int i = 0; i < items.size(); i++) {
					BosCrmServiceVo vo = items.get(i);
					if (Utilities.isEmpty(vo.getItgCustNo())) {
						totalSkip++;
						continue;
					}
					BosCrmServiceVo old = dao.select(vo);
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
