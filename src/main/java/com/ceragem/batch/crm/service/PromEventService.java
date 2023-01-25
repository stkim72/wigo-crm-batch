package com.ceragem.batch.crm.service;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ceragem.batch.crm.common.util.Utilities;
import com.ceragem.batch.crm.dao.ICrmDao;
import com.ceragem.batch.crm.dao.PromEventDao;

/**
 * 
 * @ClassName CrmCustBasService
 * @author 김은성
 * @date 2022. 4. 18.
 * @Version 1.0
 * @description CRM고객기본 Service
 * @Company Copyright ⓒ wigo.ai. All Right Reserved
 */

//@Slf4j
@Service
public class PromEventService extends AbstractCrmService {

	@Autowired
	PromEventDao dao;

	@Override
	public ICrmDao getDao() {
		return dao;
	}

	public List<HashMap<String, Object>> getPromEventAll() {
		return dao.getPromEventAll();
	}

	public int checkPointHst(HashMap<String, Object> chkMap) {
		return dao.checkPointHst(chkMap);
	}

	public int checkCoupnHst(HashMap<String, Object> chkMap) {
		
		HashMap<String, Object> voTem = dao.selectMaster(chkMap);
		if (voTem == null) {
			return -1;
		}

		// 발행기간체크
		long now = System.currentTimeMillis();
		String toDay = new SimpleDateFormat("yyyyMMdd",Locale.KOREA).format(now);
		if (Utilities.parseInt(voTem.get("TO_PBLS_STD_DAY")) < Integer.parseInt(toDay) || Utilities.parseInt(voTem.get("FROM_PBLS_STD_DAY")) > Integer.parseInt(toDay)) {
			return -1;
		}
		
		// 일최대발급체크
		int dayCoupnCnt = dao.getDayCoupnCnt(chkMap); // 당일 발급된수량
		int maxIssueCnt = Utilities.parseInt(voTem.get("MAX_ISSUE_CNT"));	 // 마스터 설정 수량

		// 일최대 발급 체크
		if ((dayCoupnCnt + 1) > maxIssueCnt) {
			return -1;
		}

		// 적용회원 체크
		HashMap<String, Object> custVo = dao.getCustInfo(chkMap);
//		String mshipTypeCd = String.valueOf(voTem.get("MSHIP_TYPE_CDS"));
		String applyMshpGradeCtnts = String.valueOf(voTem.get("APPLY_MSHP_GRADE_CTNTS"));
		
		if (custVo == null) {
			return -1;
		}
		
		if (!Arrays.asList(applyMshpGradeCtnts.split(",")).contains(custVo.get("MSHIP_GRADE_CD"))) {
			return -1;
		} else if (custVo.get("MSHIP_TYPE_CD").equals("002") && (custVo.get("CPRT_CMP_NO") == null || !(voTem.get("CPRT_CMP_NO").equals(custVo.get("CPRT_CMP_NO")))) ) {
//			if (custVo.get("CPRT_CMP_NO") == null || !(voTem.get("CPRT_CMP_NO").equals(custVo.get("CPRT_CMP_NO")))) {
				return -1;
//			}
			// return -1;
		}
		
		return dao.checkCoupnHst(chkMap);
	}

	public int savePromEventCoupn(HashMap<String, Object> chkMap) {
		return dao.savePromEventCoupn(chkMap);
	}

	public Map<String, Object> selectStor(HashMap<String, Object> alarm) {
		return dao.selectStor(alarm);
	}

	public List<HashMap<String, Object>> getBosAndPosOrderCust(HashMap<String, Object> proMap) {
		return dao.getBosAndPosOrderCust(proMap);
	}

	public String getAutoSeq(Map<String, Object> param) {
		return dao.getAutoSeq(param);
	}

	
}
