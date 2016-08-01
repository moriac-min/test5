package kr.or.kpetro.std.service;

import java.util.List;

public interface BbsManageService {
	List<BbsVO> selectBbsManageList(BbsMasterVO bbsMasterVO) throws Exception;
}
