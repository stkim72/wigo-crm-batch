package com.ceragem.batch.crm.bos.model;

import com.ceragem.batch.crm.model.BaseVo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
/**
 * 
 * @ClassName BosItemVo
 * @author 김성태
 * @date 2022. 5. 24.
 * @Version 1.0
 * @description 품목내역
 * @Company Copyright ⓒ wigo.ai. All Right Reserved
 */
public class BosItemVo extends BaseVo {
	/**
	 * 
	 */
	private static final long serialVersionUID = 470028627200962831L;
	private String itemMngOrgz; /* 품목관리조직 */
	private String itemMngOrgznm; /* 품목관리조직명 */
	private String itemCd; /* 품목코드 */
	private String itemCdNm; /* 품목명 */
	private String itemAbrvNm; /* 품목약어명 */
	private String itemGrpCd; /* 품목그룹코드 */
	private String itemGrpCdNm; /* 품목그룹코드명 */
	private String asitemGrpCdnm; /* 품목그룹코드명 */
	private String itemTyCd; /* 품목유형코드 */
	private String itemTyCdNm; /* 품목유형코드명 */
	private String itemTyDtlCd; /* 품목유형상세코드 */
	private String itemTyDtlCdNm; /* 품목유형상세코드명 */
	private String useYn; /* 사용여부 */
	private String baseItemCd; /* SAP 자재코드 */
	private String clrCd; /* 색상코드 */
	private String clrCdNm; /* 색상명 */
	private String eanCd; /* EAN코드 */
	private String imgUrl; /* IMGURL */
	private String serialMngYn; /* 시리얼관리여부 */
	private String ivtMngYn; /* 재고관리여부 */
	private String saleYn; /* 판매여부 */
	private String brwIvtYn; /* 차용재고여부 */
	private String dstrTltMngYn; /* 유통기한관리여부 */
	private Double lprc; /* 정가 */
	private String ivtMngUnit; /* 재고관리단위 */
	private String itemPchsPrc; /* 품목구매가격 */
	private String itemWhsalPrc; /* 품목도매가격 */
	private String itemSalePrc; /* 품목판매가격 */
	private String crncyUnit; /* 통화단위 */
	private String rm; /* 비고 */
	private String regUsrId; /* 등록자ID */
	private String regDt; /* 등록일시 */
	private String updUsrId; /* 수정자ID */
	private String updDt; /* 수정일시 */
	private String bpCd; /* 입고처 */
	private String bpNm; /* 입고처명 */
	private Integer asGrntyPdVal; /* 보증기간값 */
	private Integer svclife; /* 내용연수 */
	private String wrhCd; /* 창고코드 */
	private String wrhNm; /* 창고명 */
	private String ceraPrsntYn; /* 세라메이트사은품여부 */

	/**
	 * BOS제품번호
	 */
	public String getBosGodsNo() {
		return this.getItemCd();
	}

	/**
	 * BOS제품명
	 */
	public String getBosGodsNm() {
		return this.getItemTyCdNm();
	}

	/**
	 * BOS제품그룹코드
	 */
	public String getBosGodsGrpCd() {
		return this.getItemGrpCd();
	}

	/**
	 * BOS제품그룹명
	 */
	public String getBosGodsGrpNm() {
		return this.getItemGrpCdNm();
	}

	/**
	 * BOS제품유형코드
	 */
	public String getBosGodsTypeCd() {
		return this.getItemTyCd();
	}

	/**
	 * BOS제품유형명
	 */
	public String getBosGodsTypeNm() {
		return this.getItemTyCdNm();
	}

	/**
	 * BOS제품상세유형코드
	 */
	public String getBosGodsDtlTypeCd() {
		return this.getItemTyDtlCd();
	}

	/**
	 * BOS제품상세유형명
	 */
	public String getBosGodsDtlTypeNm() {
		return this.getItemTyDtlCdNm();
	}

	/**
	 * 제품번호
	 */
	public String getGodsNo() {
		return this.getBaseItemCd();
	}

	/**
	 * 색상명
	 */
	public String getColrNm() {
		return this.getClrCdNm();
	}

	/**
	 * 시리얼사용여부
	 */
	public String getSerialUseYn() {
		return this.getSerialMngYn();
	}

	/**
	 * 판매여부
	 */
	public String getSalesYn() {
		return this.getSaleYn();
	}

	/**
	 * 유통기한사용여부
	 */
	public String getExpryUseYn() {
		return getDstrTltMngYn();
	};

	/**
	 * 소비자금액
	 */
	public Double getCnsrAmt() {
		return getLprc();
	};

	/**
	 * 통화코드
	 */
	public String getCurrCd() {
		return getCrncyUnit();
	}

	/**
	 * AS보증기간
	 */
	public Integer getAsGrntPerd() {
		return getAsGrntyPdVal();
	}

	/**
	 * 내용연수수
	 */
	public Integer getDuryearCnt() {
		return getSvclife();
	}

	public String getItemMngOrgzNm() {
		return this.itemMngOrgznm;
	}

	public void setItemMngOrgzNm(String itemMngOrgznm) {
		this.itemMngOrgznm = itemMngOrgznm;
	}
}
