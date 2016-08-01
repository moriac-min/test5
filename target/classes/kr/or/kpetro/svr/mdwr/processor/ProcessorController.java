package kr.or.kpetro.svr.mdwr.processor;

import kr.or.kpetro.svr.cmm.service.RequestVO;
import kr.or.kpetro.svr.cmm.service.ResponseVO;
import kr.or.kpetro.svr.service.BusinessPlaceLicenseVO;

import org.springframework.ui.ModelMap;

public abstract class ProcessorController {
	//public abstract EgovMap	requestParse(HttpServletRequest request)throws Throwable;
	/**프로세스 비즈니스 로직 처리자*/
	public abstract ResponseVO process(RequestVO reqvo, BusinessPlaceLicenseVO licensevo, ModelMap model)throws Throwable;
	
	public ResponseVO doProcess(RequestVO reqvo, BusinessPlaceLicenseVO licensevo,
			ModelMap model) throws Throwable{
		// TODO Auto-generated method stub
		// 나의 상속자가 환경 설정값을 읽어들이고
		// 나는 값을 받아, 클라이언트에 전달한다.
		
		model.clear();
		
		// 서비스아이디parse
		reqvo.addBusinessInfo();
		ResponseVO resvo = process(reqvo, licensevo, model); 
		
		return resvo; // 자식이 구현
	}
}
