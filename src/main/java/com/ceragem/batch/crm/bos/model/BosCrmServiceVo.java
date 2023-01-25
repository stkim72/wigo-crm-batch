package com.ceragem.batch.crm.bos.model;

import com.ceragem.batch.crm.model.BaseVo;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @ClassName BosCrmServiceVo
 * @author 김성태
 * @date 2022. 5. 24.
 * @Version 1.0
 * @description CRM정기점검
 * @Company Copyright ⓒ wigo.ai. All Right Reserved
 */
@Getter
@Setter
public class BosCrmServiceVo extends BaseVo {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8724477997488231111L;

	/**
	 * 통합고객번호
	 */
	private String crmCustNo;

	private String cntrNo; /* 계약번호 */
	private Integer visitTme; /* 방문회차 */
	private String procMthCd; /* 처리방법코드 */
	private String procMthNm; /* 처리방법명 */
	private Integer svcMonth; /* 서비스개월 */
	private String svcDe; /* 서비스일자 */
	private String dueMonth; /* 예정개월 */
	private String dueDe; /* 예정일자 */
	private String rgipTyCd; /* 정기점검유형코드 */
	private String rgipTyNm; /* 정기점검유형명 */
	private String termTy; /* 기간유형코드 */
	private String termTyNm; /* 기간유형명 */
	private String inspcYn; /* 점검처리여부 */
	private String acptNo; /* 접수번호 */
	private String custNo; /* 고객번호 */
	private String procBhf; /* 처리지점 */
	private String procBhfNm; /* 처리지점명 */
	private String procDe; /* 처리일자 */
	private String procTime; /* 처리시간 */
	private String procEngr; /* 처리기사 */
	private String procEngrNm; /* 처리기사명 */

	private String nextDueDe; /* 차회예정일자 */

//	public String getItgCustNo() {
//		return custNo;
//	}

	/**
	 * 계약번호
	 */
	public String getCntrtNo() {
		return getCntrNo();
	}

	/**
	 * 방문회차수
	 */
	public Integer getVisitTmsCnt() {
		return getVisitTme();
	}

	/**
	 * 처리방법코드
	 */
	public String getTrtMethCd() {
		return getProcMthCd();
	}
	
	
	public String getProcMthCdNm() {
		return getProcMthNm();
	}

	public void setProcMthCdNm(String trtMethCdNm) {
		setProcMthNm(trtMethCdNm);
	}

	
	public String getTrtMethCdNm() {
		return getProcMthNm();
	}

	public void setTrtMethCdNm(String trtMethCdNm) {
		setProcMthNm(trtMethCdNm);
	}

	/**
	 * 처리방법명
	 */
	public String getTrtMethNm() {
		return getProcMthNm();
	}

	/**
	 * 서비스개월수
	 */
	public Integer getSvcMonsCnt() {
		return getSvcMonth();
	}

	/**
	 * 서비스일자
	 */
	public String getSvcDate() {
		return getSvcDe();
	}

	/**
	 * 예정년월
	 */
	public String getExptYyyymm() {
		return getDueMonth();
	}

	/**
	 * 예정일자
	 */
	public String getExptDate() {
		return getDueDe();
	}

	/**
	 * 정기점검유형코드
	 */
	public String getRgitvInspTypeCd() {
		return getRgipTyCd();
	}

	/**
	 * 정기점검유형명
	 */
	public String getRgitvInspTypeNm() {
		return getRgipTyNm();
	}

	public String getRgipTyCdNm() {
		return this.getRgipTyNm();
	}

	public void setRgipTyCdNm(String rgipTyCdNm) {
		this.setRgipTyNm(rgipTyCdNm);
	}

	/**
	 * 기간유형코드
	 */
	public String getPerdTypeCd() {
		return getTermTy();
	}

	/**
	 * 기간유형명
	 */
	public String getPerdTypeNm() {
//		return this.termTyNm;
		return getTermTyNm();
	}

	/**
	 * 점검처리여부
	 */
	public String getInspTrtYn() {
		return getInspcYn();
	}

	/**
	 * 정기점검접수번호
	 */
	public String getRgitvInspSubmitNo() {
		return getAcptNo();
	}

	/**
	 * BOS고객번호
	 */
	public String getBosCustNo() {
		return getCustNo();
	}

	/**
	 * 처리조직코드
	 */
	public String getTrtOrgCd() {
		return getProcBhf();
	}

	/**
	 * TRT_ORG_NM
	 */
	public String getTrtOrgNm() {
		return getProcBhfNm();
	}

	/**
	 * TRT_ENGNR_EMP_NM
	 */
	public String getTrtEngnrEmpNm() {
		return getProcEngrNm();
	}

	/**
	 * 처리일자
	 */
	public String getTrtDate() {
		return getProcDe();
	}

	/**
	 * 처리시간
	 */
	public String getTrtHour() {
		return getProcTime();
	}

	/**
	 * 처리기사사원번호
	 */
	public String getTrtEngnrEmpNo() {
		return getProcEngr();
	}

	/**
	 * 차회예정일자
	 */
	public String getNxtmExptDate() {
		return getNextDueDe();
	}

	public String getItgCustNo() {
		return getCrmCustNo();
	}
}
