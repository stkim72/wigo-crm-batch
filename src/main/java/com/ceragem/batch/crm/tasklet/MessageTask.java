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
/**
 * 
 * @ClassName	MessageTask
 * @author		김성태
 * @date		2022. 10. 5.
 * @Version		1.0
 * @description	탈퇴알림
 * @Company		Copyright ⓒ wigo.ai. All Right Reserved
 */
//@Slf4j
@Component
public class MessageTask implements Tasklet {

	@Autowired
	CrmCustBasService custService;

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
//		String ymd = contribution.getStepExecution().getJobExecution().getJobParameters().getString("ymd");
//		String mig = contribution.getStepExecution().getJobExecution().getJobParameters().getString("mig");
//		if(Utilities.isEmpty(mig)) {
//			BatchInfoBasVo batchInfo = (BatchInfoBasVo) contribution.getStepExecution().getJobExecution()
//					.getExecutionContext().get("batchInfo");
//			if (Utilities.isEmpty(ymd)) {
//				ymd = batchInfo.getLastExecDt();
//
//			}
//			Calendar cal = Calendar.getInstance();
//			if (Utilities.isNotEmpty(ymd)) {
//
//				try {
//					Date dt = Constants._DATE_FORMAT.parse(ymd.substring(0,8));
//					cal.setTime(dt);
//					cal.add(Calendar.DATE, 1);
//				} catch (Exception ex) {
//					log.debug(ymd);
//				}
//			}
//			ymd = Constants._DATE_FORMAT.format(cal.getTime());
//		} else {
//			ymd = "20220101";
//		}
//		
//		custService.batchWithDrawal(ymd);
		
		custService.executeWithdrawal();
		return RepeatStatus.FINISHED;
	}
}
