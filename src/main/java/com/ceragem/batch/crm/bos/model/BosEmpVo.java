package com.ceragem.batch.crm.bos.model;

import com.ceragem.batch.crm.model.BaseVo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
/**
 * 
 * @ClassName BosCrmEmpVo
 * @author 김성태
 * @date 2022. 5. 24.
 * @Version 1.0
 * @description 조직원정보
 * @Company Copyright ⓒ wigo.ai. All Right Reserved
 */
public class BosEmpVo extends BaseVo {
	/**
	 * 
	 */
	private static final long serialVersionUID = 641044159134775683L;
	private String orgmbNo; /* 조직원번호 */
	private String orgmbNm; /* 조직원명 */
	private String bthd; /* 생년월일 */
	private String pasportNo; /* 여권번호 */
	private String hffcSts; /* 재직상태 */
	private String hffcStsnm; /* 재직상태명 */
	private String sexCd; /* 성별 코드(Sd005) */
	private String nationCd; /* 국가 */
	private String nationCdnm; /* 국가명 */
	private String mobNo; /* 핸드폰 */
	private String cmpTelno; /* 직장전화번호 */
	private String houseTelno; /* 집전화번호 */
	private String wrhMngYn; /* 창고담당자여부 */
	private String email; /* 이메일 */
	private String bsnPsbYn; /* 영업가능여부 */
	private String wrhYn; /* 창고여부 */
	private String carOwnYn; /* 차량소유여부 */
	private String orgzCd; /* 조직코드 */
	private String orgzNm; /* 조직 코드명 */
	private String ofcps; /* 직위 */
	private String ofcpsnm; /* 직위명 */
	private String clsf; /* 직급 */
	private String clsfnm; /* 직급명 */
	private String sponsorId; /* 스폰서Id */
	private String empyDe; /* 입사 일자 */
	private String retireDe; /* 퇴사 일자 */
	private String marriageSts; /* 결혼상태 */
	private String marriageStsnm; /* 결혼상태명 */
	private String taxRtnNo; /* 세금신고번호 */
	private String regUsrId; /* 등록자 Id */
	private String regDt; /* 등록 일시 */
	private String updUsrId; /* 수정자 Id */
	private String updDt; /* 수정 일시 */
	private String sapEmpNo; /* sap Hr사번 */
	private String orgmbCustNo; /* 고객코드 */
	private String pchsNo; /* 구매처 */

}
