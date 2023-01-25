package com.ceragem.batch.crm.job;

import java.util.Calendar;
import java.util.List;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ceragem.batch.crm.common.constant.Constants;
import com.ceragem.batch.crm.common.util.CrmJobListener;
import com.ceragem.batch.crm.common.util.Utilities;
import com.ceragem.batch.crm.dao.CrmCustBasDao;
import com.ceragem.batch.crm.dao.CrmEventBatchDao;
import com.ceragem.batch.crm.exception.BatchException;
import com.ceragem.batch.crm.model.BatchMap;
import com.ceragem.batch.crm.model.CrmComnCodeVo;
import com.ceragem.batch.crm.model.MsgIfVo;
import com.ceragem.batch.crm.service.SendMessageService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@RequiredArgsConstructor
class CrmPointInfoJob {

	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;

	@Value("${spring.eon.user}")
	String eonUser;

	@Autowired
	CrmJobListener jobListener;

	@Autowired
	CrmEventBatchDao dao;

	@Autowired
	CrmCustBasDao custDao;
	@Autowired
	SendMessageService messageService;

	String currentTime = Utilities.getDateTimeString();

	List<BatchMap> expirePointList = null;

	int expirePointIndex = 0;

	String ymd = null;

	private void initDate() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, 1);
		ymd = Utilities.getDateString(cal.getTime());
	}

	@Bean("crmPointTalkJob")
	Job crmPointTalkJob() {
		log.debug("crmPointTalkJob");
		messageService.initTemplate();
		return jobBuilderFactory.get("crmPointTalkJob").listener(jobListener).start(stepExpirePointInfo()).build();
	}

	// 포인트 만료시키기
	@Bean("stepExpirePointInfo")
	Step stepExpirePointInfo() {
		return stepBuilderFactory.get("stepExpirePointInfo").<BatchMap, MsgIfVo>chunk(Constants.FETCH_COUNT)
				.reader(readerExpirePointInfo()).processor(procExpirePointInfo()).writer(writeExpirePointInfo())
				.build();
	}

	@Bean("readerExpirePointInfo")
	ItemReader<BatchMap> readerExpirePointInfo() {
		return new ItemReader<BatchMap>() {
			@Override
			public BatchMap read() throws Exception {
				if (expirePointList == null) {
					initDate();
					BatchMap param = new BatchMap();
					param.setString("ymd", ymd);
					expirePointList = dao.selectExpireTalkList(param);
				}
				if (Utilities.isEmpty(expirePointList)) {

					return null;
				}
				return expirePointList.remove(0);
			}
		};
	}

	@Bean("procExpirePointInfo")
	ItemProcessor<BatchMap, MsgIfVo> procExpirePointInfo() {
		return new ItemProcessor<BatchMap, MsgIfVo>() {

			@Override
			public MsgIfVo process(BatchMap item) throws Exception {

				String event = Constants._MSG_EXPIRED_POINT;
				CrmComnCodeVo code = messageService.getCode(event);
				if (code == null)
					throw new BatchException("[S170][" + event + "]공통코드에 등록되지 않은 메시지 입니다");

				if (Utilities.isEmpty(code.getTalkTemplate()))
					throw new BatchException("[S170][" + event + "]알림톡 템플릿이 등록되지 않았습니다.");

				int remains = item.getInt("remainPointScore");
				int expPointScore = item.getInt("expPointScore");
				remains -= expPointScore;
				if (remains < 0)
					remains = 0;

				item.setString("remainPointScore",Utilities.getNumberString( remains));
				item.setString("소멸예정포인트", Utilities.getNumberString(expPointScore));
				item.setString("잔여포인트", Utilities.getNumberString(remains));
				item.setString("소멸일자", item.getString("expDt"));

				String timTalk = messageService.getTimeString(code.getTalkSendTime());
				String msg = messageService.getTalkTemplateText(code.getTalkTemplate(), item);
				String subject = messageService.getTalkTemplateText(code.getSubject(), item);
				String itgCustNo = item.getString("itgCustNo");
				expirePointIndex++;
				MsgIfVo message = new MsgIfVo();
				message.setClient("CRM");
				message.setSenderKey(code.getSendProfileKey());
				message.setSendresult("K");
				message.setRevId(item.getString("itgCustNo"));
				message.setDestel(item.getString("mphonNo"));
				message.setClient("CRM");
				message.setDummy10(itgCustNo);
				message.setDummy9(event);
				message.setCampaignId(event + "_" + currentTime);
				message.setMemberSeq(expirePointIndex + "");
				message.setSubject(subject);
				message.setKTitle(subject);

				message.setMsg(msg);
				message.setEtc3(eonUser);
				message.setTemplateCode(code.getTalkTemplateId());
				message.setKMsg(msg);
				message.setKReservetime(timTalk);
				message.setKErrIssend("Y");
				message.setMsgType("AT");
				message.setSendType("2");
				message.setKkoBtnInfo(code.getBtnInfo());
				message.setKkoBtnType("2");

				return message;
			}
		};
	}

	@Bean("writeExpirePointInfo")
	ItemWriter<MsgIfVo> writeExpirePointInfo() {
		return new ItemWriter<MsgIfVo>() {
			@Override
			public void write(List<? extends MsgIfVo> items) throws Exception {
				messageService.sendMessage(items);

			}
		};
	}

}
