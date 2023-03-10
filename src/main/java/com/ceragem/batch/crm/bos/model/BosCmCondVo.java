package com.ceragem.batch.crm.bos.model;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @ClassName	BosCmCondVo
 * @author		κΉμ±ν
 * @date		2022. 10. 5.
 * @Version		1.0
 * @description	Bos api param
 * @Company		Copyright β wigo.ai. All Right Reserved
 */
@Getter
@Setter
public class BosCmCondVo {
	String randomKey;
	String crmUsrId = "crm";
	String usrLocale = "ko";
	String crmYn = "Y";
	String crmJobSe = "";
}
