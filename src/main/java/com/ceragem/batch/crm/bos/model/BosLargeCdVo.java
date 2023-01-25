package com.ceragem.batch.crm.bos.model;

import com.ceragem.batch.crm.model.BaseVo;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @ClassName BosLargeCdVo
 * @author 김성태
 * @date 2022. 5. 24.
 * @Version 1.0
 * @description 계약정보
 * @Company Copyright ⓒ wigo.ai. All Right Reserved
 */
@Getter
@Setter
public class BosLargeCdVo extends BaseVo {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7377111959152292625L;
	private String cntrNo; /* 계약번호 */
	private String custNo; /* 고객번호 */
	private String custNm; /* 고객명 */
//	private String                                               ; /*  계약자 통합고객번호*/
	private String custTyCd; /* 고객유형코드 */
	private String custTyCdnm; /* 고객유형명 */
	private String custTyDtlCd; /* 고객유형상세코드 */
	private String custTyDtlCdnm; /* 고객유형상세명 */
	private String ordNo; /* 주문번호 */
	private Integer ordNoSn; /* 주문번호순번 */
	private String ordDe; /* 주문일자 */
	private String ordStsCd; /* 주문상태코드 */
	private String ordStsCdnm; /* 주문상태명 */
	private String ordChnCd; /* 주문채널코드 */
	private String ordSeCd; /* 주문구분코드 */
	private String ordSeCdnm; /* 주문구분명 */
	private String ordCnclDe; /* 주문취소일자 */
	private String itemCd; /* 품목코드 */
//	private String                                               ; /*  품목 고객코드*/
	private String itemNm; /* 품목명 */
	private String itemGrpCd; /* 품목그룹코드 */
	private String itemGrpCdnm; /* 품목그룹명 */
	private String itemTyCd; /* 품목유형코드 */
	private String itemTyCdnm; /* 품목유형명 */
	private String itemTyDtlCd; /* 품목유형상세코드 */
	private String itemTyDtlCdnm; /* 품목유형상세명 */
	private String serialNo; /* 시리얼번호 */
	private String saleTyCd; /* 판매유형코드 */
	private String saleTyCdnm; /* 판매유형명 */
	private String saleSeCd; /* 판매구분코드 */
	private String saleSeCdnm; /* 판매구분명 */
	private String saleGrpCd; /* 판매그룹코드 */
	private String saleGrpCdnm; /* 판매그룹명 */
	private String cntrStsCd; /* 계약상태코드 */
	private String cntrStsCdnm; /* 계약상태명 */
	private String cntrStsDtlCd; /* 계약상태상세코드 */
	private String cntrStsDtlCdnm; /* 계약상태상세명 */
	private String cntrStsDtlRsn; /* 계약상태상세사유 */
	private String setlStsCd; /* 결제상태코드 */
	private String setlStsCdnm; /* 결제상태명 */
	private String istStsCd; /* 설치상태코드 */
	private String istStsCdnm; /* 설치상태명 */
	private String cntrCnclYn; /* 계약취소여부 */
	private String cntrCnclDe; /* 계약취소일자 */
	private String cntrCnclRsn; /* 계약취소사유 */
	private String hqSeCd; /* 본부구분코드 */
	private String seller; /* 판매인 */
	private String sellernm; /* 판매인명 */
	private String saleOrgz; /* 판매조직 */
//	private String                                               ; /*  판매조직 고객코드*/
	private String istDueOrgz; /* 설치예정조직 */
	private String istDueEngr; /* 설치예정기사 */
	private String istOrgz; /* 설치조직 */
	private String istEngr; /* 설치기사 */
	private String eqpNo; /* 설비번호 */
	private String wrhCd; /* 창고코드 */
	private String wrhNm; /* 창고명 */
	private Integer cntcSn; /* 컨택순번 */
	private String custCntcSts; /* 고객컨택상태 */
	private String custCntcDe; /* 고객컨택일자 */
	private String istReqDe; /* 설치요청일자 */
	private String istDe; /* 설치일자 */
	private String istDcsYn; /* 설치확정여부 */
	private String istDcsDe; /* 설치확정일자 */
	private String cntrStartDe; /* 계약시작일자 */
	private String cntrEndDueDe; /* 계약종료예정일자 */
	private String cntrEndYn; /* 계약종료여부 */
	private String cntrEndDe; /* 계약종료일자 */
	private String selngDcsYn; /* 매출확정여부 */
	private String selngDcsDe; /* 매출확정일자 */
	private String cdtSelYn; /* 신용조회여부 */
	private String prcPlcNo; /* 가격정책번호 */
	private String agrPdCd; /* 약정기간코드 */
	private String agrPdCdnm; /* 약정기간명 */
	private Integer cntrPdVal; /* 계약기간값 */
	private String svcPdCd; /* 서비스기간코드 */
	private String svcCycle; /* 점검주기 */
	private String prepayYn; /* 선납여부 */
	private Double prepayAmt; /* 선납금액 */
	private String prepayCycle; /* 선납주기 */
	private Integer prepayDcrt; /* 선납할인율 */
	private Double regfee; /* 등록비 */
	private Double istct; /* 설치비 */
	private Double dfee; /* 해체비 */
	private Double bassRentAmt; /* 기본렌탈금액 */
	private Double rentAmt; /* 렌탈금액 */
	private Double bassSaleAmt; /* 기본판매금액 */
	private Double saleAmt; /* 판매금액 */
	private Double setlAmt; /* 결제금액 */
	private Double ordAmt; /* 주문금액 */
	private Double ordVatExclAmt; /* 주문부가세제외금액 */
	private Double ordVatAmt; /* 주문부가세금액 */
	private Double selngAmt; /* 매출금액 */
	private Double selngVatExclAmt; /* 매출부가세제외금액 */
	private Double selngVatAmt; /* 매출부가세금액 */
	private Double selngNo; /* 매출번호 */
	private Double vatCd; /* 부가세코드 */
	private Double saleChnCd; /* 판매채널코드 */
	private Double saleChnCdnm; /* 판매채널명 */
	private String prmtYn; /* 프로모션여부 */
	private String prmtPlcNo; /* 프로모션정책번호 */
	private String prmtNo; /* 프로모션번호 */
	private String pkgYn; /* 패키지여부 */
	private String pkgPlcNo; /* 패키지정책번호 */
	private String pkgNo; /* 패키지번호 */
	private String grpYn; /* 단체여부 */
	private String grpCd; /* 단체코드 */
	private String grpNo; /* 단체번호 */
	private String ctrtcFileNo; /* 계약서파일번호 */
	private String signFileNo; /* 서명파일번호 */
	private String svcYn; /* 서비스여부 */
	private String existCntrNo; /* 기존계약번호 */
	private String rtgdTyCd; /* 반품유형코드 */
	private String rtgdReqDe; /* 반품요청일자 */
	private String rtgdDcsYn; /* 반품접수확정여부 */
	private String rtgdEndDe; /* 반품완료일자 */
	private String spltPlcNo; /* 약관정책번호 */
	private String istDueDe; /* 설치예정일자 */
	private String addPrufFileNo1; /* 추가증빙파일번호1 */
	private String addPrufFileNo2; /* 추가증빙파일번호2 */
	private String cdtGradCd; /* 신용등급코드 */
	private String regUsrId; /* 등록자ID */
	private String regDt; /* 등록일시 */
	private String updUsrId; /* 수정자ID */
	private String updDt; /* 수정일시 */
	private String postNo; /* 설치-우편번호 */
	private String bassAddr; /* 설치-기본주소 */
	private String dtlAddr; /* 설치-상세주소 */
	private String mobNo; /* 설치-모바일번호 */
	private String telNo; /* 설치-전화번호 */
	private String partclrMatter; /* 설치-특이사항 */
	private String istCustNm; /* 설치-설치고객명 */
//	private String                                               ; /*  설치자 통합고객번호*/
	private String cardNo; /* 정기결제-카드번호 */
	private String cdcmpCd; /* 정기결제-카드사코드 */
	private String ownerNm; /* 정기결제-소유주명 */
	private String fsetlMthCd; /* 정기결제-방법코드 */
	private String fsetlMthCdnm; /* 정기결제-방법명 */
	private String fsetlDeCd; /* 정기결제일자코드 */
	private String acctAfileNo; /* 계좌신청동의파일번호 */
	private String otbnStsCd; /* 출고상태코드 */
	private String otbnStsCdnm; /* 출고상태명 */
	private String rm; /* 비고 */
	private Double bassMbrshAmt; /* 기본멤버십금액 */
	private Double mbrshAmt; /* 멤버십 금액 */
	private String lmpsFreeEndDueDe; /* 일시불무상종료예정일 */
	private String pkgTrmnYn; /* 패키지해지여부 */
	private String dlvTyCd; /* 배송유형코드 */
	private String dlvTyCdnm; /* 배송유형명 */
	private String dlvTyDtlCd; /* 배송유형상세코드 */
	private String dlvTyDtlCdnm; /* 배송유형상세명 */
	private Integer agrPdVal; /* 약정기간값 */
	private String cntrPdSeCd; /* 계약기간구분코드 */
	private String cntrPdSeCdnm; /* 계약기간구분명 */
	private String cntrEndAfterProcMthCd; /* 계약이후처리방법코드 */
	private String cntrEndAfterProcMthCdnm; /* 계약종료이후처리방법 */
	private String uatcSeCd; /* 중고구분코드 */
	private String uatcSeCdnm; /* 중고구분명 */
	private String uatcGradCd; /* 중고등급코드 */
	private String uatcGradCdnm; /* 중고등급명 */
	private String shdeCreYn; /* 스케줄생성여부 */
	private String rentSeCd; /* 렌탈구분코드 */
	private String rentSeCdnm; /* 렌탈구분명 */
	private String exprnDmdDcPdCd; /* 홈체험청구할인기간코드 */
	private String exprnDmdDcPdCdnm; /* 홈체험청구할인기간명 */
	private String exprnCntrNo; /* 홈체험계약번호 */
	private String rcmdr; /* 추천인 */
	private String rcmdrnm; /* 추천인명 */
	private String orgzCd; /* 추천인조직코드 */
	private String orgzNm; /* 추천인조직명 */
	private String selfBsnSeCd; /* 본인영업구분코드 */
	private String selfBsnSeCdnm; /* 본인영업구분명 */
	private String prsntDtls; /* 사은품내역 */
	private String freeSaleTy; /* 무상판매유형 */
	private String freeSaleTynm; /* 무상판매유형명 */
	private String mngNo; /* 관리번호 */
	private String cntcStsCdnm; /* 컨택상태명 */
	private Double bassSaleFee; /* 기본판매요금 */
	private String prcPlcNm; /* 가격정책명 */
	private Double cntrQy; /* 계약수량 */
	private String dcrt; /* 할인율 */
	private String dcAmt; /* 할인금액 */
	private Double erCntrNo; /* 이지렌탈계약번호 */
	private String rprsntvCntrYn; /* 대표번호여부 */
	private String orTyCd; /* 주문유형코드 */
	private String orTyCdnm; /* 주문유형 */
	private String prsntYn; /* 사은품여부 */
	private String prsntCntrNo; /* 원계약번호(사은품) */
	private String ceramOrdNo; /* 건기식주문번호 */
	private String ptaxbilYn; /* 선세금계산서발급여부 */
	private String ptaxbilDe; /* 선세금계산서기준일자 */
	private Integer ptaxbilChrgrSn; /* 선세금계산서담당자 */
	private String chrgrEmail; /* 담당자 이메일 */
	private String chrgrCttpc; /* 담당자 연락처 */
	private String chrgrDeptNm; /* 담당자 부서 명 */
	private String rcmdrMbrshId; /* 추천고객멤버십아이디 */
	private String rcmdrMbrshNm; /* 추천고객멤버십이름 */
	private String cfileNo; /* 인수서파일번호 */
	private String insSerNo; /* 조견표번호 */
	private String bondCd; /* 채권분류코드 */
	private String bondCdnm; /* 채권분류명 */

}
