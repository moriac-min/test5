package kr.or.kpetro.svr.service.impl;

import java.util.List;

import kr.or.kpetro.com.cmm.service.impl.EgovComAbstractDAO;
import kr.or.kpetro.svr.cmm.service.RequestVO.Header;
import kr.or.kpetro.svr.service.BusinessPlaceLicenseVO;
import kr.or.kpetro.svr.service.UserVO;
import kr.or.kpetro.svr.service.MdwrVO;

import org.springframework.stereotype.Repository;

import com.dreamsecurity.magicline.cs.server.ReqMsgKeyExchange;

@Repository("BusinessPlaceLicenseDAO")
public class BusinessPlaceLicenseDAO extends EgovComAbstractDAO{

	public BusinessPlaceLicenseVO getLicenseInfo(BusinessPlaceLicenseVO businessPlaceLicenseVO) {
		// TODO Auto-generated method stub
		return (BusinessPlaceLicenseVO) selectByPk("BusinessPlaceLicenseDAO.getLicenseInfo", businessPlaceLicenseVO);
	}
	/**라이센스목록을 조회한다*/
	@SuppressWarnings("unchecked")
	public List<BusinessPlaceLicenseVO> select(UserVO _uservo) {
		return list("BusinessPlaceLicenseDAO.select", _uservo);
	}
	/**라이센스를 업데이트 한다*/
	public boolean update(UserVO _uservo){
		return update("BusinessPlaceLicenseDAO.update", _uservo)>0;
	}
	/**로그인시 발급된 세션키를 저장한다*/
	public boolean updateSession(BusinessPlaceLicenseVO _bpvo) {
		return update("BusinessPlaceLicenseDAO.updateSession", _bpvo)>0;
	}
	/**라이선스 목록*/
	public List<BusinessPlaceLicenseVO> selectBusinessPlaceLicenseList(
			BusinessPlaceLicenseVO licensevo) {
		return list("BusinessPlaceLicenseDAO.selectBusinessPlaceLicenseList", licensevo);
	}
	
	/**로그아웃*/
	public boolean updateLogOut(BusinessPlaceLicenseVO licensevo) {
		return update("BusinessPlaceLicenseDAO.updateLogOut", licensevo) >0;
	}
	/**접속상태 업데이트*/
	public void updateConectingStatus() {
		update("BusinessPlaceLicenseDAO.updateConectingStatusOn", null);
		update("BusinessPlaceLicenseDAO.updateConectingStatusOff", null);
		update("BusinessPlaceLicenseDAO.updateConectingStatusUnKnow", null);
	}
	public BusinessPlaceLicenseVO selectBusinessPlaceLoginList(BusinessPlaceLicenseVO pBplcvo) {
		return (BusinessPlaceLicenseVO) selectByPk("BusinessPlaceLicenseDAO.selectBusinessPlaceLoginList", pBplcvo);
	}

}
