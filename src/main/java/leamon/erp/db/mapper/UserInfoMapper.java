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

import leamon.erp.model.UserInfo;
import leamon.erp.model.UserInfo;

/**
 * @author Leamon India
 * @date 29 MAY, 2017
 */
public interface UserInfoMapper {
	final String getAll = "SELECT * FROM USER_INFO WHERE ISENABLE = TRUE ORDER BY ID"; 
	final String getById = "SELECT * FROM USER_INFO WHERE ID = #{id}";
	
	final String matchUser = "SELECT * FROM USER_INFO WHERE USERNAME = #{username} AND PASSWORD = #{password}";
	
	final String insert = "INSERT INTO USER_INFO (USERNAME, PASSWORD, CREATEDDATE, LASTUPDATEDDATE, ISENABLE) "
						+ "VALUES (#{username}, #{password}, #{createdDate}, #{lastUpdatedDate}, #{isEnable} )";
	
	final String updateUserPassword = "UPDATE USER_INFO SET USERNAME = #{name}, PASSWORD = #{password} "
						+ ", LASTUPDATEDDATE = #{lastUpdatedDate}  WHERE ID = #{id}";
	
	final String updatePassword = "UPDATE USER_INFO SET PASSWORD = #{password} "
			+ ", LASTUPDATEDDATE = #{lastUpdatedDate}  WHERE ID = #{id} AND USERNAME = #{name}";
	
	final String deleteById = "DELETE from USER_INFO WHERE ID = #{id}";
	
	final String disableByID = "UPDATE USER_INFO SET ISENABLE = FALSE WHERE ID = #{id}";
	
	//@Options(flushCache= FlushCachePolicy.TRUE)
	@Select(getAll)
	   @Results(value = {
	      @Result(property = "id", column = "ID"),
	      @Result(property = "username", column = "NAME"),
	      @Result(property = "password", column = "PRODUCTCODE"),
	      @Result(property = "isEnable", column = "ISENABLE"),
	      @Result(property = "createdDate", column = "CREATEDDATE"),
	      @Result(property = "lastUpdatedDate", column = "LASTUPDATEDDATE")
	      
	})
	public List<UserInfo> getAll() throws Exception;
	
	
	@Select(matchUser)
	 @Results(value = {
		      @Result(property = "id", column = "ID"),
		      @Result(property = "username", column = "NAME"),
		      @Result(property = "password", column = "PRODUCTCODE"),
		      @Result(property = "isEnable", column = "ISENABLE"),
		      @Result(property = "createdDate", column = "CREATEDDATE"),
		      @Result(property = "lastUpdatedDate", column = "LASTUPDATEDDATE")
	})
	public UserInfo matchUser(UserInfo item) throws Exception;

	@Update(updatePassword)
	void update(UserInfo userInfo) throws Exception;

	@Update(disableByID)
	void disableStockByID(UserInfo userInfo) throws Exception;
	
	@Delete(deleteById)
	void delete(int id) throws Exception;
	  
	@Insert(insert)
	@Options(useGeneratedKeys = true, keyProperty = "id")
	void insert(UserInfo userInfo) throws Exception;
	
}
