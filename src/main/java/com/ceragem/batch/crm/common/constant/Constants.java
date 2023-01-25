package com.ceragem.batch.crm.common.constant;

import java.text.SimpleDateFormat;
import java.util.Locale;

import com.ceragem.batch.crm.common.util.Utilities;

/**
 * 
 * @ClassName Constants
 * @author 김성태
 * @date 2022. 4. 18.
 * @Version 1.0
 * @description
 * @Company Copyright ⓒ wigo.ai. All Right Reserved
 */
public class Constants {
	public static final int FETCH_COUNT = 1000;
	public static final int MIG_PAGE_SIZE = 1000;
	public static final int PAGE_SIZE = 1000;
	public static final int MIG_START_YEAR = 2018;
	public static final int MIG_START_MONTH = 0;
	public static final int MIG_START_DAY = 1;
	public static final SimpleDateFormat _DATE_FORMAT = new SimpleDateFormat("yyyyMMdd", Locale.KOREA);
	public static final String _MSG_DORMBEFOREMONTH = "010";
	public static final String _MSG_DORMBEFOREDAY = "020";
	public static final String _MSG_DORMPROCESS = "030";
	
	public static final String _MSG_WITHDRAWALBEFOREMONTH = "110";
	public static final String _MSG_WITHDRAWALBEFOREDAY = "120";
	public static final String _MSG_WITHDRAWALPROCESS = "130";
	public static final String _MSG_COUPNCANCELRESEND = "140";
	
	public static final String _MSG_EXPIRED_POINT = "210";
	
	public static final String _OPEN_DT = Utilities.parseLong("20221014") > Utilities.parseLong(Utilities.getDateString()) ? "20220801" :  "20221014";
//	public static final String _OPEN_DT = "20221009";
	
	
	public final static String _API_CODE_OK = "IAR0200";
	public final static String _API_CODE_NO_DATA = "IAR0400"; /* 조회된 데이터가 없습니다. */
	public final static String _API_DOUBLE_CHIT_NO_DATA = "IAR0500"; /* 조회된 데이터가 없습니다. */
	public final static String _API_CODE_NO_DATA_MSG = "조회된 데이터가 없습니다."; /* 조회된 데이터가 없습니다. */
	public final static String _API_DOUBLE_CHIT_NO_DATA_MSG = "중복된 전표번호 입니다."; /* 조회된 데이터가 없습니다. */

	public static final String _TALK_CODE_POINT_DEPOSIT = "220";	// 포인트 알림톡
	
	public static final String _JADE_DATE = "20201201";
}
