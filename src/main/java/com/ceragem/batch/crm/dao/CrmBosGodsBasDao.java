package com.ceragem.batch.crm.dao;

import com.ceragem.batch.crm.mapper.CrmMapper;

@CrmMapper
public interface CrmBosGodsBasDao extends ICrmDao {
	int updateDeleteAll(Object param) throws Exception;
}
