package com.ceragem.batch.crm.service;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ceragem.batch.crm.common.constant.Constants;
import com.ceragem.batch.crm.common.util.CrmJobListener;
import com.ceragem.batch.crm.common.util.Utilities;
import com.ceragem.batch.crm.dao.CrmCustBasDao;
import com.ceragem.batch.crm.dao.ICrmDao;
import com.ceragem.batch.crm.model.BatchInfoBasVo;
import com.ceragem.batch.crm.model.CrmApiDataVo;
import com.ceragem.batch.crm.model.CrmCustBasVo;
import com.ceragem.batch.crm.model.CrmCustCntplcBasVo;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @ClassName CrmCustBasService
 * @author 김성태
 * @date 2022. 4. 18.
 * @Version 1.0
 * @description CRM고객기본 Service
 * @Company Copyright ⓒ wigo.ai. All Right Reserved
 */
@Slf4j
@Service
public class CrmCustBasService extends AbstractCrmService {
//	private static final int DORMANCY_MONTH = 12;
	private static final int WITHDRAWL_MONTH = -36;
	@Autowired
	CrmCustBasDao dao;

	@Autowired
	SendMessageService msgService;

	@Autowired
	CrmApiService apiService;
	@Autowired
	CrmJobListener jobListener;

	@Override
	public ICrmDao getDao() {
		return dao;
	}

	public void executeWithdrawal() throws Exception {
		msgService.initTemplate();
		Map<String, Object> param = new HashMap<String, Object>();
		List<CrmCustBasVo> list = null;

//		1. 탈회 한달전
//		param.put("messageMode", "Y");
//		param.put("offsetMonth", WITHDRAWL_MONTH + 1);
//		param.put("offsetDay", 0);
//		param.put("custStatusCd", "002");
//		param.put("category", Constants._MSG_WITHDRAWALBEFOREMONTH);
//		list = dao.selectWithDrawalSendList(param);
//		for (int i = 0; i < list.size(); i++) {
//			CrmCustBasVo vo = list.get(i);
//			msgService.sendCustomerStatus(vo, Constants._MSG_WITHDRAWALBEFOREMONTH);
//		}
//		
//		msgService.sendCustomerStatusMail(list, Constants._MSG_WITHDRAWALBEFOREMONTH);
//		2. 탈회 하루전
//		param.put("messageMode", "Y");
//		param.put("offsetMonth", WITHDRAWL_MONTH);
//		param.put("offsetDay", 1);
//		param.put("custStatusCd", "002");
//		param.put("category", Constants._MSG_WITHDRAWALBEFOREDAY);
//		list = dao.selectWithDrawalSendList(param);
//		for (int i = 0; i < list.size(); i++) {
//			CrmCustBasVo vo = list.get(i);
//			msgService.sendCustomerStatus(vo, Constants._MSG_WITHDRAWALBEFOREDAY);
//		}

//		msgService.sendCustomerStatusMail(list, Constants._MSG_WITHDRAWALBEFOREDAY);
//		3. 탈회 처리
		param.put("messageMode", null);
		param.put("offsetMonth", WITHDRAWL_MONTH);
		param.put("offsetDay", 0);
		param.put("custStatusCd", "002");
		param.put("category", Constants._MSG_WITHDRAWALPROCESS);
		list = dao.selectWithDrawalSendList(param);
//		String procDt = Utilities.getDateString("-");
		for (int i = 0; i < list.size(); i++) {
			CrmCustBasVo vo = list.get(i);
			updateWithDrawal(vo);
//			if (updateWithDrawal(vo) > 0)
//				msgService.sendCustomerStatus(vo, Constants._MSG_WITHDRAWALPROCESS,procDt);
		}
//		msgService.sendCustomerStatusMail(list, Constants._MSG_WITHDRAWALPROCESS);

//		4. 탈회 한달후
		dao.updateDeleteComplete(param);
		dao.insertCustCard(param);

	}

	public void executeDormancy() throws Exception {
		msgService.initTemplate();
		Map<String, Object> param = new HashMap<String, Object>();
//		1. 휴면 한달전
		param.put("messageMode", "Y");
		param.put("offsetMonth", 1);
		param.put("offsetDay", 0);
		param.put("custStatusCd", "001");
		param.put("category", Constants._MSG_DORMBEFOREMONTH);
		List<CrmCustBasVo> list = dao.selectDormancySendList(param);
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, 1);

		String dormDt = Utilities.getDateString(cal.getTime(), "-");
		for (int i = 0; i < list.size(); i++) {
			CrmCustBasVo vo = list.get(i);
			msgService.sendCustomerStatus(vo, Constants._MSG_DORMBEFOREMONTH, dormDt);
		}
//		msgService.sendCustomerStatusMail(list, Constants._MSG_DORMBEFOREMONTH);
//		2. 휴면 하루전
//		param.put("messageMode", "Y");
//		param.put("offsetMonth", 0);
//		param.put("offsetDay", 1);
//		param.put("custStatusCd", "001");
//		param.put("category", Constants._MSG_DORMBEFOREDAY);
//		list = dao.selectDormancySendList(param);
//		for (int i = 0; i < list.size(); i++) {
//			CrmCustBasVo vo = list.get(i);
//			msgService.sendCustomerStatus(vo, Constants._MSG_DORMBEFOREDAY);
//		}
//		msgService.sendCustomerStatusMail(list, Constants._MSG_DORMBEFOREDAY);
//		3. 휴면 처리
		param.put("messageMode", null);
		param.put("offsetMonth", 0);
		param.put("offsetDay", 0);
		param.put("custStatusCd", "001");
		param.put("category", Constants._MSG_DORMPROCESS);
		list = dao.selectDormancySendList(param);
		for (int i = 0; i < list.size(); i++) {
			CrmCustBasVo vo = list.get(i);
			updateDormancy(vo);
		}
	}

	public List<CrmCustBasVo> getMigList() throws Exception {
		Map<String, Object> param = new HashMap<>();
		param.put("defaultPhone", "Y");
		param.put("firstRecordIndex", 1);
		param.put("lastRecordIndex", 10000000);
		List<CrmCustBasVo> list = dao.selectMigList(param);
		return list;

	}

	public void savePointInfo() throws Exception {
		Map<String, Object> param = new HashMap<>();

		List<CrmCustBasVo> list = dao.selectPointInfo(param);
		for (int i = 0; i < list.size(); i++) {
			CrmCustBasVo vo = list.get(i);
			dao.updatePointInfo(vo);
		}

	}

	public void insertDefaultContact(CrmCustBasVo vo) throws Exception {
		CrmCustCntplcBasVo contact = new CrmCustCntplcBasVo();
		contact.setItgCustNo(vo.getItgCustNo());
		contact.setTelBkDgtNo(vo.getMphonBkDgtNo());
		contact.setTelNo(vo.getMphonNo());
		contact.setDelYn("N");
		contact.setEmailAddr(vo.getEmailAddr());
		contact.setZipCd(vo.getZipCd());
		contact.setAddr1Ctnts(vo.getAddr1Ctnts());
		contact.setAddr2Ctnts(vo.getAddr2Ctnts());
		contact.setCntplcTypeCd("004");
		contact.setRepCntplcYn("Y");
		dao.insertContact(contact);
	}

	public int updateNormal(CrmCustBasVo param) throws Exception {
		CrmCustBasVo vo = dao.select(param);
		if (vo == null) {
			return 0;
		}
		String status = vo.getCustStatusCd();
		if ("001".equals(status)) {
			return 0;
		}
		CrmCustBasVo sel = dao.selectDormant(param);
		if (sel == null) {
			return 0;
		}
		sel.setCustStatusCd("001");
		dao.deleteDormant(sel);
		return dao.updateDormant(sel);
	}

	public int updateDormancy(CrmCustBasVo vo) throws Exception {

		CrmApiDataVo result = apiService.getApiData("/crm/v1.0/membership/dormancy/" + vo.getItgCustNo(), null, "POST",
				false);
		if (!"IAR0200".equals(result.getCode())) {
			try {
				BatchInfoBasVo batchInfo = (BatchInfoBasVo) jobListener.getJobExecution().getExecutionContext()
						.get("batchInfo");
				batchInfo.addErrorLog("[휴면실패][/crm/v1.0/membership/dormancy/" + vo.getItgCustNo() + "["
						+ result.getCode() + "]" + result.getMessage());
			} catch (Exception ex) {
				log.debug(ex.getMessage());
			}

			log.error("[휴면실패][/crm/v1.0/membership/dormancy/" + vo.getItgCustNo() + "[" + result.getCode() + "]"
					+ result.getMessage());
			return 0;
		}
		return 1;
	}

	public int updateWithDrawal(CrmCustBasVo vo) throws Exception {
		CrmApiDataVo result = apiService.getApiData("/crm/v1.0/membership/" + vo.getItgCustNo(), null, "DELETE", false);
		if (!"IAR0200".equals(result.getCode())) {
			log.error("[탈회실패][/crm/v1.0/membership/" + vo.getItgCustNo() + "[" + result.getCode() + "]"
					+ result.getMessage());
			try {
				BatchInfoBasVo batchInfo = (BatchInfoBasVo) jobListener.getJobExecution().getExecutionContext()
						.get("batchInfo");
				batchInfo.addErrorLog("[탈회실패][/crm/v1.0/membership/" + vo.getItgCustNo() + "[" + result.getCode() + "]"
						+ result.getMessage());
			} catch (Exception ex) {
				log.debug(ex.getMessage());
			}
			return 0;
		}
		return 1;

	}

	public int updateCustType(CrmCustBasVo cust) throws Exception {
		return dao.updateCustType(cust);

	}
}
