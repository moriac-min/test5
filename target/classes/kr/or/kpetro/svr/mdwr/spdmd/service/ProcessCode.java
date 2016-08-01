package kr.or.kpetro.svr.mdwr.spdmd.service;

import egovframework.rte.psl.dataaccess.util.EgovMap;

public class ProcessCode {
	
	public static final int ERR_FILE   	= 0x00000001; // 파일오류
	public static final int ERR_LINE   	= 0x00000002; // 라인오류
	public static final String FILE_SUCESS_CODE = "31";
	public static final String FILE_ERR_CODE = "21";
	public static final String LINE_ERR_CODE = "26";
	
	public static final String LINE_STTUS_SUCESS_CODE = "02";
	public static final String LINE_STTUS_ERR_CODE = "99";
	
	public static final int NOT_FOUND_CARDNO   	= 0x00000010 + ERR_FILE;
	public static final int INVALID_MIN_FILE_SIZE  = 0x00000020 + ERR_FILE;
	public static final int NOT_FOUND_LAST  		= 0x00000040 + ERR_FILE;
	public static final int NOT_FOUND_ITEMS  		= 0x00000080 + ERR_FILE;
	public static final int INVALID_MIN_LINE_SIZE  = 0x00000100 + ERR_FILE;
	public static final int NOT_INPUT_ITEMS  = 0x00000200 + ERR_FILE;
	public static final int NOT_FOUND_CARD  = 0x00000400 + ERR_FILE;
	
	public static final int DIFFERENT_LINE_LEN  = 0x00000800 + ERR_LINE;
	public static final int INVALID_DATE_FORMAT  = 0x00001000 + ERR_LINE;
	public static final int INVALID_NUMBER_TYPE  = 0x00002000 + ERR_LINE;
	public static final int INVALID_DECIMAL_LEN  = 0x00004000 + ERR_LINE;
	
	private static EgovMap code;
	
	
	
	/*this.code.put("10", "수신완료");
	this.code.put("21", "파일오류");
	this.code.put("26", "라인오류");
	this.code.put("31", "접수완료");*/
	public static String getCollectProcessCode(int _errorCode){
		if( _errorCode == 0) return FILE_SUCESS_CODE;
		if((_errorCode & ProcessCode.ERR_FILE)>0) return FILE_ERR_CODE; // 파일오류
		if((_errorCode & ProcessCode.ERR_LINE)>0) return LINE_ERR_CODE; // 라인오류
		
		return "10";
	}
	
	/* 라인데이타 line_data_sttus
	this.code.put("01", "미처리");
	this.code.put("02", "처리완료");
	this.code.put("99", "처리오류");*/
	public static String getLineDataSttusCode(int _errorCode){
		if( _errorCode == 0) return LINE_STTUS_SUCESS_CODE;
		if((_errorCode & ProcessCode.ERR_FILE)>0) return LINE_STTUS_ERR_CODE; // 파일오류
		if((_errorCode & ProcessCode.ERR_LINE)>0) return LINE_STTUS_ERR_CODE; // 라인오류
		
		return "02";
	}
	
	public static String getErrorContent(int _errorCode){
		if(code==null) init();
		return (String)code.get(_errorCode+"");
	}

	private static void init() {
		code = new EgovMap();
		code.put(ProcessCode.NOT_FOUND_CARDNO+"", "카드번호를 찾을 수 없습니다.");
		code.put(ProcessCode.INVALID_MIN_FILE_SIZE+"", "파일길이가 최소3(char)보다 작습니다.");
		code.put(ProcessCode.NOT_FOUND_LAST+"", "파일의 마지막라인을 찾을 수 없습니다.");
		code.put(ProcessCode.NOT_FOUND_ITEMS+"", "카드번호에 등록된 항목이 존재하지 않습니다.");
		code.put(ProcessCode.INVALID_MIN_LINE_SIZE+"", "라인길이가 최소3(char)보다 작습니다.");
		code.put(ProcessCode.NOT_INPUT_ITEMS+"", "입력된 항목이 존재하지 않습니다.");
		code.put(ProcessCode.NOT_FOUND_CARD+"", "등록되지 않은 카드정보입니다.");
		code.put(ProcessCode.DIFFERENT_LINE_LEN+"", "라인의 총 길이가 일치하지 않습니다.");
		code.put(ProcessCode.INVALID_DATE_FORMAT+"", "유효하지 않은 날짜입니다.");
		code.put(ProcessCode.INVALID_NUMBER_TYPE+"", "유효하지 않은 숫자입니다.");
		code.put(ProcessCode.INVALID_DECIMAL_LEN+"", "유효하지 않은 소숫점 자릿수입니다.");
	}
}
