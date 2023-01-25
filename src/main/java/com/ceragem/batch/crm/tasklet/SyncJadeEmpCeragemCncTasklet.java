/**
 * 
 */
package com.ceragem.batch.crm.tasklet;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ceragem.batch.crm.service.CrmEmpBaseService;

import lombok.extern.slf4j.Slf4j;
/**
 * 
 * @ClassName	SyncJadeEmpCeragemCncTasklet
 * @author		김성태
 * @date		2022. 10. 5.
 * @Version		1.0
 * @description	
 * @Company		Copyright ⓒ wigo.ai. All Right Reserved
 */
@Slf4j
@Component
public class SyncJadeEmpCeragemCncTasklet implements Tasklet {

	@Autowired
	CrmEmpBaseService empService;

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		log.debug("SyncJadeEmpCeragemTasklet => empService.saveSyncCeragemCnC()");
//		if (Utilities
//				.isNotEmpty(contribution.getStepExecution().getJobExecution().getJobParameters().getString("mig"))) {
//			Calendar calStart = Calendar.getInstance();
//			Calendar cur = Calendar.getInstance();
//			calStart.set(Constants.MIG_START_YEAR, Constants.MIG_START_MONTH, Constants.MIG_START_DAY,0,0,0);
//
//			while (calStart.getTimeInMillis() <= cur.getTimeInMillis()) {
//				
//				String ymd = Utilities.getDateString(calStart.getTime());
//				empService.saveSyncCeragemCnC(ymd);
//				calStart.add(Calendar.DATE, 1);
//			}
//		} else {
//			String ymd = contribution.getStepExecution().getJobExecution().getJobParameters().getString("ymd");
//			if (Utilities.isEmpty(ymd))
//				ymd = Utilities.getDateString();
//			empService.saveSyncCeragemCnC(ymd);
//		}
		
		String ymd = contribution.getStepExecution().getJobExecution().getJobParameters().getString("ymd");
		empService.saveSyncCeragemCnC(ymd);
		return RepeatStatus.FINISHED;
	}
}
