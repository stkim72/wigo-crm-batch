package com.ceragem.batch.crm.model;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @ClassName EvSendFormVo
 * @author 김성태
 * @date 2022. 6. 28.
 * @Version 1.0
 * @description undefined Vo
 * @Company Copyright ⓒ wigo.ai. All Right Reserved
 */
@Getter
@Setter
public class EvSendListVo extends BaseVo {
	/**
	*
	*/
	private static final long serialVersionUID = 1L;
	/**
	 * SEQ
	 */
	private Integer seq;
	/**
	 * MAIL_KIND
	 */
	private String mailKind;
	/**
	 * LIST_SEQ
	 */
	private Integer listSeq;
	/**
	 * EMAIL
	 */
	private String email;
	/**
	 * NAME
	 */
	private String name;
	/**
	 * MAPPING1
	 */
	private String mapping1;
	/**
	 * MAPPING2
	 */
	private String mapping2;
	/**
	 * MAPPING3
	 */
	private String mapping3;
	/**
	 * MAPPING4
	 */
	private String mapping4;
	/**
	 * MAPPING5
	 */
	private String mapping5;
	/**
	 * MAPPING6
	 */
	private String mapping6;
	/**
	 * MAPPING7
	 */
	private String mapping7;
	/**
	 * MAPPING8
	 */
	private String mapping8;
	/**
	 * MAPPING9
	 */
	private String mapping9;
	/**
	 * MAPPING10
	 */
	private String mapping10;
	/**
	 * MAPPING
	 */
	private String mapping;
	/**
	 * SEND_RSLT_CD
	 */
	private String sendRsltCd;
	/**
	 * SEND_DATE
	 */
	private String sendDate;
	/**
	 * CP_ID
	 */
	private String cpId;
	/**
	 * EX_COUNT
	 */
	private Integer exCount = 0;
	/**
	 * OPENED
	 */
	private String opened;
	/**
	 * TRACK_END
	 */
	private String trackEnd;
	/**
	 * OPEN_S
	 */
	private String openS;
	/**
	 * OPEN_DATE
	 */
	private String openDate;
	/**
	 * OPEN_SDATE
	 */
	private String openSdate;
	/**
	 * REV_ID
	 */
	private String revId;
}
