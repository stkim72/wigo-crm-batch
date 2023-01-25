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

import com.ceragem.batch.crm.bos.model.BosCrmRmnyCmptTxnVo;
import com.ceragem.batch.crm.bos.service.BosApiService;
import com.ceragem.batch.crm.common.constant.Constants;
import com.ceragem.batch.crm.common.util.CrmJobListener;
import com.ceragem.batch.crm.common.util.Utilities;
import com.ceragem.batch.crm.dao.CrmBosRmnyCmptTxnDao;
import com.ceragem.batch.crm.model.BatchInfoBasVo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @ClassName BosRcvSyncJob
 * @author 김성태
 * @date 2022. 6. 30.
 * @Version 1.0
 * @description 수납내역 동기화
 * @Company Copyright ⓒ wigo.ai. All Right Reserved
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class BosRcvSyncJob {

	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;
	List<BosCrmRmnyCmptTxnVo> readList = null;

	@Autowired
	CrmJobListener jobListener;

	@Autowired
	BosApiService service;

	@Autowired
	CrmBosRmnyCmptTxnDao dao;

	Calendar calStart = null;
	int totalSize = 0;
	int currentPage = 0;
	int pageSize = 1000;// Constants.PAGE_SIZE;

	int endYear = 0;
	int endMonth = 0;
	int totalInsert = 0;
	int totalUpdate = 0;

	@Bean("syncBosRcvListJob")
	Job syncRcvListJob() {
		log.debug("syncBosRcvListJob");
		return jobBuilderFactory.get("syncBosRcvListJob").listener(jobListener).start(stepRcvList()).build();
	}

	@Bean("stepRcvList")
	Step stepRcvList() {
		log.debug("stepRcvList");
		return stepBuilderFactory.get("stepRcvList")
				.<BosCrmRmnyCmptTxnVo, BosCrmRmnyCmptTxnVo>chunk(Constants.FETCH_COUNT).reader(readerRcvList())
				.writer(writerRcvList()).build();
	}

	@Bean("readerRcvList")
	ItemReader<BosCrmRmnyCmptTxnVo> readerRcvList() {
		return new ItemReader<BosCrmRmnyCmptTxnVo>() {

			@Override
			public BosCrmRmnyCmptTxnVo read()
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

		JobParameters jobParameters = jobListener.getJobExecution().getJobParameters();

		if (Utilities.isNotEmpty(jobParameters.getString("mig"))) {

			if (calStart == null) {
				calStart = Calendar.getInstance();
				int yr = Constants.MIG_START_YEAR;
				int mon = Constants.MIG_START_MONTH;
				if (Utilities.isNotEmpty(jobParameters.getString("year"))) {
					int y = Utilities.parseInt(jobParameters.getString("year"));
					if (y > 2000)
						yr = y;

					if (Utilities.isNotEmpty(jobParameters.getString("month"))) {
						int m = Utilities.parseInt(jobParameters.getString("month"));
						if (m > 0 && m <= 12)
							mon = m - 1;
					}
					if (Utilities.isNotEmpty(jobParameters.getString("page"))) {
						int page = Utilities.parseInt(jobParameters.getString("page"));
						if (page > 1)
							currentPage = page - 1;
					}

					if (Utilities.isNotEmpty(jobParameters.getString("endYear"))) {
						int yy = Utilities.parseInt(jobParameters.getString("endYear"));
						if (yy > 2000)
							endYear = yy;

					}

					if (Utilities.isNotEmpty(jobParameters.getString("endMonth"))) {
						int mm = Utilities.parseInt(jobParameters.getString("endMonth"));
						if (mm > 0 && mm <= 12)
							endMonth = mm;

					}
				}

				calStart.set(yr, mon, Constants.MIG_START_DAY, 0, 0, 0);
			}

			if (endYear > 0) {
				int ey = calStart.get(Calendar.YEAR);
				if (endYear < ey)
					return;

				if (endMonth > 0) {
					int em = calStart.get(Calendar.MONTH) + 1;
					if (endMonth < em)
						return;

				}
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
			readList = service.getRcivList(fromDate, toDate, null, null, currentPage, pageSize);
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

//			if (Utilities.isNotEmpty(list))
//				readList.addAll(list);

		} else {
			if (currentPage < 0)
				return;
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
			readList = service.getRcivList(fromDate, toDate, null, null, currentPage, pageSize);
			if (readList != null)
				totalSize += readList.size();
			if (Utilities.isEmpty(readList) || readList.size() < pageSize) {
				currentPage = -1;
				return;
			}
		}

	}

	@Bean("writerRcvList")
	ItemWriter<BosCrmRmnyCmptTxnVo> writerRcvList() {
		return new ItemWriter<BosCrmRmnyCmptTxnVo>() {

			@Override
			public void write(List<? extends BosCrmRmnyCmptTxnVo> items) throws Exception {
				for (int i = 0; i < items.size(); i++) {
					BosCrmRmnyCmptTxnVo vo = items.get(i);
					if (Utilities.isEmpty(vo.getCustno()) || Utilities.isEmpty(vo.getCrmcustno()))
						continue;
					BosCrmRmnyCmptTxnVo old = dao.select(vo);
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
