package com.ceragem.batch.crm.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ceragem.batch.crm.mapper.CrmMapper;

@CrmMapper
public interface PromEventDao extends ICrmDao {

	List<HashMap<String, Object>> getPromEventAll();

	List<String> getItemNos(HashMap<String, Object> proMap);

	List<String> getStorNos(HashMap<String, Object> proMap);

	int checkPointHst(HashMap<String, Object> chkMap);

	int checkCoupnHst(HashMap<String, Object> chkMap);

	int savePromEventCoupn(HashMap<String, Object> chkMap);

	Map<String, Object> selectStor(HashMap<String, Object> alarm);

	List<HashMap<String, Object>> getBosAndPosOrderCust(HashMap<String, Object> proMap);

	HashMap<String, Object> selectMaster(HashMap<String, Object> chkMap);

	int getDayCoupnCnt(HashMap<String, Object> voTem);

	HashMap<String, Object> getCustInfo(HashMap<String, Object> voTem);

	String getAutoSeq(Map<String, Object> param);

}
