package com.ceragem.batch.crm.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdvnCmtVo extends BaseVo{
	
	/**
	 * 승급점수 이력 VO
	 */
	
	private static final long serialVersionUID = 1L;

	private String gradeChngHstSeq;
	private String itgCustNo; /* 쿠폰고유번호 */
	private String occurAdvncmtScoreSum; 		/* 승급점수	 */
	private String occurAdvncmtScoreSum2;	/* 승급점수2 */
	private String nowMshipTypeCd;		/* 현재 멤버십 등급 */  
	private String nowMshipGradeCd;		/* 현재 회원 등급 */  
	private String mshipPlcyBasNo;		/* 멤버십정책 번호 */  
	private String mshipGradeCd;		/* 변경될 멤버십 등급 */  
	private String mshpGradeCd;			/* 변경될 회원 등급 */  
	private String mshipExptGradeCd;			/* 변경될 회원 등급 */  
	private String nowAdvncmtStaScore;			/* 현재 승급 시작점수  */  
	private String nowAdvncmtEndScore;		/* 현재 승급 종료 점수 */  
	
	private String advncmtStaScore;		/* 변경되는 승급 시작점수  */  
	private String advncmtEndScore;		/*  변경되는 승급 종료 점수 */  
	
	private String extrcStdMonth;	/* 산정정책 산정기간 직전 x 개월 */  
	private String extrcStd2Month;	/* 유지정책 산정기간 직전 x 개월 */  
	private String extrcKeepMonth;	/* 자동유지기간 */ 
	private String nowGradeKeepScore;	/* 현재 등급유지점수 시작  */ 
	private String nowGradeKeep2Score;	/* 현재 등급유지점수 종료 */ 
	private String gradeKeepScore;	/* 바뀌는 등급유지점수 시작  */ 
	private String gradeKeep2Score;	/* 바뀌는 등급유지점수 종료 */ 
	private String chkKeep;   	/* 기간내에 승급 적용 카운트 */ 
	private String upMonthYn;       /* 실제 매월 1일 등급 업데이트 여부 */

	private String applyStdStaYmd;  /* 적용 시작일자 */ 
	private String applyStdEndYmd;  /* 적용 종료일자 */ 
	private String mshipAdvnCmtScore; /* 멤버십 승금 점수  */ 
	private String mshpGradeTypeCd; /* 맵버십 타입코드 */ 
	
	private String regChlCd = "CRM"; /* 채널아이디 */ 
	
	

	
	
}
