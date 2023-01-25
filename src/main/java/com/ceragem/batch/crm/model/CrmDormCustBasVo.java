package com.ceragem.batch.crm.model;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @ClassName CrmDormCustBasVo
 * @author 김성태
 * @date 2022. 4. 18.
 * @Version 1.0
 * @description CRM휴면고객기본 Vo
 * @Company Copyright ⓒ wigo.ai. All Right Reserved
 */
@Getter
@Setter
public class CrmDormCustBasVo extends CrmCustBasVo {
	/**
	*
	*/
	private static final long serialVersionUID = 1L;
	/**
	 * 통합고객번호
	 */
	private String itgCustNo;
	/**
	 * 법인구분여부
	 */
	private String corpDivYn;
	/**
	 * 고객명
	 */
	private String custNm;
	/**
	 * 성별코드
	 */
	private String gndrCd;
	/**
	 * 생년월일
	 */
	private String birthday;
	/**
	 * 사업자등록번호
	 */
	private String bizNo;
	/**
	 * 법인등록번호
	 */
	private String corpRegNo;
	/**
	 * 법인전화번호
	 */
	private String corpTelNo;
	/**
	 * 외국인여부
	 */
	private String fornYn;
	/**
	 * 대표명
	 */
	private String repNm;
	/**
	 * 이동전화번호
	 */
	private String mphonNo;
//    /**
//    * 이동전화번호암호화값 
//    */
//    private String mphonNoEncVal;
	/**
	 * 이동전화뒤자리번호
	 */
	private String mphonBkDgtNo;
	/**
	 * 이메일주소
	 */
	private String emailAddr;
	/**
	 * 담당자명
	 */
	private String picNm;
	/**
	 * 담당자이동전화번호
	 */
	private String picMphonNo;
	/**
	 * 담당자이동전화뒤자리번호
	 */
	private String picMphonBkDgtNo;
	/**
	 * 담당자이메일주소
	 */
	private String picEmailAddr;
	/**
	 * 개인정보유효기간시작일시
	 */
	private String indiInfoValidPerdStaDt;
	/**
	 * 개인정보유효기간종료일시
	 */
	private String indiInfoValidPerdEndDt;
	/**
	 * 고객상태코드
	 */
	private String custStatusCd;
	/**
	 * 고객상태변경일시
	 */
	private String custStatusChngDt;
	/**
	 * 고객정보변경일시
	 */
	private String custInfoChngDt;
	/**
	 * 고객유형코드
	 */
	private String custTypeCd;
	/**
	 * 고객구분코드
	 */
	private String custDivCd;
	/**
	 * 우편번호
	 */
	private String zipCd;
	/**
	 * 주소1내용
	 */
	private String addr1Ctnts;
	/**
	 * 주소2내용
	 */
	private String addr2Ctnts;
	/**
	 * 추천매장번호
	 */
	private String rcmdStorNo;
	/**
	 * 추천인고객번호
	 */
	private String rcmdrCustNo;
	/**
	 * 추천임직원번호
	 */
	private String rcmdExecvdempNo;
	/**
	 * 결혼여부
	 */
	private String marryYn;
	/**
	 * 대표가구번호
	 */
	private String repHshldNo;
	/**
	 * 가족관계코드
	 */
	private String famlyRelCd;
	/**
	 * 주거평수코드
	 */
	private String dwelNfpyCd;
	/**
	 * 관심분야코드
	 */
	private String intrstFildCd;
	/**
	 * 대표제품번호
	 */
	private String repGodsNo;
	/**
	 * 대표사용고객구분코드
	 */
	private String repUseCustDivCd;
	/**
	 * SMS수신동의여부
	 */
	private String smsRcvAgreeYn;
	/**
	 * SMS수신동의일시
	 */
	private String smsRcvAgreeDt;
	/**
	 * SMS수신동의채널코드
	 */
	private String smsRcvAgreeChlCd;
	/**
	 * 이메일수신동의여부
	 */
	private String emailRcvAgreeYn;
	/**
	 * 이메일수신동의일시
	 */
	private String emailRcvAgreeDt;
	/**
	 * 이메일수신동의채널코드
	 */
	private String emailRcvAgreeChlCd;
	/**
	 * 알람톡수신동의여부
	 */
	private String alrmTkRcvAgreeYn;
	/**
	 * 알람톡수신동의일시
	 */
	private String alrmTkRcvAgreeDt;
	/**
	 * 알람톡수신동의채널코드
	 */
	private String alrmTkRcvAgreeChlCd;
	/**
	 * PUSH수신동의여부
	 */
	private String pushRcvAgreeYn;
	/**
	 * PUSH수신동의일시
	 */
	private String pushRcvAgreeDt;
	/**
	 * PUSH수신동의채널코드
	 */
	private String pushRcvAgreeChlCd;
	/**
	 * CI인증여부
	 */
	private String ciCertfYn;
	/**
	 * CI
	 */
	private String ci;
	/**
	 * DI인증여부
	 */
	private String diCertfYn;
	/**
	 * DI
	 */
	private String di;
	/**
	 * 수신거부여부
	 */
	private String rcvRfslYn;
	/**
	 * 멤버십가입여부
	 */
	private String mshipSbscYn;
	/**
	 * 멤버십로그인ID
	 */
	private String mshipLoginId;
	/**
	 * 멤버십대체번호
	 */
	private String mshipSbtNo;
	/**
	 * 멤버십로그인비밀번호
	 */
	private String mshipLoginPwd;
	/**
	 * 멤버십최종로그인일시
	 */
	private String mshipLastLoginDt;
	/**
	 * 멤버십최종로그인IP주소
	 */
	private String mshipLastLoginIpAddr;
	/**
	 * 멤버십최종매장방문일시
	 */
	private String mshipLastStorVisitDt;
	/**
	 * 멤버십최종방문매장번호
	 */
	private String mshipLastVisitStorNo;
	/**
	 * 멤버십유형코드
	 */
	private String mshipTypeCd;
	/**
	 * 멤버십가입일시
	 */
	private String mshipSbscDt;
	/**
	 * 멤버십등급코드
	 */
	private String mshipGradeCd;
	/**
	 * 멤버십등급변경일시
	 */
	private String mshipGradeChngDt;
	/**
	 * 멤버십정보변경일시
	 */
	private String mshipInfoChngDt;
	/**
	 * 멤버십정보확인자임직원번호
	 */
	private String mshipInfoConfrExecvdempNo;
	/**
	 * 제휴임직원번호
	 */
	private String cprtExecvdempNo;
	/**
	 * 멤버십탈퇴일시
	 */
	private String mshipLeaveDt;
	/**
	 * 소멸포인트한도점수
	 */
	private Integer extncPointLmtScore;
	/**
	 * 적립포인트한도점수
	 */
	private Integer accumPointLmtScore;
	/**
	 * 멤버십채널코드
	 */
	private String mshipChlCd;
	/**
	 * 주의고객등록매장번호
	 */
	private String bllkRegStorNo;
	/**
	 * 주의고객주의고객회원여부
	 */
	private String bllkmshpYn;
	/**
	 * 주의고객등록채널코드
	 */
	private String bllkRegChlCd;
	/**
	 * 주의고객등록사유내용
	 */
	private String bllkRegWhyCtnts;
	/**
	 * 주의고객등록일시
	 */
	private String bllkRegDt;
	/**
	 * 등록채널코드 공통코드 : S000 [CRM : CRM , CTC : 상담 , AS : AS , SAP : SAP]
	 */
	private String regChlCd;
}
