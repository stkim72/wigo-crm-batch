package com.ceragem.batch.crm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ceragem.batch.crm.common.util.Utilities;
import com.ceragem.batch.crm.dao.BatchExecHstDao;
import com.ceragem.batch.crm.dao.ICrmDao;
import com.ceragem.batch.crm.model.BatchExecHstVo;
import com.ceragem.batch.crm.model.BatchInfoBasVo;

/**
 * 
 * @ClassName BatchExecHstService
 * @author 김성태
 * @date 2022. 5. 26.
 * @Version 1.0
 * @description 배치실행이력 Service
 * @Company Copyright ⓒ wigo.ai. All Right Reserved
 */
@Service
public class BatchExecHstService extends AbstractCrmService {
	@Autowired
	BatchExecHstDao dao;

	@Override
	public ICrmDao getDao() {
		return dao;
	}

	@Override
	public int insert(Object param) throws Exception {
		int ret = super.insert(param);
		BatchExecHstVo vo = (BatchExecHstVo) param;
		if (Utilities.isNotEmpty(vo.getBatchCd()))
			updateBatch(param);
		return ret;
	}

	public int updateBatch(Object param) throws Exception {
		return dao.updateBatch(param);
	}

	public BatchInfoBasVo getBatchInfo(Object param) throws Exception {
		return dao.selectBatchInfo(param);
	}
}
