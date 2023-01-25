package com.ceragem.batch.crm.dao;

@com.ceragem.batch.crm.mapper.CrmMapper
public interface CrmCommonDao extends ICrmDao {

	String getAutoSeq(Object param);

	String endcryptText(String param);

	String decryptText(String param);
}
