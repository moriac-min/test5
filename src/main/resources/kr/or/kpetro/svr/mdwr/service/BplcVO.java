package kr.or.kpetro.svr.mdwr.service;
/**사업장VO*/
public class BplcVO {
	/**사업장ID*/
	private String bplcId;
	private String bplcNm;
	private String bplcAddr;

	public String getBplcId() {
		return bplcId;
	}
	public void setBplcId(String bplcId) {
		this.bplcId = bplcId;
	}
	public String getBplcNm() {
		return bplcNm;
	}
	public void setBplcNm(String bplcNm) {
		this.bplcNm = bplcNm;
	}
	public String getBplcAddr() {
		return bplcAddr;
	}
	public void setBplcAddr(String bplcAddr) {
		this.bplcAddr = bplcAddr;
	}
	
}
