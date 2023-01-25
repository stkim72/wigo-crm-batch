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

import com.ceragem.batch.crm.service.AdvncmtService;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @ClassName	AdvncmtTask
 * @author		김성태
 * @date		2022. 10. 5.
 * @Version		1.0
 * @description	승급점수 Task
 * @Company		Copyright ⓒ wigo.ai. All Right Reserved
 */
@Slf4j
@Component
public class AdvncmtTask implements Tasklet {

	@Autowired
	AdvncmtService grdAdvncmtService;

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		log.debug("AdvncmtTask => grdAdvncmtService.saveAdvncmt()");
		int resultCnt = grdAdvncmtService.saveAdvncmt();
		log.debug("AdvncmtTask => pubish Copon Count = " + resultCnt);
		return RepeatStatus.FINISHED;
	}

}