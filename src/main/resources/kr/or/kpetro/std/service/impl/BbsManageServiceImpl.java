package kr.or.kpetro.std.service.impl;

import java.util.List;

import javax.annotation.Resource;

import kr.or.kpetro.std.service.BbsManageService;
import kr.or.kpetro.std.service.BbsMasterVO;
import kr.or.kpetro.std.service.BbsVO;

import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.AbstractServiceImpl;
/**
* @FileName	: BbsManageServiceImpl.java
* @Description	: 테스트샘플Service Implement
*
* @author		: 개발팀
*
*	수정일			수정자		수정내용
*	============================================================================
*	
*/
@Service("bbsManageService")
public class BbsManageServiceImpl extends AbstractServiceImpl implements BbsManageService{
	
	@Resource(name="bbsManageDAO")
	private BbsManageDAO bbsManageDAO;
	
	/**
	 * 게시물 리스트 조회한다.
	 * @param HashMap
	 * @return List
	 * @exception Exception
	 */
	public List<BbsVO> selectBbsManageList(BbsMasterVO bbsMasterVO) throws Exception {
		return (List<BbsVO>)bbsManageDAO.selectBbsManageList(bbsMasterVO);
	}
}
