package com.ceragem.batch.crm.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GradeCouponVo extends BaseVo{
	/**
	 * 등급별 쿠폰 리스트 VO
	 */
	private static final long serialVersionUID = 1L;

	private String mshipCoupnBasNo; /* 쿠폰고유번호 */
	private String cycleCd; 		/* 배치 주기  001 : 1회, 002 : 매일, 003 : 매주, 004 : 매월, 005 : 분기, 006 : 매년		 */
	private String mshipGradeCd;	/* 멤버십등급 */
	private String mshpGradeCd;		/* 회원등급 */  
	private String cprtCmpNo;		/* 제휴사코드 */  
	private String coupnTypeCd;		/* 쿠폰타입 */  
	private String mshipPlcyNm;		/* 멤버십정책명 */  
	private int prvQnty;			/* 제공매수 */  
	private String applyStdStaYmd;	/* 정책적용시작일 */  
	private String applyStdEndYmd;	/* 정책적용종료일 */  
	private String coupnBasNm;		/* 쿠폰명 */  
	private String useStdDayCondCd;	/* 발행 즉시여부 */  
	private String fromPblsStdDay;	/* 발행기간시작일 */  
	private String toPblsStdDay;	/* 발행기간종료일 */
	


	
	
	
}
