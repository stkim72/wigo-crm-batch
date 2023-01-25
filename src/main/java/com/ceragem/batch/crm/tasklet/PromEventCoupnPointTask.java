package com.ceragem.batch.crm.tasklet;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ceragem.batch.crm.common.constant.Constants;
import com.ceragem.batch.crm.common.util.Utilities;
import com.ceragem.batch.crm.dao.CrmCustBasDao;
import com.ceragem.batch.crm.model.CrmApiDataVo;
import com.ceragem.batch.crm.service.CrmApiService;
import com.ceragem.batch.crm.service.PromEventService;
import com.ceragem.batch.crm.service.SendMessageService;

import lombok.extern.slf4j.Slf4j;
/**
 * 
 * @ClassName	PromEventCoupnPointTask
 * @author		김성태
 * @date		2022. 10. 5.
 * @Version		1.0
 * @description	
 * @Company		Copyright ⓒ wigo.ai. All Right Reserved
 */
@Slf4j
@Component
public class PromEventCoupnPointTask implements Tasklet {

	@Autowired
	SendMessageService msgService;

	@Autowired
	PromEventService proService;

	@Autowired
	CrmCustBasDao custDao;
	
	@Autowired
	CrmApiService apiService;
	
	@Value("${spring.api.url.point}")
	String apiUrl;

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		log.debug("PromEventCoupnPointTask => ");

		List<HashMap<String, Object>> proData = proService.getPromEventAll();
		if (proData == null) {
			return null;
		}

		for (int i = 0; i < proData.size(); i++) {
			HashMap<String, Object> proMap = proData.get(i); // 프로모션 기본

//			매출 정리(포인트와 연결) # 프로모션은 결제금액이 아닌 주문금액으로 함
//			1) 일시불 saleTyCd = "2101","2102","2103","2104"  : 주문금액(ordAmt)
//			2) 렌탈 saleTyCd = "1401","1402","1403","1404" : 주문금액(ordAmt) + 렌탈금액(rentAmt) * 계약기간값(cntrPdVal)
//			3) 유통사매출 : saleTyCd = "2401" : 설치자(이름/전화번호 기준)에게 주문금액(ordAmt)
//			2) 홈체험 saleTyCd = "1202"  : 주문금액(ordAmt)
			
			String mshipGradeCtnts = String.valueOf(proMap.get("APPLY_MSHP_GRADE_CTNTS")); // 멤버쉽가입여부
			String [] typeCd = mshipGradeCtnts.split(",");
			if (typeCd.length > 0) proMap.put("typeCd", typeCd);
			
			String mstMshipTypeCd = String.valueOf(proMap.get("MSHIP_TYPE_CDS")); // 유형 001 임직원 002 제휴 003 회원
			String [] gradeCd = mstMshipTypeCd.split(",");
			if (gradeCd.length > 0)  proMap.put("gradeCd", gradeCd);
			
			String mstCprtCmpNo = String.valueOf(proMap.get("CPRT_CMP_NO")); // 마스터 제휴사
			proMap.put("cmpNo", mstCprtCmpNo);
			
			// BOS , POS 합산 SUM
			List<HashMap<String, Object>> ordData = proService.getBosAndPosOrderCust(proMap);
			IssueApplay(proMap , ordData);
			
		}

		return RepeatStatus.FINISHED;
	}

	private int IssueApplay(HashMap<String, Object> proMap, List<HashMap<String, Object>> ordData) throws Exception {
		
		String bnfitCd = String.valueOf(proMap.get("APPLY_BNFIT_CD")); // 001 포인트 , 002 쿠폰
		String applyPointScore = String.valueOf(proMap.get("APPLY_POINT_SCORE")); // 포인트
		String prvCoupnBasNo = String.valueOf(proMap.get("PRV_COUPN_BAS_NO")); // 쿠폰
		// String applyMshipGradeCd1 =
		// String.valueOf(proMap.get("APPLY_MSHIP_GRADE_CD1")); // 임직원
		
		String mshipPromBasNo = String.valueOf(proMap.get("MSHIP_PROM_BAS_NO")); // 프로모션 코드

		int result = 0;
		
		for (HashMap<String, Object> ordMap : ordData) {

			String itgCustNo = String.valueOf(ordMap.get("ITG_CUST_NO")); // 회원번호
			// 프로모션 혜택은 1회만

			HashMap<String, Object> chkMap = new HashMap<>();
			chkMap.put("COUPN_BAS_NO", prvCoupnBasNo);
			chkMap.put("PROM_NO", mshipPromBasNo);
			chkMap.put("ITG_CUST_NO", itgCustNo);
			chkMap.put("POINT_SCORE", applyPointScore);
			
			if (bnfitCd.equals("001")) { // 포인트
				// 기 발급된 포인트 조회
				
				int pCnt = proService.checkPointHst(chkMap);
				if (pCnt == 0) {
					
					Map<String, Object> seq = new HashMap<>(); 
					seq.put("prefix", "CHT");
					String prmSeq = proService.getAutoSeq(seq);
					
					Calendar cal2 = Calendar.getInstance();
					cal2.add(Calendar.YEAR, 2);
					cal2.add(Calendar.DATE, -1);
					
					String url = apiUrl + "/deposit/CRM/"+prmSeq;
					Map<String, Object> point = new HashMap<>();
					point.put("itgCustNo", itgCustNo);
					point.put("pblsDivCd", "905");
					point.put("validPerdStaYmd", Utilities.getDateString());
					point.put("validPerdEndYmd", Constants._DATE_FORMAT.format(cal2.getTime()));
					point.put("pblsChlCd", "CRM");
					point.put("useTypeCd", "002");
					point.put("promNo", mshipPromBasNo);
					point.put("occurPointScore", applyPointScore);
					point.put("messageYn", "Y");
					// api 포인트 적립
					CrmApiDataVo res = apiService.getApiData(url, point, "POST", false);
					
					// 프로모션 포인트 발급시 알림톡
					// 포인트 지급 api 내부에 알림톡 전송 잇음
					if ("IAR0200".equals(res.getCode())) {

						result = 1;
						
//						HashMap<String, Object> alarm = new HashMap<String, Object>();
//						alarm.put("itgCustNo", itgCustNo);
//						alarm.put("ITG_CUST_NO", itgCustNo);
//
//						// 매장
//						alarm.put("storNo", "141359");
//						Map<String, Object> stor = proService.selectStor(alarm);
//						alarm.put("storNm", String.valueOf(stor.get("STOR_NM")));
//						// 회원 정보
//						CrmCustBasVo cust = custDao.select(alarm);
//						alarm.put("custNm", cust.getCustNm());
//						if (Utilities.isEmpty(cust.getMphonNo()) && cust.getMphonNo().getBytes().length >= 13) {
//							continue;
//						}
//						alarm.put("MPHONE_NO", cust.getMphonNo());
//						// 포인트 정보
//
//						///////////////////////////////
//
//						Calendar cal = Calendar.getInstance();
//						cal.setTime(new Date());
//						DateFormat df = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss", Locale.KOREA);
//						// cal.add(Calendar.MINUTE, 2);
////							message.setKReservetime(df.format(cal.getTime()));
//						
//						Map<String, Object> map = (Map<String, Object>) res.getPayload();
//						
//						alarm.put("고객명", cust.getCustNm());
//						alarm.put("회원명", cust.getCustNm());
//						alarm.put("적립일자", df.format(cal.getTime()));
//						alarm.put("사용일자", null);
//						alarm.put("취소일자", null);
//						alarm.put("적립포인트", String.format("%,d",Integer.parseInt(applyPointScore)));
//						alarm.put("사용포인트", 0);
//						alarm.put("취소포인트", 0);
//						alarm.put("잔여포인트",	String.format("%,d",Integer.parseInt(map.get("totalPoint").toString())));
//						alarm.put("사용처", String.valueOf(stor.get("STOR_NM")));
//						if (Utilities.isNotEmpty(cust.getMphonNo())) {
//							msgService.initTemplate();
//							msgService.sendPointMessage(alarm, Constants._TALK_CODE_POINT_DEPOSIT);
//						}
					}
				}

			} else if (bnfitCd.equals("002")) { // 쿠폰
				// 기 발급된 쿠폰 조회
				int cCnt = proService.checkCoupnHst(chkMap);
				if (cCnt == 0) {
					result = proService.savePromEventCoupn(chkMap);
				}

			}
		}

		return result;
	}
}
