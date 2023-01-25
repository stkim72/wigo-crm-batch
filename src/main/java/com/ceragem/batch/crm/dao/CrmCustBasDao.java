package com.ceragem.batch.crm.dao;

import java.util.List;

import com.ceragem.batch.crm.mapper.CrmMapper;
import com.ceragem.batch.crm.model.CrmCustBasVo;

@CrmMapper
public interface CrmCustBasDao extends ICrmDao {
	List<CrmCustBasVo> selectDormancyList(Object param) throws Exception;

	List<CrmCustBasVo> selectWithDrawalList(Object param) throws Exception;

	List<CrmCustBasVo> selectMigList(Object param) throws Exception;

	CrmCustBasVo selectDormant(Object param) throws Exception;

	int updateDeleteComplete(Object vo) throws Exception;

	int updateDelete(Object vo) throws Exception;

	int insertDormant(Object vo) throws Exception;

	int deleteContact(Object vo) throws Exception;

	int deleteHshld(Object vo) throws Exception;

	int deleteDormant(Object vo) throws Exception;

	int updateDormant(Object vo) throws Exception;

	int updateEncPhone(Object vo) throws Exception;

	int insertCard(Object vo) throws Exception;

	int insertContact(Object vo) throws Exception;

	int updateMshipTypeCd(Object vo) throws Exception;

	List<CrmCustBasVo> selectPointInfo(Object param) throws Exception;

	int updatePointInfo(Object vo) throws Exception;

	CrmCustBasVo selectNmNo(Object param) throws Exception;

	List<CrmCustBasVo> selectDormancySendList(Object param) throws Exception;

	List<CrmCustBasVo> selectWithDrawalSendList(Object param) throws Exception;

	int updateCustType(Object param) throws Exception;

	int updateRemainPoint(Object param) throws Exception;

	List<CrmCustBasVo> selectBirthdayList(Object param) throws Exception;

	int insertCustCard(Object param) throws Exception;
}
