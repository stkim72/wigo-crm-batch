package com.ceragem.batch.crm.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ceragem.batch.crm.common.util.Utilities;
import com.ceragem.batch.crm.model.CrmComnCodeVo;
import com.ceragem.batch.crm.model.CrmCustBasVo;
import com.ceragem.batch.crm.model.EvSendFormVo;
import com.ceragem.batch.crm.model.EvSendListVo;
import com.ceragem.batch.crm.model.MsgIfVo;

/**
 * 
 * @ClassName SendMessageService
 * @author 김성태
 * @date 2022. 6. 28.
 * @Version 1.0
 * @description 메시지 보내기(알림톡,메일,문자 등등)
 * @Company Copyright ⓒ wigo.ai. All Right Reserved
 */

@Service
public class SendMessageService {

	private final SimpleDateFormat DATETIME_FORMAT_ORG = new SimpleDateFormat("yyyyMMddHHmmss", Locale.KOREAN);
	private final SimpleDateFormat DATETIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREAN);

//	private static final String CLIENT = "crm_1";

//	private static final int DORM_BEFOR_CNT = 1;

	@Value("${spring.profiles.active}")
	String activeProfile;

	@Value("${spring.eon.sms-sender-no}")
	String smsSenderNo;

	@Value("${spring.eon.sms-sender-name}")
	String smsSenderName;

	@Value("${spring.eon.talk-sender-key}")
	String talkSenderKey;

	@Value("${spring.eon.user}")
	String eonUser;

	@Value("${spring.eon.mail-addr}")
	String mailAddr;

	@Value("${spring.eon.mail-name}")
	String mailName;
//	@Value("${spring.message.talk-dorm-before-code}")
//	String talkDormBeforeCode;
//
//	@Value("${spring.message.talk-dorm-code}")
//	String talkDormCode;
//
//	@Value("${spring.message.talk-withdrawal-code}")
//	String talkWithdrawalCode;

	@Autowired
	MsgIfService msgService;

	@Autowired
	EvSendFormService emailService;

	@Autowired
	PushIfService pushService;

	Map<String, CrmComnCodeVo> messageCodeMap = null;

	Map<String, Integer> eventCntMap = new HashMap<String, Integer>();

	String currentTime = Utilities.getDateTimeString();

	public void initTemplate() {
		if (messageCodeMap != null)
			return;
		messageCodeMap = new HashMap<String, CrmComnCodeVo>();
		List<CrmComnCodeVo> list = msgService.getCodeList();
		for (int i = 0; i < list.size(); i++) {
			CrmComnCodeVo code = list.get(i);
//			if (Utilities.isEmpty(code.getTalkTemplate()) && !"prod".equalsIgnoreCase(activeProfile)) {
//				code.setContent("테스트#{a} #{custNm}#{mphonNo}#{tstmmm}#{custNm}#{}#{custNm}메시지");
//			}
//			if (Utilities.isEmpty(code.getSubject()) && !"prod".equalsIgnoreCase(activeProfile)) {
//				code.setSubject("테스트 제목");
//			}
			messageCodeMap.put(code.getComnCd(), code);
		}
	}

	public CrmComnCodeVo getCode(String code) {
		return messageCodeMap.get(code);
	}

	public int getEventCnt(String event) {
		if (!eventCntMap.containsKey(event))
			eventCntMap.put(event, 0);

		int cnt = eventCntMap.get(event) + 1;
		eventCntMap.put(event, cnt);
		return cnt;
	}

	public String getTimeString(String tm) throws ParseException {
		String time = tm;
		Calendar cal = Calendar.getInstance();
		if (Utilities.parseLong(Utilities.getTimeString()) >= Utilities.parseLong(time)) {
			if (Utilities.parseLong(time) > 180000)
				cal.add(Calendar.DATE, 1);
			else
				time = Utilities.getTimeString();
		}
		String dateString = Utilities.getDateString(cal.getTime());
		Date dt = DATETIME_FORMAT_ORG.parse(dateString + time);
		return DATETIME_FORMAT.format(dt);
	}

//	private String getTimeString(int hour, int minute, int second) {
//		Calendar cal = Calendar.getInstance();
//		cal.set(Calendar.HOUR, hour);
//		cal.set(Calendar.MINUTE, minute);
//		cal.set(Calendar.SECOND, second);
//		return DATETIME_FORMAT.format(cal.getTime());
//	}
	public String getTalkTemplateText(String template, Map<String, Object> map) {
		if (Utilities.isEmpty(template) || Utilities.isEmpty(map))
			return template;
		Map<String, Object> variant = getVariant(template, map);
		String ret = template;
		for (Map.Entry<String, Object> entry : variant.entrySet()) {
			String strKey = entry.getKey();
			String strValue = Utilities.nullCheck(entry.getValue());
			ret = ret.replace("#{" + strKey + "}", strValue + "");
		}
		ret = ret.replace("#{}", "");
		return ret;
	}

	public String getMailTemplateText(String template, String data) {

		if (Utilities.isEmpty(template) || Utilities.isEmpty(data))
			return template;
		String[] arr = data.split("$T$");
		String ret = template;
		for (int i = 0; i < arr.length; i++) {
			if (Utilities.isEmpty(arr[i]) || Utilities.isEmpty(arr[i].trim()))
				continue;
			StringBuffer bf = new StringBuffer();
			bf.append("${txt_");
			bf.append(i + 1);
			bf.append("}");
			ret = ret.replace(bf.toString(), arr[i]);
		}
		return ret;
	}

	public MsgIfVo getCustomerStatusMsg(CrmCustBasVo cust, String event, String procDt) throws ParseException {
		if (cust == null || Utilities.isEmpty(event) || !messageCodeMap.containsKey(event))
			return null;
		CrmComnCodeVo code = messageCodeMap.get(event);
		if (code == null || !"Y".equals(code.getUseYn()))
			return null;
		Map<String, Object> custMap = makeEonMap(cust);
		custMap.put("고객님", cust.getCustNm());
		custMap.put("고객명", cust.getCustNm());
		custMap.put("회원명", cust.getCustNm());
		custMap.put("핸드폰번호", Utilities.getPhoneNumberFormat(cust.getMphonNo()));
		custMap.put("휴면처리일자", procDt);
		custMap.put("탈퇴처리일자", procDt);
		custMap.put("비활동기간", cust.getDormPerdCd());
		custMap.put("비활동 기간", cust.getDormPerdCd());

		String timTalk = getTimeString(code.getTalkSendTime());
//		String timSms = getTimeString(Utilities.getDateString() + code.getSmsSendTime());
		StringBuffer bf = new StringBuffer();
		bf.append(cust.getCustNm());
		String msg = getTalkTemplateText(code.getTalkTemplate(), custMap);
		String subject = getTalkTemplateText(code.getSubject(), custMap);
		MsgIfVo message = new MsgIfVo();
		message.setSenderKey(code.getSendProfileKey());
		message.setSendresult("K");
		message.setRevId(cust.getItgCustNo());
		message.setDestel(cust.getMphonNo());
		message.setClient("CRM");
		message.setDummy10(cust.getItgCustNo());
		message.setDummy9(event);
		message.setCampaignId(event + "_" + currentTime);
		message.setMemberSeq(getEventCnt(event) + "");
		message.setSubject(subject);
		message.setKTitle(subject);

//		message.setMsg(code.getSmsTemplate());
		message.setMsg(msg);
//		message.setReservetime(timSms);
		message.setEtc3(eonUser);
		message.setTemplateCode(code.getTalkTemplateId());
		message.setKMsg(msg);
		message.setKReservetime(timTalk);
		message.setKErrIssend("Y");
//		message.setKErrIssend("Y".equals(cust.getSmsRcvAgreeYn()) ? "Y" : "N");
		message.setMsgType("AT");
		message.setSendType("2");
		message.setKkoBtnInfo(code.getBtnInfo());
		message.setKkoBtnType("2");
		return message;

	}

	private Map<String, Object> makeEonMap(Object data) {
		Map<String, Object> map = Utilities.beanToMap(data);
//		map.put("고객", map.get("custNm"));
		return map;
	}

	public void sendCustomerStatus(CrmCustBasVo cust, String event, String procDt) throws Exception {
		if (cust == null || Utilities.isEmpty(event))
			return;
		MsgIfVo message = getCustomerStatusMsg(cust, event, procDt);
		if (message != null) {
			sendMessage(message);
		}

	}

	public void sendCustomerStatusMail(List<CrmCustBasVo> list, String event) throws Exception {
		if (Utilities.isEmpty(list) || Utilities.isEmpty(event))
			return;
		CrmComnCodeVo code = messageCodeMap.get(event);
		if (code == null || !"Y".equals(code.getUseYn()))
			return;
		if (Utilities.isEmpty(code.getMailTemplateId()))
			return;
		Calendar cal = Calendar.getInstance();
		if (Utilities.parseLong(Utilities.getTimeString()) >= Utilities.parseLong(code.getMailSendTime())) {
			cal.add(Calendar.DATE, 1);
		}
		String dateString = Utilities.getDateString(cal.getTime());
		String time = dateString + code.getMailSendTime();

		EvSendFormVo form = new EvSendFormVo();
		form.setMailKind(code.getMailTemplateId());
		form.setRegdate(time);
//		form.setSendEmail(mailAddr);
//		form.setReturnEmail(mailAddr);
//		form.setSendName(mailName);
		form.setSendFlag("N");
		form.setListEndflag("Y");
		form.setSubject(null);
		form.setSendEmail(null);
		form.setReturnEmail(null);
		form.setSendName(null);
//		if (event.startsWith("0")) {
//			form.setSubject("휴면계정이관 안내");
//		} else {
//			form.setSubject("회원탈회 안내");
//		}

		emailService.insert(form);

		int cnt = 0;
		for (int i = 0; i < list.size(); i++) {
			CrmCustBasVo cust = list.get(i);
			if (Utilities.isEmpty(cust.getEmailAddr()))
				continue;
			cnt++;
			EvSendListVo mail = new EvSendListVo();
			mail.setSeq(form.getSeq());
			mail.setMailKind(form.getMailKind());
			mail.setListSeq(cnt);
			mail.setEmail(cust.getEmailAddr());
			mail.setName(cust.getCustNm());
			mail.setMapping1(cust.getCustNm());

			emailService.insertDetail(mail);
		}

	}
//	public void sendWithdrawlBefore(CrmCustBasVo vo) {
//		if (vo == null)
//			return;
//	}
//
//	public void sendWithdrawl(CrmCustBasVo vo) {
//		if (vo == null)
//			return;
//	}
//
//	public void sendWithdrawlAfter(CrmCustBasVo vo) {
//		if (vo == null)
//			return;
//	}

	public int sendMessage(MsgIfVo message) throws Exception {
		message.setSrctel(smsSenderNo);
		if (Utilities.isEmpty(message.getSenderKey()))
			message.setSenderKey(talkSenderKey);
//		if (!"prod".equalsIgnoreCase(activeProfile))
//			message.setDestel("01024298271");
		return msgService.insert(message);
	}

	public void sendMessage(List<? extends MsgIfVo> msgList) throws Exception {
		for (int i = 0; i < msgList.size(); i++) {
			MsgIfVo message = msgList.get(i);
			message.setMemberSeq((i + 1) + "");
			sendMessage(message);
		}

	}

	public Map<String, Object> getVariant(String template, Map<String, Object> map) {
		Map<String, Object> variant = new LinkedHashMap<>();
		int index = -1;
		if (Utilities.isEmpty(template))
			return variant;
		while (true) {
			int idx = template.indexOf("#{", index);
			if (idx < 0)
				break;
			int end = template.indexOf("}", idx);
			if (end < 0)
				break;
			if (end > idx + 2) {
				String key = template.substring(idx + 2, end).trim();
				if (!variant.containsKey(key)) {
					variant.put(key, map.get(key));
				}
			}

			index = end;
		}
		return variant;
	}

	public void sendCoupnCancel(List<HashMap<String, Object>> sendData, String evnt) throws Exception {

		if (sendData == null || Utilities.isEmpty(evnt))
			return;

		for (HashMap<String, Object> data : sendData) {

			MsgIfVo message = getCoupnCancelMsg(data, evnt);
			if (message != null) {
				sendMessage(message);
			}
		}
	}

	public MsgIfVo getCoupnCancelMsg(HashMap<String, Object> data, String evnt) throws ParseException {

		if (data == null || Utilities.isEmpty(evnt) || !messageCodeMap.containsKey(evnt))
			return null;

		CrmComnCodeVo code = messageCodeMap.get(evnt);
		if (code == null || !"Y".equals(code.getUseYn()))
			return null;

		Map<String, Object> coupnMap = Utilities.beanToMap(data);
		String timTalk = getTimeString(code.getTalkSendTime());
//		String timSms = getTimeString(Utilities.getDateString() + code.getSmsSendTime());
//		StringBuffer bf = new StringBuffer();
//		bf.append(cust.getCustNm());
		String msg = getTalkTemplateText(code.getTalkTemplate(), coupnMap);
		String subject = getTalkTemplateText(code.getSubject(), coupnMap);
		MsgIfVo message = new MsgIfVo();
		message.setSenderKey(code.getSendProfileKey());
		message.setSendresult("K");
		message.setRevId(String.valueOf(data.get("ITG_CUST_NO")));
		message.setClient("CRM");
		message.setDestel(String.valueOf(data.get("MPHONE_NO")));
		message.setDummy9(evnt);
		message.setCampaignId(evnt + "_" + currentTime);

		message.setDummy10(String.valueOf(data.get("ITG_CUST_NO")));

		message.setMemberSeq(getEventCnt(evnt) + "");

		message.setSubject(subject);
		message.setKTitle(subject);
		message.setMsg(msg);
		message.setKMsg(msg);

		message.setEtc3(eonUser);
		message.setTemplateCode(code.getTalkTemplateId());

		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
//        DateFormat df = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
		cal.add(Calendar.MINUTE, 2);
//		message.setKReservetime(df.format(cal.getTime()));

		message.setKReservetime(timTalk);
		message.setKErrIssend("Y");
		message.setMsgType("AT");
		message.setSendType("2");
		message.setKkoBtnInfo(code.getBtnTemplate());
		message.setKkoBtnType("2");
		return message;
	}

	public void sendPointMessage(HashMap<String, Object> sendData, String evnt) throws Exception {

		MsgIfVo message = getPointMsg(sendData, evnt);
		if (message != null) {
			sendMessage(message);
		}
	}

	public MsgIfVo getPointMsg(HashMap<String, Object> data, String evnt) throws Exception {

		CrmComnCodeVo code = messageCodeMap.get(evnt);
		if (code == null || !"Y".equals(code.getUseYn()))
			return null;
		Map<String, Object> coupnMap = Utilities.beanToMap(data);
		String timTalk = getTimeString(code.getTalkSendTime());
//		String timSms = getTimeString(Utilities.getDateString() + code.getSmsSendTime());
//		StringBuffer bf = new StringBuffer();
//		bf.append(cust.getCustNm());
		String msg = getTalkTemplateText(code.getTalkTemplate(), coupnMap);
		String subject = getTalkTemplateText(code.getSubject(), coupnMap);
		MsgIfVo message = new MsgIfVo();
		message.setSenderKey(code.getSendProfileKey());
		message.setSendresult("K");
		message.setRevId(String.valueOf(data.get("ITG_CUST_NO")));
		message.setClient("CRM");
		message.setDestel(String.valueOf(data.get("MPHONE_NO")));
		message.setDummy9(evnt);
		message.setCampaignId(evnt + "_" + currentTime);

		message.setDummy10(String.valueOf(data.get("ITG_CUST_NO")));

		message.setMemberSeq(getEventCnt(evnt) + "");

		message.setSubject(subject);
		message.setKTitle(subject);
		message.setMsg(msg);
		message.setKMsg(msg);

		message.setEtc3(eonUser);
		message.setTemplateCode(code.getTalkTemplateId());

//		Calendar cal = Calendar.getInstance();
//		cal.setTime(new Date());
//      DateFormat df = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
//		cal.add(Calendar.MINUTE, 2);
//		message.setKReservetime(df.format(cal.getTime()));

		message.setKReservetime(timTalk);
		message.setKErrIssend("Y");
		message.setMsgType("AT");
		message.setSendType("2");
		message.setKkoBtnInfo(code.getBtnTemplate());
		message.setKkoBtnType("2");

		return message;
	}
}
