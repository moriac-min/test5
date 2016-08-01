package kr.or.kpetro.svr.service;

import kr.or.kpetro.svr.cmm.service.SearchVO;

/**COMTNGNRLMBER 기준정보*/
@SuppressWarnings("serial")
public class UserVO extends SearchVO{
	private String userId;
	private String oriUserId;
	private String userNm;
	/**사용자구분*/
	private String userSeNm;
	/**휴대폰번호*/
	private String mbtlnum;
	/**이메일주소*/
	private String emailAdres;
	private String userPassword;
	/**주소*/
	private String addr;
	/**사업장명*/
	private String cmpnmNm;
	/**비밀번호 체크*/
	private String passwordCheck;
	/**접속아이피*/
	private String userIp;
	/**dn*/
	private String crtfcDn;
	private String esntlId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	public void setUserId(Object userId) {
		if(userId instanceof String) this.userId = (String)userId;
	}
	
	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	public void setUserPassword(Object userPassword) {
		if(userPassword instanceof String) this.userPassword = (String)userPassword;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getCmpnmNm() {
		return cmpnmNm;
	}

	public void setCmpnmNm(String cmpnmNm) {
		this.cmpnmNm = cmpnmNm;
	}

	public String getPasswordCheck() {
		return passwordCheck;
	}

	public void setPasswordCheck(String passwordCheck) {
		this.passwordCheck = passwordCheck;
	}

	public String getUserIp() {
		return userIp;
	}

	public void setUserIp(String userIp) {
		this.userIp = userIp;
	}

	public String getCrtfcDn() {
		return crtfcDn;
	}

	public void setCrtfcDn(String crtfcDn) {
		this.crtfcDn = crtfcDn;
	}

	public String getUserNm() {
		return userNm;
	}

	public void setUserNm(String userNm) {
		this.userNm = userNm;
	}

	public String getUserSeNm() {
		return userSeNm;
	}

	public void setUserSeNm(String userSeNm) {
		this.userSeNm = userSeNm;
	}

	public String getMbtlnum() {
		return mbtlnum;
	}

	public void setMbtlnum(String mbtlnum) {
		this.mbtlnum = mbtlnum;
	}

	public String getEmailAdres() {
		return emailAdres;
	}

	public void setEmailAdres(String emailAdres) {
		this.emailAdres = emailAdres;
	}

	public String getOriUserId() {
		return oriUserId;
	}

	public void setOriUserId(String oriUserId) {
		this.oriUserId = oriUserId;
	}

	public String getEsntlId() {
		return esntlId;
	}

	public void setEsntlId(String esntlId) {
		this.esntlId = esntlId;
	}
}
