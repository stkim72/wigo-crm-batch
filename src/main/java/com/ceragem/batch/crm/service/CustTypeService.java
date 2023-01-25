package com.ceragem.batch.crm.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ceragem.batch.crm.dao.CustTypeDao;
import com.ceragem.batch.crm.dao.ICrmDao;
import com.ceragem.batch.crm.model.CrmCustBasVo;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @ClassName CustTypeService
 * @author 김은성
 * @date 2022. 4. 18.
 * @Version 1.0
 * @description CRM 고객 유형 업데이트(잠재고객 001, 가망고객 002, 체험고객 003 , 구매고객 004 , 충성고객 006  단, 추천고객 005 은 제외 )
 * @Company Copyright ⓒ wigo.ai. All Right Reserved
 */

@Slf4j
@Service
public class CustTypeService extends AbstractCrmService {

	// CRM 고객 유형 업데이트(잠재고객 001, 가망고객 002, 체험고객 003 , 구매고객 004 , 충성고객 006  단, 추천고객 005 은 제외 )
	String[] arrCustTypeCd = {"006", "004", "003", "002" };

	@Autowired
	CustTypeDao dao;

	@Override
	public ICrmDao getDao() {
		return dao;
	}



	public int updateCustType(List<CrmCustBasVo> custList) {


//		CrmCustBasVo crmCustBasVo = new CrmCustBasVo();

		// 총 업데이트 카운트
		int updCnt = 0;

//		long beforeTime = System.currentTimeMillis(); //코드 실행 전에 시간 받아오기
		
		if( custList == null) return 0;

		if( custList.size() > 0 ) {		

			long beforeTimeSub = System.currentTimeMillis(); //코드 실행 전에 시간 받아오기

			// 실제 유형 업데이트				
			// declare  형식으로 속도는 빠르나 return 이 -1 임
			updCnt = dao.updCustTypeCd(custList);

			// 업데이트 된게 있다면
			//if( updCnt > 0 ) { 

				// 업데이트 로그 데이터 등록
//				int insCnt = 
						dao.insCustTypeCd(custList);


				//log.debug("******"+  arrCustTypeCd[ c ] + " // insCnt = "+ insCnt );
				long afterTimeSub = System.currentTimeMillis(); // 코드 실행 후에 시간 받아오기
				long secDiffTimeSub = (afterTimeSub - beforeTimeSub)/1000; //두 시간에 차 계산
				log.debug("$$$$$$   시간차이(m) : "+ secDiffTimeSub);

			//}

		}

		return updCnt;
	}



	public int updateCustType() throws Exception  {

		List<CrmCustBasVo> custList = null;	
		List<CrmCustBasVo> custSubList = null;		
		CrmCustBasVo crmCustBasVo = new CrmCustBasVo();

		// 총 업데이트 카운트
		int updCnt = 0;

		long beforeTime = System.currentTimeMillis(); //코드 실행 전에 시간 받아오기

		//arrCustTypeCd.length
		for(int c = 0 ; c < arrCustTypeCd.length ; c++  ) {

			// 006 이 아닌것 부터 처리
			crmCustBasVo.setChkCustTypeCd( arrCustTypeCd[ c ] );			
			custList = dao.getCustList( crmCustBasVo );	

			log.debug("#####  "+ arrCustTypeCd[ c ] + " =  "+ custList.size() );

			if( custList.size() == 0 ) continue;

			if( custList.size() > 0 ) {		

				long beforeTimeSub = System.currentTimeMillis(); //코드 실행 전에 시간 받아오기

				// 실제 유형 업데이트

				// declare  형식으로 속도는 빠르나 return 이 -1 임
				updCnt = dao.updCustTypeCd(custList);

				int chkMaxSize = 2000;
				if( custList.size() > chkMaxSize ) {

					int modSize = custList.size()/chkMaxSize;
					int modCnt = custList.size()%chkMaxSize;
					int i= 0;
					for(i = 0 ; i < modSize ; i++) {
						//updCnt = dao.updCustTypeCd(custList.subList(i, 2000 ));
						if(custSubList != null) custSubList.clear();
						custSubList = new ArrayList<CrmCustBasVo>(custList.subList( i*chkMaxSize, (i*chkMaxSize) + chkMaxSize ));

						updCnt = dao.updCustTypeCd( custSubList );
						dao.insCustTypeCd(custSubList);


						int cutSize = custSubList.size();
						log.debug("\n\n #####  "+ arrCustTypeCd[ c ] + "_"+ i  +" // "+ (i*chkMaxSize) +" =  modSize = "+ modSize +" // modCnt = "+ modCnt +" // cutSize = "+ cutSize +" // "+ custSubList.get(i).getItgCustNo() );

					}

					if( modCnt > 0 ) {

						if(custSubList != null) custSubList.clear();
						custSubList = new ArrayList<CrmCustBasVo>(custList.subList( i*chkMaxSize, custList.size() ) );
						updCnt = dao.updCustTypeCd( custSubList );
						dao.insCustTypeCd(custSubList);

						int cutSize = custSubList.size();
						log.debug("\n\n ##### zz  "+ arrCustTypeCd[ c ] + "_"+ i +" // "+ (i*chkMaxSize) +" =  modSize = "+ modSize +" // modCnt = "+ modCnt +" // cutSize = "+ cutSize +" // "+ custSubList.get(i).getItgCustNo() );
					}
				}else {
					updCnt = dao.updCustTypeCd( custList );
					dao.insCustTypeCd(custList);
				}




				// 건건 업데이트 형식으로 속도는 느리나 return 카운트가 리턴됨

				// #### updCnt = {change=2, insert=0, update=2,....}
				// Map<String, Object> upData = updateList( custList );
				// updCnt += Integer.parseInt( upData.get("update").toString() );
				//log.debug("$$$$$$ upData = "+ upData );

				// 업데이트 된게 있다면
				//if( updCnt > 0 ) { 

				// 업데이트 로그 데이터 등록
//				int insCnt = 
						dao.insCustTypeCd(custList);


				//Map<String, Object> insData = insertList( custList );
				//log.debug("$$$$$$ insData = "+ insData );

				//log.debug("******"+  arrCustTypeCd[ c ] + " // insCnt = "+ insCnt );
				long afterTimeSub = System.currentTimeMillis(); // 코드 실행 후에 시간 받아오기
				long secDiffTimeSub = (afterTimeSub - beforeTimeSub)/1000; //두 시간에 차 계산
				log.debug("$$$$$$ "+ arrCustTypeCd[ c ] + " 시간차이(m) : "+ secDiffTimeSub);

				//}

			}



		}

		long afterTime = System.currentTimeMillis(); // 코드 실행 후에 시간 받아오기
		long secDiffTime = (afterTime - beforeTime)/1000; //두 시간에 차 계산
		log.debug("################## 총 시간차이(m) : "+ secDiffTime);

		return updCnt; 
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
