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

import leamon.erp.model.PaymentInvoiceMappingInfo;

@CacheNamespace(implementation = org.mybatis.caches.ehcache.EhcacheCache.class)
public interface PaymentInvoiceMappingMapper {
	
	final String getAll = "SELECT * FROM PAYMENT_INVOICE_MAPPING WHERE ISENABLE = TRUE"; 
	final String getById = "SELECT * FROM PAYMENT_INVOICE_MAPPING WHERE ID = #{id}";
	
	final String insert = "INSERT INTO PAYMENT_INVOICE_MAPPING (PAYMENTID, INVOICEID, CREATEDDATE, LASTUPDATED, ISENABLE) "
						+ "VALUES ( "
						+ "#{paymentReceivedInfo}, #{invoiceInfoID}, #{createdDate}, #{lastUpdated}, #{isEnable})";
	
	final String update = "UPDATE PAYMENT_INVOICE_MAPPING SET PAYMENTID = #{paymentReceivedInfo}, INVOICEID = #{invoiceInfoID}, "
						+ "LASTUPDATED = #{lastUpdated} "
						+ " WHERE ID = #{id}";
	
	final String deleteById = "DELETE from PAYMENT_INVOICE_MAPPING WHERE ID = #{id}";
	
	final String disableByID = "UPDATE PAYMENT_INVOICE_MAPPING SET ISENABLE = FALSE WHERE ID = #{id}";
	

	@Select(getAll)
	   @Results(value = {
	      @Result(property = "id", column = "ID"),
	      
	      @Result(property = "paymentReceivedInfo", column = "PAYMENTID"),
	      @Result(property = "invoiceInfoID", column = "INVOICEID"),
	      
	      @Result(property = "createdDate", column = "CREATEDDATE"),
	      @Result(property = "lastUpdated", column = "LASTUPDATED"),
	      @Result(property = "isEnable", column = "ISENABLE")
	})
	public List<PaymentInvoiceMappingInfo> getAll();
	
	@Update(update)
	void update(PaymentInvoiceMappingInfo PaymentInvoiceMappingInfo);

	@Update(disableByID)
	void disableByID(PaymentInvoiceMappingInfo PaymentInvoiceMappingInfo);
	
	@Delete(deleteById)
	void delete(int id);
	  
	@Insert(insert)
	@Options(useGeneratedKeys = true, keyProperty = "id")
	void insert(PaymentInvoiceMappingInfo PaymentInvoiceMappingInfo);
}
