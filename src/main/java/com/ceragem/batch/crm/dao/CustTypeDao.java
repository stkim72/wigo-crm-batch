package com.ceragem.batch.crm.dao;

import java.util.List;

import com.ceragem.batch.crm.mapper.CrmMapper;
import com.ceragem.batch.crm.model.CrmCustBasVo;


@CrmMapper
public interface CustTypeDao extends ICrmDao {

	List<CrmCustBasVo> getCustList(CrmCustBasVo crmCustBasVo);

	int updCustTypeCd(List<CrmCustBasVo> crmCustBasVo);
	
	int insCustTypeCd(List<CrmCustBasVo> crmCustBasVo);

}
