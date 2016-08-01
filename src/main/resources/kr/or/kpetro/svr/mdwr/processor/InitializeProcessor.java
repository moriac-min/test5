package kr.or.kpetro.svr.mdwr.processor;

import java.util.List;

import javax.annotation.Resource;

import kr.or.kpetro.com.Const;
import kr.or.kpetro.com.cmm.EgovMessageSource;
import kr.or.kpetro.svr.cmm.service.RequestVO;
import kr.or.kpetro.svr.cmm.service.RequestVO.Body;
import kr.or.kpetro.svr.cmm.service.RequestVO.Header;
import kr.or.kpetro.svr.cmm.service.ResponseVO;
import kr.or.kpetro.svr.mdwr.init.service.SystemCodeVO;
import kr.or.kpetro.svr.service.BusinessPlaceLicenseVO;
import kr.or.kpetro.svr.service.MdwrManageService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.ui.ModelMap;

import egovframework.rte.psl.dataaccess.util.EgovMap;

/**미들웨어시스템 초기화*/
public class InitializeProcessor extends ProcessorController{
	protected Log log = LogFactory.getLog(this.getClass());

	@Resource(name = "MdwrManageService")
	private MdwrManageService mdwrManageService;
	
	@Resource(name = "egovMessageSource")
	EgovMessageSource egovMessageSource;
	
	@Override
	public ResponseVO process(RequestVO reqvo, BusinessPlaceLicenseVO licensevo, ModelMap model) throws Throwable {
		ResponseVO resvo = new ResponseVO(model, reqvo);
		Header mHeader = reqvo.getHeader();
		
		switch(mHeader.getServiceCd()){
			/*case 1004 : //  접속통계저장
				this.saveMiddlewareStatusLog(reqvo);
			break;*/
			
			case 1003 : // 오류코드
				this.findSystemoCode(reqvo, resvo); 
				break;
				
			case 1004 : // 수급보고 사업장 목록 조회
				this.findSupplyDemandBusinessPlaceInfo(reqvo, resvo); 
				break;
				
			default:
				resvo.setResult(egovMessageSource.getMessage("param.user.003"));
				break;	
		}
		
		return resvo;
	}
	
	
	/**수급보고 사업장목록 조회*/
	private void findSupplyDemandBusinessPlaceInfo(RequestVO reqvo,
			ResponseVO resvo) {
		Object val =reqvo.getBody().getParam().get("bsnsNo");
		String bsnsNo = "";
		
		if(val == null){
			resvo.setResult(egovMessageSource.getMessage("bsnsNo누락"));
			return;
		}
		
		if(val instanceof String){
			bsnsNo = val.toString();
		}else if(val instanceof Long){
			bsnsNo = val.toString();
		}
		
		
		List<EgovMap> list = mdwrManageService.selectSupplyDemandBusinessPlaceInfo(bsnsNo);
		
		if(list==null||list.size()==0){
		}else{
			resvo.setList(list);
		}
	}



	/**시스템코드
	 * @throws Exception */
	private void findSystemoCode(RequestVO reqvo, ResponseVO resvo) throws Exception {
		SystemCodeVO codevo = new SystemCodeVO();
		codevo.setCate(Const.ERROR_CODE_ID);
		resvo.setList(mdwrManageService.selectSystemCode(codevo));
	}
}
