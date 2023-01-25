package com.ceragem.batch.crm.job;

import java.util.ArrayList;
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

import com.ceragem.batch.crm.bos.model.BosCrmRmnyCancelTxnVo;
import com.ceragem.batch.crm.bos.service.BosApiService;
import com.ceragem.batch.crm.common.constant.Constants;
import com.ceragem.batch.crm.common.util.CrmJobListener;
import com.ceragem.batch.crm.common.util.Utilities;
import com.ceragem.batch.crm.dao.CrmBosRmnyCancelTxnDao;
import com.ceragem.batch.crm.model.BatchInfoBasVo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @ClassName BosRcvCancelSyncJob
 * @author 김성태
 * @date 2022. 6. 30.
 * @Version 1.0
 * @description 수납내역 동기화
 * @Company Copyright ⓒ wigo.ai. All Right Reserved
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class BosRcvCancelSyncJob {

	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;
	private Calendar cal = null;
	private boolean dailyMode = false;
	List<BosCrmRmnyCancelTxnVo> readList = null;

	@Autowired
	CrmJobListener jobListener;

	@Autowired
	BosApiService service;

	@Autowired
	CrmBosRmnyCancelTxnDao dao;

	Calendar calStart = null;
	int totalSize = 0;
	int currentPage = 0;
	int pageSize = 1000;
	int totalInsert = 0;
	int totalUpdate = 0;

	@Bean("syncBosRcvCancelListJob")
	Job syncRcvCancelListJob() {
		log.debug("syncBosRcvCancelListJob");
		return jobBuilderFactory.get("syncBosRcvCancelListJob").listener(jobListener).start(stepRcvCancelList())
				.build();
	}

	@Bean("stepRcvCancelList")
	Step stepRcvCancelList() {
		log.debug("stepRcvCancelList");
		return stepBuilderFactory.get("stepRcvCancelList")
				.<BosCrmRmnyCancelTxnVo, BosCrmRmnyCancelTxnVo>chunk(Constants.FETCH_COUNT)
				.reader(readerRcvCancelList()).writer(writerRcvCancelList()).build();
	}

	@Bean("readerRcvCancelList")
	ItemReader<BosCrmRmnyCancelTxnVo> readerRcvCancelList() {
		return new ItemReader<BosCrmRmnyCancelTxnVo>() {

			@Override
			public BosCrmRmnyCancelTxnVo read()
					throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
				if (Utilities.isEmpty(readList)) {
					getList();
				}
				if (Utilities.isEmpty(readList)) {
					log.debug("totalSize  : " + totalSize + "");
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
		if (dailyMode) {
			getDailyList();
			return;
		}
		readList = new ArrayList<BosCrmRmnyCancelTxnVo>();
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

			readList = service.getRcivCnclList(fromDate, toDate, null, null, currentPage, pageSize);
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
			readList = service.getRcivCnclList(fromDate, toDate, null, null, currentPage, pageSize);
			if (readList != null)
				totalSize += readList.size();
			if (Utilities.isEmpty(readList) || readList.size() < pageSize) {
				currentPage = -1;
				return;
			}

		}

	}

	private void getDailyList() throws Exception {

		JobParameters jobParameters = jobListener.getJobExecution().getJobParameters();

		if (Utilities.isNotEmpty(jobParameters.getString("mig"))) {
			readList = new ArrayList<BosCrmRmnyCancelTxnVo>();
			if (cal == null) {
				cal = Calendar.getInstance();
//				cal.set(Constants.MIG_START_YEAR, Constants.MIG_START_MONTH, Constants.MIG_START_DAY, 0, 0, 0);
				cal.set(2019, Constants.MIG_START_MONTH, Constants.MIG_START_DAY, 0, 0, 0);
			}
			String dt = Utilities.getDateString(cal.getTime());
			Calendar cur = Calendar.getInstance();
			if (cur.getTime().getTime() < cal.getTime().getTime())
				return;

			cal.add(Calendar.DATE, 1);
			List<BosCrmRmnyCancelTxnVo> list = service.getRcivCnclList(dt, dt, null, null, 1, 0);
			if (Utilities.isEmpty(list)) {
				getDailyList();
				return;
			} else {
				readList = list;
				return;
			}
		} else {
			BatchInfoBasVo batchInfo = (BatchInfoBasVo) jobListener.getJobExecution().getExecutionContext()
					.get("batchInfo");
			String toDate = Utilities.getDateString();
			String fromDate = batchInfo == null ? null : batchInfo.getLastExecDt();
			if (Utilities.isEmpty(fromDate)) {
//				fromDate = String.format("%s%s", Utilities.getDateString().substring(0, 6), "01");
				fromDate = toDate;
			} else
				fromDate = fromDate.substring(0, 8);
			if (readList == null)
				readList = new ArrayList<BosCrmRmnyCancelTxnVo>();
			else
				return;
			readList = service.getRcivCnclList(fromDate, toDate, null, null, 1, Constants.FETCH_COUNT);
		}
	}

	@Bean("writerRcvCancelList")
	ItemWriter<BosCrmRmnyCancelTxnVo> writerRcvCancelList() {
		return new ItemWriter<BosCrmRmnyCancelTxnVo>() {

			@Override
			public void write(List<? extends BosCrmRmnyCancelTxnVo> items) throws Exception {
				for (int i = 0; i < items.size(); i++) {
					BosCrmRmnyCancelTxnVo vo = items.get(i);
					if (Utilities.isEmpty(vo.getCustno()))
						continue;
					BosCrmRmnyCancelTxnVo old = dao.select(vo);
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

					batchInfo.addErrorLog(bf.toString());
				}

			}
		};
	}

}
