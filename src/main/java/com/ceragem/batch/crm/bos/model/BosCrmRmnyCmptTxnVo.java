package com.ceragem.batch.crm.bos.model;

import com.ceragem.batch.crm.model.BaseVo;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @ClassName CrmBosRmnyCmptTxnVo
 * @author 김성태
 * @date 2022. 6. 30.
 * @Version 1.0
 * @description CRMBOS수납완료내역 Vo
 * @Company Copyright ⓒ wigo.ai. All Right Reserved
 */
@Getter
@Setter
public class BosCrmRmnyCmptTxnVo extends BaseVo {
	/**
	*
	*/
	private static final long serialVersionUID = 1L;
//
//	private String complexYn;
//	private String crmCustNo;
//	private Integer tidSn;
//	private String cnclYn;
//	private String partCnclYn;
//	private String dmdTyCdNm;
//	private String rcivno;
//	private String dmdTyCd;
//
//	public String getSelngNo() {
//		return this.sselngno;
//	}
//
//	public void setSelngNo(String selngNo) {
//		this.sselngno = selngNo;
//	}
//	public String getSelngno() {
//		return this.sselngno;
//	}
//
//	public void setSelngno(String selngNo) {
//		this.sselngno = selngNo;
//	}
//	public String getRcivEndYn() {
//		return srcivendyn;
//	}
//
//	public void setRcivEndYn(String rcivEndYn) {
//		this.srcivendyn = rcivEndYn;
//	}
//
//	public String getOrdNo() {
//		return this.ordno;
//	}
//
//	public void setOrdNo(String ordNo) {
//		this.ordno = ordNo;
//	}
//
//	public Integer getDmdTme() {
//		return this.dmdtme;
//	}
//
//	public void setDmdTme(Integer dmdTme) {
//		this.dmdtme = dmdTme;
//	}
//
//	public String getDpsSeCd() {
//		return this.dpssecd;
//	}
//
//	public void setDpsSeCd(String dpsSeCd) {
//		this.dpssecd = dpsSeCd;
//	}
//
//	public String getDpsSeCdNm() {
//		return this.dpssenm;
//	}
//
//	public void setDpsSeCdNm(String dpsSeCdNm) {
//		this.dpssenm = dpsSeCdNm;
//	}
//
//	public String getDpsTyCd() {
//		return this.dpstycd;
//	}
//
//	public void setDpsTyCd(String dpsTyCd) {
//		this.dpstycd = dpsTyCd;
//	}
//
//	public String getRcivDe() {
//		return this.rcivde;
//	}
//
//	public void setRcivDe(String rcivDe) {
//		this.rcivde = rcivDe;
//	}
//
//	public String getAcctNo() {
//		return this.acctno;
//	}
//
//	public void setAcctNo(String acctNo) {
//		this.acctno = acctNo;
//	}
//
//	public String getCustNo() {
//		return this.custno;
//	}
//
//	public void setCustNo(String custNo) {
//		this.custno = custNo;
//	}
//
//	public String getRcivChrgr() {
//		return this.rcivchrgrno;
//	}
//
//	public void setRcivChrgr(String rcivChrgr) {
//		this.rcivchrgrno = rcivChrgr;
//	}
//
//	public String getRcivChrgrNm() {
//		return this.rcivchrgrnm;
//	}
//
//	public void setRcivChrgrNm(String rcivChrgrNm) {
//		this.rcivchrgrnm = rcivChrgrNm;
//	}
//
//	public String getCustNm() {
//		return this.custnm;
//	}
//
//	public void setCustNm(String custNm) {
//		this.custnm = custNm;
//	}
//
//	public String getRcivMthCd() {
//		return this.rcivmthcd;
//	}
//
//	public void setRcivMthCd(String rcivMthCd) {
//		this.rcivmthcd = rcivMthCd;
//	}
//
//	public String getRcivMthCdNm() {
//		return this.rcivmthnm;
//	}
//
//	public void setRcivMthCdNm(String rcivMthCdNm) {
//		this.rcivmthnm = rcivMthCdNm;
//	}
//
//	public String getDpsTyCdNm() {
//		return this.dpstynm;
//	}
//
//	public void setDpsTyCdNm(String dpsTyCdNm) {
//		this.dpstynm = dpsTyCdNm;
//	}
//
//	public String getRemitDe() {
//		return this.remitde;
//	}
//
//	public void setRemitDe(String remitDe) {
//		this.remitde = remitDe;
//	}
//
//	public Integer getDmdTmeSn() {
//		return this.dmdtmesn;
//	}
//
//	public void setDmdTmeSn(Integer dmdTmeSn) {
//		this.dmdtmesn = dmdTmeSn;
//	}
//
//	public String getBankCd() {
//		return this.bankcd;
//	}
//
//	public void setBankCd(String bankCd) {
//		this.bankcd = bankCd;
//	}
//
//	public String getAcctFulltxtNo() {
//		return this.acctfulltxtno;
//	}
//
//	public void setAcctFulltxtNo(String acctFulltxtNo) {
//		this.acctfulltxtno = acctFulltxtNo;
//	}
//
//	public String getCntrNo() {
//		return this.cntrno;
//	}
//
//	public void setCntrNo(String cntrNo) {
//		this.cntrno = cntrNo;
//	}
//
//	public Double getRcivAmt() {
//		return this.rcivamt;
//	}
//
//	public void setRcivAmt(Double rcivAmt) {
//		this.rcivamt = rcivAmt;
//	}

	/**
	 * 복합결제 여부
	 */
	private String complexyn;
	/**
	 * 매출 번호
	 */
	private String selngno;
	/**
	 * 청구 회차
	 */
	private Integer dmdtme;
	/**
	 * 청구 회차 순번
	 */
	private Integer dmdtmesn;
	/**
	 * 청구 유형 코드(FI012)
	 */
	private String dmdtycd;
	/**
	 * 청구 유형 코드명
	 */
	private String dmdtycdnm;
	/**
	 * 주문 번호
	 */
	private String ordno;
	/**
	 * 계약 번호
	 */
	private String cntrno;
	/**
	 * 고객 번호
	 */
	private String custno;
	/**
	 * 고객명
	 */
	private String custnm;
	/**
	 * 통합고객번호
	 */
	private String crmcustno;
	/**
	 * 수납완료여부
	 */
	private String rcivendyn;
	/**
	 * 수납 번호
	 */
	private String rcivno;
	/**
	 * 수납 방법 코드(FI001)
	 */
	private String rcivmthcd;
	/**
	 * 수납 방법 코드명
	 */
	private String rcivmthcdnm;
	/**
	 * 입금 구분 코드(FI002)
	 */
	private String dpssecd;
	/**
	 * 입금 구분 코드명
	 */
	private String dpssecdnm;
	/**
	 * 입금 유형 코드(FI003)
	 */
	private String dpstycd;
	/**
	 * 입금 유형 코드명
	 */
	private String dpstycdnm;
	/**
	 * 수납 일자
	 */
	private String rcivde;
	/**
	 * 수납 금액
	 */
	private Double rcivamt;
	/**
	 * 수납 담당자
	 */
	private String rcivchrgr;
	/**
	 * 수납 담당자명
	 */
	private String rcivchrgrnm;
	/**
	 * 취소 여부
	 */
	private String cnclyn;
	/**
	 * 취소 수납 번호
	 */
	private String cnclrcivno;
	/**
	 * 부분 취소 여부
	 */
	private String partcnclyn;
	/**
	 * 영수증 번호
	 */
	private String rciptno;
	/**
	 * (현금)은행 코드(FI004)
	 */
	private String bankcd;
	/**
	 * (현금)계좌 번호
	 */
	private String acctno;
	/**
	 * (현금)계좌 전문 번호
	 */
	private String acctfulltxtno;
	/**
	 * (현금)송금 일자
	 */
	private String remitde;
	/**
	 * (가상계좌)가상계좌번호
	 */
	private String vacctno;
	/**
	 * (가상계좌)거래일시
	 */
	private String dealdt;
	/**
	 * (가상계좌)거래은행코드(FI054)
	 */
	private String dealbankcd;
	/**
	 * (가상계좌)거래은행코드명
	 */
	private String dealbankcdnm;
	/**
	 * (가상계좌)가상계좌거래구분코드(FI057)
	 */
	private String vacctdealtycd;
	/**
	 * (가상계좌)가상계좌거래구분코드명
	 */
	private String vacctdealtycdnm;
	/**
	 * (가상계좌)거래ID 순번
	 */
	private Integer tidsn;
	/**
	 * (카드)카드 승인 번호
	 */
	private String cardauthno;
	/**
	 * (카드)카드 승인 거래 번호
	 */
	private String cardauthdealno;
	/**
	 * (카드)승인 거래 번호 순번
	 */
	private Integer authdealnosn;
	/**
	 * (즉시이체)이체 거래 순번
	 */
	private String acctdealno;
	/**
	 * 카드대체내역 번호
	 */
	private String cardrepno;
	/**
	 * 등록자 ID
	 */
	private String regusrid;
	/**
	 * 등록 일시
	 */
	private String regdt;
	/**
	 * 수정자 ID
	 */
	private String updusrid;
	/**
	 * 수정 일시
	 */
	private String upddt;
}
