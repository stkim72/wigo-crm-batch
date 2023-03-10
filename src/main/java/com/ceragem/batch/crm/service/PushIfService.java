package com.ceragem.batch.crm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ceragem.batch.crm.dao.ICrmDao;
import com.ceragem.batch.crm.dao.PushIfDao;

/**
 * 
 * @ClassName    PushIfService
 * @author    κΉμ±ν
 * @date    2022. 6. 28.
 * @Version    1.0
 * @description    undefined Service
 * @Company    Copyright β wigo.ai. All Right Reserved
 */
@Service
public class PushIfService extends AbstractCrmService {
   @Autowired
   PushIfDao dao;

   @Override
   public ICrmDao getDao() {
       return dao;
   }
}
