package kr.or.kpetro.svr.service.impl;

import java.util.Date;
import java.util.List;

import kr.or.kpetro.com.cmm.service.impl.EgovComAbstractDAO;
import kr.or.kpetro.svr.service.BusinessPlaceInfoVO;
import kr.or.kpetro.svr.service.BusinessPlaceLicenseVO;
import kr.or.kpetro.svr.service.MdwrServerVO;
import kr.or.kpetro.svr.service.MiddlewareConnectingLogVO;
import kr.or.kpetro.svr.service.MdwrVO;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.util.EgovMap;

@Repository("BusinessPlaceSystemDAO")
public class BusinessPlaceSystemDAO extends EgovComAbstractDAO{

	/**미들웨어에서 전송한 로그를 저정한다.*/
	public int insertMiddlewareStatusLog(MiddlewareConnectingLogVO logvo) {
		// TODO Auto-generated method stub
		return (Integer) insert("BusinessPlaceSystemDAO.insert", logvo);
	}

	public EgovMap selectCurrentReportFx(BusinessPlaceLicenseVO paramvo) {
		// TODO Auto-generated method stub
		return (EgovMap)selectByPk("BusinessPlaceSystemDAO.selectCurrentReportFx", paramvo);
	}
	/**미들웨어에서 미확인한 공지사항 갯수*/
	public int selectNotReadNoticeCount(
			EgovMap param) {
		return (Integer)selectByPk("BusinessPlaceSystemDAO.selectNotReadNoticeCount", param);
	}

	public Date selectCurrentTime() {
		return (Date)selectByPk("BusinessPlaceSystemDAO.selectCurrentTime", null);
	}
	
	/**현재년도*/
	public String selectCurrentYear() {
		return (String)selectByPk("BusinessPlaceSystemDAO.selectCurrentYear", null);
	}
	
	/**미들웨어목록을 조회한다.*/
	public List<MdwrVO> selectMiddlewareList() {
		return list("BusinessPlaceSystemDAO.selectMiddlewareList", null);
	}

	/**미들웨어서버목록을조회한다.*/
	public List<MdwrServerVO> selectMiddlewareServerList() {
		return list("BusinessPlaceSystemDAO.selectMiddlewareServerList", null);
	}

	/**로그인 사업장, 사용자 정보를 조회한다.*/
	public BusinessPlaceInfoVO selectMiddlewareUser(BusinessPlaceInfoVO bplcvo) {
		// TODO Auto-generated method stub
		return (BusinessPlaceInfoVO) selectByPk("BusinessPlaceSystemDAO.selectMiddlewareUser", bplcvo);
	}

	/**자동전송여부 업데이트*/
	public boolean updateAutomaticTransmit(BusinessPlaceLicenseVO paramvo) {
		return update("BusinessPlaceSystemDAO.updateAutomaticTransmit", paramvo)>0;
	}

	public List<EgovMap> selectConnectingYear(
			BusinessPlaceLicenseVO licensevo) {
		return list("BusinessPlaceSystemDAO.selectConnectingYear", licensevo);
	}

	public List<EgovMap> selectConnectingMonth(
			BusinessPlaceLicenseVO licensevo) {
		return list("BusinessPlaceSystemDAO.selectConnectingMonth", licensevo);
	}

	public List<MiddlewareConnectingLogVO> selectConnectingStats(
			BusinessPlaceLicenseVO licensevo) {
		return list("BusinessPlaceSystemDAO.selectConnectingStats", licensevo);
	}

	public List<MiddlewareConnectingLogVO> selectConnectingDailyLogs(
			BusinessPlaceLicenseVO licensevo) {
		return list("BusinessPlaceSystemDAO.selectConnectingDailyLogs", licensevo);
	}

	@SuppressWarnings("unchecked")
	public List<EgovMap> selectSupplyDemandBusinessPlaceInfo(String bsnsNo) {
		return list("BusinessPlaceSystemDAO.selectSupplyDemandBusinessPlaceInfo", bsnsNo);
	}

}
