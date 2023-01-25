package com.ceragem.batch.crm.model;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @ClassName    BatchExecHstVo
 * @author    김성태
 * @date    2022. 5. 26.
 * @Version    1.0
 * @description    배치실행이력 Vo
 * @Company    Copyright ⓒ wigo.ai. All Right Reserved
 */
@Getter
@Setter
public class BatchExecHstVo extends BaseVo {
    /**
    *
    */
private static final long serialVersionUID = 1L;
    /**
    * 배치이력코드 
    */
    private String batchHstCd;
    /**
    * 배치코드 
    */
    private String batchCd;
    /**
    * 시스템코드 
    */
    private String sysCd;
    /**
    * 작업ID 
    */
    private String wrkId;
    /**
    * 배치결과코드 
    */
    private String batchRsltCd;
    /**
    * 배치호출일시 
    */
    private String batchCallDt;
    /**
    * 배치실행밀리초 
    */
    private Integer batchExecMsec;
    /**
    * 오류메시지내역 
    */
    private String errMsgTxn;
}
