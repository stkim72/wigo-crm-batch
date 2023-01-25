package com.ceragem.batch.crm.tasklet;

import java.util.List;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ceragem.batch.crm.bos.model.BosOrgVo;
import com.ceragem.batch.crm.bos.service.BosApiService;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @ClassName	BosOrgSyncTask
 * @author		김성태
 * @date		2022. 10. 5.
 * @Version		1.0
 * @description bos 조직동기화	
 * @Company		Copyright ⓒ wigo.ai. All Right Reserved
 */
@Slf4j
@Component
public class BosOrgSyncTask implements Tasklet {
	@Autowired
	BosApiService service;

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		log.debug("BosOrgSyncTask => ");
		List<BosOrgVo> list = service.getCrmOrgList(null);
		for (int i = 0; i < list.size(); i++) {
			BosOrgVo vo = list.get(i);
			log.debug(vo.getOrgzCd() + " : " + vo.getOrgzNm());
		}
		return RepeatStatus.FINISHED;
	}
}
