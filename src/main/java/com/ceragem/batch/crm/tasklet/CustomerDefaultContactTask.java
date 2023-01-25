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
 * @ClassName	CustomerDefaultContactTask
 * @author		김성태
 * @date		2022. 10. 5.
 * @Version		1.0
 * @description 기본 연락처	
 * @Company		Copyright ⓒ wigo.ai. All Right Reserved
 */
@Slf4j
@Component
public class CustomerDefaultContactTask implements Tasklet {
	
	@Autowired
	CrmCustBasService custService;
	
	
	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		log.debug("Customer default Phone number  => custService.saveDefaultContact()");
//		custService.saveDefaultContact();
		return RepeatStatus.FINISHED;
	}
}
