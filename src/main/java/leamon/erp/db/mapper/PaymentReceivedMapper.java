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
import leamon.erp.model.PaymentReceivedInfo;

public interface PaymentReceivedMapper {
	final String getAll = "SELECT * FROM PAYMENT_RECEIVED_INFO WHERE ISENABLE = TRUE ORDER BY ID"; 
	final String getById = "SELECT * FROM PAYMENT_RECEIVED_INFO WHERE ID = #{id}";
	
	final String insert = "INSERT INTO PAYMENT_RECEIVED_INFO (PARTYINFOID, RECEIVEDPAYMENT, ADJUSTEDPAYMENT, REMAININGAMOUNT, STATUS, REMARK, TYPE, RECEIVEDDATE, CREATEDDATE, LASTUPDATED, ISENABLE ) "
						+ "VALUES (#{partyInfoID}, #{receivedPayment}, #{adjustedPayment}, #{remainingAmount}, #{status}, #{remark}, #{type}, #{receivedDate}, "
						+ "#{createdDate}, #{lastUpdated}, #{isEnable} )";
	
	final String update = "UPDATE PAYMENT_RECEIVED_INFO SET PARTYINFOID = #{partyInfoID}, RECEIVEDPAYMENT = #{receivedPayment}, RECEIVEDATE = #{receivedDate}, "
						+ "BREMARK = #{bRemark}, "
						+ "WREMARK = #{wRemark},  "
						+ "LASTUPDATEDDATE = #{lastUpdated}  WHERE ID = #{id}";
	
	final String deleteById = "DELETE from PAYMENT_RECEIVED_INFO WHERE ID = #{id}";
	
	final String disableByID = "UPDATE PAYMENT_RECEIVED_INFO SET ISENABLE = FALSE WHERE ID = #{id}";
	
	/*Release 3.8 Filter query */
	final String getAllByPartyName = "SELECT * FROM PAYMENT_RECEIVED_INFO WHERE ISENABLE = TRUE AND PARTYINFOID = #{partyInfoID} ORDER BY ID";
	final String getAllByStartDate= "SELECT * FROM PAYMENT_RECEIVED_INFO WHERE ISENABLE = TRUE AND TO_DATE(RECEIVEDDATE, 'DY DD/MM/YYYY')  "
			+ " = #{startDate, jdbcType=DATE } ORDER BY ID";
	
	final String getAllByStartEndDate= "SELECT * FROM PAYMENT_RECEIVED_INFO WHERE ISENABLE = TRUE AND  TO_DATE(RECEIVEDDATE, 'DY DD/MM/YYYY') "
			+ " BETWEEN #{startDate, jdbcType=DATE } AND #{endDate, jdbcType=DATE } ORDER BY ID";
	final String getAllByType= "SELECT * FROM PAYMENT_RECEIVED_INFO WHERE ISENABLE = TRUE AND TYPE = #{type} ORDER BY ID";
	
	final String getAllByStartDatePartyName= "SELECT * FROM PAYMENT_RECEIVED_INFO WHERE ISENABLE = TRUE AND PARTYINFOID = #{partyInfoID} "
			+ " AND TO_DATE(RECEIVEDDATE, 'DY DD/MM/YYYY')  = #{startDate, jdbcType=DATE } ORDER BY ID";
	final String getAllByStartDateEndDatePartyName= "SELECT * FROM PAYMENT_RECEIVED_INFO WHERE ISENABLE = TRUE AND PARTYINFOID = #{partyInfoID} "
			+ " AND TO_DATE(RECEIVEDDATE, 'DY DD/MM/YYYY') BETWEEN #{startDate, jdbcType=DATE } AND #{endDate, jdbcType=DATE } ORDER BY ID";
	final String getAllByStartDateEndDateType= "SELECT * FROM PAYMENT_RECEIVED_INFO WHERE ISENABLE = TRUE AND  TYPE = #{type} "
			+ " AND TO_DATE(RECEIVEDDATE, 'DY DD/MM/YYYY') BETWEEN #{startDate, jdbcType=DATE } AND #{endDate, jdbcType=DATE } ORDER BY ID";
	final String getAllByStartDateEndDatePartyNamePaymentType= "SELECT * FROM PAYMENT_RECEIVED_INFO WHERE ISENABLE = TRUE "
			+ " AND PARTYINFOID = #{partyInfoID} AND TO_DATE(RECEIVEDDATE, 'DY DD/MM/YYYY') BETWEEN #{startDate, jdbcType=DATE } AND #{endDate, jdbcType=DATE }"
			+ " AND TYPE = #{type} ORDER BY ID";
	/*End*/
	
	//@Options(flushCache= FlushCachePolicy.TRUE)
	@Select(getAll)
	   @Results(value = {
	      @Result(property = "id", column = "ID"),
	      @Result(property = "partyInfoID", column = "PARTYINFOID"),
	      @Result(property = "receivedPayment", column = "RECEIVEDPAYMENT"),
	      @Result(property = "adjustedPayment", column = "ADJUSTEDPAYMENT"),
	      @Result(property = "remainingAmount", column = "REMAININGAMOUNT"),
	      @Result(property = "status", column = "STATUS"),
	      @Result(property = "remark", column = "REMARK"),
	      @Result(property = "type", column = "TYPE"),
	      @Result(property = "receivedDate", column = "RECEIVEDDATE"),
	      
	      @Result(property = "createdDate", column = "CREATEDDATE"),
	      @Result(property = "lastUpdated", column = "LASTUPDATED"),
	      @Result(property = "isEnable", column = "ISENABLE")
	})
	public List<PaymentReceivedInfo> getAll() throws Exception;
	
	@Select(getAll)
	   @Results(value = {
	      @Result(property = "id", column = "ID"),
	      @Result(property = "partyInfoID", column = "PARTYINFOID"),
	      @Result(property = "receivedPayment", column = "RECEIVEDPAYMENT"),
	      @Result(property = "adjustedPayment", column = "ADJUSTEDPAYMENT"),
	      @Result(property = "remainingAmount", column = "REMAININGAMOUNT"),
	      @Result(property = "status", column = "STATUS"),
	      @Result(property = "remark", column = "REMARK"),
	      @Result(property = "type", column = "TYPE"),
	      @Result(property = "receivedDate", column = "RECEIVEDDATE"),
	      
	      @Result(property = "createdDate", column = "CREATEDDATE"),
	      @Result(property = "lastUpdated", column = "LASTUPDATED"),
	      @Result(property = "isEnable", column = "ISENABLE"),
	      
	      @Result(property = "accountInfo", javaType=AccountInfo.class, column = "PARTYINFOID"
	      , one =@One(select="getAccountInfo"))
	      
	})
	public List<PaymentReceivedInfo> getAllWithAccountInfo() throws Exception;

	@Update(update)
	void update(PaymentReceivedInfo paymentReceivedInfo) throws Exception;

	@Update(disableByID)
	void disablepaymentReceivedInfoByID(PaymentReceivedInfo paymentReceivedInfo) throws Exception;
	
	@Delete(deleteById)
	void delete(int id) throws Exception;
	  
	@Insert(insert)
	@Options(useGeneratedKeys = true, keyProperty = "id")
	void insert(PaymentReceivedInfo paymentReceivedInfo) throws Exception;
	
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
	
	/*--------------Filter Queries--------------------*/
	@Select(getAllByPartyName)
	   @Results(value = {
	      @Result(property = "id", column = "ID"),
	      @Result(property = "partyInfoID", column = "PARTYINFOID"),
	      @Result(property = "receivedPayment", column = "RECEIVEDPAYMENT"),
	      @Result(property = "adjustedPayment", column = "ADJUSTEDPAYMENT"),
	      @Result(property = "remainingAmount", column = "REMAININGAMOUNT"),
	      @Result(property = "status", column = "STATUS"),
	      @Result(property = "remark", column = "REMARK"),
	      @Result(property = "type", column = "TYPE"),
	      @Result(property = "receivedDate", column = "RECEIVEDDATE"),
	      
	      @Result(property = "createdDate", column = "CREATEDDATE"),
	      @Result(property = "lastUpdated", column = "LASTUPDATED"),
	      @Result(property = "isEnable", column = "ISENABLE"),
	      
	      @Result(property = "accountInfo", javaType=AccountInfo.class, column = "PARTYINFOID"
	      , one =@One(select="getAccountInfo"))
	})
	public List<PaymentReceivedInfo> getAllByPartyName(String partyInfoID) throws Exception;
	
	@Select(getAllByStartDate)
	   @Results(value = {
	      @Result(property = "id", column = "ID"),
	      @Result(property = "partyInfoID", column = "PARTYINFOID"),
	      @Result(property = "receivedPayment", column = "RECEIVEDPAYMENT"),
	      @Result(property = "adjustedPayment", column = "ADJUSTEDPAYMENT"),
	      @Result(property = "remainingAmount", column = "REMAININGAMOUNT"),
	      @Result(property = "status", column = "STATUS"),
	      @Result(property = "remark", column = "REMARK"),
	      @Result(property = "type", column = "TYPE"),
	      @Result(property = "receivedDate", column = "RECEIVEDDATE"),
	      
	      @Result(property = "createdDate", column = "CREATEDDATE"),
	      @Result(property = "lastUpdated", column = "LASTUPDATED"),
	      @Result(property = "isEnable", column = "ISENABLE"),
	      
	      @Result(property = "accountInfo", javaType=AccountInfo.class, column = "PARTYINFOID"
	      , one =@One(select="getAccountInfo"))
	})
	public List<PaymentReceivedInfo> getAllByStartDate(@Param("startDate") Date startDate) throws Exception;
	
	@Select(getAllByStartEndDate)
	   @Results(value = {
	      @Result(property = "id", column = "ID"),
	      @Result(property = "partyInfoID", column = "PARTYINFOID"),
	      @Result(property = "receivedPayment", column = "RECEIVEDPAYMENT"),
	      @Result(property = "adjustedPayment", column = "ADJUSTEDPAYMENT"),
	      @Result(property = "remainingAmount", column = "REMAININGAMOUNT"),
	      @Result(property = "status", column = "STATUS"),
	      @Result(property = "remark", column = "REMARK"),
	      @Result(property = "type", column = "TYPE"),
	      @Result(property = "receivedDate", column = "RECEIVEDDATE"),
	      
	      @Result(property = "createdDate", column = "CREATEDDATE"),
	      @Result(property = "lastUpdated", column = "LASTUPDATED"),
	      @Result(property = "isEnable", column = "ISENABLE"),
	      
	      @Result(property = "accountInfo", javaType=AccountInfo.class, column = "PARTYINFOID"
	      , one =@One(select="getAccountInfo"))
	})
	public List<PaymentReceivedInfo> getAllByStartEndDate(PaymentReceivedInfo paymentReceivedInfo) throws Exception;
	
	@Select(getAllByType)
	   @Results(value = {
	      @Result(property = "id", column = "ID"),
	      @Result(property = "partyInfoID", column = "PARTYINFOID"),
	      @Result(property = "receivedPayment", column = "RECEIVEDPAYMENT"),
	      @Result(property = "adjustedPayment", column = "ADJUSTEDPAYMENT"),
	      @Result(property = "remainingAmount", column = "REMAININGAMOUNT"),
	      @Result(property = "status", column = "STATUS"),
	      @Result(property = "remark", column = "REMARK"),
	      @Result(property = "type", column = "TYPE"),
	      @Result(property = "receivedDate", column = "RECEIVEDDATE"),
	      
	      @Result(property = "createdDate", column = "CREATEDDATE"),
	      @Result(property = "lastUpdated", column = "LASTUPDATED"),
	      @Result(property = "isEnable", column = "ISENABLE"),
	      
	      @Result(property = "accountInfo", javaType=AccountInfo.class, column = "PARTYINFOID"
	      , one =@One(select="getAccountInfo"))
	})
	public List<PaymentReceivedInfo> getAllByType(PaymentReceivedInfo paymentReceivedInfo) throws Exception;
	
	/*-------------End--------------------*/
}
