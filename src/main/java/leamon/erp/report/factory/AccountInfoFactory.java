package leamon.erp.report.factory;

import java.util.ArrayList;
import java.util.List;

import leamon.erp.model.AccountInfo;

public class AccountInfoFactory {

	public static List addAccountInfoList(){
		List companyList = new ArrayList();
		
		AccountInfo companyInfo = AccountInfo.builder()
				.name("National Lock House")
				.city("Vijaywada")
				.transport("TLS")
				.build();
		
		companyList.add(companyInfo);
		
		return companyList;
	}
}
