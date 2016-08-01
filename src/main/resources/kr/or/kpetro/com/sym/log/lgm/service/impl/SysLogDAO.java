package kr.or.kpetro.com.sym.log.lgm.service.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import kr.or.kpetro.com.cmm.service.impl.EgovComAbstractDAO;
import kr.or.kpetro.com.sym.log.lgm.service.SysLog;

/**
 * @Class Name : SysLogDAO.java
 * @Description : 로그관리(시스템)를 위한 데이터 접근 클래스
 * @Modification Information
 *
 *    수정일         수정자         수정내용
 *    -------        -------     -------------------
 *    2009. 3. 11.   이삼섭         최초생성
 *    2011. 7. 01.   이기하         패키지 분리(sym.log -> sym.log.lgm)
 *
 * @author 공통 서비스 개발팀 이삼섭
 * @since 2009. 3. 11.
 * @version
 * @see
 *
 */
@Repository("SysLogDAO")
public class SysLogDAO extends EgovComAbstractDAO {

	/**
	 * 시스템 로그정보를 생성한다.
	 * 
	 * @param SysLog
	 * @return
	 * @throws Exception 
	 */
	public void logInsertSysLog(SysLog sysLog) throws Exception{
		insert("SysLogDAO.logInsertSysLog", sysLog);
	}
}
