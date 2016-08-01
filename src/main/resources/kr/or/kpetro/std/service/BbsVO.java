package kr.or.kpetro.std.service;

import java.util.Date;

/**기준정보 공통게시판 (kr.or.kpetro.std.ptc.cbd.service.BbsVO) 원본소스
 * 2014.04.07 전문파라미터명에 맞도록 수정작업함
 * */
public class BbsVO {
	private Integer idx;
	private String subject;
	private String cont;
	private String registerNm;
	private Date registDate;
	private Integer hit;
	private String url;
	public Integer getIdx() {
		return idx;
	}
	public void setIdx(Integer idx) {
		this.idx = idx;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getCont() {
		return cont;
	}
	public void setCont(String cont) {
		this.cont = cont;
	}
	public String getRegisterNm() {
		return registerNm;
	}
	public void setRegisterNm(String registerNm) {
		this.registerNm = registerNm;
	}
	public Date getRegistDate() {
		return registDate;
	}
	public void setRegistDate(Date registDate) {
		this.registDate = registDate;
	}
	public Integer getHit() {
		return hit;
	}
	public void setHit(Integer hit) {
		this.hit = hit;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
}
