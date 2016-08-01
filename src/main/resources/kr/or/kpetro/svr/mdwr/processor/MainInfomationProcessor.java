package kr.or.kpetro.svr.mdwr.processor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import kr.or.kpetro.com.Const;
import kr.or.kpetro.com.cmm.EgovMessageSource;
import kr.or.kpetro.com.utl.EgovWebUtil;
import kr.or.kpetro.std.service.BbsManageService;
import kr.or.kpetro.std.service.BbsMasterService;
import kr.or.kpetro.std.service.BbsMasterVO;
import kr.or.kpetro.svr.cmm.service.RequestVO;
import kr.or.kpetro.svr.cmm.service.RequestVO.Header;
import kr.or.kpetro.svr.cmm.service.ResponseVO;
import kr.or.kpetro.svr.mdwr.init.service.SystemCodeVO;
import kr.or.kpetro.svr.service.BusinessPlaceLicenseVO;
import kr.or.kpetro.svr.service.MdwrManageService;
import kr.or.kpetro.svr.service.MiddlewareConnectingLogVO;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.ui.ModelMap;

import egovframework.rte.psl.dataaccess.util.EgovMap;

/**메인페이지 정보조회-공지시항,수급보고최근건수*/
public class MainInfomationProcessor extends ProcessorController{
	protected Log log = LogFactory.getLog(this.getClass());
	
	@Resource(name = "MdwrManageService")
	private MdwrManageService mdwrManageService;
	
	@Resource(name = "egovMessageSource")
	EgovMessageSource egovMessageSource;
	
	@Resource(name = "bbsMasterService")
	private BbsMasterService bbsMasterService;
	
	@Resource(name = "bbsManageService")
	private BbsManageService bbsManageService;

	@Override
	public ResponseVO process(RequestVO reqvo, BusinessPlaceLicenseVO licensevo, ModelMap model) throws Throwable {
		ResponseVO resvo = new ResponseVO(model, reqvo);
		Header mHeader = reqvo.getHeader();
		
		switch(mHeader.getServiceCd()){
			
			
			case 3001 :	// 최근수급보고조회
				this.findCurrentBusinessPlaceReport(resvo, licensevo);
			break;
			
			case 3002 :	// 최근공지사항조회
				this.findCurrentNoticeBoardList(reqvo, resvo);
			break;
			
			/*case 2003 :	// 최근공지사항상세조회
				this.findCurrentNoticeBoardDatail(reqvo, resvo);
			break;*/
			
			case 1001 :
				this.selectUpdateSystemList(resvo); // 시스템업데이트목록
			break;
			
			case 1002 : // 서버현재시간
				this.selectServerTime(resvo); 
			break;
			
			case 1003 : // 현재보고일정
				this.selectCurrentReportSchedule(resvo, licensevo); 
				break;
				
			case 2001 :	// 접속통계저장
				this.saveMiddlewareStatusLog(reqvo, resvo, licensevo);
			break;
			
			case 2002 :	// 로그아웃
				this.saveLogOut(reqvo, licensevo);
			break;
			
			case 2003 :	// 시스템코드조회
				this.findSystemoCode(reqvo, resvo, licensevo); 
			break;
			
			default:
				resvo.setResult(egovMessageSource.getMessage("param.user.003"));
				break;

		}
		return resvo;
	}
	
	/**로그아웃*/
	private void saveLogOut(RequestVO reqvo, BusinessPlaceLicenseVO licensevo) {
		reqvo.setUserInfo(licensevo);
		mdwrManageService.updateLogOut(licensevo);
	}

	private void selectCurrentReportSchedule(ResponseVO resvo, BusinessPlaceLicenseVO licensevo) {
		EgovMap map = mdwrManageService.selectCurrentReportFx(licensevo);
		resvo.setParam(map);
	}
	
	/**서버현재시간조회
	 * @throws Exception */
	private void selectServerTime(ResponseVO resvo) throws Exception {
		EgovMap param = new EgovMap();
		Date systemDttm = mdwrManageService.selectServerTime();
		param.put("systemDttm", systemDttm.getTime());
		
		resvo.setParam(param);
	}

	/**접속통계저장*/
	public void saveMiddlewareStatusLog(RequestVO reqvo){
		MiddlewareConnectingLogVO logvo = new MiddlewareConnectingLogVO();
		reqvo.getParamVO(logvo);
		reqvo.setUserInfo(logvo);
		
		mdwrManageService.insertMiddlewareStatusLog(logvo);
	}
	
	/**시스템업데이트목록*/
	public void selectUpdateSystemList(ResponseVO resvo){

		List list = new ArrayList<EgovMap>();
		EgovMap map = new EgovMap();
		map.put("jobTpNm","현재시간");
		map.put("serviceId", "MW0201002");
		map.put("jobNm", "현재시간을 동기화합니다.");
		map.put("updateDttm", EgovWebUtil.getCurrentTime());
		list.add(map);
		
		map = new EgovMap();
		map.put("jobTpNm","보고일정");
		map.put("serviceId", "MW0201003");
		map.put("jobNm", "보고일정을 동기화합니다.");
		map.put("updateDttm", EgovWebUtil.getCurrentTime());
		list.add(map);
		
		resvo.setList(list);
	}
	
	/**시스템코드
	 * @throws Exception */
	private void findSystemoCode(RequestVO reqvo, ResponseVO resvo, BusinessPlaceLicenseVO licensevo) throws Exception {
		SystemCodeVO codevo = new SystemCodeVO();
		reqvo.getParamVO(codevo);
		if(codevo.getCate()==null || codevo.getCate().equals("")){
			resvo.addAttribute(egovMessageSource.getMessage("code.user.001"));
		}else{
			codevo.setSeCode(licensevo.getCodeSeCode());
			resvo.setList(mdwrManageService.selectSystemCode(codevo));
		}
	}

	/**최근공지사항조
	 * @throws Exception */
	private void findCurrentNoticeBoardList(RequestVO reqvo, ResponseVO resvo) throws Exception {
		//마스터정보조회
		BbsMasterVO bbsMasterVO = new BbsMasterVO();
		bbsMasterVO = bbsMasterService.selectBbsMaster(Const.BBS_NOTICE_ID);
	
		if(bbsMasterVO!=null){
			bbsMasterVO.setFindex(0);
			bbsMasterVO.setPpage(5);
			// 게시물조회
			//final List<BbsVO> resultList = bbsManageService.selectBbsManageList(bbsMasterVO);
			resvo.setList(bbsManageService.selectBbsManageList(bbsMasterVO));
		}
	}

	/**최근수급보고조회*/
	private void findCurrentBusinessPlaceReport(ResponseVO resvo, BusinessPlaceLicenseVO licensevo) {
		licensevo.setFindex(0);
		licensevo.setPpage(5);
		resvo.setList(mdwrManageService.selectReportList(licensevo));
	}

	/**접속통계저장*/
	public void saveMiddlewareStatusLog(RequestVO reqvo, ResponseVO resvo, BusinessPlaceLicenseVO licensevo){
		MiddlewareConnectingLogVO logvo = new MiddlewareConnectingLogVO();
		reqvo.getParamVO(logvo);
		reqvo.setUserInfo(logvo);
		logvo.setLagreeSn(licensevo.getLagreeSn());
		
		mdwrManageService.insertMiddlewareStatusLog(logvo);
		
		EgovMap input = new EgovMap();
		EgovMap param = new EgovMap();
		param.put("bbsId", Const.BBS_NOTICE_ID);
		if(logvo.getLastReadDttm() == null){
			param.put("lastReadDttm", new Date(0));
		}else{
			param.put("lastReadDttm", new Date(logvo.getLastReadDttm()));
		}
		
		
		// 미조회 공지사항 건수 조회
		input.put("noticeCo", mdwrManageService.selectNotReadNoticeCount(param));
		resvo.setParam(input);
	}
}
