package leamon.erp.db.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import leamon.erp.model.PaymentReceivedInfo;
import leamon.erp.model.PaymentReceivedInfo;

public interface PaymentReceivedMapper {
	final String getAll = "SELECT * FROM PAYMENT_RECEIVED_INFO WHERE ISENABLE = TRUE ORDER BY ID"; 
	final String getById = "SELECT * FROM PAYMENT_RECEIVED_INFO WHERE ID = #{id}";
	
	final String insert = "INSERT INTO PAYMENT_RECEIVED_INFO (PARTYINFOID, RECEIVEDPAYMENT, RECEIVEDATE, BREMARK, WREMARK, CREATEDDATE, LASTUPDATED, ISENABLE) "
						+ "VALUES (#{partyInfoID}, #{receivedPayment}, #{receivedDate}, #{bRemark}, #{wRemark}, #{createdDate}, #{lastUpdated}, #{isEnable} )";
	
	final String update = "UPDATE PAYMENT_RECEIVED_INFO SET PARTYINFOID = #{partyInfoID}, RECEIVEDPAYMENT = #{receivedPayment}, RECEIVEDATE = #{receivedDate}, "
						+ "BREMARK = #{bRemark}, "
						+ "WREMARK = #{wRemark},  "
						+ "LASTUPDATEDDATE = #{lastUpdated}  WHERE ID = #{id}";
	
	final String deleteById = "DELETE from PAYMENT_RECEIVED_INFO WHERE ID = #{id}";
	
	final String disableByID = "UPDATE PAYMENT_RECEIVED_INFO SET ISENABLE = FALSE WHERE ID = #{id}";
	
	//@Options(flushCache= FlushCachePolicy.TRUE)
	@Select(getAll)
	   @Results(value = {
	      @Result(property = "id", column = "ID"),
	      @Result(property = "partyInfoID", column = "PARTYINFOID"),
	      @Result(property = "receivedPayment", column = "RECEIVEDPAYMENT"),
	      @Result(property = "receivedDate", column = "RECEIVEDATE"),
	      @Result(property = "bRemark", column = "BREMARK"),
	      @Result(property = "wRemark", column = "WREMARK"),
	      @Result(property = "isEnable", column = "ISENABLE"),
	      @Result(property = "createdDate", column = "CREATEDDATE"),
	      @Result(property = "lastUpdated", column = "LASTUPDATED")
	      
	})
	public List<PaymentReceivedInfo> getAll() throws Exception;
	
	

	@Update(update)
	void update(PaymentReceivedInfo paymentReceivedInfo) throws Exception;

	@Update(disableByID)
	void disablepaymentReceivedInfoByID(PaymentReceivedInfo paymentReceivedInfo) throws Exception;
	
	@Delete(deleteById)
	void delete(int id) throws Exception;
	  
	@Insert(insert)
	@Options(useGeneratedKeys = true, keyProperty = "id")
	void insert(PaymentReceivedInfo paymentReceivedInfo) throws Exception;
}
