package kr.or.kpetro.svr.service.impl;

import kr.or.kpetro.com.cmm.service.impl.EgovComAbstractDAO;
import kr.or.kpetro.svr.service.BusinessPlaceLicenseVO;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository("MiddlewareLoginDAO")
public class MiddlewareLoginDAO extends EgovComAbstractDAO{
	protected static final Log log = LogFactory.getLog(MiddlewareLoginDAO.class);
	public BusinessPlaceLicenseVO selectLoginCheck(BusinessPlaceLicenseVO _uservo) throws Exception {
		log.debug("====selectLoginCheck(UserVO userVO)===="+_uservo.getUserId());
		return (BusinessPlaceLicenseVO)selectByPk("MiddlewareLoginDAO.selectLoginCheck", _uservo);
	}
	
	public BusinessPlaceLicenseVO selectLoginCheckPKI(BusinessPlaceLicenseVO _uservo) throws Exception {
		return (BusinessPlaceLicenseVO)selectByPk("MiddlewareLoginDAO.selectLoginCheckPKI", _uservo);
	}
	
	/**아이디별 사업장 목록조회
	public List<BplcVO> getBusinessPlaceList(UserVO uservo) {
		// TODO Auto-generated method stub
		return list("BusinessPlaceLicenseDAO.select", uservo);
	}*/
}
