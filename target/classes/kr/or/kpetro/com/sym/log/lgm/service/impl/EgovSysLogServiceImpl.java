package kr.or.kpetro.com.sym.log.lgm.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import kr.or.kpetro.com.sym.log.lgm.service.EgovSysLogService;
import kr.or.kpetro.com.sym.log.lgm.service.SysLog;

import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.AbstractServiceImpl;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;

/**
 * @Class Name : EgovSysLogServiceImpl.java
 * @Description : 로그관리(시스템)를 위한 서비스 구현 클래스
 * @Modification Information
 *
 *    수정일       수정자         수정내용
 *    -------        -------     -------------------
 *    2009. 3. 11.     이삼섭
 *
 * @author 공통 서비스 개발팀 이삼섭
 * @since 2009. 3. 11.
 * @version
 * @see
 *
 */
@Service("EgovSysLogService")
public class EgovSysLogServiceImpl extends AbstractServiceImpl implements 
	EgovSysLogService {

	@Resource(name="SysLogDAO")
	private SysLogDAO sysLogDAO;	
	
    /** ID Generation */    
	@Resource(name="egovSysLogIdGnrService")
	private EgovIdGnrService egovSysLogIdGnrService;

	/**
	 * 시스템 로그정보를 생성한다.
	 * 
	 * @param SysLog
	 */
	public void logInsertSysLog(SysLog sysLog) throws Exception {
		// TODO Auto-generated method stub
		final String requstId = egovSysLogIdGnrService.getNextStringId();
		sysLog.setRequstId(requstId);
		sysLogDAO.logInsertSysLog(sysLog);    	
	}
}
