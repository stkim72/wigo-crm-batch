package com.ceragem.batch.crm.dao;

import com.ceragem.batch.crm.mapper.AiccMapper;
import com.ceragem.batch.crm.model.AiccJadeHrVo;
import com.ceragem.batch.crm.model.AiccJadeOrgVo;

@AiccMapper
public interface AiccDao extends ICrmDao {
	AiccJadeOrgVo selectOrganization(Object param);

	AiccJadeHrVo selectEmployee(Object param);

	int insertOrganization(Object param);

	int updateOrganization(Object param);

	int insertEmployee(Object param);

	int updateEmployee(Object param);

	int selectOrganizationCount(Object param);

	int selectEmployeeCount(Object param);

	int selectLoginCount(Object param);

	int insertLogin(Object param);

	int updateLogin(Object param);

	int selectUserIpCount(Object param);

	int insertUserIp(Object param);

	int selectRoleCount(Object param);

	int insertRole(Object param);
}
