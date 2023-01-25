package com.ceragem.batch.crm.job;

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
import org.springframework.beans.factory.annotation.Value;
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
import com.ceragem.batch.crm.model.CrmCustBasVo;
import com.ceragem.batch.crm.model.CrmCustCntplcBasVo;
import com.ceragem.batch.crm.service.CrmCustBasService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class BosContractSyncJob {

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
	CrmCustBasService custService;

	@Value("${spring.api.lab-url}")
	String labEventUrl;

	Calendar calStart = null;
	int totalSize = 0;
	int totalInsert = 0;
	int totalUpdate = 0;
	int currentPage = 0;
	int totalSkip = 0;
	int pageSize = Constants.MIG_PAGE_SIZE;
	StringBuffer sbIns = new StringBuffer();
	StringBuffer sbDup = new StringBuffer();
	StringBuffer sbSkip = new StringBuffer();

	@Bean("syncBosContractJob")
	Job syncBosContractJob() {

		log.debug("syncBosContractJob");
		return jobBuilderFactory.get("syncBosContractJob").listener(jobListener).start(stepBosContract()).build();
	}

	@Bean("stepBosContract")
	Step stepBosContract() {

		log.debug("stepBosContract");
		return stepBuilderFactory.get("stepBosContract")
				.<BosCrmContractVo, BosCrmContractVo>chunk(Constants.FETCH_COUNT).reader(crmContractReader())
//				.processor(crmContractProcess())
				.writer(crmContractWriter()).build();
	}

	@Bean("crmContractReader")
	ItemReader<BosCrmContractVo> crmContractReader() {
		return new ItemReader<BosCrmContractVo>() {

			@Override
			public BosCrmContractVo read()
					throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
				if (Utilities.isEmpty(readList)) {
					getList();
				}
				if (Utilities.isEmpty(readList)) {
					log.debug("totalSize  : " + totalSize + "");
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

		JobParameters jobParameters = jobListener.getJobExecution().getJobParameters();

		if (Utilities.isNotEmpty(jobParameters.getString("mig"))) {
			int pSize = Utilities.parseInt(jobParameters.getString("pageSize"));
			if (pSize > 0)
				pageSize = pSize;
			if (calStart == null) {
				calStart = Calendar.getInstance();
				int month = Constants.MIG_START_YEAR;
				if (Utilities.isNotEmpty(jobParameters.getString("year"))) {
					int mon = Utilities.parseInt(jobParameters.getString("year"));
					if (mon > 2000)
						month = mon;
				}

				calStart.set(month, Constants.MIG_START_MONTH, Constants.MIG_START_DAY, 0, 0, 0);
			}
			Calendar cur = Calendar.getInstance();
			if (calStart.getTimeInMillis() > cur.getTimeInMillis())
				return;

			String fromDate = Utilities.getDateString(calStart.getTime());
			Calendar calEnd = Calendar.getInstance();

			calEnd.setTime(calStart.getTime());
			calEnd.add(Calendar.MONTH, 1);
			calEnd.add(Calendar.DATE, -1);
			String toDate = Utilities.getDateString(calEnd.getTime());
			currentPage++;
			readList = service.getCrmContractList(null, null, null, fromDate, toDate, currentPage, pageSize);
			if (Utilities.isEmpty(readList)) {
				currentPage = 0;
				calStart.add(Calendar.MONTH, 1);
				getList();
				return;
			}
			if (readList.size() < pageSize) {
				currentPage = 0;
				calStart.add(Calendar.MONTH, 1);
			}
			totalSize += readList.size();
			log.debug("readList.size()  : " + readList.size() + "");
			log.debug("totalSize  : " + totalSize + "");

//			if (Utilities.isNotEmpty(list))
//				readList.addAll(list);

		} else {
			if (currentPage < 0)
				return;
			pageSize = Constants.PAGE_SIZE;
			BatchInfoBasVo batchInfo = (BatchInfoBasVo) jobListener.getJobExecution().getExecutionContext()
					.get("batchInfo");
			String toDate = Utilities.getDateString();
			String fromDate = batchInfo == null ? null : batchInfo.getLastExecDt();
			if (Utilities.isEmpty(fromDate)) {
//				fromDate = String.format("%s%s", Utilities.getDateString().substring(0, 6), "01");
				fromDate = toDate;
			} else
				fromDate = fromDate.substring(0, 8);
			currentPage++;
			readList = service.getCrmContractList(null, null, null, fromDate, toDate, currentPage, pageSize);
			if (readList != null)
				totalSize += readList.size();
			if (Utilities.isEmpty(readList) || readList.size() < pageSize) {
				currentPage = -1;
				return;
			}

		}

	}

	@Bean("crmContractWriter")
	ItemWriter<BosCrmContractVo> crmContractWriter() {
		return new ItemWriter<BosCrmContractVo>() {

			@Override
			public void write(List<? extends BosCrmContractVo> items) throws Exception {

				JobParameters jobParameters = jobListener.getJobExecution().getJobParameters();
				boolean migMode = Utilities.isNotEmpty(jobParameters.getString("mig"));
				Map<String, Object> so = new HashMap<String, Object>();
				for (int i = 0; i < items.size(); i++) {
					BosCrmContractVo vo = items.get(i);
					if (Utilities.isEmpty(vo.getItgCustNo())) {
						StringBuffer b = new StringBuffer();
						b.append("\ncntrNo : ");
						b.append(vo.getCntrNo());

						b.append("   itgCustNo : ");
						b.append(vo.getItgCustNo());

						b.append("   custNm : ");
						b.append(vo.getCustNm());
						sbSkip.append(b.toString());
						log.debug("Skip  =========> " + b.toString());
						totalSkip++;
						continue;
					}
					if (Utilities.isNotEmpty(vo.getIstCustNm()))
						vo.setIstCustNm(vo.getIstCustNm().trim());
					if (Utilities.isNotEmpty(vo.getMobNo()))
						vo.setMobNo(vo.getMobNo().trim());
					if ("null".equalsIgnoreCase(vo.getMobNo()))
						vo.setMobNo(null);

					so.put("cntrNo", vo.getCntrNo());
					int cnt = dao.selectListCount(so);
					StringBuffer b = new StringBuffer();
					b.append("\ncntrNo : ");
					b.append(vo.getCntrNo());

					b.append("   itgCustNo : ");
					b.append(vo.getItgCustNo());

					b.append("   custNm : ");
					b.append(vo.getCustNm());
					if (cnt == 0) {

						sbIns.append(b.toString());
						totalInsert += dao.insert(vo);
					} else {

						sbDup.append(b.toString());
						totalUpdate += dao.update(vo);
					}
					String custNm = vo.getIstCustNm();
					String mobileNo = vo.getMobNo();
					if (!migMode && Utilities.isNotEmpty(custNm) && Utilities.isNotEmpty(mobileNo)) {

						CrmCustBasVo cust = new CrmCustBasVo();

						cust.setCustNm(custNm);
						cust.setMphonNo(mobileNo);
						CrmCustBasVo old = custDao.selectNmNo(cust);
						String code = "";
						if (old == null) {
							cust.setDistrctCd(Utilities.getLocationCd(vo.getPostNo()));
							cust.setZipCd(vo.getPostNo());
							cust.setMshipGradeCd("001");
							cust.setMshipTypeCd("003");
							cust.setCustTypeCd("004");
							cust.setAddr1Ctnts(vo.getBassAddr());
							cust.setAddr2Ctnts(vo.getDtlAddr());
							cust.setCustDivCd("001");
							cust.setRegChlCd("BOS");
							cust.setAmdrId("BATCH");
							cust.setRegrId("BATCH");
							custDao.insert(cust);

							CrmCustCntplcBasVo contact = new CrmCustCntplcBasVo();
							contact.setItgCustNo(cust.getItgCustNo());
							contact.setCntplcTypeCd("001");
							contact.setDistrctCd(cust.getDistrctCd());
							contact.setAddr1Ctnts(cust.getAddr1Ctnts());
							contact.setAddr2Ctnts(cust.getAddr2Ctnts());
							contact.setTelNo(cust.getMphonNo());
							contact.setRegChlCd("BOS");
							contact.setAmdrId("BATCH");
							contact.setRegrId("BATCH");
							custDao.insertContact(contact);
							code = "001";
						} else {
							cust = custDao.select(old);
							if (cust != null && "002".equals(old.getCustStatusCd())) {
								custService.updateNormal(cust);
							}
							if (!"004".equals(cust.getCustTypeCd())) {
								cust.setCustTypeCd("004");
								custService.updateCustType(cust);
							}
							code = "002";

						}
						if (Utilities.isNotEmpty(code)) {
							String itgCustNo = cust.getItgCustNo();
							sendEvent(itgCustNo, code);
						}

					}
				}

				if (readList == null) {
					BatchInfoBasVo batchInfo = (BatchInfoBasVo) jobListener.getJobExecution().getExecutionContext()
							.get("batchInfo");
					StringBuffer bf = new StringBuffer();
					bf.append("total read : ");
					bf.append(totalSize);

					bf.append("\ntotal write : ");
					bf.append(totalInsert + totalUpdate);

					bf.append("\ntotal insert : ");
					bf.append(totalInsert);

					bf.append("\ntotal update : ");
					bf.append(totalUpdate);
					bf.append("\ntotal skip : ");
					bf.append(totalSkip);
					batchInfo.addErrorLog(bf.toString());
					if (!migMode && sbSkip.length() > 0) {
						batchInfo.addErrorLog("\n===================== 통합고객번호 누락 ===========================");
						batchInfo.addErrorLog(sbSkip.toString());
						batchInfo.addErrorLog("\n==============================================================");
					}
					if (!migMode && sbIns.length() > 0) {
						batchInfo.addErrorLog("\n===================== 신규 계약 ===============================");
						batchInfo.addErrorLog(sbIns.toString());
						batchInfo.addErrorLog("\n==============================================================");
					}

					if (!migMode && sbDup.length() > 0) {
						batchInfo.addErrorLog("\n===================== 정보 업데이트 ===============================");
						batchInfo.addErrorLog(sbDup.toString());
						batchInfo.addErrorLog("\n==============================================================");
					}

				}
			}
		};
	}

	public void sendEvent(String itgCustNo, String code) {

//		try {
//			service.sendCustEvent(itgCustNo);
//		} catch (Exception ex) {
//			log.debug(ex.getMessage());
//		}
//		try {
//			String url = String.format("%s%s/sync/%s", labEventUrl, itgCustNo, code);
//
//			Utilities.wget(url, null, null, false, "POST");
//
//		} catch (Exception ex) {
//			log.debug(ex.getMessage());
//		}
	}
}
