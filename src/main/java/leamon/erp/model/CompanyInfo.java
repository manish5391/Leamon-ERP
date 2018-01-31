package leamon.erp.model;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Data
@Builder
public class CompanyInfo {
	private Integer id;
	
	private String companyName;
	private String companySupplierOf;
	private String companyAddress;
	private String companyCity;
	private String companyState;
	private String companyStateCode;
	private String companyCountry;
	private String companyGstNumber;
	private String companyBillPrefix;
	private String companyMobile;
	private String companyBankName;
	private String companyAccountNo;
	private String companyBankIfsc;
	private String companyBankBranch;
	
	private Timestamp createdDate;
	private Timestamp lastupdated;
	private boolean isEnable;
}
