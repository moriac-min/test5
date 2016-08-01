package kr.or.kpetro.svr.mdwr.spdmd.service;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import kr.or.kpetro.svr.cmm.service.RequestVO;

import egovframework.rte.psl.dataaccess.util.EgovMap;

/**파일전송 vo*/
@SuppressWarnings("serial")
public class CollectServerFileVO  extends SpldmdReportFxVO implements Serializable{
	
	private String colctYear;
	private Integer colctFileNo;
	private Integer cntcServerId;
	private String bplcId;
	private String reportStyleSeCode;
	private String recptnCoursSeCode = "01";
	private String colctFileStreCours;
	private String colctFileNm;
	private Long colctFileSize;
	private Integer lineCo;
	private String trnsferFileStreCours;
	private String trnsferFileNm;
	private String colctProcessSttusCode;
	private String colctProcessSttusNm;
	private Date colctFileProcessDate;
	private String colctProcessErrorCn;
	private String trnsferProcessSttusCode;
	private String trnsferProcessSttusCodeNm;
	private Date trnsferProcessDate;
	private String trnsferProcessErrorCn;
	/**수급보고양식*/
	private Long reportStyleVerSn;
	
	/**에러코드*/
	private Integer errorCode;
	/**에러상세내용*/
	private String errorDesc;
	/**수급보고파일문자열*/
	private String file;
	/**사용허가순번*/
	private Long lagreeSn;
	
	public String getColctYear() {
		return colctYear;
	}
	public void setColctYear(String colctYear) {
		this.colctYear = colctYear;
	}
	public Integer getColctFileNo() {
		return colctFileNo;
	}
	public void setColctFileNo(Integer colctFileNo) {
		this.colctFileNo = colctFileNo;
	}
	public void setColctFileNo(Long colctFileNo) {
		this.colctFileNo = colctFileNo.intValue();
	}
	public Integer getCntcServerId() {
		return cntcServerId;
	}
	public void setCntcServerId(Integer cntcServerId) {
		this.cntcServerId = cntcServerId;
	}
	public String getBplcId() {
		return bplcId;
	}
	public void setBplcId(String bplcId) {
		this.bplcId = bplcId;
	}
	public String getReportStyleSeCode() {
		return reportStyleSeCode;
	}
	public void setReportStyleSeCode(String reportStyleSeCode) {
		this.reportStyleSeCode = reportStyleSeCode;
	}

	public String getRecptnCoursSeCode() {
		return recptnCoursSeCode;
		//return "01"; // 미들웨어
	}
	public void setRecptnCoursSeCode(String recptnCoursSeCode) {
		this.recptnCoursSeCode = recptnCoursSeCode;
	}
	public String getColctFileStreCours() {
		return colctFileStreCours;
	}
	public void setColctFileStreCours(String colctFileStreCours) {
		this.colctFileStreCours = colctFileStreCours;
	}
	public String getColctFileNm() {
		return colctFileNm;
	}
	public void setColctFileNm(String colctFileNm) {
		this.colctFileNm = colctFileNm;
	}
	public Long getColctFileSize() {
		return colctFileSize;
	}
	public void setColctFileSize(Long colctFileSize) {
		this.colctFileSize = colctFileSize;
	}
	public Integer getLineCo() {
		return lineCo;
	}
	public void setLineCo(Integer lineCo) {
		this.lineCo = lineCo;
	}
	public String getTrnsferFileStreCours() {
		return trnsferFileStreCours;
	}
	public void setTrnsferFileStreCours(String trnsferFileStreCours) {
		this.trnsferFileStreCours = trnsferFileStreCours;
	}
	public String getTrnsferFileNm() {
		return trnsferFileNm;
	}
	public void setTrnsferFileNm(String trnsferFileNm) {
		this.trnsferFileNm = trnsferFileNm;
	}
	public String getColctProcessSttusCode() {
		if(colctProcessSttusCode==null) return ProcessCode.getCollectProcessCode(0);
		return colctProcessSttusCode;
	}
	public void setColctProcessSttusCode(String colctProcessSttusCode) {
		this.colctProcessSttusCode = colctProcessSttusCode;
	}
	public Date getColctFileProcessDate() {
		return colctFileProcessDate;
	}
	public void setColctFileProcessDate(Date colctFileProcessDate) {
		this.colctFileProcessDate = colctFileProcessDate;
	}
	public String getColctProcessErrorCn() {
		return colctProcessErrorCn;
	}
	public void setColctProcessErrorCn(String colctProcessErrorCn) {
		this.colctProcessErrorCn = colctProcessErrorCn;
	}
	public String getTrnsferProcessSttusCode() {
		return trnsferProcessSttusCode;
	}
	public void setTrnsferProcessSttusCode(String trnsferProcessSttusCode) {
		this.trnsferProcessSttusCode = trnsferProcessSttusCode;
	}
	public Date getTrnsferProcessDate() {
		return trnsferProcessDate;
	}
	public void setTrnsferProcessDate(Date trnsferProcessDate) {
		this.trnsferProcessDate = trnsferProcessDate;
	}
	public String getTrnsferProcessErrorCn() {
		return trnsferProcessErrorCn;
	}
	public void setTrnsferProcessErrorCn(String trnsferProcessErrorCn) {
		this.trnsferProcessErrorCn = trnsferProcessErrorCn;
	}
	public String getColctProcessSttusNm() {
		return colctProcessSttusNm;
	}
	public void setColctProcessSttusNm(String colctProcessSttusNm) {
		this.colctProcessSttusNm = colctProcessSttusNm;
	}
	public String getTrnsferProcessSttusCodeNm() {
		return trnsferProcessSttusCodeNm;
	}
	public void setTrnsferProcessSttusCodeNm(String trnsferProcessSttusCodeNm) {
		this.trnsferProcessSttusCodeNm = trnsferProcessSttusCodeNm;
	}
	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}
	public Integer getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}
	public void setColctProcessSttusCode(int _errorCode) {
		this.colctProcessSttusCode = ProcessCode.getCollectProcessCode(_errorCode);
		this.colctProcessErrorCn = ProcessCode.getErrorContent(_errorCode);
	}
	public Long getReportStyleVerSn() {
		return reportStyleVerSn;
	}
	public void setReportStyleVerSn(Long reportStyleVerSn) {
		this.reportStyleVerSn = reportStyleVerSn;
	}
	public String getErrorDesc() {
		return errorDesc;
	}
	public void setErrorDesc(String errorDesc) {
		this.errorDesc = errorDesc;
	}
	public Long getLagreeSn() {
		return lagreeSn;
	}
	public void setLagreeSn(Long lagreeSn) {
		this.lagreeSn = lagreeSn;
	}
}
