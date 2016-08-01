package kr.or.kpetro.svr.web;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.kpetro.com.Const;
import kr.or.kpetro.com.cmm.EgovMessageSource;
import kr.or.kpetro.com.cmm.util.EgovUserDetailsHelper;
import kr.or.kpetro.svr.cmm.service.RequestVO;
import kr.or.kpetro.svr.cmm.service.RequestVO.Body;
import kr.or.kpetro.svr.cmm.service.RequestVO.Header;
import kr.or.kpetro.svr.cmm.service.ResponseVO;
import kr.or.kpetro.svr.cmm.utl.EgovFileScrty;
import kr.or.kpetro.svr.service.BusinessPlaceLicenseVO;
import kr.or.kpetro.svr.service.EncryptService;
import kr.or.kpetro.svr.service.EncryptVO;
import kr.or.kpetro.svr.service.LicenseService;
import kr.or.kpetro.svr.service.MdwrManageService;
import kr.or.kpetro.svr.service.UserVO;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dreamsecurity.magicline.cs.exception.MagicLineCSException;
import com.dreamsecurity.magicline.cs.server.MagicLineCipher;
import com.dreamsecurity.magicline.cs.server.ReqMsgKeyExchange;
import com.dreamsecurity.magicline.cs.server.ReqMsgLogin;
import com.dreamsecurity.magicline.cs.util.Base64;

import egovframework.rte.psl.dataaccess.util.EgovMap;
/**사용자 인증
 * 서비스목록
 * 1.일반로그인
 * 2.공인인증서로그인
 */
@Controller
public class AuthenticateController{
	
	protected static final Log log = LogFactory.getLog(AuthenticateController.class);

	@Resource(name = "egovMessageSource")
	EgovMessageSource egovMessageSource;
	
	@Resource(name = "MdwrManageService")
	private MdwrManageService mdwrManageService;
	
	@Resource(name = "encryptService")
	private EncryptService encryptService;
	
	@Resource(name = "licenseService")
	private LicenseService licenseService;
	
	/**인증서비스, 인증요청*/
	@RequestMapping(value="/AuthReq.do")
	public String authReq(
			HttpServletRequest request,
			HttpServletResponse response,
			ModelMap model) {
		// 서버인증서, 챌린저 전송
		model.clear();
		RequestVO reqvo = (RequestVO)request.getAttribute("reqvo");
		
		ResponseVO resvo = new ResponseVO(model, reqvo);
		try {
			
			String jsessionid = request.getSession().getId();
			EncryptVO vo = encryptService.getServerCertificate();
			
			EgovMap outvo = new EgovMap();
			outvo.put("certificate", vo.getCertificate());	// 서버인증서
			outvo.put("challenge", vo.getChallenge());	// 챌린저
			outvo.put("conId", jsessionid);					// 세션아이디
			
			// session create
			request.getSession().setAttribute(Const.SVC_CHALLENGE, vo.getChallenge());
			// param세팅
			resvo.setParam(outvo);
			
		} catch (MagicLineCSException e) {
			// TODO Auto-generated catch block
			log.debug(e);
			resvo.addAttribute(egovMessageSource.getMessage("pki.svr.0001"));
		}
		
		resvo.addAttribute();
		return Const.JSON_PAGE;
	}
	
	
	/**로그인
	 * @throws Exception 
	 * @throws UnsupportedEncodingException */
	@RequestMapping(value="/Auth.do")
	public String findLoginCheck(@ModelAttribute EncryptVO _encryptvo,
			HttpServletRequest request,
			HttpServletResponse response,
			ModelMap model) throws Exception{

		model.clear();
		RequestVO reqvo = (RequestVO)request.getAttribute("reqvo");
		ResponseVO resvo = new ResponseVO(model, reqvo);
		
		if(!EgovUserDetailsHelper.isAuthenticated()){ // 인증정보없을경우
			resvo.addAttribute(egovMessageSource.getMessage("login.user.008"));
			return Const.JSON_PAGE;
		}
		
		try {
			Body body = null;
			Header header = reqvo.getHeader();
			EgovMap param = null; // body 내, 파라미터객체
			BusinessPlaceLicenseVO pBplcvo =  new BusinessPlaceLicenseVO(); // 전문파싱 파라미터객체
			String[] removeVarient = {"macAddr", "sessionKey", "sessionIV", "symmAlgo"}; // 삭제대상
			
			byte[] clientKeyExMsg = new Base64().decode(_encryptvo.getKeyMsg());
			ReqMsgKeyExchange keyExMsg = new ReqMsgKeyExchange();
			keyExMsg.process(clientKeyExMsg);
			
			// 메시지 파싱 start
			MagicLineCipher cipher = new MagicLineCipher();
			byte[] clientEncData = new Base64().decode(reqvo.getBodyString());
			byte[] clientDecData;
			clientDecData = cipher.decrypt(keyExMsg.getKey(), keyExMsg.getIv(), keyExMsg.getKeyAlg(), clientEncData);
			log.debug("... 클라이언트에서 보낸 데이터를 복호화 한 결과값: " + new String(clientDecData));
			reqvo.setBodyString(new String(clientDecData));
			reqvo.doProcess();
			body = reqvo.getBody();
			// 메시지 파싱 end
			
			if(body.getMacAddress()==null||body.getMacAddress().equals("")){
				resvo.addAttribute(egovMessageSource.getMessage("login.user.011"));
				return Const.JSON_PAGE;
			}
			
			// 파라미터 세팅
			param = body.getParam();
			if(!header.getServiceId().equals("MW0102003")  && param == null){
				log.debug("====param null=======");
				resvo.addAttribute(egovMessageSource.getMessage("param.user.002"));
				return Const.JSON_PAGE;
			}
			reqvo.getParamVO(pBplcvo); // 파라미터 사용자정보
			pBplcvo.setMacAddr(body.getMacAddress());
			

			// 서비스체크
			UserVO uservo = null;
			try{
				if(header.getServiceId().equals("MW0102003") ){ // 인증서로그인의 경우, 인증서 체크
					// 공인인증데이타 검증
					byte[] loginMsg = keyExMsg.getContent();
					ReqMsgLogin login = new ReqMsgLogin();
					login.process(loginMsg, (byte[])EgovUserDetailsHelper.getAuthenticatedUser());
					
					log.debug("... ... 사용자 인증서 유효성 검증.");
					if(!Const.PKI_VERIFY.equals("NONE"))login.verifyUserCert(ReqMsgLogin.REVOKE_CHECK_CRL, ReqMsgLogin.CERT_VERIFY_FULL_PATH, null);
					
					byte[] userCert = login.getUserCert();						// binary 형태의 사용자 인증서 데이터
					BigInteger serialNo = login.userCertInfo.getSerialNumber();	// 사용자 인증서의 일련번호
					String subjectDN = login.userCertInfo.getSubjectDN();
					String issuerDN  = login.userCertInfo.getIssuerDN();
					Date   notBefore = login.userCertInfo.getNotBefore();
					Date   notAfter  = login.userCertInfo.getNotAfter();
					
					log.debug("... ... 사용자 인증서 일련번호: " + serialNo);
					log.debug("... ... 사용자 인증서 발급자: " + issuerDN);
					log.debug("... ... 사용자 인증서 주체: " + subjectDN);
					log.debug("... ... 사용자 인증서 유효기간 시작: " + notBefore);
					log.debug("... ... 사용자 인증서 유효기간 끝: " + notAfter);
					
					pBplcvo.setCrtfcDn(subjectDN);
					uservo = mdwrManageService.selectLoginCheckPKI(pBplcvo);
					if(uservo!=null){
						pBplcvo.setUserId(uservo.getEsntlId());
						pBplcvo.setOriUserId(uservo.getUserId());
					}
					// 인증서체크 끝
				}else{
					// 파마리터 유효성 체크
					if(pBplcvo.getUserId()==null || pBplcvo.getUserId().equals("")){
						resvo.addAttribute(egovMessageSource.getMessage("login.user.001"));
						return Const.JSON_PAGE;
					}
					
					if(pBplcvo.getUserPassword()==null || pBplcvo.getUserPassword().equals("")){
						resvo.addAttribute(egovMessageSource.getMessage("login.user.002")); // 비밀번호를 입력해주세요
						return Const.JSON_PAGE;
					}
					
					String enpassword = EgovFileScrty.encryptPassword(pBplcvo.getUserPassword(), pBplcvo.getUserId().getBytes());
					pBplcvo.setUserPassword(enpassword);
					uservo = mdwrManageService.selectLoginCheck(pBplcvo);
					if(uservo!=null){
						pBplcvo.setUserId(uservo.getEsntlId());
						pBplcvo.setOriUserId(uservo.getUserId());
					}
				}
			}catch(Exception e){
				log.debug(e);
			}
			
			// 로그인정보 select
			//try {
				
				if(uservo==null){
					resvo.addAttribute( egovMessageSource.getMessage("login.user.003")); // 아이디가존재하지않습니다.
					return Const.JSON_PAGE;
				}
				
				if(uservo.getPasswordCheck().equals("0")){
					resvo.addAttribute(egovMessageSource.getMessage("login.user.004")); // 비밀번호가 일치하지 않습니다.
					return Const.JSON_PAGE;
				}
				
				if(pBplcvo.getBplcId()==null || pBplcvo.getBplcId().equals("")){ // 사업장목록
					List<BusinessPlaceLicenseVO> bplist = mdwrManageService.selectBusinessPlaceLicenseList(pBplcvo);
					
					if(bplist==null || bplist.isEmpty() || bplist.size()==0){
						resvo.addAttribute(egovMessageSource.getMessage("login.user.005")); // 사업장이 존재하지 않습니다.
					}else{
						resvo.setResult(egovMessageSource.getMessage("notice.user.001"));
						resvo.setList(bplist, removeVarient);
					}
					
				}else{
					if(pBplcvo.getLagreeSn()==null){
						// 아이디, 비밀번호, 사업장로그인
						BusinessPlaceLicenseVO rloginvo = mdwrManageService.selectBusinessPlaceLoginList(pBplcvo);
						if(rloginvo==null || rloginvo.getBplcId()==null){
							resvo.addAttribute(egovMessageSource.getMessage("login.user.005")); // 사업장이 존재하지 않습니다.
							return Const.JSON_PAGE;
						}
						
						if(rloginvo.getMacAddr()==null){
							// 사업장목록조회
							pBplcvo.setBplcId("");
							List<BusinessPlaceLicenseVO> bplist = mdwrManageService.selectBusinessPlaceLicenseList(pBplcvo);
							resvo.setResult(egovMessageSource.getMessage("notice.user.001"));
							resvo.setList(bplist, removeVarient);
							
						}else{
							if(rloginvo.getMacAddr().equals(pBplcvo.getMacAddr())){
								List<BusinessPlaceLicenseVO> outlist = new ArrayList();
								outlist.add(rloginvo);
								resvo.setList(outlist, removeVarient);
								
								// 암호키를 저장한다.
								rloginvo.setSessionIV(new Base64().encode(keyExMsg.getIv()));
								rloginvo.setSessionKey(new Base64().encode(keyExMsg.getKey()));
								rloginvo.setSymmAlgo(keyExMsg.getKeyAlg());
								rloginvo.setUserId(pBplcvo.getUserId());
								rloginvo.setUserIp(reqvo.getLocalAddr());
					    		
								if(!mdwrManageService.updateEncryptKey(rloginvo)){
									resvo.addAttribute( egovMessageSource.getMessage("login.user.007")); // 처리중오류가발생하였습니다.
									return Const.JSON_PAGE;
								}
								
							}else{
								resvo.addAttribute(egovMessageSource.getMessage("login.user.010")); // 맥정보가 일치하지 않습니다.
							}
						}
						
					}else{
						// 라이센스 지정
						BusinessPlaceLicenseVO rloginvo = mdwrManageService.selectBusinessPlaceLoginList(pBplcvo);
						List<BusinessPlaceLicenseVO> outlist = new ArrayList();
						outlist.add(rloginvo);
						resvo.setList(outlist, removeVarient);
						
						// 맥을 등록한다.
						rloginvo.setUserIp(reqvo.getLocalAddr());
						rloginvo.setMacAddr(pBplcvo.getMacAddr());
						//bplc_Id = ?    and lagree_Sn = ?  
						if(!mdwrManageService.updateBusinessPlaceLicense(rloginvo)){
							resvo.addAttribute(egovMessageSource.getMessage("login.user.006")); // 처리중오류가발생하였습니다.
							return Const.JSON_PAGE;
						}
						
						
						// 암호키를 저장한다.
						rloginvo.setSessionIV(new Base64().encode(keyExMsg.getIv()));
						rloginvo.setSessionKey(new Base64().encode(keyExMsg.getKey()));
						rloginvo.setSymmAlgo(keyExMsg.getKeyAlg());
						rloginvo.setUserId(pBplcvo.getUserId());
						rloginvo.setUserIp(reqvo.getLocalAddr());
			    		
						if(!mdwrManageService.updateEncryptKey(rloginvo)){
							resvo.addAttribute( egovMessageSource.getMessage("login.user.007")); // 처리중오류가발생하였습니다.
							return Const.JSON_PAGE;
						}
						
					}
				}
				
				resvo.addAttribute(keyExMsg.getKey(), keyExMsg.getIv(), keyExMsg.getKeyAlg());
				return Const.JSON_PAGE;
				
			//} catch (Exception e) {
			//	log.debug(e);
			//	resvo.addAttribute("00002"); // 에러가 발생하였습니다.
			//	return Const.JSON_PAGE;
			//}
			
			// body 암호화
			
			
		} catch (MagicLineCSException e) {
			log.debug(e);
			resvo.addAttribute(egovMessageSource.getMessage("pki.svr.0002")); // 암복호화 오류
			return Const.JSON_PAGE;
		}
		
	}
}
