package kr.or.kpetro.svr.mdwr.init.service;

import java.util.List;

import kr.or.kpetro.com.cmm.service.impl.EgovComAbstractDAO;

import org.springframework.stereotype.Repository;

@Repository("SystemCodeDAO")
public class SystemCodeDAO extends EgovComAbstractDAO{
	public List<SystemCodeVO> selectSystemCode(SystemCodeVO vo) {
		return list("DataUpdateDAO.selectSystemCode", vo);
	}

}
