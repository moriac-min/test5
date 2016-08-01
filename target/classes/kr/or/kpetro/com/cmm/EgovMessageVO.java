package kr.or.kpetro.com.cmm;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

/**
 * @Class Name : EgovMessageVO.java
 * @Description : EgovMessage VO class
 * @Modification Information
 * @
 * @  수정일         수정자                   수정내용
 * @ -------    --------    ---------------------------
 * @ 2014.03.06             최초 생성
 *
 *  @author 
 *  @since 2014.03.06
 *  @version 1.0
 *  @see
 *  
 */
@SuppressWarnings("serial")
public class EgovMessageVO implements Serializable{
	
	public EgovMessageVO(String _code){
		//error.pki.0001
		String[] arrMessage = StringUtils.split(_code, ".");
		if(arrMessage!=null && arrMessage.length==3){
			String code = "";
			for(int i=0;i<arrMessage.length-1;i++){
				if(arrMessage[i].length()>0) code += arrMessage[i].substring(0,1).toUpperCase();
			}
			code+="-";
			code += arrMessage[arrMessage.length-1].toUpperCase();
			
			this.messageCode = code;
		}else{
			this.messageCode = _code;
		}
	}
	
	
	private String messageCode;
	private String message;
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getMessageCode() {
		return messageCode;
	}
	public void setMessageCode(String messageCode) {
		this.messageCode = messageCode;
	}
	
	
}
