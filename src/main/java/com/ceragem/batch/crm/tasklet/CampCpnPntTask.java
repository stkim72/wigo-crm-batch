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

import com.ceragem.batch.crm.service.CampCpnPntssueService;

import lombok.extern.slf4j.Slf4j;
/**
 * 
 * @ClassName	CampCpnPntTask
 * @author		김성태
 * @date		2022. 10. 5.
 * @Version		1.0
 * @description 쿠폰발행	
 * @Company		Copyright ⓒ wigo.ai. All Right Reserved
 */
@Slf4j
@Component
public class CampCpnPntTask implements Tasklet {
	
	@Autowired
	CampCpnPntssueService campCpnPntssueService;
	
	
	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		log.debug("campCpnPntssueService => campCpnPntssueService.saveCampIssue()");
		int resultCnt = campCpnPntssueService.saveCampIssue();
		log.debug("campCpnPntssueService => pubish Copon Count = "+ resultCnt );
		return RepeatStatus.FINISHED;
	}
	
	
	
	
}