package com.ceragem.batch.crm.bos.model;

import com.ceragem.batch.crm.model.BaseVo;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @ClassName CrmBosBcustTxnVo
 * @author 김성태
 * @date 2022. 6. 30.
 * @Version 1.0
 * @description CRMBOS부실고객내역 Vo
 * @Company Copyright ⓒ wigo.ai. All Right Reserved
 */
@Getter
@Setter
public class BosCrmBcustTxnVo extends BaseVo {
	/**
	*
	*/
	private static final long serialVersionUID = 1L;
	/**
	 * 고객번호
	 */
	private String custno;
	/**
	 * 통합고객번호
	 */
	private String crmcustno;
	/**
	 * 고객명
	 */
	private String custnm;
	/**
	 * 생년월일
	 */
	private String bthd;
	/**
	 * 모바일번호
	 */
	private String mobno;
	/**
	 * 전화번호
	 */
	private String telno;
	/**
	 * 부실고객사유
	 */
	private String bcustreason;
	/**
	 * 부실고객등록일
	 */
	private String bcustregdt;
}
