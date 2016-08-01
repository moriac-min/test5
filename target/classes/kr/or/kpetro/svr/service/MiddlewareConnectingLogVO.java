package kr.or.kpetro.svr.service;
/**미들웨어접속로그*/
import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class MiddlewareConnectingLogVO extends UserVO implements Serializable{
	private String bplcId;
	private Long lagreeSn;
	private String conectSttusManageDay;
	private Integer conectSttusLogSn;
	private String conectReportDttm;
	private Long trnsmisSucessCo;
	private Long trnsmisErrorCo;
	private Long avrgSucessVeSe;
	private Long avrgErrorVeSe;
	private Date conectReportDate;
	private Long trnsmisTotalCo;
	
	private Long conectLogCreatPdSe;
	/**최종 공지사항 확인 일시-클라이언트에서 관리*/
	private Long lastReadDttm;
	
	public String getBplcId() {
		return bplcId;
	}
	public void setBplcId(String bplcId) {
		this.bplcId = bplcId;
	}
	public String getConectSttusManageDay() {
		return conectSttusManageDay;
	}
	public void setConectSttusManageDay(String conectSttusManageDay) {
		this.conectSttusManageDay = conectSttusManageDay;
	}
	public Integer getConectSttusLogSn() {
		return conectSttusLogSn;
	}
	public void setConectSttusLogSn(Integer conectSttusLogSn) {
		this.conectSttusLogSn = conectSttusLogSn;
	}
	public String getConectReportDttm() {
		return conectReportDttm;
	}
	public void setConectReportDttm(String conectReportDttm) {
		this.conectReportDttm = conectReportDttm;
	}
	public Long getTrnsmisSucessCo() {
		return trnsmisSucessCo;
	}
	public void setTrnsmisSucessCo(Long trnsmisSucessCo) {
		this.trnsmisSucessCo = trnsmisSucessCo;
	}
	public Long getTrnsmisErrorCo() {
		return trnsmisErrorCo;
	}
	public void setTrnsmisErrorCo(Long trnsmisErrorCo) {
		this.trnsmisErrorCo = trnsmisErrorCo;
	}
	public Long getConectLogCreatPdSe() {
		return conectLogCreatPdSe;
	}
	public void setConectLogCreatPdSe(Long conectLogCreatPdSe) {
		this.conectLogCreatPdSe = conectLogCreatPdSe;
	}
	public Long getLastReadDttm() {
		return lastReadDttm;
	}
	public void setLastReadDttm(Long lastReadDttm) {
		this.lastReadDttm = lastReadDttm;
	}
	public Long getAvrgSucessVeSe() {
		return avrgSucessVeSe;
	}
	public void setAvrgSucessVeSe(Long avrgSucessVeSe) {
		this.avrgSucessVeSe = avrgSucessVeSe;
	}
	public Long getAvrgErrorVeSe() {
		return avrgErrorVeSe;
	}
	public void setAvrgErrorVeSe(Long avrgErrorVeSe) {
		this.avrgErrorVeSe = avrgErrorVeSe;
	}
	public Date getConectReportDate() {
		return conectReportDate;
	}
	public void setConectReportDate(Date conectReportDate) {
		this.conectReportDate = conectReportDate;
	}
	public Long getTrnsmisTotalCo() {
		return trnsmisTotalCo;
	}
	public void setTrnsmisTotalCo(Long trnsmisTotalCo) {
		this.trnsmisTotalCo = trnsmisTotalCo;
	}
	public Long getLagreeSn() {
		return lagreeSn;
	}
	public void setLagreeSn(Long lagreeSn) {
		this.lagreeSn = lagreeSn;
	}
	
	
}
