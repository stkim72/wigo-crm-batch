package com.ceragem.batch.crm.tasklet;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ceragem.batch.crm.bos.service.BosApiService;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @ClassName	BosCustomerMigTask
 * @author		김성태
 * @date		2022. 10. 5.
 * @Version		1.0
 * @description 	
 * @Company		Copyright ⓒ wigo.ai. All Right Reserved
 */
@Slf4j
@Component
public class BosCustomerMigTask implements Tasklet {
	@Autowired
	BosApiService service;
//	@Autowired
//	BatchExecHstService batchService;

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		log.debug("BosCustomerMigTask => ");
		
		// 고객 정보 가여오기
		// 고객정보 없으면 입력
		// 있으면 통합고객번호로 bos data update 하는 api 호출(아직없음)
		// 정기점검,계약정보,상품 업데이트
		
//		service.getCustomerDtl(null)
		
		
//		BatchInfoBasVo batchInfo = (BatchInfoBasVo) contribution.getStepExecution().getJobExecution()
//				.getExecutionContext().get("batchInfo");
//		String toDate = Utilities.getDateString();
//		String fromDate = batchInfo == null ? null : batchInfo.getLastExecDt();
//		if (Utilities.isEmpty(fromDate))
//			fromDate = "20000101";
//		else
//			fromDate = fromDate.substring(0, 8);
//		List<BosCrmServiceVo> list = service.getCrmServiceList(null, fromDate, toDate);
//		for (int i = 0; i < list.size(); i++) {
//			BosCrmServiceVo vo = list.get(i);
//			log.debug(vo.getRgipTyCd() + " : " + vo.getRgipTyCdnm());
//		}
		return RepeatStatus.FINISHED;
	}
}
