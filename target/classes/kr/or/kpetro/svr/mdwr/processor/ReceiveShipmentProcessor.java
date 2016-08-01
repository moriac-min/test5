package kr.or.kpetro.svr.mdwr.processor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import kr.or.kpetro.com.Const;
import kr.or.kpetro.com.cmm.EgovMessageSource;
import kr.or.kpetro.svr.cmm.service.RequestVO;
import kr.or.kpetro.svr.cmm.service.RequestVO.Body;
import kr.or.kpetro.svr.cmm.service.RequestVO.Header;
import kr.or.kpetro.svr.cmm.service.ResponseVO;
import kr.or.kpetro.svr.cmm.utl.KpetroUtil;
import kr.or.kpetro.svr.mdwr.spdmd.service.CollectServerFileVO;
import kr.or.kpetro.svr.mdwr.spdmd.service.ProcessCode;
import kr.or.kpetro.svr.mdwr.spdmd.service.SpldmdReportCardVO;
import kr.or.kpetro.svr.mdwr.spdmd.service.SpldmdReportFxVO;
import kr.or.kpetro.svr.mdwr.spdmd.service.SpldmdReportStyleVO;
import kr.or.kpetro.svr.service.BusinessPlaceLicenseVO;
import kr.or.kpetro.svr.service.MdwrManageService;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.ui.ModelMap;

import egovframework.rte.psl.dataaccess.util.EgovMap;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

/**미들웨어시스템 초기화*/
public class ReceiveShipmentProcessor extends ProcessorController{
	protected Log log = LogFactory.getLog(this.getClass());

	@Resource(name = "MdwrManageService")
	private MdwrManageService mdwrManageService;
	
	@Resource(name = "egovMessageSource")
	EgovMessageSource egovMessageSource;
	
	@Override
	public ResponseVO process(RequestVO reqvo, BusinessPlaceLicenseVO licensevo,ModelMap model) throws Throwable {
		ResponseVO resvo = new ResponseVO(model, reqvo);
		Header mHeader = reqvo.getHeader();
		log.debug("serviceCd:"+mHeader.getServiceCd());
		switch(mHeader.getServiceCd()){
			case 1001 :	// 전송대상파일정보조회
				this.findReportFileInfo(reqvo, resvo, licensevo);
			break;
			
			case 1002 :	// 보고양식조회
				this.findReportCardItem(reqvo, resvo, licensevo);
			break;
			
			case 1003 :	// 수급보고파일전송
				this.saveTransferReport(reqvo, resvo, licensevo);
			break;
			
			case 2004 :	// 수급보고현황조회 (보고년도)
				this.findReportYear(resvo, licensevo);
			break;
			
			case 2005 :	// 수급보고현황조회 (목록)
				this.findReportList(reqvo, resvo, licensevo);
			break;
			
			case 2006 :	// 수급보고현황 상세조회
				this.findReportData(reqvo, resvo, licensevo);
			break;
			
			default:
				resvo.setResult(egovMessageSource.getMessage("param.user.003"));
				break;
		}
		
		return resvo;
	}
	/**수급보고현황 상세조회*/
	private void findReportData(RequestVO reqvo, ResponseVO resvo,
			BusinessPlaceLicenseVO licensevo) {
		CollectServerFileVO filevo = new CollectServerFileVO();
		reqvo.getParamVO(filevo);
		// 
		if(filevo.getColctYear()==null||filevo.getColctYear().equals("")) 
		{
			//spldmd.user.008=수집연도가 필요합니다.
			resvo.setResult(egovMessageSource.getMessage("spldmd.user.008"));
			return;
		}
		
		if(filevo.getColctFileNo()==null||filevo.getColctFileNo().equals(0)) 
		{
			//spldmd.user.009=수집연도가 필요합니다.
			resvo.setResult(egovMessageSource.getMessage("spldmd.user.009"));
			return;
		}
		CollectServerFileVO vo = mdwrManageService.selectReportData(filevo);
		if(vo==null){
			resvo.setResult(egovMessageSource.getMessage("spldmd.user.003")); // 수급보고파일이 존재하지 않습니다.
		}else{
			resvo.setParam(vo);
		}
		
	}
	/**수급보고현황 목록 조회*/
	private void findReportList(RequestVO reqvo,ResponseVO resvo,
			BusinessPlaceLicenseVO licensevo) {
		
		if(reqvo.getBody()!=null && reqvo.getBody().getParam()!=null){
			// 페이지번호
			if(reqvo.getBody().getParam().get("page")!=null){
				try{licensevo.setPage(Integer.parseInt(reqvo.getBody().getParam().get("page").toString()));
				}catch(Exception e){
					log.debug(e);
				}
			}
			// 보고년도
			if(reqvo.getBody().getParam().get("reportYear")!=null){
				try{licensevo.setSrchWord(reqvo.getBody().getParam().get("reportYear").toString());
				}catch(Exception e){
					log.debug(e);
				}
			}
		}
		
		// 페이징 설정
		licensevo.setPitem(Const.PAGING_ITEM_SIZE);
		licensevo.setPsize(Const.PAGING_PAGE_SIZE);
		
		PaginationInfo paginationInfo = new PaginationInfo();
		if(licensevo.getPage()==null || licensevo.getPage().equals(0)) licensevo.setPage(1);
		paginationInfo.setCurrentPageNo(licensevo.getPage());
		paginationInfo.setRecordCountPerPage(licensevo.getPitem());
		paginationInfo.setPageSize(licensevo.getPsize());
		
		licensevo.setFindex(paginationInfo.getFirstRecordIndex());
		licensevo.setLindex(paginationInfo.getLastRecordIndex());
		licensevo.setPpage(paginationInfo.getRecordCountPerPage());
		
		int cnt = mdwrManageService.selectReportCount(licensevo);
		paginationInfo.setTotalRecordCount(cnt);
		
		// param
		EgovMap param = new EgovMap();
		if(licensevo.getSrchWord()!=null){
			param.put("reportYear", licensevo.getSrchWord());
		}
		
		resvo.setList(mdwrManageService.selectReportList(licensevo));
		resvo.addPagingInfo(param, paginationInfo);
		resvo.setParam(param);
		
	}

	/**수급보고현황조회 목록*/
	private void findReportYear(ResponseVO resvo,
			BusinessPlaceLicenseVO licensevo) {
		resvo.setList(mdwrManageService.selectReportYear(licensevo));
	}

	/**수급보고파일전송
	 * @throws Exception 
	 * @throws IOException */
	private void saveTransferReport(RequestVO reqvo, ResponseVO resvo,
			BusinessPlaceLicenseVO licensevo) throws Exception  {
		CollectServerFileVO collectvo = new CollectServerFileVO();
		reqvo.getParamVO(collectvo);
		reqvo.setUserInfo(collectvo);
		
		// 사업장id
		collectvo.setBplcId(licensevo.getBplcId());
		collectvo.setLagreeSn(licensevo.getLagreeSn());
		// 양식구분코드
		collectvo.setReportStyleSeCode(licensevo.getReportStyleSeCode());
		// 서버아이디
		collectvo.setCntcServerId(Const.SVR_ID);
		// 필수입력체크
		/*if(collectvo.getReportStyleVerSn()==null || collectvo.getReportStyleVerSn().equals(0)){
			resvo.setResult(egovMessageSource.getMessage("spldmd.user.002"));
			return;
		}*/
		// 서버에서 구하기
		// 파일명체크
		if(collectvo.getFile()==null || collectvo.getFile().equals("")){
			resvo.setResult(egovMessageSource.getMessage("spldmd.user.003"));
			return;
		}
		
		if(collectvo.getColctFileNm()==null || collectvo.getColctFileNm().equals("")){
			resvo.setResult(egovMessageSource.getMessage("spldmd.user.005"));
			return;
		}
		
		// 파일명유효성체크
		int fileExtPos = collectvo.getColctFileNm().lastIndexOf(".");
		if(fileExtPos == -1){
			resvo.setResult(egovMessageSource.getMessage("spldmd.user.007"));
			return;
		}
		
		try{
			String fileExt = collectvo.getColctFileNm().substring(fileExtPos+1).toLowerCase();
			log.debug("fileExt:"+fileExt);
			if(!fileExt.equals(Const.REPORT_EXT)){
				resvo.setResult(egovMessageSource.getMessage("spldmd.user.007"));
				return;
			}
		}catch(Exception e){
			log.debug(e);
			resvo.setResult(egovMessageSource.getMessage("spldmd.user.007"));
			return;
		}
		
		
		// 회차정보
		if(collectvo.getReportTmeId()==null || collectvo.getReportTmeId().equals(0)){
			resvo.setResult(egovMessageSource.getMessage("spldmd.user.001"));
			return;
		}
		SpldmdReportStyleVO fxvo = mdwrManageService.selectReportStyle(collectvo);
		if(fxvo.getReportStyleVerSn()==null || fxvo.getReportStyleVerSn()==0){
			resvo.setResult(egovMessageSource.getMessage("spldmd.user.006"));
			return;
		}
		
		collectvo.setReportStyleVerSn(fxvo.getReportStyleVerSn());
		
		// 파일저장
		FileOutputStream fos = null;
		String fileDir = Const.DIR_RECEIVE_DIR + fxvo.getReportYear() + "/" +fxvo.getReportWkno() + "/";
		String filePath = Const.DIR_RECEIVE;
		
		
		// 디렉토리 생성
		KpetroUtil.createPath(filePath, fileDir);
		
		String fileNm = KpetroUtil.getTimeStamp()+"_" + collectvo.getColctFileNm().substring(0, collectvo.getColctFileNm().length()-Const.REPORT_EXT.length()-1);// 
		
		// rename
		File file = new File(filePath+fileDir+fileNm+"."+Const.REPORT_EXT);
		int nFileSeq = 1;
		String newFileNm = fileNm+"."+Const.REPORT_EXT;
		
		while(file.exists()){
			newFileNm = fileNm+"_"+(nFileSeq++)+"."+Const.REPORT_EXT;
			file = new File(filePath+fileDir +newFileNm);
		}
		
		//filePath = file.getAbsolutePath();
		try{
			
			fos = new FileOutputStream(file.getAbsolutePath());
			fos.write(collectvo.getFile().getBytes(Const.charset));
			//fos.write(collectvo.getFile().getBytes(Charset.forName("UTF-8")));
			collectvo.setColctFileSize(new Long(collectvo.getFile().getBytes(Const.charset).length));
			collectvo.setColctFileStreCours(fileDir + newFileNm);
			fos.close();
		}catch(IOException e){
			log.debug(e);
			resvo.setResult(egovMessageSource.getMessage("spldmd.user.003"));
			// 파일업로드 오류
			collectvo.setColctProcessSttusCode(ProcessCode.ERR_FILE);
			collectvo.setColctProcessErrorCn(e.toString());
			mdwrManageService.saveServerFile(collectvo);
			return;
		}
		
		// 저장
		CollectServerFileVO retvo = mdwrManageService.saveTransferReport(file.getAbsolutePath(), collectvo);
		if(!collectvo.getColctProcessSttusCode().equals(ProcessCode.FILE_SUCESS_CODE)){
			retvo.setColctProcessSttusCode(collectvo.getColctProcessSttusCode());
		}
		resvo.setParam(retvo);
	}

	/**보고양식조회*/
	private void findReportCardItem(RequestVO reqvo, ResponseVO resvo, BusinessPlaceLicenseVO licensevo) {
		SpldmdReportCardVO cardvo = new SpldmdReportCardVO();
		reqvo.getParamVO(cardvo);
		cardvo.setReportStyleSeCode(licensevo.getReportStyleSeCode());
		
		if(cardvo.getReportStyleVerSn()==null || cardvo.getReportStyleVerSn().equals(0)){
			resvo.addAttribute(egovMessageSource.getMessage("spldmd.user.001"));
			return;
		}else{
			// 카드조회
			resvo.setList(mdwrManageService.selectReportCardList(cardvo));
			// 항목조회
			resvo.setList2(mdwrManageService.selectReportItemList(cardvo));
		}
		
	}

	/**수급보고 대상파일 정보조회*/
	private void findReportFileInfo(RequestVO reqvo, ResponseVO resvo, BusinessPlaceLicenseVO licensevo) {
		List<EgovMap> list = reqvo.getBody().getList();
		List<SpldmdReportStyleVO> invalidateList = new ArrayList();
		
		if(list!=null && list.size()>0){
			List<SpldmdReportStyleVO> reportlist = new ArrayList();
			for(int i=0;i<list.size();i++){
				EgovMap map = list.get(i);
				SpldmdReportStyleVO reportvo = new SpldmdReportStyleVO();
				String tmp = (String)map.get("fileNm");
				reportvo.setFileNm(tmp);
				
				if(tmp==null || tmp.equals("")){
					invalidateList.add(reportvo);
				}else{
					if(tmp.length()==24){
						if(tmp.substring(0,1).equals(licensevo.getReportFilePrefix()) && tmp.substring(21,24).equals(Const.REPORT_EXT) ){
							reportvo.setReportPdBeginDay(tmp.substring(1,9));
							reportvo.setReportPdEndDay(tmp.substring(9,17));
							reportlist.add(reportvo);
						}else{
							invalidateList.add(reportvo);
						}
					}else{
						invalidateList.add(reportvo);
					}
				}
			}
			// List<SpldmdReportStyleVO>
			
			
			if(reportlist.size()==0){
				resvo.setList(invalidateList);
				
			}else{
				List<SpldmdReportStyleVO> retList=  mdwrManageService.selectReportFileInfo(reportlist, licensevo.getReportStyleSeCode());
				if(retList==null){
					retList = new ArrayList<SpldmdReportStyleVO>();
				}
				
				if(invalidateList!=null && invalidateList.size()>0){
					for(int i=0;i<invalidateList.size();i++){
						retList.add(invalidateList.get(i));
					}
				}
				resvo.setList(retList);
			}
			
		}
	}

}
