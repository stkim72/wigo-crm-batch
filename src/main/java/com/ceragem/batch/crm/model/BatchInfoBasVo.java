package com.ceragem.batch.crm.model;

import java.util.ArrayList;
import java.util.List;

import com.ceragem.batch.crm.common.util.Utilities;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @ClassName BatchInfoBasVo
 * @author 김성태
 * @date 2022. 5. 25.
 * @Version 1.0
 * @description 배치정보기본 Vo
 * @Company Copyright ⓒ wigo.ai. All Right Reserved
 */
@Getter
@Setter
public class BatchInfoBasVo extends BaseVo {
	/**
	*
	*/
	private static final long serialVersionUID = 1L;
	/**
	 * 배치코드
	 */
	private String batchCd;
	/**
	 * 시스템코드
	 */
	private String sysCd;
	/**
	 * 배치명
	 */
	private String batchNm;
	/**
	 * 작업ID
	 */
	private String wrkId;
	/**
	 * 최종실행일시
	 */
	private String lastExecDt;
	/**
	 * 최종성공여부
	 */
	private String lastSuccYn;
	/**
	 * 사용여부
	 */
	private String useYn;

	/* 에러 로그 */
	private List<String> errorLogList = new ArrayList<>();

	public void addErrorLog(String error) {
		if (Utilities.isNotEmpty(error))
			errorLogList.add(error);
	}

	public String getErrorLog() {
		if (Utilities.isEmpty(errorLogList))
			return null;
		StringBuffer errorBf = new StringBuffer();
		for (int i = 0; i < errorLogList.size(); i++) {
			if (i > 0) {
				errorBf.append("\n");
			}
			errorBf.append(errorLogList.get(i));
		}
		return errorBf.toString();
	}
}
