package leamon.erp.db.mapper;

import java.util.List;

import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import leamon.erp.model.OperationInfo;

@CacheNamespace(implementation = org.mybatis.caches.ehcache.EhcacheCache.class)
public interface OperationInfoMapper {
	
	final String getAll = "SELECT * FROM OPERATIONS_INFO WHERE ISENABLE = TRUE"; 
	final String getById = "SELECT * FROM OPERATIONS_INFO WHERE ID = #{id}";
	
	final String getAllByParam = "SELECT *  FROM OPERATIONS_INFO WHERE KEY = #{key} AND PROPERTYNAME = #{propertyname} AND  ISENABLE = TRUE";
	
	final String deleteById = "DELETE from OPERATIONS_INFO WHERE ID = #{id}";
	
	final String disableByID = "UPDATE OPERATIONS_INFO SET ISENABLE = FALSE WHERE ID = #{id}";
	
	final String getByNameCity = "SELECT * FROM OPERATIONS_INFO WHERE NAME = #{name} AND CITY = #{city}";

	@Select(getAll)
	   @Results(value = {
	      @Result(property = "id", column = "ID"),
	      @Result(property = "key", column = "KEY"),
	      @Result(property = "propertyname", column = "PROPERTYNAME"),
	      @Result(property = "propertyvalue", column = "PROPERTYVALUE"),
	      @Result(property = "description", column = "DESCRIPTION"),
	      @Result(property = "createdDate", column = "CREATEDDATE"),
	      @Result(property = "lastUpdated", column = "LASTUPDATED"),
	      @Result(property = "isEnable", column = "ISENABLE")
	})
	public List<OperationInfo> getAll();
	
	@Select(getAllByParam)
	   @Results(value = {
	      @Result(property = "id", column = "ID"),
	      @Result(property = "key", column = "KEY"),
	      @Result(property = "propertyname", column = "PROPERTYNAME"),
	      @Result(property = "propertyvalue", column = "PROPERTYVALUE"),
	      @Result(property = "description", column = "DESCRIPTION"),
	      @Result(property = "createdDate", column = "CREATEDDATE"),
	      @Result(property = "lastUpdated", column = "LASTUPDATED"),
	      @Result(property = "isEnable", column = "ISENABLE")
	})
	public OperationInfo getbyParam(OperationInfo info);
	
	/*@Update(update)
	void update(OperationInfo OperationInfo);*/

	@Update(disableByID)
	void disableByID(OperationInfo OperationInfo);
	
	@Delete(deleteById)
	void delete(int id);
	  
}
