package kr.or.kpetro.std.service;

import kr.or.kpetro.com.Const;
import kr.or.kpetro.svr.cmm.service.SearchVO;

/**기준정보 공통게시판 (kr.or.kpetro.com.cop.bbs.service.BbsMasterVO) 원본소스
 * 2014.04.07 전문파라미터명에 맞도록 수정작업함
 * */

public class BbsMasterVO extends SearchVO  {
	
    private static final long serialVersionUID = 1L;

    private String bbsId;				//게시판ID    
    private String bbsNm;               //게시판명     
    private String bbsIntrcn;           //게시판소개    
    private String bbsTyCode;           //게시판유형코드  
    private String replyPosblAt;        //답급여부     
    private String answerAt;            //댓글여부     
    private String fileAtchPosblAt;     //첨부파일여부   
    private String atchPosblFileNumber; //첨부파일수    
    private String atchPosblFileSize;   //첨부파일크기   
    private String useAt;               //사용여부     
    private String frstRegisterId;      //최초등록자ID  
    private String frstRegisterPnttm;   //최초등록시점   
    private String frstRegisterIp;      //최초등록IP   
    private String lstUpdUsrId;         //최종수정자ID  
    private String lstUpdUsrPnttm;      //최종수정시점   
    private String lstUpdUsrIp;         //최종수정IP   
    private String bbsUrl = Const.BBS_NOTICE_URL;
    
	public String getBbsId() {
		return bbsId;
	}
	public void setBbsId(String bbsId) {
		this.bbsId = bbsId;
	}
	public String getBbsNm() {
		return bbsNm;
	}
	public void setBbsNm(String bbsNm) {
		this.bbsNm = bbsNm;
	}
	public String getBbsIntrcn() {
		return bbsIntrcn;
	}
	public void setBbsIntrcn(String bbsIntrcn) {
		this.bbsIntrcn = bbsIntrcn;
	}
	public String getBbsTyCode() {
		return bbsTyCode;
	}
	public void setBbsTyCode(String bbsTyCode) {
		this.bbsTyCode = bbsTyCode;
	}
	public String getReplyPosblAt() {
		return replyPosblAt;
	}
	public void setReplyPosblAt(String replyPosblAt) {
		this.replyPosblAt = replyPosblAt;
	}
	public String getAnswerAt() {
		return answerAt;
	}
	public void setAnswerAt(String answerAt) {
		this.answerAt = answerAt;
	}
	public String getFileAtchPosblAt() {
		return fileAtchPosblAt;
	}
	public void setFileAtchPosblAt(String fileAtchPosblAt) {
		this.fileAtchPosblAt = fileAtchPosblAt;
	}
	public String getAtchPosblFileNumber() {
		return atchPosblFileNumber;
	}
	public void setAtchPosblFileNumber(String atchPosblFileNumber) {
		this.atchPosblFileNumber = atchPosblFileNumber;
	}
	public String getAtchPosblFileSize() {
		return atchPosblFileSize;
	}
	public void setAtchPosblFileSize(String atchPosblFileSize) {
		this.atchPosblFileSize = atchPosblFileSize;
	}
	public String getUseAt() {
		return useAt;
	}
	public void setUseAt(String useAt) {
		this.useAt = useAt;
	}
	public String getFrstRegisterId() {
		return frstRegisterId;
	}
	public void setFrstRegisterId(String frstRegisterId) {
		this.frstRegisterId = frstRegisterId;
	}
	public String getFrstRegisterPnttm() {
		return frstRegisterPnttm;
	}
	public void setFrstRegisterPnttm(String frstRegisterPnttm) {
		this.frstRegisterPnttm = frstRegisterPnttm;
	}
	public String getFrstRegisterIp() {
		return frstRegisterIp;
	}
	public void setFrstRegisterIp(String frstRegisterIp) {
		this.frstRegisterIp = frstRegisterIp;
	}
	public String getLstUpdUsrId() {
		return lstUpdUsrId;
	}
	public void setLstUpdUsrId(String lstUpdUsrId) {
		this.lstUpdUsrId = lstUpdUsrId;
	}
	public String getLstUpdUsrPnttm() {
		return lstUpdUsrPnttm;
	}
	public void setLstUpdUsrPnttm(String lstUpdUsrPnttm) {
		this.lstUpdUsrPnttm = lstUpdUsrPnttm;
	}
	public String getLstUpdUsrIp() {
		return lstUpdUsrIp;
	}
	public void setLstUpdUsrIp(String lstUpdUsrIp) {
		this.lstUpdUsrIp = lstUpdUsrIp;
	}
	public String getBbsUrl() {
		return bbsUrl;
	}

}
