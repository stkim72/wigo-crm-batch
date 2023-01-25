package com.ceragem.batch.crm.bos.model;

import com.ceragem.batch.crm.model.BaseVo;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @ClassName BosAddress
 * @author 김성태
 * @date 2022. 5. 25.
 * @Version 1.0
 * @description Bos 주소 객체
 * @Company Copyright ⓒ wigo.ai. All Right Reserved
 */
@Getter
@Setter
public class BosAddress extends BaseVo{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2509686724420149560L;
	private String addrSe; /* 주소구분코드(PK) "10:기본 / 11:거주지 기본주소는 한건만 등록 가능함" */
	private String zip; /* 우편번호 */
	private String bassAddr; /* 기본 주소 */
	private String dtlAddr; /* 상세 주소 */

}
