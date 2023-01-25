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

import com.ceragem.batch.crm.service.CrmCustBasService;

import lombok.extern.slf4j.Slf4j;
/**
 * 
 * @ClassName	CustomerPointInfoTask
 * @author		김성태
 * @date		2022. 10. 5.
 * @Version		1.0
 * @description 포인트 알림	
 * @Company		Copyright ⓒ wigo.ai. All Right Reserved
 */
@Slf4j
@Component
public class CustomerPointInfoTask implements Tasklet {
	
	@Autowired
	CrmCustBasService custService;
	
	
	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		log.debug("Customer CustomerPointInfoTask => custService.savePointInfo()");
		custService.savePointInfo();
		return RepeatStatus.FINISHED;
	}
}
