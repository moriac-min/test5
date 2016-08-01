package kr.or.kpetro.svr.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import kr.or.kpetro.com.Const;
import kr.or.kpetro.svr.mdwr.init.service.SystemCodeDAO;
import kr.or.kpetro.svr.mdwr.init.service.SystemCodeVO;
import kr.or.kpetro.svr.mdwr.spdmd.service.CollectServerFileVO;
import kr.or.kpetro.svr.mdwr.spdmd.service.ProcessCode;
import kr.or.kpetro.svr.mdwr.spdmd.service.SpldmdReportCardVO;
import kr.or.kpetro.svr.mdwr.spdmd.service.SpldmdReportFxVO;
import kr.or.kpetro.svr.mdwr.spdmd.service.SpldmdReportLineVO;
import kr.or.kpetro.svr.mdwr.spdmd.service.SpldmdReportStyleVO;
import kr.or.kpetro.svr.mdwr.spdmd.service.StyleCardIemVO;
import kr.or.kpetro.svr.mdwr.spdmd.service.impl.ReceiveShipmentDAO;
import kr.or.kpetro.svr.service.BusinessPlaceInfoVO;
import kr.or.kpetro.svr.service.BusinessPlaceLicenseVO;
import kr.or.kpetro.svr.service.MdwrManageService;
import kr.or.kpetro.svr.service.MdwrServerVO;
import kr.or.kpetro.svr.service.MdwrVO;
import kr.or.kpetro.svr.service.MiddlewareConnectingLogVO;
import kr.or.kpetro.svr.service.ReportScheduleVO;

import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.AbstractServiceImpl;
import egovframework.rte.psl.dataaccess.util.EgovMap;

@Service("MdwrManageService")
public class MdwrManageServiceImpl extends AbstractServiceImpl implements MdwrManageService{

	@Resource(name = "MiddlewareLoginDAO")
    private MiddlewareLoginDAO middlewareLoginDAO;
	
	@Resource(name = "BusinessPlaceLicenseDAO")
    private BusinessPlaceLicenseDAO businessPlaceLicenseDAO;
	
	/**사업장시스템관리*/
	@Resource(name = "BusinessPlaceSystemDAO")
    private BusinessPlaceSystemDAO businessPlaceSystemDAO;
	
	/**파일수집*/
	@Resource(name = "ReceiveShipmentDAO")
    private ReceiveShipmentDAO receiveShipmentDAO;

	/**시스템업데이트*/
	@Resource(name = "SystemCodeDAO")
    private SystemCodeDAO systemCodeDAO;
	
	
	@Override
	public BusinessPlaceLicenseVO selectLoginCheck(BusinessPlaceLicenseVO userVO) throws Exception {
		// TODO Auto-generated method stub
		return middlewareLoginDAO.selectLoginCheck(userVO);
	}
	
	@Override
	public BusinessPlaceLicenseVO selectLoginCheckPKI(BusinessPlaceLicenseVO userVO) throws Exception {
		return middlewareLoginDAO.selectLoginCheckPKI(userVO);
	}

	@Override
	public List<BusinessPlaceLicenseVO> selectBusinessPlaceLicenseList(BusinessPlaceLicenseVO userVO)
			throws Exception {
		return businessPlaceLicenseDAO.select(userVO);
	}

	@Override
	public boolean updateBusinessPlaceLicense(BusinessPlaceLicenseVO userVO) throws Exception {
		return businessPlaceLicenseDAO.update(userVO);
	}

	@Override
	public Date selectServerTime() throws Exception {
		return businessPlaceSystemDAO.selectCurrentTime();
	}

	@Override
	public List<SystemCodeVO> selectSystemCode(SystemCodeVO vo) throws Exception {
		// 코드데이타 체크
		if(vo.getCate().indexOf(",")>-1){ // 배열
			vo.setCates( vo.getCate().split(","));
		}else{
			vo.setCates(new String[]{vo.getCate()});
		}
		/**불필요데이타체크*/
		String[] arrTemp = vo.getCates();
		for(int i=0;i<arrTemp.length;i++){
			arrTemp[i] = arrTemp[i].replace("'", "");
		}
		vo.setCates(arrTemp);
		
		return systemCodeDAO.selectSystemCode(vo);
	}

	@Override
	public List<ReportScheduleVO> selectCurrentReportSchedule() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean updateEncryptKey(BusinessPlaceLicenseVO bpvo) {
		return businessPlaceLicenseDAO.updateSession(bpvo);
	}

	/**미들웨어 상태로그 저장
	 * @return */
	@Override
	public boolean insertMiddlewareStatusLog(MiddlewareConnectingLogVO logvo) {
		return businessPlaceSystemDAO.insertMiddlewareStatusLog(logvo) > 0;
	}

	@Override
	public EgovMap selectCurrentReportFx(BusinessPlaceLicenseVO paramvo) {
		return businessPlaceSystemDAO.selectCurrentReportFx(paramvo);
	}

	@Override
	public int selectNotReadNoticeCount(
			EgovMap param) {
		return businessPlaceSystemDAO.selectNotReadNoticeCount(param);
	}

	@Override
	public List<CollectServerFileVO> selectCurrentBusinessPlaceReport(BusinessPlaceLicenseVO bplvo){
		// TODO Auto-generated method stub
		return receiveShipmentDAO.selectCurrentBusinessPlaceReport(bplvo);
	}

	
	/**수급보고파일에 대한 수급보고 정보를 리턴한다*/
	@Override
	public List<SpldmdReportStyleVO> selectReportFileInfo(
			List<SpldmdReportStyleVO> reportlist, String _reportStyleSeCode) {
		List<SpldmdReportStyleVO> list = receiveShipmentDAO.selectReportFileInfo(reportlist, _reportStyleSeCode);
		
		// 보낸 파이리스트
		for(int i=0;i<reportlist.size();i++){
			SpldmdReportStyleVO vo = reportlist.get(i);
			SpldmdReportStyleVO findvo = getReportInfo(list, vo);
			if(findvo==null){
				vo.setReportPdBeginDay(null);
				vo.setReportPdEndDay(null);
			}else{
				vo.setReportYear(findvo.getReportYear());
				vo.setReportWkno(findvo.getReportWkno());
				vo.setReportPdBeginDay(findvo.getReportPdBeginDay());
				vo.setReportPdEndDay(findvo.getReportPdEndDay());
				vo.setReportTmeId(findvo.getReportTmeId());
				vo.setReportStyleVerSn(findvo.getReportStyleVerSn());
				vo.setSpldmdReportClosDay(findvo.getSpldmdReportClosDay());
			}
		}
		return reportlist;
	}
	
	private SpldmdReportStyleVO getReportInfo(List<SpldmdReportStyleVO> targetList, SpldmdReportFxVO findvo){
		if(targetList==null) return null;
		for(int i=0;i<targetList.size();i++){
			if(targetList.get(i).getReportPdBeginDay().equals(findvo.getReportPdBeginDay()) && 
					targetList.get(i).getReportPdEndDay().equals(findvo.getReportPdEndDay())){
				return targetList.get(i);
			}
		}
		return null;
	}

	@Override
	public List selectReportCardList(SpldmdReportCardVO reportvo) {
		return receiveShipmentDAO.selectReportCardList(reportvo);
	}

	@Override
	public List selectReportItemList(SpldmdReportCardVO reportvo) {
		return receiveShipmentDAO.selectReportItemList(reportvo);
	}

	@Override
	public SpldmdReportStyleVO selectReportStyle(SpldmdReportFxVO collectvo) {
		return receiveShipmentDAO.selectReportStyle(collectvo);
	}

	@Override
	public CollectServerFileVO saveTransferReport(String filePath,
			CollectServerFileVO collectvo) throws SQLException {
		
		SpldmdReportCardVO cardvo = new SpldmdReportCardVO();
		cardvo.setReportStyleSeCode(collectvo.getReportStyleSeCode());
		cardvo.setReportStyleVerSn(collectvo.getReportStyleVerSn());
		// 카드조회
		List<SpldmdReportCardVO> list1 = receiveShipmentDAO.selectReportCardList(cardvo);
		// 항목조회
		List<StyleCardIemVO> list2 = receiveShipmentDAO.selectReportItemList(cardvo);
		
		List<SpldmdReportLineVO> lineList = new ArrayList<SpldmdReportLineVO>();
		int regSeq = 1;
		Integer errorCode = null;
		String errorCn = null;
		try {
			BufferedReader buf = new BufferedReader(new InputStreamReader(new FileInputStream(new File(filePath)), Const.charset));
			String s = "";
			while((s=buf.readLine())!=null && !s.trim().equals("")){ // 카드단위
				SpldmdReportLineVO linevo  = new SpldmdReportLineVO();
				linevo.setSpldmdReportLineVO(s, list1, list2);
				linevo.setLineNo(regSeq++);
				//linevo.getItemListArray().add(linevo.getItemList());
				lineList.add(linevo);
				if(linevo.getErrorCode() != null && errorCode==null){
					// 라인파싱 에러
					//processCode = linevo.getErrorCode();
					errorCode = linevo.getErrorCode();
					errorCn = linevo.getLineNo()+"라인:"+linevo.getErrorDesc();
					//collectvo.setColctProcessSttusCode( linevo.getErrorCode());
					//collectvo.setColctProcessErrorCn(linevo.getLineNo()+"라인:"+linevo.getErrorDesc());
					//break;
				}
			}
			
		} catch (IOException e) {
			log.debug(e);
			collectvo.setColctProcessSttusCode(ProcessCode.getCollectProcessCode(ProcessCode.ERR_FILE)); // 파일오류
			collectvo.setColctProcessErrorCn(e.getMessage().toString());
		}
		// 서버파일저장
		if(errorCode!=null){
			collectvo.setColctProcessSttusCode(errorCode);
			collectvo.setColctProcessErrorCn(errorCn);
		}
		collectvo.setLineCo(regSeq-1);
		return receiveShipmentDAO.saveTransferReport(collectvo, lineList);
	}

	@Override
	public boolean saveServerFile(CollectServerFileVO collectvo) {
		receiveShipmentDAO.insertCollectServerFile(collectvo);
		return true;
	}

	@Override
	public List<SpldmdReportFxVO> selectReportYear(BusinessPlaceLicenseVO licensevo) {
		return receiveShipmentDAO.selectReportYear(licensevo);
	}

	@Override
	public List<SpldmdReportFxVO> selectReportList(
			BusinessPlaceLicenseVO licensevo) {
		return receiveShipmentDAO.selectReportList(licensevo);
	}

	@Override
	public int selectReportCount(BusinessPlaceLicenseVO licensevo) {
		return receiveShipmentDAO.selectReportCount(licensevo);
	}

	@Override
	public CollectServerFileVO selectReportData(CollectServerFileVO filevo) {
		
		List<SpldmdReportLineVO> list = receiveShipmentDAO.selectReportData(filevo);
		
		if(list!=null && list.size()>0){
			String file = "";
			for(int i=0;i<list.size();i++){
				file += list.get(i).getLineDataValue() +"\n\r";
			}
			
			CollectServerFileVO outvo = new CollectServerFileVO();
			outvo.setFile(file);
			outvo.setReportStyleVerSn(list.get(0).getReportStyleVerSn());
			outvo.setColctFileProcessDate(list.get(0).getColctFileProcessDate());
			outvo.setColctProcessErrorCn(list.get(0).getColctProcessErrorCn());
			outvo.setColctProcessSttusNm(list.get(0).getColctProcessSttusNm());
			return outvo;
		}
		
		return null;
	}

	@Override 
	public List<MdwrVO> selectMiddlewareList() {
		return businessPlaceSystemDAO.selectMiddlewareList();
	}

	@Override
	public List<BusinessPlaceLicenseVO> selectBusinessPlaceLicenseListAll(
			BusinessPlaceLicenseVO licensevo) {
		return businessPlaceLicenseDAO.selectBusinessPlaceLicenseList(licensevo);
	}

	@Override
	public List<MdwrServerVO> selectMiddlewareServerList() {
		return businessPlaceSystemDAO.selectMiddlewareServerList();
	}

	@Override
	public BusinessPlaceInfoVO selectMiddlewareUser(BusinessPlaceInfoVO bplcvo) {
		return businessPlaceSystemDAO.selectMiddlewareUser(bplcvo);
	}

	@Override
	public boolean updateAutomaticTransmit(BusinessPlaceLicenseVO paramvo) {
		return businessPlaceSystemDAO.updateAutomaticTransmit(paramvo);
	}

	@Override
	public List<SpldmdReportFxVO> selectReportYearList() {
		return receiveShipmentDAO.selectReportYearList();
	}

	@Override
	public List<SpldmdReportFxVO> selectReportSchedule(SpldmdReportFxVO paramvo) {
		return receiveShipmentDAO.selectReportSchedule(paramvo);
	}

	@Override
	public List<EgovMap> selectConnectingYear(
			BusinessPlaceLicenseVO licensevo) {
		return businessPlaceSystemDAO.selectConnectingYear(licensevo);
	}

	@Override
	public List<EgovMap> selectConnectingMonth(
			BusinessPlaceLicenseVO licensevo) {
		return businessPlaceSystemDAO.selectConnectingMonth(licensevo);
	}

	@Override
	public List<MiddlewareConnectingLogVO> selectConnectingStats(
			BusinessPlaceLicenseVO licensevo) {
		return businessPlaceSystemDAO.selectConnectingStats(licensevo);
	}

	@Override
	public List<MiddlewareConnectingLogVO> selectConnectingDailyLogs(
			BusinessPlaceLicenseVO licensevo) {
		return businessPlaceSystemDAO.selectConnectingDailyLogs(licensevo);
	}

	@Override
	public boolean updateLogOut(BusinessPlaceLicenseVO licensevo) {
		return businessPlaceLicenseDAO.updateLogOut(licensevo);
	}

	@Override
	public BusinessPlaceLicenseVO selectBusinessPlaceLoginList(BusinessPlaceLicenseVO pBplcvo) {
		return businessPlaceLicenseDAO.selectBusinessPlaceLoginList(pBplcvo);
	}

	@Override
	public List<EgovMap> selectSupplyDemandBusinessPlaceInfo(String bsnsNo) {
		return businessPlaceSystemDAO.selectSupplyDemandBusinessPlaceInfo(bsnsNo);
	}
}
