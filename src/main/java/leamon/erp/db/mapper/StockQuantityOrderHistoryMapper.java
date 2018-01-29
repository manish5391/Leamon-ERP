package leamon.erp.db.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import leamon.erp.model.StockItemQuantityOrderHistory;

/**
 * @author Leamon India Inc.
 * @date 05 Nov, 2017
 * 
 * @version 1.0
 * 
 * @SoftwareVersion : 3.1
 */

public interface StockQuantityOrderHistoryMapper {
	final String getAll = "SELECT * FROM STOCK_ITEM_QUANTITY_ORDER_HISTORY WHERE ISENABLE = TRUE ORDER BY ID"; 
	final String getById = "SELECT * FROM STOCK_ITEM_QUANTITY_ORDER_HISTORY WHERE ID = #{id}";
	
	final String insert = "INSERT INTO STOCK_ITEM_QUANTITY_ORDER_HISTORY (STOCK_ITEM_ID, INVOICEID, INVOICE_ITEM_IID, QUANTITY, DESCRIPTION, CREATEDDATE, LASTUPDATEDDATE, ISENABLE) "
						+ "VALUES (#{stokItemid}, #{invoiceid}, #{invoiceItemId}, #{quantity}, #{description}, #{createdDate}, #{lastUpdatedDate}, #{isEnable} )";
	
	final String update = "UPDATE STOCK_ITEM_QUANTITY_ORDER_HISTORY SET STOCK_ITEM_ID = #{stokItemid}, INVOICEID = #{invoiceid}, "
						+ "INVOICE_ITEM_IID = #{invoiceItemId}, QUANTITY = #{quantity},  "
						+ "DESCRIPTION = #{description}, LASTUPDATEDDATE = #{lastUpdatedDate}  WHERE ID = #{id}";
	
	final String deleteById = "DELETE from STOCK_ITEM_QUANTITY_ORDER_HISTORY WHERE ID = #{id}";
	
	final String disableByID = "UPDATE STOCK_ITEM_QUANTITY_ORDER_HISTORY SET ISENABLE = FALSE WHERE ID = #{id}";
	
	//@Options(flushCache= FlushCachePolicy.TRUE)
	@Select(getAll)
	   @Results(value = {
	      @Result(property = "id", column = "ID"),
	      @Result(property = "stokItemid", column = "STOCK_ITEM_ID"),
	      @Result(property = "invoiceid", column = "INVOICEID"),
	      @Result(property = "invoiceItemId", column = "INVOICE_ITEM_IID"),
	      @Result(property = "quantity", column = "QUANTITY"),
	      @Result(property = "description", column = "DESCRIPTION"),
	      @Result(property = "isEnable", column = "ISENABLE"),
	      @Result(property = "createdDate", column = "CREATEDDATE"),
	      @Result(property = "lastUpdatedDate", column = "LASTUPDATEDDATE")
	      
	})
	public List<StockItemQuantityOrderHistory> getAll() throws Exception;
	
	@Update(update)
	void update(StockItemQuantityOrderHistory stockItemQuantityOrderHistory) throws Exception;

	@Update(disableByID)
	void disableStockByID(StockItemQuantityOrderHistory stockItemQuantityOrderHistory) throws Exception;
	
	@Delete(deleteById)
	void delete(int id) throws Exception;
	  
	@Insert(insert)
	@Options(useGeneratedKeys = true, keyProperty = "id")
	void insert(StockItemQuantityOrderHistory stockItemQuantityOrderHistory) throws Exception;
	
}
