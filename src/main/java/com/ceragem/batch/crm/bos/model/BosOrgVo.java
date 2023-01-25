package com.ceragem.batch.crm.bos.model;

import com.ceragem.batch.crm.model.BaseVo;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @ClassName BosCrmOrgVo
 * @author 김성태
 * @date 2022. 5. 24.
 * @Version 1.0
 * @description Crm 조직정보
 * @Company Copyright ⓒ wigo.ai. All Right Reserved
 */
@Getter
@Setter
public class BosOrgVo extends BaseVo {
	/**
	 * 
	 */
	private static final long serialVersionUID = 9172489961902633470L;
	private String orgzCd; /* 조직코드 */
	private String orgzNm; /* 조직명 */
	private String hqSeCd; /* 본부구분코드 */
	private String hqSeCdnm; /* 본부구분코드명 */
	private String orgzSe; /* 조직구분 */
	private String orgzSenm; /* 조직구분명 */
	private String orgzLvl; /* 조직레벨 */
	private String gmOrgmbNo; /* 조직장조직원번호 */
	private String orgmbNm; /* 조직장명 */
	private String upperOrgzCd; /* 상위조직코드 */
	private String upperOrgNm; /* 상위조직명 */
	private String bizplace; /* 사업장 */
	private String orgzSubBizplace; /* 종사업장 */
	private String bsnGrp; /* 영업그룹 */
	private String orgzCustNo; /* 고객코드 */
	private String chrgrDept; /* 담당부서 */
	private String chrgrNm; /* 담당자명 */
	private String telNo; /* 담당자 전화번호(복호) */
	private String chrgrEmail; /* 담당자 이메일 */
	private String faxNo; /* 팩스번호 */
	private String postNo; /* 우편번호 */
	private String bassAddr; /* 기본주소 */
	private String dtlAddr; /* 상세주소 */
	private String operDe; /* 운영일자 */
	private String cldDe; /* 폐쇄일자 */
	private String wrhCd; /* 창고코드 */
	private String wrhNm; /* 창고명 */
	private String istYn; /* 설치여부 */
	private String opertSmpYn; /* 작업간소화여부 */
	private String rgipYn; /* 정기점검여부 */
	private String bsnPsbYn; /* 영업가능여부 */
	private String rm; /* 비고 */
	private String regUsrId; /* 등록자ID */
	private String regDt; /* 등록일시 */
	private String updUsrId; /* 수정자ID */
	private String updDt; /* 수정일시 */

}
