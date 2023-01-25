package com.ceragem.batch.crm.bos.model;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @ClassName	BosLowMap
 * @author		김성태
 * @date		2022. 10. 5.
 * @Version		1.0
 * @description	bos 에서 사용할 프로퍼티 소문자로 만들기
 * @Company		Copyright ⓒ wigo.ai. All Right Reserved
 */
public class BosLowMap extends HashMap<String, Object> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BosLowMap() {

	}

	public BosLowMap(Map<String, Object> map) {
		this.putAll(map);

	}

	@Override
	public void putAll(Map<? extends String, ? extends Object> map) {
		for (Entry<? extends String, ? extends Object> entry : map.entrySet()) {
			super.put(entry.getKey().toLowerCase(), entry.getValue());
		}
	}

	@Override
	public Object put(String key, Object value) {
		return super.put(key.toLowerCase(), value);
	}
}
