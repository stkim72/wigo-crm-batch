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

import com.ceragem.batch.crm.model.GradeCouponVo;
import com.ceragem.batch.crm.service.GrdCoupnIssueService;

import lombok.extern.slf4j.Slf4j;
/**
 * 
 * @ClassName	GrdCoupn001IssueTask
 * @author		김성태
 * @date		2022. 10. 5.
 * @Version		1.0
 * @description	
 * @Company		Copyright ⓒ wigo.ai. All Right Reserved
 */
@Slf4j
@Component
public class GrdCoupn001IssueTask implements Tasklet {
	
	@Autowired
	GrdCoupnIssueService grdCoupnIssueService;
	
	
	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		log.debug("GrdCoupn001IssueTask => grdCoupnIssueService.saveGrdCoupn()");
		
		GradeCouponVo gradeCouponVo = new GradeCouponVo();
		gradeCouponVo.setMshipGradeCd("001");
		
		int resultCnt = grdCoupnIssueService.saveGrdCoupn( gradeCouponVo );
		
		log.debug("GrdCoupn001IssueTask => pubish Copon Count = "+ resultCnt );
		
		return RepeatStatus.FINISHED;
		
	}
	
	
	
	
}