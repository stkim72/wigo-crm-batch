package com.ceragem.batch.crm.model;

import lombok.Getter;
import lombok.Setter;


/**
 * 
 * @ClassName    MsgrIfVo
 * @author    김은성
 * @date    2022. 8. 3.
 * @Version    1.0
 * @description    undefined Vo
 * @Company    Copyright ⓒ wigo.ai. All Right Reserved
 */
@Getter
@Setter
public class MsgrIfVo extends BaseVo {
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
    * SEND_TYPE 
    */
    private String sendType;
    /**
    * SUBJECT 
    */
    private String subject;
    /**
    * MSG 
    */
    private String msg;
    /**
    * DESTEL 
    */
    private String destel;
    /**
    * SRCTEL 
    */
    private String srctel;
    /**
    * RESERVETIME 
    */
    private String reservetime;
    /**
    * SENDTIME 
    */
    private String sendtime;
    /**
    * REVTIME 
    */
    private String revtime;
    /**
    * SENDRESULT 
    */
    private String sendresult;
    /**
    * TRANSRESULT 
    */
    private String transresult;
    /**
    * FILE_CNT 
    */
    private Integer fileCnt = 0;
    /**
    * FILE_CNT_REAL 
    */
    private Integer fileCntReal;
    /**
    * FILE_TYPE1 
    */
    private String fileType1;
    /**
    * FILE_PATH1 
    */
    private String filePath1;
    /**
    * FILE_TYPE2 
    */
    private String fileType2;
    /**
    * FILE_PATH2 
    */
    private String filePath2;
    /**
    * FILE_TYPE3 
    */
    private String fileType3;
    /**
    * FILE_PATH3 
    */
    private String filePath3;
    /**
    * INTERFACE_DATE 
    */
    private String interfaceDate;
    /**
    * INTERFACE_FL 
    */
    private String interfaceFl;
    /**
    * SMS_TEST 
    */
    private String smsTest;
    /**
    * ETC3 
    */
    private String etc3;
    /**
    * DUMMY1 
    */
    private String dummy1;
    /**
    * DUMMY2 
    */
    private String dummy2;
    /**
    * DUMMY3 
    */
    private String dummy3;
    /**
    * DUMMY4 
    */
    private String dummy4;
    /**
    * DUMMY5 
    */
    private String dummy5;
    /**
    * SENDER_KEY 
    */
    private String senderKey;
    /**
    * TEMPLATE_CODE 
    */
    private String templateCode;
    /**
    * K_MSG 
    */
    private String kMsg;
    /**
    * K_RESERVETIME 
    */
    private String kReservetime;
    /**
    * K_SENDTIME 
    */
    private String kSendtime;
    /**
    * K_RSLTDATE 
    */
    private String kRsltdate;
    /**
    * K_TRANSRESULT 
    */
    private String kTransresult;
    /**
    * K_ERR_ISSEND 
    */
    private String kErrIssend;
    /**
    * MSG_TYPE 
    */
    private String msgType;
    /**
    * KKO_HEADER 
    */
    private String kkoHeader;
    /**
    * KKO_BTN_TYPE 
    */
    private String kkoBtnType;
    /**
    * KKO_BTN_INFO 
    */
    private String kkoBtnInfo;
    /**
    * IMG_URL 
    */
    private String imgUrl;
    /**
    * IMG_LINK 
    */
    private String imgLink;
    /**
    * K_WIDE 
    */
    private String kWide;
    /**
    * K_TITLE 
    */
    private String kTitle;
    /**
    * AD_FLAG 
    */
    private String adFlag;
    /**
    * REV_ID 
    */
    private String revId;
    /**
    * SRVC_KEY_VAL 
    */
    private String srvcKeyVal;
    /**
    * ERR_MESSAGE 
    */
    private String errMessage;
    /**
    * MSG_JDT 
    */
    private String msgJdt;
    /**
    * BARCODE_TYPE 
    */
    private String barcodeType;
    /**
    * BARCODE_WIDTH 
    */
    private Integer barcodeWidth;
    /**
    * BARCODE_HEIGHT 
    */
    private Integer barcodeHeight;
    /**
    * BARCODE_POS_X 
    */
    private Integer barcodePosX;
    /**
    * BARCODE_POS_Y 
    */
    private Integer barcodePosY;
    /**
    * BARCODE_VALUE 
    */
    private String barcodeValue;
    /**
    * DUMMY6 
    */
    private String dummy6;
    /**
    * DUMMY7 
    */
    private String dummy7;
    /**
    * DUMMY8 
    */
    private String dummy8;
    /**
    * DUMMY9 
    */
    private String dummy9;
    /**
    * DUMMY10 
    */
    private String dummy10;
}
