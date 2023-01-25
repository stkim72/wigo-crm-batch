package com.ceragem.batch.crm.bos.model;

import com.ceragem.batch.crm.model.BaseVo;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @ClassName CrmBosRmnyCancelTxnVo
 * @author 김성태
 * @date 2022. 6. 30.
 * @Version 1.0
 * @description CRMBOS수납취소내역 Vo
 * @Company Copyright ⓒ wigo.ai. All Right Reserved
 */
@Getter
@Setter
public class BosCrmRmnyCancelTxnVo extends BaseVo {
	/**
	*
	*/
	private static final long serialVersionUID = 1L;

	String complexyn;/* 복합결제 여부 */
	String selngno;/* 매출 번호 */
	Integer dmdtme;/* 청구 회차 */
	Integer dmdtmesn;/* 청구 회차 순번 */
	String dmdtycd;/* 청구 유형 코드(FI012) */
	String dmdtycdnm;/* 청구 유형 코드명 */
	String ordno;/* 주문 번호 */
	String cntrno;/* 계약 번호 */
	String custno;/* 고객 번호 */
	String custnm;/* 고객명 */
	String crmcustno;/* 통합고객번호 */
	String rcivendyn;/* 수납완료여부 */
	String rcivno;/* 수납 번호 */
	String rcivmthcd;/* 수납 방법 코드(FI001) */
	String rcivmthcdnm;/* 수납 방법 코드명 */
	String dpssecd;/* 입금 구분 코드(FI002) */
	String dpssecdnm;/* 입금 구분 코드명 */
	String dpstycd;/* 입금 유형 코드(FI003) */
	String dpstycdnm;/* 입금 유형 코드명 */
	String rcivde;/* 수납 일자 */
	Double rcivamt;/* 수납 금액 */
	String rcivchrgr;/* 수납 담당자 */
	String rcivchrgrnm;/* 수납 담당자명 */
	String cnclyn;/* 취소 여부 */
	String cnclrcivno;/* 취소 수납 번호 */
	String partcnclyn;/* 부분 취소 여부 */
	String rciptno;/* 영수증 번호 */
	String bankcd;/* (현금)은행 코드(FI004) */
	String acctno;/* (현금)계좌 번호 */
	String acctfulltxtno;/* (현금)계좌 전문 번호 */
	String remitde;/* (현금)송금 일자 */
	String vacctno;/* (가상계좌)가상계좌번호 */
	String dealdt;/* (가상계좌)거래일시 */
	String dealbankcd;/* (가상계좌)거래은행코드(FI054) */
	String dealbankcdnm;/* (가상계좌)거래은행코드명 */
	String vacctdealtycd;/* (가상계좌)가상계좌거래구분코드(FI057) */
	String vacctdealtycdnm;/* (가상계좌)가상계좌거래구분코드명 */
	Integer tidsn;/* (가상계좌)거래ID 순번 */
	String cardauthno;/* (카드)카드 승인 번호 */
	String cardauthdealno;/* (카드)카드 승인 거래 번호 */
	Long authdealnosn;/* (카드)승인 거래 번호 순번 */
	String acctdealno;/* (즉시이체)이체 거래 순번 */
	String cardrepno;/* 카드대체내역 번호 */
	String regusrid;/* 등록자 ID */
	String regdt;/* 등록 일시 */
	String updusrid;/* 수정자 ID */
	String upddt;/* 수정 일시 */

//	
//	
//	private String complexYn;
//
//	
//	
//	
//	
//	
//	
//	
//	
//	/**
//	 * 주문번호
//	 */
//	private String ordno;
//	/**
//	 * 계약번호
//	 */
//	private String cntrno;
//	/**
//	 * 고객번호
//	 */
//	private String custno;
//	/**
//	 * 고객명
//	 */
//	private String custnm;
//	/**
//	 * [매출] 매출번호
//	 */
//	private String sselngno;
//	/**
//	 * [매출] 수납 완료 여부
//	 */
//	private String srcivendyn;
//	/**
//	 * [매출] 수납일자
//	 */
//	private String srcivde;
//	/**
//	 * [매출] 수납금액
//	 */
//	private Double srcivamt;
//	/**
//	 * 청구 유형 코드(FI003)
//	 */
//	private String dpstycd;
//	/**
//	 * 청구 유형명
//	 */
//	private String dpstynm;
//	/**
//	 * 청구회차
//	 */
//	private Integer dmdtme;
//	/**
//	 * 청구 회차 순번
//	 */
//	private Integer dmdtmesn;
//	/**
//	 * 입금 구분 코드(FI002)
//	 */
//	private String dpssecd;
//	/**
//	 * 입금 구분명
//	 */
//	private String dpssenm;
//	/**
//	 * 수납일자
//	 */
//	private String rcivde;
//	/**
//	 * 수납금액
//	 */
//	private Double rcivamt;
//	/**
//	 * 수납 방법 코드(FI001)
//	 */
//	private String rcivmthcd;
//	/**
//	 * 수납 방법명
//	 */
//	private String rcivmthnm;
//	/**
//	 * 수납담당자명
//	 */
//	private String rcivchrgrno;
//	/**
//	 * 수납담당자
//	 */
//	private String rcivchrgrnm;
//	/**
//	 * 은행코드
//	 */
//	private String bankcd;
//	/**
//	 * 은행명
//	 */
//	private String banknm;
//	/**
//	 * (현금)계좌 번호 복호화
//	 */
//	private String acctorgno;
//	/**
//	 * (현금)계좌 번호 암호화
//	 */
//	private String acctno;
//	/**
//	 * (현금)계좌 전문 번호
//	 */
//	private String acctfulltxtno;
//	/**
//	 * (현금)송금 일자
//	 */
//	private String remitde;
//	/**
//	 * (수표)수표 번호
//	 */
//	private String chequeno;
//	/**
//	 * 선납 여부
//	 */
//	private String prepayyn;
//	/**
//	 * (카드)카드 승인 거래 번호
//	 */
//	private String cardauthdealno;
//	/**
//	 * (가상계좌)가상계좌번호
//	 */
//	private String vacctno;
//	/**
//	 * (가상계좌)거래은행코드(FI054)
//	 */
//	private String dealbankcd;
//	/**
//	 * (가상계좌)거래은행명
//	 */
//	private String dealbanknm;
//	/**
//	 * (가상계좌)가상계좌거래구분코드(FI057)
//	 */
//	private String vacctdealtycd;
//	/**
//	 * (가상계좌)가상계좌거래구분명
//	 */
//	private String vacctdealtynm;
//
//	public String getSelngno() {
//		return sselngno;
//	}
//
//	public void setSelngno(String selngno) {
//		this.sselngno = selngno;
//	}
//
//	public String getDpstycdnm() {
//		return dpstynm;
//	}
//
//	public void setDpstycdnm(String dpstycdnm) {
//		this.dpstynm = dpstycdnm;
//	}
//
//	public String getDpssecdnm() {
//		return dpssenm;
//	}
//
//	public void setDpssecdnm(String dpssecdnm) {
//		this.dpssenm = dpssecdnm;
//	}
//
//	public String getRcivmthcdnm() {
//		return rcivmthnm;
//	}
//
//	public void setRcivmthcdnm(String rcivmthcdnm) {
//		this.rcivmthnm = rcivmthcdnm;
//	}

}
