package com.ceragem.batch.crm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ceragem.batch.crm.dao.AiccDao;
import com.ceragem.batch.crm.dao.CrmOrgBaseDao;
import com.ceragem.batch.crm.dao.ICrmDao;
import com.ceragem.batch.crm.model.CrmJadeOrgVo;

/**
 * 
 * @ClassName	CrmOrgBaseService
 * @author		김성태
 * @date		2022. 10. 5.
 * @Version		1.0
 * @description CRM 조직(부서) 서비스	
 * @Company		Copyright ⓒ wigo.ai. All Right Reserved
 */
@Service
public class CrmOrgBaseService extends AbstractCrmService {
	@Autowired
	CrmOrgBaseDao dao;

	@Autowired
	CrmJadeService jadeService;

	@Autowired
	AiccDao aiccDao;

	@Override
	public ICrmDao getDao() {
		return dao;
	}

	public void saveSyncOrg(String ymd) throws Exception {
		List<CrmJadeOrgVo> list = jadeService.getCeragemOrgList(ymd);
		insertList(list);
	}

	@Override
	public int insert(Object param) throws Exception {
		CrmJadeOrgVo vo = get(param);
		int cnt = aiccDao.selectOrganizationCount(param);
		if (cnt > 0) {
			aiccDao.updateOrganization(param);
		} else {
			aiccDao.insertOrganization(param);
		}
//		AiccJadeOrgVo  src = Utilities.beanToBean(param, AiccJadeOrgVo.class);
//		if (org == null) {
//			aiccDao.insertOrganization(src);
//		} else {
//			aiccDao.updateOrganization(src);
//		}
		if (vo == null)
			return super.insert(param);
		else
			return super.update(param);

	}

}
