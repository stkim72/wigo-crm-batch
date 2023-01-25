package com.ceragem.batch.crm.dao;

import java.util.HashMap;
import java.util.List;

import com.ceragem.batch.crm.mapper.CrmMapper;
import com.ceragem.batch.crm.model.GradeCouponVo;

@CrmMapper
public interface GrpCoupnIssueDao extends ICrmDao {

	List<GradeCouponVo> getGrdCouponList(GradeCouponVo gradeCouponVo);

	int pubCoupon(GradeCouponVo gradeCouponVo);

	List<HashMap<String, Object>> getCoupnCancelFor7Day();

}
