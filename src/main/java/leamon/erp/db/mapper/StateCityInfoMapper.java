package leamon.erp.db.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import leamon.erp.model.StateCityInfo;

public interface StateCityInfoMapper {

	final String getAll = "SELECT * FROM STATECITYINFO WHERE ISENABLE = TRUE ORDER BY ID"; 
	final String getById = "SELECT * FROM STATECITYINFO WHERE ID = #{id}";
	
	@Select(getAll)
	   @Results(value = {
	      @Result(property = "id", column = "ID"),
	      @Result(property = "city", column = "CITY"),
	      @Result(property = "state", column = "STATE"),
	      @Result(property = "stateCode", column = "STATECODE"),
	      @Result(property = "abbreviations", column = "ABBREVIATIONS"),
	      @Result(property = "isEnable", column = "ISENABLE"),
	      @Result(property = "createdDate", column = "CREATEDDATE"),
	      @Result(property = "lastUpdatedDate", column = "LASTUPDATED")
	})
	public List<StateCityInfo> getAll() throws Exception;
}
