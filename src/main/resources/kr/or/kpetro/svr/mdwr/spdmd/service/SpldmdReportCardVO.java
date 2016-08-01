package kr.or.kpetro.svr.mdwr.spdmd.service;

import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**수급보고카드 vo*/
@SuppressWarnings("serial")
public class SpldmdReportCardVO  implements Serializable{
	protected static final Log log = LogFactory.getLog(SpldmdReportCardVO.class);
	private String reportStyleSeCode;
	private Long reportStyleVerSn;
	private Integer reportStyleCardNo;
	private String reportStyleCardNm;
	private String singlRowAt;
	private Integer lineDataByteLt;
	private String opertTableNm;

	public String getReportStyleSeCode() {
		return reportStyleSeCode;
	}
	public void setReportStyleSeCode(String reportStyleSeCode) {
		this.reportStyleSeCode = reportStyleSeCode;
	}
	public Long getReportStyleVerSn() {
		return reportStyleVerSn;
	}
	public void setReportStyleVerSn(Long reportStyleVerSn) {
		this.reportStyleVerSn = reportStyleVerSn;
	}
	public Integer getReportStyleCardNo() {
		return reportStyleCardNo;
	}
	public void setReportStyleCardNo(Integer reportStyleCardNo) {
		this.reportStyleCardNo = reportStyleCardNo;
	}
	public String getReportStyleCardNm() {
		return reportStyleCardNm;
	}
	public void setReportStyleCardNm(String reportStyleCardNm) {
		this.reportStyleCardNm = reportStyleCardNm;
	}
	public String getSinglRowAt() {
		return singlRowAt;
	}
	public void setSinglRowAt(String singlRowAt) {
		this.singlRowAt = singlRowAt;
	}
	public Integer getLineDataByteLt() {
		return lineDataByteLt;
	}
	public void setLineDataByteLt(Integer lineDataByteLt) {
		this.lineDataByteLt = lineDataByteLt;
	}
	public String getOpertTableNm() {
		return opertTableNm;
	}
	public void setOpertTableNm(String opertTableNm) {
		this.opertTableNm = opertTableNm;
	}
}
