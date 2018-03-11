package leamon.erp.db.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import leamon.erp.model.AccountInfo;
import leamon.erp.model.OpeningBalanceInfo;

/**
 * @version 1.0
 * @author Manish Kumar Mishra
 * @since Leamon-ERP : 3.2.2
 * 
 * @date JAN 27,2018 
 * @author mmishra
 *
 */
public interface OpeningBalanceMapper {
	final String getAll = "SELECT * FROM OPENING_BALANCE_INFO WHERE ISENABLE = TRUE ORDER BY ID"; 
	final String getById = "SELECT * FROM OPENING_BALANCE_INFO WHERE ID = #{id}";
	
	final String insert = "INSERT INTO OPENING_BALANCE_INFO (PARTYINFOID, BILLNUMBER, BILLDATE, OPENINGBALANCEAMOUNT, RECEIVEDOPENINGBALANCEAMOUNT, "
						+ "REMAININGOPENINGBALANCEAMOUNT, REMARK, TYPE, STATUS, CREATEDDATE, LASTUPDATED, ISENABLE ) "
			
						+ "VALUES (#{partyinfoid}, #{billnumber}, #{billdate}, #{openingbalanceamount}, #{receivedopeningbalanceamount}, #{remainingopeningbalanceamount}, "
						+ "#{remark}, #{type}, "
						+ "#{status}, #{createdDate}, #{lastUpdated}, #{isEnable} )";
	
	final String update = "UPDATE OPENING_BALANCE_INFO SET PARTYINFOID = #{partyinfoid}, BILLNUMBER = #{billnumber}, BILLDATE = #{billdate}, "
			+ " OPENINGBALANCEAMOUNT = #{openingbalanceamount}, "
			+ "RECEIVEDOPENINGBALANCEAMOUNT = #{receivedopeningbalanceamount}, "
			+ "REMAININGOPENINGBALANCEAMOUNT = #{remainingopeningbalanceamount}, REMARK = #{remark}, TYPE = #{type}, "
			+ " STATUS = #{status}, "
			+ "LASTUPDATED = #{lastUpdated}  WHERE ID = #{id}";
	
	final String deleteById = "DELETE from OPENING_BALANCE_INFO WHERE ID = #{id}";
	
	final String disableByID = "UPDATE OPENING_BALANCE_INFO SET ISENABLE = FALSE WHERE ID = #{id}";
	
	/*---------Criteria Sql------------*/
	String selectByPartyName = "SELECT * FROM OPENING_BALANCE_INFO WHERE ISENABLE = TRUE AND PARTYINFOID = #{partyInfoID} ORDER BY LASTUPDATED ";
	String selectByStartDate = "SELECT * FROM OPENING_BALANCE_INFO WHERE ISENABLE = TRUE AND TO_DATE(LASTUPDATED, 'DY DD/MM/YYYY') = #{startDate, jdbcType=DATE } "
			+ " ORDER BY LASTUPDATED ";
	String selectByStartEndDate = "SELECT * FROM OPENING_BALANCE_INFO WHERE ISENABLE = TRUE AND TO_DATE(LASTUPDATED, 'DY DD/MM/YYYY') BETWEEN #{startDate, jdbcType=DATE }"
			+ " AND #{endDate, jdbcType=DATE }  ORDER BY LASTUPDATED";
	String selectByStartDatePartyName = "SELECT * FROM OPENING_BALANCE_INFO WHERE ISENABLE = TRUE AND TO_DATE(LASTUPDATED, 'DY DD/MM/YYYY') = #{startDate, jdbcType=DATE } "
			+ " AND PARTYINFOID = #{partyInfoID} ORDER BY LASTUPDATED ";
	String selectByStartEndDatePartyName = "SELECT * FROM OPENING_BALANCE_INFO WHERE ISENABLE = TRUE AND TO_DATE(CREATEDDATE, 'DY DD/MM/YYYY') "
			+ "BETWEEN #{startDate, jdbcType=DATE }  AND #{endDate, jdbcType=DATE } AND PARTYINFOID = #{partyInfoID} ORDER BY LASTUPDATED";
	/*---------End--------------------*/
	
	//@Options(flushCache= FlushCachePolicy.TRUE)
	@Select(getAll)
	   @Results(value = {
	      @Result(property = "id", column = "ID"),
	      @Result(property = "partyinfoid", column = "PARTYINFOID"),
	      @Result(property = "billnumber", column = "BILLNUMBER"),
	      @Result(property = "billdate", column = "BILLDATE"),
	      @Result(property = "openingbalanceamount", column = "OPENINGBALANCEAMOUNT"),
	      @Result(property = "receivedopeningbalanceamount", column = "RECEIVEDOPENINGBALANCEAMOUNT"),
	      @Result(property = "remainingopeningbalanceamount", column = "REMAININGOPENINGBALANCEAMOUNT"),
	      @Result(property = "remark", column = "REMARK"),
	      @Result(property = "type", column = "TYPE"),
	      @Result(property = "status", column = "STATUS"),
	      
	      @Result(property = "createdDate", column = "CREATEDDATE"),
	      @Result(property = "lastUpdated", column = "LASTUPDATED"),
	      @Result(property = "isEnable", column = "ISENABLE"),
	      
	      @Result(property = "accountInfo", javaType=AccountInfo.class, column = "PARTYINFOID"
	      , one =@One(select="getAccountInfo"))
	      
	})
	public List<OpeningBalanceInfo> getAll() throws Exception;

	@Update(update)
	void update(OpeningBalanceInfo openingBalanceInfo) throws Exception;

	
	@Delete(deleteById)
	void delete(int id) throws Exception;
	  
	@Insert(insert)
	@Options(useGeneratedKeys = true, keyProperty = "id")
	void insert(OpeningBalanceInfo openingBalanceInfo) throws Exception;
	
	@Select("SELECT * FROM ACCOUNT_INFO WHERE ID = #{PARTYINFOID}")
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
	public AccountInfo getAccountInfo();
	
	/*-----Release 3.8------Filtered Sql------------------*/
	@Select(selectByPartyName)
	   @Results(value = {
	      @Result(property = "id", column = "ID"),
	      @Result(property = "partyinfoid", column = "PARTYINFOID"),
	      @Result(property = "billnumber", column = "BILLNUMBER"),
	      @Result(property = "billdate", column = "BILLDATE"),
	      @Result(property = "openingbalanceamount", column = "OPENINGBALANCEAMOUNT"),
	      @Result(property = "receivedopeningbalanceamount", column = "RECEIVEDOPENINGBALANCEAMOUNT"),
	      @Result(property = "remainingopeningbalanceamount", column = "REMAININGOPENINGBALANCEAMOUNT"),
	      @Result(property = "remark", column = "REMARK"),
	      @Result(property = "type", column = "TYPE"),
	      @Result(property = "status", column = "STATUS"),
	      
	      @Result(property = "createdDate", column = "CREATEDDATE"),
	      @Result(property = "lastUpdated", column = "LASTUPDATED"),
	      @Result(property = "isEnable", column = "ISENABLE"),
	      
	      @Result(property = "accountInfo", javaType=AccountInfo.class, column = "PARTYINFOID"
	      , one =@One(select="getAccountInfo"))
	      
	})
	public List<OpeningBalanceInfo> getAllOpeningBalanceByPartyName(@Param("partyInfoID")String partyInfoID) throws Exception;
	
	@Select(selectByStartDate)
	   @Results(value = {
	      @Result(property = "id", column = "ID"),
	      @Result(property = "partyinfoid", column = "PARTYINFOID"),
	      @Result(property = "billnumber", column = "BILLNUMBER"),
	      @Result(property = "billdate", column = "BILLDATE"),
	      @Result(property = "openingbalanceamount", column = "OPENINGBALANCEAMOUNT"),
	      @Result(property = "receivedopeningbalanceamount", column = "RECEIVEDOPENINGBALANCEAMOUNT"),
	      @Result(property = "remainingopeningbalanceamount", column = "REMAININGOPENINGBALANCEAMOUNT"),
	      @Result(property = "remark", column = "REMARK"),
	      @Result(property = "type", column = "TYPE"),
	      @Result(property = "status", column = "STATUS"),
	      
	      @Result(property = "createdDate", column = "CREATEDDATE"),
	      @Result(property = "lastUpdated", column = "LASTUPDATED"),
	      @Result(property = "isEnable", column = "ISENABLE"),
	      
	      @Result(property = "accountInfo", javaType=AccountInfo.class, column = "PARTYINFOID"
	      , one =@One(select="getAccountInfo"))
	      
	})
	public List<OpeningBalanceInfo> getAllOpeningBalanceByStartDate(@Param("startDate") java.util.Date startDate) throws Exception;
	
	@Select(selectByStartEndDate)
	   @Results(value = {
	      @Result(property = "id", column = "ID"),
	      @Result(property = "partyinfoid", column = "PARTYINFOID"),
	      @Result(property = "billnumber", column = "BILLNUMBER"),
	      @Result(property = "billdate", column = "BILLDATE"),
	      @Result(property = "openingbalanceamount", column = "OPENINGBALANCEAMOUNT"),
	      @Result(property = "receivedopeningbalanceamount", column = "RECEIVEDOPENINGBALANCEAMOUNT"),
	      @Result(property = "remainingopeningbalanceamount", column = "REMAININGOPENINGBALANCEAMOUNT"),
	      @Result(property = "remark", column = "REMARK"),
	      @Result(property = "type", column = "TYPE"),
	      @Result(property = "status", column = "STATUS"),
	      
	      @Result(property = "createdDate", column = "CREATEDDATE"),
	      @Result(property = "lastUpdated", column = "LASTUPDATED"),
	      @Result(property = "isEnable", column = "ISENABLE"),
	      
	      @Result(property = "accountInfo", javaType=AccountInfo.class, column = "PARTYINFOID"
	      , one =@One(select="getAccountInfo"))
	      
	})
	public List<OpeningBalanceInfo> getAllOpeningBalanceByStartEndDate(@Param("startDate") Date startDate, @Param("endDate") Date endDate) throws Exception;
	
	@Select(selectByStartDatePartyName)
	   @Results(value = {
	      @Result(property = "id", column = "ID"),
	      @Result(property = "partyinfoid", column = "PARTYINFOID"),
	      @Result(property = "billnumber", column = "BILLNUMBER"),
	      @Result(property = "billdate", column = "BILLDATE"),
	      @Result(property = "openingbalanceamount", column = "OPENINGBALANCEAMOUNT"),
	      @Result(property = "receivedopeningbalanceamount", column = "RECEIVEDOPENINGBALANCEAMOUNT"),
	      @Result(property = "remainingopeningbalanceamount", column = "REMAININGOPENINGBALANCEAMOUNT"),
	      @Result(property = "remark", column = "REMARK"),
	      @Result(property = "type", column = "TYPE"),
	      @Result(property = "status", column = "STATUS"),
	      
	      @Result(property = "createdDate", column = "CREATEDDATE"),
	      @Result(property = "lastUpdated", column = "LASTUPDATED"),
	      @Result(property = "isEnable", column = "ISENABLE"),
	      
	      @Result(property = "accountInfo", javaType=AccountInfo.class, column = "PARTYINFOID"
	      , one =@One(select="getAccountInfo"))
	      
	})
	public List<OpeningBalanceInfo> getAllOpeningBalanceByStartDatePartyName(@Param("startDate") Date startDate, @Param("partyInfoID")String partyInfoID) throws Exception;
	
	@Select(selectByStartEndDatePartyName)
	   @Results(value = {
	      @Result(property = "id", column = "ID"),
	      @Result(property = "partyinfoid", column = "PARTYINFOID"),
	      @Result(property = "billnumber", column = "BILLNUMBER"),
	      @Result(property = "billdate", column = "BILLDATE"),
	      @Result(property = "openingbalanceamount", column = "OPENINGBALANCEAMOUNT"),
	      @Result(property = "receivedopeningbalanceamount", column = "RECEIVEDOPENINGBALANCEAMOUNT"),
	      @Result(property = "remainingopeningbalanceamount", column = "REMAININGOPENINGBALANCEAMOUNT"),
	      @Result(property = "remark", column = "REMARK"),
	      @Result(property = "type", column = "TYPE"),
	      @Result(property = "status", column = "STATUS"),
	      
	      @Result(property = "createdDate", column = "CREATEDDATE"),
	      @Result(property = "lastUpdated", column = "LASTUPDATED"),
	      @Result(property = "isEnable", column = "ISENABLE"),
	      
	      @Result(property = "accountInfo", javaType=AccountInfo.class, column = "PARTYINFOID"
	      , one =@One(select="getAccountInfo"))
	      
	})
	public List<OpeningBalanceInfo> getAllOpeningBalanceByStartEndDatePartyName(@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("partyInfoID")String partyInfoID) throws Exception;
	/*-----------End------------------*/
	
}
