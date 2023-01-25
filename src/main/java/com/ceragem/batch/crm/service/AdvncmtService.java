package com.ceragem.batch.crm.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ceragem.batch.crm.dao.AdvnCmtDao;
import com.ceragem.batch.crm.dao.ICrmDao;
import com.ceragem.batch.crm.model.AdvnCmtVo;

/**
 * 
 * @ClassName CrmCustBasService
 * @author 김은성
 * @date 2022. 4. 18.
 * @Version 1.0
 * @description CRM 승급업데이트 Service
 * @Company Copyright ⓒ wigo.ai. All Right Reserved
 */
@Service
public class AdvncmtService extends AbstractCrmService {

	//	private static final int DORMANCY_MONTH = 12;
	//	private static final int WITHDRAWL_MONTH = 48;

	@Autowired
	AdvnCmtDao dao;

	@Autowired
	AdvncmtService service;


	@Override
	public ICrmDao getDao() {
		return dao;
	}


	public int saveAdvncmt() throws Exception {

		// 멤버십 정책의 적용기간과  체크한  

		
		List<AdvnCmtVo> list = dao.getAdmtList();
		List<AdvnCmtVo> advnSubList = null;		
		int resultCnt = 0;


		int chkMaxSize = 2000;
		if( list.size() > chkMaxSize ) {

			int modSize = list.size()/chkMaxSize;
			int modCnt = list.size()%chkMaxSize;
			int i= 0;
			for(i = 0 ; i < modSize ; i++) {

				if(advnSubList != null) advnSubList.clear();
				advnSubList = new ArrayList<AdvnCmtVo>(list.subList( i*chkMaxSize, (i*chkMaxSize) + chkMaxSize ));


				// 매일 00시 
				resultCnt = dao.update( advnSubList );


				// 매일 00시 - 일배치 로그 등록
				// 실제 1달간의 업데이트는 하단 날짜(01일) 비교구문에서 처리
				//		int dayCnt = 
				dao.insertDay( advnSubList );


				// 매월 1일 - 실제 등급변경내역 인서트
				if(  getToday().equals( getMonth() +"01" ) ){
					dao.insert( advnSubList );
					
					// 승급포인트 지급
					dao.pubAdvnPoint( advnSubList );
					
					// 알림톡 발송
					dao.insertAdvnMsg();
				}

			}

			if( modCnt > 0 ) {
				
				if(advnSubList != null) advnSubList.clear();
				advnSubList = new ArrayList<AdvnCmtVo>(list.subList(  i*chkMaxSize, list.size() ) );
				
				// 매일 00시 
				resultCnt = dao.update( advnSubList );


				// 매일 00시 - 일배치 로그 등록
				// 실제 1달간의 업데이트는 하단 날짜(01일) 비교구문에서 처리
				//		int dayCnt = 
				dao.insertDay( advnSubList );


				// 매월 1일 - 실제 등급변경내역 인서트
				if(  getToday().equals( getMonth() +"01" ) ){
					
					dao.insert( advnSubList );
					
					// 승급포인트 지급
					dao.pubAdvnPoint( advnSubList );
					
					// 알림톡 발송
					dao.insertAdvnMsg();
				}
				

			}
		}else {
			
			
			if(  list.size() == 0 ) return 1;
			
			
			
			// 매일 00시 
			resultCnt = dao.update( list );


			// 매일 00시 - 일배치 로그 등록
			// 실제 1달간의 업데이트는 하단 날짜(01일) 비교구문에서 처리
			//		int dayCnt = 
			dao.insertDay( list );


			// 매월 1일 - 실제 등급변경내역 인서트
			if(  getToday().equals( getMonth() +"01" ) ){
				dao.insert( list );
				
				// 승급포인트 지급
				dao.pubAdvnPoint( list );
				
				// 알림톡 발송
				dao.insertAdvnMsg();
			}

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

}
