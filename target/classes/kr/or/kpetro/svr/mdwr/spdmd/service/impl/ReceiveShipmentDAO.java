package kr.or.kpetro.svr.mdwr.spdmd.service.impl;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import kr.or.kpetro.com.cmm.service.impl.EgovComAbstractDAO;
import kr.or.kpetro.svr.mdwr.spdmd.service.CollectServerFileVO;
import kr.or.kpetro.svr.mdwr.spdmd.service.ProcessCode;
import kr.or.kpetro.svr.mdwr.spdmd.service.SpldmdReportCardVO;
import kr.or.kpetro.svr.mdwr.spdmd.service.SpldmdReportFxVO;
import kr.or.kpetro.svr.mdwr.spdmd.service.SpldmdReportLineVO;
import kr.or.kpetro.svr.mdwr.spdmd.service.SpldmdReportStyleVO;
import kr.or.kpetro.svr.service.BusinessPlaceLicenseVO;
import kr.or.kpetro.svr.web.AuthenticateController;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.util.EgovMap;

@Repository("ReceiveShipmentDAO")
public class ReceiveShipmentDAO extends EgovComAbstractDAO{
	protected static final Log log = LogFactory.getLog(ReceiveShipmentDAO.class);
	/**최근수급보고 파일을 조회한다..*/
	public List<CollectServerFileVO> selectCurrentBusinessPlaceReport(
			BusinessPlaceLicenseVO bplvo) {
		return list("ReceiveShipmentDAO.selectCurrentBusinessPlaceReport", bplvo);
	}

	public List<SpldmdReportStyleVO> selectReportFileInfo(
			List<SpldmdReportStyleVO> reportlist, String _reportStyleSeCode) {
		HashMap map = new HashMap();
		map.put("reportStyleSeCode", _reportStyleSeCode);
		map.put("reportList", reportlist);
		return list("ReceiveShipmentDAO.selectReportFileInfo", map);
	}

	public List selectReportCardList(SpldmdReportCardVO cardvo) {
		return list("ReceiveShipmentDAO.selectReportCardList", cardvo);
	}

	public List selectReportItemList(SpldmdReportCardVO cardvo) {
		return list("ReceiveShipmentDAO.selectReportItemList", cardvo);
	}
	public SpldmdReportStyleVO selectReportStyle(SpldmdReportFxVO vo) {
		return (SpldmdReportStyleVO) selectByPk("ReceiveShipmentDAO.selectReportStyle", vo);
	}
	
	public CollectServerFileVO insertCollectServerFile(CollectServerFileVO collectvo){
		CollectServerFileVO outvo = null;
		
		// db시간구하기
		String curtYear = (String)selectByPk("BusinessPlaceSystemDAO.selectCurrentYear", null);
		collectvo.setColctYear(curtYear);
		
		// 서버파일 등록
		Integer colctFileNo = (Integer)insert("ReceiveShipmentDAO.saveCollectServerFile", collectvo);
		if(colctFileNo !=null){
			outvo = new CollectServerFileVO();
			outvo.setColctYear(curtYear);
			outvo.setColctFileNo(colctFileNo);
		}
		
		return outvo;
	}

	public CollectServerFileVO saveTransferReport(CollectServerFileVO collectvo, List<SpldmdReportLineVO> lineList) throws SQLException {
		CollectServerFileVO outvo = null;
		try{
			super.getSqlMapClient().startTransaction();
			outvo = insertCollectServerFile(collectvo);
			
			if(outvo.getColctFileNo()==null || outvo.getColctFileNo().equals(0)){
				return outvo;
			}
			
			HashMap map = new HashMap();
			map.put("colctYear", outvo.getColctYear());
			map.put("colctFileNo", outvo.getColctFileNo());
			map.put("lineList", lineList);
			insert("ReceiveShipmentDAO.saveTransferReport", map);
			
			super.getSqlMapClient().commitTransaction();
			try{
				if(collectvo.getColctProcessSttusCode().equals(ProcessCode.FILE_SUCESS_CODE)){
					insert("ReceiveShipmentDAO.saveTransferReportOriginal", map);
					
					// 데이타 유효성 검사
					java.util.HashMap outmap = new HashMap<String, String>();
					outmap.put("pCOLCT_YEAR", outvo.getColctYear());
					outmap.put("pCOLCT_FILE_NO", outvo.getColctFileNo());
					list("ReceiveShipmentDAO.saveTransferReportValidation", outmap);
					
					log.debug("pERR_LINE_NO:" +outmap.get("pERR_LINE_NO"));
					log.debug("pERR_DESC:" +outmap.get("pERR_DESC"));
					String errDesc = (String)outmap.get("pERR_DESC");
					if(errDesc!=null && !errDesc.equals("")){
						// 에러로그 업데이트
						outvo.setColctProcessSttusCode(ProcessCode.LINE_ERR_CODE); // 라인오류
						outvo.setColctProcessErrorCn(errDesc);
						update("ReceiveShipmentDAO.updateCollectServerFile", outvo);
						// 해당 row 업데이트
						if(outmap.get("pERR_LINE_NO")!=null){
							SpldmdReportLineVO linevo = new SpldmdReportLineVO();
							linevo.setColctYear(outvo.getColctYear());
							linevo.setColctFileNo(outvo.getColctFileNo());
							linevo.setLineNo((Integer)outmap.get("pERR_LINE_NO"));
							linevo.setLineDataSttusCode(ProcessCode.LINE_STTUS_ERR_CODE);
							linevo.setLineDataErrorCn(errDesc);
							update("ReceiveShipmentDAO.updateTransferReport", linevo);
						}
					}
					
				}
			}catch(Exception e){
				log.debug(e.getCause());
				// 오류 업데이트 처리
				outvo.setColctProcessSttusCode("21"); // 파일오류
				outvo.setColctProcessErrorCn(e.getCause().getCause().getMessage());
				//outvo.setColctProcessErrorCn("DB등록 중 오류가 발생하였습니다.");
				update("ReceiveShipmentDAO.updateCollectServerFile", outvo);
			}
		}catch(Exception e){
			log.debug(e);
		}finally{
			super.getSqlMapClient().endTransaction();
		}
		return outvo;
	}

	public List<SpldmdReportFxVO> selectReportYear(
			BusinessPlaceLicenseVO licensevo) {
		return list("ReceiveShipmentDAO.selectReportYear", licensevo);
	}

	public List<SpldmdReportFxVO> selectReportList(
			BusinessPlaceLicenseVO licensevo) {
		return list("ReceiveShipmentDAO.selectReportList", licensevo);
	}

	public int selectReportCount(BusinessPlaceLicenseVO licensevo) {
		return (Integer)selectByPk("ReceiveShipmentDAO.selectReportCount", licensevo);
	}

	public List<SpldmdReportLineVO> selectReportData(CollectServerFileVO filevo) {
		// TODO Auto-generated method stub
		return list("ReceiveShipmentDAO.selectReportData", filevo);
	}

	public List<SpldmdReportFxVO> selectReportYearList() {
		return list("ReceiveShipmentDAO.selectReportYearList", null);
	}

	public List<SpldmdReportFxVO> selectReportSchedule(SpldmdReportFxVO vo) {
		return list("ReceiveShipmentDAO.selectReportSchedule", vo);
	}

}
