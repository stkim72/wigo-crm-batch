package com.ceragem.batch.crm.tasklet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ceragem.batch.crm.common.constant.Constants;
import com.ceragem.batch.crm.common.util.Utilities;
import com.ceragem.batch.crm.service.GrdCoupnIssueService;
import com.ceragem.batch.crm.service.SendMessageService;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @ClassName	CoupnCancelReSendTask
 * @author		김성태
 * @date		2022. 10. 5.
 * @Version		1.0
 * @description 만료쿠폰알림	
 * @Company		Copyright ⓒ wigo.ai. All Right Reserved
 */
@Slf4j
@Component
public class CoupnCancelReSendTask implements Tasklet {
	
	@Autowired
	SendMessageService msgService;
	
	@Autowired
	GrdCoupnIssueService cpnService;
	
	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		log.debug("CoupnCancelReSendTask => ");
		// 7일후 만료되는 쿠폰 알림톡 전송 익일10 시 발송
		List<HashMap<String,Object>> list = cpnService.getCoupnCancelFor7Day();
		
		List<HashMap<String, Object>> sendData = new ArrayList<>();
		for (HashMap<String, Object> data : list) {
			if (Utilities.isNotEmpty(data.get("MPHON_NO")) && data.get("MPHON_NO").toString().toCharArray().length < 13) {
		        HashMap<String,Object> alarm = new HashMap<String,Object>();
		        alarm.put("회원명", data.get("CUST_NM"));
		        alarm.put("고객명", data.get("CUST_NM"));
		        alarm.put("쿠폰명", data.get("COUPN_BAS_NM"));
		        alarm.put("쿠폰만료일자", data.get("COND_TO_DT"));
		        alarm.put("쿠폰발급일자", data.get("REG_DT"));
		        alarm.put("ITG_CUST_NO", data.get("ITG_CUST_NO"));
		        alarm.put("MPHONE_NO", data.get("MPHON_NO"));   // MPHONE_NO
		        alarm.put("custNm", data.get("CUST_NM"));
		        
		        sendData.add(alarm);
			}
		}
		msgService.initTemplate();
		msgService.sendCoupnCancel(sendData,Constants._MSG_COUPNCANCELRESEND);
		
		return RepeatStatus.FINISHED;
	}

}
