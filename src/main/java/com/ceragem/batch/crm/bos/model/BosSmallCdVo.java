package com.ceragem.batch.crm.bos.model;

import com.ceragem.batch.crm.model.BaseVo;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @ClassName BosSmallCdVo
 * @author 김성태
 * @date 2022. 10. 5.
 * @Version 1.0
 * @description bos 소분류
 * @Company Copyright ⓒ wigo.ai. All Right Reserved
 */
@Getter
@Setter
public class BosSmallCdVo extends BaseVo {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7037147229109674814L;
	String cmGrpCd;
	String cmGrpCdNm;
	String cmCd;
	String cmCdNm;
	String cmCdDesc;
	String useYn;
	int sortOrdr;
}
