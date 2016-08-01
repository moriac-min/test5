package kr.or.kpetro.svr.mdwr.spdmd.service;

import java.io.Serializable;
import java.util.Date;

import kr.or.kpetro.svr.cmm.service.RequestVO;

import egovframework.rte.psl.dataaccess.util.EgovMap;

/**수급보고카항목vo*/
@SuppressWarnings("serial")
public class StyleCardIemVO extends SpldmdReportCardVO  implements Serializable{
	private String styleCardIemSn;
	private String styleCardIemNm;
	private String styleCardIemOrdr;
	private Integer styleCardIemByteLt;
	private String styleCardColumnNm; 
	private String iemUpdtPosblAt;
	private String iemDataTyCode;
	private String iemPostngTyCode;
	private Integer iemDcmlCphr;
	private String iemReadngAt;
	private Integer iemReadngOrdr;
	private String iemChoiseGroupCodeNm;
	private String  iemDataEssntlAt;
	private Integer iemDataIndictLt;
	/**add*/
	private Object itemValue;
	
	public String getStyleCardIemSn() {
		return styleCardIemSn;
	}
	public void setStyleCardIemSn(String styleCardIemSn) {
		this.styleCardIemSn = styleCardIemSn;
	}
	public String getStyleCardIemNm() {
		return styleCardIemNm;
	}
	public void setStyleCardIemNm(String styleCardIemNm) {
		this.styleCardIemNm = styleCardIemNm;
	}
	public String getStyleCardIemOrdr() {
		return styleCardIemOrdr;
	}
	public void setStyleCardIemOrdr(String styleCardIemOrdr) {
		this.styleCardIemOrdr = styleCardIemOrdr;
	}
	public Integer getStyleCardIemByteLt() {
		return styleCardIemByteLt;
	}
	public void setStyleCardIemByteLt(Integer styleCardIemByteLt) {
		this.styleCardIemByteLt = styleCardIemByteLt;
	}
	public String getStyleCardColumnNm() {
		return styleCardColumnNm;
	}
	public void setStyleCardColumnNm(String styleCardColumnNm) {
		this.styleCardColumnNm = styleCardColumnNm;
	}
	public String getIemUpdtPosblAt() {
		return iemUpdtPosblAt;
	}
	public void setIemUpdtPosblAt(String iemUpdtPosblAt) {
		this.iemUpdtPosblAt = iemUpdtPosblAt;
	}
	public String getIemDataTyCode() {
		return iemDataTyCode;
	}
	public void setIemDataTyCode(String iemDataTyCode) {
		this.iemDataTyCode = iemDataTyCode;
	}
	public String getIemPostngTyCode() {
		return iemPostngTyCode;
	}
	public void setIemPostngTyCode(String iemPostngTyCode) {
		this.iemPostngTyCode = iemPostngTyCode;
	}
	public Integer getIemDcmlCphr() {
		return iemDcmlCphr;
	}
	public void setIemDcmlCphr(Integer iemDcmlCphr) {
		this.iemDcmlCphr = iemDcmlCphr;
	}
	public String getIemReadngAt() {
		return iemReadngAt;
	}
	public void setIemReadngAt(String iemReadngAt) {
		this.iemReadngAt = iemReadngAt;
	}
	public String getIemChoiseGroupCodeNm() {
		return iemChoiseGroupCodeNm;
	}
	public void setIemChoiseGroupCodeNm(String iemChoiseGroupCodeNm) {
		this.iemChoiseGroupCodeNm = iemChoiseGroupCodeNm;
	}
	public Object getItemValue() {
		return itemValue;
	}
	public void setItemValue(Object itemValue) {
		this.itemValue = itemValue;
	}
	public Integer getIemReadngOrdr() {
		return iemReadngOrdr;
	}
	public void setIemReadngOrdr(Integer iemReadngOrdr) {
		this.iemReadngOrdr = iemReadngOrdr;
	}
	public String getIemDataEssntlAt() {
		return iemDataEssntlAt;
	}
	public void setIemDataEssntlAt(String iemDataEssntlAt) {
		this.iemDataEssntlAt = iemDataEssntlAt;
	}
	public Integer getIemDataIndictLt() {
		return iemDataIndictLt;
	}
	public void setIemDataIndictLt(Integer iemDataIndictLt) {
		this.iemDataIndictLt = iemDataIndictLt;
	}
}
