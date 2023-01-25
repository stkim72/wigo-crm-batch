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

import com.ceragem.batch.crm.service.CrmOrgBaseService;

import lombok.extern.slf4j.Slf4j;
/**
 * 
 * @ClassName	SyncJadeOrgTasklet
 * @author		김성태
 * @date		2022. 10. 5.
 * @Version		1.0
 * @description	
 * @Company		Copyright ⓒ wigo.ai. All Right Reserved
 */
@Slf4j
@Component
public class SyncJadeOrgTasklet implements Tasklet {
	
	@Autowired
	CrmOrgBaseService orgService;
	
	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		log.debug("SyncJadeOrgTasklet => orgService.saveSyncOrg()");
		String ymd = contribution.getStepExecution().getJobExecution().getJobParameters().getString("ymd");
		orgService.saveSyncOrg(ymd);
		return RepeatStatus.FINISHED;
	}
}
