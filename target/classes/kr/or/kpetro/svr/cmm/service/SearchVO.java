package kr.or.kpetro.svr.cmm.service;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * @Class Name : ComDefaultVO.java
 * @Description : ComDefaultVO class
 * @Modification Information
 * @
 * @  수정일         수정자                   수정내용
 * @ -------    --------    ---------------------------
 * @ 2009.02.01    조재영         최초 생성
 *
 *  @author 공통서비스 개발팀 조재영
 *  @since 2009.02.01
 *  @version 1.0
 *  @see 
 *  
 */
public class SearchVO implements Serializable {
	
	/** 검색조건 */
    private String srchTp;
    
    /** 검색Keyword */
    private String srchWord;
    
    /** 검색사용여부 */
    private String srchUseYN;
    
    /** 현재페이지 */
    private Integer page;// = 1;
    
    /** 페이지갯수 */
    private Integer pitem;
    
    /** 페이지사이즈 */
    private Integer psize;// = 10;

    /** firstIndex */
    private Integer findex;// = 1;

    /** lastIndex */
    private Integer lindex;// = 1;

    /** recordCountPerPage */
    private Integer ppage;// = 10;
    
    public Integer getPpage() {
		return ppage;
	}

	public void setPpage(Integer ppage) {
		if(ppage==null)ppage=10;
		this.ppage = ppage;
	}

	/** 검색KeywordFrom */
    private String srchFr;    

	/** 검색KeywordTo */
    private String srchTo;

	public String getSrchTp() {
		return srchTp;
	}

	public void setSrchTp(String srchTp) {
		this.srchTp = srchTp;
	}

	public String getSrchWord() {
		return srchWord;
	}

	public void setSrchWord(String srchWord) {
		this.srchWord = srchWord;
	}

	public String getSrchUseYN() {
		return srchUseYN;
	}

	public void setSrchUseYN(String srchUseYN) {
		this.srchUseYN = srchUseYN;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Long page) {
		this.page = page.intValue();
	}
	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getPitem() {
		return pitem;
	}

	public void setPitem(Integer pitem) { 
		this.pitem = pitem;
	}

	public Integer getPsize() {
		return psize;
	}

	public void setPsize(Integer psize) {
		if(psize==null)psize=10;
		this.psize = psize;
	}

	public Integer getFindex() {
		return findex;
	}

	public void setFindex(Integer findex) {
		if(findex==null)findex=1;
		this.findex = findex;
	}

	public Integer getLindex() {
		return lindex;
	}

	public void setLindex(Integer lindex) {
		if(lindex==null)lindex=1;
		this.lindex = lindex;
	}

	public String getSrchFr() {
		return srchFr;
	}

	public void setSrchFr(String srchFr) {
		this.srchFr = srchFr;
	}

	public String getSrchTo() {
		return srchTo;
	}

	public void setSrchTo(String srchTo) {
		this.srchTo = srchTo;
	}  
    
	
}
