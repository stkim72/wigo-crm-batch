package com.ceragem.batch.crm.model;

import java.io.Serializable;
import java.util.Map;

import com.ceragem.batch.crm.common.util.Utilities;

import lombok.Data;

/**
 * 
 * <pre>
 * com.ceragem.crm.common.model - BaseBo.java
 * </pre>
 *
 * @ClassName : BaseBo
 * @Description : 기본Vo
 * @author : 김성태
 * @date : 2021. 1. 5.
 * @Version : 1.0
 * @Company : Copyright ⓒ wigo.ai. All Right Reserved
 */
@Data
public class BaseVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4361056844282850052L;
	private String stat;
	private String peerIp;
	private String regrId = "CRM";
	private String regDt;
	private String amdrId = "CRM";
	private String amdDt;

	public BaseVo() {
	}

	public BaseVo(Map<String, Object> param) {
		Utilities.mapToBean(param, this);
	}
//	
//	public String getInsDt() {
//		return insDt;
//	}
//	public void setInsDt(String insDt) {
//		this.insDt = insDt;
//	}
//	public String getInsId() {
//		return insId;
//	}
//	public void setInsId(String insId) {
//		this.insId = insId;
//	}
//	public String getUpdDt() {
//		return updDt;
//	}
//	public void setUpdDt(String updDt) {
//		this.updDt = updDt;
//	}
//	public String getUpdId() {
//		return updId;
//	}
//	public void setUpdId(String updId) {
//		this.updId = updId;
//	}
//	public String getPeerIp() {
//		return peerIp;
//	}
//	public void setPeerIp(String peerIp) {
//		this.peerIp = peerIp;
//	}

}
