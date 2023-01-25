package com.ceragem.batch.crm.bos.model;

import com.ceragem.batch.crm.model.BaseVo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
/**
 * 
 * @ClassName BosContractVo
 * @author 김성태
 * @date 2022. 5. 24.
 * @Version 1.0
 * @description Bos 계약정보
 * @Company Copyright ⓒ wigo.ai. All Right Reserved
 */
public class BosContractVo extends BaseVo {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3874083484216136652L;

	/**
	 * 통합고객번호
	 */
	private String crmCustNo;

	private String ordDe; /* 주문 일자 */
	private String saleSeCd; /* 판매 구분 코드 */
	private String saleSeNm; /* 판매 구분 명 */
	private String saleTyCd; /* 판매 유형 코드 */
	private String saleTyNm; /* 판매 유형 명 */
	private String saleGrpCd; /* 판매 그룹 코드 */
	private String saleGrpNm; /* 판매 그룹 명 */
	private String rentSeCd; /* 렌탈 구분 코드 */
	private String rentSeNm; /* 렌탈 구분 명 */
	private String freeSaleTy; /* 무상판매 유형 */
	private String freeSaleTyNm; /* 무상판매 유형 명 */
	private String ordSeCd; /* 주문 구분 코드 */
	private String ordSeNm; /* 주문 구분 명 */
	private String ordChnCd; /* 주문 채널 코드 */
	private String ordChnNm; /* 주문 채널 명 */
	private String ordNo; /* 주문 번호 */
	private Integer ordNoSn; /* 주문 번호 순번 */
	private String cntrNo; /* 계약번호 */
	private String custNo; /* 고객번호 */
	private String custNm; /* 고객 명 */
	private String itemTyCd; /* 품목 유형 코드 */
	private String itemTyNm; /* 품목 유형 명 */
	private String itemCd; /* 품목 코드 */
	private String itemNm; /* 품목 명 */
	private Integer cntrQy; /* 계약 수량 */
	private String dcrt; /* 할인율 */
	private Double dcAmt; /* 할인금액 */
	private String prcPlcNo; /* 가격 정책 번호 */
	private String prcPlcNm; /* 가격 정책 명 */
	private String prsntDtls; /* 사은품 내역 */
	private String cntrStsCd; /* 계약상태코드 */
	private String cntrStsNm; /* 계약상태 */
	private String cntrStsDtlCd; /* 계약상태상세코드 */
	private String cntrStsDtlNm; /* 계약상태상세 */
	private String cntrStsDtlRsn; /* 계약상태상세사유 */
	private String setlStsCd; /* 결제상태코드 */
	private String setlStsNm; /* 결제상태명 */
	private String istStsCd; /* 설치상태코드 */
	private String istStsNm; /* 설치상태명 */
	private String otbnStsCd; /* 출고상태 */
	private String otbnStsNm; /* 출고상태명 */
	private String hqSeCd; /* 판매 본부 코드 */
	private String hqSeNm; /* 판매 본부 명 */
	private String upperOrgz; /* 상위 조직 코드 */
	private String upperOrgzNm; /* 상위 조직 명 */
	private String saleOrgz; /* 판매 조직 코드 */
	private String saleOrgzNm; /* 판매 조직 명 */
	private String seller; /* 판매인 코드 */
	private String sellerNm; /* 판매인 명 */
	private String sponsorId; /* 스폰서 ID */
	private String sponsorNm; /* 스폰서 명 */
	private String rcmdr; /* 추천인 코드 */
	private String rcmdrNm; /* 추천인 명 */
	private String rcmdrMbrshId; /* 추천고객 ID */
	private String rcmdrMbrshNm; /* 추천고객 명 */
	private String cnclYn; /* 계약취소가능여부 */
	private String cntrCnclYn; /* 계약 취소 여부 */
	private String cntrCnclDe; /* 계약 취소 일자 */
	private String cntrCnclRsn; /* 계약 취소 사유 */
	private String agrPdCd; /* 약정 기간 코드 */
	private String agrPdNm; /* 약정 기간 명 */
	private Integer cntrPdVal; /* 계약 기간 값 */
	private String cntrPdSeCd; /* 기간 구분 */
	private String exprnDmdDcPdCd; /* 홈체험 청구할인 개월 */
	private String prepayYn; /* 선납 여부 */
	private Double prepayAmt; /* 선납 금액 */
	private String prepayCycle; /* 선납 주기 */
	private Integer regfee; /* 등록비 */
	private Double bassRentAmt; /* 기본 렌탈 금액 */
	private Double rentAmt; /* 렌탈 금액 */
	private Double bassSaleAmt; /* 기본 판매 금액 */
	private Double saleAmt; /* 판매 금액 */
	private Double setlAmt; /* 결제 금액 */
	private Double bassMbrshAmt; /* 기본 멤버십 금액 */
	private Double mbrshAmt; /* 멤버십 금액 */
	private String pamtOcrDe; /* 결제 일자 */
	private String cntcDe; /* 컨택 일자 */
	private String istDueDe; /* 설치예정일시 */
	private String otbnDe; /* 출고일 */
	private String istCustNm; /* 설치처 고객 명 */
	private String zip; /* 설치처 우편번호 */
	private String bassAddr; /* 설치처 기본주소 */
	private String dtlAddr; /* 설치처 상세주소 */
	private String wrhCd; /* 창고 코드 */
	private String wrhNm; /* 창고 명 */
	private String otbnCustNo; /* 출고처 */
	private String istDueOrgz; /* 설치 예정 조직 코드 */
	private String istDueOrgzNm; /* 설치 예정 조직 명 */
	private String istDueEngr; /* 설치 예정 기사 코드 */
	private String istDueEngrNm; /* 설치 예정 기사 명 */
	private String dlvCmp; /* 택배사 */
	private String invoiceNo; /* 송장번호 */
	private String istDcsDe; /* 설치 확정 일자 */
	private String selngDcsDe; /* 매출 확정 일자 */
	private String procDe; /* 맞교환 설치일자 */
	private String nprocRsnCd; /* 설치 취소사유 코드 */
	private String nprocRsnNm; /* 설치 취소사유 명 */
	private String trfRsnCd; /* 이관사유 코드 */
	private String trfRsnNm; /* 이관사유 명 */
	private String rtgdTyCd; /* 반환 유형 코드 */
	private String rtgdTyNm; /* 반환 유형 명 */
	private String rtgdReqDe; /* 반환 요청 일자 */
	private String rtgdDcsYn; /* 반환 확정 여부 */
	private String rtgdEndDe; /* 반환 종료 일자 */
	private String pkgYn; /* 패키지여부 */
	private String pkgPlcNo; /* 패키지 정책 번호 */
	private String pkgNm; /* 패키지명 */
	private String pkgNo; /* 패키지 번호 */
	private String grpYn; /* 그룹 여부 */
	private String dsGrpSe; /* 그룹 코드 */
	private String dsGrpSeNm; /* 그룹 코드 명 */
	private String grpNo; /* 그룹 번호 */
	private String exprnCntrNo; /* 홈체험 계약 번호 */
	private String dlvTyCd; /* 배송유형코드 */
	private String dlvTyNm; /* 배송유형명 */
	private String dlvTyDtlCd; /* 배송 유형 상세 코드 */
	private String dlvTyDtlNm; /* 배송유형상세명 */
	private String shdeCreYn; /* 청구스케줄 생성 여부 */
	private String cntrEndAfterProcMthCd; /* 계약 종료 이후 처리 방법 코드 */
	private String cntrEndAfterProcMthNm; /* 계약 종료 이후 처리 방법 명 */
	private String fsetlMthCd; /* 정기결제 방법 코드 */
	private String fsetlMthNm; /* 정기결제 방법 명 */
	private String fsetlDeCd; /* 정기결제 일자 */
	private String signFileYn; /* SIGN 파일 여부 */
	private String ctrtcFileYn; /* 계약서 파일 여부 */
	private String acctFileYn; /* 출금동의 파일 여부 */

	private String baseItemCd;
	private String saleOrgzCustCd;
	private String upperOrgzCd;

	private String regUsrId; /* 등록자 ID */
	private String regUsrNm; /* 등록자 명 */
	private String regDt; /* 등록일시 */
	private String updUsrId; /* 수정자 ID */
	private String updUsrNm; /* 수정자 명 */
	private String updDt; /* 수정일시 */

	/**
	 * 사용쿠폰기본번호
	 */
	private String useCoupnBasNo;

	/**
	 * 사용포인트금액
	 */
	private Integer usePointAmt;

	/* 쿠폰할인금액 */
	private Double cpnDcAmt;

	/**
	 * 품목 제품/상품구분 코드
	 */
	private String itemPrCd;

	/**
	 * 품목 제품/상품구분 코드명
	 */
	private String itemPrCdNm;
	
	/*
	 * 캠페인
	 * */
	private String campBasNo;
	
	/*
	 * 프로모션
	 * */
	private String promNo;
	
	public String getItemPrNm() {
		return getItemPrCdNm();
	}
	
	public void setItemPrNm(String itemPrNm) {
		setItemPrCdNm(itemPrNm);
	}
	
	public String getItgCustNo() {
		return getCrmCustNo();
	}

	public void setItgCustNo(String itgCustNo) {
		setCrmCustNo(itgCustNo);
	}

	public Integer getUsePointScore() {
		return this.usePointAmt;
	}

	public void setUsePointScore(Integer usePointScore) {
		this.usePointAmt = usePointScore;
	}
}
