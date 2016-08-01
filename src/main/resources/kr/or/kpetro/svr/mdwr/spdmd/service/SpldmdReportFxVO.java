package kr.or.kpetro.svr.mdwr.spdmd.service;

import java.io.Serializable;
import java.util.Date;

import kr.or.kpetro.svr.cmm.service.RequestVO;
import kr.or.kpetro.svr.service.UserVO;

import egovframework.rte.psl.dataaccess.util.EgovMap;

/**수급보고일정 vo*/
@SuppressWarnings("serial")
public class SpldmdReportFxVO extends UserVO  implements Serializable{
	private Integer reportTmeId;
	private String reportYear;
	private Integer reportWkno;
	private String reportWknoUseAt;
	public String reportPdBeginDay;
	public String reportPdEndDay;
	private String spldmdReportClosDay;
	private String spldmdReportLastClosDay;
	/**수급보고파일명*/
	private String fileNm;
	
	
	public Integer getReportTmeId() {
		return reportTmeId;
	}
	public void setReportTmeId(Integer reportTmeId) {
		this.reportTmeId = reportTmeId;
	}
	
	public void setReportTmeId(String reportTmeId) {
		try{this.reportTmeId = Integer.parseInt(reportTmeId);}catch(Exception e){}
		
	}
	
	public void setReportTmeId(Long reportTmeId) {
		try{this.reportTmeId = reportTmeId.intValue();}catch(Exception e){}
		
	}
	public String getReportYear() {
		return reportYear;
	}
	public void setReportYear(String reportYear) {
		this.reportYear = reportYear;
	}
	public Integer getReportWkno() {
		return reportWkno;
	}
	public void setReportWkno(Integer reportWkno) {
		this.reportWkno = reportWkno;
	}
	public String getReportWknoUseAt() {
		return reportWknoUseAt;
	}
	public void setReportWknoUseAt(String reportWknoUseAt) {
		this.reportWknoUseAt = reportWknoUseAt;
	}
	public String getReportPdBeginDay() {
		return reportPdBeginDay;
	}
	public void setReportPdBeginDay(String reportPdBeginDay) {
		this.reportPdBeginDay = reportPdBeginDay;
	}
	public String getReportPdEndDay() {
		return reportPdEndDay;
	}
	public void setReportPdEndDay(String reportPdEndDay) {
		this.reportPdEndDay = reportPdEndDay;
	}
	public String getSpldmdReportClosDay() {
		return spldmdReportClosDay;
	}
	public void setSpldmdReportClosDay(String spldmdReportClosDay) {
		this.spldmdReportClosDay = spldmdReportClosDay;
	}
	public String getSpldmdReportLastClosDay() {
		return spldmdReportLastClosDay;
	}
	public void setSpldmdReportLastClosDay(String spldmdReportLastClosDay) {
		this.spldmdReportLastClosDay = spldmdReportLastClosDay;
	}
	public String getFileNm() {
		return fileNm;
	}
	public void setFileNm(String fileNm) {
		this.fileNm = fileNm;
	}
	
}
