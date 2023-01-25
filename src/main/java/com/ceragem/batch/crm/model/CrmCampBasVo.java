package com.ceragem.batch.crm.model;


import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @ClassName    CrmCampBasVo
 * @author    user
 * @date    2022. 6. 7.
 * @Version    1.0
 * @description    CRM캠페인기본 Vo
 * @Company    Copyright ⓒ wigo.ai. All Right Reserved
 */
@Getter
@Setter
public class CrmCampBasVo extends BaseVo {
    /**
    *
    */
private static final long serialVersionUID = 1L;
    /**
    * 캠페인기본번호 
    */
    private String campBasNo;
    /**
    * 캠페인유형코드 
    */
    private String campTypeCd;
    private String campTypeCdNm;
    /**
    * 설문기본번호 
    */
    private String qstnrBasNo;
    /**
    * 캠페인형태코드 
    */
    private String campShapCd;
    private String campShapCdNm;
    /**
    * 캠페인실행수 
    */
    private Integer campExecCnt;
    /**
    * 캠페인조건코드 
    */
    private String campCondCd;
    /**
    * 부모캠페인기본번호 
    */
    private String prntsCampBasNo;
    /**
    * FROM적용매출금액 
    */
    private Integer fromApplySaleAmt;
    /**
    * TO적용매출금액 
    */
    private Integer toApplySaleAmt;
    /**
    * FROM적용매출조건코드 
    */
    private String fromApplySaleCondCd;
    /**
    * TO적용매출조건코드 
    */
    private String toApplySaleCondCd;
    /**
    * FROM적용결재금액 
    */
    private Integer fromApplySnctAmt;
    /**
    * TO적용결재금액 
    */
    private Integer toApplySnctAmt;
    /**
    * FROM적용결제조건코드 
    */
    private String fromApplyPayCondCd;
    /**
    * TO적용결제조건코드 
    */
    private String toApplyPayCondCd;
    /**
    * 적용멤버십등급코드 
    */
    private String applyMshipGradeCd;
    /**
    * 캠페인시작년월일 
    */
    private String campStaYmd;
    /**
    * 캠페인종료년월일 
    */
    private String campEndYmd;
    /**
    * 반복여부 
    */
    private String rpeatYn;
    /**
    * 반복코드 
    */
    private String rpeatCd;
    private String rpeatCdNm;
    /**
    * 반복월 
    */
    private String rpeatMonth;
    /**
    * 반복일 
    */
    private String rpeatDay;
    /**
    * 반복요일정책코드 
    */
    private String rpeatDowPlcyCd;
    /**
    * 캠페인상태코드 
    */
    private String campStatusCd;
    private String campStatusCdNm;
    
    /**
    * 전체예산금액 
    */
    private Integer wholeBdgetAmt;
    /**
    * 캠페인피로도정책코드 
    */
    private String campFtgabltPlcyCd;
    /**
    * 캠페인기본명 
    */
    private String campBasNm;
    /**
    * 캠페인기본내용 
    */
    private String campBasCtnts;
    /**
    * 등록채널코드 
    * 공통코드 : S000 [CRM : CRM , CTC : 상담 , AS : AS , SAP : SAP , POS : POS , BOS : BOS , MEM : 멤버십 , CRA : 세라체크 , DNA : 세라DNA , IoT : IoT]
    */
    private String regChlCd;
    /**
    * 주문채널리스트 
    */
    private Object ordrChlList;
    
    /**
    * 발송시간 
    */
    private String dspHour;
    /**
    * 월요일사용여부 
    */
    private String dow1UseYn;
    /**
    * 화요일사용여부 
    */
    private String dow2UseYn;
    /**
    * 수요일사용여부 
    */
    private String dow3UseYn;
    /**
    * 목요일사용여부 
    */
    private String dow4UseYn;
    /**
    * 금요일사용여부 
    */
    private String dow5UseYn;
    /**
    * 토요일사용여부 
    */
    private String dow6UseYn;
    /**
    * 일요일사용여부 
    */
    private String dow7UseYn;
    

    /**
    * 포인트유효기간시작년월일 
    */
    private String applyPointStaYmd;
    /**
    * 포인트유효기간종료년월일 
    */
    private String applyPointEndYmd;
    
    /**
    * 매출발생시작년월일 
    */
    private String saleOccurStaYmd;
    /**
    * 매출발생종료년월일 
    */
    private String saleOccurEndYmd;
    /**
    * 종료후일수 
    */
    private Integer endAftrDayCnt;
 
    
    /**
    * 하위캠페인기본번호 
    */
    private String lowRspnCampNo;
    /**
    * 하위반응미적용캠페인번호 
    */
    private String lowRspnDnaCampNo;
    
    
    private String applyPoint;
    private String applyCustCnt;
    private String applyCoupnBasNo;
    private String applyCoupnBasNoNm;
    private String applyGrpNo;
    private String applyGrpNoNm;
    private String dspChlCd;
    private String dspCtntTypeNo;
    private String dspCtntTypeNoNm;
    private String rspnApplyYn;
    private String rspnApplyYnNm;
    private String dayGapCnt;
    
    
    private String campTmsNm;
    
    private String drRspnCnt; /* 직접반응수 */
    private String indirRspnCnt; /* 간접반응수 */
    
    
    
    /**
     * 계약번호 
     */
     private String cntrNo;
     /**
     * 고객번호 
     */
     private String custNo;
     /**
     * 고객명 
     */
     private String custNm;
     /**
     * 계약자 통합고객번호 
     */
     private String itgCustNo;
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
     * 주문번호 
     */
     private String ordNo;
     /**
     * 주문번호순번 
     */
     private Integer ordNoSn;
     /**
     * 주문일자 
     */
     private String ordDe;
     /**
     * 주문상태코드 
     */
     private String ordStsCd;
     /**
     * 주문상태명 
     */
     private String ordStsCdnm;
     /**
     * 주문채널코드 
     */
     private String ordChnCd;
     /**
     * 주문구분코드 
     */
     private String ordSeCd;
     /**
     * 주문구분명 
     */
     private String ordSeCdnm;
     /**
     * 주문취소일자 
     */
     private String ordCnclDe;
     /**
     * 품목코드 
     */
     private String itemCd;
     /**
     * 품목명 
     */
     private String itemNm;
     /**
     * 품목그룹코드 
     */
     private String itemGrpCd;
     /**
     * 품목그룹명 
     */
     private String itemGrpCdnm;
     /**
     * 품목유형코드 
     */
     private String itemTyCd;
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
     * 판매유형코드 
     */
     private String saleTyCd;
     /**
     * 판매유형명 
     */
     private String saleTyCdnm;
     /**
     * 판매구분코드 
     */
     private String saleSeCd;
     /**
     * 판매구분명 
     */
     private String saleSeCdnm;
     /**
     * 판매그룹코드 
     */
     private String saleGrpCd;
     /**
     * 판매그룹명 
     */
     private String saleGrpCdnm;
     /**
     * 계약상태코드 
     */
     private String cntrStsCd;
     /**
     * 계약상태명 
     */
     private String cntrStsCdnm;
     /**
     * 계약상태상세코드 
     */
     private String cntrStsDtlCd;
     /**
     * 계약상태상세명 
     */
     private String cntrStsDtlCdnm;
     /**
     * 계약상태상세사유 
     */
     private String cntrStsDtlRsn;
     /**
     * 결제상태코드 
     */
     private String setlStsCd;
     /**
     * 결제상태명 
     */
     private String setlStsCdnm;
     /**
     * 설치상태코드 
     */
     private String istStsCd;
     /**
     * 설치상태명 
     */
     private String istStsCdnm;
     /**
     * 계약취소여부 
     */
     private String cntrCnclYn;
     /**
     * 계약취소일자 
     */
     private String cntrCnclDe;
     /**
     * 계약취소사유 
     */
     private String cntrCnclRsn;
     /**
     * 본부구분코드 
     */
     private String hqSeCd;
     /**
     * 판매인 
     */
     private String seller;
     /**
     * 판매인명 
     */
     private String sellernm;
     /**
     * 판매조직 
     */
     private String saleOrgz;
     /**
     * 설치예정조직 
     */
     private String istDueOrgz;
     /**
     * 설치예정기사 
     */
     private String istDueEngr;
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
     * 창고코드 
     */
     private String wrhCd;
     /**
     * 창고명 
     */
     private String wrhNm;
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
     * 설치확정일자 
     */
     private String istDcsDe;
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
     * 매출확정일자 
     */
     private String selngDcsDe;
     /**
     * 신용조회여부 
     */
     private String cdtSelYn;
     /**
     * 가격정책번호 
     */
     private String prcPlcNo;
     /**
     * 약정기간코드 
     */
     private String agrPdCd;
     /**
     * 약정기간명 
     */
     private String agrPdCdnm;
     /**
     * 계약기간값 
     */
     private Integer cntrPdVal;
     /**
     * 서비스기간코드 
     */
     private String svcPdCd;
     /**
     * 점검주기 
     */
     private String svcCycle;
     /**
     * 선납여부 
     */
     private String prepayYn;
     /**
     * 선납금액 
     */
     private Double prepayAmt;
     /**
     * 선납주기 
     */
     private String prepayCycle;
     /**
     * 선납할인율 
     */
     private Integer prepayDcrt;
     /**
     * 등록비 
     */
     private Double regfee;
     /**
     * 설치비 
     */
     private Double istct;
     /**
     * 해체비 
     */
     private Double dfee;
     /**
     * 기본렌탈금액 
     */
     private Double bassRentAmt;
     /**
     * 렌탈금액 
     */
     private Double rentAmt;
     /**
     * 기본판매금액 
     */
     private Double bassSaleAmt;
     /**
     * 판매금액 
     */
     private Double salesAmt;
     /**
     * 결제금액 
     */
     private Double setlAmt;
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
     * 패키지여부 
     */
     private String pkgYn;
     /**
     * 패키지정책번호 
     */
     private String pkgPlcNo;
     /**
     * 패키지번호 
     */
     private String pkgNo;
     /**
     * 단체여부 
     */
     private String grpYn;
     /**
     * 단체코드 
     */
     private String grpCd;
     /**
     * 단체번호 
     */
     private String grpNo;
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
     * 반품유형코드 
     */
     private String rtgdTyCd;
     /**
     * 반품요청일자 
     */
     private String rtgdReqDe;
     /**
     * 반품접수확정여부 
     */
     private String rtgdDcsYn;
     /**
     * 반품완료일자 
     */
     private String rtgdEndDe;
     /**
     * 약관정책번호 
     */
     private String spltPlcNo;
     /**
     * 설치예정일자 
     */
     private String istDueDe;
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
     * 등록자ID 
     */
     private String regUsrId;
     /**
     * 수정자ID 
     */
     private String updUsrId;
     /**
     * 수정일시 
     */
     private String updDt;
     /**
     * 설치-우편번호 
     */
     private String postNo;
     /**
     * 설치-기본주소 
     */
     private String bassAddr;
     /**
     * 설치-상세주소 
     */
     private String dtlAddr;
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
     * 설치-설치고객명 
     */
     private String istCustNm;
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
     * 정기결제-방법코드 
     */
     private String fsetlMthCd;
     /**
     * 정기결제-방법명 
     */
     private String fsetlMthCdnm;
     /**
     * 정기결제일자코드 
     */
     private String fsetlDeCd;
     /**
     * 계좌신청동의파일번호 
     */
     private String acctAfileNo;
     /**
     * 출고상태코드 
     */
     private String otbnStsCd;
     /**
     * 출고상태명 
     */
     private String otbnStsCdnm;
     /**
     * 비고 
     */
     private String rm;
     /**
     * 기본멤버십금액 
     */
     private Double bassMbrshAmt;
     /**
     * 멤버십금액 
     */
     private Double mbrshAmt;
     /**
     * 일시불무상종료예정일 
     */
     private String lmpsFreeEndDueDe;
     /**
     * 패키지해지여부 
     */
     private String pkgTrmnYn;
     /**
     * 배송유형코드 
     */
     private String dlvTyCd;
     /**
     * 배송유형명 
     */
     private String dlvTyCdnm;
     /**
     * 배송유형상세코드 
     */
     private String dlvTyDtlCd;
     /**
     * 배송유형상세명 
     */
     private String dlvTyDtlCdnm;
     /**
     * 약정기간값 
     */
     private Integer agrPdVal;
     /**
     * 계약기간구분코드 
     */
     private String cntrPdSeCd;
     /**
     * 계약기간구분명 
     */
     private String cntrPdSeCdnm;
     /**
     * 계약이후처리방법코드 
     */
     private String cntrEndAfterProcMthCd;
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
     * 스케줄생성여부 
     */
     private String shdeCreYn;
     /**
     * 렌탈구분코드 
     */
     private String rentSeCd;
     /**
     * 렌탈구분명 
     */
     private String rentSeCdnm;
     /**
     * 홈체험청구할인기간코드 
     */
     private String exprnDmdDcPdCd;
     /**
     * 홈체험청구할인기간명 
     */
     private String exprnDmdDcPdCdnm;
     /**
     * 홈체험계약번호 
     */
     private String exprnCntrNo;
     /**
     * 추천인 
     */
     private String rcmdr;
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
     * 사은품내역 
     */
     private String prsntDtls;
     /**
     * 무상판매유형 
     */
     private String freeSaleTy;
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
     * 가격정책명 
     */
     private String prcPlcNm;
     /**
     * 계약수량 
     */
     private Double cntrQy;
     /**
     * 할인율 
     */
     private String dcrt;
     /**
     * 할인금액 
     */
     private Double dcAmt;
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
     * 추천고객멤버십아이디 
     */
     private String rcmdrMbrshId;
     /**
     * 추천고객멤버십이름 
     */
     private String rcmdrMbrshNm;
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
     /**
     * 거래일to(조회조건) 
     */
     private String cntrStaDt;
     /**
     * 거래일from(조회조건) 
     */
     private String cntrEndDt;

     
     /**
      * 쿠폰발급수 
      */
      private Integer coupnIssueCnt;
      /**
      * 적용포인트유효기간 
      */
      private Integer applyPointValidPerd;
      /**
      * 피로도시작시간 
      */
      private String ftgabltStaHour;
      /**
      * 피로도종료시간 
      */
      private String ftgabltEndHour;
      /**
      * 피로도시작시간 
      */
      private String ftgabltStaMnut;
      /**
      * 피로도종료시간 
      */
      private String ftgabltEndMnut;
      
      /* 발송타입  sms, push */
      private String sendType;
      
      private String tmpltGroup; // Z023_GROUP;
      
      private String joinTbl;
      
      private String joinCol;
      
      private int issueCnt; // 발급 순차 번호
    
    
}
