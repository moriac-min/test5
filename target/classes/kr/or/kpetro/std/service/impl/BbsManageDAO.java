package kr.or.kpetro.std.service.impl;

import java.util.List;

import kr.or.kpetro.com.cmm.service.impl.EgovComAbstractDAO;
import kr.or.kpetro.std.service.BbsMasterVO;
import kr.or.kpetro.std.service.BbsVO;

import org.springframework.stereotype.Repository;
/**
* @FileName	: BbsManageDAO.java
* @Description	: 테스트샘플 Data Access Object
* @author		: 개발팀
*	수정일			수정자		수정내용
*	============================================================================
*	
*/
@Repository("bbsManageDAO")
public class BbsManageDAO extends EgovComAbstractDAO{
	/**
	 * 게시물 리스트 조회한다.
	 * @param HashMap
	 * @return List
	 * @exception Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<BbsVO> selectBbsManageList(BbsMasterVO bbsMasterVO) throws Exception{
		return (List<BbsVO>) list("bbsManageDAO.selectBbsManageList", bbsMasterVO);
	}
}
