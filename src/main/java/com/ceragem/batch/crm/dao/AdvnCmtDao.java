package com.ceragem.batch.crm.dao;

import java.util.List;

import com.ceragem.batch.crm.mapper.CrmMapper;
import com.ceragem.batch.crm.model.AdvnCmtVo;

@CrmMapper
public interface AdvnCmtDao extends ICrmDao {

	List<AdvnCmtVo> getAdmtList();

	int insertDay(List<AdvnCmtVo> list);

	int insertDay(AdvnCmtVo list);

	int pubAdvnPoint(List<AdvnCmtVo> advnSubList);
	

	int pubAdvnPoint(AdvnCmtVo advnSubList);

	int insertAdvnMsg();

	int updateOne(AdvnCmtVo advnSubList);

	int insertOne(AdvnCmtVo advnSubList);

	int insertDayOne(AdvnCmtVo advnSubList);


}
