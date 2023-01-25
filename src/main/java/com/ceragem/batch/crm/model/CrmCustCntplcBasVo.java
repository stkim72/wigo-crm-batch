package com.ceragem.batch.crm.model;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @ClassName CrmCustCntplcBasVo
 * @author user
 * @date 2022. 4. 12.
 * @Version 1.0
 * @description CRM고객연락처기본 Vo
 * @Company Copyright ⓒ wigo.ai. All Right Reserved
 */
@Getter
@Setter
public class CrmCustCntplcBasVo extends BaseVo {
	/**
	*
	*/
	private static final long serialVersionUID = 1L;
	/**
	 * 연락처일련번호
	 */
	private String cntplcSeq;
	/**
	 * 통합고객번호
	 */
	private String itgCustNo;
	/**
	 * 연락처유형코드 공통코드 : CU300 [001 : 설치장소 , 002 : 직장 , 003 : 자택 , 004 : 휴대전화]
	 */
	private String cntplcTypeCd;
	/**
	 * 대표연락처여부
	 */
	private String repCntplcYn;
	/**
	 * 전화번호
	 */
	private String telNo;
//	/**
//	 * 전화번호암호화값
//	 */
//	private String telNoEncVal;
	/**
	 * 전화뒤자리번호
	 */
	private String telBkDgtNo;
	private String distrctCd;
	/**
	 * 우편번호
	 */
	private String zipCd;
	/**
	 * 주소1내용
	 */
	private String addr1Ctnts;
	/**
	 * 주소2내용
	 */
	private String addr2Ctnts;
	/**
	 * 이메일주소
	 */
	private String emailAddr;
	/**
	 * 등록채널코드 공통코드 : S000 [CRM : CRM , CTC : 상담 , AS : AS , SAP : SAP]
	 */
	private String regChlCd;
	/**
	 * 삭제여부
	 */
	private String delYn;

	private String postStaDt;

	private String postEndDt;
}
