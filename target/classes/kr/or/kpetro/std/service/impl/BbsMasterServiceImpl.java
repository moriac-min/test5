package kr.or.kpetro.std.service.impl;

import javax.annotation.Resource;

import kr.or.kpetro.std.service.BbsMasterService;
import kr.or.kpetro.std.service.BbsMasterVO;

import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.AbstractServiceImpl;

@Service("bbsMasterService")
public class BbsMasterServiceImpl extends AbstractServiceImpl implements BbsMasterService{
	
	@Resource(name="bbsMasterDAO")
	private BbsMasterDAO bbsMasterDAO;
	public BbsMasterVO selectBbsMaster(String bbsId) throws Exception {
    	return (BbsMasterVO)bbsMasterDAO.selectBbsMaster(bbsId);
    }    
}
