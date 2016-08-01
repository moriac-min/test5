package kr.or.kpetro.svr.web;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import kr.or.kpetro.com.Const;
import kr.or.kpetro.com.utl.EgovWebUtil;
import kr.or.kpetro.svr.cmm.service.RequestVO;
import kr.or.kpetro.svr.cmm.service.ResponseVO;
import kr.or.kpetro.svr.cmm.service.ResponseVO.Body;
import kr.or.kpetro.svr.cmm.service.ResponseVO.Header;
import kr.or.kpetro.svr.cmm.utl.KpetroUtil;
import kr.or.kpetro.svr.mdwr.processor.ProcessorController;
import kr.or.kpetro.svr.mdwr.processor.ProcessorManager;
import kr.or.kpetro.svr.mdwr.service.MdwrServerVO;
import kr.or.kpetro.svr.mdwr.service.SmartUpdaterService;
import kr.or.kpetro.svr.service.BusinessPlaceLicenseVO;
import kr.or.kpetro.svr.service.MdwrManageService;
import kr.or.kpetro.svr.service.MiddlewareConnectingLogVO;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import egovframework.rte.psl.dataaccess.util.EgovMap;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.beans.BeansException;

/**메인처리자
 * */
@Controller
public class MainController{
	protected static final Log log = LogFactory.getLog(AuthenticateController.class);
	private ApplicationContext ctx;
	@Resource(name = "SmartUpdaterService")
	private SmartUpdaterService smartUpdaterService;
	
	@Resource(name = "MdwrManageService")
	private MdwrManageService mdwrManageService;
	
	@Resource(name = "processorManager")
	private ProcessorManager processorManager;
	
	/**미들웨어 초기 공통코드
	 * @throws Throwable */
	@RequestMapping(value="/Init.do")
	public String MiddleWareInit(
			HttpServletRequest request,
			ModelMap model) throws Throwable {
		// 서비스아이디판별
		RequestVO reqvo = (RequestVO)request.getAttribute("reqvo");

		log.debug("==reqvo.getHeader().getServiceTp()=="+reqvo.getHeader().getServiceTp());
		// 서비스parse
		ProcessorController processor = processorManager.getServiceType(reqvo.getHeader().getServiceTp());
		ResponseVO resvo;
		resvo = processor.doProcess(reqvo, null, model);
		
		resvo.addAttribute();

		return Const.JSON_PAGE;
	}
	
	/**미들웨어 메인처리자
	 * @throws Throwable */
	@RequestMapping(value="/Main.do")
	public String MiddleWareMain(
			HttpServletRequest request,
			ModelMap model) throws Throwable {
		// 서비스아이디판별
		RequestVO reqvo = (RequestVO)request.getAttribute(Const.VO_REQUEST);
		BusinessPlaceLicenseVO licensevo = (BusinessPlaceLicenseVO)request.getAttribute(Const.VO_LICENSE);

		// 서비스parse
		ProcessorController processor = processorManager.getServiceType(reqvo.getHeader().getServiceTp());
		ResponseVO resvo;
		resvo = processor.doProcess(reqvo, licensevo, model);
		
		if(resvo.getBody()==null||(resvo.getBody().getParam()==null && resvo.getBody().getList()==null)){
			// 데이타암호화
			resvo.addAttribute();
		}else{
			resvo.addAttribute(licensevo.getKey(), licensevo.getIV(), licensevo.getSymmAlgo());
		}
		return Const.JSON_PAGE;
	}
	
	/**스마트업데이트 메인 처리자
	 * @throws Exception */
	@RequestMapping(value="/SMain.do")
	public String smartUpdateMain(
			HttpServletRequest request,
			ModelMap model) throws Exception {
		// 서버인증서, 챌린저 전송
		model.clear();
		
		RequestVO reqvo = (RequestVO)request.getAttribute("reqvo");
		ResponseVO resvo = new ResponseVO(model, reqvo);
		resvo.setResult("0");
		if(reqvo.getHeader().getServiceId().equals("SU0101001")){ // 서버목록
			List<MdwrServerVO> list = smartUpdaterService.selectReceiveServerList();
			resvo.setList(list);
		}else if(reqvo.getHeader().getServiceId().equals("SU0101002")){ // 최종다운로드버전
			EgovMap vo = smartUpdaterService.selectFinalMiddleware();
			resvo.setParam(vo);
		}else{
			resvo.setResult("00006");
		}
		resvo.addAttribute();
		return Const.JSON_PAGE;
	}
}
