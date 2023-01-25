package com.ceragem.batch.crm.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ceragem.batch.crm.dao.GrpCoupnIssueDao;
import com.ceragem.batch.crm.dao.ICrmDao;
import com.ceragem.batch.crm.model.GradeCouponVo;

/**
 * 
 * @ClassName CrmCustBasService
 * @author 김은성
 * @date 2022. 4. 18.
 * @Version 1.0
 * @description CRM고객기본 Service
 * @Company Copyright ⓒ wigo.ai. All Right Reserved
 */
@Service
public class GrdCoupnIssueService extends AbstractCrmService {
//
//	private static final int DORMANCY_MONTH = 12;
//	private static final int WITHDRAWL_MONTH = 48;

	@Autowired
	GrpCoupnIssueDao dao;

	@Override
	public ICrmDao getDao() {
		return dao;
	}


	public int saveGrdCoupn(GradeCouponVo gradeCouponVo) {

		// 멤버십 정책의 적용기간과 쿠폰마스터의 발행기간을 체크한 
		// 등급별 쿠폰을 읽어온다. 
		List<GradeCouponVo> list = dao.getGrdCouponList(gradeCouponVo);	
		int resultCnt = 0;
		
		
		
		
		for(int i = 0 ; i < list.size() ; i++) {

			// 해당 정책에 맞는 회원에게 쿠폰을 지급한다.
			// 001 : 1회, 002 : 매일, 003 : 매주, 004 : 매월, 005 : 분기, 006 : 매년	
			
			// 제공 매수에 맞게 지급
//			for( int j=0 ; j < list.get(i).getPrvQnty() ; j++) {
				

				// 평생 1회
				if(  "001".equals( list.get(i).getCycleCd() ) ){
					resultCnt += dao.pubCoupon( list.get(i) );
				}
	
				// 매일 00시
				if(  "002".equals( list.get(i).getCycleCd() ) ){
					resultCnt += dao.pubCoupon( list.get(i) );
				}
	
				// 매주 월요일
				if(  "003".equals( list.get(i).getCycleCd() ) && getToday().equals( getCurMonday() ) ){
					resultCnt += dao.pubCoupon( list.get(i) );
				}
				
				// 매월 1일
				if(  "004".equals( list.get(i).getCycleCd() ) && getToday().equals( getYear() + getMonth() +"01" ) ){
					resultCnt += dao.pubCoupon( list.get(i) );
				}
				
				// 분기
				if(  "005".equals( list.get(i).getCycleCd() ) && ( getToday().equals( getYear() +"0101" ) ||  getToday().equals( getYear() +"0401" ) ||  getToday().equals( getYear() +"0701" ) ||  getToday().equals( getYear() +"1001" )  )){
					resultCnt += dao.pubCoupon( list.get(i) );
				}
	
				// 매년
				if(  "006".equals( list.get(i).getCycleCd() ) &&  getToday().equals( getYear() +"0101" )  ){
					resultCnt += dao.pubCoupon( list.get(i) );
				}
				
//			}
						

		}
		
		return resultCnt; 


	}


	public String getCurMonday(){
		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyyMMdd");
		Calendar c = Calendar.getInstance();
		c.set(Calendar.DAY_OF_WEEK,Calendar.MONDAY);
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
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMM");      
		String formatedNow = now.format(formatter);
		return formatedNow;	
	}
	
	public String getToday(){     
		LocalDate now = LocalDate.now();         
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");    
		String formatedNow = now.format(formatter);
		return formatedNow;	
	}


	public List<HashMap<String, Object>> getCoupnCancelFor7Day() {
		return dao.getCoupnCancelFor7Day();
	}

}
