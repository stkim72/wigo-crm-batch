package com.ceragem.batch.crm.job;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ceragem.batch.crm.bos.model.BosCrmContractVo;
import com.ceragem.batch.crm.bos.service.BosApiService;
import com.ceragem.batch.crm.common.constant.Constants;
import com.ceragem.batch.crm.common.util.CrmJobListener;
import com.ceragem.batch.crm.common.util.Utilities;
import com.ceragem.batch.crm.dao.CrmCustBasDao;
import com.ceragem.batch.crm.dao.CrmCustBosCntrtHstDao;
import com.ceragem.batch.crm.model.BatchInfoBasVo;
import com.ceragem.batch.crm.model.CrmApiDataVo;
import com.ceragem.batch.crm.model.CrmCustBasVo;
import com.ceragem.batch.crm.service.CrmApiService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class BosContractPointJob {

	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;

	List<BosCrmContractVo> readList = null;

	@Autowired
	CrmJobListener jobListener;

	@Autowired
	BosApiService service;

	@Autowired
	CrmCustBosCntrtHstDao dao;

	@Autowired
	CrmCustBasDao custDao;

	@Autowired
	CrmApiService apiService;

	int totalSize = 0;
	int totalHome = 0;
	int totalPur = 0;
	int totalHomeS = 0;
	int totalPurS = 0;
	StringBuffer sbHome = new StringBuffer();
	StringBuffer sbHomeS = new StringBuffer();
	StringBuffer sbPur = new StringBuffer();
	StringBuffer sbPurS = new StringBuffer();

//	Map<String, CrmMshipPlcyBasVo> plcyMap = null;

	@Bean("bosPointJob")
	Job bosPointJob() {
		log.debug("bosPointJob");
		return jobBuilderFactory.get("bosPointJob").listener(jobListener).start(stepBosContractPoint()).build();
	}

	@Bean("stepBosContractPoint")
	Step stepBosContractPoint() {
		log.debug("stepBosContractPoint");
		return stepBuilderFactory.get("stepBosContractPoint")
				.<BosCrmContractVo, BosCrmContractVo>chunk(Constants.FETCH_COUNT).reader(crmContractPointReader())
//				.processor(crmContractProcess())
				.writer(crmContractPointWriter()).build();
	}

	@Bean("crmContractPointReader")
	ItemReader<BosCrmContractVo> crmContractPointReader() {
		return new ItemReader<BosCrmContractVo>() {

			@Override
			public BosCrmContractVo read()
					throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
				if (readList == null) {
					getList();
				}
				if (Utilities.isEmpty(readList)) {
					readList = null;
					if (totalSize == 0) {
						BatchInfoBasVo batchInfo = (BatchInfoBasVo) jobListener.getJobExecution().getExecutionContext()
								.get("batchInfo");
						batchInfo.addErrorLog("total read : 0");
					}
					return null;
				}
				return readList.remove(0);
			}
		};
	}

	protected void getList() throws Exception {
		if (readList != null)
			return;
		JobParameters jobParameters = jobListener.getJobExecution().getJobParameters();
		BatchInfoBasVo batchInfo = (BatchInfoBasVo) jobListener.getJobExecution().getExecutionContext()
				.get("batchInfo");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		String toDate = Utilities.getDateString(cal.getTime());
		String fromDate = batchInfo == null ? null : batchInfo.getLastExecDt();
		if (Utilities.isEmpty(fromDate)) {
//			fromDate = String.format("%s%s", Utilities.getDateString().substring(0, 6), "01");
			fromDate = Constants._OPEN_DT;
		} else
			fromDate = fromDate.substring(0, 8);
		if (Utilities.isNotEmpty(jobParameters.getString("mig"))) {
			fromDate = Constants._OPEN_DT;
		}
		Map<String, Object> param = new HashMap<>();
		param.put("ordDe", Constants._OPEN_DT);
		param.put("toDate", toDate);
		param.put("fromDate", fromDate);
		readList = dao.selectPointContractList(param);
		totalSize += readList.size();
	}

	@Bean("crmContractPointWriter")
	ItemWriter<BosCrmContractVo> crmContractPointWriter() {
		return new ItemWriter<BosCrmContractVo>() {
			@Override
			public void write(List<? extends BosCrmContractVo> items) throws Exception {
				BatchInfoBasVo batchInfo = (BatchInfoBasVo) jobListener.getJobExecution().getExecutionContext()
						.get("batchInfo");
				for (int i = 0; i < items.size(); i++) {
					CrmApiDataVo result = null;
					BosCrmContractVo item = items.get(i);

					String rcmdCust = item.getRcmdItgCustNo();

					StringBuffer b = new StringBuffer();
					b.append("\ncntrNo : ");
					b.append(item.getCntrNo());

					b.append("   itgCustNo : ");
					b.append(item.getItgCustNo());

					b.append("   custNm : ");
					b.append(item.getCustNm());

					b.append("   membership : ");
					b.append(item.isMembership());

					b.append("   rcmdCust : ");
					b.append(rcmdCust);

					if ("1202".equals(item.getSaleTyCd())) {

						if (item.isMembership()) {
							totalHome++;
							sbHome.append(b);
							result = apiService.getApiData(
									"/crm/v1.0/membership/event/" + item.getItgCustNo() + "/050/BOS/",
									Utilities.isEmpty(rcmdCust) ? null : "rcmdrCustNo=" + rcmdCust, "GET", false);
							if (!"IAR0200".equals(result.getCode())) {
								log.error("[" + item.getCntrNo() + "][/crm/v1.0/membership/event/" + item.getItgCustNo()
										+ "/050/BOS/]" + "[" + result.getCode() + "]" + result.getMessage());
								batchInfo.addErrorLog(
										"[" + item.getCntrNo() + "][/crm/v1.0/membership/event/" + item.getItgCustNo()
												+ "/050/BOS/]" + "[" + result.getCode() + "]" + result.getMessage());
							}
						} else {
							sbHomeS.append(b);
							totalHomeS++;
						}

						if (Utilities.isNotEmpty(rcmdCust)) {
							Map<String, Object> so = new HashMap<>();
							so.put("itgCustNo", rcmdCust);
							CrmCustBasVo cust = custDao.select(so);
							if (cust == null) {
								rcmdCust = null;
							} else if (!"Y".equals(cust.getMshipSbscYn())) {
								rcmdCust = null;
							} else if ("001".equals(cust.getMshipGradeCd())) {
								rcmdCust = null;
							}
						}

						if (Utilities.isNotEmpty(rcmdCust)) {

							result = apiService.getApiData(
									"/crm/v1.0/membership/event/" + item.getItgCustNo() + "/020/BOS/",
									"rcmdrCustNo=" + rcmdCust, "GET", false);
							if (!"IAR0200".equals(result.getCode())) {
								log.error("[" + item.getRcmdItgCustNo() + "][/crm/v1.0/membership/event/"
										+ item.getItgCustNo() + "/020/BOS/?rcmdrCustNo=" + item.getRcmdItgCustNo() + "]"
										+ "[" + result.getCode() + "]" + result.getMessage());
								batchInfo.addErrorLog("[" + item.getRcmdItgCustNo() + "][/crm/v1.0/membership/event/"
										+ item.getItgCustNo() + "/020/BOS/?rcmdrCustNo=" + item.getRcmdItgCustNo() + "]"
										+ "[" + result.getCode() + "]" + result.getMessage());
							}

						}
						continue;
					} else {
						Map<String, Object> point = getPoint(item); // new HashMap<>();
						if (point == null) {
							totalPurS++;
							sbPurS.append(b);
							continue;
						}
						int saleType = Utilities.parseInt(item.getSaleTyCd());
						boolean inst = "2".equals(item.getCustTyCd()) || saleType == 2401;
						String itgCustNo = item.getItgCustNo();
						if (item.isMembership() || inst) {

							if (inst) {
								itgCustNo = "";
								String custNm = item.getIstCustNm();
								String mobileNo = item.getMobNo();
								if (Utilities.isNotEmpty(custNm) && Utilities.isNotEmpty(mobileNo)) {
									CrmCustBasVo cust = new CrmCustBasVo();

									cust.setCustNm(custNm);
									cust.setMphonNo(mobileNo);
									CrmCustBasVo old = custDao.selectNmNo(cust);
									if (old != null && "Y".equals(old.getMshipSbscYn())) {
										itgCustNo = old.getItgCustNo();
									}
								}

								if (Utilities.isEmpty(itgCustNo)) {
									totalPurS++;
									sbPurS.append(b);
									sbPurS.append(" ==> 구매포인트 제공받을 설치자 정보가 존재하지 않습니다.");
									continue;
								}
								b.append("   적립자 통합고객번호 : ");
								b.append(itgCustNo);

								b.append("   적립자 : ");
								b.append(custNm);

							}
							totalPur++;
							sbPur.append(b);
							point.put("itgCustNo", itgCustNo);
							// 구매포인트
							result = apiService.getApiData("/crm/v1.0/point/deposit-amt/BOS/" + item.getCntrNo(), point,
									"POST", false);
							if (!"IAR0200".equals(result.getCode())) {
								log.error("[" + item.getCntrNo() + "][/crm/v1.0/point/deposit-amt/BOS/"
										+ item.getCntrNo() + "][" + result.getCode() + "]" + result.getMessage());
								batchInfo.addErrorLog("[" + item.getCntrNo() + "][/crm/v1.0/point/deposit-amt/BOS/"
										+ item.getCntrNo() + "][" + result.getCode() + "]" + result.getMessage());
							}
						} else {
							totalPurS++;
							sbPurS.append(b);
						}
					}

				}

				StringBuffer bf = new StringBuffer();
				bf.append("total read : ");
				bf.append(totalSize);

				bf.append("\ntotal write : ");
				bf.append(totalHome + totalHomeS + totalPur + totalPurS);

				bf.append("\ntotal 홈체험 : ");
				bf.append(totalHome);

				bf.append("\ntotal 홈체험 skip: ");
				bf.append(totalHomeS);

				bf.append("\ntotal 구매 : ");
				bf.append(totalPur);

				bf.append("\ntotal 구매 skip: ");
				bf.append(totalPurS);

				batchInfo.addErrorLog(bf.toString());
				if (sbHome.length() > 0) {
					batchInfo.addErrorLog("\n===================== 홈체험 ===========================");
					batchInfo.addErrorLog(sbHome.toString());
					batchInfo.addErrorLog("\n==============================================================");
				}
				if (sbHomeS.length() > 0) {
					batchInfo.addErrorLog("\n===================== 홈체험 Skip ===============================");
					batchInfo.addErrorLog(sbHomeS.toString());
					batchInfo.addErrorLog("\n==============================================================");
				}

				if (sbPur.length() > 0) {
					batchInfo.addErrorLog("\n===================== 구매 ===========================");
					batchInfo.addErrorLog(sbPur.toString());
					batchInfo.addErrorLog("\n==============================================================");
				}
				if (sbPurS.length() > 0) {
					batchInfo.addErrorLog("\n===================== 구매 Skip ===============================");
					batchInfo.addErrorLog(sbPurS.toString());
					batchInfo.addErrorLog("\n==============================================================");
				}

			}
		};

	}

	protected Map<String, Object> getPoint(BosCrmContractVo item) throws Exception {

		String rcmdCust = item.getRcmdItgCustNo();
		if (Utilities.isNotEmpty(rcmdCust)) {
			Map<String, Object> so = new HashMap<>();
			so.put("itgCustNo", rcmdCust);
			CrmCustBasVo cust = custDao.select(so);
			if (cust == null) {
				rcmdCust = null;
			} else if (!"Y".equals(cust.getMshipSbscYn())) {
				rcmdCust = null;
			} else if ("001".equals(cust.getMshipGradeCd())) {
				rcmdCust = null;
			}
		}

		String validPerdStaYmd = Utilities.getDateString();

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.YEAR, 2);
		cal.add(Calendar.DATE, -1);

		String validPerdEndYmd = Constants._DATE_FORMAT.format(cal.getTime());

		double amt = getAmt(item);
		if (amt == 0) {
			log.error("[" + item.getCntrNo() + "][seleType=" + item.getSaleTyCdNm() + "][amt=0][" + item.getItgCustNo()
					+ "] no amt");
			return null;
		}
		Map<String, Object> point = new HashMap<>();
		point.put("itgCustNo", item.getItgCustNo());
		point.put("storNo", item.getSaleOrgzCustCd());
		point.put("chitNo", item.getCntrNo());
		point.put("buyGodsNo", item.getBaseItemCd());

		point.put("ordrAmt", null);
		point.put("applyAmt", amt);
		point.put("payAmt", amt);

		point.put("pblsDivCd", "901");

		point.put("validPerdStaYmd", validPerdStaYmd);
		point.put("validPerdEndYmd", validPerdEndYmd);

		point.put("txn", "구매포인트");

		point.put("pblsChlCd", "BOS");

		point.put("useTypeCd", "002");

		point.put("dealNo", item.getCntrNo());

		point.put("regChlCd", "CRM");

		point.put("promNo", item.getPromNo());
		point.put("campNo", item.getCampBasNo());

		point.put("regrId", "CRM");
		point.put("amdrId", "CRM");
		point.put("rcmdrCustNo", rcmdCust);
		List<Map<String, Object>> itemList = new ArrayList<>();
		Map<String, Object> goods = new HashMap<>();

		goods.put("chitNo", item.getCntrNo());
		goods.put("buyGodsNo", item.getBaseItemCd());
		goods.put("ordrAmt", amt);
		goods.put("applyAmt", amt);
		goods.put("payAmt", amt);
		goods.put("accumYn", "Y");
		goods.put("pblsDivCd", "901");
		goods.put("rcmdrCustNo", rcmdCust);
		itemList.add(goods);
		point.put("itemList", itemList);
		point.put("messageYn", "B");
		return point;
	}

	private double getAmt(BosCrmContractVo item) {

		int saleType = Utilities.parseInt(item.getSaleTyCd());
		Double amt = item.getSetlAmt();
		Double rent = item.getRentAmt();
		Integer cntrPdVal = item.getCntrPdVal();
		if (rent == null)
			rent = 0.0;
		if (amt == null)
			return 0;
		if (cntrPdVal == null)
			cntrPdVal = 0;
		switch (saleType) {
		case 2101:
		case 2102:
		case 2103:
		case 2104:
		case 1202:
		case 2401:
			return amt;
		case 1401:
		case 1402:
		case 1403:
		case 1404:
			return amt + rent * cntrPdVal;

		default:
			return 0.0;
		}

	}

}
