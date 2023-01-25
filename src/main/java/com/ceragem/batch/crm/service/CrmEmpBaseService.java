package com.ceragem.batch.crm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ceragem.batch.crm.common.util.Utilities;
import com.ceragem.batch.crm.dao.AiccDao;
import com.ceragem.batch.crm.dao.CrmCustBasDao;
import com.ceragem.batch.crm.dao.CrmEmpBaseDao;
import com.ceragem.batch.crm.dao.CrmUserBaseDao;
import com.ceragem.batch.crm.dao.ICrmDao;
import com.ceragem.batch.crm.model.CrmCustBasVo;
import com.ceragem.batch.crm.model.CrmCustCntplcBasVo;
import com.ceragem.batch.crm.model.CrmJadeHrVo;
import com.ceragem.batch.crm.model.CrmLoginUserVo;
import com.ceragem.batch.crm.model.CrmUserBaseVo;

/**
 * 
 * @ClassName	CrmEmpBaseService
 * @author		김성태
 * @date		2022. 10. 5.
 * @Version		1.0
 * @description 직원서비스	
 * @Company		Copyright ⓒ wigo.ai. All Right Reserved
 */
@Service
public class CrmEmpBaseService extends AbstractCrmService {
	@Autowired
	CrmEmpBaseDao dao;
	@Autowired
	CrmUserBaseDao userDao;
	@Autowired
	AiccDao aiccDao;
	@Autowired
	CrmCustBasDao custDao;

	@Autowired
	CrmJadeService jadeService;

	@Value("${spring.jade.ceragem}")
	String ceragemCd;
	@Value("${spring.jade.ceragem-cns}")
	String ceragemCdnCd;

	@Override
	public ICrmDao getDao() {
		return dao;
	}

	public void saveSyncCeragem(String ymd) throws Exception {
		List<CrmLoginUserVo> list = jadeService.getHrList(ymd, ceragemCd);
		insertList(list);
	}

	public void saveSyncCeragemCnC(String ymd) throws Exception {
		List<CrmLoginUserVo> list = jadeService.getHrList(ymd, ceragemCdnCd);
		insertList(list);
	}

	public void saveSyncEmp(String ymd) throws Exception {
//		List<CrmLoginUserVo> list = jadeService.getCeragemHrList(ymd);
//		list.addAll(jadeService.getCeragemCnsHrList(ymd));
		List<CrmLoginUserVo> list = jadeService.getCeragemEmpList(ymd);
		insertList(list);
	}

	@Override
	public int insert(Object param) throws Exception {
		CrmLoginUserVo v = (CrmLoginUserVo) param;
		if (Utilities.isNotEmpty(v.getEmpNm()))
			v.setEmpNm(v.getEmpNm().trim());
		if (Utilities.isNotEmpty(v.getMobileNo()))
			v.setMobileNo(v.getMobileNo().trim());
		if (Utilities.isEmpty(v.getOrgId()))
			return 0;
		if (Utilities.isEmpty(v.getEmailAddr()))
			v.setLoginId(v.getEmpId());
		else
			v.setLoginId(v.getEmailAddr());
//		String phone = v.getMobileNo();
//		v.setMphonNoEncVal(Utilities.encrypt(phone));
//		v.setMobileNo("");
		v.setDelYn("30".equals(v.getStatusCd()) ? "Y" : "N");

		syncAicc(param);
		if (Utilities.isNotEmpty(v.getMphonNo()) && Utilities.isNotEmpty(v.getEmpNm())) {
			CrmCustBasVo cust = new CrmCustBasVo();
			cust.setCustNm(v.getEmpNm());
			cust.setMphonNo(v.getMphonNo());
			cust.setEmailAddr(v.getEmailAddr());
			cust.setBirthday(v.getBithYmd());
			cust.setRegChlCd("CRM");
			cust.setRegrId("CRM");
			cust.setAmdrId("CRM");
			cust.setMshipSbscYn("N");
			cust.setMshipSbscDt(null);
//			cust.setMshipSbscYn("Y");
//			cust.setMshipSbscDt(Utilities.getDateTimeString());
			cust.setMshipGradeCd("001");
			cust.setMshipGradeChngDt(Utilities.getDateTimeString());
			cust.setMshipChlCd("CRM");

			CrmCustBasVo emp = custDao.selectNmNo(cust);
			if (emp != null) {
				cust.setItgCustNo(emp.getItgCustNo());
			}
			if ("Y".equals(v.getDelYn()) || Utilities.isNotEmpty(v.getRetireYmd())) {

				cust.setMshipTypeCd("003");
				if (emp != null) {
					custDao.updateMshipTypeCd(cust);
				}
			} else {
				cust.setMshipTypeCd("001");
				if (emp != null)
					custDao.updateMshipTypeCd(cust);
				else {
					custDao.insert(cust);
					CrmCustCntplcBasVo contact = new CrmCustCntplcBasVo();
					contact.setItgCustNo(cust.getItgCustNo());
					contact.setTelNo(cust.getMphonNo());
					contact.setEmailAddr(cust.getEmailAddr());
					contact.setCntplcTypeCd("004");
					contact.setRegChlCd("CRM");
					contact.setRegrId("CRM");
					contact.setAmdrId("CRM");
					custDao.insertContact(contact);
					custDao.insertCard(cust);
				}
			}
		}

		CrmJadeHrVo vo = get(param);
		if (vo == null)
			return super.insert(param);
		else
			return super.update(param);
	}

	private void syncAicc(Object param) throws Exception {
		CrmUserBaseVo usr = userDao.select(param);
		if (usr == null) {
			userDao.insert(param);
		} else {
			userDao.updateEmp(param);
		}
		int cnt = aiccDao.selectEmployeeCount(param);
		if (cnt == 0) {
			aiccDao.insertEmployee(param);
		} else {
			aiccDao.updateEmployee(param);
		}

		cnt = aiccDao.selectLoginCount(param);
		if (cnt == 0) {
			aiccDao.insertLogin(param);
		} else {
			aiccDao.updateLogin(param);
		}

		cnt = aiccDao.selectUserIpCount(param);
		if (cnt == 0) {
			aiccDao.insertUserIp(param);
		}

		cnt = aiccDao.selectRoleCount(param);
		if (cnt == 0) {
			aiccDao.insertRole(param);
		}

	}

	/**
	 * 암호화
	 * 
	 * @param text
	 * @return
	 * @throws Exception
	 */
//	static public String encrypt(String text) throws Exception {
//		return KisaSeed256.encrypt(text);
//	}

	/**
	 * 복호화
	 * 
	 * @param text
	 * @return
	 * @throws Exception
	 */
//	static public String decrypt(String text) throws Exception {
//		return KisaSeed256.decrypt(text);
//	}
}
