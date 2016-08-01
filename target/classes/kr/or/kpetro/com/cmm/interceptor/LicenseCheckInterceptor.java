package kr.or.kpetro.com.cmm.interceptor;

import java.util.Iterator;
import java.util.Set;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.kpetro.com.Const;
import kr.or.kpetro.svr.cmm.service.RequestVO;
import kr.or.kpetro.svr.cmm.service.RequestVO.Body;
import kr.or.kpetro.svr.cmm.service.RequestVO.Header;
import kr.or.kpetro.svr.service.BusinessPlaceLicenseVO;
import kr.or.kpetro.svr.service.LicenseService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndViewDefiningException;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.dreamsecurity.magicline.cs.server.MagicLineCipher;
import com.dreamsecurity.magicline.cs.util.Base64;

/**
 * 라이센스여부 체크 인터셉터
 * @author 공통서비스
 * @since 2014.03.10
 * @version 1.0
 * @see
 *  
 * <pre>
 * << 개정이력(Modification Information) >>
 * 
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2014.03.10            최초 생성 
 *  </pre>
 */


public class LicenseCheckInterceptor extends HandlerInterceptorAdapter {
	/** log */
	protected static final Log log = LogFactory.getLog(LicenseCheckInterceptor.class);
    
    /**비암호화제외자*/
	private Set<String> permittedURL;
    
	public void setPermittedURL(Set<String> permittedURL) {
		this.permittedURL = permittedURL;
	}
	
	@Resource(name = "licenseService")
	private LicenseService licenseService;
	
	/**
	 * 세션에 계정정보(LoginVO)가 있는지 여부로 인증 여부를 체크한다.
	 * 계정정보(LoginVO)가 없다면, 로그인 페이지로 이동한다.
	 */
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {	
		
		String requestURI = request.getRequestURI(); //요청 URI
		boolean isPermittedURL = false; 

		/*requestURI에 jsessionid 파라미터가 붙는 경우가 있어 해당 URI에서 잘라냄*/
		if(requestURI.indexOf(";jsessionid=")>=0){
			requestURI = requestURI.substring(0,requestURI.indexOf(";jsessionid="));
		}
		
		if(this.permittedURL!=null){
			for(Iterator<String> it = this.permittedURL.iterator(); it.hasNext();){
				
				String urlPattern = request.getContextPath() + (String) it.next();

				if(Pattern.matches(urlPattern, requestURI)){// 정규표현식을 이용해서 요청 URI가 허용된 URL에 맞는지 점검함.
					isPermittedURL = true;
				}
			}
		} 
		
		// 라이센스 체크, 라이센스 체크가 필요한 페이지는 데이타 암호화 됨
		// 1)파라미터 파싱
		RequestVO reqvo = new RequestVO(request);
		Header header = reqvo.getHeader();
		Body body = null;
		BusinessPlaceLicenseVO licensevo = null;
		
		// body 복호화
		if(!isPermittedURL && header.getBizPlaceId()!=null && !header.getBizPlaceId().equals("")&&reqvo.getBodyString()!=null &&!reqvo.getBodyString().equals("")){
			BusinessPlaceLicenseVO inputvo = new BusinessPlaceLicenseVO();
			inputvo.setBplcId(header.getBizPlaceId());
			licensevo = licenseService.LicenseInfo(inputvo);
			if(licensevo.getSessionKey()==null || licensevo.getSessionIV()==null || licensevo.getSymmAlgo()==null){
				
			}else{
				// 복호화
				MagicLineCipher cipher = new MagicLineCipher();
				byte[] clientEncData = new Base64().decode(reqvo.getBodyString());
				byte[] clientDecData;
				byte[] sessionKey = new Base64().decode(licensevo.getSessionKey());
				byte[] sessionIV = new Base64().decode(licensevo.getSessionIV());
				
				//reqvo.setSessionKey(sessionKey);
				//reqvo.setSessionIV(sessionIV);
				//reqvo.setSessionKeyAlg(licensevo.getSymmAlgo());

				try{
					clientDecData = cipher.decrypt(sessionKey, sessionIV, licensevo.getSymmAlgo(), clientEncData);
					reqvo.setBodyString(new String(clientDecData));
					log.debug("복호화데이타:"+new String(clientDecData));
				}catch(Exception e){
					log.debug(e);
					ModelAndView modelAndView = new ModelAndView("/com/cmm/page/encrypt");		// 암복호화에실패하였습니다.
					throw new ModelAndViewDefiningException(modelAndView);
				}
				
				
				body = reqvo.getBody();
				
				reqvo.doProcess(); // 파라미터 jsonParse
				
				// 라이센스체크
				if(body.getMacAddress()!=null && licensevo.getMacAddr()!=null){
					if(body.getMacAddress().equals(licensevo.getMacAddr())){
						isPermittedURL = true;
					}
				}
			}
		}else{
			reqvo.doProcess(); // 파라미터 jsonParse
		}
		
		request.setAttribute(Const.VO_REQUEST, reqvo);
		request.setAttribute(Const.VO_LICENSE, licensevo);
		if(!isPermittedURL){
			ModelAndView modelAndView = new ModelAndView("/com/cmm/page/license");		// 라이센스 권한이 존재하지 않습니다.	
			throw new ModelAndViewDefiningException(modelAndView);
		}else{
			return true;
		}

	}
}
