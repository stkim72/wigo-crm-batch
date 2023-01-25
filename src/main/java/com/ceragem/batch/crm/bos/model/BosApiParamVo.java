package com.ceragem.batch.crm.bos.model;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @ClassName	BosApiParamVo
 * @author		김성태
 * @date		2022. 10. 5.
 * @Version		1.0
 * @description	api parameter
 * @Company		Copyright ⓒ wigo.ai. All Right Reserved
 */
@Getter
@Setter
public class BosApiParamVo {
	private BosCmCondVo cmCond;
	private Map<String, Object> cond;

	public BosApiParamVo(String randomKey) {
		cmCond = new BosCmCondVo();
		cmCond.setRandomKey(randomKey);
		cond = new HashMap<String, Object>();
	}

	public void setParam(String name, Object value) {
		cond.put(name, value);
	}

}
