package kr.or.kpetro.svr.cmm.utl;

import java.io.File;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import kr.or.kpetro.com.cmm.interceptor.LicenseCheckInterceptor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class KpetroUtil {
	protected static final Log log = LogFactory.getLog(KpetroUtil.class);
	//protected static final Log log = LogFactory.getLog(LicenseCheckInterceptor.class);
	/**
     * 2014.3.3
     * 공통 컴포넌트 utl.fcc 패키지와 Dependency제거를 위해 내부 메서드로 추가 정의함
     * 응용어플리케이션에서 고유값을 사용하기 위해 시스템에서17자리의TIMESTAMP값을 구하는 기능
     *
     * @param
     * @return Timestamp 값
     * @exception MyException
     * @see
     */
    public static String getTimeStamp() {

	String rtnStr = null;

	// 문자열로 변환하기 위한 패턴 설정(년도-월-일 시:분:초:초(자정이후 초))
	String pattern = "yyyyMMddhhmmssSSS";

	try {
	    SimpleDateFormat sdfCurrent = new SimpleDateFormat(pattern, Locale.KOREA);
	    Timestamp ts = new Timestamp(System.currentTimeMillis());

	    rtnStr = sdfCurrent.format(ts.getTime());
	} catch (Exception e) {
	}

	return rtnStr;
    }
    
    public static boolean isValidateDate(String a) {
    	if(a==null || a.trim().equals("")) return true;
		DateFormat formatter;
		try {
			formatter = new SimpleDateFormat("yyyyMMdd");
			formatter.setLenient(false);
			formatter.parse(a);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
    
    public static boolean isServiceIdValidate(String _serviceId){
    	if(_serviceId == null ) return false;
    	if(_serviceId.equals("")) return false;
    	if(_serviceId.length()==9){
    		String tmp = _serviceId.substring(2);
    		try{
    			Integer.parseInt(tmp);
    			return true;
    		}catch(Exception e){
    			log.debug(e);
    			log.debug("==serviceId==="+_serviceId);
    			return false;
    		}
    	}else{
    		return false;
    	}
    }
    
    /**
     * 업로드 Path를 체크하여 없으면 생성한다.
     *
     * @param filePath 하위에 생성할 디렉토리명
     * @throws Exception
     */
    public static String createPath(String filePath, String makePath) throws Exception {
    	// 2014.04.10 보안점검에 따른 조치
    	if(filePath==null) return "";
    	if(makePath==null) return "";
    	
    	File cFilePath = new File(filePath);

		if (!cFilePath.isDirectory()) return "";
		
		String[] arrDir = makePath.split("/");
		String preDirPath = "";
		for(int i=0;i<arrDir.length;i++){
			// 폴더생성
			if(!arrDir[i].equals("")){
				cFilePath = new File(filePath + preDirPath + arrDir[i]);
				if(!cFilePath.isDirectory()){
					cFilePath.mkdir();
				}
				preDirPath += arrDir[i] +"/";
			}
		}
		
		return filePath + makePath;
    }
}
