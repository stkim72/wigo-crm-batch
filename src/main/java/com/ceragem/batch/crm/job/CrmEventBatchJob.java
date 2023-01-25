package com.ceragem.batch.crm.job;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
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
import com.ceragem.batch.crm.model.BatchInfoBasVo;
import com.ceragem.batch.crm.model.BatchMap;
import com.ceragem.batch.crm.model.CrmApiDataVo;
import com.ceragem.batch.crm.model.CrmComnCodeVo;
import com.ceragem.batch.crm.model.CrmCustBasVo;
import com.ceragem.batch.crm.model.MsgIfVo;
import com.ceragem.batch.crm.service.CrmApiService;
import com.ceragem.batch.crm.service.SendMessageService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@RequiredArgsConstructor
class CrmEventBatchJob {

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

	@Autowired
	CrmApiService apiService;

	String currentTime = Utilities.getDateTimeString();

	List<BatchMap> expirePointList = null;

	List<CrmCustBasVo> birthdayList = null;
	int expirePointIndex = 0;

	String yesterday = null;
	String today = null;

	public void initDate() {
		JobParameters jobParameters = jobListener.getJobExecution().getJobParameters();
		String ymd = jobParameters.getString("ymd");
		if (Utilities.isEmpty(ymd)) {
			Calendar cal = Calendar.getInstance();
			today = Utilities.getDateString(cal.getTime());
			cal.add(Calendar.DATE, -1);
			yesterday = Utilities.getDateString(cal.getTime());
		} else {
			today = ymd;
			try {
				Date dt = Constants._DATE_FORMAT.parse(ymd);
				Calendar cal = Calendar.getInstance();
				cal.setTime(dt);
				cal.add(Calendar.DATE, -1);
				yesterday = Utilities.getDateString(cal.getTime());

			} catch (ParseException e) {
				log.debug(e.getMessage());
			}
		}
	}

	@Bean("crmEventJob")
	Job crmEventJob() {
		log.debug("crmEventJob");

		messageService.initTemplate();
		return jobBuilderFactory.get("crmEventJob").listener(jobListener).start(stepExpirePoint()).next(stepBirthday())
				.build();
	}

	// 포인트 만료시키기
	@Bean("stepExpirePoint")
	Step stepExpirePoint() {
		return stepBuilderFactory.get("stepExpirePoint").<BatchMap, MsgIfVo>chunk(Constants.FETCH_COUNT)
				.reader(readerExpirePoint()).processor(procExpirePoint()).writer(writeExpirePoint()).build();
	}

	@Bean("readerExpirePoint")
	ItemReader<BatchMap> readerExpirePoint() {
		return new ItemReader<BatchMap>() {
			@Override
			public BatchMap read() throws Exception {
				Calendar cal = Calendar.getInstance();
				today = Utilities.getDateString(cal.getTime());
				BatchMap param = new BatchMap();
				param.setString("ymd", today);
				dao.updateExpirePoint(param);
				return null;

//				if (expirePointList == null) {
//					initDate();
//					BatchMap param = new BatchMap();
//					param.setString("ymd", yesterday);
//					expirePointList = dao.selectExpirePointList(param);
//				}
//				if (Utilities.isEmpty(expirePointList)) {
//					BatchMap param = new BatchMap();
//					param.setString("ymd", today);
//					dao.updateExpirePoint(param);
//					return null;
//				}
//				return expirePointList.remove(0);
			}
		};
	}

	@Bean("procExpirePoint")
	ItemProcessor<BatchMap, MsgIfVo> procExpirePoint() {
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
				item.setString("remainPointScore", Utilities.getNumberString(remains));
				item.setString("소멸예정포인트", Utilities.getNumberString(expPointScore));
				item.setString("잔여포인트", Utilities.getNumberString(remains));
				item.setString("소멸일자", item.getString("expDt"));
				custDao.updateRemainPoint(item);

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

	@Bean("writeExpirePoint")
	ItemWriter<MsgIfVo> writeExpirePoint() {
		return new ItemWriter<MsgIfVo>() {
			@Override
			public void write(List<? extends MsgIfVo> items) throws Exception {
				messageService.sendMessage(items);

			}
		};
	}

	/* 생일인 사람 호출하기 */
	@Bean("stepBirthday")
	Step stepBirthday() {
		return stepBuilderFactory.get("stepBirthday").<CrmCustBasVo, CrmCustBasVo>chunk(Constants.FETCH_COUNT)
				.reader(readerBirthday()).writer(writeBirthday()).build();
	}

	@Bean("readerBirthday")
	ItemReader<CrmCustBasVo> readerBirthday() {
		return new ItemReader<CrmCustBasVo>() {
			@Override
			public CrmCustBasVo read() throws Exception {
				if (birthdayList == null) {
					BatchMap param = new BatchMap();
					param.setString("birthday", today.substring(4));
//					param.setString("ymd", "20240529");
					birthdayList = custDao.selectBirthdayList(param);
				}
				if (Utilities.isEmpty(birthdayList)) {
					return null;
				}
				return birthdayList.remove(0);
			}
		};
	}

	@Bean("writeBirthday")
	ItemWriter<CrmCustBasVo> writeBirthday() {
		return new ItemWriter<CrmCustBasVo>() {
			@Override
			public void write(List<? extends CrmCustBasVo> items) throws Exception {
				BatchInfoBasVo batchInfo = (BatchInfoBasVo) jobListener.getJobExecution().getExecutionContext()
						.get("batchInfo");

				for (int i = 0; i < items.size(); i++) {
					CrmCustBasVo item = items.get(i);
					CrmApiDataVo result = apiService.getApiData(
							"/crm/v1.0/membership/event/" + item.getItgCustNo() + "/100/CRM/", null, "GET", false);
					if (!"IAR0200".equals(result.getCode())) {
						String msg = "[생일이벤트][/crm/v1.0/membership/event/" + item.getItgCustNo() + "/100/CRM/]" + "["
								+ result.getCode() + "]" + result.getMessage();
						if (batchInfo != null)
							batchInfo.addErrorLog(msg);
						continue;
//						birthdayList.clear();
//						break;
					}

				}

			}
		};
	}
}
