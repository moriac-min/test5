package kr.or.kpetro.com;

import java.nio.charset.Charset;

import kr.or.kpetro.com.cmm.service.EgovProperties;

/**
 * @Class Name : Const.java
 * @Description : 상수 선언 (Globals.java 와 성격 유사)
 * @Modification : 
 *
 *    수정일       수정자         수정내용
 *    -------        -------     -------------------
 *    2014. 2. 13.     Y
 *
 * @author 
 * @since 2014. 2. 13
 * @version 1.0
 * @see 첨부파일 저장
 *
 */
public class Const {
	/**에러페이지*/
	public final static String ERROR_PAGE = "/com/cmm/page/error";	
	/**알림(alert)페이지*/
	public final static String ALERT_PAGE = "/com/cmm/page/alert";
	/**json페이지*/
	public final static String JSON_PAGE = "jsonView";
	/**에러메시지 변수명
	public final static String SVC_RESULT = "result";
	public final static String SVC_R_MSG = "errMsg";
	public final static String SVC_R_CODE = "errCode";
	public final static String SVC_AUTH = "svcAuth";
	public final static String SVC_CHALLENGE = "challenge";
	public static final String SVC_LOGIN = "userinfo";
	public static final String SVC_BPLCID = "bplcId";*/
	/**변수명정의*/
	public final static String J_HEADER = "Header";
	public final static String J_BODY = "Body";
	public final static String J_BizPlaceID = "bizPlaceId";
	public final static String J_ServiceID = "serviceId";
	public final static String J_UserID = "userId";
	public final static String J_MacAddress = "macAddress";
	public final static String J_ResultCode = "resultCode";
	public final static String J_RESULT_SUCESS = "00000";
	
	public final static String SVC_CHALLENGE = "challenge";
	public final static String VO_LICENSE = "licensevo";
	public final static String VO_REQUEST = "reqvo";
	
	/**첨부파일 임시저장소*/
    public static final String FILE_UPLOAD_DIR_TEMP = EgovProperties.getProperty("File.Upload.Dir.Temp");
    /**첨부파일 업로드 가능 확장자*/
    public static final String FILE_UPLOAD_ALLOW = EgovProperties.getProperty("File.Upload.Allow");
    /**수그보고파일 생성 저장소*/
    public static final String FILE_UPLOAD_DIR_REPORT = EgovProperties.getProperty("File.Upload.Dir.Report");
    /**수급보고폴더*/
    public static final String DIR_RECEIVE = EgovProperties.getProperty("File.Upload.Dir.Receive");
    /**수급보고 로컬 폴더*/
    public static final String DIR_RECEIVE_DIR = EgovProperties.getProperty("File.Upload.Dir.Receive.Dir");
    public static final Charset charset = Charset.forName("KSC5601");
    //public static final Charset charset = Charset.forName("UTF-8");
    
	/**PKI*/
	public static final String PKI_WORK_DIR = EgovProperties.getProperty("pathWorkDir");
	public static final String PKI_CERT = EgovProperties.getProperty("pathSvrKmCert");
	public static final String PKI_KEY = EgovProperties.getProperty("pathSvrKmKey");
	public static final String PKI_PASS = EgovProperties.getProperty("keyPasswd");
	public static final String PKI_TRUST_ROOT1 = EgovProperties.getProperty("pathTrustRoot1");
	public static final String PKI_TRUST_ROOT2 = EgovProperties.getProperty("pathTrustRoot2");
	public static final String PKI_VERIFY = EgovProperties.getProperty("pkiVerifyCert");
	/**암호화대상 url*/
	public static final String CLEAR_TEXT_URLS = EgovProperties.getProperty("clearTextUrls");
	/**서버아이디*/
	public static final int SVR_ID = Integer.parseInt(EgovProperties.getProperty("Server.ID"));
	/**수급보고파일확장자*/
	public static final String REPORT_EXT = "ept";
	public static final String ERROR_CODE_ID = "IMW_ERR_CODE";
	public static final Integer PAGING_ITEM_SIZE = Integer.parseInt(EgovProperties.getProperty("Paging.Item.Size"));
	public static final Integer PAGING_PAGE_SIZE = Integer.parseInt(EgovProperties.getProperty("Paging.Page.Size"));
	public static final String BBS_NOTICE_ID = EgovProperties.getProperty("Board.Notice.Id");
	public static final String BBS_NOTICE_URL = EgovProperties.getProperty("Board.Notice.Url");
	public static final String MDWR_DOWN_URL = EgovProperties.getProperty("Mdwr.Down.Url");
	
}
