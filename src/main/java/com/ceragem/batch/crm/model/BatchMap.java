package com.ceragem.batch.crm.model;

import java.util.LinkedHashMap;

import com.ceragem.batch.crm.common.util.Utilities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BatchMap extends LinkedHashMap<String, Object> {

	private static final long serialVersionUID = -1891735901489714161L;

	@Override
	public Object put(String k, Object value) {
		String key = k;
		key = Utilities.convert2CamelCase(key);
		return super.put(key, value);
	}

	public String getString(String key) {
		return Utilities.nullCheck(get(key));
	}

	public int getInt(String key) {
		return Utilities.parseInt(get(key));
	}

	public long getLong(String key) {
		return Utilities.parseLong(get(key));
	}

	public boolean getBoolean(String key) {
		return Utilities.parseBoolean(get(key));
	}

	public float getFloat(String key) {
		return Utilities.parseFloat(get(key));
	}

	public double getDouble(String key) {
		return Utilities.parseDouble(get(key));
	}

	public void setString(String key, Object value) {
		put(key, Utilities.nullCheck(value));
	}

	public void setInt(String key, Object value) {
		put(key, Utilities.parseInt(value));
	}

	public void setLong(String key, Object value) {
		put(key, Utilities.parseLong(value));
	}

	public void setBoolean(String key, Object value) {
		put(key, Utilities.parseBoolean(value));
	}

	public void setFloat(String key, Object value) {
		put(key, Utilities.parseFloat(value));
	}

	public void setDouble(String key, Object value) {
		put(key, Utilities.parseDouble(value));
	}

}
