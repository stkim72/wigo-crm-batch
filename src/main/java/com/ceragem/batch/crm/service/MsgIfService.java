package com.ceragem.batch.crm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ceragem.batch.crm.dao.ICrmDao;
import com.ceragem.batch.crm.dao.MsgIfDao;
import com.ceragem.batch.crm.model.CrmComnCodeVo;

/**
 * 
 * @ClassName MsgIfService
 * @author 김성태
 * @date 2022. 6. 28.
 * @Version 1.0
 * @description undefined Service
 * @Company Copyright ⓒ wigo.ai. All Right Reserved
 */
@Service
public class MsgIfService extends AbstractCrmService {
	@Autowired
	MsgIfDao dao;

	@Override
	public ICrmDao getDao() {
		return dao;
	}

	public List<CrmComnCodeVo> getCodeList() {
		return dao.selectCodeList();
	}
}
