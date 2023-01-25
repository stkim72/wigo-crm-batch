package com.ceragem.batch.crm.tasklet;

import java.util.Calendar;
import java.util.List;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ceragem.batch.crm.bos.model.BosCrmContractVo;
import com.ceragem.batch.crm.bos.service.BosApiService;
import com.ceragem.batch.crm.common.constant.Constants;
import com.ceragem.batch.crm.common.util.Utilities;
import com.ceragem.batch.crm.dao.CrmCustBosCntrtHstDao;
import com.ceragem.batch.crm.model.BatchInfoBasVo;
import com.ceragem.batch.crm.service.BatchExecHstService;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @ClassName	BosContractSyncTask
 * @author		김성태
 * @date		2022. 10. 5.
 * @Version		1.0
 * @description	bos계약 동기화 task
 * @Company		Copyright ⓒ wigo.ai. All Right Reserved
 */
@Slf4j
@Component
public class BosContractSyncTask implements Tasklet {
	@Autowired
	BosApiService service;
	@Autowired
	BatchExecHstService batchService;
	@Autowired
	CrmCustBosCntrtHstDao dao;

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		log.debug("BosServiceSyncTask => ");
		BatchInfoBasVo batchInfo = (BatchInfoBasVo) contribution.getStepExecution().getJobExecution()
				.getExecutionContext().get("batchInfo");

		if (Utilities
				.isNotEmpty(contribution.getStepExecution().getJobExecution().getJobParameters().getString("mig"))) {
			return executeMig();
		}
		String toDate = Utilities.getDateString();
		String fromDate = batchInfo == null ? null : batchInfo.getLastExecDt();
		if (Utilities.isEmpty(fromDate)) {
//			return executeMig();
			fromDate = String.format("%s%s", Utilities.getDateString().substring(0, 6), "01");
		} else
			fromDate = fromDate.substring(0, 8);

		return execute(fromDate, toDate);

	}

	private RepeatStatus executeMig() throws Exception {
		Calendar calStart = Calendar.getInstance();
		Calendar cur = Calendar.getInstance();
		calStart.set(Constants.MIG_START_YEAR, Constants.MIG_START_MONTH, Constants.MIG_START_DAY);

		while (calStart.getTimeInMillis() <= cur.getTimeInMillis()) {
			String fromDate = Utilities.getDateString(calStart.getTime());
			Calendar calEnd = Calendar.getInstance();

			calEnd.setTime(calStart.getTime());
			calEnd.add(Calendar.MONTH, 1);
			calEnd.add(Calendar.DATE, -1);

			String toDate = Utilities.getDateString(calEnd.getTime());
			execute(fromDate, toDate);
			calStart.add(Calendar.MONTH, 1);
		}
		return RepeatStatus.FINISHED;

	}

	private RepeatStatus execute(String fromDate, String toDate) throws Exception {
		List<BosCrmContractVo> list = service.getCrmContractList(null, null, null, fromDate, toDate,1,10000);
		for (int i = 0; i < list.size(); i++) {
			BosCrmContractVo vo = list.get(i);
			if (Utilities.isEmpty(vo.getItgCustNo()))
				continue;
			BosCrmContractVo old = dao.select(vo);
			if (old == null)
				dao.insert(vo);
			else
				dao.update(vo);
		}
		return RepeatStatus.FINISHED;

	}
}
