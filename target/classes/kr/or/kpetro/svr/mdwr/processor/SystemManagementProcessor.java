package kr.or.kpetro.svr.mdwr.processor;

import javax.annotation.Resource;

import kr.or.kpetro.com.cmm.EgovMessageSource;
import kr.or.kpetro.svr.cmm.service.RequestVO;
import kr.or.kpetro.svr.cmm.service.RequestVO.Header;
import kr.or.kpetro.svr.cmm.service.ResponseVO;
import kr.or.kpetro.svr.mdwr.spdmd.service.SpldmdReportFxVO;
import kr.or.kpetro.svr.service.BusinessPlaceInfoVO;
import kr.or.kpetro.svr.service.BusinessPlaceLicenseVO;
import kr.or.kpetro.svr.service.MdwrManageService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.ui.ModelMap;

/**미들웨어 시스템관리*/
public class SystemManagementProcessor extends ProcessorController{
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
			case 1001 :	// 미들웨어 버전 목록
				this.findMiddlewareList(resvo);
			break;
			
			case 1002 :	// 미들웨어 버전 목록
				this.findBusinessPlaceLicenseList(resvo, licensevo);
			break;
			
			case 2001 :	// 접속서버목록
				this.findMiddlewareServerList(resvo);
			break;
			
			case 3001 :	// 사용자,사업자정보조회
				this.findMiddlewareUser(reqvo, resvo, licensevo);
			break;
			
			case 3002 :	// 자동업데이트여부 수정
				this.saveAutomaticTransmit(reqvo, resvo, licensevo);
			break;
			
			case 4001 :	// 수급보고 년도 조회
				this.findReportYearList(resvo);
			break;
			
			case 4002 :	// 수급보고 스케줄 조회
				this.findReportList(reqvo, resvo); 
			break;
			
			case 5001 :	// 접속로그 년도조회
				this.findConnectingYear( resvo, licensevo); 
			break;
			
			case 5002 :	// 접속로그 월조회
				this.findConnectingMonth(reqvo, resvo, licensevo); 
			break;
			
			case 5003 :	// 접속로그 조회
				this.findConnectingStats(reqvo, resvo, licensevo); 
			break;
			
			case 5004 :	// 접속로그 일별 조회
				this.findConnectingDailyLogs(reqvo, resvo, licensevo); 
			break;
			
			default:
				resvo.setResult(egovMessageSource.getMessage("param.user.003"));
				break;
		}
		
		return resvo;
	}

	/**일별접속로그 조회*/
	private void findConnectingDailyLogs(RequestVO reqvo, ResponseVO resvo,
			BusinessPlaceLicenseVO licensevo) {
		if(reqvo.getBody().getParam()==null || reqvo.getBody().getParam().get("conectSttusManageDay")==null){
			resvo.setResult(egovMessageSource.getMessage("manage.user.012"));
			return;
		}
		
		licensevo.setSrchWord(reqvo.getBody().getParam().get("conectSttusManageDay").toString());
		resvo.setList(mdwrManageService.selectConnectingDailyLogs(licensevo));
	}

	/**접속로그현황 조회*/
	private void findConnectingStats(RequestVO reqvo, ResponseVO resvo,
			BusinessPlaceLicenseVO licensevo) {
		if(reqvo.getBody().getParam()==null){
			resvo.setResult(egovMessageSource.getMessage("manage.user.007"));
			return;
		}
		
		if(reqvo.getBody().getParam().get("conectYear")==null){
			resvo.setResult(egovMessageSource.getMessage("manage.user.008"));
			return;
		}
		if(reqvo.getBody().getParam().get("conectMonth")==null){
			resvo.setResult(egovMessageSource.getMessage("manage.user.010"));
			return;
		}
		
		String conectYear = reqvo.getBody().getParam().get("conectYear").toString();
		String conectMonth = reqvo.getBody().getParam().get("conectMonth").toString();
		try{
			Integer.parseInt(conectYear);
		}catch(Exception e){
			resvo.setResult(egovMessageSource.getMessage("manage.user.006"));
			return;
		}
		
		if(conectYear.length()!=4){
			resvo.setResult(egovMessageSource.getMessage("manage.user.006"));
			return;
		}
		
		try{
			Integer.parseInt(conectMonth);
		}catch(Exception e){
			resvo.setResult(egovMessageSource.getMessage("manage.user.011"));
			return;
		}
		
		if(conectMonth.length()>2){
			resvo.setResult(egovMessageSource.getMessage("manage.user.011"));
			return;
		}
		
		if(conectMonth.length()==1){
			conectMonth = "0"+conectMonth;
		}
		
		licensevo.setSrchWord(conectYear+conectMonth);
		resvo.setList(mdwrManageService.selectConnectingStats(licensevo));
	}

	/**로그 검색월 조회*/
	private void findConnectingMonth(RequestVO reqvo, ResponseVO resvo,
			BusinessPlaceLicenseVO licensevo) {
		if(reqvo.getBody().getParam()==null){
			resvo.setResult(egovMessageSource.getMessage("manage.user.007"));
			return;
		}
		
		if(reqvo.getBody().getParam().get("conectYear")==null){
			resvo.setResult(egovMessageSource.getMessage("manage.user.008"));
			return;
		}
		
		String conectYear = reqvo.getBody().getParam().get("conectYear").toString();
		try{
			Integer.parseInt(conectYear);
		}catch(Exception e){
			resvo.setResult(egovMessageSource.getMessage("manage.user.006"));
			return;
		}
		
		if(conectYear.length()!=4){
			resvo.setResult(egovMessageSource.getMessage("manage.user.006"));
			return;
		}
		
		licensevo.setSrchWord(conectYear);
		resvo.setList(mdwrManageService.selectConnectingMonth(licensevo));
	}

	/**로그 검색년도 조회*/
	private void findConnectingYear(ResponseVO resvo,
			BusinessPlaceLicenseVO licensevo) {
		resvo.setList(mdwrManageService.selectConnectingYear(licensevo));
	}

	/**수급보고일정목록 조회*/
	private void findReportList(RequestVO reqvo, ResponseVO resvo) {
		if(reqvo.getBody().getParam()==null){
			resvo.setResult(egovMessageSource.getMessage("manage.user.004"));
			return;
		}
		if(reqvo.getBody().getParam().get("reportYear")==null ||reqvo.getBody().getParam().get("reportYear").equals("")){
			resvo.setResult(egovMessageSource.getMessage("manage.user.005"));
			return;
		}
		
		SpldmdReportFxVO paramvo = new SpldmdReportFxVO();
		paramvo.setReportYear(reqvo.getBody().getParam().get("reportYear").toString());
		
		if(reqvo.getBody().getParam().get("reportMonth")!=null){
			String srchMonth = reqvo.getBody().getParam().get("reportMonth").toString();
			try{
				Integer.parseInt(srchMonth);
			}catch(Exception e){
				resvo.setResult(egovMessageSource.getMessage("manage.user.006"));
				return;
			}
			
			if(srchMonth.length()<2){
				srchMonth = "0"+srchMonth;
			}
			paramvo.setSrchWord(srchMonth);
		}
		 resvo.setList(mdwrManageService.selectReportSchedule(paramvo));
		
	}

	/**수급보고년도조회*/
	private void findReportYearList(ResponseVO resvo) {
		resvo.setList(mdwrManageService.selectReportYearList());
	}

	/**자동전송 사용여부*/
	private void saveAutomaticTransmit(RequestVO reqvo, ResponseVO resvo,
			BusinessPlaceLicenseVO licensevo) {
		BusinessPlaceLicenseVO paramvo = new BusinessPlaceLicenseVO();
		reqvo.getParamVO(paramvo);
		
		if(paramvo.getAtmcTrnsmisAt()==null){
			// 자동전송여부가 존재하지 않습니다.
			resvo.setResult(egovMessageSource.getMessage("manage.user.002"));
			return;
		}
		
		if(paramvo.getAtmcTrnsmisAt().equals("Y") ||paramvo.getAtmcTrnsmisAt().equals("N") ){
			mdwrManageService.updateAutomaticTransmit(paramvo);
		}else{
			// 자동전송여부값이 옳바르지 않습니다.
			resvo.setResult(egovMessageSource.getMessage("manage.user.003"));
			return;
		}
	}

	/**사용자,사업자정보조회*/
	private void findMiddlewareUser(RequestVO reqvo, ResponseVO resvo,
			BusinessPlaceLicenseVO licensevo) {
		// 사용자id체크
		if(reqvo.getBody().getUserId()==null || reqvo.getBody().getUserId().equals("")){
			// 아이디정보가 존재하지 않습니다.
			resvo.setResult(egovMessageSource.getMessage("manage.user.001"));
			return;
		}
		BusinessPlaceInfoVO bplcvo = new BusinessPlaceInfoVO();
		bplcvo.setBplcId(licensevo.getBplcId());
		bplcvo.setUserId(reqvo.getBody().getUserId());
		resvo.setParam(mdwrManageService.selectMiddlewareUser(bplcvo));
	}

	/**접속서버목록*/
	private void findMiddlewareServerList(ResponseVO resvo) {
		resvo.setList(mdwrManageService.selectMiddlewareServerList());
	}

	/**미들웨어 버전 목록 조회*/
	private void findBusinessPlaceLicenseList(ResponseVO resvo,
			BusinessPlaceLicenseVO licensevo) {
		resvo.setList(mdwrManageService.selectBusinessPlaceLicenseListAll(licensevo));
	}

	/**미들웨어 목록 조회*/
	private void findMiddlewareList(ResponseVO resvo) {
		resvo.setList(mdwrManageService.selectMiddlewareList());
	}
}
