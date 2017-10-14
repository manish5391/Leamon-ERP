package leamon.erp.db.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import leamon.erp.model.AccountInfo;
import leamon.erp.model.CompanyInfo;

public interface CompanyInfoMapper {

	
	final String getAll = "SELECT * FROM COMPANY_INFO WHERE ISENABLE = TRUE"; 
	final String getById = "SELECT * FROM COMPANY_INFO WHERE ID = #{id}";
	
	final String insert = "INSERT INTO COMPANY_INFO (NAME, SUPPLIER, ADDRESS, CITY, STATE, STATECODE, GST_TIN_NUMBER, BILLPREFIX, PHONE, BANKNAME, BANKACCOUNT, IFSC, "
							+ " BANKBRANCH, COUNTRY, CREATEDDATE, LASTUPDATED, ISENABLE) "
						+ "VALUES (#{companyName}, #{companySupplierOf}, #{companyAddress}, #{companyCity}, #{companyState}, #{companyStateCode}, #{companyGstNumber}, "
						+ "#{companyBillPrefix}, #{companyMobile}, #{companyBankName}, #{companyAccountNo}, #{companyBankIfsc}, "
						+ "#{companyBankBranch}, #{companyCountry}, #{createdDate}, #{lastUpdated}, #{isEnable})";
	
	final String update = "UPDATE COMPANY_INFO SET SUPPLIER = #{companySupplierOf}, ADDRESS = #{companyAddress}, CITY = #{companyCity},  STATE = #{companyState}, STATECODE = #{companyStateCode}, GST_TIN_NUMBER = #{companyGstNumber}, "
						+ "BILLPREFIX = #{companyBillPrefix}, PHONE = #{companyMobile}, BANKNAME = #{companyBankName}, "
						+ "BANKACCOUNT = #{companyAccountNo}, IFSC = #{companyBankIfsc}, BANKBRANCH = #{companyBankBranch}, "
						+ "COUNTRY = #{companyCountry}, "
						+ "LASTUPDATED = #{lastupdated} "
						+ " WHERE ID = #{id}";
	
	final String deleteById = "DELETE from COMPANY_INFO WHERE ID = #{id}";
	
	final String disableByID = "UPDATE COMPANY_INFO SET ISENABLE = FALSE WHERE ID = #{id}";
	
	final String getByNameCity = "SELECT * FROM COMPANY_INFO WHERE NAME = #{name} AND CITY = #{city}";

	@Select(getAll)
	   @Results(value = {
	      @Result(property = "id", column = "ID"),
	      @Result(property = "companyName", column = "NAME"),
	      @Result(property = "companySupplierOf", column = "SUPPLIER"),
	      @Result(property = "companyAddress", column = "ADDRESS"),
	      @Result(property = "companyCity", column = "CITY"),
	      @Result(property = "companyState", column = "STATE"),
	      @Result(property = "companyStateCode", column = "STATECODE"),
	      @Result(property = "companyGstNumber", column = "GST_TIN_NUMBER"),
	      @Result(property = "companyBillPrefix", column = "BILLPREFIX"),
	      @Result(property = "companyMobile", column = "PHONE"),
	      @Result(property = "companyBankName", column = "BANKNAME"),
	      @Result(property = "companyAccountNo", column = "BANKACCOUNT"),
	      @Result(property = "companyBankIfsc", column = "IFSC"),
	      @Result(property = "companyBankBranch", column = "BANKBRANCH"),
	      @Result(property = "companyCountry", column = "COUNTRY"),
	      @Result(property = "createdDate", column = "CREATEDDATE"),
	      @Result(property = "lastupdated", column = "LASTUPDATED"),
	      @Result(property = "isEnable", column = "ISENABLE")
	})
	public List<CompanyInfo> getAll();
	
	@Update(update)
	void update(CompanyInfo accountInfo);

	@Update(disableByID)
	void disableStockByID(CompanyInfo accountInfo);
	
	@Delete(deleteById)
	void delete(int id);
	  
	@Insert(insert)
	@Options(useGeneratedKeys = true, keyProperty = "id")
	void insert(CompanyInfo accountInfo);

}
