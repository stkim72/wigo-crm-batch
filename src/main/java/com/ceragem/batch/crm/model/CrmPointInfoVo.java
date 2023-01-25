package com.ceragem.batch.crm.model;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @ClassName CrmPointHstVo
 * @author 김성태
 * @date 2022. 4. 21.
 * @Version 1.0
 * @description CRM포인트정보 Vo
 * @Company Copyright ⓒ wigo.ai. All Right Reserved
 */
@Getter
@Setter
public class CrmPointInfoVo {

	/**
	 * 통합고객번호
	 */
	private String itgCustNo;
	/**
	 * 전체포인트
	 */
	private int totalPoint = 0;

	/**
	 * 가용포인트
	 */
	private int availablePoint = 0;

	/**
	 * 누적적립
	 */
	private int totalDeposit = 0;

	/**
	 * 누적사용
	 */
	private int totalWithdrawal = 0;

	/**
	 * 만료된포인트
	 */
	private int expiredPoint = 0;

	/**
	 * 소멸예정포인트
	 */
	private int occurPointScore = 0;

	private String mshipGradeCd;
	
	private String mshipTypeCd;
	
	private String cprtCmpNo;
}
