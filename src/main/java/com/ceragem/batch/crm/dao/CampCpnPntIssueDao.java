package com.ceragem.batch.crm.dao;

import java.util.List;

import com.ceragem.batch.crm.mapper.CrmMapper;
import com.ceragem.batch.crm.model.CrmCampBasVo;

@CrmMapper
public interface CampCpnPntIssueDao extends ICrmDao {

	int pubCoupon(CrmCampBasVo gradeCouponVo);

	List<CrmCampBasVo> getCampIssueList();

	int pubPoint(CrmCampBasVo cVo);
	
	int saveCamHis(CrmCampBasVo cVo);

	int insertMsgIf(CrmCampBasVo cVo);
	
	int insertPushIf(CrmCampBasVo cVo);

}
