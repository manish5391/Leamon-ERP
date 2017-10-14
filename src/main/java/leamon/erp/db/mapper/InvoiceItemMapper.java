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

import leamon.erp.model.InvoiceItemInfo;

@CacheNamespace(implementation = org.mybatis.caches.ehcache.EhcacheCache.class)
public interface InvoiceItemMapper {

	final String getAll = "SELECT * FROM INVOICE_ITEM WHERE ISENABLE = TRUE"; 
	final String getById = "SELECT * FROM INVOICE_ITEM WHERE ID = #{id}";
	
	final String insert = "INSERT INTO INVOICE_ITEM (INVOICEID, STOCKITEMID, SNO, DESCRIPTION, SIZE, QTY, UNIT, RATE, "
							+ " AMOUNT, TD, CREATEDDATE, LASTUPDATEDDATE, ISENABLE) "
						+ "VALUES (#{invoiceID}, #{stockItemId}, #{sno}, #{description}, #{size}, #{qty}, #{unit}, #{rate}, "
						+ "#{amount}, #{td}, #{createdDate}, #{lastUpdated}, #{isEnable})";
	
	final String update = "UPDATE INVOICE_ITEM SET INVOICEID = #{invoiceID}, STOCKITEMID = #{stockItemId}, SNO = #{sno}, DESCRIPTION = #{description},  SIZE = #{size}, QTY = #{qty}, UNIT = #{unit}, "
						+ "RATE = #{rate}, AMOUNT = #{amount}, TD = #{td}, "
						+ "LASTUPDATEDDATE = #{lastUpdated} "
						+ " WHERE ID = #{id}";
	
	final String deleteById = "DELETE from INVOICE_ITEM WHERE ID = #{id}";
	
	final String disableByID = "UPDATE INVOICE_ITEM SET ISENABLE = FALSE WHERE ID = #{id}";
	
	@Select(getAll)
	   @Results(value = {
	      @Result(property = "id", column = "ID"),
	      @Result(property = "invoiceID", column = "INVOICEID"),
	      @Result(property = "stockItemId", column = "STOCKITEMID"),
	      @Result(property = "sno", column = "SNO"),
	      @Result(property = "description", column = "DESCRIPTION"),
	      @Result(property = "size", column = "SIZE"),
	      @Result(property = "qty", column = "QTY"),
	      @Result(property = "unit", column = "UNIT"),
	      @Result(property = "rate", column = "RATE"),
	      @Result(property = "amount", column = "AMOUNT"),
	      @Result(property = "td", column = "TD"),
	      @Result(property = "createdDate", column = "CREATEDDATE"),
	      @Result(property = "lastUpdated", column = "LASTUPDATEDDATE"),
	      @Result(property = "isEnable", column = "ISENABLE")
	})
	public List<InvoiceItemInfo> getAll();
	
	@Update(update)
	void update(InvoiceItemInfo InvoiceItemInfo);

	@Update(disableByID)
	void disableInvoiceById(InvoiceItemInfo InvoiceItemInfo);
	
	@Delete(deleteById)
	void delete(int id);
	  
	@Insert(insert)
	@Options(useGeneratedKeys = true, keyProperty = "id")
	void insert(InvoiceItemInfo InvoiceItemInfo);
}
