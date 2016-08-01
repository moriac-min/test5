package kr.or.kpetro.svr.service;

import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**미들웨어vo*/
@SuppressWarnings("serial")
public class MdwrVO  implements Serializable{ 
	protected static final Log log = LogFactory.getLog(MdwrVO.class);
	
	private Integer mdwrVerId;
	private String mdwrWdtbDay;
	private Integer mdwrFileSize;
	private String mdwrDetailCn;
	public Integer getMdwrVerId() {
		return mdwrVerId;
	}
	public void setMdwrVerId(Integer mdwrVerId) {
		this.mdwrVerId = mdwrVerId;
	}
	public String getMdwrWdtbDay() {
		return mdwrWdtbDay;
	}
	public void setMdwrWdtbDay(String mdwrWdtbDay) {
		this.mdwrWdtbDay = mdwrWdtbDay;
	}
	public Integer getMdwrFileSize() {
		return mdwrFileSize;
	}
	public void setMdwrFileSize(Integer mdwrFileSize) {
		this.mdwrFileSize = mdwrFileSize;
	}
	public String getMdwrDetailCn() {
		return mdwrDetailCn;
	}
	public void setMdwrDetailCn(String mdwrDetailCn) {
		this.mdwrDetailCn = mdwrDetailCn;
	}

}