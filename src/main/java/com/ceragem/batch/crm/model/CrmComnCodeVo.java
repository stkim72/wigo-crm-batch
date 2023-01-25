package com.ceragem.batch.crm.model;

import com.ceragem.batch.crm.common.util.Utilities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CrmComnCodeVo extends BaseVo {
	/**
	 * 
	 */
	public static final long serialVersionUID = 1L;
	/**
	 * <p>
	 * 공통코드
	 * </p>
	 */
	private String comnCd;
	/**
	 * <p>
	 * 최상위공통코드
	 * </p>
	 */
	private String topComnCd;
	/**
	 * <p>
	 * 공통코드레벨번호
	 * </p>
	 */
	private Integer comnCdLvlNo;
	/**
	 * <p>
	 * 공통코드순번
	 * </p>
	 */
	private Integer comnCdOdrg;
	/**
	 * <p>
	 * 부모공통코드
	 * </p>
	 */
	private String prntsComnCd;
	/**
	 * <p>
	 * 공통코드명
	 * </p>
	 */
	private String comnCdNm;
	/**
	 * <p>
	 * 사용여부
	 * </p>
	 */
	private String useYn;
	/**
	 * <p>
	 * 참조1공통코드
	 * </p>
	 */
	private String rfrn1ComnCd;
	/**
	 * <p>
	 * 참조2공통코드
	 * </p>
	 */
	private String rfrn2ComnCd;
	/**
	 * <p>
	 * 참조3공통코드
	 * </p>
	 */
	private String rfrn3ComnCd;
	/**
	 * <p>
	 * 참조4공통코드
	 * </p>
	 */
	private String rfrn4ComnCd;
	/**
	 * <p>
	 * 참조5공통코드
	 * </p>
	 */
	private String rfrn5ComnCd;
	/**
	 * <p>
	 * 참조6공통코드
	 * </p>
	 */
	private String rfrn6ComnCd;
	/**
	 * <p>
	 * 참조7공통코드
	 * </p>
	 */
	private String rfrn7ComnCd;
	/**
	 * <p>
	 * 참조8공통코드
	 * </p>
	 */
	private String rfrn8ComnCd;
	/**
	 * <p>
	 * 참조9공통코드
	 * </p>
	 */
	private String rfrn9ComnCd;
	/**
	 * <p>
	 * 공통코드1사용여부
	 * </p>
	 */
	private String comnCd1UseYn;
	/**
	 * <p>
	 * 공통코드2사용여부
	 * </p>
	 */
	private String comnCd2UseYn;
	/**
	 * <p>
	 * 공통코드3사용여부
	 * </p>
	 */
	private String comnCd3UseYn;
	/**
	 * <p>
	 * 공통코드4사용여부
	 * </p>
	 */
	private String comnCd4UseYn;
	/**
	 * <p>
	 * 공통코드5사용여부
	 * </p>
	 */
	private String comnCd5UseYn;
	/**
	 * <p>
	 * 공통코드6사용여부
	 * </p>
	 */
	private String comnCd6UseYn;
	/**
	 * <p>
	 * 공통코드7사용여부
	 * </p>
	 */
	private String comnCd7UseYn;
	/**
	 * <p>
	 * 공통코드8사용여부
	 * </p>
	 */
	private String comnCd8UseYn;
	/**
	 * <p>
	 * 공통코드9사용여부
	 * </p>
	 */
	private String comnCd9UseYn;

	private String templateCode;
	private String tranSysDcd;
	private String sendProfileKey;
	private String reqUserId;
	private String reqDept;
	private String subject;
	private String content;
	private String btnInfo;
	private String ispStatusCode;
	private String tmplStatusCode;
	private String ispCplYn;
	private String errMsg;
	private String etc1;
	private String etc2;
	private String etc3;
	private String etc4;
	private String etc5;
	private String eaiStatus;
	private String createDate;
	private String modifyDate;

	public String getTalkTemplate() {
		return content;
	}

	public String getSmsTemplate() {
		return null;
	}

	public String getMailTemplate() {
		return null;
	}

	public String getTalkTemplateId() {
		return rfrn1ComnCd;
	}

	public String getSmsTemplateId() {
		return rfrn3ComnCd;
	}

	public String getMailTemplateId() {
		return rfrn5ComnCd;
	}

	public String getTalkSendTime() {
		if (Utilities.isEmpty(rfrn2ComnCd))
			return "100000";
		else
			return (rfrn2ComnCd + "0000").substring(0, 6);
	}

	public String getSmsSendTime() {
		if (Utilities.isEmpty(rfrn4ComnCd))
			return "100000";
		else
			return (rfrn4ComnCd + "0000").substring(0, 6);

	}

	public String getMailSendTime() {
		if (Utilities.isEmpty(rfrn6ComnCd))
			return "100000";
		else
			return (rfrn6ComnCd + "0000").substring(0, 6);
	}

	public String getBtnTemplate() {
//		return rfrn7ComnCd;
		return btnInfo;
	}
}
