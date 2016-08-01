package kr.or.kpetro.com.cmm.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import kr.or.kpetro.com.Const;
import kr.or.kpetro.com.cmm.service.EgovUserDetailsService;
import kr.or.kpetro.svr.cmm.service.RequestVO;
import egovframework.rte.fdl.cmmn.AbstractServiceImpl;

/**
 * 
 * @author 공통서비스 개발팀 서준식
 * @since 2011. 6. 25.
 * @version 1.0
 * @see
 *
 * <pre>
 * 개정이력(Modification Information) 
 * 
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2011. 8. 12.    서준식        최초생성
 *  2014.03.10 챌린지값 관리
 *  </pre>
 */

public class EgovUserDetailsSessionServiceImpl extends AbstractServiceImpl implements
		EgovUserDetailsService {

	public Object getAuthenticatedUser() {
		return RequestContextHolder.getRequestAttributes().getAttribute(Const.SVC_CHALLENGE, RequestAttributes.SCOPE_SESSION);
	}

	public List<String> getAuthorities() {

		// 권한 설정을 리턴한다.
		List<String> listAuth = new ArrayList<String>();

		return listAuth;
	}

	public Boolean isAuthenticated() {
		// 인증된 유저인지 확인한다.

		if (RequestContextHolder.getRequestAttributes() == null) {
			return false;
		} else {

			if (RequestContextHolder.getRequestAttributes().getAttribute(
					Const.SVC_CHALLENGE, RequestAttributes.SCOPE_SESSION) == null) {
				return false;
			} else {
				// challenge 존재시
				return true;
			}
		}
	}

	@Override
	public RequestVO getRequestVO() {
		return (RequestVO)RequestContextHolder.getRequestAttributes().getAttribute(Const.VO_REQUEST, RequestAttributes.SCOPE_REQUEST);
	}
}
