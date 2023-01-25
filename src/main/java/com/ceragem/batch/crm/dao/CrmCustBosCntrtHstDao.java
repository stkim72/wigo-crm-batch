package com.ceragem.batch.crm.dao;

import java.util.List;

import com.ceragem.batch.crm.bos.model.BosCrmContractVo;
import com.ceragem.batch.crm.mapper.CrmMapper;
import com.ceragem.batch.crm.model.CrmMshipPlcyBasVo;
import com.ceragem.batch.crm.model.CrmPointInfoVo;

@CrmMapper
public interface CrmCustBosCntrtHstDao extends ICrmDao {
	List<BosCrmContractVo> selectPointContractList(Object param) throws Exception;

	CrmPointInfoVo selectPointInfo(Object param) throws Exception;

	int insertPointHst(Object param) throws Exception;

	int insertAdvHst(Object param) throws Exception;

	int updateRemainPoint(Object param) throws Exception;

	List<CrmMshipPlcyBasVo> selectPlcyList(Object param);
}
