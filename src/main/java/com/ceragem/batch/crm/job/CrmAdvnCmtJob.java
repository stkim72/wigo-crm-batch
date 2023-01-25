package com.ceragem.batch.crm.job;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ceragem.batch.crm.common.util.CrmJobListener;
import com.ceragem.batch.crm.dao.AdvnCmtDao;
import com.ceragem.batch.crm.tasklet.AdvncmtTask;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class CrmAdvnCmtJob {

	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;
	private final AdvncmtTask advnCmtTask;
	
	@Autowired
	CrmJobListener jobListener;
	
	@Autowired
	AdvnCmtDao advnCmtDao;

	@Bean("advnCmtJob")
	Job advnCmtJob() {
		log.debug("advnCmtJob");
		return jobBuilderFactory.get("advnCmtJob").listener(jobListener).start(stepAdvnCmtTask()).build();
	}

	@Bean("stepAdvnCmtTask")
	Step stepAdvnCmtTask() {
		log.debug("advnCmtTask");
		return stepBuilderFactory.get("stepAdvnCmtTask").tasklet(advnCmtTask).build();
	}
	
	
//	
//	@Bean("advnCmtJob")
//	Job advnCmtJob() {
//		log.debug("advnCmtJob");
//		return jobBuilderFactory.get("advnCmtJob").listener(jobListener).start(stepAdvnCmt()).build();
//	}	
//	
//	
//	@Bean("stepAdvnCmt")
//	Step stepAdvnCmt() {
//		return stepBuilderFactory.get("stepAdvnCmt").<AdvnCmtVo, AdvnCmtVo>chunk(Constants.FETCH_COUNT)
//				.reader(readerAdvnCmt()).processor(procAdvnCmt()).writer(writeAdvnCmt()).build();
//	}
//
//	@Bean("readerAdvnCmt")
//	ItemReader<AdvnCmtVo> readerAdvnCmt() {
//		
//				return  new ListItemReader<AdvnCmtVo>(advnCmtDao.getAdmtList());
//				
//			
//	}
//	
//
//	
//	@Bean("procAdvnCmt")
//	ItemProcessor<AdvnCmtVo, AdvnCmtVo> procAdvnCmt() {
//		return new ItemProcessor<AdvnCmtVo, AdvnCmtVo>() {
//
//			int resultCnt=0;
//			
//			@Override
//			public AdvnCmtVo process(AdvnCmtVo advnSubList) throws Exception {
//				//if( item != null ) {
//					
//					//System.out.println("### 33 = "+ advnSubList.getItgCustNo() +" // "+ advnSubList.getUpMonthYn() );
//				//}
//
//					// 매일 00시 
//					resultCnt = advnCmtDao.updateOne( advnSubList );
//
//					// 매일 00시 - 일배치 로그 등록
//					// 실제 1달간의 업데이트는 하단 날짜(01일) 비교구문에서 처리
//					//		int dayCnt = 
//					resultCnt += advnCmtDao.insertDayOne( advnSubList );
//
//
//					// 매월 1일 - 실제 등급변경내역 인서트
//					if(  getToday().equals( getMonth() +"01" ) ){
//						
//						advnCmtDao.insertOne( advnSubList );
//						
//						// 승급포인트 지급
//						advnCmtDao.pubAdvnPoint( advnSubList );
//						
//						// 알림톡 발송
//						advnCmtDao.insertAdvnMsg();
//					}
//				
//				return advnSubList; //resultCnt;
//			}
//		};
//	}
//
//	@Bean("writeAdvnCmt")
//	ItemWriter<AdvnCmtVo> writeAdvnCmt() {
//		return new ItemWriter<AdvnCmtVo>() {
//			@Override
//			public void write(List<? extends AdvnCmtVo> items) throws Exception {
//				//for( int i=0 ; i < items.size() ; i++) {
//				//	System.out.println("### 444 = "+ items.get(i).getItgCustNo() +" // "+ items.get(i).getUpMonthYn() );
//				//}
//				
//			}
//		};
//	}
//	
//	
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
