package com.ceragem.batch.crm.tasklet;

import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ceragem.batch.crm.bos.model.BosItemVo;
import com.ceragem.batch.crm.bos.service.BosApiService;
import com.ceragem.batch.crm.common.constant.Constants;
import com.ceragem.batch.crm.common.util.Utilities;
import com.ceragem.batch.crm.dao.CrmBosGodsBasDao;
import com.ceragem.batch.crm.model.BatchInfoBasVo;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @ClassName	BosGoodsSyncTask
 * @author		김성태
 * @date		2022. 10. 5.
 * @Version		1.0
 * @description bos 상품 동기화	
 * @Company		Copyright ⓒ wigo.ai. All Right Reserved
 */
@Slf4j
@Component
public class BosGoodsSyncTask implements Tasklet {
	@Autowired
	BosApiService service;

	@Autowired
	CrmBosGodsBasDao dao;
	int totalSize = 0;
	int totalInsert = 0;
	int totalUpdate = 0;
	int currentPage = 0;
	int pageSize = Constants.PAGE_SIZE;

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		log.debug("BosGoodsSyncTask => ");
		List<BosItemVo> readList = new ArrayList<BosItemVo>();
		for (int i = 0; i < 1000; i++) {
			List<BosItemVo> list = service.getCrmItemList(null, i + 1, pageSize);
			if (Utilities.isNotEmpty(list))
				readList.addAll(list);
			if (Utilities.isEmpty(list) || list.size() < pageSize)
				break;
		}

		if (Utilities.isEmpty(readList))
			return RepeatStatus.FINISHED;
		// 전체사용안함처리
		dao.updateDeleteAll(null);
		totalSize = readList.size();
		for (int i = 0; i < readList.size(); i++) {

			BosItemVo vo = readList.get(i);

			BosItemVo old = dao.select(vo);
			if (old == null)
				totalInsert +=dao.insert(vo);
			else
				totalUpdate += dao.update(vo);

		}
		
		BatchInfoBasVo batchInfo = (BatchInfoBasVo) contribution.getStepExecution().getJobExecution().getExecutionContext()
				.get("batchInfo");
		StringBuffer bf = new StringBuffer();
		bf.append("total read : ");
		bf.append(totalSize);

		bf.append("\ntotal write : ");
		bf.append(totalInsert + totalUpdate);

		bf.append("\ntotal insert : ");
		bf.append(totalInsert);

		bf.append("\ntotal update : ");
		bf.append(totalUpdate);


		batchInfo.addErrorLog(bf.toString());
		return RepeatStatus.FINISHED;
	}
}
