package kr.or.kpetro.svr.service;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dreamsecurity.magicline.cs.exception.MagicLineCSException;
import com.dreamsecurity.magicline.cs.util.Base64;

import kr.or.kpetro.svr.cmm.service.RequestVO;
import kr.or.kpetro.svr.web.AuthenticateController;

import egovframework.rte.psl.dataaccess.util.EgovMap;

/**라이센스 vo*/
@SuppressWarnings("serial")
public class BusinessPlaceLicenseVO  extends UserVO implements Serializable{
	protected static final Log log = LogFactory.getLog(BusinessPlaceLicenseVO.class);
	/**사업장ID*/
	private String bplcId;
	/**사용허가순번*/
	private Long lagreeSn;
	/**사용허가순번*/
	/**미들웨어구분*/
	private String mdwrSeCode;
	private String mdwrSeNm;
	/**사용시작일시*/
	private Date useBeginDate;
	/**사용종료일시*/
	private Date useExprDate;
	/**사용허가코드*/
	private String lagreeSttusCode;
	private String lagreeSttusNm;
	/**PC mac address*/
	private String macAddr;
	/**미들웨어접속상태코드*/
	private String mdwrConectSttusCode;
	/**상태수집여부*/
	private String sttusColctAt;
	/**수집일시*/
	private Date sttusColctDate;
	/**수집주기*/
	private Integer sttusColctCycle;
	/**연계서버ID*/
	private Integer cntcServerId;
	/**등록일시*/
	private Date registDate;
	/**등록자아이디*/
	private String registerId;
	/**수정자아이디*/
	private String upduserId;
	/**수정일시*/
	private Date updtDate;
	/**등록자IP*/
	private String registerIp;
	/**수정자IP*/
	private String upduserIp;
	/***암복호화키*/
	private String sessionKey;
	private String sessionIV;
	private String symmAlgo;
	/**자동전송여부*/
	private String atmcTrnsmisAt;
	private Date pcMacRegistDate;
	
	public String getSessionKey() {
		return sessionKey;
	}
	public byte[] getKey(){
		if(this.sessionKey == null) return null;
		try {
			return new Base64().decode(this.sessionKey);
		} catch (MagicLineCSException e) {
			log.debug(e);
		}
		return null;
	}
	public void setSessionKey(String _sessionKey) {
		this.sessionKey = _sessionKey;
	}
	public String getSessionIV() {
		return sessionIV;
	}
	public byte[] getIV() {
		if(this.sessionIV == null) return null;
		try {
			return new Base64().decode(this.sessionIV);
		} catch (MagicLineCSException e) {
			log.debug(e);
		}
		return null;
	}
	public void setSessionIV(String _sessionIV) {
		this.sessionIV = _sessionIV;
	}
	public String getSymmAlgo() {
		return symmAlgo;
	}
	public void setSymmAlgo(String symmAlgo) {
		this.symmAlgo = symmAlgo;
	}
	public String getMdwrSeCode() {
		return mdwrSeCode;
	}
	public void setMdwrSeCode(String mdwrSeCode) {
		this.mdwrSeCode = mdwrSeCode;
	}
	public Date getUseBeginDate() {
		return useBeginDate;
	}
	public void setUseBeginDate(Date useBeginDate) {
		this.useBeginDate = useBeginDate;
	}
	public Date getUseExprDate() {
		return useExprDate;
	}
	public void setUseExprDate(Date useExprDate) {
		this.useExprDate = useExprDate;
	}
	public String getLagreeSttusCode() {
		return lagreeSttusCode;
	}
	public void setLagreeSttusCode(String lagreeSttusCode) {
		this.lagreeSttusCode = lagreeSttusCode;
	}
	public String getMacAddr() {
		return macAddr;
	}
	public void setMacAddr(String pcMacAdres) {
		this.macAddr = pcMacAdres;
	}
	public String getMdwrConectSttusCode() {
		return mdwrConectSttusCode;
	}
	public void setMdwrConectSttusCode(String mdwrConectSttusCode) {
		this.mdwrConectSttusCode = mdwrConectSttusCode;
	}
	public String getSttusColctAt() {
		return sttusColctAt;
	}
	public void setSttusColctAt(String sttusColctAt) {
		this.sttusColctAt = sttusColctAt;
	}
	public Date getSttusColctDate() {
		return sttusColctDate;
	}
	public void setSttusColctDate(Date sttusColctDate) {
		this.sttusColctDate = sttusColctDate;
	}
	public Integer getSttusColctCycle() {
		return sttusColctCycle;
	}
	public void setSttusColctCycle(Integer sttusColctCycle) {
		this.sttusColctCycle = sttusColctCycle;
	}
	public Integer getCntcServerId() {
		return cntcServerId;
	}
	public void setCntcServerId(Integer cntcServerId) {
		this.cntcServerId = cntcServerId;
	}
	public Date getRegistDate() {
		return registDate;
	}
	public void setRegistDate(Date registDate) {
		this.registDate = registDate;
	}
	public String getRegisterId() {
		return registerId;
	}
	public void setRegisterId(String registerId) {
		this.registerId = registerId;
	}
	public String getUpduserId() {
		return upduserId;
	}
	public void setUpduserId(String upduserId) {
		this.upduserId = upduserId;
	}
	public Date getUpdtDate() {
		return updtDate;
	}
	public void setUpdtDate(Date updtDate) {
		this.updtDate = updtDate;
	}
	public String getRegisterIp() {
		return registerIp;
	}
	public void setRegisterIp(String registerIp) {
		this.registerIp = registerIp;
	}
	public String getUpduserIp() {
		return upduserIp;
	}
	public void setUpduserIp(String upduserIp) {
		this.upduserIp = upduserIp;
	}
	public String getBplcId() {
		if(bplcId!=null && bplcId.equals("")) return null;
		return bplcId;
	}
	public void setBplcId(String bplcId) {
		this.bplcId = bplcId;
	}
	public void setBplcId(Object bplcId) {
		if(bplcId !=null) this.bplcId = (String)bplcId;
		
	}
	public Long getLagreeSn() {
		if(lagreeSn!=null&&lagreeSn.equals(0L)) return null;
		return lagreeSn;
	}
	public void setLagreeSn(Long lagreeSn) {
		this.lagreeSn = lagreeSn;
	}
	public void setLagreeSn(String lagreeSn) {
		this.lagreeSn = new Long(lagreeSn);
	}
	public void setLagreeSn(Object lagreeSn) {
		if(lagreeSn instanceof String){
			try{
				this.lagreeSn = Long.parseLong((String)lagreeSn);
			}catch(Exception e){}
		}else if(lagreeSn instanceof Long){
			this.lagreeSn = (Long)lagreeSn;
		}else{}
	}
	
	/**양식구분 사업장정보*/
	public String getReportStyleSeCode(){
		
		if(this.mdwrSeCode == null) return null;
		String ret = "";
		if(this.mdwrSeCode.equals("01")){ 
			ret = "001";
		}else if(this.mdwrSeCode.equals("02") || this.mdwrSeCode.equals("03")){
			ret = "002";
		}
		return ret;
	}
	
	/**공통코드 사업장구분*/
	public String getCodeSeCode(){
		
		if(this.mdwrSeCode == null) return null;
		
		String ret = "";
		
		if(this.mdwrSeCode.equals("01")){ 
			ret = "OLT";
		}else if(this.mdwrSeCode.equals("02") || this.mdwrSeCode.equals("03")){
			ret = "AGENCY";
		}
		
		return ret;
	}
	/**파일 prefix*/
	public String getReportFilePrefix(){
		
		if(this.mdwrSeCode == null) return null;
		
		String ret = "";
		
		if(this.mdwrSeCode.equals("01")){ 
			ret = "C";
		}else if(this.mdwrSeCode.equals("02") || this.mdwrSeCode.equals("03")){
			ret = "B";
		}
		return ret;
	}
	public String getMdwrSeNm() {
		return mdwrSeNm;
	}
	public void setMdwrSeNm(String mdwrSeNm) {
		this.mdwrSeNm = mdwrSeNm;
	}
	public String getLagreeSttusNm() {
		return lagreeSttusNm;
	}
	public void setLagreeSttusNm(String lagreeSttusNm) {
		this.lagreeSttusNm = lagreeSttusNm;
	}
	public String getAtmcTrnsmisAt() {
		return atmcTrnsmisAt;
	}
	public void setAtmcTrnsmisAt(String atmcTrnsmisAt) {
		this.atmcTrnsmisAt = atmcTrnsmisAt;
	}
	public Date getPcMacRegistDate() {
		return pcMacRegistDate;
	}
	public void setPcMacRegistDate(Date pcMacRegistDate) {
		this.pcMacRegistDate = pcMacRegistDate;
	}
}
