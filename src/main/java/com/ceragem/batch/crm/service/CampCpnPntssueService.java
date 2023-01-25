package com.ceragem.batch.crm.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ceragem.batch.crm.dao.CampCpnPntIssueDao;
import com.ceragem.batch.crm.dao.ICrmDao;
import com.ceragem.batch.crm.model.CrmCampBasVo;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @ClassName CrmCustBasService
 * @author 김은성
 * @date 2022. 4. 18.
 * @Version 1.0
 * @description CRM고객기본 Service
 * @Company Copyright ⓒ wigo.ai. All Right Reserved
 */

@Slf4j
@Service
public class CampCpnPntssueService extends AbstractCrmService {
	//
	//	private static final int DORMANCY_MONTH = 12;
	//	private static final int WITHDRAWL_MONTH = 48;

	@Autowired
	CampCpnPntIssueDao dao;

	@Override
	public ICrmDao getDao() {
		return dao;
	}


	public int saveCampIssue() {

		// 멤버십 정책의 적용기간과 쿠폰마스터의 발행기간을 체크한 
		// 등급별 쿠폰을 읽어온다. 
		
		List<CrmCampBasVo> list = dao.getCampIssueList();
		




		List<CrmCampBasVo> resultCpnList = pubCoupon(list); // 쿠폰 지급 카운트
		List<CrmCampBasVo> resultPntList = pubPoint(list); // 포인트 지급 카운트

		//int sumResult = resultCnt + resultPntCnt; 

	//	System.out.println("## resultCnt = "+ resultCnt );
	//	System.out.println("## resultPntCnt = "+ resultPntCnt );

		CrmCampBasVo cVo = null;
		
		
		List<CrmCampBasVo> mergedList = new ArrayList<CrmCampBasVo>(resultCpnList);
		mergedList.removeAll(resultPntList);
		mergedList.addAll(resultPntList);
		
			
		
		// 히스토리 작성
		if( mergedList.size() > 0 ) {
			for(int i=0 ; i < mergedList.size();i++ ) {
				
				
				cVo = mergedList.get(i);
				dao.saveCamHis( cVo );
				
			}
		}
		
		int i=0;
		
		// 등록성공일 경우 쿠폰 메세지 전송
		if( mergedList.size() > 0 ) {
			
			//	int logCnt = 0;
			for(i=0 ; i < mergedList.size();i++ ) {
				
				cVo = null;
				cVo = mergedList.get(i);

				// EON.WSOMZ023  의 Z023_GROUP 값  09 : 포인트발송, 10 : 쿠폰발송, 11 : 쿠폰포인트 발송
				if( "09".equals( cVo.getTmpltGroup()  ) ) {						
					cVo.setJoinTbl("CRM_POINT_HST");	
					cVo.setJoinCol("B.CAMP_NO");
				}else if( "10".equals( cVo.getTmpltGroup()  ) ) {						
					cVo.setJoinTbl("CRM_COUPN_PBLS_HST");		
					cVo.setJoinCol("B.CAMP_NO");					
				}else if( "11".equals( cVo.getTmpltGroup()  ) ) {						
					cVo.setJoinTbl("CRM_POINT_HST");	 //CRM_CAMP_DSP_HST	
					cVo.setJoinCol("B.CAMP_NO");					
				}else{
					log.debug("#### 템플릿이 존재하지 않습니다.");
					continue;
				}
				
				
				
				//System.out.println( "####@@ = "+ cVo.getCampBasNo() +" // "+ cVo.getCampBasNm() +"//"+ cVo.getApplyPoint() +"//"+ cVo.getSendType());
				
				if( "PUSH".equals(cVo.getSendType()) ){
					
					// push 전송 처리
					dao.insertPushIf( cVo );
					
				}else {
				
					// sms 전송 처리
				 	dao.insertMsgIf( cVo );
					
				}
				
			}

		}
		
		return i; 

		
	}
	

	// 쿠폰 지급 처리
	public List<CrmCampBasVo> pubCoupon(List<CrmCampBasVo> list) {

	
		CrmCampBasVo cVo = null;
		
		List<CrmCampBasVo> nList = new LinkedList<CrmCampBasVo>();

		for(int i = 0 ; i < list.size() ; i++) {


			// 해당 정책에 맞는 회원에게 쿠폰을 지급한다.
			// 001 : 1회, 002 : 매일, 003 : 매주, 004 : 매월, 005 : 분기, 006 : 매년	

			// 제공 매수에 맞게 지급
			cVo = list.get(i);
			
			int resultCnt = 0;
			for( int j=0 ; j < cVo.getCoupnIssueCnt() ; j++) {
				
				
				cVo.setIssueCnt( (j+1) );
				
				resultCnt = 0;


				// 평생 1회
				if(  "001".equals( cVo.getRpeatCd() ) ){
					resultCnt = dao.pubCoupon( cVo );
					if(resultCnt > 0) nList.add(cVo);
				}

				// 매일 00시
				if(  "002".equals( cVo.getRpeatCd() ) ){
					resultCnt = dao.pubCoupon( cVo );
					if(resultCnt > 0) nList.add(cVo);
				}

				// 매주 선택된 요일
				if(  "003".equals( cVo.getRpeatCd() ) && ( 
						getToday().equals( getCurDay( cVo.getDow1UseYn() ) )  
						|| getToday().equals( getCurDay( cVo.getDow2UseYn() ) ) 
						|| getToday().equals( getCurDay( cVo.getDow3UseYn() ) ) 
						|| getToday().equals( getCurDay( cVo.getDow4UseYn() ) ) 
						|| getToday().equals( getCurDay( cVo.getDow5UseYn() ) ) 
						|| getToday().equals( getCurDay( cVo.getDow6UseYn() ) ) 
						|| getToday().equals( getCurDay( cVo.getDow7UseYn() ) )     
						) 
						){
					resultCnt = dao.pubCoupon( cVo );
					if(resultCnt > 0) nList.add(cVo);
				}

				// 매월 반복일자
				
				//System.out.println("###cVo.getRpeatCd() = "+ cVo.getRpeatCd() );
				//System.out.println("###"+ getToday() +"//"+ ( getYear() + getMonth() +  cVo.getRpeatDay() ));
				//System.out.println("###"+ getYear() + getMonth() +  cVo.getRpeatDay() );
				
				
				if(  "004".equals( cVo.getRpeatCd() ) && getToday().equals( getYear() + getMonth() +  cVo.getRpeatDay() ) ){
					resultCnt = dao.pubCoupon( cVo );
					if(resultCnt > 0) nList.add(cVo);
				}

				// 분기
				if(  "005".equals( cVo.getRpeatCd() ) && ( getToday().equals( getYear() +"01"+ cVo.getRpeatDay() ) ||  getToday().equals( getYear() +"04"+ cVo.getRpeatDay() ) ||  getToday().equals( getYear() +"07"+ cVo.getRpeatDay() ) ||  getToday().equals( getYear() +"10"+ cVo.getRpeatDay() )  )){
					resultCnt = dao.pubCoupon( cVo );
					if(resultCnt > 0) nList.add(cVo);
				}

				// 매년 정해진 날짜
				if(  "006".equals( cVo.getRpeatCd() ) &&  getToday().equals( getYear() +  cVo.getRpeatMonth() +  cVo.getRpeatDay()  )  ){
					resultCnt = dao.pubCoupon( cVo );
					if(resultCnt > 0) nList.add(cVo);
				}
				
				
			}


		}

		return nList;

	}



	// 포인트 지급 처리
	public List<CrmCampBasVo> pubPoint(List<CrmCampBasVo> list) {

		
		CrmCampBasVo cVo = null;
		
		List<CrmCampBasVo> nList = new LinkedList<CrmCampBasVo>();
		int resultCnt = 0;
		
		for(int i = 0 ; i < list.size() ; i++) {

			cVo = list.get(i);
			
			resultCnt = 0;

			// 평생 1회
			if(  "001".equals( cVo.getRpeatCd() ) ){
				resultCnt = dao.pubPoint( cVo );
				if(resultCnt > 0) nList.add(cVo);
			}

			// 매일 00시
			if(  "002".equals( cVo.getRpeatCd() ) ){
				resultCnt = dao.pubPoint( cVo );
				if(resultCnt > 0) nList.add(cVo);
			}

			// 매주 선택된 요일
			if(  "003".equals( cVo.getRpeatCd() ) && ( 
					getToday().equals( getCurDay( cVo.getDow1UseYn() ) )  
					|| getToday().equals( getCurDay( cVo.getDow2UseYn() ) ) 
					|| getToday().equals( getCurDay( cVo.getDow3UseYn() ) ) 
					|| getToday().equals( getCurDay( cVo.getDow4UseYn() ) ) 
					|| getToday().equals( getCurDay( cVo.getDow5UseYn() ) ) 
					|| getToday().equals( getCurDay( cVo.getDow6UseYn() ) ) 
					|| getToday().equals( getCurDay( cVo.getDow7UseYn() ) )     
					) 
					){
				resultCnt = dao.pubPoint( cVo );
				if(resultCnt > 0) nList.add(cVo);
			}

			// 매월 반복일자
			if(  "004".equals( cVo.getRpeatCd() ) && getToday().equals( getYear() + getMonth() +  cVo.getRpeatDay() ) ){
				resultCnt = dao.pubPoint( cVo );
				if(resultCnt > 0) nList.add(cVo);
			}

			// 분기
			if(  "005".equals( cVo.getRpeatCd() ) && ( getToday().equals( getYear() +"01"+ cVo.getRpeatDay() ) ||  getToday().equals( getYear() +"04"+ cVo.getRpeatDay() ) ||  getToday().equals( getYear() +"07"+ cVo.getRpeatDay() ) ||  getToday().equals( getYear() +"10"+ cVo.getRpeatDay() )  )){
				resultCnt = dao.pubPoint( cVo );
				if(resultCnt > 0) nList.add(cVo);
			}

			// 매년 정해진 날짜
			if(  "006".equals( cVo.getRpeatCd() ) &&  getToday().equals( getYear() +  cVo.getRpeatMonth() +  cVo.getRpeatDay()  )  ){
				resultCnt = dao.pubPoint( cVo );
				if(resultCnt > 0) nList.add(cVo);
			}
			

		}

		return nList;

	}

	public String getCurDay(String day){

		if(  day == null ) return "";

		int dayNum = Integer.parseInt( day );

		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyyMMdd");
		Calendar c = Calendar.getInstance();
		c.set(Calendar.DAY_OF_WEEK, dayNum);
		return formatter.format(c.getTime());
	}



	public String getCurMonday(){
		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyyMMdd");
		Calendar c = Calendar.getInstance();
		c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		return formatter.format(c.getTime());
	}

	public String getYear(){		
		LocalDate now = LocalDate.now();         
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy");      
		String formatedNow = now.format(formatter);
		return formatedNow;	
	}

	public String getMonth(){		
		LocalDate now = LocalDate.now();         
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM");      
		String formatedNow = now.format(formatter);
		return formatedNow;	
	}

	public String getToday(){     
		LocalDate now = LocalDate.now();         
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");    
		String formatedNow = now.format(formatter);
		return formatedNow;	
	}

}
