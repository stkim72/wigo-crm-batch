package com.ceragem.batch.crm.bos.model;

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
public class BosCrmContractVo extends BosContractVo {

	/**
		 * 
		 */
	private static final long serialVersionUID = -1837016344108053465L;

	/**
	 * 고객유형코드
	 */
	private String custTyCd;
	/**
	 * 고객유형명
	 */
	private String custTyCdnm;
	/**
	 * 고객유형상세코드
	 */
	private String custTyDtlCd;
	/**
	 * 고객유형상세명
	 */
	private String custTyDtlCdnm;
	/**
	 * 주문상태코드
	 */
	private String ordStsCd;
	/**
	 * 주문상태명
	 */
	private String ordStsCdnm;
	/**
	 * 주문구분명
	 */
	private String ordSeCdnm;
	/**
	 * 주문취소일자
	 */
	private String ordCnclDe;
	/**
	 * 품목그룹코드
	 */
	private String itemGrpCd;
	/**
	 * 품목그룹명
	 */
	private String itemGrpCdnm;
	/**
	 * 품목유형명
	 */
	private String itemTyCdnm;
	/**
	 * 품목유형상세코드
	 */
	private String itemTyDtlCd;
	/**
	 * 품목유형상세명
	 */
	private String itemTyDtlCdnm;
	/**
	 * 시리얼번호
	 */
	private String serialNo;
	/**
	 * 판매유형명
	 */
	private String saleTyCdnm;
	/**
	 * 판매구분명
	 */
	private String saleSeCdnm;
	/**
	 * 판매그룹명
	 */
	private String saleGrpCdnm;
	/**
	 * 계약상태명
	 */
	private String cntrStsCdnm;
	/**
	 * 계약상태상세명
	 */
	private String cntrStsDtlCdnm;
	/**
	 * 결제상태명
	 */
	private String setlStsCdnm;
	/**
	 * 설치상태명
	 */
	private String istStsCdnm;
	/**
	 * 판매인명
	 */
	private String sellernm;
	/**
	 * 설치조직
	 */
	private String istOrgz;
	/**
	 * 설치기사
	 */
	private String istEngr;
	/**
	 * 설비번호
	 */
	private String eqpNo;
	/**
	 * 컨택순번
	 */
	private Integer cntcSn;
	/**
	 * 고객컨택상태
	 */
	private String custCntcSts;
	/**
	 * 고객컨택일자
	 */
	private String custCntcDe;
	/**
	 * 설치요청일자
	 */
	private String istReqDe;
	/**
	 * 설치일자
	 */
	private String istDe;
	/**
	 * 설치확정여부
	 */
	private String istDcsYn;
	/**
	 * 계약시작일자
	 */
	private String cntrStartDe;
	/**
	 * 계약종료예정일자
	 */
	private String cntrEndDueDe;
	/**
	 * 계약종료여부
	 */
	private String cntrEndYn;
	/**
	 * 계약종료일자
	 */
	private String cntrEndDe;
	/**
	 * 매출확정여부
	 */
	private String selngDcsYn;
	/**
	 * 신용조회여부
	 */
	private String cdtSelYn;
	/**
	 * 약정기간명
	 */
	private String agrPdCdnm;
	/**
	 * 서비스기간코드
	 */
	private String svcPdCd;
	/**
	 * 점검주기
	 */
	private String svcCycle;
	/**
	 * 선납할인율
	 */
	private Integer prepayDcrt;
	/**
	 * 설치비
	 */
	private Double istct;
	/**
	 * 해체비
	 */
	private Double dfee;
	/**
	 * 판매금액
	 */
	private Double salesAmt;
	/**
	 * 주문금액
	 */
	private Double ordAmt;
	/**
	 * 주문부가세제외금액
	 */
	private Double ordVatExclAmt;
	/**
	 * 주문부가세금액
	 */
	private Double ordVatAmt;
	/**
	 * 매출금액
	 */
	private Double selngAmt;
	/**
	 * 매출부가세제외금액
	 */
	private Double selngVatExclAmt;
	/**
	 * 매출부가세금액
	 */
	private Double selngVatAmt;
	/**
	 * 매출번호
	 */
	private String selngNo;
	/**
	 * 부가세코드
	 */
	private String vatCd;
	/**
	 * 판매채널코드
	 */
	private String saleChnCd;
	/**
	 * 판매채널명
	 */
	private String saleChnCdnm;
	/**
	 * 프로모션여부
	 */
	private String prmtYn;
	/**
	 * 프로모션정책번호
	 */
	private String prmtPlcNo;
	/**
	 * 프로모션번호
	 */
	private String prmtNo;
	/**
	 * 단체코드
	 */
	private String grpCd;
	/**
	 * 계약서파일번호
	 */
	private String ctrtcFileNo;
	/**
	 * 서명파일번호
	 */
	private String signFileNo;
	/**
	 * 서비스여부
	 */
	private String svcYn;
	/**
	 * 기존계약번호
	 */
	private String existCntrNo;
	/**
	 * 약관정책번호
	 */
	private String spltPlcNo;
	/**
	 * 추가증빙파일번호1
	 */
	private String addPrufFileNo1;
	/**
	 * 추가증빙파일번호2
	 */
	private String addPrufFileNo2;
	/**
	 * 신용등급코드
	 */
	private String cdtGradCd;
	/**
	 * 설치-우편번호
	 */
	private String postNo;
	/**
	 * 설치-모바일번호
	 */
	private String mobNo;
	/**
	 * 설치-전화번호
	 */
	private String telNo;
	/**
	 * 설치-특이사항
	 */
	private String partclrMatter;
	/**
	 * 정기결제-카드번호
	 */
	private String cardNo;
	/**
	 * 정기결제-카드사코드
	 */
	private String cdcmpCd;
	/**
	 * 정기결제-소유주명
	 */
	private String ownerNm;
	/**
	 * 정기결제-방법명
	 */
	private String fsetlMthCdnm;
	/**
	 * 계좌신청동의파일번호
	 */
	private String acctAfileNo;
	/**
	 * 출고상태명
	 */
	private String otbnStsCdnm;
	/**
	 * 비고
	 */
	private String rm;
	/**
	 * 일시불무상종료예정일
	 */
	private String lmpsFreeEndDueDe;
	/**
	 * 패키지해지여부
	 */
	private String pkgTrmnYn;
	/**
	 * 배송유형명
	 */
	private String dlvTyCdnm;
	/**
	 * 배송유형상세명
	 */
	private String dlvTyDtlCdnm;
	/**
	 * 약정기간값
	 */
	private Integer agrPdVal;
	/**
	 * 계약기간구분명
	 */
	private String cntrPdSeCdnm;
	/**
	 * 계약종료이후처리방법
	 */
	private String cntrEndAfterProcMthCdnm;
	/**
	 * 중고구분코드
	 */
	private String uatcSeCd;
	/**
	 * 중고구분명
	 */
	private String uatcSeCdnm;
	/**
	 * 중고등급코드
	 */
	private String uatcGradCd;
	/**
	 * 중고등급명
	 */
	private String uatcGradCdnm;
	/**
	 * 렌탈구분명
	 */
	private String rentSeCdnm;
	/**
	 * 홈체험청구할인기간명
	 */
	private String exprnDmdDcPdCdnm;
	/**
	 * 추천인명
	 */
	private String rcmdrnm;
	/**
	 * 추천인조직코드
	 */
	private String orgzCd;
	/**
	 * 추천인조직명
	 */
	private String orgzNm;
	/**
	 * 본인영업구분코드
	 */
	private String selfBsnSeCd;
	/**
	 * 본인영업구분명
	 */
	private String selfBsnSeCdnm;
	/**
	 * 무상판매유형명
	 */
	private String freeSaleTynm;
	/**
	 * 관리번호
	 */
	private String mngNo;
	/**
	 * 컨택상태명
	 */
	private String cntcStsCdnm;
	/**
	 * 기본판매요금
	 */
	private Double bassSaleFee;
	/**
	 * 이지렌탈계약번호
	 */
	private String erCntrNo;
	/**
	 * 대표번호여부
	 */
	private String rprsntvCntrYn;
	/**
	 * 주문유형코드
	 */
	private String orTyCd;
	/**
	 * 주문유형
	 */
	private String orTyCdnm;
	/**
	 * 사은품여부
	 */
	private String prsntYn;
	/**
	 * 원계약번호(사은품)
	 */
	private String prsntCntrNo;
	/**
	 * 건기식주문번호
	 */
	private String ceramOrdNo;
	/**
	 * 선세금계산서발급여부
	 */
	private String ptaxbilYn;
	/**
	 * 선세금계산서기준일자
	 */
	private String ptaxbilDe;
	/**
	 * 선세금계산서담당자
	 */
	private String ptaxbilChrgrSn;
	/**
	 * 담당자이메일
	 */
	private String chrgrEmail;
	/**
	 * 담당자연락처
	 */
	private String chrgrCttpc;
	/**
	 * 담당자부서명
	 */
	private String chrgrDeptNm;
	/**
	 * 인수서파일번호
	 */
	private String cfileNo;
	/**
	 * 조견표번호
	 */
	private String insSerNo;
	/**
	 * 채권분류코드
	 */
	private String bondCd;
	/**
	 * 채권분류명
	 */
	private String bondCdnm;

	/*
	 * 멤버십가입여부
	 */
	private String mshipSbscYn;

	/*
	 * 멤버십등급
	 */
	private String mshipGradeCd;

	/**
	 * 회원상태
	 */
	private String custStatusCd;

	public String getCustTyCdNm() {
		return custTyCdnm;
	}

	public String getCustTyDtlCdNm() {
		return custTyDtlCdnm;
	}

	public String getOrdStsCdNm() {
		return ordStsCdnm;
	}

	public String getOrdSeCdNm() {
		return ordSeCdnm;
	}

	public String getItemGrpCdNm() {
		return itemGrpCdnm;
	}

	public String getItemTyCdNm() {
		return itemTyCdnm;
	}

	public String getItemTyDtlCdNm() {
		return itemTyDtlCdnm;
	}

	public String getSaleTyCdNm() {
		return saleTyCdnm;
	}

	public String getSaleSeCdNm() {
		return saleSeCdnm;
	}

	public String getSaleGrpCdNm() {
		return saleGrpCdnm;
	}

	public String getCntrStsCdNm() {
		return cntrStsCdnm;
	}

	public String getCntrStsDtlCdNm() {
		return cntrStsDtlCdnm;
	}

	public String getSetlStsCdNm() {
		return setlStsCdnm;
	}

	public String getIstStsCdNm() {
		return istStsCdnm;
	}

	public String getSellerNm() {
		return sellernm;
	}

	public String getAgrPdCdNm() {
		return agrPdCdnm;
	}

	public String getSaleChnCdNm() {
		return saleChnCdnm;
	}

	public String getFsetlMthCdNm() {
		return fsetlMthCdnm;
	}

	public String getOtbnStsCdNm() {
		return otbnStsCdnm;
	}

	public String getDlvTyCdNm() {
		return dlvTyCdnm;
	}

	public String getDlvTyDtlCdNm() {
		return dlvTyDtlCdnm;
	}

	public String getCntrPdSeCdNm() {
		return cntrPdSeCdnm;
	}

	public String getCntrEndAfterProcMthCdNm() {
		return cntrEndAfterProcMthCdnm;
	}

	public String getUatcSeCdNm() {
		return uatcSeCdnm;
	}

	public String getUatcGradCdNm() {
		return uatcGradCdnm;
	}

	public String getRentSeCdNm() {
		return rentSeCdnm;
	}

	public String getExprnDmdDcPdCdNm() {
		return exprnDmdDcPdCdnm;
	}

	public String getRcmdrNm() {
		return rcmdrnm;
	}

	public String getSelfBsnSeCdNm() {
		return selfBsnSeCdnm;
	}

	public String getFreeSaleTyNm() {
		return freeSaleTynm;
	}

	public String getCntcStsCdNm() {
		return cntcStsCdnm;
	}

	public String getOrTyCdNm() {
		return orTyCdnm;
	}

	public String getBondCdNm() {
		return bondCdnm;
	}

	public void setCustTyCdNm(String value) {
		this.custTyCdnm = value;
	}

	public void setCustTyDtlCdNm(String value) {
		this.custTyDtlCdnm = value;
	}

	public void setOrdStsCdNm(String value) {
		this.ordStsCdnm = value;
	}

	public void setOrdSeCdNm(String value) {
		this.ordSeCdnm = value;
	}

	public void setItemGrpCdNm(String value) {
		this.itemGrpCdnm = value;
	}

	public void setItemTyCdNm(String value) {
		this.itemTyCdnm = value;
	}

	public void setItemTyDtlCdNm(String value) {
		this.itemTyDtlCdnm = value;
	}

	public void setSaleTyCdNm(String value) {
		this.saleTyCdnm = value;
	}

	public void setSaleSeCdNm(String value) {
		this.saleSeCdnm = value;
	}

	public void setSaleGrpCdNm(String value) {
		this.saleGrpCdnm = value;
	}

	public void setCntrStsCdNm(String value) {
		this.cntrStsCdnm = value;
	}

	public void setCntrStsDtlCdNm(String value) {
		this.cntrStsDtlCdnm = value;
	}

	public void setSetlStsCdNm(String value) {
		this.setlStsCdnm = value;
	}

	public void setIstStsCdNm(String value) {
		this.istStsCdnm = value;
	}

	public void setSellerNm(String value) {
		this.sellernm = value;
	}

	public void setAgrPdCdNm(String value) {
		this.agrPdCdnm = value;
	}

	public void setSaleChnCdNm(String value) {
		this.saleChnCdnm = value;
	}

	public void setFsetlMthCdNm(String value) {
		this.fsetlMthCdnm = value;
	}

	public void setOtbnStsCdNm(String value) {
		this.otbnStsCdnm = value;
	}

	public void setDlvTyCdNm(String value) {
		this.dlvTyCdnm = value;
	}

	public void setDlvTyDtlCdNm(String value) {
		this.dlvTyDtlCdnm = value;
	}

	public void setCntrPdSeCdNm(String value) {
		this.cntrPdSeCdnm = value;
	}

	public void setCntrEndAfterProcMthCdNm(String value) {
		this.cntrEndAfterProcMthCdnm = value;
	}

	public void setUatcSeCdNm(String value) {
		this.uatcSeCdnm = value;
	}

	public void setUatcGradCdNm(String value) {
		this.uatcGradCdnm = value;
	}

	public void setRentSeCdNm(String value) {
		this.rentSeCdnm = value;
	}

	public void setExprnDmdDcPdCdNm(String value) {
		this.exprnDmdDcPdCdnm = value;
	}

	public void setRcmdrNm(String value) {
		this.rcmdrnm = value;
	}

	public void setSelfBsnSeCdNm(String value) {
		this.selfBsnSeCdnm = value;
	}

	public void setFreeSaleTyNm(String value) {
		this.freeSaleTynm = value;
	}

	public void setCntcStsCdNm(String value) {
		this.cntcStsCdnm = value;
	}

	public void setOrTyCdNm(String value) {
		this.orTyCdnm = value;
	}

	public void setBondCdNm(String value) {
		this.bondCdnm = value;
	}

	public String getRcmdItgCustNo() {
		return getRcmdrMbrshId();
	}

	public boolean isMembership() {
		return "Y".equals(mshipSbscYn) && !"001".equals(mshipGradeCd) && !"003".equals(custStatusCd);
	}

}
