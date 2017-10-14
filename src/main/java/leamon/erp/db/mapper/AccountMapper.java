package leamon.erp.db.mapper;

import java.util.List;

import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import leamon.erp.model.AccountInfo;

@CacheNamespace(implementation = org.mybatis.caches.ehcache.EhcacheCache.class)
public interface AccountMapper {
	
	final String getAll = "SELECT * FROM ACCOUNT_INFO WHERE ISENABLE = TRUE"; 
	final String getById = "SELECT * FROM ACCOUNT_INFO WHERE ID = #{id}";
	
	final String insert = "INSERT INTO ACCOUNT_INFO (NAME, NICKNAME, GST_TIN_NUMBER, TRANSPORT, PHONE, HOUSENUBER, STREET, CITY, STATE, PINCODE, COUNTRY, LANDMARK, "
							+ " ENGAGEDATE, PAN, LICENCE, DESCRIPTION, IMAGEPATH, CREATEDDATE, LASTUPDATED, ISENABLE) "
						+ "VALUES (#{name}, #{nickName}, #{gstNumber}, #{transport}, #{phone}, #{houseShopNumber}, #{street}, #{city}, #{state}, #{pinCode}, #{country}, "
						+ "#{landMark}, #{engagedDate}, #{panCard}, #{licence}, #{description}, #{imagePath}, #{createdDate}, #{lastUpdated}, #{isEnable})";
	
	final String update = "UPDATE ACCOUNT_INFO SET NAME = #{name}, NICKNAME = #{nickName}, GST_TIN_NUMBER = #{gstNumber}, TRANSPORT = #{transport},  PHONE = #{phone}, HOUSENUBER = #{houseShopNumber}, STREET = #{street}, "
						+ "CITY = #{city}, STATE = #{state}, PINCODE = #{pinCode}, "
						+ "COUNTRY = #{country}, LANDMARK = #{landMark}, ENGAGEDATE = #{engagedDate}, "
						+ "PAN = #{panCard}, LICENCE = #{licence}, DESCRIPTION = #{description}, IMAGEPATH = #{imagePath}, "
						+ "LASTUPDATED = #{lastUpdated} "
						+ " WHERE ID = #{id}";
	
	final String deleteById = "DELETE from ACCOUNT_INFO WHERE ID = #{id}";
	
	final String disableByID = "UPDATE ACCOUNT_INFO SET ISENABLE = FALSE WHERE ID = #{id}";
	
	final String getByNameCity = "SELECT * FROM ACCOUNT_INFO WHERE NAME = #{name} AND CITY = #{city}";

	@Select(getAll)
	   @Results(value = {
	      @Result(property = "id", column = "ID"),
	      @Result(property = "name", column = "NAME"),
	      @Result(property = "nickName", column = "NICKNAME"),
	      @Result(property = "gstNumber", column = "GST_TIN_NUMBER"),
	      @Result(property = "transport", column = "TRANSPORT"),
	      @Result(property = "phone", column = "PHONE"),
	      @Result(property = "houseShopNumber", column = "HOUSENUBER"),
	      @Result(property = "street", column = "STREET"),
	      @Result(property = "city", column = "CITY"),
	      @Result(property = "state", column = "STATE"),
	      @Result(property = "pinCode", column = "PINCODE"),
	      @Result(property = "country", column = "COUNTRY"),
	      @Result(property = "landMark", column = "LANDMARK"),
	      @Result(property = "engagedDate", column = "ENGAGEDATE"),
	      @Result(property = "panCard", column = "PAN"),
	      @Result(property = "licence", column = "LICENCE"),
	      @Result(property = "description", column = "DESCRIPTION"),
	      @Result(property = "imagePath", column = "IMAGEPATH"),
	      @Result(property = "createdDate", column = "CREATEDDATE"),
	      @Result(property = "lastUpdated", column = "LASTUPDATED"),
	      @Result(property = "isEnable", column = "ISENABLE")
	})
	public List<AccountInfo> getAll();
	
	@Update(update)
	void update(AccountInfo accountInfo);

	@Update(disableByID)
	void disableStockByID(AccountInfo accountInfo);
	
	@Delete(deleteById)
	void delete(int id);
	  
	@Insert(insert)
	@Options(useGeneratedKeys = true, keyProperty = "id")
	void insert(AccountInfo accountInfo);
}
