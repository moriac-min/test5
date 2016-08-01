package kr.or.kpetro.svr.mdwr.service.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.util.EgovMap;

import kr.or.kpetro.com.cmm.service.impl.EgovComAbstractDAO;
import kr.or.kpetro.svr.mdwr.service.MdwrServerVO;

@Repository("SmartUpdaterDAO")
public class SmartUpdaterDAO  extends EgovComAbstractDAO {

	public List<MdwrServerVO> selectReceiveServerList() {
		// TODO Auto-generated method stub
		return list("SmartUpdaterDAO.selectReceiveServerList", null);
	}

	public EgovMap selectFinalMiddleware(EgovMap map) {
		// TODO Auto-generated method stub
		return (EgovMap)selectByPk("SmartUpdaterDAO.selectFinalMiddleware", map);
	}

}
