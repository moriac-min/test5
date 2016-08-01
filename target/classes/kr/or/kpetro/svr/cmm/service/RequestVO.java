package kr.or.kpetro.svr.cmm.service;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.MapIterator;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.dreamsecurity.magicline.cs.server.ReqMsgKeyExchange;
import com.dreamsecurity.magicline.cs.util.Base64;

import kr.or.kpetro.com.Const;
import kr.or.kpetro.svr.cmm.service.RequestVO.Header;
import kr.or.kpetro.svr.cmm.utl.JsonUtil;

import egovframework.rte.psl.dataaccess.util.EgovMap;


/**
 * @Class Name : ServiceVO.java
 * @Description : 미들웨어 데이타 전송 객체
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
public class RequestVO implements Serializable {
	protected Log log = LogFactory.getLog(this.getClass());
	private Header header;
	private Body body;
	
	private String bodyString;
	private String headerString;
	/**세션키
	private byte[] sessionKey;
	private byte[] sessionIV;
	private String sessionKeyAlg;*/
	/**ip*/
	private String localAddr;
	/**현재서버*/
	private String serverNm;
	
	/*public byte[] getSessionKey() {
		return sessionKey;
	}

	public void setSessionKey(byte[] sessionKey) {
		this.sessionKey = sessionKey;
	}

	public byte[] getSessionIV() {
		return sessionIV;
	}

	public void setSessionIV(byte[] sessionIV) {
		this.sessionIV = sessionIV;
	}

	public String getSessionKeyAlg() {
		return sessionKeyAlg;
	}

	public void setSessionKeyAlg(String sessionKeyAlg) {
		this.sessionKeyAlg = sessionKeyAlg;
	}*/
	
	public RequestVO(){
		this.header = new Header();
		this.body = new Body();
	}
	
	public RequestVO(HttpServletRequest request){
		this.header = new Header();
		this.body = new Body();
		
		this.setHeaderString(request.getParameter(Const.J_HEADER)); // 비암호화(bplcId, result, svcId)
		this.setBodyString(request.getParameter(Const.J_BODY)); // 암호구간(svcDef, param, list)
		
		this.localAddr = request.getLocalAddr();
		this.serverNm = request.getLocalName();
		
		log.debug("headerString:"+this.headerString);
		log.debug("bodyString:"+this.bodyString);
	}

	public String getBodyString() {
		return bodyString;
	}
	
	public void setBodyString(String bodyString) {
		this.bodyString = bodyString;
	}


	public String getHeaderString() {
		return headerString;
	}


	public void setHeaderString(String headerString) {
		if(headerString==null) return;
		this.headerString = headerString;
		JsonUtil jutil = new JsonUtil();
		JSONObject jsonHeader = jutil.getJSONObject(this.headerString);
		if(jsonHeader!=null) {
			this.header.bizPlaceId = jutil.getString(jsonHeader, Const.J_BizPlaceID);
			this.header.serviceId = jutil.getString(jsonHeader, Const.J_ServiceID);
			log.debug("header.serviceId:"+header.serviceId);
			/**서비스구분,아이디*/
			if(this.header.serviceId!=null && !this.header.serviceId.equals("") && this.header.serviceId.length()==9){
				this.header.serviceCd = -1;
				try{
					this.header.serviceCd = Integer.parseInt(this.header.serviceId.substring(4));
					this.header.serviceTp = this.header.serviceId.substring(0,4);
				}catch(Exception e){
					log.debug("invalide service code");
				}
			}
		}
	}

	/**파라미터로 받은 변수를 객체로 리턴한다.*/
	public Object getParamVO(Object _obj) {
		if(_obj == null) return _obj;
		EgovMap param = this.body.param;
		if(param == null || param.size()==0) return _obj;

		MapIterator it = param.mapIterator();
		Method localMethod;
		while(it.hasNext()){
			String key = (String)it.next();
			log.debug("key:"+key);
			Object val = it.getValue();
			
			String strMethod = "set"+key.substring(0,1).toUpperCase()+key.substring(1);
			try{
				if(val instanceof String){
					localMethod = _obj.getClass().getMethod(strMethod, String.class);
					localMethod.invoke(_obj, new Object[]{(String)val});
				}else if(val instanceof Long){
					localMethod = _obj.getClass().getMethod(strMethod, Long.class);
					localMethod.invoke(_obj, new Object[]{(Long)val});
				}else if(val instanceof Integer){
					localMethod = _obj.getClass().getMethod(strMethod, Integer.class);
					localMethod.invoke(_obj, new Object[]{(Integer)val});
				}
			}catch (SecurityException e) {
				log.debug(e);
			} catch (NoSuchMethodException e) {
				log.debug(e);
			}catch (IllegalArgumentException e) {
				log.debug(e);
			} catch (IllegalAccessException e) {
				log.debug(e);
			} catch (InvocationTargetException e) {
				log.debug(e);
			}
			
		}
		return _obj;
	}

	public class Header implements Serializable{
		public Header(){}
		private String bizPlaceId;
		/**서비스아이디*/
		private String serviceId;
		/**서비스대분류*/
		private String serviceTp;
		/**서비스업무코드*/
		private int serviceCd;
		public String getBizPlaceId() {
			return bizPlaceId;
		}

		public void setBizPlaceId(String bizPlaceId) {
			this.bizPlaceId = bizPlaceId;
		}

		public String getServiceId() {
			return this.serviceId;
		}
		
		public String getServiceTp(){
			return this.serviceTp;
		}
		
		public int getServiceCd(){
			return this.serviceCd;
		}
	}
	
	public class Body implements Serializable{
		/**결과- Server 결과처리자*/
		//private String result;
		/**서비스아이디*/
		//private String svcId;
		/**MAC ADDR*/
		private String macAddress;
		/**로그인 아이디*/
		private String userId;
		/**valueObject 파라미터*/
		private EgovMap param;
		/**arrayList 파라미터*/
		private List<EgovMap> list;
		public String getMacAddress() {
			return macAddress;
		}
		public void setMacAddress(String macAddress) {
			this.macAddress = macAddress;
		}
		public String getUserId() {
			return userId;
		}
		public void setUserId(String userId) {
			this.userId = userId;
		}
		public EgovMap getParam() {
			return param;
		}
		public void setParam(EgovMap param) {
			this.param = param;
		}
		public List<EgovMap> getList() {
			return list;
		}
		public void setList(List<EgovMap> list) {
			this.list = list;
		}
	}

	public Header getHeader() {
		return this.header;
	}
	
	public Body getBody(){
		return this.body;
	}
	
	/**jsonparse*/
	public boolean doProcess(){
		if(this.bodyString==null) return false;
		
		JsonUtil jutil = new JsonUtil();
		
		// Body
		JSONObject jsonBody = jutil.getJSONObject(this.bodyString);
		if(jsonBody!=null) {
			JSONObject jsonSvcDef = jutil.getJSONObject(jsonBody, "svcDef");
			log.debug("svcDef is not null");
			if(jsonSvcDef!=null){
				log.debug("jutil.getString(jsonSvcDef, Const.J_MacAddress):"+jutil.getString(jsonSvcDef, Const.J_MacAddress));
				this.body.setMacAddress(jutil.getString(jsonSvcDef, Const.J_MacAddress));
				this.body.setUserId(jutil.getString(jsonSvcDef, Const.J_UserID));
			}
			
			// 파라미터
			JSONObject jsonParam = jutil.getJSONObject(jsonBody, "param");
			if(jsonParam!=null){
				EgovMap mapParam = new EgovMap();
				// 파라미터세팅
				Iterator localIterator = jsonParam.keySet().iterator();
				String str = null;
				while (localIterator.hasNext())
				{
					str = (String)localIterator.next();
					mapParam.put(str, jsonParam.get(str));
				}
				
				this.body.setParam(mapParam);
			}
			// 리스트
			JSONArray jsonList = jutil.getJSONList(jsonBody, "list");
			if(jsonList!=null){
				JSONArray localJSONArray = (JSONArray)jsonList;
				List<EgovMap> list = new ArrayList();
				
				if (localJSONArray.get(0) != null) // 
				{
					for (int i = 0; i < localJSONArray.size(); i++)
					{
						JSONObject localJSONObject = (JSONObject)localJSONArray.get(i);
						
						// 파라미터세팅
						Iterator localIterator = localJSONObject.keySet().iterator();
						String str = null;
						EgovMap paramMap = new EgovMap();
						while (localIterator.hasNext())
						{
							str = (String)localIterator.next();
							paramMap.put(str, localJSONObject.get(str));
						}
						list.add(paramMap);
					}
					this.body.setList(list);
				}
			}
		}
		
		return true;
	}

	/**객체에 userinfo를 세팅한다.*/
	public void setUserInfo(Object _obj) {
		try{
			Method localMethod = _obj.getClass().getMethod("setUserId", String.class);
			localMethod.invoke(_obj, new Object[]{this.body.getUserId()});
			
			localMethod = _obj.getClass().getMethod("setUserIp", String.class);
			localMethod.invoke(_obj, new Object[]{this.localAddr});
		}catch (SecurityException e) {
			log.debug(e);
		} catch (NoSuchMethodException e) {
			log.debug(e);
		}catch (IllegalArgumentException e) {
			log.debug(e);
		} catch (IllegalAccessException e) {
			log.debug(e);
		} catch (InvocationTargetException e) {
			log.debug(e);
		}
		
	}
	
	public void addBusinessInfo(){
		if(this.body.param == null) this.body.param = new EgovMap();
		
		this.body.param.put("bplcId", this.header.bizPlaceId);
	}

	public String getServerNm() {
		return serverNm;
	}
	public String getLocalAddr() {
		return localAddr;
	}
}
