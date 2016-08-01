package kr.or.kpetro.svr.mdwr.service;

public class MdwrServerVO {
	private String cntcServerIp;
	private String cntcServerPort;
	
	private String mdwrVerId;
	private String mdwrDwldCours;
	private String mdwrFileSize;
	public String getCntcServerIp() {
		return cntcServerIp;
	}
	public void setCntcServerIp(String cntcServerIp) {
		this.cntcServerIp = cntcServerIp;
	}
	public String getCntcServerPort() {
		return cntcServerPort;
	}
	public void setCntcServerPort(String cntcServerPort) {
		this.cntcServerPort = cntcServerPort;
	}
	public String getMdwrVerId() {
		return mdwrVerId;
	}
	public void setMdwrVerId(String mdwrVerId) {
		this.mdwrVerId = mdwrVerId;
	}
	public String getMdwrDwldCours() {
		return mdwrDwldCours;
	}
	public void setMdwrDwldCours(String mdwrDwldCours) {
		this.mdwrDwldCours = mdwrDwldCours;
	}
	public String getMdwrFileSize() {
		return mdwrFileSize;
	}
	public void setMdwrFileSize(String mdwrFileSize) {
		this.mdwrFileSize = mdwrFileSize;
	}
	
}
