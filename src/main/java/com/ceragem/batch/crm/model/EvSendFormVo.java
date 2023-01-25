package com.ceragem.batch.crm.model;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @ClassName    EvSendFormVo
 * @author    김성태
 * @date    2022. 6. 28.
 * @Version    1.0
 * @description    undefined Vo
 * @Company    Copyright ⓒ wigo.ai. All Right Reserved
 */
@Getter
@Setter
public class EvSendFormVo extends BaseVo {
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
    * REGDATE 
    */
    private String regdate;
    /**
    * CONTENT 
    */
    private String content;
    /**
    * SUBJECT 
    */
    private String subject;
    /**
    * SEND_EMAIL 
    */
    private String sendEmail;
    /**
    * SEND_NAME 
    */
    private String sendName;
    /**
    * RETURN_EMAIL 
    */
    private String returnEmail;
    /**
    * SEND_FLAG 
    */
    private String sendFlag;
    /**
    * LIST_ENDFLAG 
    */
    private String listEndflag;
    /**
    * JDT 
    */
    private String jdt;
    /**
    * ATTACH_FILE1 
    */
    private String attachFile1;
    /**
    * ATTACH_FILE1_NM 
    */
    private String attachFile1Nm;
    /**
    * ATTACH_FILE2 
    */
    private String attachFile2;
    /**
    * ATTACH_FILE2_NM 
    */
    private String attachFile2Nm;
    /**
    * ATTACH_FILE3 
    */
    private String attachFile3;
    /**
    * ATTACH_FILE3_NM 
    */
    private String attachFile3Nm;
    /**
    * ATTACH_FILE4 
    */
    private String attachFile4;
    /**
    * ATTACH_FILE4_NM 
    */
    private String attachFile4Nm;
    /**
    * ATTACH_FILE5 
    */
    private String attachFile5;
    /**
    * ATTACH_FILE5_NM 
    */
    private String attachFile5Nm;
    /**
    * SERFLD1 
    */
    private String serfld1;
    /**
    * SERFLD2 
    */
    private String serfld2;
    /**
    * SERFLD3 
    */
    private String serfld3;
    /**
    * SERFLD4 
    */
    private String serfld4;
    /**
    * SERFLD5 
    */
    private String serfld5;
}
