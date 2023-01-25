package com.ceragem.batch.crm.bos.model;

import com.ceragem.batch.crm.model.BaseVo;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @ClassName CrmBosRtnTxnVo
 * @author 김성태
 * @date 2022. 6. 30.
 * @Version 1.0
 * @description CRMBOS반환내역 Vo
 * @Company Copyright ⓒ wigo.ai. All Right Reserved
 */
@Getter
@Setter
public class BosCrmRtnTxnVo extends BaseVo {
	/**
	*
	*/
	private static final long serialVersionUID = 1L;
	/**
	 * 계약번호
	 */
	private String cntrno;
	/**
	 * 반환순번
	 */
	private Integer rtnsn;
	/**
	 * 고객번호
	 */
	private String custno;
	/**
	 * 주문번호
	 */
	private String ordno;
	/**
	 * 반환유형코드
	 */
	private String rtntycd;
	/**
	 * 반환유형
	 */
	private String rtntycdnm;
	/**
	 * 반환상태코드
	 */
	private String rtnstscd;
	/**
	 * 반환상태
	 */
	private String rtnstscdnm;
	/**
	 * 반환상태상세코드
	 */
	private String rtnstsdtlcd;
	/**
	 * 반환상태상세
	 */
	private String rtnstsdtlcdnm;
	/**
	 * 반환사유코드
	 */
	private String rtnrsncd;
	/**
	 * 반환사유
	 */
	private String rtnrsncdnm;
	/**
	 * 비고
	 */
	private String rm;
	/**
	 * 반환결제상태코드
	 */
	private String rtnsetlstscd;
	/**
	 * 반환결제상태
	 */
	private String rtnsetlstscdnm;
	/**
	 * 반환컨택순번
	 */
	private Integer rtncntcsn;
	/**
	 * 반환컨택유형코드
	 */
	private String rtncntctycd;
	/**
	 * 반환컨택유형
	 */
	private String rtncntctycdnm;
	/**
	 * 반환컨택상태코드
	 */
	private String rtncntcstscd;
	/**
	 * 반환컨택상태
	 */
	private String rtncntcstscdnm;
	/**
	 * 품목코드
	 */
	private String itemcd;
	/**
	 * 품목 코드(SAP코드)
	 */
	private String baseitemcd;
	/**
	 * 품목그룹코드
	 */
	private String itemgrpcd;
	/**
	 * 품목그룹
	 */
	private String itemgrpcdnm;
	/**
	 * 판매유형코드
	 */
	private String saletycd;
	/**
	 * 판매유형
	 */
	private String saletycdnm;
	/**
	 * 판매구분코드
	 */
	private String salesecd;
	/**
	 * 판매구분
	 */
	private String salesecdnm;
	/**
	 * 약정기간코드
	 */
	private String agrpdcd;
	/**
	 * 약정기간
	 */
	private String agrpdcdnm;
	/**
	 * 계약기간값
	 */
	private Integer cntrpdval;
	/**
	 * 사용일수
	 */
	private Integer usedayco;
	/**
	 * 사용개월수
	 */
	private Integer usemnco;
	/**
	 * 잔여일수
	 */
	private Integer remndayco;
	/**
	 * 잔여개월수
	 */
	private Integer remnmnco;
	/**
	 * 반환요청일자
	 */
	private String rtnreqde;
	/**
	 * 반환접수일자
	 */
	private String rtnacptde;
	/**
	 * 반환접수확정일자
	 */
	private String rtnacptdcsde;
	/**
	 * 반환완료일자
	 */
	private String rtnendde;
	/**
	 * 반환취소일자
	 */
	private String rtncnclde;
	/**
	 * 반환취소사유코드
	 */
	private String rtncnclrsncd;
	/**
	 * 반환취소사유
	 */
	private String rtncnclrsncdnm;
	/**
	 * 설치회수여부
	 */
	private String istrvlyn;
	/**
	 * 가격정책번호
	 */
	private String prcplcno;
	/**
	 * 위약금정책번호
	 */
	private String penltyplcno;
	/**
	 * 원월렌탈료
	 */
	private Double basemtrentfee;
	/**
	 * 원선납금액
	 */
	private Double baseprepayamt;
	/**
	 * 원판매금액
	 */
	private Double basesaleamt;
	/**
	 * 원등록비
	 */
	private Double baseregfee;
	/**
	 * 원설치비
	 */
	private Double baseistct;
	/**
	 * 원해체비
	 */
	private Double basedfee;
	/**
	 * 반환총수납금액
	 */
	private Double rtntotrcivamt;
	/**
	 * 반환월렌탈료
	 */
	private Double rtnmtrentfee;
	/**
	 * 반환선납금액
	 */
	private Double rtnprepayamt;
	/**
	 * 반환판매금액
	 */
	private Double rtnsaleamt;
	/**
	 * 반환등록비
	 */
	private Double rtnregfee;
	/**
	 * 반환설치비
	 */
	private Double rtnistct;
	/**
	 * 반환해체비
	 */
	private Double rtndfee;
	/**
	 * 위약금액
	 */
	private Double bapamt;
	/**
	 * 조정위약금액
	 */
	private Double mdfbapamt;
	/**
	 * 최종위약금액
	 */
	private Double lastbapamt;
	/**
	 * 위약금율
	 */
	private Double penltyrt;
	/**
	 * 패키지위약금액
	 */
	private Double pkgbapamt;
	/**
	 * 분실여부
	 */
	private String lstyn;
	/**
	 * 분실금액
	 */
	private Double lstamt;
	/**
	 * 조정분실금액
	 */
	private Double mdflstamt;
	/**
	 * 최종분실금액
	 */
	private Double lastlstamt;
	/**
	 * 우편번호
	 */
	private String postno;
	/**
	 * 기본주소
	 */
	private String bassaddr;
	/**
	 * 상세주소
	 */
	private String dtladdr;
	/**
	 * 시리얼번호
	 */
	private String serialno;
	/**
	 * 본부구분코드
	 */
	private String hqsecd;
	/**
	 * 판매조직
	 */
	private String saleorgz;
	/**
	 * 판매인
	 */
	private String seller;
	/**
	 * 반환예정조직
	 */
	private String rtndueorgz;
	/**
	 * 반환예정기사
	 */
	private String rtndueengr;
	/**
	 * 반환조직
	 */
	private String rtnorgz;
	/**
	 * 반환창고코드
	 */
	private String rtnwrhcd;
	/**
	 * 반환기사
	 */
	private String rtnengr;
	/**
	 * 반환예정일자
	 */
	private String rtnduede;
	/**
	 * 반환일자
	 */
	private String rtnde;
	/**
	 * 선납렌탈료매출번호
	 */
	private String prepayrentfeeselngno;
	/**
	 * 위약금매출번호
	 */
	private String penltyselngno;
	/**
	 * 분실비용매출번호
	 */
	private String lstctselngno;
	/**
	 * 해체비매출번호
	 */
	private String dfeeselngno;
	/**
	 * 렌탈료매출번호
	 */
	private String rentfeeselngno;
	/**
	 * 패키지해지여부
	 */
	private String pkgendyn;
	/**
	 * 계약수량
	 */
	private Integer cntrqy;
	/**
	 * 반환수량
	 */
	private Integer rtnqy;
	/**
	 * 등록자Id
	 */
	private String regusrid;
	/**
	 * 등록일시
	 */
	private String regdt;
	/**
	 * 수정자Id
	 */
	private String updusrid;
	/**
	 * 수정일시
	 */
	private String upddt;
}
