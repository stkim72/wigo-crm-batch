package com.ceragem.batch.crm.common.util;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.axis.encoding.Base64;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.w3c.dom.Document;

import com.ceragem.batch.crm.common.util.security.KisaSeed256;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @ClassName	Utilities
 * @author		κΉμ±ν
 * @date		2022. 10. 5.
 * @Version		1.0
 * @description	
 * @Company		Copyright β wigo.ai. All Right Reserved
 */
@Slf4j
@Component
public class Utilities {

	private static final String _ENC_LANG = "UTF-8";
	private static ConfigurableApplicationContext context;
	private static ObjectMapper objectMapper;

	@Autowired
	ConfigurableApplicationContext ctx;
	@Autowired
	MessageSource localMessageSource;

	@PostConstruct
	public void init() throws Exception {
		context = this.ctx;
		objectMapper = (ObjectMapper) context.getBean("jacksonObjectMapper");
//		localeResolver = this.sessionLocaleResolver;
		TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {

			@Override
			public X509Certificate[] getAcceptedIssuers() {
				return null;
			}

			@Override
			public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
//				log.debug("checkServerTrusted : "+authType);
			}

			@Override
			public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
//				log.debug("checkClientTrusted : "+authType);
			}
		} };

		SSLContext sc = SSLContext.getInstance("SSL");
		sc.init(null, trustAllCerts, new java.security.SecureRandom());
		HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

		HostnameVerifier allHostsValid = new HostnameVerifier() {
			public boolean verify(String hostname, SSLSession session) {
				return true;
			}
		};

		HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);

	}

	/**
	 * λ°°μ΄μ λ¬Έμμ΄λ‘ λ³ν
	 * 
	 * @param arr λ°°μ΄
	 * @return
	 */
	public static String getArrayString(String[] arr) {
		return getArrayString(arr, ",");
	}

	/**
	 * λ°°μ΄μ λ¬Έμμ΄λ‘ λ³ν
	 * 
	 * @param arr        λ°°μ΄
	 * @param strSpliter κ΅¬λΆμ
	 * @return
	 */
	public static String getArrayString(String[] arr, String strSpliter) {
		if (arr == null || arr.length == 0)
			return "";
		StringBuffer strRet = new StringBuffer();
		for (int i = 0; i < arr.length; i++) {
			if (strRet.length() > 0)
				strRet.append(strSpliter);
			strRet.append(arr[i]);
		}
		return strRet.toString();
	}

	/**
	 * ν΄λλ₯Ό μμ±
	 * 
	 * @param strDirectory ν΄λλͺ(μ λκ²½λ‘)
	 */
	public static void clearDirectory(String strDirectory) {
		clearDirectory(new File(strDirectory));

	}

	/**
	 * ν΄λλ₯Ό μμ±
	 * 
	 * @param fDirectory κ²½λ‘
	 */
	public static void clearDirectory(File fDirectory) {
		if (fDirectory == null)
			return;
		if (!fDirectory.isDirectory())
			return;

		File[] listFile = fDirectory.listFiles();
		int nLength = listFile.length;

		for (int i = 0; i < nLength; i++) {
			File fSub = listFile[i];
			if (fSub.isFile())
				fSub.delete();
			else if (fSub.isDirectory())
				deleteFile(fSub);
		}

	}

	/**
	 * ν΄λλ₯Ό μ­μ 
	 * 
	 * @param fDirectory κ²½λ‘
	 */
	static public void deleteDirectory(File fDirectory) {
		if (fDirectory == null || !fDirectory.isDirectory())
			return;
		deleteFile(fDirectory);
	}

	/**
	 * ν΄λλ₯Ό μ­μ 
	 * 
	 * @param strDirectory κ²½λ‘
	 */
	static public void deleteDirectory(String strDirectory) {
		deleteDirectory(new File(strDirectory));

	}

	/**
	 * νμΌ λλ ν΄λλ₯Ό μ­μ 
	 * 
	 * @param fDelete κ²½λ‘
	 */
	static public void deleteFile(File fDelete) {
		if (fDelete == null)
			return;
		clearDirectory(fDelete);
		fDelete.delete();
	}

	/**
	 * ν΄λ μμ±
	 * 
	 * @param strDirectory κ²½λ‘
	 * @return
	 */
	static public boolean createDirectory(String strDirectory) {
		File dInfo = new File(strDirectory);
		dInfo.mkdirs();
		return dInfo.exists();
	}

	/**
	 * λ°μ΄νΈλ₯Ό λ¬Έμμ΄λ‘
	 * 
	 * @param bt
	 * @return
	 */
	public static String getByteString(byte[] bt) {
		if (bt == null)
			return "";
		return new String(bt);
	}

	/**
	 * λ μ§λ₯Ό λ¬Έμμ΄λ‘ λ³ν
	 * 
	 * @return
	 */
	public static String getDateString() {
		return getDateString("");
	}

	/**
	 * λ μ§λ₯Ό λ¬Έμμ΄λ‘ λ³ν
	 * 
	 * @param dt λ μ§
	 * @return
	 */
	public static String getDateString(Date dt) {
		return getDateString(dt, "");
	}

	/**
	 * λ μ§λ₯Ό λ¬Έμμ΄λ‘ λ³ν
	 * 
	 * @param strSpliter κ΅¬λΆμ
	 * @return
	 */
	public static String getDateString(String strSpliter) {
		return getDateString(new Date(), strSpliter);
	}

	/**
	 * λ μ§λ₯Ό λ¬Έμμ΄λ‘ λ³ν
	 * 
	 * @param dt         λ μ§
	 * @param strSpliter κ΅¬λΆμ
	 * @return
	 */
	public static String getDateString(Date dt, String strSpliter) {
		String strFormat = "yyyy" + strSpliter + "MM" + strSpliter + "dd";
		SimpleDateFormat sf = new SimpleDateFormat(strFormat, Locale.KOREA);
		return sf.format(dt);
	}

	/**
	 * λ¬Έμμ΄μ λ μ§λ‘ λ³νν μ€μ λ νμμ λ¬Έμμ΄λ‘ λ³ν
	 * 
	 * @param strDate
	 * @return
	 */
	public static String getDateFormat(String strDate) {
		return Utilities.getDateFormat(strDate, "/");
	}

	/**
	 * λ¬Έμμ΄μ λ μ§λ‘ λ³νν μ€μ λ νμμ λ¬Έμμ΄λ‘ λ³ν
	 * 
	 * @param strDate λ μ§
	 * @param spliter κ΅¬λΆμ
	 * @return
	 */
	public static String getDateFormat(String date, String spliter) {
		String strDate = date;
		if (isEmpty(strDate))
			return null;
		strDate = getOnlyNumberString(strDate);
		if (strDate.length() < 8)
			return strDate;
		return strDate.substring(0, 4) + spliter + strDate.substring(4, 6) + spliter + strDate.substring(6, 8);
	}

	/**
	 * λ¬Έμμ΄μ μκ°μΌλ‘ λ³νν μ€μ λ νμμ λ¬Έμμ΄λ‘ λ³ν
	 * 
	 * @param strDate
	 * @return
	 */
	public static String getTimeFormat(String strDate) {
		return getTimeFormat(strDate, "");
	}

	/**
	 * λ¬Έμμ΄μ μκ°μΌλ‘ λ³νν μ€μ λ νμμ λ¬Έμμ΄λ‘ λ³ν
	 * 
	 * @param strDate μκ°
	 * @param spliter κ΅¬λΆμ
	 * @return
	 */
	public static String getTimeFormat(String date, String spliter) {
		String strDate = date;
		if (Utilities.isEmpty(strDate))
			return null;
		strDate = Utilities.getOnlyNumberString(strDate);
		if (strDate.length() < 6)
			return strDate;
		return strDate.substring(0, 2) + spliter + strDate.substring(2, 4) + spliter + strDate.substring(4, 6);
	}

	/**
	 * λ¬Έμμ΄μ λ μ§μκ°μΌλ‘ λ³νν μ€μ λ νμμ λ¬Έμμ΄λ‘ λ³ν
	 * 
	 * @param strDateTime
	 * @return
	 */
	public static String getDateTimeFormat(String strDateTime) {
		return getDateTimeFormat(strDateTime, "", "");
	}

	/**
	 * λ¬Έμμ΄μ μκ°μΌλ‘ λ³νν μ€μ λ νμμ λ¬Έμμ΄λ‘ λ³ν
	 * 
	 * @param strDateTime
	 * @param spliterD    λ μ§ κ΅¬λΆμ
	 * @param spliterT    μκ° κ΅¬λΆμ
	 * @return
	 */
	public static String getDateTimeFormat(String datetime, String spliterD, String spliterT) {
		String strDateTime = datetime;
		if (Utilities.isEmpty(strDateTime))
			return null;
		strDateTime = Utilities.getOnlyNumberString(strDateTime);

		if (strDateTime.length() < 14)
			return strDateTime;
		return getDateFormat(strDateTime, spliterD) + "" + getTimeFormat(strDateTime.substring(8));
	}

	/**
	 * λ¬Έμμ΄μ DateνμμΌλ‘ λ³ν
	 * 
	 * @param strDate
	 * @return
	 */
	public static Date parseDate(String strDate) {
		if (strDate == null || strDate.length() == 0)
			return null;
		return parseDate(strDate, "", "", "");

	}

	/**
	 * λ¬Έμμ΄μ DateνμμΌλ‘ λ³ν
	 * 
	 * @param strDate
	 * @param spliter κ΅¬λΆμ
	 * @return
	 */
	public static Date parseDate(String strDate, String spliter) {
		if (strDate == null || strDate.length() == 0)
			return null;
		return parseDate(strDate, spliter, spliter, spliter);

	}

	/**
	 * λ¬Έμμ΄μ DateνμμΌλ‘ λ³ν
	 * 
	 * @param strDate   λ μ§
	 * @param spliterD  λ μ§ κ΅¬λΆμ
	 * @param spliterT  μκ° κ΅¬λΆμ
	 * @param separator λ μ§ μκ° κ΅¬λΆμ
	 * @return
	 */
	public static Date parseDate(String strDate, String spliterD, String spliterT, String separator) {
		if (strDate == null || strDate.length() == 0)
			return null;
		String strFormat = "yyyy" + spliterD + "MM" + spliterD + "dd" + separator + "HH" + spliterT + "mm" + spliterT
				+ "ss";
		return parseDate(strDate, new SimpleDateFormat(strFormat, Locale.KOREA));

	}

	/**
	 * λ¬Έμμ΄μ DateνμμΌλ‘ λ³ν
	 * 
	 * @param strDate λ μ§
	 * @param format  Simpledate format
	 * @return
	 */
	public static Date parseDate(String strDate, SimpleDateFormat format) {
		try {
			return format.parse(strDate);
		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * νμΌ ν¬κΈ°λ₯Ό λ¬Έμμ΄λ‘ λ³ν
	 * 
	 * @param nSize
	 * @return
	 */
	public static String getSizeString(long nSize) {
		int k = 1024;
		int m = k * k;
		int g = m * k;
		if (nSize < k)
			return nSize + "";
		else if (nSize < m) {
			return parseInt(nSize / k + "") + "." + parseLong((nSize % k) * 100 / k + "") + " K";
		} else if (nSize < g) {
			return parseInt(nSize / m + "") + "." + parseLong((nSize % m) * 100 / m + "") + " M";
		} else {
			return parseInt(nSize / g + "") + "." + parseLong((nSize % g) * 100 / g + "") + " G";
		}
	}

	/**
	 * νμΌ ν¬κΈ°λ‘ λ¬Έμμ΄λ‘ λ³ν
	 * 
	 * @param nSize
	 * @return
	 */
	// public static String getFileSizeString( Object nSize ) {
	// return FileUtils.byteCountToDisplaySize(parseLong(nSize));
	//
	// }

	/**
	 * linuxνμ pathλ‘ λ³ν
	 * 
	 * @param fullPath
	 * @return
	 */
	public static String getFilePath(String path) {
		String fullPath = path;
		if (fullPath == null) {
			return null;
		}
		fullPath = fullPath.replace('\\', '/');
		int index = fullPath.lastIndexOf("/");
		if (index == -1) {
			index = fullPath.lastIndexOf("\\");
			if (index > -1) {
				return fullPath.substring(0, index);
			}
		}

		return fullPath.substring(0, index);
	}

	/**
	 * κ²½λ‘λ₯Ό μ μΈν νμΌλͺ κ°μ Έμ€κΈ°
	 * 
	 * @param filepath
	 * @return
	 */
	public static String getFileName(String filepath) {
		if (filepath == null) {
			return null;
		}
		int index = filepath.lastIndexOf("/");
		if (index > -1) {
			return filepath.substring(index + 1);
		}
		index = filepath.lastIndexOf("\\");
		if (index > -1) {
			return filepath.substring(index + 1);
		}

		return filepath;
	}

	/**
	 * κ²½λ‘μ νμ₯μλ₯Ό μ μΈν νμΌλͺ κ°μ Έμ€κΈ°
	 * 
	 * @param filepath
	 * @return
	 */
	public static String getFileNameWithoutExtension(String filepath) {
		String filename = getFileName(filepath);
		if (filename == null || filename.length() == 0)
			return "";

		int pos = filename.lastIndexOf(".");
		if (pos <= 0)
			return filename;

		return filename.substring(0, pos);
	}

	/**
	 * λ¬Έμμ΄ μλΆλΆ trim
	 * 
	 * @param strSource
	 * @param strTrim
	 * @return
	 */
	public static String trimStart(String source, String strTrim) {
		String strSource = source;
		if (strTrim == null || strTrim.length() == 0)
			return strSource;
		while (strSource.startsWith(strTrim)) {
			strSource = strSource.substring(strTrim.length());
		}
		return strSource;

	}

	/**
	 * λ¬Έμμ΄ λ·λΆλΆ trim
	 * 
	 * @param strSource
	 * @param strTrim
	 * @return
	 */
	public static String trimEnd(String source, String strTrim) {
		String strSource = source;
		if (strTrim == null || strTrim.length() == 0)
			return strSource;
		if (strSource == null || strSource.length() == 0)
			return "";
		while (strSource.endsWith(strTrim)) {
			int nIndex = strSource.length() - strTrim.length();
			strSource = strSource.substring(0, nIndex);
		}
		return strSource;

	}

	/**
	 * μΌμͺ½μ padding
	 * 
	 * @param source
	 * @param nSize
	 * @param szPad
	 * @return
	 */
	public static String padLeft(Object source, int nSize, char szPad) {
		String strSource = source == null ? "" : source.toString();

		int nCnt = nSize - strSource.length();
		if (nCnt < 1)
			return strSource;
		StringBuffer bf = new StringBuffer();
		for (int i = 0; i < nCnt; i++) {
//			strSource = szPad + strSource;
			bf.insert(0, szPad);

		}
		bf.append(strSource);
		return bf.toString();
	}

	/**
	 * μ€λ₯Έμͺ½ padding
	 * 
	 * @param source
	 * @param nSize
	 * @param szPad
	 * @return
	 */
	public static String padRight(Object source, int nSize, char szPad) {
		String strSource = source == null ? "" : source.toString();

		int nCnt = nSize - strSource.length();
		if (nCnt < 1)
			return strSource;
		StringBuffer bf = new StringBuffer();
		bf.append(strSource);
		for (int i = 0; i < nCnt; i++) {
//			strSource = strSource + szPad;
			bf.append(szPad);
		}
		return bf.toString();
	}

	/**
	 * μ«μλ₯Ό μΌμͺ½μ 0μ λΆμ¬ λ¬Έμμ΄λ‘ λ¦¬ν΄
	 * 
	 * @param nNumber
	 * @param nSize
	 * @return
	 */
	public static String getNumberString(long nNumber, int nSize) {
		return getNumberString(nNumber, nSize, '0');
	}

	/**
	 * μ«μλ₯Ό μΌμͺ½μ @param chPad μ λΆμ¬ λ¬Έμμ΄λ‘ λ¦¬ν΄
	 * 
	 * @param nNumber
	 * @param nSize
	 * @param chPad
	 * @return
	 */
	public static String getNumberString(long nNumber, int nSize, char chPad) {
		String strNumber = nNumber + "";
		return padLeft(strNumber, nSize, chPad);
	}

	/**
	 * μλ°μ€ν¬λ¦½νΈ μ€λ₯νμ€νΈ λ³ν
	 * 
	 * @param strValue
	 * @return
	 */
	public static String getScriptText(String strValue) {
		if (strValue == null)
			return "";
		return strValue.replace("\"", "\\\"").replace("\r\n", "\n").replace("\n", "\\n");

	}

	/**
	 * λ¬Έμμ΄μ byte ν¬κΈ°λ₯Ό κ°μ Έμ΄
	 * 
	 * @param strText
	 * @return
	 * @throws Exception
	 */

	public static byte[] getStringByte(String strText) throws Exception {
		if (strText == null || strText.length() == 0)
			return new byte[] {};
		return strText.getBytes(_ENC_LANG);
	}

	/**
	 * milisecond tickλ₯Ό μκ°νμμΌλ‘ λ³ν
	 * 
	 * @param time mili-second
	 * @return
	 */
	public static String getTimeString(Object time) {
		return getTimeString(parseLong(time));
	}

	/**
	 * milisecond tickλ₯Ό μκ°νμμΌλ‘ λ³ν
	 * 
	 * @param time mili-second
	 * @return
	 */
	public static String getTimeString(long time) {
		long totalSec = time / 1000;
		long hour = totalSec / 3600;
		long min = (totalSec % 3600) / 60;
		long sec = (totalSec % 3600) % 60;
		return Utilities.padLeft(hour, 2, '0') + "" + Utilities.padLeft(min, 2, '0') + ""
				+ Utilities.padLeft(sec, 2, '0');
	}

	/**
	 * dateλ₯Ό date time λ¬Έμμ΄λ‘ λ³ν
	 * 
	 * @param dt
	 * @return
	 */
	public static String getDateTimeString(Date dt) {
		if (dt == null)
			return null;
		return getDateString(dt, "") + "" + getTimeString(dt, "");
	}

	/**
	 * dateλ₯Ό date time λ¬Έμμ΄λ‘ λ³ν
	 * 
	 * @return
	 */
	public static String getDateTimeString() {
		return getDateString() + "" + getTimeString();
	}

	/**
	 * dateλ₯Ό time λ¬Έμμ΄λ‘ λ³ν
	 * 
	 * @return
	 */
	public static String getTimeString() {
		return getTimeString(new Date(), "");
	}

	/**
	 * dateλ₯Ό time λ¬Έμμ΄λ‘ λ³ν
	 * 
	 * @param strSpliter κ΅¬λΆμ
	 * @return
	 */
	public static String getTimeString(String strSpliter) {
		return getTimeString(new Date(), strSpliter);

	}

	/**
	 * dateλ₯Ό time λ¬Έμμ΄λ‘ λ³ν
	 * 
	 * @param dt
	 * @param strSpliter κ΅¬λΆμ
	 * @return
	 */
	public static String getTimeString(Date dt, String strSpliter) {
		try {
			String strFormat = "HH" + strSpliter + "mm" + strSpliter + "ss";
			SimpleDateFormat sf = new SimpleDateFormat(strFormat, Locale.KOREA);
			return sf.format(dt);
		} catch (Exception ex) {
			log.debug(ex.toString());
		}
		return "";
	}

	/**
	 * κ³ μ ν λ¬Έμμ΄ λ¦¬ν΄
	 * 
	 * @return
	 */
	public static String getPKCd() {
		return getPKCd(Long.toHexString(System.currentTimeMillis()).toUpperCase());
	}

	/**
	 * κ³ μ ν λ¬Έμμ΄ λ¦¬ν΄
	 * 
	 * @param prefix
	 * @return
	 */
	public static String getPKCd(String prefix) {
		int length = prefix == null ? 20 : 20 - prefix.length();
		if (length < 0)
			return prefix.substring(0, 20);
		return Utilities.nullCheck(prefix) + Utilities.getUniqID(length);
	}

	/**
	 * λ¬Έμ μ«μλ‘ κ³ μ κ° μμ±
	 * 
	 * @param nLength
	 * @return
	 */
	public static String getUniqID(int nLength) {
		if (nLength < 1)
			return "";
		Random rd = new Random();
		final char chAdded = 'A';
		final char chRange = (char) 26;
		char[] chValue = new char[nLength];
		for (int i = 0; i < nLength; i++) {
			chValue[i] = (char) (rd.nextDouble() * chRange + chAdded);

		}
		return new String(chValue).toUpperCase();
	}

	/**
	 * μ«μλ‘ κ³ μ κ° μμ±
	 * 
	 * @param nLength
	 * @return
	 */
	public static String getUniqNumberID(int nLength) {
		if (nLength < 1)
			return "";
		Random rd = new Random();

		final char chAdded = '0';
		final char chRange = (char) 9;
		char[] chValue = new char[nLength];
		for (int i = 0; i < nLength; i++) {
			chValue[i] = (char) (rd.nextDouble() * chRange + chAdded + (i == 0 ? 1 : 0));
		}
		return new String(chValue);
	}

	/**
	 * uuid guid μμ±
	 * 
	 * @return
	 */
	public static String getUUID() {
		return String.valueOf(UUID.randomUUID());
	}

	/**
	 * μ°κΈ°κ°λ₯ μ¬λΆ
	 * 
	 * @param strPath κ²½λ‘
	 * @return
	 */

	public static boolean isWritableDirectory(String strPath) {
		File dir = new File(strPath);
		return dir.isDirectory() && dir.canWrite();
	}

	/**
	 * λ¬Έμμ΄μ΄ Null μΌκ²½μ° "" λ¦¬ν΄
	 * 
	 * @param strValue
	 * @return
	 */
	public static String nullCheck(String strValue) {
		return ((strValue == null) ? "" : strValue);
	}

	/**
	 * λ¬Έμμ΄μ΄ Null μΌκ²½μ° "" λ¦¬ν΄
	 * 
	 * @param strValue
	 * @return
	 */
	public static String nullCheck(Object obj) {
		return ((obj == null) ? "" : obj.toString());
	}

	/**
	 * boolean νμμΌλ‘ λ³ν μλ¬μ false λ¦¬ν΄
	 * 
	 * @param strValue
	 * @return
	 */
	public static boolean parseBoolean(String strValue) {
		try {
			if (Utilities.isEmpty(strValue))
				return false;
			return Boolean.parseBoolean(strValue);
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * boolean νμμΌλ‘ λ³ν μλ¬μ false λ¦¬ν΄
	 * 
	 * @param strValue
	 * @return
	 */
	public static boolean parseBoolean(Object obj) {
		try {
			if (Utilities.isEmpty(obj))
				return false;
			return Utilities.parseBoolean(obj.toString());
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Double νμμΌλ‘ λ³ν μλ¬μ 0 λ¦¬ν΄
	 * 
	 * @param strValue
	 * @return
	 */
	public static double parseDouble(String strValue) {
		try {
			return Double.parseDouble(strValue);
		} catch (Exception e) {
			return 0.0;
		}
	}

	/**
	 * Double νμμΌλ‘ λ³ν μλ¬μ 0 λ¦¬ν΄
	 * 
	 * @param strValue
	 * @return
	 */
	public static double parseDouble(Object obj) {
		try {
			if (obj == null)
				return 0.0;
			return Utilities.parseDouble(obj.toString());
		} catch (Exception e) {
			return 0.0;
		}
	}

	/**
	 * Float νμμΌλ‘ λ³ν μλ¬μ 0 λ¦¬ν΄
	 * 
	 * @param strValue
	 * @return
	 */
	public static float parseFloat(String strValue) {
		try {
			return Float.parseFloat(strValue);
		} catch (Exception e) {
			return 0.0f;
		}
	}

	/**
	 * Float νμμΌλ‘ λ³ν μλ¬μ 0 λ¦¬ν΄
	 * 
	 * @param strValue
	 * @return
	 */
	public static float parseFloat(Object obj) {
		try {
			if (Utilities.isEmpty(obj))
				return 0.0f;
			return Utilities.parseFloat(obj.toString());
		} catch (Exception e) {
			return 0.0f;
		}
	}

	/**
	 * int νμμΌλ‘ λ³ν μλ¬μ 0 λ¦¬ν΄
	 * 
	 * @param strValue
	 * @return
	 */
	public static int parseInt(Object obj) {
		try {
			if (Utilities.isEmpty(obj))
				return 0;
			return (int) Double.parseDouble(obj.toString());
		} catch (Exception e) {
			return 0;
		}
	}

	/**
	 * byte νμμΌλ‘ λ³ν μλ¬μ 0 λ¦¬ν΄
	 * 
	 * @param strValue
	 * @return
	 */
	public static byte parseByte(Object obj) {
		try {
			if (Utilities.isEmpty(obj))
				return 0;
			return (byte) Double.parseDouble(obj.toString());
		} catch (Exception e) {
			return 0;
		}
	}

	/**
	 * long νμμΌλ‘ λ³ν μλ¬μ 0 λ¦¬ν΄
	 * 
	 * @param strValue
	 * @return
	 */
	public static long parseLong(String strValue) {

		try {
			return Long.parseLong(strValue);
		} catch (Exception e) {
			return 0;
		}
	}

	/**
	 * long νμμΌλ‘ λ³ν μλ¬μ 0 λ¦¬ν΄
	 * 
	 * @param strValue
	 * @return
	 */
	public static long parseLong(Object obj) {
		try {
			if (obj == null)
				return 0;
			return Utilities.parseLong(obj.toString());
		} catch (Exception e) {
			return 0;
		}
	}

	public static boolean saveXML(String strFileName, Document xDoc) {
		try {

			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	/**
	 * μ¬λ¦Όν¨μ
	 * 
	 * @param dbNumber
	 * @return
	 */
	public static int Ceiling(double dbNumber) {
		int nNumber = (int) dbNumber;
		if (nNumber < dbNumber)
			nNumber++;
		return nNumber;
	}

	/**
	 * urlμΈμ½λ©νκΈ°
	 * 
	 * @param strText
	 * @return
	 */
	public static String getEncodeText(String strText) {
		if (strText == null || strText.length() == 0)
			return "";
		String strRet = "";
		try {
			strRet = java.net.URLEncoder.encode(strText, _ENC_LANG);

		} catch (Exception e) {
			strRet = "";
		}
		return strRet;
	}

	/**
	 * url λμ½λ©νκΈ°
	 * 
	 * @param strText
	 * @return
	 */
	public static String getDecodeText(String text) {
		String strText = text;
		if (strText == null || strText.length() == 0)
			return "";
		String strRet = "";
		try {
			strText = strText.replace("_amp_", "&");
			strText = strText.replace("_dpy_", "%");

			strRet = java.net.URLDecoder.decode(strText, _ENC_LANG);

		} catch (Exception e) {
			strRet = "";
		}
		return strRet;

	}

	/**
	 * date νμμΌλ‘ λ¦¬ν΄
	 * 
	 * @param nYear
	 * @param nMonth
	 * @param nDay
	 * @return
	 */
	public static Date getDate(int nYear, int nMonth, int nDay) {
		Calendar cd = Calendar.getInstance();
		cd.set(Calendar.HOUR_OF_DAY, 0);
		cd.set(Calendar.MINUTE, 0);
		cd.set(Calendar.SECOND, 0);
		cd.set(Calendar.MILLISECOND, 0);
		cd.set(Calendar.YEAR, nYear);
		cd.set(Calendar.MONTH, nMonth - 1);
		cd.set(Calendar.DATE, nDay);
		return cd.getTime();
	}

	/**
	 * νμ¬μκ° λ¦¬ν΄
	 * 
	 * @return
	 */
	public static Date getCurrentTime() {
		return getCalendar().getTime();
	}

	/**
	 * νμ¬ Calendarκ°μ²΄ λ¦¬ν΄
	 * 
	 * @return
	 */
	public static Calendar getCalendar() {
		Calendar cd = Calendar.getInstance();
		return cd;
	}

	/**
	 * νμ¬ λ
	 * 
	 * @return
	 */
	public static int getYear() {
		return getCalendar().get(Calendar.YEAR);
	}

	/**
	 * νμ¬μ
	 * 
	 * @return
	 */
	public static int getMonth() {
		return getCalendar().get(Calendar.MONTH) + 1;
	}

	/**
	 * νμ¬μΌ
	 * 
	 * @return
	 */
	public static int getDay() {
		return getCalendar().get(Calendar.DATE);
	}

	/**
	 * Calendar κ°μ²΄ λ¦¬ν΄
	 * 
	 * @param nYear
	 * @param nMonth
	 * @param nDay
	 * @return
	 */
	public static Calendar getCalendar(int nYear, int nMonth, int nDay) {
		Calendar cd = Calendar.getInstance();
		cd.set(Calendar.YEAR, nYear);
		cd.set(Calendar.MONTH, nMonth - 1);
		cd.set(Calendar.DATE, nYear);
		return cd;
	}

	/**
	 * νμΌ νμ₯μ κ°μ Έμ€κΈ°
	 * 
	 * @param filename
	 * @return
	 */

	public static String getFileExtension(String filename) {
		if (filename == null) {
			return null;
		}
		int index = filename.lastIndexOf(".");
		if (index > -1) {
			return filename.substring(index + 1);
		}
		return "";
	}

	/**
	 * μΉ΄λ© νμμΌλ‘ λ³ν
	 * 
	 * @param list
	 * @return
	 */
	public static String convert2CamelCaseToData(String key) {
		StringBuffer ret = new StringBuffer();
		ret.append("data-");
		for (int i = 0; i < key.length(); i++) {
			char sz = key.charAt(i);
			if (sz >= 'A' && sz <= 'Z')
				ret.append(("-" + sz).toLowerCase());
			else
				ret.append(sz);
		}
		return ret.toString();
	}

	/**
	 * μΉ΄λ© νμμΌλ‘ λ³ν
	 * 
	 * @param map
	 * @return
	 */
	public static Map<String, Object> convert2CamelCase(Map<String, Object> map) {
		Map<String, Object> ret = new HashMap<String, Object>();
		for (String key : map.keySet()) {
			ret.put(convert2CamelCase(key), map.get(key));
		}
		return ret;
	}

	/**
	 * μΉ΄λ© νμμΌλ‘ λ³ν
	 * 
	 * @param underScore
	 * @return
	 */
	public static String convert2CamelCase(String underScore) {

		if (Utilities.isEmpty(underScore))
			return underScore;
		if (underScore.startsWith("_"))
			return underScore;
		if (underScore.indexOf('_') < 0 && Character.isLowerCase(underScore.charAt(0))) {
			return underScore;
		}
		StringBuilder result = new StringBuilder();
		boolean nextUpper = false;
		int len = underScore.length();

		for (int i = 0; i < len; i++) {
			char currentChar = underScore.charAt(i);
			if (currentChar == '_') {
				nextUpper = true;
			} else {
				if (nextUpper) {
					result.append(Character.toUpperCase(currentChar));
					nextUpper = false;
				} else {
					result.append(Character.toLowerCase(currentChar));
				}
			}
		}
		return result.toString();
	}

	public static String wget(String strUri, String strPost, String token, boolean json) {
		return wget(strUri, strPost, token, json, null, null);
	}

	public static String wget(String strUri, String strPost, String token, boolean json, String method) {
		return wget(strUri, strPost, token, json, method, null);
	}

	public static String wget(String strUri, String strPost, String token, boolean json, String strMethod,
			Map<String, Object> header) {
		BufferedReader br = null;
		OutputStreamWriter osw = null;
		String method = strMethod;

		try {
			URL url = new URL(strUri);

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			if (conn == null)
				return "";
			conn.setDoOutput(true);
			conn.setConnectTimeout(2000);
			conn.setUseCaches(false);
			if (strPost != null) {
				if (Utilities.isEmpty(method))
					method = "POST";

				if (json)
					conn.setRequestProperty("Content-Type", "application/json;charset=" + _ENC_LANG);
				else
					conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + _ENC_LANG);
			} else {
				if (Utilities.isEmpty(method))
					method = "GET";
				if (json)
					conn.setRequestProperty("Content-Type", "application/json;charset=" + _ENC_LANG);
				else
					conn.setRequestProperty("Content-Type", "text/plain");
			}
			conn.setRequestMethod(method);
			if (Utilities.isNotEmpty(token)) {
				conn.setRequestProperty("Authorization", "bearer " + token);
			}
			if (header != null) {
				for (String key : header.keySet()) {
					conn.setRequestProperty(key, (String) header.get(header.get(key)));
				}
			}

			if (strPost != null && strPost.length() > 0) {
				osw = new OutputStreamWriter(conn.getOutputStream(), _ENC_LANG);
				osw.write(strPost);
				osw.flush();
			}

			int responseCode = conn.getResponseCode();
			if (responseCode != HttpURLConnection.HTTP_OK)
				return "";

			br = new BufferedReader(new InputStreamReader(conn.getInputStream(), _ENC_LANG));

			char[] buffer = new char[1024];
			StringBuffer sb = new StringBuffer();
			do {
				int nRead = br.read(buffer);
				if (nRead <= 0)
					break;

				sb.append(buffer, 0, nRead);
			} while (true);
//			 String match = "[^\uAC00-\uD7A3xfe0-9a-zA-Z\\s]";
//			 return sb.toString().replaceAll(match, "");
			return sb.toString().replace("β", "");
		} catch (Exception ex) {

			return "";
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (Exception ex) {
				log.debug(ex.toString());
			}
			try {
				if (osw != null)
					osw.close();
			} catch (Exception ex) {
				log.debug(ex.toString());
			}

		}
	}

	/**
	 * μΉurlμμ λ°μ΄ν°λ₯Όμ½μ΄ λ¬Έμμ΄λ‘ λ¦¬ν΄
	 * 
	 * @param strUri
	 * @param strPost
	 * @return
	 */
	public static String wget(String strUri) {
		return wget(strUri, null);
	}

	/**
	 * μΉurlμμ λ°μ΄ν°λ₯Όμ½μ΄ λ¬Έμμ΄λ‘ λ¦¬ν΄
	 * 
	 * @param strUri
	 * @param strPost
	 * @return
	 */
	public static String wget(String strUri, String strPost) {
		return wget(strUri, strPost, null);
	}

	/**
	 * μΉurlμμ λ°μ΄ν°λ₯Όμ½μ΄ λ¬Έμμ΄λ‘ λ¦¬ν΄
	 * 
	 * @param strUri
	 * @param strPost
	 * @return
	 */
	public static String wget(String strUri, String strPost, String token) {
		return wget(strUri, strPost, token, false);
	}

	/**
	 * ν μλ² μμ΄νΌ
	 * 
	 * @return
	 */
	static public String getServerIp() {
		try {
			return InetAddress.getLocalHost().getHostAddress();
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * λ°μ¬λ¦Ό
	 * 
	 * @param number
	 * @param num
	 * @return
	 */
	static public double round(double number, int num) {
		double pow = Math.pow(10, num);
		double tmp = number * pow;
		double value = Math.round(tmp);
		return value / pow;
	}

	/**
	 * λ°μ¬λ¦Ό
	 * 
	 * @param number
	 * @param num
	 * @return
	 */
	static public double round(float number, int num) {
		double pow = Math.pow(10, num);
		double tmp = number * pow;
		long value = Math.round(tmp);
		return value / pow;
	}

	/**
	 * λ°μ¬λ¦Ό λ¬Έμμ΄
	 * 
	 * @param number
	 * @param num
	 * @return
	 */
	static public String roundString(double number, int num) {
		double value = Utilities.round(number, num);
		return Utilities.getNumberString(value, num);
	}

	/**
	 * λ°μ¬λ¦Ό λ¬Έμμ΄
	 * 
	 * @param number
	 * @param num
	 * @return
	 */
	static public String roundString(float number, int num) {
		double value = Utilities.round(number, num);
		return Utilities.getNumberString(value, num);
	}

	/**
	 * μ½€λ§λ€μ΄κ°λ νμ
	 * 
	 * @param nNumber
	 * @return
	 */
	public static String getMoneyString(long nNumber) {
		return getNumberString(nNumber);
	}

	/**
	 * μ½€λ§λ€μ΄κ°λ νμ
	 * 
	 * @param nNumber
	 * @return
	 */
	public static String getNumberString(long number) {
		long nNumber = number;
//		String strMoney = "";
		StringBuffer bf = new StringBuffer();
		while (nNumber > 0) {
			if (bf.length() > 0)
				bf.insert(0, ",");
			bf.insert(0, getNumberString(nNumber % 1000, 3));
			nNumber /= 1000;
			if (nNumber < 1)
				break;
		}
		String strMoney = bf.toString();
		strMoney = trimStart(strMoney, "0");
		if (strMoney.length() == 0)
			strMoney = "0";
		return strMoney;
	}

	/**
	 * μ½€λ§ λ€μ΄κ°λ νμ
	 * 
	 * @param number
	 * @param num
	 * @return
	 */
	static public String getNumberString(double number, int num) {
		long nVal = (long) number;
		String n = getNumberString(nVal);
		if (num < 1)
			return n;
		String format = String.format("%" + num + "f", number);
		int nIndex = format.indexOf(".");

		String dbVal = "";
		if (nIndex > -1)
			dbVal = format.substring(nIndex + 1);

		StringBuffer zero = new StringBuffer();
		for (int i = 0; i < num; i++) {
			zero.append("0");
		}
		String db = dbVal + zero.toString();
		db = db.substring(0, num);
		return n + "." + db;
	}

	/**
	 * νμΌμμ λ¬Έμμ΄ μ½κΈ°
	 * 
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public static String readText(File file) throws Exception {
		FileReader reader = new FileReader(file);
		try {
			return FileCopyUtils.copyToString(reader);
		} finally {
			reader.close();
		}
	}

	/**
	 * νμΌ μ°κΈ°
	 * 
	 * @param file
	 * @param data
	 * @throws Exception
	 */
	public static void writeFile(File file, byte[] data) throws Exception {
		FileCopyUtils.copy(data, file);
	}

	/**
	 * λΉκ°μ²΄κ° μλμ§ μ¬λΆ
	 * 
	 * @param obj
	 * @return
	 */
	static public boolean isNotEmpty(Object obj) {
		return !Utilities.isEmpty(obj);
	}

	/**
	 * λΉκ°μ²΄μΈκ°
	 * 
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	static public boolean isEmpty(Object obj) {

		if (obj == null)
			return true;
		else if ((obj instanceof String)) {
			return ((String) obj).isEmpty();
		} else if (obj instanceof Map) {
			return ((Map<?, ?>) obj).isEmpty();
		} else if (obj instanceof Collection) {
			return ((Collection<?>) obj).isEmpty();
		} else if (obj instanceof Object[]) {
			return (((Object[]) obj).length == 0);
		} else if (obj instanceof File) {
			return !((File) obj).exists();
		} else if (obj instanceof List) {
			return ((List) obj).size() == 0;
		}
		try {
			Method method = obj.getClass().getMethod("isEmpty", (Class<?>[]) null);
			return (Boolean) method.invoke(obj);
		} catch (Exception ex) {
			return false;
		}
	}

	/**
	 * νμμ€ μμΉ νμΈμ©
	 * 
	 * @return
	 */
	public static StackTraceElement getSourceInfo() {
		try {
			return Thread.currentThread().getStackTrace()[2];

		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * μ΄λ―Έμ§ ν¬κΈ°
	 * 
	 * @param fileName
	 * @return
	 */
	public static Dimension getImageSize(String fileName) {
		try {
			BufferedImage bimg = ImageIO.read(new File(fileName));
			int width = bimg.getWidth();
			int height = bimg.getHeight();
			return new Dimension(width, height);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * μ€λ³΅νμΌλͺ λ³κ²½
	 * 
	 * @param list
	 * @return
	 */
	public static List<String> renameFilenames(List<String> list) {
		Map<String, String> map = new HashMap<String, String>();
		List<String> sList = new ArrayList<String>();
		for (int i = 0; i < list.size(); i++) {
			String name = list.get(i);
			if (map.containsKey(name)) {
				for (int j = 1; j < 100000; j++) {
					String name2 = name + "(" + j + ")";
					if (map.containsKey(name2))
						continue;
					else {
						map.put(name2, name);
						sList.add(name2);
						break;
					}
				}
			} else {
				map.put(name, name);
				sList.add(name);
			}
		}
		return sList;
	}

	/**
	 * spring singlton κ°μ²΄ κ°μ Έμ€κΈ°
	 * 
	 * @param cls
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(Class<?> cls) {

		try {
			return (T) context.getBean(cls);
		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * spring singlton κ°μ²΄ κ°μ Έμ€κΈ°
	 * 
	 * @param name
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name) {
		try {
			return (T) context.getBean(name);
		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * ν΄λμ νμΌ κ°μ²΄ κ°μ Έμ€κΈ°
	 * 
	 * @param path
	 * @param list
	 * @return
	 */
	public static List<String> getFiles(String path, List<String> list) {
		File fPath = new File(path);
		if (!fPath.isDirectory())
			return null;
		File[] fList = fPath.listFiles();
		for (int i = 0; i < fList.length; i++) {
			File file = fList[i];
			if (file.isFile())
				list.add(file.getPath());
			if (file.isDirectory()) {
				getFiles(file.getPath(), list);
			}
		}
		return list;
	}

	/**
	 * ν΄λμ νμΌκ°μ²΄ κ°μ Έμ€κΈ°
	 * 
	 * @param path
	 * @return
	 */
	public static List<String> getFiles(String path) {
		return getFiles(path, new ArrayList<String>());

	}

	/**
	 * 16μ§μ λ¬Έμμ΄ byteλ‘
	 * 
	 * @param hex
	 * @return
	 */
	public static byte[] hexToByteArray(String hex) {
		if (hex == null || hex.length() % 2 != 0) {
			return new byte[] {};
		}

		byte[] bytes = new byte[hex.length() / 2];
		for (int i = 0; i < hex.length(); i += 2) {
			byte value = (byte) Integer.parseInt(hex.substring(i, i + 2), 16);
			bytes[(int) Math.floor(i / 2)] = value;
		}
		return bytes;
	}

	/**
	 * νμΌμ°κΈ°
	 * 
	 * @param fileName
	 * @param content
	 * @throws Exception
	 */
	public static void writeFile(String fileName, String content) throws Exception {
		OutputStreamWriter outputStreamWriter = null;
		BufferedWriter out = null;
		try {
			outputStreamWriter = new OutputStreamWriter(new FileOutputStream(fileName), "EUC-KR");
			out = new BufferedWriter(outputStreamWriter);
			if (isNotEmpty(content))
				out.write(content);

		} catch (Exception ex) {
			log.debug(ex.toString());
		} finally {
			try {
				if (out != null)
					out.close();
			} catch (Exception ex) {
				log.debug(ex.toString());
			}
			try {
				if (outputStreamWriter != null)
					outputStreamWriter.close();
			} catch (Exception ex) {
				log.debug(ex.toString());
			}
		}
	}

	/**
	 * #{name} μ΄ ν¬ν¨ λ λ¬Έμμ΄μ λ³ν
	 * 
	 * @param contents
	 * @param map
	 * @return
	 */
	public static String makeParamText(String content, Map<String, String> map) {
		String contents = content;
		if (map == null)
			return contents;
		if (isEmpty(contents))
			return contents;

		for (Map.Entry<String, String> elem : map.entrySet()) {
			String name = "#{" + elem.getKey() + "}";
			String value = nullCheck(elem.getValue());
			contents = contents.replace(name, value);
		}
		return contents;

	}

	/**
	 * λ¬Έμμ΄μμ μ«μλ§ κ°μ Έμ¨λ€
	 * 
	 * @param str
	 * @return
	 */
	public static String getOnlyNumberString(Object str) {
		if (str == null)
			return null;
		if (Utilities.isEmpty(str))
			return str.toString();
		return str.toString().replaceAll("[^0-9]", "");
	}

	/**
	 * μ νλ²νΈ νμμΌλ‘ λ³ν
	 * 
	 * @param str
	 * @return
	 */
	public static String getPhoneNumberFormat(Object phoneNumber) {
		if (Utilities.isEmpty(phoneNumber))
			return Utilities.nullCheck(phoneNumber);
		String numbers = Utilities.getOnlyNumberString(phoneNumber);
		if (numbers.length() == 10)
			return numbers.substring(0, 3) + "-" + numbers.substring(3, 6) + "-" + numbers.substring(6);
		else if (numbers.length() >= 11) {
			return numbers.substring(0, 3) + "-" + numbers.substring(3, 7) + "-" + numbers.substring(7);
		}
		return Utilities.nullCheck(phoneNumber);
	}

	@SuppressWarnings("rawtypes")
	public static <T> T beanToBean(Object obj, Class cls) {

		return mapToBean(beanToMap(obj), cls);

	}

	public static boolean beanToBean(Object target, Object src) {
		try {
			Map<String, Object> map = Utilities.beanToMap(src);
			Utilities.mapToBean(map, target);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> T mapToBean(Map map, Class cls) {
		return (T) objectMapper.convertValue(map, cls);

	}

	@SuppressWarnings("rawtypes")
	public static List<Map<String, Object>> convertMapList(List list) {
		if (list == null)
			return null;
		List<Map<String, Object>> arr = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < list.size(); i++) {
			arr.add(beanToMap(list.get(i)));
		}
		return arr;
	}

	@SuppressWarnings("unchecked")
	public static Map<String, Object> beanToMap(Object obj) {
		if (obj == null)
			return null;
		return (Map<String, Object>) objectMapper.convertValue(obj, Map.class);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static boolean beanToMap(Map target, Object obj) {
		if (target == null)
			return false;
		Map map = objectMapper.convertValue(obj, Map.class);
		target.putAll((Map) map);
		return true;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static boolean mapToBean(Map map, Object bean) {
		if (bean == null || map == null)
			return false;
		if (map == null || bean == null)
			return false;
		try {
			BeanUtils.populate(bean, map);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	public static List<Map<String, Object>> beanToMap(List<?> list) {
		List<Map<String, Object>> l = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < list.size(); i++) {
			l.add(beanToMap(list.get(i)));
		}
		return l;

	}

	public static String encodeBase64(byte[] value) {
		return Base64.encode(value);
	}

	static public String encodeBase64(String value) throws Exception {
		return encodeBase64(value.getBytes());
	}

	static public String decodeBase64(String val) {
//		String value = val;
		try {
			int offset = val.length() % 4;
			StringBuffer off = new StringBuffer();
			for (int i = 0; i < offset; i++) {
				off.append("=");
			}
			off.insert(0, val);
//			value += off;
			return new String(Base64.decode(off.toString()), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}

	public static byte[] decodeBase64Byte(String value) {

		return Base64.decode(value);
	}

	public static <T> T parseJson(String json, Class<T> clz) throws Exception {
		try {
			return objectMapper.readValue(json, clz);
		} catch (Exception ex) {
			return null;
		}

	}

	public static <T> T wgetJson(String jsonUrl, Class<T> clz) throws Exception {
		try {
			URL url = new URL(jsonUrl);
			return objectMapper.readValue(url, clz);
		} catch (Exception ex) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public static Map<String, Object> getJson(String json) throws Exception {
		return parseJson(json, Map.class);

	}

	public static String getJsonString(Object obj) throws Exception {
		if (obj == null)
			return null;
		if (obj instanceof String)
			return (String) obj;
		return objectMapper.writeValueAsString(obj);
	}

	public static String getJsonPString(Object obj, String callback) throws Exception {
		if (Utilities.isEmpty(callback))
			return objectMapper.writeValueAsString(obj);
		else
			return callback + "(" + objectMapper.writeValueAsString(obj) + ")";
	}

	public static Map<String, Object> getInsertResult(int count) {
		return getInsertResult(count, null);
	}

	public static Map<String, Object> getInsertResult(int count, Object data) {
		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put("insert", count);
		if (data != null)
			ret.put("result", data);
		return ret;
	}

	public static Map<String, Object> getUpdateResult(int count) {
		return getUpdateResult(count, null);
	}

	public static Map<String, Object> getUpdateResult(int count, Object data) {
		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put("update", count);
		if (data != null)
			ret.put("result", data);
		return ret;
	}

	public static Map<String, Object> getDeleteResult(int count) {
		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put("delete", count);
		return ret;
	}

	public static Map<String, Object> addResult(Map<String, Object> src, Map<String, Object> result) {
		if (src == null || result == null)
			return src;
		int insert = Utilities.parseInt(src.get("insert")) + Utilities.parseInt(result.get("insert"));
		int update = Utilities.parseInt(src.get("update")) + Utilities.parseInt(result.get("update"));
		int delete = Utilities.parseInt(src.get("delete")) + Utilities.parseInt(result.get("delete"));
		src.put("insert", insert);
		src.put("update", update);
		src.put("delete", delete);
		src.put("change", insert + update + delete);
		return src;
	}

	public static Map<String, Object> getSaveResult(List<Map<String, Object>> resultList) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("resultList", resultList);
		for (int i = 0; i < resultList.size(); i++) {
			Utilities.addResult(result, resultList.get(i));
		}
		return result;
	}

	/**
	 * μνΈν
	 * 
	 * @param text
	 * @return
	 * @throws Exception
	 */
	static public String encrypt(String text) throws Exception {

		try {
			return text;
//			return KisaSeed256.encrypt(text);
		} catch (Exception ex) {
			return text;
		}

	}

	/**
	 * λ³΅νΈν
	 * 
	 * @param text
	 * @return
	 * @throws Exception
	 */
	static public String decrypt(String text) throws Exception {
		try {
//			return text;
			return KisaSeed256.decrypt(text);
		} catch (Exception ex) {
			return text;
		}

	}

	public static String getLocationCd(String zipCd) {
		if (isEmpty(zipCd) || zipCd.length() < 2)
			return null;
		int num = parseInt(zipCd.substring(0, 2));
		if (num < 1 || num > 63)
			return "99";
		if (num < 10)
			return "11";
		else if (num < 21)
			return "41";
		else if (num < 24)
			return "28";
		else if (num < 27)
			return "42";
		else if (num < 30)
			return "43";
		else if (num < 31)
			return "36";
		else if (num < 34)
			return "43";
		else if (num < 36)
			return "30";
		else if (num < 41)
			return "47";
		else if (num < 44)
			return "27";
		else if (num < 46)
			return "31";
		else if (num < 50)
			return "26";
		else if (num < 54)
			return "48";
		else if (num < 57)
			return "45";
		else if (num < 61)
			return "46";
		else if (num < 63)
			return "29";
		else if (num < 64)
			return "50";
		return "99";

	}

	
}
