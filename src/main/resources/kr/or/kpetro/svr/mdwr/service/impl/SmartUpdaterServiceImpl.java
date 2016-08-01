package kr.or.kpetro.svr.mdwr.service.impl;

import java.util.List;

import javax.annotation.Resource;

import kr.or.kpetro.com.Const;
import kr.or.kpetro.svr.mdwr.service.MdwrServerVO;
import kr.or.kpetro.svr.mdwr.service.SmartUpdaterService;

import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.AbstractServiceImpl;
import egovframework.rte.psl.dataaccess.util.EgovMap;

@Service("SmartUpdaterService")
public class SmartUpdaterServiceImpl extends AbstractServiceImpl implements SmartUpdaterService{

	@Resource(name = "SmartUpdaterDAO")
    private SmartUpdaterDAO smartUpdaterDAO;

	@Override
	public List<MdwrServerVO> selectReceiveServerList() throws Exception {
		// TODO Auto-generated method stub
		return smartUpdaterDAO.selectReceiveServerList();
	}

	@Override
	public EgovMap selectFinalMiddleware() {
		// TODO Auto-generated method stub
		EgovMap map = new EgovMap();
		map.put("url", Const.MDWR_DOWN_URL);
		return smartUpdaterDAO.selectFinalMiddleware(map);
	}


}
