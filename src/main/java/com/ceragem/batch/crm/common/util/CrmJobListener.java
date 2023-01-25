package com.ceragem.batch.crm.common.util;

import java.text.SimpleDateFormat;
import java.util.Locale;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ceragem.batch.crm.exception.BatchException;
import com.ceragem.batch.crm.model.BatchExecHstVo;
import com.ceragem.batch.crm.model.BatchInfoBasVo;
import com.ceragem.batch.crm.service.BatchExecHstService;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @ClassName CrmJobListener
 * @author 김성태
 * @date 2022. 10. 5.
 * @Version 1.0
 * @description 배치 job listener
 * @Company Copyright ⓒ wigo.ai. All Right Reserved
 */
@Slf4j
@Component
public class CrmJobListener implements JobExecutionListener {
	@Autowired
	BatchExecHstService service;
	private final static SimpleDateFormat DATETIME_FORMAT = new SimpleDateFormat("yyyyMMddHHmmss", Locale.KOREAN);
	private JobExecution jobExecution;

	@Override
	public void beforeJob(JobExecution jobExecution) {
		String jobName = jobExecution.getJobInstance().getJobName();

		this.jobExecution = jobExecution;
		BatchInfoBasVo so = new BatchInfoBasVo();
		so.setWrkId(jobName);
		try {
			BatchInfoBasVo vo = service.getBatchInfo(so);
			if (vo == null)
				throw new BatchException("[" + jobName + "]등록되지 않은 배치입니다.");
			jobExecution.getExecutionContext().put("batchInfo", vo);
			if (!"Y".equals(vo.getUseYn())) {
				throw new BatchException("[" + jobName + "]사용중지 된 배치입니다.");
			}
			log.warn(
					"#####################################################################################################");
			log.warn("                               " + vo.getBatchNm() + " : " + jobName + " start");
			log.warn(
					"#####################################################################################################");

		} catch (Exception e) {
			throw new BatchException(e.getMessage(), e);
		}
	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		String jobName = jobExecution.getJobInstance().getJobName();
		BatchInfoBasVo batchInfo = (BatchInfoBasVo) jobExecution.getExecutionContext().get("batchInfo");
//		if (batchInfo != null && !"Y".equals(batchInfo.getUseYn()))
//			return;
		int ms = (int) (jobExecution.getEndTime().getTime() - jobExecution.getStartTime().getTime());
		BatchExecHstVo vo = new BatchExecHstVo();
		vo.setBatchCallDt(DATETIME_FORMAT.format(jobExecution.getStartTime()));
		vo.setBatchRsltCd(jobExecution.getExitStatus().equals(ExitStatus.COMPLETED) ? "Y" : "N");
		StringBuffer error = new StringBuffer();
		String errDesc = jobExecution.getExitStatus().getExitDescription();
		if (errDesc != null && errDesc.length() > 4000)
			errDesc = errDesc.substring(0, 4000) + "\n....\n";

		if (Utilities.isNotEmpty(errDesc))
			error.append(errDesc);
		if (batchInfo != null) {
			String log = batchInfo.getErrorLog();
			if (Utilities.isNotEmpty(log))
				error.append(log);
		}

//		else
		vo.setErrMsgTxn(error.toString());
		vo.setWrkId(jobExecution.getJobInstance().getJobName());
		vo.setRegDt(DATETIME_FORMAT.format(jobExecution.getEndTime()));
		vo.setBatchExecMsec(ms);
		try {

			service.insert(vo);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		log.warn(
				"#####################################################################################################");
		log.warn("                               " + batchInfo.getBatchNm() + " : " + jobName + " end");
		log.warn(
				"#####################################################################################################");
	}

	public JobExecution getJobExecution() {
		return jobExecution;
	}

}
