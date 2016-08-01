package kr.or.kpetro.svr.service;

import kr.or.kpetro.com.Const;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import com.dreamsecurity.magicline.cs.exception.MagicLineCSException;
import com.dreamsecurity.magicline.cs.server.MagicLineCS;
import com.dreamsecurity.magicline.cs.server.MagicLineCipher;

/**암복호화 처리자(PKI)*/
@Service("encryptService")
public class EncryptService implements InitializingBean{
	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		System.out.println("ApplicationStart()");

		// 1. 환경 설정값 로드
		//   #. 응용프로그램의 환경설정값으로부터 다음 변수값들을 로드한다.
		//   #. 실제 구현될 응용프로그램에서는 응용프로그램의 환경설정으로부터 값을 얻도록 구현한다.
		//
		System.out.println("... 환경 정보 읽기.");
		String pathWorkDir   = Const.PKI_WORK_DIR;			// 라이선스파일이 저장된 경로. 응용프로그램 프로세스가 쓰기 권한을 가진 경로여야 한다.
		String pathSvrKmCert = Const.PKI_CERT;	// 암호화용 서버 인증서의 경로. 
		String pathSvrKmKey  = Const.PKI_KEY;		// 암호화용 서버 개인키의 경로.
		String keyPasswd     = Const.PKI_PASS;				// 개인키 비밀번호.
		String pathTrustRoot1 = Const.PKI_TRUST_ROOT1;
		String pathTrustRoot2 = Const.PKI_TRUST_ROOT2;

		
		// 2. MagicLineCS 서버 클래스 초기화
		//   #. 라이선스 경로를 지정해서 MagicLine을 초기화한다.
		//
		try {
			System.out.println("... MagicLineCS 초기화.");
			MagicLineCS.initialize(pathWorkDir);
			MagicLineCS.loadKmCert(pathSvrKmCert);
			MagicLineCS.loadKmPriKey(pathSvrKmKey, keyPasswd);
			MagicLineCS.addTrustCert(pathTrustRoot1);
			MagicLineCS.addTrustCert(pathTrustRoot2);
		} catch (MagicLineCSException e) {
			// 오류 처리: 실제 구현에서는 응용프로그램에 알맞는 오류 처리 코드를 추가한다.
			e.printStackTrace();
		}
	}
	
	/**서버인증서 요청
	 * @throws MagicLineCSException 
	 * */
	public EncryptVO getServerCertificate() throws MagicLineCSException{
		
		byte[] challenge  = null;
    	MagicLineCipher cipher = new MagicLineCipher();
		challenge = cipher.generateRandom(20);
		
		EncryptVO vo = new EncryptVO();
		vo.setChallenge(challenge);
		vo.setCertificate(MagicLineCS.getServerCertificate());
		
		return vo;
	}
	
	
}
