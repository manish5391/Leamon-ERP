package leamon.erp.db.mapper;

import java.util.List;

import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import leamon.erp.model.LeamonProperty;

@CacheNamespace(implementation = org.mybatis.caches.ehcache.EhcacheCache.class)
public interface LeamonPropertyMapper {
	
	final String getAll = "SELECT * FROM LEAMON_PROPERTY WHERE ISENABLE = TRUE"; 
	final String getById = "SELECT * FROM LEAMON_PROPERTY WHERE ID = #{id}";

	@Select(getAll)
	   @Results(value = {
	      @Result(property = "id", column = "ID"),
	      @Result(property = "propertyname", column = "PROPERTYNAME"),
	      @Result(property = "propertyvalue", column = "PROPERTYVALUE"),
	      @Result(property = "description", column = "description"),
	      
	      @Result(property = "createdDate", column = "CREATEDDATE"),
	      @Result(property = "lastUpdated", column = "LASTUPDATED"),
	      @Result(property = "isenable", column = "ISENABLE")
	})
	public List<LeamonProperty> getAll();
}
