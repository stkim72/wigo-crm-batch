package com.ceragem.batch.crm.dao;

import java.util.List;

import com.ceragem.batch.crm.mapper.CrmMapper;
import com.ceragem.batch.crm.model.BatchMap;

@CrmMapper
public interface CrmEventBatchDao {
	List<BatchMap> selectExpirePointList(Object param) throws Exception;
	List<BatchMap> selectExpireTalkList(Object param) throws Exception;
	int updateExpirePoint(Object param) throws Exception;
}
