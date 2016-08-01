package kr.or.kpetro.svr.service;

import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**미들웨어서버 vo*/
@SuppressWarnings("serial")
public class MdwrServerVO  implements Serializable{ 
	protected static final Log log = LogFactory.getLog(MdwrServerVO.class);
	
	private Integer cntcServerId;
	private String cntcServerNm;
	private String cntcServerIp;
	private Integer cntcServerPortNo;
	
	public Integer getCntcServerId() {
		return cntcServerId;
	}
	public void setCntcServerId(Integer cntcServerId) {
		this.cntcServerId = cntcServerId;
	}
	public String getCntcServerNm() {
		return cntcServerNm;
	}
	public void setCntcServerNm(String cntcServerNm) {
		this.cntcServerNm = cntcServerNm;
	}
	public String getCntcServerIp() {
		return cntcServerIp;
	}
	public void setCntcServerIp(String cntcServerIp) {
		this.cntcServerIp = cntcServerIp;
	}
	public Integer getCntcServerPortNo() {
		return cntcServerPortNo;
	}
	public void setCntcServerPortNo(Integer cntcServerPortNo) {
		this.cntcServerPortNo = cntcServerPortNo;
	}
	
	
}