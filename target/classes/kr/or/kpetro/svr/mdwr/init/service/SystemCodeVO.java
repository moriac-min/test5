package kr.or.kpetro.svr.mdwr.init.service;
/**시스템코드-공통*/
public class SystemCodeVO {
	private String cate;
	private String code;
	private String val;
	/**미들웨어구분*/
	private String seCode;
	/**조회대상 codes*/
	private String[] cates;
	
	public String getCate() {
		return cate;
	}
	public void setCate(String cate) {
		this.cate = cate;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getVal() {
		return val;
	}
	public void setVal(String val) {
		this.val = val;
	}
	public void setSeCode(String seCode) {
		this.seCode = seCode;
	}
	public String getSeCode() {
		return seCode;
	}
	public String[] getCates() {
		return cates;
	}
	public void setCates(String[] cates) {
		this.cates = cates;
	}
	
}
