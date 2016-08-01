package kr.or.kpetro.svr.mdwr.quartz.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

@Service("conectingCheckScheduling")
public class ConectingCheckScheduling {
	
	@Resource(name="ConectingCheckService")
	private ConectingCheckService conectingCheckService;
	
	public void conectStatusUpdate() throws Exception {
		conectingCheckService.saveConectingStatus();
	}
}
