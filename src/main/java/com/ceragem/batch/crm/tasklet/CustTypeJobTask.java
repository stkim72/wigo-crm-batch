/**
 * 
 */
package com.ceragem.batch.crm.tasklet;

import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ceragem.batch.crm.dao.CustTypeDao;
import com.ceragem.batch.crm.model.CrmCustBasVo;
import com.ceragem.batch.crm.service.CustTypeService;

import lombok.extern.slf4j.Slf4j;
/**
 * 
 * @ClassName	CustTypeJobTask
 * @author		김성태
 * @date		2022. 10. 5.
 * @Version		1.0
 * @description 고객유형	
 * @Company		Copyright ⓒ wigo.ai. All Right Reserved
 */
@Slf4j
@Component
public class CustTypeJobTask implements Tasklet {
	
	// CRM 고객 유형 업데이트( 잠재고객 001, 가망고객 002, 체험고객 003 , 구매고객 004 , 추천고객 005, 충성고객 006   )
	String[] arrCustTypeCd = { "006", "005", "004", "003", "002" };
		
		
	@Autowired
	CustTypeDao dao;
	
	
	@Autowired
	CustTypeService custTypeService;
	
	
	//@Transactional
	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		log.debug("CustTypeJobTask");
		
		int resultCnt=0;
		//resultCnt = custTypeService.updateCustType(); // 통으로 호출시 에러 남
		
		
		List<CrmCustBasVo> custList = null;	
		List<CrmCustBasVo> custSubList = null;		
		CrmCustBasVo crmCustBasVo = new CrmCustBasVo();
		
		// 총 업데이트 카운트
//		int updCnt = 0;
		
//		long beforeTime = System.currentTimeMillis(); //코드 실행 전에 시간 받아오기
		
		//arrCustTypeCd.length
		for(int c = 0 ; c < arrCustTypeCd.length ; c++  ) {
			
			// 006 이 아닌것 부터 처리
			crmCustBasVo.setChkCustTypeCd( arrCustTypeCd[ c ] );			
			custList = dao.getCustList( crmCustBasVo );	

			
			if( custList.size() == 0 || custList == null) continue;
			
			log.debug("\n\n #####  "+ arrCustTypeCd[ c ] + "_"+ custList.size() );
				
			
			int chkMaxSize = 1000;
			
			// chkMaxSize = 1000 개 이상일 경우 1000 개씩 짤라서 호출
			if( custList.size() > chkMaxSize ) {

				int modSize = custList.size()/chkMaxSize;
				int modCnt = custList.size()%chkMaxSize;
				
						
				int i= 0;
				for(i = 0 ; i < modSize ; i++) {
					if(custSubList != null) custSubList.clear();
					custSubList = new ArrayList<CrmCustBasVo>(custList.subList( i*chkMaxSize, (i*chkMaxSize) + chkMaxSize ));
					
//					updCnt = 
							custTypeService.updateCustType( custSubList );
//					int cutSize = custSubList.size();
					
				}
				
				if( modCnt > 0 ) {
					
					if(custSubList != null) custSubList.clear();
					custSubList = new ArrayList<CrmCustBasVo>(custList.subList( i*chkMaxSize, custList.size() ) );
//					updCnt = 
							custTypeService.updateCustType( custSubList );					
//					int cutSize = custSubList.size();
				}
				
			}else {
//				updCnt = 
						custTypeService.updateCustType( custList );
			}
			
		
		}
	
		
		
		log.debug("CustTypeJobTask => "+ resultCnt );
		return RepeatStatus.FINISHED;
	}
	
	
	
	
}