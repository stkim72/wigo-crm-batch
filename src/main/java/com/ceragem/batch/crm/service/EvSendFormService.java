package com.ceragem.batch.crm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ceragem.batch.crm.dao.EvSendFormDao;
import com.ceragem.batch.crm.dao.ICrmDao;

/**
 * 
 * @ClassName    EvSendFormService
 * @author    김성태
 * @date    2022. 6. 28.
 * @Version    1.0
 * @description    undefined Service
 * @Company    Copyright ⓒ wigo.ai. All Right Reserved
 */
@Service
public class EvSendFormService extends AbstractCrmService {
   @Autowired
   EvSendFormDao dao;

   @Override
   public ICrmDao getDao() {
       return dao;
   }

	public int insertDetail(Object param) {
		return dao.insertDetail(param);
	}
}
