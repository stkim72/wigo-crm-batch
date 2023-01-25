package com.ceragem.batch.crm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ceragem.batch.crm.dao.CrmDormCustBasDao;
import com.ceragem.batch.crm.dao.ICrmDao;

/**
 * 
 * @ClassName CrmDormCustBasService
 * @author 김성태
 * @date 2022. 4. 18.
 * @Version 1.0
 * @description CRM휴면고객기본 Service
 * @Company Copyright ⓒ wigo.ai. All Right Reserved
 */
@Service
public class CrmDormCustBasService extends AbstractCrmService {
	@Autowired
	CrmDormCustBasDao dao;

	@Override
	public ICrmDao getDao() {
		return dao;
	}
}
