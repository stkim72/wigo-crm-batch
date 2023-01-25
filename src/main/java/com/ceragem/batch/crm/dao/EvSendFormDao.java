package com.ceragem.batch.crm.dao;

import com.ceragem.batch.crm.mapper.CrmMapper;

@CrmMapper
public interface EvSendFormDao extends ICrmDao {

	int insertDetail(Object param);

}
