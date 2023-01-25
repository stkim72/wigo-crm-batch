package com.ceragem.batch.crm.bos.model;

import java.util.List;

import com.ceragem.batch.crm.model.BaseVo;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @ClassName BosCustVo
 * @author 김성태
 * @date 2022. 5. 24.
 * @Version 1.0
 * @description BOS 사용자 정보
 * @Company Copyright ⓒ wigo.ai. All Right Reserved
 */
@Getter
@Setter
public class BosCustVo extends BaseVo {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4507806472321186239L;

	/**
	 * 통합고객번호
	 */
	private String crmCustNo;

	private String custNo; /* 고객번호 */
	private String custNm; /* 고객명 */
	private String custSeCd; /* 고객 구분 코드 */
	private String custSeNm; /* 고객 구분 */
	private String custTyCd; /* 고객 유형 코드 1:개인/2:법인 */
	private String custTyNm; /* 고객 유형 명 */
	private String custTyDtlCd; /* 고객 유형 상세 코드 "1001:개인2001:개인사업자(2:법인)3001:법인(2:법인)" */
	private String custTyDtlNm; /* 고객 유형 상세 명 */
	private String custCntrSeCd; /* 고객 계약 구분 코드 */
	private String custCntrSeNm; /* 고객 계약 구분 */
	private String sexCd; /* 성별코드 F:여자/M:남자 */
	private String sex; /* 성별 */
	private String bthd; /* 생일 */
	private String nationCd; /* 국가코드 */
	private String nationNm; /* 국가명 */
	private String dmstcCd; /* 내국인 코드 0:내국인 / 1:외국인 */
	private String pasportNo; /* 여권 번호 */
	private String mobNo; /* 모바일번호 */
	private String telNo; /* 전화번호 */
	private String wrcTelNo; /* 직장전화번호 */
	private String email; /* 이메일 */
	private String bsnmNo; /* 사업자 번호 */
	private String cmpNm; /* 회사 명 */
	private String rprsntvNm; /* 대표자 명 */
	private String cmpScale; /* 회사 규모 */
	private String bizcnd; /* 업태 */
	private String induty; /* 업종 */
	private String taxtTy; /* 과세 유형 코드 1:과세/2:비과세 */
	private String frstSscrDe; /* 최초 가입 일자 */
	private String sscrDe; /* 가입 일자 */
	private String rm; /* 비고 */
	private String marktRcvAgreYn; /* 마케팅 수신 동의 여부 */
	private String smsRcvAgreYn; /* SMS 수신 동의 여부 */
	private String emailRcvAgreYn; /* EMAIL 수신 동의 여부 */
	private String nmCnfirmLastDt; /* 이름 확인 최종 일시 */
	private String safekey; /* SAFEKEY */
	private String cdtCnfirmLastDt; /* 신용 확인 최종 일시 */
	private String taxRtnTyCd; /* 세금 신고 유형 코드 "10:세금계산서발행 20:현금영수증 30:현금영수증 자진신고 50:미발행" */
	private String psnCertSeCd; /* 개인인증 구분 코드 "11:사업자번호 13:휴대폰번호" */
	private String psnCertInfo; /* 개인인증 정보 */
	private String cashrcIssuTyCd; /* 현금영수증 발급유형코드 "10:소득공제 11:지출증빙 12:미발행" */
	private List<BosAddress> addrList; /* 고객 주소 리스트 */

	public String getItgCustNo() {
		return getCrmCustNo();
	}
}
