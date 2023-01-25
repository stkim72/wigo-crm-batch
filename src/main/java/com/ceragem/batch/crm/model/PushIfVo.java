package com.ceragem.batch.crm.model;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @ClassName    PushIfVo
 * @author    김성태
 * @date    2022. 6. 28.
 * @Version    1.0
 * @description    undefined Vo
 * @Company    Copyright ⓒ wigo.ai. All Right Reserved
 */
@Getter
@Setter
public class PushIfVo extends BaseVo {
    /**
    *
    */
private static final long serialVersionUID = 1L;
    /**
    * CLIENT 
    */
    private String client;
    /**
    * CAMPAIGN_ID 
    */
    private String campaignId;
    /**
    * MEMBER_SEQ 
    */
    private String memberSeq;
    /**
    * SEND_GUBUN 
    */
    private String sendGubun;
    /**
    * SUBJECT 
    */
    private String subject;
    /**
    * PUSH_APP_ID 
    */
    private String pushAppId;
    /**
    * PUSH_APP_OS 
    */
    private String pushAppOs;
    /**
    * PUSH_MSG 
    */
    private String pushMsg;
    /**
    * PUSH_TKN_INFO 
    */
    private String pushTknInfo;
    /**
    * PUSH_RESERVETIME 
    */
    private String pushReservetime;
    /**
    * PUSH_SEND_INTERVAL 
    */
    private Integer pushSendInterval = 180;
    /**
    * DESTEL 
    */
    private String destel;
    /**
    * SRCTEL 
    */
    private String srctel;
    /**
    * SENDRESULT 
    */
    private String sendresult;
    /**
    * PUSH_SENDTIME 
    */
    private String pushSendtime;
    /**
    * PUSH_TRANSRESULT 
    */
    private String pushTransresult;
    /**
    * PUSH_RESULTTIME 
    */
    private String pushResulttime;
    /**
    * SND_RETRY_CNT 
    */
    private Integer sndRetryCnt = 0;
    /**
    * PUSH_CP_ID 
    */
    private String pushCpId;
    /**
    * SNDR_ID 
    */
    private String sndrId;
    /**
    * SNDR_DEPT 
    */
    private String sndrDept;
    /**
    * MSG_JDT 
    */
    private String msgJdt;
    /**
    * SRVC_KEY_VAL 
    */
    private String srvcKeyVal;
    /**
    * ERR_MESSAGE 
    */
    private String errMessage;
    /**
    * MRKN_YN 
    */
    private String mrknYn;
    /**
    * REV_ID 
    */
    private String revId;
}
