package com.ceragem.batch.crm.model;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @ClassName CrmMshipPlcyBasVo
 * @author user
 * @date 2022. 5. 20.
 * @Version 1.0
 * @description 맴버십기본정책 Vo
 * @Company Copyright ⓒ wigo.ai. All Right Reserved
 */
@Getter
@Setter
public class CrmMshipPlcyBasVo extends BaseVo {
	/**
	*
	*/
	public static final long serialVersionUID = 1L;
	/**
	 * 멤버십정책기본번호
	 */
	private String mshipPlcyBasNo;
	/**
	 * 멤버십등급코드
	 */
	private String mshipGradeCd;
	/**
	 * 회원등급코드
	 */
	private String mshpGradeCd;
	/**
	 * 멤버십정책명
	 */
	private String mshipPlcyNm;
	/**
	 * 멤버십정책내용
	 */
	private String mshipPlcyCtnts;
	/**
	 * 제휴회사번호
	 */
	private String cprtCmpNo;
	/**
	 * 적용기준시작년월일
	 */
	private String applyStdStaYmd;
	/**
	 * 적용기준종료년월일
	 */
	private String applyStdEndYmd;
	/**
	 * 산정기준월
	 */
	private String extrcStdMonth;
	/**
	 * 승급시작점수
	 */
	private String advncmtStaScore;
	/**
	 * 승급종료점수
	 */
	private String advncmtEndScore;
	/**
	 * 산정보존월
	 */
	private String extrcKeepMonth;
	/**
	 * 산정기준2월
	 */
	private String extrcStd2Month;
	/**
	 * 등급보존점수
	 */
	private String gradeKeepScore;
	/**
	 * 등급보존2점수
	 */
	private String gradeKeep2Score;
	/**
	 * 등급보존월
	 */
	private String gradeKeepMonth;
	/**
	 * 구매보상승급율
	 */
	private Double buyRewardAdvncmtRate;
	/**
	 * 구매추천보상승급율
	 */
	private Double buyRcmdRewardAdvncmtRate;
	/**
	 * 구매적립포인트율
	 */
	private Double buyAccumPointRate;
	/**
	 * 가입포인트점수
	 */
	private Integer sbscPointScore;
	/**
	 * 가입포인트적용기준코드
	 */
	private String sbscPointApplyStdCd;
	/**
	 * 적립한도포인트점수
	 */
	private Integer accumLmtPointScore;
	/**
	 * 구매추천포인트율
	 */
	private Double buyRcmdPointRate;
	/**
	 * 구매승급포인트점수
	 */
	private String buyAdvncmtPointScore;
	/**
	 * 승급포인트적용기준코드
	 */
	private String advncmtPointApplyStdCd;
	/**
	 * 사용한도포인트점수
	 */
	private Integer useLmtPointScore;
	/**
	 * 포인트소멸알림여부
	 */
	private String pointExtncAlertYn;
	/**
	 * 포인트소멸알림기간
	 */
	private String pointExtncAlertPerd;
	/**
	 * 선물포인트유효기간
	 */
	private Integer giftPointValidPerd;
	/**
	 * 자사몰무료배송여부
	 */
	private String mycomMallFreeDlvYn;
	/**
	 * 무료서비스연장개월수
	 */
	private Integer freeSvcExtnsnMonsCnt;
	/**
	 * 세라케어쿠폰제공개월수
	 */
	private Integer ceracCoupnPrvMonsCnt;
	/**
	 * 상태코드
	 */
	private String statusCd;
	/**
	 * 등록채널코드 공통코드 : S000 [CRM : CRM , CTC : 상담 , AS : AS , SAP : SAP , POS : POS]
	 */
	private String regChlCd;

	/**
	 * 통합회원번호
	 */
	private String itgCustNo;

	/**
	 * 추천인 통합회원번호
	 */
	private String rcmdrCustNo;

	private String buyGodsNo;
	private String storNo;

	private int mshipGodsChk; // 제외 상품 여부 MSHIP_GODS_CHK;
	private int mshipStoreChk; // 제외 매장 여부 MSHIP_STORE_CHK;
	private String chitNo; // 전표번호
	private String rcmdrMshipGradeCd; // 추천인 회원등급
	private String accumYn; // 적립여부
	private String pblsDivCd; // 이벤트 구분코드

	private int todayPblsPnt; // 오늘 적립된 포인트 금액

}
