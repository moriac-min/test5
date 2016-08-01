package kr.or.kpetro.std.service.impl;

import kr.or.kpetro.com.cmm.service.impl.EgovComAbstractDAO;
import kr.or.kpetro.std.service.BbsMasterVO;

import org.springframework.stereotype.Repository;

@Repository("bbsMasterDAO")
public class BbsMasterDAO extends EgovComAbstractDAO{
	public BbsMasterVO selectBbsMaster(String bbsId) throws Exception {
        return (BbsMasterVO) selectByPk("bbsMasterDAO.selectBbsMaster", bbsId);
    }	

}
