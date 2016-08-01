package kr.or.kpetro.svr.mdwr.spdmd.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import kr.or.kpetro.com.Const;
import kr.or.kpetro.svr.cmm.utl.KpetroUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**수급보고라인 vo*/
@SuppressWarnings("serial")
public class SpldmdReportLineVO extends CollectServerFileVO{ //  implements Serializable
	protected Log log = LogFactory.getLog(this.getClass());
	
	private Integer lineNo;
	private Integer reportStyleCardNo;
	private String lineDataValue;
	private String lineDataSttusCode = "02";	//처리완료
	private Date lineDataProcessDate;
	private String lineDataErrorCn;
	private String trnsferProcessSttusCode;
	private Date trnsferProcessDate;
	private String trnsferProcessErrorCn;
	public static final int FIX_DATA_LEN = 3; // 카드2, 마지막구분1

	private boolean lastLine = false;
	//private char[] itemData;
	private SpldmdReportCardVO cardvo;
	//private List<List<StyleCardIemVO>> itemListArray;
	private List<StyleCardIemVO> itemList;

	public SpldmdReportLineVO(){
	}
	
	/**문자열 fix항목 제거, 실길이 리턴*/
	public void setSpldmdReportLineVO(String s, List<SpldmdReportCardVO> _cardList, List<StyleCardIemVO> _itemList){
	//	if(itemListArray==null) itemListArray = new ArrayList();
		itemList = new ArrayList<StyleCardIemVO>();
		log.debug("str:"+s);
		setLineDataValue(s);
		String msg = "";
		
		byte[] arrCardData = s.getBytes(Const.charset);
		if(arrCardData.length > 3){
			String sCardNo = new String(new byte[]{arrCardData[0],arrCardData[1]});
			try{
				this.reportStyleCardNo = Integer.parseInt(sCardNo);
			}catch(Exception e){
				this.reportStyleCardNo =  -1;
				msg = "카드번호가 숫자가 아님:"+sCardNo;
				setErrorCode(ProcessCode.NOT_FOUND_CARDNO);
				log.debug(msg);
				setErrorDesc(msg);
				return;
			}
			
			//마지막행 여부
			if(arrCardData[arrCardData.length-1] == '0'){
				this.lastLine=false;
			}else if(arrCardData[arrCardData.length-1] == '1'){
				this.lastLine=true;
			}else{
				setErrorCode(ProcessCode.NOT_FOUND_LAST);
				msg ="마지막문자 구분자가 다름:"+String.valueOf(arrCardData[arrCardData.length-1]);
				log.debug(msg);
				setErrorDesc(msg);
				return;
			}
			
			// 최종 item 2014.04.01 총byte
			/*
			itemData = new char[arrCardData.length-FIX_DATA_LEN];
			for(int i=2;i<arrCardData.length-1;i++){
				itemData[i-2] = arrCardData[i];
			}*/
			
			// 카드정보찾기
			for(int i=0;i<_cardList.size();i++){
				if(_cardList.get(i).getReportStyleCardNo().intValue() == this.reportStyleCardNo) {
					this.cardvo = _cardList.get(i);
					break;
				}
			}
			
			if(this.cardvo.getReportStyleCardNo() == null || this.cardvo.getReportStyleCardNo().equals("")){
				setErrorCode(ProcessCode.NOT_FOUND_CARD);
				msg ="양식내, 카드정보가 존재하지 않음[카드번호:"+this.cardvo.getReportStyleCardNo()+"]";
				log.debug(msg);
				setErrorDesc(msg);
				return;
			}
			
			if(_itemList==null){
				setErrorCode(ProcessCode.NOT_FOUND_ITEMS);
				msg ="양식내, 항목데이타가 한개이상 존재하지 않음";
				log.debug(msg);
				setErrorDesc(msg);
				return;
			} 

			boolean isFind = false;
			this.itemList = new ArrayList<StyleCardIemVO>();
			for(int i=0;i<_itemList.size();i++){
				if(_itemList.get(i).getReportStyleCardNo().intValue() == this.reportStyleCardNo){
					StyleCardIemVO itemvo = new StyleCardIemVO();
					itemvo.setStyleCardIemSn(_itemList.get(i).getStyleCardIemSn());
					itemvo.setStyleCardIemNm(_itemList.get(i).getStyleCardIemNm());
					itemvo.setStyleCardIemOrdr(_itemList.get(i).getStyleCardIemOrdr());
					itemvo.setStyleCardIemByteLt(_itemList.get(i).getStyleCardIemByteLt());
					itemvo.setStyleCardColumnNm(_itemList.get(i).getStyleCardColumnNm());
					itemvo.setIemUpdtPosblAt(_itemList.get(i).getIemUpdtPosblAt());
					itemvo.setIemDataTyCode(_itemList.get(i).getIemDataTyCode());
					itemvo.setIemPostngTyCode(_itemList.get(i).getIemPostngTyCode());
					itemvo.setIemDcmlCphr(_itemList.get(i).getIemDcmlCphr());
					itemvo.setIemReadngAt(_itemList.get(i).getIemReadngAt());
					itemvo.setIemReadngOrdr(_itemList.get(i).getIemReadngOrdr());
					itemvo.setIemChoiseGroupCodeNm(_itemList.get(i).getIemChoiseGroupCodeNm());
					this.itemList.add(itemvo);
					
					isFind = true;
					//_itemList.remove(i);
					//i--;
				}else{
					if(isFind) break;
				}
			}
			
			// itemlist
			/*log.debug("itemList");
			for(int i=0;i<itemList.size();i++){
				log.debug(i+":"+itemList.get(i).getStyleCardColumnNm());
			}
			*/
			if(this.itemList.size()==2){
				setErrorCode(ProcessCode.NOT_INPUT_ITEMS);
				log.debug("항목데이타를 찾을 수 없음");
				
				msg ="양식내, 항목데이타가 한개이상 존재하지 않음[카드번호와 마지막라인 구분자만 존재]";
				log.debug(msg);
				setErrorDesc(msg);
				return;
			}
			
			if(this.itemList.size()<3){
				setErrorCode(ProcessCode.INVALID_MIN_LINE_SIZE);
				msg ="양식내, 항목데이타가 한개이상 존재하지 않음[항목사이즈:"+this.itemList.size()+"]";
				log.debug(msg);
				setErrorDesc(msg);
				return;
			}
			
			// 항목만 넘김
			//this.itemList.remove(0); // 카드번호
			//this.itemList.remove(this.itemList.size()-1);// 마지막 문자구분
			
			// 유효성체크
			
			if(cardvo.getLineDataByteLt().intValue() == (arrCardData.length)){
				// item parsing start
				int nItemReadPos = 0;
				for(int i=0;i<itemList.size();i++){
					StyleCardIemVO carditemvo = itemList.get(i);
					byte[] tempData = new byte[carditemvo.getStyleCardIemByteLt()];
					for(int j=0;j<tempData.length;j++){
						tempData[j] = arrCardData[nItemReadPos++];
					}
					// 항목별 데이타체크
					String checkData = new String(tempData, Const.charset);
					carditemvo.setItemValue(checkData);	// 원본 데이타
					//if(checkData.trim().equals(""))break;
					// 항목데이타 유효성 체크
					/*
					if(carditemvo.getIemDataTyCode().equals("02")){ // 숫자
						// 부호체크
						int addFlag = 1;
						if(tempData[0]=='-'){
							checkData = checkData.substring(1);
							addFlag = -1;
						}
						
						try{
							Double val = Double.parseDouble(checkData)*addFlag;
							
							// 소숫점자릿수 체크
							if(carditemvo.getIemDcmlCphr()!=null && carditemvo.getIemDcmlCphr().intValue()>0){
								if(checkData.indexOf(".")==-1 || checkData.substring(checkData.indexOf(".")+1).trim().length()!=carditemvo.getIemDcmlCphr().intValue()){
									this.setLineDataSttusCode(ProcessCode.INVALID_DECIMAL_LEN);
									msg =carditemvo.getStyleCardIemNm()+":"+"소숫점이 "+carditemvo.getIemDcmlCphr()+"자리가 필요하지만, 일치하지 않음:"+(checkData);
									log.debug(msg);
									setErrorDesc(msg);
									break;
								}
							}
							//carditemvo.setItemValue(val);	// 원본 데이타
						}catch(Exception e){
							msg ="숫자형이여야하지만, 숫자가 아님:"+(checkData);
							log.debug(msg);
							setErrorDesc(msg);
							this.setLineDataSttusCode(ProcessCode.INVALID_NUMBER_TYPE);
							break;
						}
					}else if(carditemvo.getIemDataTyCode().equals("03")){ // 날짜, 무조건8
						if(!KpetroUtil.isValidateDate(checkData)){
							msg ="유효하지 않은 날짜임:"+(checkData);
							log.debug(msg);
							setErrorDesc(msg);
							this.setLineDataSttusCode(ProcessCode.INVALID_DATE_FORMAT);
							break;
						}
						
					}else if(carditemvo.getIemDataTyCode().equals("04")){ //코드, 개별정합성체크 프로시저 실행
					}*/
					
				}
				// item parsing end
				
			}else{
				msg ="라인데이타가 일치하지 않음:고정길이:"+(cardvo.getLineDataByteLt())+",데이타길이:"+(arrCardData.length);
				log.debug(msg);
				setErrorDesc(msg);
				this.setLineDataSttusCode(ProcessCode.DIFFERENT_LINE_LEN);
			}
			
		}else{
			msg ="카드데이타가 최소길이 3보다 작음";
			log.debug(msg);
			setErrorDesc(msg);
			this.setLineDataSttusCode(ProcessCode.INVALID_MIN_FILE_SIZE);
		}
	}
	public Integer getLineNo() {
		return lineNo;
	}
	public void setLineNo(Integer lineNo) {
		this.lineNo = lineNo;
	}
	public Integer getReportStyleCardNo() {
		return reportStyleCardNo;
	}
	public void setReportStyleCardNo(Integer reportStyleCardNo) {
		this.reportStyleCardNo = reportStyleCardNo;
	}
	public String getLineDataValue() {
		return lineDataValue;
	}
	public void setLineDataValue(String lineDataValue) {
		this.lineDataValue = lineDataValue;
	}
	public String getLineDataSttusCode() {
		return lineDataSttusCode;
	}
	public void setLineDataSttusCode(String lineDataSttusCode) {
		this.lineDataSttusCode = lineDataSttusCode;
	}
	public Date getLineDataProcessDate() {
		return lineDataProcessDate;
	}
	public void setLineDataProcessDate(Date lineDataProcessDate) {
		this.lineDataProcessDate = lineDataProcessDate;
	}
	public String getLineDataErrorCn() {
		return lineDataErrorCn;
		//return ProcessCode.getLineDataSttusCode(this.getErrorCode());
	}
	public void setLineDataErrorCn(String lineDataErrorCn) {
		this.lineDataErrorCn = lineDataErrorCn;
	}
	public String getTrnsferProcessSttusCode() {
		return trnsferProcessSttusCode;
	}
	public void setTrnsferProcessSttusCode(String trnsferProcessSttusCode) {
		this.trnsferProcessSttusCode = trnsferProcessSttusCode;
	}
	public Date getTrnsferProcessDate() {
		return trnsferProcessDate;
	}
	public void setTrnsferProcessDate(Date trnsferProcessDate) {
		this.trnsferProcessDate = trnsferProcessDate;
	}
	public String getTrnsferProcessErrorCn() {
		return trnsferProcessErrorCn;
	}
	public void setTrnsferProcessErrorCn(String trnsferProcessErrorCn) {
		this.trnsferProcessErrorCn = trnsferProcessErrorCn;
	}
	public boolean isLastLine() {
		return lastLine;
	}
	public void setLastLine(boolean lastLine) {
		this.lastLine = lastLine;
	}
	public List<StyleCardIemVO> getItemList() {
		return itemList;
	}
	
	public SpldmdReportCardVO getCardvo(){
		return this.cardvo;
	}

	public void setLineDataSttusCode(int _errorCode) {
		setErrorCode(_errorCode);
		this.lineDataSttusCode = ProcessCode.getLineDataSttusCode(_errorCode);
		this.lineDataErrorCn = ProcessCode.getErrorContent(_errorCode);
	}

}
