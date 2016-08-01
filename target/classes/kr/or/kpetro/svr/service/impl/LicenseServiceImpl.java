package kr.or.kpetro.svr.service.impl;

import javax.annotation.Resource;

import kr.or.kpetro.svr.service.BusinessPlaceLicenseVO;
import kr.or.kpetro.svr.service.LicenseService;

import org.springframework.stereotype.Service;

/**라이센스관리자*/
@Service("licenseService")
public class LicenseServiceImpl implements LicenseService{

	@Resource(name = "BusinessPlaceLicenseDAO")
    private BusinessPlaceLicenseDAO licenseDAO;
	
	@Override
	public BusinessPlaceLicenseVO LicenseInfo(BusinessPlaceLicenseVO businessPlaceLicenseVO) throws Exception {
		// TODO Auto-generated method stub
		return  licenseDAO.getLicenseInfo(businessPlaceLicenseVO);
	}
}
