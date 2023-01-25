package com.ceragem.batch.crm.tasklet;

import java.util.List;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ceragem.batch.crm.bos.model.BosEmpVo;
import com.ceragem.batch.crm.bos.service.BosApiService;

import lombok.extern.slf4j.Slf4j;
/**
 * 
 * @ClassName	BosEmpSyncTask
 * @author		김성태
 * @date		2022. 10. 5.
 * @Version		1.0
 * @description 보스 직원 동기화	
 * @Company		Copyright ⓒ wigo.ai. All Right Reserved
 */
@Slf4j
@Component
public class BosEmpSyncTask implements Tasklet {
	@Autowired
	BosApiService service;

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		log.debug("BosEmpSyncTask => ");
		List<BosEmpVo> list = service.getCrmEmpList(null);
		for (int i = 0; i < list.size(); i++) {
			BosEmpVo vo = list.get(i);
			log.debug(vo.getOrgmbNo() + " : " + vo.getOrgmbNm());
		}
		return RepeatStatus.FINISHED;
	}
}
