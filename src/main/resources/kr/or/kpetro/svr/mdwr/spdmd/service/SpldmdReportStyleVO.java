package kr.or.kpetro.svr.mdwr.spdmd.service;

import java.io.Serializable;
import java.util.Date;

import kr.or.kpetro.svr.cmm.service.RequestVO;

import egovframework.rte.psl.dataaccess.util.EgovMap;

/**수급보고양식vo*/
@SuppressWarnings("serial")
public class SpldmdReportStyleVO extends SpldmdReportFxVO  implements Serializable{
	private String reportStyleSeCode;
	private Long reportStyleVerSn;
	private String seCode;
	
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
	public String getSeCode() {
		return seCode;
	}
	public void setSeCode(String seCode) {
		this.seCode = seCode;
	}
}
