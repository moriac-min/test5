package kr.or.kpetro.svr.cmm.service;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.dreamsecurity.magicline.cs.exception.MagicLineCSException;
import com.dreamsecurity.magicline.cs.server.MagicLineCipher;
import com.dreamsecurity.magicline.cs.util.Base64;

import kr.or.kpetro.com.Const;
import kr.or.kpetro.com.cmm.EgovMessageSource;
import kr.or.kpetro.com.cmm.EgovMessageVO;
import kr.or.kpetro.svr.cmm.service.RequestVO.Body;
import kr.or.kpetro.svr.service.BusinessPlaceLicenseVO;
import kr.or.kpetro.svr.service.UserVO;
import kr.or.kpetro.svr.web.AuthenticateController;

import egovframework.rte.psl.dataaccess.util.EgovMap;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;


/**
 * @Class Name : ResponseVO.java
 * @Description : 미들웨어 데이타 리턴 객체
 * @Modification : 
 *
 *    수정일       수정자         수정내용
 *    -------        -------     -------------------
 *    2014. 2. 13.     
 *
 * @author 
 * @since 2014. 2. 13
 * @version 1.0
 * @see 
 *
 */
@SuppressWarnings("serial")
public class ResponseVO{
	protected static final Log log = LogFactory.getLog(ResponseVO.class);

	private ModelMap model;
	
	private Header header;
	private Body body;
	//private EgovMap param;
	//private List<EgovMap> list;
	
	public Header getHeader(){ return this.header;}
	public Body getBody(){ return this.body;}
	public ResponseVO(){}
	public ResponseVO(ModelMap _model, RequestVO _reqvo){
		this.model = _model;
		this.header = new Header(_reqvo);
		this.body = new Body(_reqvo);
	}

	public class Header implements Serializable{
		/**사업장ID*/
		private String bizPlaceId;
		/**결과- Server 결과처리자*/
		private String resultCode;
		/**결과-메시지내용*/
		private String resultMsg;
		/**서비스아이디*/
		private String serviceId;
		
		/**헤더생성*/
		public Header(RequestVO _reqvo){
			this.resultCode = Const.J_RESULT_SUCESS;
			
			if(_reqvo == null) return;
			
			this.bizPlaceId = _reqvo.getHeader().getBizPlaceId();
			this.serviceId = _reqvo.getHeader().getServiceId();
		}
		
		public String getResultCode(){ return this.resultCode;}
		public String getResultMsg(){ return this.resultMsg;}
		public String getBizPlaceId(){return this.bizPlaceId;}
		public String getServiceId(){return this.serviceId;}
	}
	
	public class Body implements Serializable{
		/**서비스정의*/
		//private EgovMap svcDef;
		/**파라미터*/
		private Object param;
		/**리스트*/
		private List<EgovMap> list;
		/**부가리스트*/
		private List<EgovMap> list2;
		/**body 생성*/
		public Body(RequestVO _reqvo){
			if(_reqvo == null) return;
			
			//if( _reqvo.getBody().getMacAddr()!=null)this.svcDef.put(MAC_ADDR, _reqvo.getBody().getMacAddr());
			//if( _reqvo.getBody().getUserId()!=null)this.svcDef.put(USER_ID, _reqvo.getBody().getUserId());
		}
		
		//public EgovMap getSvcDef(){return this.svcDef;}
		public Object getParam(){return this.param;}
		public List<EgovMap> getList(){return this.list;}
		public List<EgovMap> getList2(){return this.list2;}
	}
	
	public void addAttribute(){
		if(this.model==null) return;
		
		this.model.addAttribute(Const.J_HEADER, this.header);
		// 암호가 필요한 경우, body를 String 으로 암호화한다.
		/*ObjectMapper objmapper = new ObjectMapper();
			String strJson = null;
			try {
				strJson = objmapper.writeValueAsString(bodyvo);
			} catch (JsonGenerationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
		if(this.body.param==null && this.body.list ==null && this.body.list2 ==null  ) return;
		
		this.model.addAttribute(Const.J_BODY, this.body);
	}
	
	public void addAttribute(byte[] sKey, byte[] sIV, String sAlgo){
		if(this.model==null) return;
		
		this.model.addAttribute(Const.J_HEADER, this.header);
		if(this.body.param==null && this.body.list ==null && this.body.list2 ==null)return;
		
		String strJson = null;
		// 암호가 필요한 경우, body를 String 으로 암호화한다.
		ObjectMapper objmapper = new ObjectMapper();
		objmapper.getSerializationConfig().setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
		
		try {
			
			strJson = objmapper.writeValueAsString(this.body);
			log.debug("... json문자열: " + strJson);
			// json encrypt
			MagicLineCipher cipher = new MagicLineCipher();
			
			try {
				byte[] serverEncData = null;
				serverEncData = cipher.encrypt(sKey,sIV,sAlgo, strJson.getBytes());
				log.debug("... 서버에서 클라이언트로 보낼 데이터를 암호화한 결과값 (S3): " + new Base64().encode(serverEncData));
				
				this.model.addAttribute(Const.J_BODY, new Base64().encode(serverEncData));
				
			} catch (MagicLineCSException e) {
				// TODO Auto-generated catch block
				log.debug(e);
			}
			
			
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**body 내, 파라미터를 세팅한다.*/
	public void setParam(EgovMap outvo) {
		// TODO Auto-generated method stub
		this.body.param = outvo;
	}
	
	public void setParam(Object outvo) {
		// TODO Auto-generated method stub
		if(outvo==null) return;
		this.body.param = outvo;
	}
	/**에러코드를 세팅한다.
	public void addAttribute(String _errorCode) {
		this.header.resultCode = _errorCode;
		this.addAttribute();
	}*/
	
	public void addAttribute(EgovMessageVO _messagevo) {
		//this.header.resultCode = _messagevo.getMessageCode();
		//this.header.resultMsg = _messagevo.getMessage();
		
		this.header.resultCode = _messagevo.getMessage();
		this.addAttribute();
	}
	
	/**불필요 변수를 제거하고, 서비스리스트 세팅*/
	public void setList(List _list, String[] removeVarient) {
		if(_list == null) return;
		
		Method localMethod;
		if(removeVarient !=null){
			for(int j=0;j<_list.size();j++){
				Object obj = _list.get(j);
				for(int i=0;i<removeVarient.length;i++){
					try {
						String strMethod = "set"+removeVarient[i].substring(0,1).toUpperCase()+removeVarient[i].substring(1);
						localMethod = obj.getClass().getMethod(strMethod, String.class);
						localMethod.invoke(obj, new Object[]{null});
					} catch (SecurityException e) {
						log.debug("SecurityException");
						log.debug(e);
					} catch (NoSuchMethodException e) {
						log.debug("NoSuchMethodException");
						log.debug(e);
					} catch (IllegalArgumentException e) {
						log.debug("IllegalArgumentException");
						log.debug(e);
					} catch (IllegalAccessException e) {
						log.debug("IllegalAccessException");
						log.debug(e);
					} catch (InvocationTargetException e) {
						log.debug("InvocationTargetException");
						log.debug(e);
					}
				}
			}
		}
		this.body.list = _list;
	}
	/**body list를 세팅한다.*/
	public void setList(List _list) {
		if(_list==null || _list.size()==0) return;
		this.body.list = _list;
	}
	
	/**body list2를 세팅한다.*/
	public void setList2(List _list) {
		if(_list==null || _list.size()==0) return;
		this.body.list2 = _list;
	}
	
	/**리턴값이 있고, 결과코드가 정상이 아닌경우 결과를 세팅한다.*/
	public void setResult(EgovMessageVO _messagevo) {
		this.header.resultCode = _messagevo.getMessage();
		//this.header.resultMsg = _messagevo.getMessage();
	}
	
	public void setResult(String resultCode){
		this.header.resultCode = resultCode;
	}
	
	public void addPagingInfo(EgovMap param, PaginationInfo paginationInfo){
		log.debug("총갯수:"+paginationInfo.getTotalRecordCount());
		log.debug("현재페이지번호:"+paginationInfo.getCurrentPageNo());
		log.debug("페이징시작:"+paginationInfo.getFirstPageNoOnPageList());
		log.debug("페이징끝:"+paginationInfo.getLastPageNoOnPageList());
		log.debug("첫페이지번호:"+paginationInfo.getFirstPageNo());
		log.debug("끝페이지번호:"+paginationInfo.getLastPageNo());
		
		param.put("page", paginationInfo.getCurrentPageNo());
		param.put("listFPage", paginationInfo.getFirstPageNoOnPageList());
		param.put("listLPage", paginationInfo.getLastPageNoOnPageList());
		param.put("lastPage", paginationInfo.getLastPageNo());
		param.put("total", paginationInfo.getTotalRecordCount());
	}
	
}
