package kr.or.kpetro.svr.service;

import java.util.Date;
import java.util.List;

import kr.or.kpetro.svr.mdwr.init.service.SystemCodeVO;
import kr.or.kpetro.svr.mdwr.spdmd.service.CollectServerFileVO;
import kr.or.kpetro.svr.mdwr.spdmd.service.SpldmdReportCardVO;
import kr.or.kpetro.svr.mdwr.spdmd.service.SpldmdReportFxVO;
import kr.or.kpetro.svr.mdwr.spdmd.service.SpldmdReportLineVO;
import kr.or.kpetro.svr.mdwr.spdmd.service.SpldmdReportStyleVO;
import egovframework.rte.psl.dataaccess.util.EgovMap;

public interface MdwrManageService {
	/**사용자ID 로그인*/
	public BusinessPlaceLicenseVO selectLoginCheck(BusinessPlaceLicenseVO userVO) throws Exception;
	/**공인인증서 로그인*/
	public BusinessPlaceLicenseVO selectLoginCheckPKI(BusinessPlaceLicenseVO userVO) throws Exception;
	/**사업장라이센스 상세*/
	public List<BusinessPlaceLicenseVO> selectBusinessPlaceLicenseList(BusinessPlaceLicenseVO userVO) throws Exception;
	/**사업장라이센스 업데이트*/
	public boolean updateBusinessPlaceLicense(BusinessPlaceLicenseVO userVO) throws Exception;
	/**서버시간을 반환한다*/
	public Date selectServerTime()throws Exception;
	/**업데이트 대상 오류코드조회*/
	public List<SystemCodeVO> selectSystemCode(SystemCodeVO vo)throws Exception;
	/**수급보고 스케줄 정보*/
	public List<ReportScheduleVO> selectCurrentReportSchedule()throws Exception;
	/**암호화키 저장*/
	public boolean updateEncryptKey(BusinessPlaceLicenseVO bpvo);
	/**미들웨어접속통계저장*/
	public boolean insertMiddlewareStatusLog(MiddlewareConnectingLogVO middlewareConnectingLogVO);
	/**미들웨어 미조회 공지사항 갯수*/
	public int selectNotReadNoticeCount(EgovMap param);
	/**현재수급보고일정*/
	public EgovMap selectCurrentReportFx(BusinessPlaceLicenseVO userVO);
	/**최근수급보고를 조회한다*/
	public List<CollectServerFileVO> selectCurrentBusinessPlaceReport(
			BusinessPlaceLicenseVO bplvo);
	/**보고대상파일정보를 조회한다.*/
	public List<SpldmdReportStyleVO> selectReportFileInfo(List<SpldmdReportStyleVO> reportlist, String _bplcId);
	/**수급보고카드목록*/
	public List selectReportCardList(SpldmdReportCardVO reportvo);
	/**수급보고항목목록*/
	public List selectReportItemList(SpldmdReportCardVO reportvo);
	/**수급보고회차 양식정보조회*/
	public SpldmdReportStyleVO selectReportStyle(SpldmdReportFxVO collectvo);
	/**수급보고서버파일 저장*/
	public boolean saveServerFile(CollectServerFileVO collectvo);
	/**수급서버파일 저장*/
	public CollectServerFileVO saveTransferReport(String filePath,
			CollectServerFileVO collectvo) throws Exception;
	/**수급보고현황 보고년도 조회*/
	public List<SpldmdReportFxVO> selectReportYear(BusinessPlaceLicenseVO licensevo);
	/**수급보고현황 목록 조회*/
	public List<SpldmdReportFxVO> selectReportList(BusinessPlaceLicenseVO licensevo);
	/**수급보고현황 갯수 조회*/
	public int selectReportCount(BusinessPlaceLicenseVO licensevo);
	/**수급보고상세 조회*/
	public CollectServerFileVO selectReportData(CollectServerFileVO filevo);
	/**미들웨어목록조회*/
	public List<MdwrVO> selectMiddlewareList();
	public List<BusinessPlaceLicenseVO> selectBusinessPlaceLicenseListAll(BusinessPlaceLicenseVO licensevo);
	/**접속서버목록조회*/
	public List<MdwrServerVO> selectMiddlewareServerList();
	/**사용자,사업장정보조회*/
	public BusinessPlaceInfoVO selectMiddlewareUser(BusinessPlaceInfoVO bplcvo);
	/**자동전송여부 업데이트*/
	public boolean updateAutomaticTransmit(BusinessPlaceLicenseVO paramvo);
	/**수급보고년도조회*/
	public List<SpldmdReportFxVO> selectReportYearList();
	/**수급보고일정조회*/
	public List<SpldmdReportFxVO> selectReportSchedule(SpldmdReportFxVO paramvo);
	public List<EgovMap> selectConnectingYear(BusinessPlaceLicenseVO licensevo);
	public List<EgovMap> selectConnectingMonth(BusinessPlaceLicenseVO licensevo);
	public List<MiddlewareConnectingLogVO> selectConnectingStats(BusinessPlaceLicenseVO licensevo);
	public List<MiddlewareConnectingLogVO> selectConnectingDailyLogs(BusinessPlaceLicenseVO licensevo);
	/**로그아웃*/
	public boolean updateLogOut(BusinessPlaceLicenseVO licensevo);
	/**사업장로그인*/
	public BusinessPlaceLicenseVO selectBusinessPlaceLoginList(BusinessPlaceLicenseVO pBplcvo);
	/**수급보고 사업장 목록 조회*/
	public List selectSupplyDemandBusinessPlaceInfo(String bsnsNo);

}
