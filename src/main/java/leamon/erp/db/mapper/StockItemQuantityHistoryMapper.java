package leamon.erp.db.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import leamon.erp.model.StockItemQuantityHistory;

/**
 * It records history of actions for stock item quantity.
 * 
 * @author Leamon India Inc.
 * @date Mar 20,2018
 * 
 * @version 1.0
 * 
 * @SoftwareVersion : 3.9
 */

public interface StockItemQuantityHistoryMapper {
	final String getAll = "SELECT * FROM STOCK_ITEM_QUANTITY_HISTORY WHERE ISENABLE = TRUE ORDER BY ID"; 
	final String getById = "SELECT * FROM STOCK_ITEM_QUANTITY_HISTORY WHERE ID = #{id}";
	
	final String insert = "INSERT INTO STOCK_ITEM_QUANTITY_HISTORY (STOCK_ITEM_ID, OLDQUANTITY, PLUSQUANTITY, MINUSQUANTITY, NEWQUANTITY, ACTION, DESCRIPTION, CREATEDDATE, LASTUPDATEDDATE, ISENABLE) "
						+ "VALUES (#{stokItemid}, #{oldQuantity}, #{plusQuantity}, #{minusQuantity}, #{newQuantity}, #{action}, #{description}, #{createdDate}, #{lastUpdatedDate}, #{isEnable} )";
	
	final String update = "UPDATE STOCK_ITEM_QUANTITY_HISTORY SET STOCK_ITEM_ID = #{stokItemid}, "
						+ "QUANTITY = #{quantity},  "
						+ "DESCRIPTION = #{description}, LASTUPDATEDDATE = #{lastUpdatedDate}  WHERE ID = #{id}";
	
	final String deleteById = "DELETE from STOCK_ITEM_QUANTITY_HISTORY WHERE ID = #{id}";
	
	final String disableByID = "UPDATE STOCK_ITEM_QUANTITY_HISTORY SET ISENABLE = FALSE WHERE ID = #{id}";
	
	//@Options(flushCache= FlushCachePolicy.TRUE)
	@Select(getAll)
	   @Results(value = {
	      @Result(property = "id", column = "ID"),
	      @Result(property = "stockItemId", column = "STOCK_ITEM_ID"),
	      @Result(property = "oldQuantity", column = "OLDQUANTITY"),
	      @Result(property = "plusQuantity", column = "PLUSQUANTITY"),
	      @Result(property = "minusQuantity", column = "MINUSQUANTITY"),
	      @Result(property = "newQuantity", column = "NEWQUANTITY"),
	      @Result(property = "action", column = "ACTION"),
	      @Result(property = "description", column = "DESCRIPTION"),
	      @Result(property = "createdDate", column = "CREATEDDATE"),
	      @Result(property = "lastUpdatedDate", column = "LASTUPDATEDDATE"),
	      @Result(property = "isEnable", column = "ISENABLE")
	})
	public List<StockItemQuantityHistory> getAll() throws Exception;
	
	@Update(update)
	void update(StockItemQuantityHistory stockItemQuantityHistory) throws Exception;

	@Update(disableByID)
	void disableStockByID(StockItemQuantityHistory stockItemQuantityHistory) throws Exception;
	
	@Delete(deleteById)
	void delete(int id) throws Exception;
	  
	@Insert(insert)
	@Options(useGeneratedKeys = true, keyProperty = "id")
	void insert(StockItemQuantityHistory stockItemQuantityHistory) throws Exception;
	
}
