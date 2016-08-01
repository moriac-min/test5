package kr.or.kpetro.svr.mdwr.service;

import java.util.List;

import egovframework.rte.psl.dataaccess.util.EgovMap;

public interface SmartUpdaterService {
	/**미들웨어서버 목록조회*/
	public List<MdwrServerVO> selectReceiveServerList() throws Exception;
	/**미들웨어 최종 버전조회*/
	public EgovMap selectFinalMiddleware();
}
