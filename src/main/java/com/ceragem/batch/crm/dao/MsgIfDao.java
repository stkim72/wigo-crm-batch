package com.ceragem.batch.crm.dao;

import java.util.List;

import com.ceragem.batch.crm.mapper.CrmMapper;
import com.ceragem.batch.crm.model.CrmComnCodeVo;

@CrmMapper
public interface MsgIfDao extends ICrmDao {
	List<CrmComnCodeVo> selectCodeList();
}
