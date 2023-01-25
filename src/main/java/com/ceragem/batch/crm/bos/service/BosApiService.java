package com.ceragem.batch.crm.bos.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ceragem.batch.crm.bos.model.BosApiParamVo;
import com.ceragem.batch.crm.bos.model.BosCmCondVo;
import com.ceragem.batch.crm.bos.model.BosContractVo;
import com.ceragem.batch.crm.bos.model.BosCrmBcustTxnVo;
import com.ceragem.batch.crm.bos.model.BosCrmContractVo;
import com.ceragem.batch.crm.bos.model.BosCrmRmnyCancelTxnVo;
import com.ceragem.batch.crm.bos.model.BosCrmRmnyCmptTxnVo;
import com.ceragem.batch.crm.bos.model.BosCrmRtnTxnVo;
import com.ceragem.batch.crm.bos.model.BosCrmServiceVo;
import com.ceragem.batch.crm.bos.model.BosCustVo;
import com.ceragem.batch.crm.bos.model.BosEmpVo;
import com.ceragem.batch.crm.bos.model.BosItemVo;
import com.ceragem.batch.crm.bos.model.BosLargeCdVo;
import com.ceragem.batch.crm.bos.model.BosLowMap;
import com.ceragem.batch.crm.bos.model.BosOrgVo;
import com.ceragem.batch.crm.bos.model.BosSmallCdVo;
import com.ceragem.batch.crm.common.util.Utilities;
import com.ceragem.batch.crm.exception.BatchException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @ClassName BosApiService
 * @author 김성태
 * @date 2022. 5. 24.
 * @Version 1.0
 * @description Bos 연계 API
 * @Company Copyright ⓒ wigo.ai. All Right Reserved
 */
@Slf4j
@Service
public class BosApiService {

	String randomKey = null;
	String randomKeyDt = null;

	@Value("${spring.api.bos-url}/CRM_API_0001Ctl/getRandomKey.do")
	String randomKeyUrl;
	@Value("${spring.api.bos-url}/CRM_API_0002Ctl/selectCmCdGrpList.do")
	String largeCdUrl;
	@Value("${spring.api.bos-url}/CRM_API_0003Ctl/selectCmCdDtlList.do")
	String smallCdUrl;
	@Value("${spring.api.bos-url}/CRM_API_0004Ctl/selectCustDtl.do")
	String custDtlUrl;
	@Value("${spring.api.bos-url}/CRM_API_0005Ctl/selectCntrList.do")
	String contractListUrl;
	@Value("${spring.api.bos-url}/CRM_API_0006Ctl/selectCntrDtl.do")
	String contractDtlUrl;
	@Value("${spring.api.bos-url}/CRM_API_0007Ctl/selectCrmItemList.do")
	String crmItemListUrl;
	@Value("${spring.api.bos-url}/CRM_API_0008Ctl/selectCrmOrgList.do")
	String crmOrgListUrl;
	@Value("${spring.api.bos-url}/CRM_API_0009Ctl/selectCrmOrgmbList.do")
	String crmEmpListUrl;

	@Value("${spring.api.bos-url}/CRM_API_0010Ctl/selectCrmServiceList.do")
	String crmServiceListUrl;
	@Value("${spring.api.bos-url}/CRM_API_0011Ctl/selectCrmCtlList.do")
	String crmCtlListUrl;
	@Value("${spring.api.bos-url}/CRM_API_0020Ctl/selectOrgList.do")
	String orgListUrl;
	@Value("${spring.api.bos-url}/CRM_API_0021Ctl/selectOrgDtlList.do")
	String orgDtlUrl;
	@Value("${spring.api.bos-url}/CRM_API_0022Ctl/selectOrgmbList.do")
	String empListUrl;
	@Value("${spring.api.bos-url}/CRM_API_0023Ctl/selectOrgmbDtlList.do")
	String empDtlUrl;
//추가
	@Value("${spring.api.bos-url}/CRM_API_0012Ctl/selectCrmRtnList.do")
	String crmRtnListUrl;
	@Value("${spring.api.bos-url}/CRM_API_0013Ctl/selectCrmPoorCustDtlList.do")
	String crmPoorCustDtlListUrl;
	@Value("${spring.api.bos-url}/CRM_API_0032Ctl/selectRcivList.do")
	String rcivListUrl;
	@Value("${spring.api.bos-url}/CRM_API_0033Ctl/selectRcivCnclList.do")
	String rcivCnclListUrl;
	@Value("${spring.api.bos-url}/CRM_API_0056Ctl/saveCrmCustInfo.do")
	String bosEventUrl;
	String enc = "UTF-8";

	@Resource(name = "jacksonObjectMapper")
	ObjectMapper objectMapper;

	static final String[] LARGE_CD_AREA = { "AM", "CM", "CR", "FI", "OG", "SD", "SV", "WM" };

	/**
	 * 
	 * @author 김성태
	 * @date 2022. 5. 24.
	 * @param uri
	 * @param param
	 * @return
	 * @throws Exception
	 * @description api text 가져오기
	 *
	 */
	private String getStr(String uri, BosApiParamVo param) throws Exception {

//		serverUrl.append( uri );
		String method = "POST";
		String enc = "UTF-8";
		BufferedReader br = null;
		OutputStreamWriter osw = null;
		String body = Utilities.getJsonString(param);

		log.debug("[" + uri + "][" + body + "]");
		BosCmCondVo cmCond = param.getCmCond();
		try {

			URL url = new URL(uri);

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			if (conn == null)
				return null;
			conn.setDoOutput(true);
			conn.setConnectTimeout(2000);
			conn.setUseCaches(false);
			conn.setRequestMethod(method);
			conn.setRequestProperty("Content-Type", "application/json;charset=" + enc);
			if (Utilities.isNotEmpty(cmCond.getRandomKey()))
				conn.setRequestProperty("randomKey", cmCond.getRandomKey());
			conn.setRequestProperty("crmUsrId", cmCond.getCrmUsrId());
			conn.setRequestProperty("crmYn", cmCond.getCrmYn());
			conn.setRequestProperty("crmJobSe", cmCond.getCrmJobSe());
			conn.setRequestProperty("usrLocale", cmCond.getUsrLocale());

			if (Utilities.isNotEmpty(body)) {
				osw = new OutputStreamWriter(conn.getOutputStream(), enc);
				osw.write(body);
				osw.flush();
			}

			int responseCode = conn.getResponseCode();
			if (responseCode != HttpURLConnection.HTTP_OK) {
				throw new BatchException("[" + responseCode + "][" + uri + "] 보스 서버 에러");
//				return null;
			}

			br = new BufferedReader(new InputStreamReader(conn.getInputStream(), enc));

			char[] buffer = new char[1024];
			StringBuffer sb = new StringBuffer();
			do {
				int nRead = br.read(buffer);
				if (nRead <= 0)
					break;

				sb.append(buffer, 0, nRead);
			} while (true);
			return sb.toString();
		} catch (Exception ex) {

			return null;
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (Exception ex) {
				log.debug(ex.getMessage());
			}
			try {
				if (osw != null)
					osw.close();
			} catch (Exception ex) {
				log.debug(ex.getMessage());
			}

		}
	}

	/**
	 * 
	 * @author 김성태
	 * @date 2022. 5. 24.
	 * @param uri
	 * @param param
	 * @return bos api map으로 가져오기
	 * @throws Exception
	 * @description
	 *
	 */
	public Map<String, Object> getResultMap(String uri, BosApiParamVo param) throws Exception {
		String result = getStr(uri, param);
		if (Utilities.isEmpty(result))
			return null;
		return Utilities.getJson(result);
	}

	/**
	 * 
	 * @author 김성태
	 * @date 2022. 5. 24.
	 * @param <T>
	 * @param uri
	 * @param param
	 * @param clz   클래스유형
	 * @return 결과 객체
	 * @throws Exception
	 * @description api결과 vo로 가져오기
	 *
	 */
	public <T> T getResultVo(String uri, BosApiParamVo param, Class<T> clz) throws Exception {
		return getResultVo(uri, param, clz, false);
	}

	@SuppressWarnings("unchecked")
	public <T> T getResultVo(String uri, BosApiParamVo param, Class<T> clz, boolean lowerCase) throws Exception {
		Map<String, Object> result = getResultMap(uri, param);
		if (Utilities.isEmpty(result))
			return null;
		if (!"0".equals(result.get("rtnCode"))) {
			throw new BatchException((String) result.get("rtnMsg"));
		}
		Map<String, Object> obj = (Map<String, Object>) result.get("dtlCond");
		if (lowerCase) {
			BosLowMap map = new BosLowMap(obj);
			obj = map;
		}
		return objectMapper.convertValue(obj, clz);
	}

	/**
	 * 
	 * @author 김성태
	 * @date 2022. 5. 24.
	 * @param uri
	 * @param param
	 * @return
	 * @throws Exception
	 * @description list<Map> 결과 가져오기
	 *
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> getResultList(String uri, BosApiParamVo param) throws Exception {
		return getResultList(uri, param, Map.class);
	}

	/**
	 * 
	 * @author 김성태
	 * @date 2022. 5. 24.
	 * @param <T>
	 * @param uri
	 * @param param
	 * @param clz
	 * @return
	 * @throws Exception
	 * @description 결과 가져오기
	 *
	 */
	public <T> List<T> getResultList(String uri, BosApiParamVo param, Class<T> clz) throws Exception {
		return getResultList(uri, param, clz, false);

	}

	@SuppressWarnings("unchecked")
	public <T> List<T> getResultList(String uri, BosApiParamVo param, Class<T> clz, boolean lowerCase)
			throws Exception {
		Map<String, Object> result = getResultMap(uri, param);
		if (Utilities.isEmpty(result))
			return null;
		if (!"0".equals(result.get("rtnCode"))) {
			throw new BatchException((String) result.get("rtnMsg"));
		}
		List<Map<String, Object>> list = (List<Map<String, Object>>) result.get("list");
//		if(list.size()>0)
//			log.debug(""+ list.size());
		if (lowerCase && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Map<String, Object> map = list.get(i);
				BosLowMap l = new BosLowMap(map);
				list.set(i, l);
			}
		}
		return objectMapper.convertValue(list, TypeFactory.defaultInstance().constructCollectionType(List.class, clz));
	}

	/**
	 * 
	 * @author 김성태
	 * @date 2022. 5. 24.
	 * @return
	 * @throws Exception
	 * @description 암호화키 가져오기
	 *
	 */
	public String getRandomKey() throws Exception {
		String dt = Utilities.getDateString();
		if (!dt.equals(randomKeyDt))
			randomKey = null;
		if (Utilities.isNotEmpty(randomKey))
			return randomKey;
		randomKeyDt = null;
		Map<String, Object> result = getResultMap(randomKeyUrl, new BosApiParamVo(null));
		if (result == null)
			throw new BatchException("랜덤키를 획득하지 못했습니다.");
		randomKey = (String) result.get("randomKey");
		randomKeyDt = dt;
		return randomKey;

	}

	/**
	 * 
	 * @author 김성태
	 * @date 2022. 5. 24.
	 * @param themaRelmCd 주제영역 AM:자산, CM:시스템, CR:수수료, FI:수납, OG:조직, SD:영업, SV:서비스,
	 *                    WM:물류
	 * @return
	 * @throws Exception
	 * @description 공통코드 대분류가져오기
	 *
	 */
	public List<BosLargeCdVo> getLargeCdList(String themaRelmCd) throws Exception {
		BosApiParamVo param = new BosApiParamVo(getRandomKey());
		param.setParam("themaRelmCd", themaRelmCd);
		List<BosLargeCdVo> list = getResultList(largeCdUrl, param, BosLargeCdVo.class);
		return list;
	}

	/**
	 * 
	 * @author 김성태
	 * @date 2022. 5. 24.
	 * @param cmGrpCd 대분류코드
	 * @return
	 * @throws Exception
	 * @description 소분류가져오기
	 *
	 */
	public List<BosSmallCdVo> getSmallCdList(String cmGrpCd) throws Exception {
		BosApiParamVo param = new BosApiParamVo(getRandomKey());
		param.setParam("cmGrpCd", cmGrpCd);
		List<BosSmallCdVo> list = getResultList(smallCdUrl, param, BosSmallCdVo.class);
		return list;
	}

	/**
	 * 
	 * @author 김성태
	 * @date 2022. 5. 24.
	 * @param crmCustNo 통합고객번호
	 * @return
	 * @throws Exception
	 * @description 고객정보 가져오기
	 *
	 */
	public BosCustVo getCustomerDtl(String crmCustNo) throws Exception {
		BosApiParamVo param = new BosApiParamVo(getRandomKey());
		param.setParam("crmCustNo", crmCustNo);
		BosCustVo vo = getResultVo(custDtlUrl, param, BosCustVo.class);
		return vo;
	}

	/**
	 * 
	 * @author 김성태
	 * @date 2022. 5. 24.
	 * @param crmCustNo 통합고객번호
	 * @param ordNo     주문번호
	 * @param cntrNo    계약번호
	 * @return
	 * @throws Exception
	 * @description 계약정보 가져오기
	 *
	 */
	public List<BosContractVo> getContractList(String crmCustNo, String ordNo, String cntrNo) throws Exception {
		BosApiParamVo param = new BosApiParamVo(getRandomKey());
		param.setParam("crmCustNo", crmCustNo);
		param.setParam("ordNo", ordNo);
		param.setParam("cntrNo", cntrNo);
		List<BosContractVo> list = getResultList(contractListUrl, param, BosContractVo.class);
		return list;
	}

	/**
	 * 
	 * @author 김성태
	 * @date 2022. 5. 24.
	 * @param cntrNo 계약번호
	 * @return
	 * @throws Exception
	 * @description 계약상세 조회
	 *
	 */
	public BosContractVo getContractDtl(String cntrNo) throws Exception {
		BosApiParamVo param = new BosApiParamVo(getRandomKey());
		param.setParam("cntrNo", cntrNo);
		BosContractVo vo = getResultVo(contractDtlUrl, param, BosContractVo.class);
		return vo;
	}

	/**
	 * 
	 * @author 김성태
	 * @date 2022. 5. 24.
	 * @param crmCustNo 통합고객번호
	 * @return
	 * @throws Exception
	 * @description 품목내역(분석)
	 *
	 */
	public List<BosItemVo> getCrmItemList(String crmCustNo, int page, int size) throws Exception {
		BosApiParamVo param = new BosApiParamVo(getRandomKey());
		int pageNo = page;
		int pageSize = size;
		if (pageNo < 1)
			pageNo = 1;
		if (pageSize < 1)
			pageSize = 1000;
		param.setParam("crmCustNo", crmCustNo);
		param.setParam("pageNo", pageNo);
		param.setParam("pageSize", pageSize);
		List<BosItemVo> list = getResultList(crmItemListUrl, param, BosItemVo.class);
		return list;
	}

	/**
	 * 
	 * @author 김성태
	 * @date 2022. 5. 24.
	 * @param orgzCd 조직코드
	 * @return 조직내역(분석)
	 * @throws Exception
	 * @description
	 *
	 */
	public List<BosOrgVo> getCrmOrgList(String orgzCd) throws Exception {
		BosApiParamVo param = new BosApiParamVo(getRandomKey());
		param.setParam("orgzCd", orgzCd);
		List<BosOrgVo> list = getResultList(crmOrgListUrl, param, BosOrgVo.class);
		return list;
	}

	/**
	 * 
	 * @author 김성태
	 * @date 2022. 5. 24.
	 * @param orgmbNo 조직원번호
	 * @return
	 * @throws Exception
	 * @description 조직원내역(분석)
	 *
	 */
	public List<BosEmpVo> getCrmEmpList(String orgmbNo) throws Exception {
		BosApiParamVo param = new BosApiParamVo(getRandomKey());
		param.setParam("orgmbNo", orgmbNo);
		List<BosEmpVo> list = getResultList(crmEmpListUrl, param, BosEmpVo.class);
		return list;
	}

	/**
	 * 
	 * @author 김성태
	 * @date 2022. 5. 24.
	 * @param crmCustNo 통합고객번호
	 * @param frStartDe 검색시작일
	 * @param toEndDe   검색종료일
	 * @return
	 * @throws Exception
	 * @description 정기점검내역(분석)
	 *
	 */
	public List<BosCrmServiceVo> getCrmServiceList(String crmCustNo, String frStartDe, String toEndDe, int page,
			int size) throws Exception {
		int pageNo = page;
		int pageSize = size;
		if (pageNo < 1)
			pageNo = 1;
		if (pageSize < 1)
			pageSize = 1000;
		BosApiParamVo param = new BosApiParamVo(getRandomKey());
		param.setParam("crmCustNo", crmCustNo);
		param.setParam("frStartDe", frStartDe);
		param.setParam("toEndDe", toEndDe);
		param.setParam("pageNo", pageNo);
		param.setParam("pageSize", pageSize);
		List<BosCrmServiceVo> list = getResultList(crmServiceListUrl, param, BosCrmServiceVo.class);
		return list;
	}

	/**
	 * 
	 * @author 김성태
	 * @date 2022. 5. 24.
	 * @param crmCustNo 통합고객번호
	 * @param ordNo     주문번호
	 * @param cntrNo    계약번호
	 * @param frStartDe 검색시작일
	 * @param toEndDe   검색종료일
	 * @return 계약내역(분석)
	 * @throws Exception
	 * @description
	 *
	 */
	public List<BosCrmContractVo> getCrmContractList(String crmCustNo, String ordNo, String cntrNo, String frStartDe,
			String toEndDe, int page, int size) throws Exception {
		int pageNo = page;
		int pageSize = size;
		if (pageNo < 1)
			pageNo = 1;
		if (pageSize < 1)
			pageSize = 1000;
		BosApiParamVo param = new BosApiParamVo(getRandomKey());
		param.setParam("crmCustNo", crmCustNo);
		param.setParam("ordNo", ordNo);
		param.setParam("cntrNo", cntrNo);
		param.setParam("frStartDe", frStartDe);
		param.setParam("toEndDe", toEndDe);
		param.setParam("pageNo", pageNo);
		param.setParam("pageSize", pageSize);
		List<BosCrmContractVo> list = getResultList(crmCtlListUrl, param, BosCrmContractVo.class);
		return list;
	}

	/**
	 * 
	 * @author 김성태
	 * @date 2022. 5. 24.
	 * @param orgzCd 조직코드
	 * @return
	 * @throws Exception
	 * @description 조직목록 조회
	 *
	 */
	public List<BosOrgVo> getOrgList(String orgzCd) throws Exception {
		BosApiParamVo param = new BosApiParamVo(getRandomKey());
		param.setParam("orgzCd", orgzCd);
		List<BosOrgVo> list = getResultList(orgListUrl, param, BosOrgVo.class);
		return list;
	}

	/**
	 * 
	 * @author 김성태
	 * @date 2022. 5. 24.
	 * @param orgzCd 조직코드
	 * @return
	 * @throws Exception
	 * @description 조직정보 상세 조회
	 *
	 */
	public BosOrgVo getOrgDtl(String orgzCd) throws Exception {
		BosApiParamVo param = new BosApiParamVo(getRandomKey());
		param.setParam("orgzCd", orgzCd);
		BosOrgVo vo = getResultVo(orgDtlUrl, param, BosOrgVo.class);
		return vo;
	}

	/**
	 * 
	 * @author 김성태
	 * @date 2022. 5. 24.
	 * @param orgmbNo 조직원번호
	 * @return
	 * @throws Exception
	 * @description 조직원목록 조회
	 *
	 */
	public List<BosEmpVo> getEmpList(String orgmbNo) throws Exception {
		BosApiParamVo param = new BosApiParamVo(getRandomKey());
		param.setParam("orgmbNo", orgmbNo);
		List<BosEmpVo> list = getResultList(empListUrl, param, BosEmpVo.class);
		return list;
	}

	/**
	 * 
	 * @author 김성태
	 * @date 2022. 5. 24.
	 * @param orgmbNo 조직원번호
	 * @return
	 * @throws Exception
	 * @description 조직원정보 상세 조회
	 *
	 */
	public BosEmpVo getEmpDtl(String orgmbNo) throws Exception {
		BosApiParamVo param = new BosApiParamVo(getRandomKey());
		param.setParam("orgmbNo", orgmbNo);
		BosEmpVo vo = getResultVo(empDtlUrl, param, BosEmpVo.class);
		return vo;
	}

	/**
	 * 
	 * @author 김성태
	 * @date 2022. 6. 30.
	 * @param
	 * @return
	 * @throws Exception
	 * @description 반환내역(분석)
	 *
	 */
	public List<BosCrmRtnTxnVo> getRtnList(String crmCustNo, String ordNo, String cntrNo, String frStartDe,
			String toEndDe,int page, int size) throws Exception {
		BosApiParamVo param = new BosApiParamVo(getRandomKey());
		int pageNo = page;
		int pageSize = size;
		if (pageNo < 1)
			pageNo = 1;
		if (pageSize < 1)
			pageSize = 1000;
		param.setParam("crmCustNo", crmCustNo);
		param.setParam("ordNo", ordNo);
		param.setParam("cntrNo", cntrNo);
		param.setParam("frStartDe", frStartDe);
		param.setParam("toEndDe", toEndDe);
		param.setParam("pageNo", pageNo);
		param.setParam("pageSize", pageSize);
		
		List<BosCrmRtnTxnVo> list = getResultList(crmRtnListUrl, param, BosCrmRtnTxnVo.class, true);
		return list;
	}

	public List<BosCrmBcustTxnVo> getBcustList(int page, int size) throws Exception {
		BosApiParamVo param = new BosApiParamVo(getRandomKey());
		int pageNo = page;
		int pageSize = size;
		if (pageNo < 1)
			pageNo = 1;
		if (pageSize < 1)
			pageSize = 1000;
		param.setParam("pageNo", pageNo);
		param.setParam("pageSize", pageSize);
		List<BosCrmBcustTxnVo> list = getResultList(crmPoorCustDtlListUrl, param, BosCrmBcustTxnVo.class, true);
		return list;
	}

	/**
	 * 
	 * @author 김성태
	 * @date 2022. 6. 30.
	 * @param rcivFrDe
	 * @param rcivToDe
	 * @param ordNo
	 * @param cntrNo
	 * @return
	 * @throws Exception
	 * @description 수납완료내역
	 *
	 */
	public List<BosCrmRmnyCmptTxnVo> getRcivList(String rcivFrDe, String rcivToDe, String ordNo, String cntrNo,
			int page, int size) throws Exception {
		BosApiParamVo param = new BosApiParamVo(getRandomKey());
		int pageNo = page;
		int pageSize = size;
		if (pageNo < 1)
			pageNo = 1;
		if (pageSize < 1)
			pageSize = 1000;
		param.setParam("rcivFrDe", rcivFrDe);
		param.setParam("rcivToDe", rcivToDe);
		param.setParam("ordNo", ordNo);
		param.setParam("cntrNo", cntrNo);
		param.setParam("pageNo", pageNo);
		param.setParam("pageSize", pageSize);
		List<BosCrmRmnyCmptTxnVo> list = getResultList(rcivListUrl, param, BosCrmRmnyCmptTxnVo.class, true);
		return list;
	}

	/**
	 * 
	 * @author 김성태
	 * @date 2022. 6. 30.
	 * @param rcivFrDe
	 * @param rcivToDe
	 * @param ordNo
	 * @param cntrNo
	 * @return
	 * @throws Exception
	 * @description 수납취소내역
	 *
	 */
	public List<BosCrmRmnyCancelTxnVo> getRcivCnclList(String rcivFrDe, String rcivToDe, String ordNo, String cntrNo,
			int page, int size) throws Exception {
		int pageNo = page;
		int pageSize = size;
		if (pageNo < 1)
			pageNo = 1;
		if (pageSize < 1)
			pageSize = 1000;
		BosApiParamVo param = new BosApiParamVo(getRandomKey());
		param.setParam("rcivFrDe", rcivFrDe);
		param.setParam("rcivToDe", rcivToDe);
		param.setParam("ordNo", ordNo);
		param.setParam("cntrNo", cntrNo);
		param.setParam("pageNo", pageNo);
		param.setParam("pageSize", pageSize);

		List<BosCrmRmnyCancelTxnVo> list = getResultList(rcivCnclListUrl, param, BosCrmRmnyCancelTxnVo.class, true);
		return list;
	}
	
	/**
	 * 
	 * @author 김성태
	 * @date 2022. 10. 5.
	 * @param itgCustNo
	 * @return 보스 고객 이벤트 전송
	 * @throws Exception
	 * @description
	 *
	 */
	public boolean sendCustEvent(String itgCustNo) throws Exception {
		BosApiParamVo param = new BosApiParamVo(getRandomKey());
		param.setParam("crmCustNo", itgCustNo);
		Map<String, Object> result = getResultMap(bosEventUrl, param);
		if (Utilities.isEmpty(result))
			return false;
		return true;
//		BosEmpVo vo = getResultVo(empDtlUrl, param, BosEmpVo.class);
//		return vo;
	}
}
