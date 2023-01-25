package com.ceragem.batch.crm.service;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ceragem.batch.crm.common.constant.Constants;
import com.ceragem.batch.crm.common.util.Utilities;
import com.ceragem.batch.crm.model.CrmJadeOrgVo;
import com.ceragem.batch.crm.model.CrmLoginUserVo;

/**
 * 
 * @ClassName	CrmJadeService
 * @author		김성태
 * @date		2022. 10. 5.
 * @Version		1.0
 * @description jade 연동 서비스	
 * @Company		Copyright ⓒ wigo.ai. All Right Reserved
 */
@Service
public class CrmJadeService {
	@Value("${spring.jade.token-url}")
	String tokenUrl;
	@Value("${spring.jade.rest-url}")
	String restUrl;
	@Value("${spring.jade.ceragem}")
	String ceragemCd;
	@Value("${spring.jade.ceragem-cns}")
	String ceragemCdnCd;

	@Value("${spring.jade.ceragem-hr}")
	String ceragemHrCds;

	@Value("${spring.jade.p1-param}")
	String p1Param;

	@Value("${spring.jade.org-param}")
	String orgParam;
	@Value("${spring.jade.hr-param}")
	String hrParam;

	String token = null;
//	private String tokenDt = null;

	@SuppressWarnings("unchecked")
	private String getToken() throws Exception {
//		if(Utilities.isEmpty(token) || Utilities.isEmpty(tokenDt) || !Utilities.getDateString().equals(tokenDt)) {
		String jsonStr = Utilities.wget(tokenUrl, null, null, true, "POST", null);
		Map<String, Object> map = Utilities.parseJson(jsonStr, Map.class);
		token = (String) map.get("Token");
//			tokenDt = Utilities.getDateString();
//		}
		return token;
	}

	@SuppressWarnings("unchecked")
	public List<CrmJadeOrgVo> getCeragemOrgList(String ymd) throws Exception {
		Map<String, Object> parameter = new HashMap<String, Object>();

		parameter.put("p1", URLEncoder.encode(p1Param, "UTF-8"));
		parameter.put("p2", URLEncoder.encode(orgParam, "UTF-8"));
		parameter.put("p3", URLEncoder.encode(getToken(), "UTF-8"));
		String dt = Utilities.isEmpty(ymd) ? Utilities.getDateString() : ymd;
		
//		dt = Constants._JADE_DATE;
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("C_CD", ceragemCd);
		param.put("YMD", dt);
		parameter.put("PARAM", param);
		String jsonStr = Utilities.getJsonString(parameter);
		String p = "jsonData=" + jsonStr;
//		Map<String, Object> result = Utilities.wgetJson(restUrl + "?" + p, Map.class);
		String json = Utilities.wget(restUrl + "?" + p, null, null);
		Map<String, Object> result = Utilities.parseJson(json, Map.class);
		List<Map<String, Object>> list = (List<Map<String, Object>>) result.get("Data");

//		String json = Utilities.wget(restUrl+"?"+p,null,null);
//		CrmJadeResultVo<CrmJadeHrVo> result = Utilities.parseJson(json, CrmJadeResultVo.class);
//		List<CrmJadeHrVo> list = result.Etc;

		return mapToBean(list, CrmJadeOrgVo.class);
	}

	public List<CrmLoginUserVo> getCeragemEmpList(String ymd) throws Exception {
		String[] cds = ceragemHrCds.split(",");
		List<CrmLoginUserVo> list = new ArrayList<CrmLoginUserVo>();
		for (int i = 0; i < cds.length; i++) {
			List<CrmLoginUserVo> l = getHrList(ymd, cds[i]);
			if (Utilities.isNotEmpty(l))
				list.addAll(l);
		}
		return list;
	}

//	public List<CrmLoginUserVo> getCeragemHrList(String ymd) throws Exception {
//		return getHrList(ymd,false);
//	}
//
//	public List<CrmLoginUserVo> getCeragemCnsHrList(String ymd) throws Exception {
//		return getHrList(ymd,true);
//	}

	@SuppressWarnings("unchecked")
	public List<CrmLoginUserVo> getHrList(String ymd, String code) throws Exception {
		Map<String, Object> parameter = new HashMap<String, Object>();

		parameter.put("p1", URLEncoder.encode(p1Param, "UTF-8"));
		parameter.put("p2", URLEncoder.encode(hrParam, "UTF-8"));
		parameter.put("p3", URLEncoder.encode(getToken(), "UTF-8"));

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("C_CD", ceragemCd);
		String dt = Utilities.isEmpty(ymd) ? Utilities.getDateString() : ymd;
		dt = Constants._JADE_DATE;
		param.put("YMD", dt);
		param.put("CP_CD", code);
		parameter.put("PARAM", param);

		String jsonStr = Utilities.getJsonString(parameter);
		String p = "jsonData=" + jsonStr;
//		Map<String, Object> result = Utilities.wgetJson(restUrl + "?" + p, Map.class);
		String json = Utilities.wget(restUrl + "?" + p, null, null);
		Map<String, Object> result = Utilities.parseJson(json, Map.class);
		List<Map<String, Object>> list = (List<Map<String, Object>>) result.get("Data");

//		String jsonStr = Utilities.getJsonString(parameter);
//		String json = Utilities.wget(restUrl,jsonStr,null,true);

//		CrmJadeResultVo<CrmJadeHrVo> result = Utilities.parseJson(json, CrmJadeResultVo.class);
//		List<CrmJadeHrVo> list = result.Etc;

		return mapToBean(list, CrmLoginUserVo.class);
	}

	private <T> List<T> mapToBean(List<Map<String, Object>> list, Class<T> clz) {

		try {
			List<T> ret = new ArrayList<T>();
			for (int i = 0; i < list.size(); i++) {
				Map<String, Object> map = Utilities.convert2CamelCase(list.get(i));
				ret.add(Utilities.mapToBean(map, clz));
			}
			return ret;
		} catch (Exception ex) {
			return null;
		}
	}
}
