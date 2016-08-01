package kr.or.kpetro.svr.mdwr.quartz.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.AbstractServiceImpl;
import kr.or.kpetro.svr.mdwr.quartz.service.ConectingCheckService;
import kr.or.kpetro.svr.service.impl.BusinessPlaceLicenseDAO;

@Service("ConectingCheckService")
public class ConectingCheckServiceImpl extends AbstractServiceImpl implements ConectingCheckService {
	
	@Resource(name="BusinessPlaceLicenseDAO")
	private BusinessPlaceLicenseDAO businessPlaceLicenseDAO;	
	
	public void saveConectingStatus(){
		businessPlaceLicenseDAO.updateConectingStatus();
	}
}
