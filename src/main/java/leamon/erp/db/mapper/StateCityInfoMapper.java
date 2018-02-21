package leamon.erp.db.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import leamon.erp.model.StateCityInfo;
import leamon.erp.model.StockItem;

public interface StateCityInfoMapper {

	final String getAll = "SELECT * FROM STATECITYINFO WHERE ISENABLE = TRUE ORDER BY LASTUPDATED DESC";
	final String getAllIncludingDisabled = "SELECT * FROM STATECITYINFO ORDER BY ID";
	final String getById = "SELECT * FROM STATECITYINFO WHERE ID = #{id}";
	
	/*Release 3.6 (By : Manish Kumar Mishra) */
	final String insert = "INSERT INTO STATECITYINFO (ID, CITY, STATE, STATECODE, ABBREVIATIONS, CREATEDDATE, LASTUPDATED, ISENABLE) "
			+ "VALUES (#{id}, #{city}, #{state}, #{stateCode}, #{abbreviations}, #{createdDate}, #{lastUpdatedDate}, #{isEnable} )";
	
	final String update = "UPDATE STATECITYINFO SET CITY = #{city}, STATE = #{state}, STATECODE = #{stateCode}, ABBREVIATIONS = #{abbreviations}, "
			+ " LASTUPDATED = #{lastUpdatedDate}  WHERE ID = #{id}";
	
	final String deleteById = "DELETE from STATECITYINFO WHERE ID = #{id}";
	
	final String disableByID = "UPDATE STATECITYINFO SET ISENABLE = FALSE WHERE ID = #{id}";
	
	final String distinctState = "SELECT DISTINCT STATE, STATECODE, ABBREVIATIONS FROM STATECITYINFO WHERE ISENABLE = TRUE";
	/*End*/
	
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
	
	@Select(getAllIncludingDisabled)
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
	public List<StateCityInfo> getAllIncludingDisabledID() throws Exception;
	
	@Select(distinctState)
	   @Results(value = {
	      @Result(property = "state", column = "STATE"),
	      @Result(property = "stateCode", column = "STATECODE"),
	      @Result(property = "abbreviations", column = "ABBREVIATIONS"),
	})
	public List<StateCityInfo> getAllDistinctState() throws Exception;
	
	@Insert(insert)
	@Options(useGeneratedKeys = true, keyProperty = "id")
	void insert(StateCityInfo stateCityInfo) throws Exception;
	
	@Update(update)
	void update(StateCityInfo stateCityInfo) throws Exception;

	@Update(disableByID)
	void disableStateCityInfoByID(StateCityInfo stateCityInfo) throws Exception;
	
	@Delete(deleteById)
	void delete(int id) throws Exception;
	
}
