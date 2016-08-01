package kr.or.kpetro.svr.service;
/**암복호화 객체 PKI모듈*/
import java.io.Serializable;

@SuppressWarnings("serial")
public class EncryptVO implements Serializable{
	private byte[] challenge;
	private byte[] certificate;
	private String keyMsg;
	private String sessionId;
	private byte[] sessionKey;
	private byte[] sessionIV;
	private String symmAlgo;
	private String testData;
	private String bplcId;
	private String encrypt;

	public byte[] getSessionKey() {
		return sessionKey;
	}

	public void setSessionKey(byte[] sessionKey) {
		this.sessionKey = sessionKey;
	}

	public byte[] getSessionIV() {
		return sessionIV;
	}

	public void setSessionIV(byte[] sessionIV) {
		this.sessionIV = sessionIV;
	}

	public String getSymmAlgo() {
		return symmAlgo;
	}

	public void setSymmAlgo(String symmAlgo) {
		this.symmAlgo = symmAlgo;
	}

	public byte[] getChallenge() {
		return challenge;
	}

	public void setChallenge(byte[] challenge) {
		this.challenge = challenge;
	}

	public byte[] getCertificate() {
		return certificate;
	}

	public void setCertificate(byte[] certificate) {
		this.certificate = certificate;
	}

	public String getKeyMsg() {
		return keyMsg;
	}

	public void setKeyMsg(String keyMsg) {
		this.keyMsg = keyMsg;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getTestData() {
		return testData;
	}

	public void setTestData(String testData) {
		this.testData = testData;
	}

	public String getBplcId() {
		return bplcId;
	}

	public void setBplcId(String bplcId) {
		this.bplcId = bplcId;
	}

	public String getEncrypt() {
		return encrypt;
	}

	public void setEncrypt(String encrypt) {
		this.encrypt = encrypt;
	}
	
	
}
