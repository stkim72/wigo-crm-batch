package com.ceragem.batch.crm.dao;

import com.ceragem.batch.crm.mapper.CrmMapper;
import com.ceragem.batch.crm.model.BatchInfoBasVo;

@CrmMapper
public interface BatchExecHstDao extends ICrmDao {

	int updateBatch(Object param) throws Exception;

	BatchInfoBasVo selectBatchInfo(Object param)throws Exception;

}
