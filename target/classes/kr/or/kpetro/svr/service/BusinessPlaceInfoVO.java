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

/**사업장정보vo*/
@SuppressWarnings("serial")
public class BusinessPlaceInfoVO  extends UserVO implements Serializable{
	protected static final Log log = LogFactory.getLog(BusinessPlaceInfoVO.class);
	/**자동전송여부*/
	private String atmcTrnsmisAt;
	/**사업자등록번호*/
	private String bizrno;
	/**대표자명*/
	private String rprsntvNm;
	private String rprsntvBdigtIhidnum;
	private String bsnmRegistDe;
	/*사업개시일자*/
	private String bsnsBeginDe;
	/*업태*/
	private String bizcndCode;
	private String bizcndNm;
	/*업종*/
	private String indutyCode;
	private String indutyNm;
	/*사업장아이디*/
	private String bplcId;
	/*상호명*/
	private String cmpnm;
	/* 시군코드*/
	private String sigunCode;
	private String sigunNm;
	/*건물관리번호*/
	private String buldManageNo;
	/*pole 구분*/
	private String poleSeCode;
	/*알뜰업체구분 sangpyo_code*/
	private String frugEntrpsSeCode;
	
	private String frugEntrpsSeNm;
	/*좌표값*/
	private String xCrdntValue;
	private String yCrdntValue;
	/*상세주소*/
	private String detailAdres;
	/**알뜰주유소여부*/
	private String frugEntrpsSeYn;
	private String rprsntvAdres;
	public String getAtmcTrnsmisAt() {
		return atmcTrnsmisAt;
	}
	public void setAtmcTrnsmisAt(String atmcTrnsmisAt) {
		this.atmcTrnsmisAt = atmcTrnsmisAt;
	}
	public String getBizrno() {
		return bizrno;
	}
	public void setBizrno(String bizrno) {
		this.bizrno = bizrno;
	}
	public String getRprsntvNm() {
		return rprsntvNm;
	}
	public void setRprsntvNm(String rprsntvNm) {
		this.rprsntvNm = rprsntvNm;
	}
	public String getRprsntvBdigtIhidnum() {
		return rprsntvBdigtIhidnum;
	}
	public void setRprsntvBdigtIhidnum(String rprsntvBdigtIhidnum) {
		this.rprsntvBdigtIhidnum = rprsntvBdigtIhidnum;
	}
	public String getBsnmRegistDe() {
		return bsnmRegistDe;
	}
	public void setBsnmRegistDe(String bsnmRegistDe) {
		this.bsnmRegistDe = bsnmRegistDe;
	}
	public String getBsnsBeginDe() {
		return bsnsBeginDe;
	}
	public void setBsnsBeginDe(String bsnsBeginDe) {
		this.bsnsBeginDe = bsnsBeginDe;
	}
	public String getBizcndCode() {
		return bizcndCode;
	}
	public void setBizcndCode(String bizcndCode) {
		this.bizcndCode = bizcndCode;
	}
	public String getBizcndNm() {
		return bizcndNm;
	}
	public void setBizcndNm(String bizcndNm) {
		this.bizcndNm = bizcndNm;
	}
	public String getIndutyCode() {
		return indutyCode;
	}
	public void setIndutyCode(String indutyCode) {
		this.indutyCode = indutyCode;
	}
	public String getBplcId() {
		return bplcId;
	}
	public void setBplcId(String bplcId) {
		this.bplcId = bplcId;
	}
	public String getCmpnm() {
		return cmpnm;
	}
	public void setCmpnm(String cmpnm) {
		this.cmpnm = cmpnm;
	}
	public String getSigunCode() {
		return sigunCode;
	}
	public void setSigunCode(String sigunCode) {
		this.sigunCode = sigunCode;
	}
	public String getBuldManageNo() {
		return buldManageNo;
	}
	public void setBuldManageNo(String buldManageNo) {
		this.buldManageNo = buldManageNo;
	}
	public String getPoleSeCode() {
		return poleSeCode;
	}
	public void setPoleSeCode(String poleSeCode) {
		this.poleSeCode = poleSeCode;
	}
	public String getFrugEntrpsSeCode() {
		return frugEntrpsSeCode;
	}
	public void setFrugEntrpsSeCode(String frugEntrpsSeCode) {
		this.frugEntrpsSeCode = frugEntrpsSeCode;
	}
	public String getxCrdntValue() {
		return xCrdntValue;
	}
	public void setxCrdntValue(String xCrdntValue) {
		this.xCrdntValue = xCrdntValue;
	}
	public String getyCrdntValue() {
		return yCrdntValue;
	}
	public void setyCrdntValue(String yCrdntValue) {
		this.yCrdntValue = yCrdntValue;
	}
	public String getDetailAdres() {
		return detailAdres;
	}
	public void setDetailAdres(String detailAdres) {
		this.detailAdres = detailAdres;
	}
	public String getIndutyNm() {
		return indutyNm;
	}
	public void setIndutyNm(String indutyNm) {
		this.indutyNm = indutyNm;
	}
	public String getSigunNm() {
		return sigunNm;
	}
	public void setSigunNm(String sigunNm) {
		this.sigunNm = sigunNm;
	}
	public String getFrugEntrpsSeNm() {
		return frugEntrpsSeNm;
	}
	public void setFrugEntrpsSeNm(String frugEntrpsSeNm) {
		this.frugEntrpsSeNm = frugEntrpsSeNm;
	}
	public String getRprsntvAdres() {
		return rprsntvAdres;
	}
	public void setRprsntvAdres(String rprsntvAdres) {
		this.rprsntvAdres = rprsntvAdres;
	}
	public String getFrugEntrpsSeYn() {
		return frugEntrpsSeYn;
	}
	public void setFrugEntrpsSeYn(String frugEntrpsSeYn) {
		this.frugEntrpsSeYn = frugEntrpsSeYn;
	}
}
